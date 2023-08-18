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

public class infect implements CommandExecutor {
    Plugin plugin = Bukkit.getPluginManager().getPlugin("CoWirus");
    private final Message message = CoWirus.getMessage();
    private Logger logger = plugin.getLogger();
    CoWirus plugin2 = (CoWirus) Bukkit.getPluginManager().getPlugin("CoWirus");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Server server = player.getServer();
            if (args.length == 0) {
                message.send(player, "Использование: /infect Игрок (время)");
                message.send(player, "Где (время) это количество времени(в тиках) которое игрок 'отыгал' с заражением");
            } else if (args.length == 1) {
                Player target = server.getPlayer(args[0]);
                if (!Bukkit.getOnlinePlayers().contains(target)) {
                    message.send(player, "Игрок &6" + args[0] + "&f не найден.");
                    return true;
                }
                target.setMetadata("when_infected", new FixedMetadataValue(plugin, target.getStatistic(Statistic.PLAY_ONE_MINUTE)));
                plugin2.updInfected(target.getName(), target.getStatistic(Statistic.PLAY_ONE_MINUTE));
                message.send(player, "Игрок &6" + target.getName() + " &fбыл успешно заражен.");
            } else if (args.length == 2) {
                Player target = server.getPlayer(args[0]);
                if (!Bukkit.getOnlinePlayers().contains(target)) {
                    message.send(player, "Игрок &6" + args[0] + "&f не найден.");
                    return true;
                }
                int ticks = Integer.parseInt(args[1]);
                target.setMetadata("when_infected", new FixedMetadataValue(plugin, target.getStatistic(Statistic.PLAY_ONE_MINUTE) - ticks));
                plugin2.updInfected(target.getName(), target.getStatistic(Statistic.PLAY_ONE_MINUTE) - ticks);
                message.send(player, "Игрок &6" + target.getName() + " &fбыл успешно заражен с времени в&6 " + (ticks / 20) / 60 + " &fминут.");
            }
        } else{
            Server server = sender.getServer();
            if (args.length == 0) {
                logger.info("Использование: /infect Игрок (время)");
                logger.info("Где (время) это количество времени(в тиках) которое игрок 'отыграл' с заражением");
            } else if (args.length == 1) {
                Player target = server.getPlayer(args[0]);
                if (!Bukkit.getOnlinePlayers().contains(target)) {
                    logger.info("Игрок " + args[0] + " не найден.");
                    return true;
                }
                target.setMetadata("when_infected", new FixedMetadataValue(plugin, target.getStatistic(Statistic.PLAY_ONE_MINUTE)));
                plugin2.updInfected(target.getName(), target.getStatistic(Statistic.PLAY_ONE_MINUTE));
                logger.info("Игрок " + target.getName() + " был успешно заражен.");
            } else if (args.length == 2) {
                Player target = server.getPlayer(args[0]);
                if (!Bukkit.getOnlinePlayers().contains(target)) {
                   logger.info("Игрок " + args[0] + " не найден.");
                    return true;
                }
                int ticks = Integer.parseInt(args[1]);
                target.setMetadata("when_infected", new FixedMetadataValue(plugin, target.getStatistic(Statistic.PLAY_ONE_MINUTE) - ticks));
                plugin2.updInfected(target.getName(), target.getStatistic(Statistic.PLAY_ONE_MINUTE) - ticks);
                logger.info("Игрок " + target.getName() + " был успешно заражен с времени в " + (ticks / 20) / 60 + " минут.");
            }
        }
        return true;
    }
}
