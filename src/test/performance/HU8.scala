package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU8 extends Simulation {

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
		.pause(8)
	}

	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(14)
		.exec(http("Login")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "${stoken}"))
		.pause(8)
	}

	object ShowTournaments {
		val showTournaments = exec(http("ShowTournaments")
			.get("/tournaments")
			.headers(headers_0))
		.pause(11)
	}

	object ShowTournament {
		val showTournament = exec(http("ShowTournament")
			.get("/tournaments/4")
			.headers(headers_0))
		.pause(21)
	}

	object AddNewDog {
		val addNewDog = exec(http("AddNewDog")
			.get("/pet/tournament/4")
			.headers(headers_0))
		.pause(12)
	}

	object AddNewDogCorrect {
		val addNewDogCorrect = exec(http("AddNewDogCorrect")
			.get("/tournaments/4/addpet/7")
			.headers(headers_0))
		.pause(20)
	}

	object FailAddNewDog {
		val failAddNewDog = exec(http("FailAddNewDog")
			.get("/tournaments/4/addpet/2")
			.headers(headers_0))
		.pause(8)
	}

	val scnHU8Positive = scenario("HU8Positive").exec(Home.home,
									Login.login,
									ShowTournaments.showTournaments,
									ShowTournament.showTournament,
									AddNewDog.addNewDog,
									AddNewDogCorrect.addNewDogCorrect
	)

	val scnHU8Negative = scenario("HU8Negative").exec(Home.home,
									Login.login,
									ShowTournaments.showTournaments,
									ShowTournament.showTournament,
									FailAddNewDog.failAddNewDog
	)
	

		setUp(
		scnHU8Positive.inject(rampUsers(5000) during (100 seconds)),
		scnHU8Negative.inject(rampUsers(5000) during (100 seconds))
	).protocols(httpProtocol)
}