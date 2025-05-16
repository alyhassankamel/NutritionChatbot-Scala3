name := "chatbot"

version := "0.1"

scalaVersion := "3.6.4"

// Disable forking to ensure console input works
fork := false

// Enable deprecation warnings
scalacOptions += "-deprecation"

// Ensure resources are included in the classpath
Compile / unmanagedResourceDirectories += baseDirectory.value / "src" / "main" / "resources"