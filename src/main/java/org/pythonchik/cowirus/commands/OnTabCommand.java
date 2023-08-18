package org.pythonchik.cowirus.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


import java.util.ArrayList;
import java.util.List;

public class OnTabCommand implements TabCompleter {

    FileConfiguration config;
    public OnTabCommand(FileConfiguration config){ this.config = config;}
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("cure") || command.getName().equalsIgnoreCase("status")|| command.getName().equalsIgnoreCase("concure")) {
            if (args.length == 1) {
                List<String> completions = new ArrayList<>();
                for (Player name : Bukkit.getOnlinePlayers()) {
                    completions.add(name.getName());
                }
                return completions;
            }
        } else if (command.getName().equalsIgnoreCase("infect")){
            if (args.length == 1) {
                List<String> completions = new ArrayList<>();
                for (Player name : Bukkit.getOnlinePlayers()) {
                    completions.add(name.getName());
                }
                return completions;
            } else if (args.length == 2) {
                List<String> completions = new ArrayList<>();
                for (String i : config.getKeys(false)){
                    if (i.equals("global") || i.equals("stage_" + config.getInt("global.stages"))) {
                        continue;
                    }
                    completions.add(config.getString(i + ".Tfrom"));
                }
                return completions;
            }
        }else if (command.getName().equalsIgnoreCase("cowuse")) {
            if (args.length == 1) {
                List<String> completions = new ArrayList<>();
                for (Player name : Bukkit.getOnlinePlayers()) {
                    completions.add(name.getName());
                }
                return completions;
            } else if (args.length == 2) {
                List<String> completions = new ArrayList<>();
                int a = config.getInt("global.stages")-1;
                for (int i = 1; i<=a; i++){
                    completions.add(String.valueOf(i));
                }
                return completions;
            } else if (args.length == 3) {
                List<String> completions = new ArrayList<>();
                for (String i : config.getConfigurationSection("stage_" + args[1]).getKeys(false)){
                    if (String.valueOf(i).equals("Tfrom") || String.valueOf(i).equals("message")){
                        continue;
                    }
                    completions.add(String.valueOf(i));
                }
                return completions;
            } else if (args.length == 4) {
                List<String> completions = new ArrayList<>();
                completions.add("sun");
                completions.add("cave");
                completions.add("end");
                return completions;
            }
        }else if (command.getName().equalsIgnoreCase("tempcure")) {
            if (args.length == 1) {
                List<String> completions = new ArrayList<>();
                for (Player name : Bukkit.getOnlinePlayers()) {
                    completions.add(name.getName());
                }
                return completions;
            } else if (args.length == 2) {
                List<String> completions = new ArrayList<>();
                completions.add("0");
                completions.add("60");
                completions.add("120");
                completions.add("666");
                return completions;
            }
        }
        return null;
    }
}

