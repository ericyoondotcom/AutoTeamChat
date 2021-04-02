package com.yoonicode.autoteamchat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.scoreboard.Team;

import java.util.List;

public class PluginListener implements Listener {
    PluginMain main;

    public PluginListener(PluginMain main){
        this.main = main;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        if(main.teamChatPlayers.contains(player.getUniqueId())){
            Team team = main.scoreboard.getEntryTeam(player.getName());
            if(team == null){
                player.sendMessage("You're not on a team. You've been moved to " + ChatColor.AQUA + "ALL" + ChatColor.RESET + " chat.");
                main.teamChatPlayers.remove(player.getUniqueId());
                return;
            }
            event.setCancelled(true);
            if(main.useBuiltInTeamMessages){
                final String temp_msg = event.getMessage();
                final Player temp_player = player;
                Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                    public void run() {
                        temp_player.performCommand("teammsg " + temp_msg);
                    }
                });
            } else {
                String cleaned = ChatColor.translateAlternateColorCodes('&',
                    main.teamMessageFormat
                        .replaceAll("%color%", team.getColor().toString())
                        .replaceAll("%team%", team.getDisplayName())
                        .replaceAll("%playername%", player.getDisplayName())
                        .replaceAll("%message%", event.getMessage())
                );
                for(String entry : team.getEntries()){
                    Player e = Bukkit.getPlayer(entry);
                    if(e == null || !e.isOnline()) continue;
                    e.sendMessage(cleaned);
                }
            }
        }
    }

    @EventHandler
    public void onAutocomplete(TabCompleteEvent event){
        String buffer = event.getBuffer();
        if(!buffer.startsWith("/")) return;
        String[] args = buffer.split(" ");
        List<String> completions = main.commands.getCompletions(args, event.getCompletions());
        event.setCompletions(completions);
    }
}
