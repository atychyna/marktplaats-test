import AssemblyKeys._

organization := "org.atychyna"

name := "marktplaats-test"

version := "1.0-SNAPSHOT"

scalaVersion := "2.10.3"

sbtVersion := "0.13.0"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
    "org.scalatra" %% "scalatra" % "2.2.1",
    "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" artifacts (Artifact("javax.servlet", "jar", "jar")),
    "org.eclipse.jetty" % "jetty-webapp" % "8.1.8.v20121106"
)

assemblySettings

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
  {
    case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.first
    case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
    case "application.conf" => MergeStrategy.concat
    case "unwanted.txt"     => MergeStrategy.discard
    case x => old(x)
  }
}

mainClass in assembly := Some("org.atychyna.marktplaats.JettyLauncher")