package org.pythonchik.cowirus;

import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.pythonchik.cowirus.commands.*;

import java.io.File;



public final class CoWirus extends JavaPlugin {

    private static FileConfiguration infected;
    private static FileConfiguration config;
    Plugin plugin = this;
    private static CoWirus instance;

    public static CoWirus getInstance() {
        return instance;
    }
    public static Message message;
    public static FileConfiguration getInfected() {
        return infected;
    }

    @Override
    public void onEnable() {
        instance = this;
        loadInfected();
        loadConfig();

        message = new Message(this);
        getCommand("infect").setExecutor(new infect());
        getCommand("infect").setTabCompleter(new OnTabCommand(config));

        getCommand("cure").setExecutor(new cure());
        getCommand("cure").setTabCompleter(new OnTabCommand(config));

        getCommand("concure").setExecutor(new concure());
        getCommand("concure").setTabCompleter(new OnTabCommand(config));

        getCommand("status").setExecutor(new status());
        getCommand("status").setTabCompleter(new OnTabCommand(config));

        getCommand("CoWreload").setExecutor(new reload(infected));
        getCommand("CoWreload").setTabCompleter(new OnTabCommand(config));

        getCommand("cowuse").setExecutor(new trigger(config));
        getCommand("cowuse").setTabCompleter(new OnTabCommand(config));

        getCommand("tempcure").setExecutor(new tempcure());
        getCommand("tempcure").setTabCompleter(new OnTabCommand(config));

        getServer().getPluginManager().registerEvents(new DamageListener(), this);
        getServer().getPluginManager().registerEvents(new playerjoin(infected), this);

        effects();

    }


    public void reload5(){
        Bukkit.getPluginManager().disablePlugin(plugin);
        Bukkit.getPluginManager().enablePlugin(plugin);
    }
    @Override
    public void onDisable() {
        saveInfected();
        saveConfig1();
    }
    public static Message getMessage(){return message;}



