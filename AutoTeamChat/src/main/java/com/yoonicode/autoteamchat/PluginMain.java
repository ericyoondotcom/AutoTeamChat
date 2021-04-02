package com.yoonicode.autoteamchat;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.UUID;

public class PluginMain extends JavaPlugin {
    public ArrayList<UUID> teamChatPlayers = new ArrayList<UUID>();
    public Scoreboard scoreboard;
    public PluginCommands commands;
    public boolean useBuiltInTeamMessages;
    public String teamMessageFormat;

    @Override
    public void onEnable(){
        getLogger().info("AutoTeamChat initialized!\nhttps://github.com/yummypasta/AutoTeamChat\nyoonicode.com");
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        saveDefaultConfig();
        reloadFromConfig();

        Bukkit.getPluginManager().registerEvents(new PluginListener(this), this);

        commands = new PluginCommands(this);
        for(String command : PluginCommands.registeredCommands){
            getCommand(command).setExecutor(commands);
        }
    }

    public void reloadFromConfig(){
        FileConfiguration config = getConfig();
        useBuiltInTeamMessages = config.getBoolean("useBuiltInTeamMessages", false);
        teamMessageFormat = config.getString("teamMessageFormat", "AutoTeamChat: teamMessageFormat missing from config.yml!");
    }
}
