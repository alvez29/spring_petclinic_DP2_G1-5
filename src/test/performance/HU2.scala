package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class AddPetToTournamentDiagnosis extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36 OPR/68.0.3618.118")

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
		.pause(6)
	}

	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/login")
			.headers(headers_2)))
		.pause(16)
	}

	object Loged {
		val loged = exec(http("Loged")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "8b3475de-842b-4b41-b8a9-84bfba324600"))
		.pause(17)
	}

	object Tournaments {
		val tournaments = exec(http("Tournaments")
			.get("/tournaments")
			.headers(headers_0))
		.pause(9)
	}

	object AddNewRace {
		val addnewrace = exec(http("AddNewRace")
			.get("/race/new")
			.headers(headers_0))
		.pause(35)
	}

	object AddRace {
		val addrace = exec(http("AddRace")
			.post("/race/new")
			.headers(headers_3)
			.formParam("name", "Carrera de rendimiento")
			.formParam("date", "2020/05/27")
			.formParam("capacity", "200")
			.formParam("breedRestriction", "Greyhound")
			.formParam("rewardMoney", "200.00")
			.formParam("canodrome", "Sevilla")
			.formParam("petId", "")
			.formParam("_csrf", "bb39cc34-189f-44ad-bcad-f5acc96afbda"))
		.pause(9)
	}

	object FindOwners {
		val findowners = exec(http("FindOwners")
			.get("/owners/find")
			.headers(headers_0))
		.pause(6)
	}

	object FindAllOwners {
		val findallowners = exec(http("FindAllOwners")
			.get("/owners?lastName=")
			.headers(headers_0))
		.pause(6)
	}

	object FindEduardo {
		val findeduardo = exec(http("FindEduardo")
			.get("/owners/3")
			.headers(headers_0))
		.pause(8)
	}

	object AddVisit {
		val addvisit = exec(http("AddVisit")
			.get("/owners/3/pets/4/visits/new")
			.headers(headers_0))
		.pause(23)
	}

	object VisitAdded {
		val visitadded = exec(http("VisitAdded")
			.post("/owners/3/pets/4/visits/new")
			.headers(headers_3)
			.formParam("date", "2020/05/20")
			.formParam("description", "Esta gucci")
			.formParam("clinic", "Canin Vet")
			.formParam("competitionCheck", "PASSED")
			.formParam("petId", "4")
			.formParam("_csrf", "bb39cc34-189f-44ad-bcad-f5acc96afbda"))
		.pause(15)
	}

	object FindTournament {
		val findtournament = exec(http("request_14")
			.get("/tournaments/15")
			.headers(headers_0))
		.pause(10)
	}

	object AddDog {
		val adddog = exec(http("AddDog")
			.get("/pet/tournament/15")
			.headers(headers_0))
		.pause(7)
	}

	object DogAdded {
		val dogadded = exec(http("DogAdded")
			.get("/tournaments/15/addpet/4")
			.headers(headers_0))
		.pause(5)
	}

	val dogScn = scenario("AddDog").exec(Home.home,
										 Login.login,
										 Loged.loged,
										 Tournaments.tournaments,
										 AddNewRace.addnewrace,
										 AddRace.addrace,
										 FindOwners.findowners,
										 FindAllOwners.findallowners,
										 FindEduardo.findeduardo,
										 AddVisit.addvisit,
										 VisitAdded.visitadded,
										 Tournaments.tournaments,
										 FindTournament.findtournament,
										 AddDog.adddog,
										 DogAdded.dogadded)

	setUp(
		dogScn.inject(rampUsers(225000) during (10 seconds))
		).protocols(httpProtocol)
		.assertions(
			global.responseTime.max.lt(5000),
			global.responseTime.mean.lt(5000),
			global.successfulRequests.percent.gt(95)
		)
}