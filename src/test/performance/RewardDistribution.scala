package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RewardDistribution extends Simulation {

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

	object CarlosEsteban {
		val carlosEsteban = exec(http("CarlosEsteban")
			.get("/owners/10")
			.headers(headers_0))
		.pause(20)
	}

	object JeffBlack {
		val jeffBlack = exec(http("JeffBlack")
			.get("/owners/7")
			.headers(headers_0))
		.pause(20)
	}

	object AddVisitLucky {
		val addVisitLucky = exec(http("AddVisitLucky")
			.get("/owners/7/pets/9/visits/new")
			.headers(headers_0))
		.pause(38)
	}

	object AddVisitPongo {
		val addVisitPongo = exec(http("AddVisitPongo")
			.get("/owners/10/pets/14/visits/new")
			.headers(headers_0))
		.pause(19)
	}

	val scnPositive = scenario("RewardDistributionPositive").exec(Home.home,
													Login.login,
													FindOwners.findOwners,
													FindOwner.findOwner,
													JeffBlack.jeffBlack,
													AddVisitLucky.addVisitLucky)
	
	val scnNegative = scenario("RewardDistributionNegative").exec(Home.home,
													Login.login,
													FindOwners.findOwners,
													FindOwner.findOwner,
													CarlosEsteban.carlosEsteban,
													AddVisitPongo.addVisitPongo)
setUp(
		scnPositive.inject(rampUsers(5000) during (100 seconds)),
		scnNegative.inject(rampUsers(5000) during (100 seconds))
		).protocols(httpProtocol)
		 .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}