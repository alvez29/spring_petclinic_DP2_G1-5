package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class CreateTournamentsDiagnosis extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36 OPR/68.0.3618.63")

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
		.pause(5)
	}

	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/login")
			.headers(headers_2)))
		.pause(14)
	}

	object Loged {
		val loged = exec(http("Loged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "dc2aecd2-1d0d-4eef-badb-ddf0c6216c09"))
		.pause(9)
	}

	object FindTournaments {
		val findTournaments = exec(http("FindTournaments")
			.get("/tournaments")
			.headers(headers_0))
		.pause(13)
	}

	object NewRace {
		val newRace = exec(http("NewRace")
			.get("/race/new")
			.headers(headers_0))
		.pause(48)
	}

	object AddRace {
		val addRace = exec(http("AddRace")
			.post("/race/new")
			.headers(headers_3)
			.formParam("name", "Carrera de Rendimiento")
			.formParam("date", "2020/12/10")
			.formParam("capacity", "200")
			.formParam("breedRestriction", "Beagle")
			.formParam("rewardMoney", "200.00")
			.formParam("canodrome", "Rendimiento")
			.formParam("petId", "")
			.formParam("_csrf", "13b83c03-287a-4f7c-b077-411641db593e"))
		.pause(11)
	}

	object NewBeauty {
		val newBeauty = exec(http("NewBeauty")
			.get("/beauty/new")
			.headers(headers_0))
		.pause(32)
	}

	object AddBeauty {
		val addBeauty = exec(http("AddBeauty")
			.post("/beauty/new")
			.headers(headers_3)
			.formParam("name", "Belleza de Rendimiento")
			.formParam("date", "2020/12/17")
			.formParam("capacity", "100")
			.formParam("breedRestriction", "Beagle")
			.formParam("rewardMoney", "200.00")
			.formParam("place", "Belleza")
			.formParam("petId", "")
			.formParam("_csrf", "13b83c03-287a-4f7c-b077-411641db593e"))
		.pause(11)
	}

	object NewHability {
		val newHability = exec(http("NewHability")
			.get("/hability/new")
			.headers(headers_0))
		.pause(36)
	}

	object AddHability {
		val addHability = exec(http("AddHability")
			.post("/hability/new")
			.headers(headers_3)
			.formParam("name", "Habilidad de Rendimiento")
			.formParam("date", "2021/02/12")
			.formParam("capacity", "200")
			.formParam("breedRestriction", "Beagle")
			.formParam("rewardMoney", "200.00")
			.formParam("circuit", "Habilidad")
			.formParam("habilityId", "")
			.formParam("_csrf", "13b83c03-287a-4f7c-b077-411641db593e"))
		.pause(7)
	}

	val raceScn = scenario("CreateRace").exec(Home.home,
										  Login.login,
										  Loged.loged,
										  FindTournaments.findTournaments,
										  NewRace.newRace,
										  AddRace.addRace)

	val beautyScn = scenario("CreateBeauty").exec(Home.home,
											Login.login,
										  	Loged.loged,
										  	FindTournaments.findTournaments,
										  	NewBeauty.newBeauty,
										  	AddBeauty.addBeauty)
	
	val habilityScn = scenario("CreateHability").exec(Home.home,
										  	  Login.login,
										  	  Loged.loged,
										  	  FindTournaments.findTournaments,
										   	  NewHability.newHability,
										  	  AddHability.addHability)



	setUp(
		raceScn.inject(rampUsers(75000) during (10 seconds)),
		beautyScn.inject(rampUsers(75000) during (10 seconds)),
		habilityScn.inject(rampUsers(75000) during (10 seconds))
		).protocols(httpProtocol)
		.assertions(
			global.responseTime.max.lt(5000),
			global.responseTime.mean.lt(5000),
			global.successfulRequests.percent.gt(95)
		)
}