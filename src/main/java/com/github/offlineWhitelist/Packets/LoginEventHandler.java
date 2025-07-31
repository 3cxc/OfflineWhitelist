package com.github.offlineWhitelist.Packets;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import static com.github.offlineWhitelist.ConfigManager.*;

/**
 * 兼容检测方法(Bukkit API)
 * @since 3.1
 */
public class LoginEventHandler implements Listener {

    @EventHandler
    public void PlayerLoginEvent(AsyncPlayerPreLoginEvent event){
        if (!PLAYER_WHITELIST.contains(event.getName()) && PROXY_MODE){
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,KICK_MESSAGE);
        }
    }

}
