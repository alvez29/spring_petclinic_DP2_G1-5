package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class HU11 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.js""", """.*.css""", """.*.png""", """.*.ico"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_0 = Map("Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Origin" -> "http://www.dp2.com",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_10 = Map("Accept" -> "image/webp,*/*")

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
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(16)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "admin1")
			.formParam("password", "4dm1n")
			.formParam("_csrf", "${stoken}"))
		.pause(9) 
	}

	object FindOwners {
		val findOwners = exec(http("FindOwners")
			.get("/owners/find")
			.headers(headers_0))
		.pause(8)
	}

	object OwnerList {
		val ownerList = exec(http("OwnerList")
			.get("/owners?lastName=")
			.headers(headers_0))
		.pause(13)
	}

	object ShowOwner {
		val showOwner = exec(http("ShowOwner")
			.get("/owners/10")
			.headers(headers_0))
		.pause(30)
	}

	object AddVisit {
		val addVisit = exec(http("AddVisit")
			.get("/owners/10/pets/12/visits/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(31)
		.exec(http("VisitAdded")
			.post("/owners/10/pets/12/visits/new")
			.headers(headers_2)
			.formParam("date", "2020/05/22")
			.formParam("description", "This is a test")
			.formParam("clinic", "Canin Vet")
			.formParam("competitionCheck", "PASSED")
			.formParam("petId", "12")
			.formParam("_csrf", "${stoken}"))
		.pause(9)
	}



	object AddVisitNeg {
		val addVisitNeg = exec(http("AddVisitNeg")
			.get("/owners/10/pets/12/visits/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(27)
		.exec(http("NotAddedVisit")
			.post("/owners/10/pets/12/visits/new")
			.headers(headers_2)
			.formParam("date", "2020/05/22")
			.formParam("description", "This is a test")
			.formParam("clinic", "This is an invented clinic")
			.formParam("competitionCheck", "PASSED")
			.formParam("petId", "12")
			.formParam("_csrf", "${stoken}")
			.resources(http("request_10")
			.get("/login")
			.headers(headers_10),
            http("request_11")
			.get("/owners/10/pets/12/visits/new")
			.headers(headers_0)))
		.pause(47)
	}


	object ShowOwnerWithoutVisit {
		val showOwnerWithoutVisit = exec(http("ShowOwnerWithoutVisit")
			.get("/owners/10/")
			.headers(headers_0))
		.pause(24)
	}


	val scnPositive = scenario("scnPositive").exec(
		Home.home,
		Login.login,
		FindOwners.findOwners,
		OwnerList.ownerList,
		ShowOwner.showOwner,
		AddVisit.addVisit,
	)

	val scnNegative = scenario("scnNegative").exec(
		Home.home,
		Login.login,
		FindOwners.findOwners,
		OwnerList.ownerList,
		ShowOwner.showOwner,
		AddVisitNeg.addVisitNeg,
		ShowOwnerWithoutVisit.showOwnerWithoutVisit
	)
		
	setUp(
		scnPositive.inject(rampUsers(140000) during (5 seconds)),
		scnNegative.inject(rampUsers(140000) during (5 seconds))
		).protocols(httpProtocol)
		 .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )}