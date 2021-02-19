package fr.syrql.blade.listener;

import fr.syrql.blade.MainBlade;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BladeListener implements Listener {

    private final MainBlade mainBlade;
    private HashMap<String, List<ItemStack>> playersName = new HashMap<>();

    public BladeListener(MainBlade mainBlade) {
        this.mainBlade = mainBlade;
    }


    @EventHandler
    public void onDeath(final PlayerDeathEvent event) {
        Player player = event.getEntity();

        List<ItemStack> itemStacks = new ArrayList<>();

        for (int i = 0; i < player.getInventory().getSize(); i++) {
            final ItemStack itemStack = player.getInventory().getItem(i);

            if (itemStack != null &&
                    itemStack.getType() == Material
                            .getMaterial(mainBlade.getConfigManager().getString("material"))
                    && itemStack.getItemMeta().getDisplayName()
                    .equalsIgnoreCase(mainBlade.getConfigManager().getString("name"))) {
                itemStacks.add(itemStack);
                event.getDrops().remove(itemStack);
            }
        }
        playersName.put(player.getName(), itemStacks);
    }

    @EventHandler
    public void onRespawn(final PlayerRespawnEvent event) {

        final Player player = event.getPlayer();

        if (playersName.containsKey(player.getName())) {
            playersName.get(player.getName()).forEach(itemStack -> {
                player.getInventory().addItem(itemStack);
            });
        }
        player.sendMessage(mainBlade.getConfigManager().getString("message-back"));
        playersName.remove(player.getName());
    }
}
