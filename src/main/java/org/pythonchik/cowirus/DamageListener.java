package org.pythonchik.cowirus;


import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;



public class DamageListener implements Listener {
    Plugin plugin = Bukkit.getPluginManager().getPlugin("CoWirus");
    CoWirus plugin2 = (CoWirus) Bukkit.getPluginManager().getPlugin("CoWirus");

    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamagePlayer (EntityDamageByEntityEvent event){
        if (event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
            if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
                if (event.getDamager().hasMetadata("when_infected") && !event.getEntity().hasMetadata("when_infected") && !event.getEntity().hasMetadata("concured")) {
                    try {
                        if (((Player) event.getEntity()).getInventory().getHelmet().getType().equals(Material.CARVED_PUMPKIN) && ((Player) event.getEntity()).getInventory().getHelmet().getItemMeta().getDisplayName().equals("Головной комплект химзащиты")
                                && ((Player) event.getEntity()).getInventory().getChestplate().getType().equals(Material.IRON_CHESTPLATE) && ((Player) event.getEntity()).getInventory().getChestplate().getItemMeta().getDisplayName().equals("Верх скафандра")
                                && ((Player) event.getEntity()).getInventory().getLeggings().getType().equals(Material.IRON_LEGGINGS) && ((Player) event.getEntity()).getInventory().getLeggings().getItemMeta().getDisplayName().equals("Низ скафандра")
                                && ((Player) event.getEntity()).getInventory().getBoots().getType().equals(Material.IRON_BOOTS) && ((Player) event.getEntity()).getInventory().getBoots().getItemMeta().getDisplayName().equals("Ботинки скафандра")
                        ) {
                            if (Math.random() <= 0.05) { //5% chance
                                event.getEntity().setMetadata("when_infected", new FixedMetadataValue(plugin, ((Player) event.getEntity()).getPlayer().getStatistic(Statistic.PLAY_ONE_MINUTE)));
                                plugin2.updInfected(event.getEntity().getName(), ((Player) event.getEntity()).getPlayer().getStatistic(Statistic.PLAY_ONE_MINUTE));
                            }
                        } else {

                            if (Math.random() >= 0.5) { //50% chance
                                event.getEntity().setMetadata("when_infected", new FixedMetadataValue(plugin, ((Player) event.getEntity()).getPlayer().getStatistic(Statistic.PLAY_ONE_MINUTE)));
                                plugin2.updInfected(event.getEntity().getName(), ((Player) event.getEntity()).getPlayer().getStatistic(Statistic.PLAY_ONE_MINUTE));
                            }
                        }
                    } catch (Exception ignored) {

                        if (Math.random() >= 0.5) { //50% chance
                            event.getEntity().setMetadata("when_infected", new FixedMetadataValue(plugin, ((Player) event.getEntity()).getPlayer().getStatistic(Statistic.PLAY_ONE_MINUTE)));
                            plugin2.updInfected(event.getEntity().getName(), ((Player) event.getEntity()).getPlayer().getStatistic(Statistic.PLAY_ONE_MINUTE));
                        }
                    }
                }
            }
        } else if (event.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)){
            //vaccine
        }
    }
}
