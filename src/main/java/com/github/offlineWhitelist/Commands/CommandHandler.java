package com.github.offlineWhitelist.Commands;

import com.github.offlineWhitelist.ConfigManager;
import com.github.offlineWhitelist.OfflineWhitelist;
import com.github.offlineWhitelist.PermissionType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static com.github.offlineWhitelist.ConfigManager.PLAYER_WHITELIST;
import static com.github.offlineWhitelist.ConfigManager.PROXY_MODE;
import static com.github.offlineWhitelist.OfflineWhitelist.plugin;

/**
 * 白名单命令管理类
 */
@SuppressWarnings("NullableProblems")
public class CommandHandler implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command , String s, String[] args) {
        if (command.getName().equalsIgnoreCase("whitelist")){
            //仅使用/whitelist时的提示
            if (args.length == 0){
                sender.sendMessage(ChatColor.GRAY+"/whitelist on|off|add|remove|reload|list");
            }

            if (args.length == 1){
                //获取第一个子命令
                switch(args[0]){
                    case "on":
                        SwitchHandler(sender,true);
                        break;

                    case "off":
                        SwitchHandler(sender,false);
                        break;

                    case "reload":
                        if (!PermissionType.RELOAD.has(sender)){sender.sendMessage(OfflineWhitelist.NoPermissionMessage);return false;}
                        //重载配置文件
                        plugin.reloadConfig();
                        new ConfigManager().loadConfig();
                        sender.sendMessage("已重新加载配置文件和语言文件");
                        break;

                    case "list":
                        if (!PermissionType.LIST.has(sender)){sender.sendMessage(OfflineWhitelist.NoPermissionMessage);return false;}
                        if (PLAYER_WHITELIST.isEmpty()){
                            sender.sendMessage("白名单内没有玩家");
                            break;
                        }
                        sender.sendMessage(MessageFormat.format("白名单内共有 {0} 名玩家: {1}",PLAYER_WHITELIST.size(),PLAYER_WHITELIST));
                        break;

                    case "add","remove":
                        sender.sendMessage(ChatColor.RED+"请指定一个有效的玩家名");
                        break;

                    default:
                        sender.sendMessage(ChatColor.RED+"无效的子命令");
                        break;
                }
                return false;
            }

            if (args.length == 2){
                if (args[0].equalsIgnoreCase("add")){//添加一个玩家到白名单
                    if (!PermissionType.ADD.has(sender)){sender.sendMessage(OfflineWhitelist.NoPermissionMessage);return false;}
                    if (!PLAYER_WHITELIST.contains(args[1])){
                        PLAYER_WHITELIST.add(args[1]);
                        ConfigManager.saveConfig("Whitelist.list",PLAYER_WHITELIST);
                        sender.sendMessage(MessageFormat.format("已添加玩家 {0} 到白名单内",args[1]));
                        return false;
                    }
                    sender.sendMessage(ChatColor.RED+"此玩家已在白名单内");
                }

                if (args[0].equalsIgnoreCase("remove")){//删除一个玩家的白名单
                    if (!PermissionType.REMOVE.has(sender)){sender.sendMessage(OfflineWhitelist.NoPermissionMessage);return false;}
                    if (PLAYER_WHITELIST.contains(args[1])){
                        PLAYER_WHITELIST.remove(args[1]);
                        ConfigManager.saveConfig("Whitelist.list",PLAYER_WHITELIST);
                        sender.sendMessage(MessageFormat.format("已从白名单内删除玩家 {0}",args[1]));
                        return false;
                    }
                    sender.sendMessage(ChatColor.RED+"此玩家不在白名单内");
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        //玩家权限检查
        if (!PermissionType.TAB.has(sender))return null;
        if (args.length == 1){//返回命令帮助列表
            List<String> list = new ArrayList<>();
            list.add("on");
            list.add("off");
            list.add("add");
            list.add("remove");
            list.add("reload");
            list.add("list");
            return list;
        }
        if (args.length == 2){
            //尝试返回列表
            if (args[0].equalsIgnoreCase("add")){
                return getPlayerList();//返回在线的且没有白名单的玩家列表(原版如此)
            }else if (args[0].equalsIgnoreCase("remove")){
                return PLAYER_WHITELIST;
            }
        }
        return null;
    }

    /**
     * 返回在线的且没有白名单的玩家列表
     * @return 玩家列表
     */
    private static List<String> getPlayerList() {
        ArrayList<Player> OnlinePlayerList = new ArrayList<>(Bukkit.getOnlinePlayers());
        List<String> list = new ArrayList<>();
        for (Player player : OnlinePlayerList) {
            if (!PLAYER_WHITELIST.contains(player.getName())) {//检测玩家是否在白名单内，不在则把该玩家添加到要返回的List中
                list.add(player.getName());
            }
        }
        return list;
    }

    /**
     * 修改白名单状态
     * @param sender 执行命令的对象
     * @param targetState 为true代表尝试启用白名单，反之则是尝试关闭白名单
     */
    private void SwitchHandler(CommandSender sender, boolean targetState) {
        // 检查是否有权限
        if (!PermissionType.SWITCH.has(sender)){sender.sendMessage(OfflineWhitelist.NoPermissionMessage);return;}

        // 获取当前白名单状态
        boolean currentState = plugin.getConfig().getBoolean("Whitelist.proxy_mode");

        if (currentState == targetState) {
            sender.sendMessage(ChatColor.RED + "白名单已经" + (targetState ? "启用" : "关闭"));
            return;
        }

        // 修改白名单状态
        plugin.getConfig().set("Whitelist.proxy_mode", targetState);
        plugin.saveConfig();
        sender.sendMessage("已" + (targetState ? "启用" : "关闭") + "白名单");
    }
}
