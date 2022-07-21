version = "0.0.1"

project.extra["PluginName"] = "Discord collection log poster"
project.extra["PluginDescription"] = "Posts new collection log entries to a discord webhook"


tasks {
    jar {
        manifest {
            attributes(mapOf(
                    "Plugin-Version" to project.version,
                    "Plugin-Id" to nameToId(project.extra["PluginName"] as String),
                    "Plugin-Provider" to project.extra["PluginProvider"],
                    "Plugin-Description" to project.extra["PluginDescription"],
                    "Plugin-License" to project.extra["PluginLicense"]
            ))
        }
    }

}

