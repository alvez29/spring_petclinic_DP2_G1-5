package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU10 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.js""", """.*.css""", """.*.png""", """.*.ico"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_2 = Map("Origin" -> "http://www.dp2.com")


	object Home {
		val home = exec(http("Home")
			.get("/"))
		.pause(4) 
	}

	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_2)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(16)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "${stoken}"))
		.pause(9) 
	}


	object Tournaments {
		val tournaments = exec(http("Tournaments")
			.get("/tournaments"))
		.pause(11)
	}

	object BeautyContest {
		val beautyContest = exec(http("BeautyContest")
			.get("/tournaments/6"))
		.pause(14)
	}

	object AddSponsorPos {
		val addSponsorPos = exec(http("AddSponsorPos")
			.get("/tournaments/6/sponsors/add")
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(29)
		.exec(http("AddedSponsor")
			.post("/tournaments/6/sponsors/add")
			.headers(headers_2)
			.formParam("name", "Sponsor Test")
			.formParam("money", "7000")
			.formParam("url", "http://www.google.es")
			.formParam("sponsorId", "")
			.formParam("_csrf", "${stoken}"))
		.pause(14)
	}

	object AddSponsorNeg {
		val addSponsorNeg = exec(http("AddSponsorNeg")
			.get("/tournaments/6/sponsors/add")
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(29)
		.exec(http("NotAddedSponsor")
			.post("/tournaments/6/sponsors/add")
			.headers(headers_2)
			.formParam("name", "Sponsor1")
			.formParam("money", "7000")
			.formParam("url", "http://www.google.es")
			.formParam("sponsorId", "")
			.formParam("_csrf", "${stoken}")
			.resources(http("request_11")
			.get("/tournaments/6/sponsors/add")))
		.pause(36)
	}

	object BeautyContestNoSponsor{
		val beautyContestNoSponsor = exec(http("request_12")
			.get("/tournaments/6"))
		.pause(17)
	}

	val scnPositive = scenario("scnPositive").exec(
		Home.home,
		Login.login,
		Tournaments.tournaments,
		BeautyContest.beautyContest,
		AddSponsorPos.addSponsorPos
	)

	val scnNegative = scenario("scnNegative").exec(
		Home.home,
		Login.login,
		Tournaments.tournaments,
		BeautyContest.beautyContest,
		AddSponsorNeg.addSponsorNeg,
		BeautyContestNoSponsor.beautyContestNoSponsor
	)	

	setUp(
		scnPositive.inject(rampUsers(5000) during (100 seconds)),
		scnNegative.inject(rampUsers(5000) during (100 seconds))
		).protocols(httpProtocol)
		 .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )}