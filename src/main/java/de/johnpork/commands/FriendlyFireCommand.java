package de.johnpork.commands;

import de.johnpork.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;

public class FriendlyFireCommand implements CommandExecutor {
    private PlayerManager playerManager;
    public FriendlyFireCommand(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!commandSender.hasPermission("ff")){
            commandSender.sendMessage(ChatColor.RED + "No permssions.");
            return false;
        }
        if(strings.length != 1){
            commandSender.sendMessage(ChatColor.GRAY + "/ff " + ChatColor.GOLD +"PlayerName ");
            return false;
        }
        if(Bukkit.getPlayer(strings[0]) == null){
            commandSender.sendMessage(ChatColor.RED + "This player does not exist.");
            return false;
        }
        Player player = Bukkit.getPlayer(strings[0]);
        if (playerManager.getPlayers().contains(player)){
            player.sendMessage(ChatColor.GREEN +"Reverse Friendly Fire was disabled.");
            commandSender.sendMessage(ChatColor.GRAY + "You disabled Reverse FriendlyFire for the Player " + ChatColor.GOLD+ player.getDisplayName() + ChatColor.GRAY +".");
            playerManager.removePlayer(player);
            return true;
        }
        player.sendMessage(ChatColor.RED + "Reverse Friendly Fire was enabled.");
        commandSender.sendMessage(ChatColor.GRAY +"You enabled Reverse Friendly Fire for the Player"+ ChatColor.GOLD + player.getDisplayName() + ChatColor.GRAY + ".");
        playerManager.addPlayer(player);


        return false;
    }
}
