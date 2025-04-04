package com.github.offlineWhitelist;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

import static com.github.offlineWhitelist.OfflineWhitelist.plugin;

public class ConfigManager {

    public static volatile List<String> Player_Whitelist;

    public static String KickMessage;

    public static volatile boolean proxy_mode;

    /**
     * 加载配置文件
     */
    public void loadConfig(){
        Player_Whitelist = plugin.getConfig().getStringList("Whitelist.list");
        proxy_mode = plugin.getConfig().getBoolean("Whitelist.KickOnlinePlayer",false);
        //读取语言文件
        File file = new File(plugin.getDataFolder(), "message.yml");
        FileConfiguration messageConfig = YamlConfiguration.loadConfiguration(file);

        //将踢出信息列表拼接成一个String字符串
        List<String> kickMessageList = messageConfig.getStringList("kick-message");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < kickMessageList.size(); i++) {
            result.append(kickMessageList.get(i));
            if (i < kickMessageList.size() - 1) {
                result.append("\n");
            }
        }
        KickMessage = result.toString();
    }

    /**
     * 更改并保存一个配置文件项
     * @param path 配置文件项
     * @param del 新值
     */
    public static void saveConfig(String path,List<String> del){
        plugin.getConfig().set(path,del);
        plugin.saveConfig();
    }
}
