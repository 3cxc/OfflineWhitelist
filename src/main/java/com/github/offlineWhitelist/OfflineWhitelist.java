package com.github.offlineWhitelist;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.github.offlineWhitelist.Commands.CommandHandler;
import com.github.offlineWhitelist.Packets.LoginPacketListener;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;

import static com.github.offlineWhitelist.ConfigManager.*;

public final class OfflineWhitelist extends JavaPlugin {

    public static JavaPlugin plugin;

    public static ProtocolManager pm;

    public static String NoPermissionMessage = ChatColor.RED+"您没有权限执行此命令";

    @Override
    public void onLoad(){
        //尝试获取ProtocolLib的API
        pm = ProtocolLibrary.getProtocolManager();
        //尝试生成主配置文件和语言文件，如果已经存在则直接加载
        plugin = getPlugin(OfflineWhitelist.class);
        if (!new File(plugin.getDataFolder(), "config.yml").exists()){
            saveDefaultConfig();
        }
        if (!new File(plugin.getDataFolder(), "message.yml").exists()){
            saveResource("message.yml", false);
        }
        //加载配置文件
        new ConfigManager().loadConfig();
    }


    @Override
    public void onEnable() {
        if (!plugin.getServer().getPluginManager().isPluginEnabled("ProtocolLib")) {
            getLogger().warning(ChatColor.RED + "未安装前置插件ProtocolLib,本插件将自动停用.");
            plugin.getPluginLoader().disablePlugin(plugin);
        }
        getLogger().info(ChatColor.GREEN + "作者: 3cxc，Github开源地址: https://github.com/3cxc/OfflineWhitelist");
        getLogger().info(ChatColor.GREEN + "本插件为免费插件，若你是通过付费渠道获得的，那你被骗了");
        registerCommands();
        //注册监听器
        new LoginPacketListener().addListener();
        //每300tick执行一次，每次执行检查一次在线玩家是否都有白名单
        //如果发现有在线的玩家没有白名单的，且KickOnlinePlayer为true，则踢出该玩家
        new BukkitRunnable() {
            @Override
            public void run() {
                ArrayList<Player> list = new ArrayList<>(getServer().getOnlinePlayers());
                for (Player player : list) {
                    if (!Player_Whitelist.contains(player.getName())) {
                        if (proxy_mode) {
                            player.kickPlayer(KickMessage);
                        }
                    }
                }
            }
        }.runTaskTimer(this,50,300);
    }

    @Override
    public void onDisable(){
        saveConfig();
        getLogger().info(ChatColor.GREEN+"数据已保存并已卸载");
    }

    //注册命令
    private void registerCommands(){
        PluginCommand command = plugin.getCommand("whitelist");
        if (command != null){
            command.setExecutor(new CommandHandler());
            command.setTabCompleter(new CommandHandler());
        }else {
            plugin.getLogger().warning(ChatColor.RED+"命令注册失败,安全起见将禁用本插件");
            plugin.getPluginLoader().disablePlugin(plugin);
        }
    }
}
