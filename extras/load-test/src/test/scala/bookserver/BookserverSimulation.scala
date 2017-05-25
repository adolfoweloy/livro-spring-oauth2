package bookserver

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class BookserverSimulation extends Simulation {

  val httpConf = http.baseURL("http://localhost:8081")

  val bookserverScenarios = List(
    AuthorizationServer.token.inject(rampUsers(1000) over(5 seconds)))

  setUp(bookserverScenarios).protocols(httpConf)

}