    private void effects() {
        new BukkitRunnable() {
            @Override
            public void run(){
                for (Player player: Bukkit.getOnlinePlayers()){
                    if (player.hasMetadata("when_infected")){
                        if (!player.getGameMode().equals(GameMode.SPECTATOR)) {
                            if (player.hasMetadata("temp_cured")){
                                if (Integer.parseInt(player.getMetadata("temp_cured").get(0).value().toString()) >= player.getStatistic(Statistic.PLAY_ONE_MINUTE)) {
                                    continue;
                                } else {
                                    player.removeMetadata("temp_cured",instance);
                                    updInfected("temp_cured_" + player.getName(),null);
                                }
                            }
                            var tick = player.getStatistic(Statistic.PLAY_ONE_MINUTE) - Integer.parseInt(player.getMetadata("when_infected").get(0).value().toString());
                            for (int i = 1; i <= config.getInt("global.stages"); i++) {
                                String stageName = "stage_" + i;
                                if (tick < config.getInt(stageName + ".Tfrom")) {
                                    if (i == 1) {
                                        break;
                                    }
                                    stageName = "stage_" + (i - 1);
                                    if (tick - config.getInt(stageName + ".Tfrom") <= 301 ) {
                                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getString(stageName + ".message")));
                                    }
                                    ConfigurationSection stageSection = config.getConfigurationSection(stageName);
                                    var rand = Math.random();
                                    for (String chanceName : stageSection.getKeys(false)) {
                                        if (!chanceName.equals("Tfrom") && !chanceName.equals("message")) {
                                            ConfigurationSection chanceSelection = stageSection.getConfigurationSection(chanceName);
                                            if (chanceSelection.getDouble("low") <= rand && rand <= chanceSelection.getDouble("high")) {
                                                for (String when : chanceSelection.getKeys(false)) {
                                                    switch (when) {
                                                        case "sun" -> {
                                                            if (!(player.getLocation().getWorld().getHighestBlockYAt(player.getLocation()) > player.getLocation().getY()) && (player.getLocation().getWorld().getTime() < 12300 || player.getLocation().getWorld().getTime() > 23850) && player.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                                                                ConfigurationSection lastSelection = chanceSelection.getConfigurationSection("sun");
                                                                apply(player, lastSelection);
                                                            }
                                                        }
                                                        case "cave" -> {
                                                            if (!player.getWorld().getEnvironment().equals(World.Environment.THE_END) && (!(player.getLocation().getWorld().getTime() < 12300 || player.getLocation().getWorld().getTime() > 23850) || (player.getLocation().getWorld().getHighestBlockYAt(player.getLocation()) > player.getLocation().getY()))) {
                                                                ConfigurationSection lastSelection = chanceSelection.getConfigurationSection("cave");
                                                                apply(player, lastSelection);
                                                            }
                                                        }
                                                        case "end" -> {
                                                            if (player.getWorld().getEnvironment().equals(World.Environment.THE_END)) {
                                                                ConfigurationSection lastSelection = chanceSelection.getConfigurationSection("end");
                                                                apply(player, lastSelection);
                                                            }
                                                        }
                                                        default -> {
                                                        }
                                                    }
                                                }
                                                break;
                                            }
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(this,0, config.getInt("global.period"));
    }

    public void apply(Player player, ConfigurationSection lastSelection){
        ConfigurationSection effectSelection = lastSelection.getConfigurationSection("effects");
        if (effectSelection != null) {
            for (String effectName : effectSelection.getKeys(false)) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effectName), effectSelection.getInt(effectName + ".duration"), effectSelection.getInt(effectName + ".power")));
            }
        }
        if (lastSelection.getInt("damage") != 0) {
            player.damage(lastSelection.getDouble("damage"));
        }
        if (lastSelection.getBoolean("spil")){
            spil(player);
        }
        if (lastSelection.getBoolean("elytra")) {
            if (player.isGliding() && lastSelection.getBoolean("particle")) {
                player.setGliding(false);
                player.spawnParticle(Particle.DRAGON_BREATH, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 100, 0, 1, 0, 0.1);
            } else{
                player.setGliding(false);
            }
        }

        if (lastSelection.getBoolean("drop")){
            player.dropItem(false);
        }

        if (lastSelection.getBoolean("dragon")){
            player.getWorld().spawnEntity(new Location(player.getWorld(),player.getLocation().getX(),player.getLocation().getY(),player.getLocation().getZ(),player.getLocation().getYaw(),player.getLocation().getPitch()),EntityType.DRAGON_FIREBALL).setVelocity(player.getEyeLocation().getDirection().multiply(1.5));

        }

        if (lastSelection.getInt("fire") > 0){
            player.setFireTicks(lastSelection.getInt("fire"));
        }

        if (lastSelection.getInt("endermite") >0){
            for (int l=0;l < lastSelection.getInt("endermite");l++) player.getLocation().getWorld().spawnEntity(player.getLocation(),EntityType.ENDERMITE);
        }

        if (lastSelection.getBoolean("endersound")){
            player.playSound(player, Sound.ENTITY_ENDERMAN_SCREAM,9999,1);
        }

        if (lastSelection.getBoolean("particle") && !lastSelection.getBoolean("elytra")) {
            player.spawnParticle(Particle.DRAGON_BREATH, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 100, 0, 1, 0, 0.1);
        }
    }

    private void spil(Player player){
        Location location = player.getLocation().toVector().add(player.getLocation().getDirection().multiply(0.8D)).toLocation(player.getWorld()).add(0.0D, 1.0D, 0.0D);
        Entity spitmonster = player.getWorld().spawnEntity(location, EntityType.LLAMA_SPIT);
        spitmonster.setVelocity(player.getEyeLocation().getDirection().multiply(1));
        player.playSound(player, Sound.ENTITY_FOX_SPIT,200,0);
    }

    public void loadInfected() {
        File configFile = new File(getDataFolder(), "infected.yml");
        if (!configFile.exists()) {
            saveResource("infected.yml", false);
        }
        infected = YamlConfiguration.loadConfiguration(configFile);
    }
    public void loadConfig() {
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }
        config = null;
        config = YamlConfiguration.loadConfiguration(configFile);
    }
    public void updInfected(String path, Integer value){
        infected.set(path,value);
        try {
            infected.save(new File(getDataFolder(), "infected.yml"));
        } catch (Exception ignored) {}
    }
    public void saveInfected() {
        try {
            infected.save(new File(getDataFolder(), "infected.yml"));
        } catch (Exception ignored) {}
    }
    public void saveConfig1() {
        try {
            config.save(new File(getDataFolder(), "config.yml"));
        } catch (Exception ignored) {}
    }
}