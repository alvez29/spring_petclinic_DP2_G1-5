package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU12 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.js""", """.*.css""", """.*.png""", """.*.ico"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_3 = Map("Origin" -> "http://www.dp2.com")

	object Home{
		val home = exec(http("Home")
			.get("/")
			.resources(http("request_1")
			.get("/")))
		.pause(4)
	}

	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_3)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(16)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "${stoken}"))
		.pause(9) 
	}



	object Tournaments {
		val tournaments = exec(http("Tournaments")
			.get("/tournaments"))
		.pause(12)
	}

	object FirstRace {
		val firstRace = exec(http("FirstRace")
			.get("/tournaments/1"))
		.pause(8)
	}

	object ShowResultFirstRace {
		val showResultFirstRace = exec(http("ShowResultFirstRace")
			.get("/tournaments/race/1/result"))
		.pause(26)
	}

	object SecondRace {
		val secondRace = exec(http("SecondRace")
			.get("/tournaments/2"))
		.pause(8)
	}

	object NoDataShowResults {
		val noDataShowResults = exec(http("NoDataShowResults")
			.get("/tournaments/race/2/result"))
		.pause(14)
	}

	val scnPositive = scenario("scnPositive").exec(
		Home.home,
		Login.login,
		Tournaments.tournaments,
		FirstRace.firstRace,
		ShowResultFirstRace.showResultFirstRace
	)

	val scnNegative = scenario("scnNegative").exec(
		Home.home,
		Login.login,
		Tournaments.tournaments,
		SecondRace.secondRace,
		NoDataShowResults.noDataShowResults
	)
		

setUp(
		scnPositive.inject(rampUsers(1) during (50 seconds)),
		scnNegative.inject(rampUsers(1) during (50 seconds))
		).protocols(httpProtocol)
		 .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )}