import java.nio.file.Path

import io.gatling.commons.util.PathHelper._

object IDEPathHelper {

  val gatlingConfUrl: Path = getClass.getClassLoader.getResource("gatling.conf").toURI
  val projectRootDir = gatlingConfUrl.ancestor(3)

  val mavenSourcesDirectory = projectRootDir / "src" / "test" / "scala"
  val mavenResourcesDirectory = projectRootDir / "src" / "test" / "resources"
  val mavenTargetDirectory = projectRootDir / "target"
  val mavenBinariesDirectory = mavenTargetDirectory / "classes"

  val dataDirectory = mavenResourcesDirectory
  val bodiesDirectory = mavenResourcesDirectory

  val recorderOutputDirectory = mavenSourcesDirectory
  val resultsDirectory = mavenTargetDirectory / "gatling"

  val recorderConfigFile = mavenResourcesDirectory / "recorder.conf"

}
