package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU15 extends Simulation {

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
		.pause(6)
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
		.pause(11)
	}

	object HabilityContest {
		val habilityContest = exec(http("HabilityContest")
			.get("/tournaments/5"))
		.pause(13)
	}

	object AddDog {
		val addDog = exec(http("AddDog")
			.get("/pet/tournament/5"))
		.pause(10)
	}

	object DogAddded {
		val dogAdded = exec(http("DogAddded")
			.get("/tournaments/5/addpet/8"))
		.pause(9)
	}

	object Error {
		val error = exec(http("Error")
			.get("/tournaments/5/addpet/5"))
		.pause(8)
	} 

	val scnPositive = scenario("ShowResultsHabilityPositive").exec(Home.home,
																Login.login,
																Tournaments.tournaments,
																HabilityContest.habilityContest,
																AddDog.addDog,
																DogAddded.dogAdded)

	
	val scnNegative = scenario("ShowResultsHabilityNegative").exec(Home.home,
																Login.login,
																Tournaments.tournaments,
																HabilityContest.habilityContest,
																AddDog.addDog,
																Error.error)


	setUp(
		scnPositive.inject(rampUsers(2000) during (100 seconds)),
		scnNegative.inject(rampUsers(2000) during (100 seconds))
	)
	.protocols(httpProtocol)
			.assertions(
				global.responseTime.max.lt(5000),
				global.responseTime.mean.lt(1000),
				global.successfulRequests.percent.gt(95)

			)



}