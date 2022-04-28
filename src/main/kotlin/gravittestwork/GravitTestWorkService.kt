package gravittestwork

import gravittestwork.dto.Payment
import gravittestwork.utils.Requester
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import kotlin.random.Random

class GravitTestWorkService {
    private val requester = Requester()
    private val config = GravitTestWorkConfig
    private val rewards: Map<Int, (Player) -> Unit> = mapOf(
        3 to ::giveStick,
        4 to ::giveCoal,
        6 to ::giveIron,
        12 to ::damagePlayer,
        15 to ::giveDiamond,
        20 to ::giveBook,
        70 to ::giveSkull,
        80 to ::giveArmor,
        100 to ::invisible,
        150 to ::armorCurse,
        300 to ::giveStar
    )

    private fun rand(amount: Int): Float {
        val min = amount - 3
        val max = amount + 3

        return (min..max).random().toFloat()
    }

    fun getUserPayments(username: String): Array<Payment> {
        return requester.get("${config.url}/payments/byUsername/${username}").getOrThrow()
    }

    fun giveItems(player: Player, payments: Array<Payment>): Array<Payment> {
        for (payment in payments) {
            var amountMutable = payment.amount

            loop@ while (true) {
                for (action in rewards.entries.shuffled()) {
                    val random = rand(action.key)

                    if (random > amountMutable)
                        if (action.key == rewards.keys.first())
                            break@loop;
                        else
                            continue
                    else {
                        amountMutable -= random
                        action.value.invoke(player)
                        continue
                    }
                }
            }
        }

        return requester.delete("${config.url}/payments/${payments.map { it.id }.joinToString(",")}").getOrThrow()
    }

    fun giveStick(player: Player) {
        val item = ItemStack(Material.STICK, 5)
        player.inventory.addItem(item)
    }

    fun giveCoal(player: Player) {
        val item = ItemStack(Material.COAL, 3)
        player.inventory.addItem(item)
    }

    fun giveIron(player: Player) {
        val item = ItemStack(Material.IRON_INGOT, 2)
        player.inventory.addItem(item)
    }

    fun damagePlayer(player: Player) {
        player.damage(1.0)
    }

    fun giveDiamond(player: Player) {
        val item = ItemStack(Material.DIAMOND)
        player.inventory.addItem(item)
    }

    fun giveBook(player: Player) {
        val item = ItemStack(Material.BOOK)
        player.inventory.addItem(item)
    }

    fun giveSkull(player: Player) {
        val item = ItemStack(Material.SKULL_ITEM, 1, 1)
        player.inventory.addItem(item)
    }

    fun giveArmor(player: Player) {
        val item = ItemStack(Material.DIAMOND_CHESTPLATE)
        item.addEnchantment(Enchantment.PROTECTION_FIRE, 2);

        player.inventory.addItem(item)
    }

    fun invisible(player: Player) {
        val potion = PotionEffect(PotionEffectType.INVISIBILITY, (60 * 60) * 20, 1)
        player.addPotionEffect(potion)
    }

    fun armorCurse(player: Player) {
        val items = player.inventory.armorContents.filterNotNull()

        if (items.isNotEmpty()) {
            val item = items[Random.nextInt(items.size)]
            item.addEnchantment(Enchantment.BINDING_CURSE, 1);
        } else {
            player.health = 0.0
        }
    }

    fun giveStar(player: Player) {
        val item = ItemStack(Material.NETHER_STAR)
        player.inventory.addItem(item)
    }
}