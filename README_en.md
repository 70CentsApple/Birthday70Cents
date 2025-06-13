<div align="center">

<img alt="LOGO" src="./images/icon.png" width="200" height="200" />

# 🎂 Birthday70Cents
</div>

[>>Simplified Chinese<<](README.md)

> 🎁 A Minecraft 1.21.4 plugin for birthday gifts by [70CentsApple](https://github.com/70CentsApple).

**Birthday70Cents** is a simple and fun plugin that allows players to set their birthday, receive birthday gifts, and for administrators to manage those gifts efficiently.

- 🌐 Supports [Folia](https://papermc.io/software/folia) and [Paper](https://papermc.io/) 1.21.4
- 🎁 Fully configurable birthday present system
- 👥 Permissions-based player control
- 📦 Able to define action chains for present unwrapping, such as executing commands or giving items

---

## 🛡️ Commands & Permissions

| Command                              | Description (* = admin by default)           | Permission                |
|--------------------------------------|----------------------------------------------|---------------------------|
| `/birthday70cents`                   | Base command (aliases: `/birthday`, `/bday`) | `birthday.basic`          |
| `/birthday help`                     | Show command help                            | `birthday.basic.help`     |
| `/birthday set <MM-dd>`              | Set your birthday (format: MM-dd)            | `birthday.basic.set`      |
| `/birthday withdraw`                 | Claim your birthday present                  | `birthday.basic.withdraw` |
| `/birthday edit`                     | * Edit birthday gift items and action chains | `birthday.admin.edit`     |
| `/birthday get`                      | * Get sample birthday present                | `birthday.admin.get`      | 
| `/birthday query <player>`           | * Query player’s birthday info               | `birthday.admin.query`    |
| `/birthday modify <player> <reset?>` | * Modify player birthday info                | `birthday.admin.modify`   |
| `/birthday reload`                   | * Reload plugin configuration                | `birthday.admin.reload`   |

> 📌 You can fine-tune child permissions using LuckPerms (for example) as needed.

---

## 🧩 Configuration
The plugin's configuration file is located at `config.yml`.
The players' birthdays are stored in `birthday.yml`, which is automatically created in the plugin's folder.
You can edit this file to manage player birthdays, but it is recommended to use the provided commands for safety.

Example configuration:
```yaml
# Language, takes value: [en_us, zh_cn]
language: en_us

# If a player's birthday is 02-29:
# 0  - They could only withdraw their present at exactly 02-29.
# 1  - If this year is not a leap year, consider the user's birthday as 03-01.
# -1 - If this year is not a leap year, consider the user's birthday as 02-28.
adjust-leap-year: 0
```
