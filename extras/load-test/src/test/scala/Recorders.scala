import io.gatling.recorder.GatlingRecorder
import io.gatling.recorder.config.RecorderPropertiesBuilder

object Recorders extends App {

  val props = new RecorderPropertiesBuilder
  props.simulationOutputFolder(IDEPathHelper.recorderOutputDirectory.toString)
  props.simulationPackage("bookserver")
  props.bodiesFolder(IDEPathHelper.bodiesDirectory.toString)

  GatlingRecorder.fromMap(props.build, Some(IDEPathHelper.recorderConfigFile))
}