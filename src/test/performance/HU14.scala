package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU14 extends Simulation {

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
		val home = exec(http("request_0")
			.get("/"))
		.pause(25)
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
		.pause(12)
	}

	object HabilityContest3 {
		val habilityContest3 = exec(http("HabilityContest3")
			.get("/tournaments/13"))
		.pause(18)
	}

	object ShowResults {
		val showResults = exec(http("ShowResults")
			.get("/tournaments/hability/13/result"))
		.pause(10)
	}

	object HabilityContest4 {
		val habilityContest4 = exec(http("HabilityContest4")
			.get("/tournaments/14"))
		.pause(8)
	}

	object ShowResults2 {
		val showResults2 = exec(http("ShowResults2")
			.get("/tournaments/hability/14/result"))
		.pause(5)
	}
	
	val scnPositive = scenario("ShowResultsHabilityPositive").exec(Home.home,
																Login.login,
																Tournaments.tournaments,
																HabilityContest3.habilityContest3,
																ShowResults.showResults)

	
	val scnNegative = scenario("ShowResultsHabilityNegative").exec(Home.home,
																Login.login,
																Tournaments.tournaments,
																HabilityContest4.habilityContest4,
																ShowResults2.showResults2)


	setUp(
		scnPositive.inject(rampUsers(150000) during (50 seconds)),
		scnNegative.inject(rampUsers(150000) during (50 seconds))
	)
	.protocols(httpProtocol)
			.assertions(
				global.responseTime.max.lt(5000),
				global.responseTime.mean.lt(1000),
				global.successfulRequests.percent.gt(95)

			)


}