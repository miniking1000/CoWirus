package org.pythonchik.cowirus.commands;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.pythonchik.cowirus.CoWirus;
import org.pythonchik.cowirus.Message;

import java.util.logging.Logger;


public class status implements CommandExecutor {

    private final Message message = CoWirus.getMessage();
    Plugin plugin = Bukkit.getPluginManager().getPlugin("CoWirus");
    private Logger logger = plugin.getLogger();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        if (sender instanceof Player) {
            Player player = (Player) sender;
            Server server = player.getServer();

            if (args.length == 0) {
                message.send(player, "Использование: /status Игрок");
            } else if (args.length == 1) {
                Player target = server.getPlayer(args[0]);
                if (!Bukkit.getOnlinePlayers().contains(target)) {
                    message.send(player, "Игрок &6" + args[0] + "&f не найден.");
                    return true;
                }
                if (target.hasMetadata("when_infected")) {
                    message.send(player, "Игрок &6" + target.getName() + " &fуже заражен в течении &6" + (target.getStatistic(Statistic.PLAY_ONE_MINUTE) - Integer.parseInt(target.getMetadata("when_infected").get(0).value().toString())) + "&f тиков (&6" + ((target.getStatistic(Statistic.PLAY_ONE_MINUTE) - Integer.parseInt(target.getMetadata("when_infected").get(0).value().toString())) / 20) / 60 + "&fминут).");
                } else {
                    message.send(player, "Игрок &6" + target.getName() + " &fещё не заражен.");
                }
                if (target.hasMetadata("temp_cured")){
                    message.send(player, "А так-же, он будет исцелен ещё &6" + (Integer.parseInt(target.getMetadata("temp_cured").get(0).value().toString()) - target.getStatistic(Statistic.PLAY_ONE_MINUTE)) + "&f тиков (&6" + ((Integer.parseInt(target.getMetadata("temp_cured").get(0).value().toString()) - target.getStatistic(Statistic.PLAY_ONE_MINUTE)) /20) + "&fсекунд).");
                }
                if (target.hasMetadata("concured")){
                    message.send(player,"А так-же, &mэтот лох&r он имеет иммунитет к заражению");
                }
            }
        } else {

            Server server = sender.getServer();
            if (args.length == 0) {
                logger.info("Использование: /status Игрок");
            } else if (args.length == 1) {
                Player target = server.getPlayer(args[0]);
                if (!Bukkit.getOnlinePlayers().contains(target)) {
                    logger.info("Игрок " + args[0] + " не найден.");
                    return true;
                }
                if (target.hasMetadata("when_infected")) {
                    logger.info("Игрок " + target.getName() + " уже заражен в течении " + (target.getStatistic(Statistic.PLAY_ONE_MINUTE) - Integer.parseInt(target.getMetadata("when_infected").get(0).value().toString())) + " тиков (" + ((target.getStatistic(Statistic.PLAY_ONE_MINUTE) - Integer.parseInt(target.getMetadata("when_infected").get(0).value().toString())) / 20) / 60 + "минут).");
                } else {
                    logger.info("Игрок " + target.getName() + " ещё не заражен.");
                }
                if (target.hasMetadata("temp_cured")){
                    logger.info("А так-же, он будет исцелен ещё " + (Integer.parseInt(target.getMetadata("temp_cured").get(0).value().toString()) - target.getStatistic(Statistic.PLAY_ONE_MINUTE)) + " тиков (" + ((Integer.parseInt(target.getMetadata("temp_cured").get(0).value().toString()) - target.getStatistic(Statistic.PLAY_ONE_MINUTE)) /20) + "секунд).");
                }
                if (target.hasMetadata("concured")){
                    logger.info("А так-же, он имеет иммунитет к заражению");
                }
            }
        }
        return true;
    }
}
