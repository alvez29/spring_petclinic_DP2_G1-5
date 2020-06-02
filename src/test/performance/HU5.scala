package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU5 extends Simulation {

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

	val headers_5 = Map(
		"Accept" -> "*/*",
		"Proxy-Connection" -> "Keep-Alive",
		"User-Agent" -> "Microsoft-CryptoAPI/10.0")

	val headers_8 = Map(
		"Proxy-Connection" -> "Keep-Alive",
		"User-Agent" -> "Microsoft-WNS/10.0")

    val uri2 = "http://ctldl.windowsupdate.com/msdownload/update/v3/static/trustedr/en"
    val uri3 = "http://cdn.content.prod.cms.msn.com/singletile/summary/alias/experiencebyname/today"

	object Home {
		val home = exec(http("request_0")
			.get("/")
			.headers(headers_0))
		.pause(10)
	}

	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_2)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(4)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "${stoken}"))
		.pause(19)
	}

	object ShowListTournament {
		val showListTournament = exec(http("ShowListTournament")
			.get("/tournaments")
			.headers(headers_0))
		.pause(14)
	}

	object ShowTournament {
		val showTournament = exec(http("ShowTournament")
			.get("/tournaments/10")
			.headers(headers_0))
		.pause(15)
	}

	object EditTournament {
		val editTournament = exec(http("EditTournament")
			.get(uri3 + "?market=es-ES&source=appxmanifest&tenant=amp&vertical=news")
			.headers(headers_8)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(15)
		.exec(http("EditFinished")
			.post("/tournaments/race/10/edit")
			.headers(headers_3)
			.formParam("name", "Fourth Race Test")
			.formParam("date", "2020/10/14")
			.formParam("capacity", "800")
			.formParam("breedRestriction", "Greyhound")
			.formParam("rewardMoney", "7500.0")
			.formParam("canodrome", "Gran Hipodromo de Andalucia")
			.formParam("status", "PENDING")
			.formParam("petId", "10")
			.formParam("_csrf", "${stoken}"))
		.pause(20)
	}

	object SponsorCreated {
		val sponsorCreated = exec(http("SponsorsForm")
			.get("/tournaments/6/sponsors/add")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(8)
		.exec(http("SponsorCreated")
			.post("/tournaments/6/sponsors/add")
			.headers(headers_3)
			.formParam("name", "luis")
			.formParam("money", "700.0")
			.formParam("url", "https://www.google.es")
			.formParam("sponsorId", "")
			.formParam("_csrf", "${stoken}"))
		.pause(20)
	}

	object FailEdit {
		val failEdit = exec(http("EditTournament")
			.get("/tournaments/beauty/6/edit")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(17)
		.exec(http("FailEdit")
			.post("/tournaments/beauty/6/edit")
			.headers(headers_3)
			.formParam("name", "Beauty ConTEST2")
			.formParam("date", "2021/06/04")
			.formParam("capacity", "800")
			.formParam("breedRestriction", "Beagle")
			.formParam("rewardMoney", "7500.0")
			.formParam("place", "Pabellon España")
			.formParam("status", "PENDING")
			.formParam("petId", "6")
			.formParam("_csrf", "${stoken}"))
		.pause(12)
	}

	val scnHU5Positive = scenario("HU5Positive").exec(Home.home,
														Login.login,
														ShowListTournament.showListTournament,
														ShowTournament.showTournament,
														EditTournament.editTournament,
	)

	val scnHU5Negative = scenario("HU5Negative").exec(Home.home,
														Login.login,
														ShowListTournament.showListTournament,
														ShowTournament.showTournament,
														SponsorCreated.sponsorCreated,
														FailEdit.failEdit
	)
		

	setUp(
		scnHU5Positive.inject(atOnceUsers(1)),
		scnHU5Negative.inject(atOnceUsers(1))
	).protocols(httpProtocol)
}