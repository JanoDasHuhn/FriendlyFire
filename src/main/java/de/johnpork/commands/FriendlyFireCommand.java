package de.johnpork.commands;

import de.johnpork.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FriendlyFireCommand implements CommandExecutor {
    private PlayerManager playerManager;
    public FriendlyFireCommand(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!commandSender.hasPermission("ff")){
            commandSender.sendMessage("No permssions");
            return false;
        }
        if(strings.length != 1){
            commandSender.sendMessage("/ff PlayerName ");
            return false;
        }
        if(Bukkit.getPlayer(strings[0]) == null){
            commandSender.sendMessage("this player does not exist");
            return false;
        }
        Player player = Bukkit.getPlayer(strings[0]);
        if (playerManager.getPlayers().contains(player)){
            player.sendMessage("Reverse Friendly Fire wurde fuer dich deaktiviert");
            commandSender.sendMessage("Du hast Friendly Fire fuer den Spieler "+ player.getDisplayName() + " deaktiviert.");
            playerManager.removePlayer(player);
            return true;
        }
        player.sendMessage("Reverse Friendly Fire wurde fuer dich aktiviert");
        commandSender.sendMessage("Du hast Friendly Fire fuer den Spieler "+ player.getDisplayName() + " aktiviert.");
        playerManager.addPlayer(player);


        return false;
    }
}
