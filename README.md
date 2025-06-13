# 🎂 Birthday70Cents

> 🎁 一款适用于 Minecraft 1.21.4 的生日礼物插件。

**Birthday70Cents** 是一个简单有趣的插件，允许玩家设置生日、领取生日礼物，管理员可以高效管理。

- 🌐 支持 [Folia](https://papermc.io/software/folia) 与 [Paper](https://papermc.io/) 1.21.4
- 🎁 完全可配置的生日礼物系统
- 👥 基于权限的玩家控制
- 📦 可以定义礼物拆包后的执行命令、给予物品等动作链

---

## 🛡️ 命令与权限

| 命令                                   | 说明（* 表示默认仅管理员）               | 权限                        |
|--------------------------------------|------------------------------|---------------------------|
| `/birthday70cents`                   | 基础命令（别名：`/birthday`、`/bday`） | `birthday.basic`          |
| `/birthday help`                     | 显示帮助菜单                       | `birthday.basic.help`     |
| `/birthday set <MM-dd>`              | 设置你的生日（格式：MM-dd）             | `birthday.basic.set`      |
| `/birthday withdraw`                 | 领取你的生日礼物                     | `birthday.basic.withdraw` |
| `/birthday edit`                     | * 编辑生日礼物物品及动作链               | `birthday.admin.edit`     |
| `/birthday get`                      | * 获取测试生日礼物                   | `birthday.admin.get`      |
| `/birthday query <player>`           | * 查询玩家的生日信息                  | `birthday.admin.query`    |
| `/birthday modify <player> <reset?>` | * 修改玩家的生日信息                  | `birthday.admin.modify`   |
| `/birthday reload`                   | * 重载插件配置                     | `birthday.admin.reload`   |

> 📌 你可以使用 LuckPerms 等权限插件，根据需要调整权限。

---

## 🧩 配置

插件的主配置文件位于 `config.yml`。  
玩家的生日信息存储在 `birthday.yml`，该文件会在插件首次运行时自动生成。  
你可以直接编辑文件来管理玩家生日，但建议优先使用命令以确保安全。

示例配置：

```yaml
# 语言，取值：[en_us, zh_cn]
language: en_us

# 如果玩家生日是 02-29：
# 0  - 仅在闰年的 02-29 可领取礼物。
# 1  - 若当年非闰年，则视为 03-01。
# -1 - 若当年非闰年，则视为 02-28。
adjust-leap-year: 0
```
