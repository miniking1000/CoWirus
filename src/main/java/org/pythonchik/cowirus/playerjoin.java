package org.pythonchik.cowirus;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class playerjoin implements Listener {
    private FileConfiguration infected;
    Plugin plugin = Bukkit.getPluginManager().getPlugin("CoWirus");
    public playerjoin(FileConfiguration infected) {
        this.infected = infected;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        if (infected.contains(playerName)) {
            this.infected = CoWirus.getInfected();
            player.setMetadata("when_infected", new FixedMetadataValue(plugin, infected.getInt(playerName)));
        }
        if (infected.contains("concured_" + playerName)) {
            this.infected = CoWirus.getInfected();
            player.setMetadata("concured", new FixedMetadataValue(plugin, infected.getInt("concured_" + playerName)));
        }
        if (infected.contains("concured_" + playerName)) {
            this.infected = CoWirus.getInfected();
            player.setMetadata("concured", new FixedMetadataValue(plugin, infected.getInt("concured_" + playerName)));
        }


    }
}