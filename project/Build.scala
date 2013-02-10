import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "teamlinkup"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm
  )


//  resolvers += "repo.codahale.com" at "http://repo.codahale.com"
//  libraryDependencies += "com.codahale" % "jerkson_2.9.1" % "0.5.0"


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
