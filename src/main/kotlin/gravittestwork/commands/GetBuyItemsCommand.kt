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
        val payments = service.getUserPayments(player.name)

        if (payments.isNotEmpty()) {
            val paymentsGived = service.giveItems(player, payments)
            player.sendMessage("Вам было выдано ${paymentsGived.size} заказов")
        } else {
            player.sendMessage("У вас нет заказов, ожидающих выдачи...")
        }

    }
}