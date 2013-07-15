import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "PsyDMP"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    "mysql" % "mysql-connector-java" % "5.1.25",
    "org.json"%"org.json"%"chargebee-1.0",
    javaCore,
    javaJdbc,
    javaEbean
    
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here  
    resolvers += (
    "Local Maven Repository" at "file://C:/Users/Zhefan/.m2/repository"
),
 resolvers += "Scala-Tools Maven2 Snapshots Repository" at "http://scala-tools.org/repo-snapshots"
 ) 

}
