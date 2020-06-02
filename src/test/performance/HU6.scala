package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU6 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_5 = Map("User-Agent" -> "Get Flash Player version xml/1.0")

    val uri2 = "http://fpdownload2.macromedia.com/get/flashplayer/update/current/xml/version_es_win_pep.xml"


	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(13)
	}

	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(13)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "${stoken}")
		).pause(13)
	}

	object ShowListTournament {
		val showListTournament= exec(http("ShowListTournament")
			.get("/tournaments")
			.headers(headers_0))
		.pause(35)
	}

	object ShowTournament {
		val showTournament = exec(http("ShowTournament")
			.get(uri2)
			.headers(headers_5))
		.pause(7)
	}

	object SponsorForm {
	val sponsorForm = 	exec(http("SponsorForm")
			.get("/tournaments/2/sponsors/add")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
	).pause(28)
		.exec(http("SponsorCreated")
			.post("/tournaments/2/sponsors/add")
			.headers(headers_3)
			.formParam("name", "luis")
			.formParam("money", "7500.")
			.formParam("url", "https://www.google.es")
			.formParam("sponsorId", "")
			.formParam("_csrf", "${stoken}")
		).pause(15)
	}

	object EditTournament {
		val editTournament = exec(http("EditTournament")
			.get("/tournaments/race/2/edit")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(14)
		exec(http("EditFinished")
			.post("/tournaments/race/2/edit")
			.headers(headers_3)
			.formParam("name", "Second Race Test")
			.formParam("date", "2020/06/14")
			.formParam("capacity", "800")
			.formParam("breedRestriction", "Greyhound")
			.formParam("rewardMoney", "7500.0")
			.formParam("canodrome", "Lorem Ipsum Patata")
			.formParam("status", "PENDING")
			.formParam("petId", "2")
			.formParam("_csrf", "${stoken}")
		).pause(34)
	}

	object TournamentForm {
		val tournamentForm = exec(http("TournamentForm")
			.get("/race/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(49)
		exec(http("TournamentCreated")
			.post("/race/new")
			.headers(headers_3)
			.formParam("name", "race performace test")
			.formParam("date", "2020/05/26")
			.formParam("capacity", "800")
			.formParam("breedRestriction", "Basset Hound")
			.formParam("rewardMoney", "7000.")
			.formParam("canodrome", "Benito Villamarin")
			.formParam("petId", "")
			.formParam("_csrf", "${stoken}")
		).pause(18)
	}


	val scnHU6Positive = scenario("HU6Positive").exec(Home.home,
											Login.login,
											ShowListTournament.showListTournament,
											ShowTournament.showTournament,
											SponsorForm.sponsorForm,
											EditTournament.editTournament
											)
	val scnHU6Negative = scenario("HU6Negative").exec(Home.home,
											Login.login,
											ShowListTournament.showListTournament,
											TournamentForm.tournamentForm
											)
		
		

	setUp(
		scnHU6Positive.inject(atOnceUsers(1)),
		scnHU6Negative.inject(atOnceUsers(1))
	).protocols(httpProtocol)
	}