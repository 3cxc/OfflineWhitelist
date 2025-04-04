package com.github.offlineWhitelist.Packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;

import static com.github.offlineWhitelist.ConfigManager.*;
import static com.github.offlineWhitelist.OfflineWhitelist.plugin;
import static com.github.offlineWhitelist.OfflineWhitelist.pm;

/**
 * 数据包监听器
 */
public class LoginPacketListener {
    public void addListener(){
        pm.addPacketListener(new PacketAdapter(
                plugin,
                ListenerPriority.HIGHEST,
                PacketType.Login.Client.START

        ) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                //获取登录数据包的玩家资料
                WrappedGameProfile gameProfile = packet.getGameProfiles().readSafely(0);
                //玩家不在白名单，且白名单已开启就踢出
                if (!Player_Whitelist.contains(gameProfile.getName()) && proxy_mode){
                    PacketContainer disconnectPacket = new PacketContainer(PacketType.Login.Server.DISCONNECT);
                    WrappedChatComponent component = WrappedChatComponent.fromText(KickMessage);
                    disconnectPacket.getChatComponents().write(0, component);

                    ProtocolLibrary.getProtocolManager().sendServerPacket(event.getPlayer(), disconnectPacket);

                    event.getPlayer().kickPlayer("无白名单");
                    event.setCancelled(true);
                }
            }
        });
    }
}
