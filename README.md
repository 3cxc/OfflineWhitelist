# OfflineWhitelist
--------
[![Latest Release](https://img.shields.io/github/v/release/3cxc/OfflineWhitelist)](https://github.com/3cxc/OfflineWhitelist/release)
[![License](https://img.shields.io/github/license/3cxc/OfflineWhitelist.svg)](https://github.com/3cxc/OfflineWhitelist/blob/master/LICENSE)

> 一个解决了离线UUID问题的白名单插件
> 
> 该问题是由于离线生成UUID机制与正版生成UUID机制不同所引起的，在离线服务器上表现为从未加入服务器的玩家添加白名单后玩家仍然无法进入的问题。
>
> 通过简单粗暴的替代原版白名单系统，并只验证玩家的名称(而不验证UUID)解决了此问题。

## 可用命令
| 命令                | 用途            | 对应权限节点                   |
|-------------------|---------------|--------------------------| 
| /whitelist        | 显示可用子命令       | none                     |
| /whitelist on     | 启用白名单         | offline.whitelist.switch |
| /whitelist off    | 关闭白名单         | offline.whitelist.switch |
| /whitelist reload | 重载配置文件和语言文件   | offline.whitelist.reload |
| /whitelist list   | 列出白名单内的所有玩家   | offline.whitelist.list   |
| /whitelist add    | 向白名单内添加一个玩家   | offline.whitelist.add    |
| /whitelist remove | 删除一个在白名单内的玩家  | offline.whitelist.remove |

## 多版本兼容性

> [!WARNING]
> 插件只在1.18.2版本测试过,1.18.2+版本应该都是兼容的.
> 
> 对于1.18.2以下的版本,我没有进行测试,不知道是否兼容.(理论上可以)
>
> 该插件可能不支持Foila等服务端
> 
> 对于 1.21+ 服务端,您可能需要启用兼容模式以解决报错
> 
> 请参阅 此 [issue](https://github.com/3cxc/OfflineWhitelist/issues/2)

理论上,本插件支持 1.13-最新版本.

支持的服务端: Bukkit / Spigot / Paper

## 语言支持

> [!NOTE]
> 后续会将命令提示也弄到语言文件

多语言(请自行更改)

## 其他
本插件代码基于 MIT 协议开源

使用本插件代码不需要经过本插件作者同意，但需注明所使用到的代码的来源

本插件所用所有代码均为原创,不存在借用/抄袭等行为
