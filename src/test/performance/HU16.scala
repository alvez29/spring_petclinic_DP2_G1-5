package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU16 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.js""", """.*.css""", """.*.png""", """.*.ico"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_0 = Map("Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map(
		"Accept" -> "application/font-woff2;q=1.0,application/font-woff;q=0.9,*/*;q=0.8",
		"Accept-Encoding" -> "identity")

	val headers_2 = Map(
		"Origin" -> "http://www.dp2.com",
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
			.get("/tournaments")
			.headers(headers_0))
		.pause(16)
	}

	object BeautyContest {
		val beautyContest = exec(http("BeautyContest")
			.get("/tournaments/3")
			.headers(headers_0))
		.pause(10)
	}

	object ShowResults {
		val showResults = exec(http("ShowResults")
			.get("/tournaments/beauty/3/result")
			.headers(headers_0))
		.pause(9)
	}

	object BeautyContest2 {
		val beautyContest2 = exec(http("BeautyContest2")
			.get("/tournaments/6")
			.headers(headers_0))
		.pause(11)
	}

	object ShowResults2 {
		val showResults2 = exec(http("ShowResults2")
			.get("/tournaments/beauty/6/result")
			.headers(headers_0))
		.pause(9)
	}

	val scnPositive = scenario("ShowResultsBeautyPositive").exec(Home.home,
																Login.login,
																Tournaments.tournaments,
																BeautyContest.beautyContest,
																ShowResults.showResults)

	
	val scnNegative = scenario("ShowResultsBeautyNegative").exec(Home.home,
																Login.login,
																Tournaments.tournaments,
																BeautyContest2.beautyContest2,
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