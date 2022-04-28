package gravittestwork.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Default
import gravittestwork.GravitTestWorkService
import org.bukkit.entity.Player

@CommandAlias("getbuyitems")
class GetBuyItemsCommand : BaseCommand() {
    private val service = GravitTestWorkService()

    @Default
    fun main(player: Player) {
        val amount = service.getUser(player.name)
        player.sendMessage("Ваш баланс ${amount}, выдаём покупки")
        service.giveItems(player, amount)
    }
}