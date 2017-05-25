package bookserver

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object ConsultaLivrosCenario {

  val consulta = scenario("Consulta do endpoint de administração")
      .exec(
        http("Total de livros")
          .get("/api/v2/admin/total_livros")
          .header("Authorization", "Bearer 99b29934-f534-4a74-bb3c-fd24d536398d"))


}
