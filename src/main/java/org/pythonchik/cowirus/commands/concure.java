package org.pythonchik.cowirus.commands;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.pythonchik.cowirus.CoWirus;
import org.pythonchik.cowirus.Message;

import java.util.logging.Logger;

public class concure implements CommandExecutor {

    private final Message message = CoWirus.getMessage();


    CoWirus plugin2 = (CoWirus) Bukkit.getPluginManager().getPlugin("CoWirus");

    private Logger logger = plugin2.getLogger();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Server server = player.getServer();
            Plugin plugin = Bukkit.getPluginManager().getPlugin("CoWirus");
            if (args.length == 0) {
                message.send(player, "Использование: /concure Игрок - Делает игрока невосприимчевым к заражению");
            } else if (args.length == 1) {
                Player target = server.getPlayer(args[0]);
                if (!Bukkit.getOnlinePlayers().contains(target)) {
                    message.send(player, "Игрок &6" + args[0] + "&f не найден.");
                    return true;
                }
                if (target.hasMetadata("when_infected")) {
                    target.removeMetadata("when_infected", plugin);
                    plugin2.updInfected(target.getName(), null);
                }
                target.setMetadata("concured", new FixedMetadataValue(plugin,target.getStatistic(Statistic.PLAY_ONE_MINUTE)));
                plugin2.updInfected("concured_" + target.getName(), target.getStatistic(Statistic.PLAY_ONE_MINUTE));
                message.send(player, "Игрок &6" + target.getName() + "&f вылечелся и получил иммунитет к заражению ");
            }
        } else{
            Server server = sender.getServer();
            Plugin plugin = Bukkit.getPluginManager().getPlugin("CoWirus");
            if (args.length == 0) {
                logger.info("Использование: /concure Игрок - Делает игрока невосприимчевым к заражению(И лечит)");
            } else if (args.length == 1) {
                Player target = server.getPlayer(args[0]);
                if (!Bukkit.getOnlinePlayers().contains(target)) {
                   logger.info("Игрок " + args[0] + " не найден.");
                    return true;
                }
                if (target.hasMetadata("when_infected")) {
                    target.removeMetadata("when_infected", plugin);
                    plugin2.updInfected(target.getName(), null);
                }
                target.setMetadata("concured", new FixedMetadataValue(plugin,target.getStatistic(Statistic.PLAY_ONE_MINUTE)));
                plugin2.updInfected("concured_" + target.getName(), target.getStatistic(Statistic.PLAY_ONE_MINUTE));
                logger.info("Игрок " + target.getName() + " вылечелся и получил иммунитет к заражению ");
            }
        }
        return true;
    }
}
