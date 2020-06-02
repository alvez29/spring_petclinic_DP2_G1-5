package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class PendingDiagnosis extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36 OPR/68.0.3618.125")

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

	object Loged {
		val loged = exec(http("Loged")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/login")
			.headers(headers_2)))
		.pause(5)
		.exec(http("request_3")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "795f18cc-ce90-40a2-ba95-1ade1ead5330"))
		.pause(6)
	}

	object VisitsAdded {
		val visitsAdded = exec(http("Visits added")
			.get("/owners/find")
			.headers(headers_0))
		.pause(1)
		.exec(http("request_6")
			.get("/owners?lastName=")
			.headers(headers_0))
		.pause(9)
		.exec(http("request_7")
			.get("/owners/9")
			.headers(headers_0))
		.pause(3)
		.exec(http("request_8")
			.get("/owners/9/pets/11/visits/new")
			.headers(headers_0))
		.pause(11)
		.exec(http("request_9")
			.post("/owners/9/pets/11/visits/new")
			.headers(headers_3)
			.formParam("date", "2020/05/25")
			.formParam("description", "dwaddaw")
			.formParam("clinic", "Canin Vet")
			.formParam("competitionCheck", "PASSED")
			.formParam("petId", "11")
			.formParam("_csrf", "20f37d89-ffee-4e4e-93d6-9c135922c8a5"))
		.pause(4)
		.exec(http("request_10")
			.get("/owners/find")
			.headers(headers_0)
			.resources(http("request_11")
			.get("/owners?lastName=")
			.headers(headers_0)))
		.pause(1)
		.exec(http("request_12")
			.get("/owners/3")
			.headers(headers_0))
		.pause(4)
		.exec(http("request_13")
			.get("/owners/3/pets/3/visits/new")
			.headers(headers_0))
		.pause(5)
		.exec(http("request_14")
			.post("/owners/3/pets/3/visits/new")
			.headers(headers_3)
			.formParam("date", "2020/05/25")
			.formParam("description", "dwaddaw")
			.formParam("clinic", "Canin Vet")
			.formParam("competitionCheck", "PASSED")
			.formParam("petId", "3")
			.formParam("_csrf", "20f37d89-ffee-4e4e-93d6-9c135922c8a5"))
		.pause(7)
	}

	object NewTournament {
		val newTournament = exec(http("New Tournament")
			.get("/tournaments")
			.headers(headers_0))
		.pause(1)
		.exec(http("request_16")
			.get("/race/new")
			.headers(headers_0))
		.pause(33)
		.exec(http("request_17")
			.post("/race/new")
			.headers(headers_3)
			.formParam("name", "Carrera de rendimiento definitiva")
			.formParam("date", "2020/06/04")
			.formParam("capacity", "200")
			.formParam("breedRestriction", "Greyhound")
			.formParam("rewardMoney", "10000.00")
			.formParam("canodrome", "Sevilla")
			.formParam("petId", "")
			.formParam("_csrf", "20f37d89-ffee-4e4e-93d6-9c135922c8a5"))
		.pause(12)
	}

	object DogAdded {
		val dogAdded = exec(http("Dogs added")
			.get("/tournaments/17")
			.headers(headers_0))
		.pause(2)
		.exec(http("request_19")
			.get("/pet/tournament/17")
			.headers(headers_0))
		.pause(4)
		.exec(http("request_20")
			.get("/tournaments/17/addpet/3")
			.headers(headers_0))
		.pause(1)
		.exec(http("request_21")
			.get("/pet/tournament/17")
			.headers(headers_0))
		.pause(1)
		.exec(http("request_22")
			.get("/tournaments/17/addpet/11")
			.headers(headers_0))
		.pause(9)
	}

	object SponsorAdded {
		val sponsorAdded = exec(http("Sponsor added")
			.get("/tournaments/17/sponsors/add")
			.headers(headers_0))
		.pause(15)
		.exec(http("request_24")
			.post("/tournaments/17/sponsors/add")
			.headers(headers_3)
			.formParam("name", "Asistente-de-google")
			.formParam("money", "10000.00")
			.formParam("url", "https://www.youtube.com")
			.formParam("sponsorId", "")
			.formParam("_csrf", "20f37d89-ffee-4e4e-93d6-9c135922c8a5"))
		.pause(7)
	}

	object JudgeAdded {
		val judgeAdded = exec(http("Judge added")
			.get("/judge/tournament/17")
			.headers(headers_0)
			.resources(http("request_26")
			.get("/tournaments/17/addjudge/1")
			.headers(headers_0)))
		.pause(6)
	}

	object SetPending {
		val setPending = exec(http("Pending")
			.get("/tournaments/race/17/edit")
			.headers(headers_0))
		.pause(2)
		.exec(http("request_28")
			.post("/tournaments/race/17/edit")
			.headers(headers_3)
			.formParam("name", "Carrera de rendimiento definitiva")
			.formParam("date", "2020/06/03")
			.formParam("capacity", "200")
			.formParam("breedRestriction", "Greyhound")
			.formParam("rewardMoney", "10000.0")
			.formParam("canodrome", "Sevilla")
			.formParam("status", "PENDING")
			.formParam("petId", "17")
			.formParam("_csrf", "20f37d89-ffee-4e4e-93d6-9c135922c8a5"))
		.pause(6)
	}

	val racePending = scenario("NewRace").exec(Home.home,
													  Loged.loged,
													  VisitsAdded.visitsAdded,
													  NewTournament.newTournament,
													  DogAdded.dogAdded,
													  SponsorAdded.sponsorAdded,
													  JudgeAdded.judgeAdded,
													  SetPending.setPending)



	setUp(
		racePending.inject(rampUsers(225000) during (10 seconds))
		).protocols(httpProtocol)
		.assertions(
			global.responseTime.max.lt(5000),
			global.responseTime.mean.lt(5000),
			global.successfulRequests.percent.gt(95)
		)
}