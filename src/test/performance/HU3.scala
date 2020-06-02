package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class DoubleResultDiagnosis extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36 OPR/68.0.3618.129")

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
		.pause(8)
	}

	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/login")
			.headers(headers_2)))
		.pause(8)
		.exec(http("Loged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "3bc8866a-bc32-4ae4-a499-44e3e87bdd21"))
		.pause(9)
	}

	object ListTournaments {
		val listTournaments = exec(http("List Tournaments")
			.get("/tournaments")
			.headers(headers_0))
		.pause(34)
	}

	object ShowTournament {
		val showTournament = exec(http("ShowTournament")
			.get("/tournaments/3")
			.headers(headers_0))
		.pause(19)
	}

	object AddResult {
		val result = exec(http("AddResult")
			.get("/tournament/beauty/3/pet/2/add_result")
			.headers(headers_0))
		.pause(25)
	}

	object ResultAdded {
		val resultAdded = exec(http("Added")
			.post("/tournament/beauty/3/pet/2/add_result")
			.headers(headers_3)
			.formParam("haircut", "7")
			.formParam("haircutdif", "7")
			.formParam("technique", "7")
			.formParam("posture", "7")
			.formParam("_csrf", "cd79d615-809a-4ac9-96a3-89fa1ba0d057"))
		.pause(33) 
	}

	


	val firstResultScn = scenario("AddResult1").exec(Home.home,
												 Login.login,
												 ListTournaments.listTournaments,
												 ShowTournament.showTournament,
												 AddResult.result,
												 ResultAdded.resultAdded)

	
	val secondResultScn = scenario("AddResult2").exec(Home.home,
												 Login.login,
												 ListTournaments.listTournaments,
												 ShowTournament.showTournament,
												 AddResult.result,
												 ResultAdded.resultAdded,
												 AddResult.result,
												 ResultAdded.resultAdded)
												 
	setUp(
		firstResultScn.inject(rampUsers(112500) during (10 seconds)),
		secondResultScn.inject(rampUsers(112500) during (10 seconds))
		).protocols(httpProtocol)
		.assertions(
			global.responseTime.max.lt(5000),
			global.responseTime.mean.lt(5000),
			global.successfulRequests.percent.gt(95)
		)
}