package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class FreeVisitMessage extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.ico""", """.*.png""", """.*.js"""), WhiteList())
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

	object FindOwners {
		val findOwners = exec(http("FindOwners")
			.get("/owners/find")
			.headers(headers_0))
		.pause(15)
	}

	object FindOwner {
		val findOwner = exec(http("FindOwner")
			.get("/owners?lastName=")
			.headers(headers_0))
		.pause(14)
	}

	object BettyDavis {
		val bettyDavis = exec(http("BettyDavis")
			.get("/owners/2")
			.headers(headers_0))
		.pause(20)
	}

	object AddVisitBasil {
		val addVisitBasil = exec(http("AddVisitBasil")
			.get("/owners/2/pets/2/visits/new")
			.headers(headers_0))
		.pause(38)
	}

	object GeorgeFranklin {
		val georgeFranklin = exec(http("GeorgeFranklin")
			.get("/owners/1")
			.headers(headers_0))
		.pause(21)
	}

	object AddVisitLeo {
		val addVisitLeo = exec(http("AddVisitLeo")
			.get("/owners/1/pets/1/visits/new")
			.headers(headers_0))
		.pause(19)
	}

	val scnPositive = scenario("FreeVisitMessagePositive").exec(Home.home,
													Login.login,
													FindOwners.findOwners,
													FindOwner.findOwner,
													BettyDavis.bettyDavis,
													AddVisitBasil.addVisitBasil)
	
	val scnNegative = scenario("FreeVisitMessageNegative").exec(Home.home,
													Login.login,
													FindOwners.findOwners,
													FindOwner.findOwner,
													GeorgeFranklin.georgeFranklin,
													AddVisitLeo.addVisitLeo)
setUp(
		scnPositive.inject(rampUsers(100000) during (5 seconds)),
		scnNegative.inject(rampUsers(100000) during (5 seconds))
		).protocols(httpProtocol)
		 .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}