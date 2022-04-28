package gravittestwork

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin

class GravitTestWorkConfig(val plugin: JavaPlugin) {
    private val fileConfig: FileConfiguration = plugin.config

    companion object {
        lateinit var url: String
    }

    private fun setDefaultValues() {
        fileConfig.addDefault("url", "http://127.0.0.1:4000")
        save()
    }

    private fun save() {
        fileConfig.options().copyDefaults(true)
        plugin.saveConfig()
    }

    init {
        setDefaultValues()
        url = plugin.config.getString("url").toString()
    }
}