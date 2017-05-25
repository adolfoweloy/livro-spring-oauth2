package bookserver

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object AuthorizationServer {

  var token = scenario("Authorization Server")
      .exec(http("Obter token via resource owner password credentials")
          .post("/oauth/token")
          .header("Content-type", "application/x-www-form-urlencoded")
          .header("Authorization", "Basic Ym9va3NlcnZlci1jbGllbnQ6MTIzNDU2")
          .formParam("grant_type", "password")
          .formParam("username", "jujuba@mailinator.com")
          .formParam("password", "123")
          .formParam("scope", "read"))
}
