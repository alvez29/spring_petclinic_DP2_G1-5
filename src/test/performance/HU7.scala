package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU7 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")


		object Home {
			val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(7)
		}

		object Login {
			val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
			).pause(9)
			.exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "${stoken}"))
		.pause(7)
		}

		object ShowTournaments {
			val showTournaments = exec(http("ShowTournaments")
			.get("/tournaments")
			.headers(headers_0))
		.pause(14)
		}

		object RaceCreated {
		val raceCreated = exec(http("RaceForm")
			.get("/race/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(49)
		.exec(http("RaceCreated")
			.post("/race/new")
			.headers(headers_3)
			.formParam("name", "race hutest 1")
			.formParam("date", "2020/11/05")
			.formParam("capacity", "800")
			.formParam("breedRestriction", "Basset Hound")
			.formParam("rewardMoney", "7000.")
			.formParam("canodrome", "Benito Villamarin")
			.formParam("petId", "")
			.formParam("_csrf", "${stoken}"))
		.pause(15)
		}

		object SponsorsCreated {
			val sponsorsCreated = exec(http("SponsorsForm")
			.get("/tournaments/17/sponsors/add")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
			).pause(13)
			.exec(http("SponsorsCreated")
			.post("/tournaments/17/sponsors/add")
			.headers(headers_3)
			.formParam("name", "luis")
			.formParam("money", "7500.")
			.formParam("url", "https://www.google.es")
			.formParam("sponsorId", "")
			.formParam("_csrf", "${stoken}"))
		.pause(15)
		}

		object AddJudge {
			val addJudge = exec(http("AddJudge")
			.get("/judge/tournament/17")
			.headers(headers_0))
		.pause(13)
		}

		object AddJuggeCorrect {
			val addJuggeCorrect = exec(http("AddJuggeCorrect")
			.get("/tournaments/17/addjudge/1")
			.headers(headers_0))
		.pause(31)
		}

		object BeautyCreated {
			val  beautyCreated = exec(http("BeautyForm")
			.get("/beauty/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
			).pause(59)
			.exec(http("BeautyCreated")
			.post("/beauty/new")
			.headers(headers_3)
			.formParam("name", "beauty hutest ")
			.formParam("date", "2020/11/05")
			.formParam("capacity", "800")
			.formParam("breedRestriction", "Basset Hound")
			.formParam("rewardMoney", "7000.")
			.formParam("place", "sevilla")
			.formParam("petId", "")
			.formParam("_csrf", "${stoken}"))
		.pause(29)
		}

		object EditTournament {
			val editTournament = exec(http("EditTournament")
			.get("/tournaments/beauty/18/edit")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
			).pause(20)
			.exec(http("FailEdit")
			.post("/tournaments/beauty/18/edit")
			.headers(headers_3)
			.formParam("name", "beauty hutest ")
			.formParam("date", "2020/11/04")
			.formParam("capacity", "800")
			.formParam("breedRestriction", "Basset Hound")
			.formParam("rewardMoney", "7000.0")
			.formParam("place", "sevilla")
			.formParam("status", "PENDING")
			.formParam("petId", "18")
			.formParam("_csrf", "${stoken}"))
		.pause(12)
		}


	val scnHU7Positive = scenario("HU7Positive").exec(Home.home,
									Login.login,
									ShowTournaments.showTournaments,
									RaceCreated.raceCreated,
									SponsorsCreated.sponsorsCreated,
									AddJudge.addJudge,
									AddJuggeCorrect.addJuggeCorrect
)

	val scnHU7Negative = scenario("HU7Negative").exec(Home.home,
									Login.login,
									ShowTournaments.showTournaments,
									BeautyCreated.beautyCreated,
									SponsorsCreated.sponsorsCreated,
									AddJudge.addJudge,
									AddJuggeCorrect.addJuggeCorrect,
									ShowTournaments.showTournaments,
									EditTournament.editTournament,
									
)
		

	setUp(
		scnHU7Positive.inject(atOnceUsers(1)),
		scnHU7Negative.inject(atOnceUsers(1))
	).protocols(httpProtocol)
}