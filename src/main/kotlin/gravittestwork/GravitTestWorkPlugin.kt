package gravittestwork

import co.aikar.commands.BukkitCommandManager
import gravittestwork.commands.GetBuyItemsCommand
import org.bukkit.plugin.java.JavaPlugin
import java.util.Locale

@Suppress("unused")
class GravitTestWorkPlugin : JavaPlugin() {
    override fun onEnable() {
        val commandManager = BukkitCommandManager(this)
        commandManager.usePerIssuerLocale(false, false);
        commandManager.locales.defaultLocale = Locale("ru");

        GravitTestWorkConfig(this)
        commandManager.registerCommand(GetBuyItemsCommand())
    }
}