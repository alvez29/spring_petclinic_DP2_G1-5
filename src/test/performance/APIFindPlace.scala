package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class APIFindPlace extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.ico""", """.*.js""", """.*.png"""), WhiteList())
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
    val login = exec(
      http("Login")
        .get("/login")
        .headers(headers_0)
        .check(css("input[name=_csrf]", "value").saveAs("stoken"))
    ).pause(20)
    .exec(
      http("Logged")
        .post("/login")
        .headers(headers_3)
        .formParam("username", "admin1")
        .formParam("password", "4dm1n")        
        .formParam("_csrf", "${stoken}")
    ).pause(142)
  }

	object Tournaments {
		val tournaments = exec(http("Tournaments")
			.get("/tournaments")
			.headers(headers_0))
		.pause(22)
	}

	object FirstRace {
		val firstRace = exec(http("FirstRace")
			.get("/tournaments/1")
			.headers(headers_0))
		.pause(28)
	}

	object SecondRace {
		val secondRace = exec(http("SecondRace")
			.get("/tournaments/2")
			.headers(headers_0))
		.pause(19)
	}


	val scnPositive = scenario("APIFindPlacePositive").exec(Home.home,
													Login.login,
													Tournaments.tournaments,
													FirstRace.firstRace)
		
	val scnNegative = scenario("APIFindPlaceNegative").exec(Home.home,
													Login.login,
													Tournaments.tournaments,
													SecondRace.secondRace)

	setUp(
		scnPositive.inject(rampUsers(4500) during (50 seconds)),
		scnNegative.inject(rampUsers(4500) during (50 seconds))
		).protocols(httpProtocol)
		 .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}