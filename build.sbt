name := """micro-posts2"""
organization := "com.example"

version := "1.0-SNAPSHOT"
import com.typesafe.config.{ Config, ConfigFactory }
import scala.collection.JavaConverters._

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.8"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies += "com.github.t3hnar" %% "scala-bcrypt" % "4.3.0"
libraryDependencies ++= Seq(
  "org.scalikejdbc"        %% "scalikejdbc"                     % "3.5.0",
  "org.scalikejdbc"        %% "scalikejdbc-config"              % "3.5.0",
  "org.scalikejdbc"        %% "scalikejdbc-test"                % "3.5.0" % Test,
  "org.scalikejdbc"        %% "scalikejdbc-syntax-support-macro"% "3.5.0",
  "org.skinny-framework"   %% "skinny-orm"                      % "3.1.0",
  "org.scalikejdbc"        %% "scalikejdbc-play-initializer"    % "2.8.0-scalikejdbc-3.5",
  "ch.qos.logback"         % "logback-classic"                  % "1.2.3",
  "mysql"                  % "mysql-connector-java"             % "8.0.22",
  "com.adrianhurt"         %% "play-bootstrap"                  % "1.6.1-P28-B4",
  "org.flywaydb"           %% "flyway-play"                  % "7.12.0",
  "org.mariadb.jdbc" % "mariadb-java-client" % "1.4.4",
  "org.hsqldb" % "hsqldb" % "2.5.0",
)

// silhouette library
libraryDependencies += "io.github.honeycomb-cheesecake" %% "play-silhouette" % "7.0.7"
libraryDependencies += "io.github.honeycomb-cheesecake" %% "play-silhouette-cas" % "7.0.7"
libraryDependencies += "io.github.honeycomb-cheesecake" %% "play-silhouette-crypto-jca" % "7.0.7"
libraryDependencies += "io.github.honeycomb-cheesecake" %% "play-silhouette-password-argon2" % "7.0.7"
libraryDependencies += "io.github.honeycomb-cheesecake" %% "play-silhouette-password-bcrypt" % "7.0.7"
libraryDependencies += "io.github.honeycomb-cheesecake" %% "play-silhouette-persistence" % "7.0.7"
libraryDependencies += "io.github.honeycomb-cheesecake" %% "play-silhouette-totp" % "7.0.7"
libraryDependencies += "io.github.honeycomb-cheesecake" %% "play-silhouette-testkit" % "7.0.7" % Test



lazy val envConfig = settingKey[Config]("env-config")

envConfig := {
  val env = sys.props.getOrElse("env", "dev")
  ConfigFactory.parseFile(file("env") / (env + ".conf"))
}

enablePlugins(FlywayPlugin)

flywayLocations := envConfig.value.getStringList("flywayLocations").asScala
flywayDriver := envConfig.value.getString("jdbcDriver")
flywayUrl := envConfig.value.getString("jdbcUrl")
flywayUser := envConfig.value.getString("jdbcUserName")
flywayPassword := envConfig.value.getString("jdbcPassword")


TwirlKeys.templateImports ++= Seq("forms._")

resolvers += "Atlassian Maven" at "https://maven.atlassian.com/content/repositories/atlassian-public/"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
