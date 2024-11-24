package de.johnpork.commands;

import de.johnpork.managers.PlayerManager;
import org.bukkit.Bukkit;
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
            commandSender.sendMessage(Color.RED + "No permssions.");
            return false;
        }
        if(strings.length != 1){
            commandSender.sendMessage(Color.gray + "/ff " + Color.ORANGE +"PlayerName ");
            return false;
        }
        if(Bukkit.getPlayer(strings[0]) == null){
            commandSender.sendMessage(Color.RED + "This player does not exist.");
            return false;
        }
        Player player = Bukkit.getPlayer(strings[0]);
        if (playerManager.getPlayers().contains(player)){
            player.sendMessage(Color.GREEN +"Reverse Friendly Fire was disabled.");
            commandSender.sendMessage(Color.GRAY + "You disabled Reverse FriendlyFire for the Player " + Color.orange+ player.getDisplayName() + Color.gray +".");
            playerManager.removePlayer(player);
            return true;
        }
        player.sendMessage(Color.RED + "Reverse Friendly Fire was enabled.");
        commandSender.sendMessage(Color.gray +"You enabled Reverse Friendly Fire for the Player"+ Color.ORANGE + player.getDisplayName() + Color.gray + ".");
        playerManager.addPlayer(player);


        return false;
    }
}
