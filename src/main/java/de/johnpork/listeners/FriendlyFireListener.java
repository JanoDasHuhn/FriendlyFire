package de.johnpork.listeners;

import de.johnpork.managers.PlayerManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.PluginManager;

import java.net.http.WebSocket;

public class FriendlyFireListener implements Listener {
    private PlayerManager playerManager;
    public FriendlyFireListener(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @EventHandler
    void onDamage(EntityDamageByEntityEvent event){
        if(event.getDamager().getType() != EntityType.PLAYER){
            return;
        }
        if(!(event.getEntity().getType() == EntityType.PLAYER)){
            return;
        }
        Player damager = (Player) event.getDamager();
        Player victim = (Player) event.getDamager();
        if(!playerManager.getPlayers().contains(damager)){
            return;
        }
        damager.damage(event.getDamage());
        event.setCancelled(true);
    }

}