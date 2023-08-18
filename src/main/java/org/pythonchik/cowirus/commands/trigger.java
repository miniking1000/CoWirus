package org.pythonchik.cowirus.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.pythonchik.cowirus.CoWirus;
import org.pythonchik.cowirus.Message;

import java.util.logging.Logger;


public class trigger implements CommandExecutor {
    private final Message message = CoWirus.getMessage();
    private final FileConfiguration config;
    CoWirus plugin = CoWirus.getInstance();
    private Logger logger = plugin.getLogger();
    public trigger(FileConfiguration config) {this.config = config;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            Server server = player.getServer();
            if (args.length == 0 || args.length == 1 || args.length == 2) {
                message.send(player, "Использование: /cowuse Игрок Стадия Категория (Условие)");
            } else if (args.length == 3) {
                Player target = server.getPlayer(args[0]);
                if (!Bukkit.getOnlinePlayers().contains(target)) {
                    message.send(player, "Игрок &6" + args[0] + "&f не найден.");
                    return true;
                }
                things(target, Integer.parseInt(args[1]), args[2], null);
            } else if (args.length == 4) {
                Player target = server.getPlayer(args[0]);
                if (!Bukkit.getOnlinePlayers().contains(target)) {
                    message.send(player, "Игрок &6" + args[0] + "&f не найден.");
                    return true;
                }
                things(target, Integer.parseInt(args[1]), args[2], args[3]);
            }
        } else{
            Server server = sender.getServer();
            if (args.length == 0 || args.length == 1 || args.length == 2) {
                logger.info("Использование: /cowuse Игрок Стадия Категория (Условие)");
            } else if (args.length == 3) {
                Player target = server.getPlayer(args[0]);
                if (!Bukkit.getOnlinePlayers().contains(target)) {
                    logger.info("Игрок " + args[0] + " не найден.");
                    return true;
                }
                things(target, Integer.parseInt(args[1]), args[2], null);
            } else if (args.length == 4) {
                Player target = server.getPlayer(args[0]);
                if (!Bukkit.getOnlinePlayers().contains(target)) {
                    logger.info("Игрок " + args[0] + " не найден.");
                    return true;
                }
                things(target, Integer.parseInt(args[1]), args[2], args[3]);
            }

        }
        return true;
    }

    private void things (Player player, Integer stageInt, String category, String category2) {
        try {
            ConfigurationSection chanceSelection = config.getConfigurationSection("stage_" + stageInt + "." + category);
            if (category2 == null) {
                for (String category3 : chanceSelection.getKeys(false)) {
                    switch (category3) {
                        case "sun" -> {
                            if (!(player.getLocation().getWorld().getHighestBlockYAt(player.getLocation()) > player.getLocation().getY()) && (player.getLocation().getWorld().getTime() < 12300 || player.getLocation().getWorld().getTime() > 23850) && player.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                                ConfigurationSection lastSelection = chanceSelection.getConfigurationSection("sun");
                                plugin.apply(player, lastSelection);
                            }
                        }
                        case "cave" -> {
                            if (!player.getWorld().getEnvironment().equals(World.Environment.THE_END) && (!(player.getLocation().getWorld().getTime() < 12300 || player.getLocation().getWorld().getTime() > 23850) || (player.getLocation().getWorld().getHighestBlockYAt(player.getLocation()) > player.getLocation().getY()))) {
                                ConfigurationSection lastSelection = chanceSelection.getConfigurationSection("cave");
                                plugin.apply(player, lastSelection);
                            }
                        }
                        case "end" -> {
                            if (player.getWorld().getEnvironment().equals(World.Environment.THE_END)) {
                                ConfigurationSection lastSelection = chanceSelection.getConfigurationSection("end");
                                plugin.apply(player, lastSelection);
                            }
                        }
                        default -> {
                        }
                    }
                }
            } else {
                switch (category2) {
                    case "sun":
                        ConfigurationSection lastSelection = chanceSelection.getConfigurationSection("sun");
                        plugin.apply(player, lastSelection);
                        break;
                    case "cave":
                        ConfigurationSection lastSelection2 = chanceSelection.getConfigurationSection("cave");
                        plugin.apply(player, lastSelection2);
                        break;
                    case "end":
                        ConfigurationSection lastSelection3 = chanceSelection.getConfigurationSection("end");
                        plugin.apply(player, lastSelection3);
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception ignored) {
            logger.info("Команда CoWuse вызвала ошибку.");
        }
    }
}
