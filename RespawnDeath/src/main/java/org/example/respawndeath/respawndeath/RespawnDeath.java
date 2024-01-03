package org.example.respawndeath.respawndeath;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.configuration.file.FileConfiguration;

public final class RespawnDeath extends JavaPlugin implements Listener {
    private Sound Sound;
    private String Title;
    private String Subtitle;
    private int Stay;
    private int Out;
    private int In;
    private boolean Enable;

    @Override
    public void onEnable() {
        getLogger().info("Enable");
        Bukkit.getPluginManager().registerEvents(this,this);
        saveDefaultConfig();
        reloadConfig();
        FileConfiguration config = getConfig();
        Enable = config.getBoolean("Enable");
        Title = config.getString("Title");
        Subtitle = config.getString("Subtitle");
        Stay = config.getInt("Stay");
        In = config.getInt("In");
        Out = config.getInt("Out");
        Sound = Sound.valueOf(config.getString("Sound"));

    }

    @Override
    public void onDisable() {
        getLogger().info("Disable");
    }
    @EventHandler
    public void PlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (Enable) {
            sendTitle(player, Title, Subtitle, Stay, In, Out);
        }

        Sound(player);

        new BukkitRunnable() {
            @Override
            public void run() {
                player.spigot().respawn();
            }
        }.runTaskLater(this,1L);
    }
    private void sendTitle(Player player, String title, String subtitle, int Stay, int In, int Out) {
        player.sendTitle(title, subtitle, In, Stay, Out);
    }
    private void Sound(Player player) {
        player.playSound(player.getLocation(), Sound, 1f,1f);
    }
}