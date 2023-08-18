package org.pythonchik.cowirus.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.pythonchik.cowirus.CoWirus;
import org.pythonchik.cowirus.Message;

import java.io.File;
import java.util.logging.Logger;



public class reload implements CommandExecutor {
    private FileConfiguration infected;

    private final Message message = CoWirus.getMessage();
    public reload(FileConfiguration infected) {
        this.infected = infected;
    }
    CoWirus plugin = (CoWirus) Bukkit.getPluginManager().getPlugin("CoWirus");

    private Logger logger = plugin.getLogger();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        plugin.loadInfected();
        plugin.loadConfig();
        plugin.reload5();
        this.infected = CoWirus.getInfected();
        for (Player player : Bukkit.getOnlinePlayers()){
            try{
                if (infected.getInt(player.getName()) > 0) {
                    player.setMetadata("when_infected", new FixedMetadataValue(plugin, infected.getInt(player.getName())));
                }
            } catch (Exception ignored){}
        }
        if (sender instanceof Player) message.send(sender,"Перезагрузка завершена.");
        else logger.info("Перезагрузка завершена");

        return true;
    }

}
