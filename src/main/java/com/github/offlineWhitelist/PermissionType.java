package com.github.offlineWhitelist;

import org.bukkit.command.CommandSender;

public enum PermissionType {
    SWITCH("offline.whitelist.switch"),
    ADD("offline.whitelist.add"),
    REMOVE("offline.whitelist.remove"),
    RELOAD("offline.whitelist.reload"),
    LIST("offline.whitelist.list"),
    TAB("offline.whitelist.tab");

    private final String permissionNode;

    PermissionType(String permissionNode) {
        this.permissionNode = permissionNode;
    }

    // 检查发送者是否有权限
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean has(CommandSender sender) {
        return sender.hasPermission(permissionNode);
    }

}
