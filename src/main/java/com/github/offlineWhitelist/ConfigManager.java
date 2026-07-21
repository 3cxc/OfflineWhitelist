package com.github.offlineWhitelist;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

import static com.github.offlineWhitelist.OfflineWhitelist.plugin;

public class ConfigManager {

    public static volatile boolean PROXY_MODE;
    public static volatile boolean KICK_ONLINE_PLAYER;
    public static volatile List<String> PLAYER_WHITELIST;

    public static volatile String NO_PERMISSION_MESSAGE;
    public static volatile String RELOAD_CONFIG_MESSAGE;
    public static volatile String WHITELIST_EMPTY_MESSAGE;
    public static volatile String WHITELIST_LIST_MESSAGE;
    public static volatile String WHITELIST_NAME_ERROR_MESSAGE;
    public static volatile String WHITELIST_ADD_MESSAGE;
    public static volatile String WHITELIST_EXIST_MESSAGE;
    public static volatile String WHITELIST_REMOVE_MESSAGE;
    public static volatile String WHITELIST_NONE_MESSAGE;
    public static volatile String WHITELIST_SUBCOMMAND_ERROR_MESSAGE;
    public static String KICK_MESSAGE;

    /**
     * 加载配置文件
     */
    public void loadConfig(){
        PLAYER_WHITELIST = plugin.getConfig().getStringList("Whitelist.list");
        PROXY_MODE = plugin.getConfig().getBoolean("Whitelist.proxy_mode",true);
        KICK_ONLINE_PLAYER = plugin.getConfig().getBoolean("Whitelist.KickOnlinePlayer",false);
        //读取语言文件
        File file = new File(plugin.getDataFolder(), "message.yml");
        FileConfiguration messageConfig = YamlConfiguration.loadConfiguration(file);

        NO_PERMISSION_MESSAGE = messageConfig.getString("no-permission-message");
        RELOAD_CONFIG_MESSAGE = messageConfig.getString("reload-config-message");
        WHITELIST_EMPTY_MESSAGE = messageConfig.getString("whitelist-empty-message");
        WHITELIST_LIST_MESSAGE = messageConfig.getString("whitelist-list-message");
        WHITELIST_NAME_ERROR_MESSAGE = messageConfig.getString("whitelist-name-error-message");
        WHITELIST_ADD_MESSAGE = messageConfig.getString("whitelist-add-message");
        WHITELIST_EXIST_MESSAGE = messageConfig.getString("whitelist-exist-message");
        WHITELIST_REMOVE_MESSAGE = messageConfig.getString("whitelist-remove-message");
        WHITELIST_NONE_MESSAGE = messageConfig.getString("whitelist-none-message");
        WHITELIST_SUBCOMMAND_ERROR_MESSAGE = messageConfig.getString("whitelist-subcommand-error-message");

        //将踢出信息列表拼接成一个String字符串
        List<String> kickMessageList = messageConfig.getStringList("kick-message");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < kickMessageList.size(); i++) {
            result.append(kickMessageList.get(i));
            if (i < kickMessageList.size() - 1) {
                result.append("\n");
            }
        }
        KICK_MESSAGE = result.toString();
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
