package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU13 extends Simulation {

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
		.pause(7)
	}

		object Login {
		val login = exec(http("Login")
			.get("/login")
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(8)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "${stoken}"))
		.pause(7)
	}

	object Tournaments {
		val tournaments = exec(http("Tournaments")
			.get("/tournaments"))
		.pause(17)
	}

	object FirstRace {
		val firstRace = exec(http("FirstRace")
			.get("/tournaments/1"))
		.pause(8)
	}

	object ShowResults {
		val showResults = exec(http("ShowResults")
			.get("/tournaments/race/1/result"))
		.pause(9)
	}

	object SecondRace {
		val secondRace = exec(http("SecondRace")
			.get("/tournaments/2"))
		.pause(6)
	}

	object ShowResults2 {
		val showResults2 = exec(http("ShowResults2")
			.get("/tournaments/race/2/result"))
		.pause(7)
	}

	val scnPositive = scenario("ShowResultsRacePositive").exec(Home.home,
																Login.login,
																Tournaments.tournaments,
																FirstRace.firstRace,
																ShowResults.showResults)

	
	val scnNegative = scenario("ShowResultsRaceNegative").exec(Home.home,
																Login.login,
																Tournaments.tournaments,
																SecondRace.secondRace,
																ShowResults2.showResults2)

	setUp(
		scnPositive.inject(rampUsers(125000) during (50 seconds)),
		scnNegative.inject(rampUsers(125000) during (50 seconds))
	)
	.protocols(httpProtocol)
			.assertions(
				global.responseTime.max.lt(5000),
				global.responseTime.mean.lt(1000),
				global.successfulRequests.percent.gt(95)

			)

}