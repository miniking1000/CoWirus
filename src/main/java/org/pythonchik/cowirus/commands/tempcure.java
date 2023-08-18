package org.pythonchik.cowirus.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.pythonchik.cowirus.CoWirus;
import org.pythonchik.cowirus.Message;

import java.util.logging.Logger;

public class tempcure implements CommandExecutor {
    private final Message message = CoWirus.getMessage();

    CoWirus plugin = CoWirus.getInstance();
    private Logger logger = plugin.getLogger();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Server server = player.getServer();
            if (args.length == 0) {
                message.send(player, "Использование: /tempcure Игрок (секунды)");
            } else if (args.length == 1) {
                Player target = server.getPlayer(args[0]);
                if (!Bukkit.getOnlinePlayers().contains(target)) {
                    message.send(player, "Игрок &6" + args[0] + "&f не найден.");
                    return true;
                }
                target.setMetadata("temp_cured", new FixedMetadataValue(plugin, target.getStatistic(Statistic.PLAY_ONE_MINUTE)+1200));
                plugin.updInfected("temp_cured_" + target.getName(), target.getStatistic(Statistic.PLAY_ONE_MINUTE)+1200);
                message.send(player, "Игрок &6" + args[0] + "&f временно исцелен на 60с");
            } else if (args.length == 2) {
                Player target = server.getPlayer(args[0]);
                if (!Bukkit.getOnlinePlayers().contains(target)) {
                    message.send(player, "Игрок &6" + args[0] + "&f не найден.");
                    return true;
                }
                int ticks = Integer.parseInt(args[1]) * 20;
                target.setMetadata("temp_cured", new FixedMetadataValue(plugin, target.getStatistic(Statistic.PLAY_ONE_MINUTE) + ticks));
                plugin.updInfected("temp_cured_" + target.getName(), target.getStatistic(Statistic.PLAY_ONE_MINUTE) + ticks);
                message.send(player, "Игрок &6" + args[0] + "&f временно исцелен на &6" + args[1] + "&fc");
            }
        } else{
            Server server = sender.getServer();
            if (args.length == 0) {
                logger.info("Использование: /tempcute Игрок (секунды)");
            } else if (args.length == 1) {
                Player target = server.getPlayer(args[0]);
                if (!Bukkit.getOnlinePlayers().contains(target)) {
                    logger.info("Игрок " + args[0] + " не найден.");
                    return true;
                }
                target.setMetadata("temp_cured", new FixedMetadataValue(plugin, target.getStatistic(Statistic.PLAY_ONE_MINUTE)+1200));
                plugin.updInfected("temp_cured_" + target.getName(), target.getStatistic(Statistic.PLAY_ONE_MINUTE)+1200);
                logger.info("Игрок " + args[0] + " временно исцелен на 60с");
            } else if (args.length == 2) {
                Player target = server.getPlayer(args[0]);
                if (!Bukkit.getOnlinePlayers().contains(target)) {
                    logger.info("Игрок " + args[0] + " не найден.");
                    return true;
                }
                int ticks = Integer.parseInt(args[1]) * 20;
                target.setMetadata("temp_cured", new FixedMetadataValue(plugin, target.getStatistic(Statistic.PLAY_ONE_MINUTE) + ticks));
                plugin.updInfected("temp_cured_" + target.getName(), target.getStatistic(Statistic.PLAY_ONE_MINUTE) + ticks);
                logger.info("Игрок " + args[0] + " временно исцелен на " + args[1] + "c");
            }
        }
        return true;
    }
}
