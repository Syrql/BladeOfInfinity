package fr.syrql.blade.command;

import fr.syrql.blade.MainBlade;
import fr.syrql.blade.util.ItemBuilder;
import fr.syrql.blade.util.command.ACommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class BladeCommand extends ACommand {

    private final MainBlade mainBlade;

    public BladeCommand(MainBlade mainBlade) {
        super(mainBlade, "bladeofinfinity", "permission.blade", true);
        this.mainBlade = mainBlade;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if (args.length == 0) {
            sender.sendMessage("/bladeofinfinity <player>");
            return true;
        } else if (args.length == 1) {

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage(mainBlade.getConfigManager().getString("player-not-found"));
                return true;
            }

            Map<Enchantment, Integer> enchantment = new HashMap<>();

            for (String effects : mainBlade.getConfig().getStringList("enchant.type")) {
                List<String> stringList = new ArrayList<>(Arrays.asList(effects.split(" ")));
                enchantment.put(Enchantment.getByName(stringList.get(0)), Integer.parseInt(stringList.get(1)));
            }

            System.out.println(enchantment);
            ItemStack itemStack = new ItemBuilder(Material
                    .getMaterial(mainBlade.getConfigManager().getString("material")))
                    .setName(mainBlade.getConfigManager().getString("name"))
                    .setLore(mainBlade.getConfigManager().getStringList("lore"))
                    .addEnchantments(enchantment)
                    .toItemStack();
            target.getInventory().addItem(itemStack);
            target.sendMessage(mainBlade.getConfigManager().getString("message-blade")
                    .replace("<sender>", sender.getName()));
        }
        return true;
    }
}
