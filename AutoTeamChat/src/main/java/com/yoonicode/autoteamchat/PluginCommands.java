package com.yoonicode.autoteamchat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PluginCommands implements CommandExecutor {
    public static final String[] registeredCommands = {
            "chat",
            "reload"
    };
    public final PluginMain main;

    public PluginCommands(PluginMain main) {
        this.main = main;
    }

    public List<String> getCompletions(String[] args, List<String> existingCompletions) {

        if (args[0].equals("chat")) {
            return new ArrayList<String>() {
                {
                    add("team");
                    add("all");
                }
            };
        }
        return existingCompletions;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (label.equals("chat") || label.equals("autoteamchat:chat")) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("Only players can use this command.");
                return false;
            }
            if (!commandSender.hasPermission("minecraft.command.teammsg") && !commandSender.isOp()) {
                commandSender.sendMessage("You don't have permission to run this command!");
                return false;
            }
            Player player = (Player) commandSender;
            UUID uid = player.getUniqueId();
            if (args.length == 0) {
                if (main.teamChatPlayers.contains(uid)) {
                    joinAllChat(player);
                } else {
                    joinTeamChat(player);
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("all") || args[0].equalsIgnoreCase(("a"))) {
                joinAllChat(player);
                return true;
            } else if (args[0].equalsIgnoreCase("team") || args[0].equalsIgnoreCase(("t"))) {
                joinTeamChat(player);
                return true;
            } else {
                commandSender.sendMessage("That is not a valid chat channel! Usage:\n " + ChatColor.AQUA + "/chat team\n/chat all");
                return false;
            }
        }
        if (label.equals("reload") || label.equals("autoteamchat:reload")) {
            main.reloadFromConfig();
            commandSender.sendMessage("Config reloaded!");
            return true;
        }
        return false;
    }

    void joinAllChat(Player player){
        UUID uid = player.getUniqueId();
        if (!main.teamChatPlayers.contains(uid)) {
            player.sendMessage("You are already in all chat!");
            return;
        }
        main.teamChatPlayers.remove(uid);
        player.sendMessage("You are now in " + ChatColor.AQUA + "ALL" + ChatColor.RESET + " chat");
        return;
    }

    void joinTeamChat(Player player){
        UUID uid = player.getUniqueId();
        if(main.teamChatPlayers.contains(uid)){
            player.sendMessage("You are already in team chat!");
            return;
        }
        if(main.scoreboard.getEntryTeam(player.getName()) == null){
            player.sendMessage("You're not on a team! Join a team before using team chat.");
            return;
        }
        main.teamChatPlayers.add(uid);
        player.sendMessage("You are now in " + ChatColor.GREEN + "TEAM" + ChatColor.RESET + " chat");
        return;
    }
}
