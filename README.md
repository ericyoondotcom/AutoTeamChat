# AutoTeamChat
Super simple Minecraft Bukkit plugin for switching to team chat mode: messages automatically get sent to team chat. Team chat quickly without typing /teammsg every single time!

View on [Bukkit](https://dev.bukkit.org/projects/automatic-team-chat-mode)<br />
View on [Spigot](https://www.spigotmc.org/resources/automatic-team-chat-mode.90829/)

---

Made by Eric ([yoonicode.com](http://yoonicode.com/?utm_source=github&utm_medium=web&utm_campaign=autoteamchat-github)) | IGN [i18n](https://namemc.com/profile/i18n.4)<br>
Open source on Github: [yummypasta/AutoTeamChat](https://github.com/yummypasta/AutoTeamChat)<br>

## Usage
1. Assign players to teams using the `/teams` built-in command
2. Players can switch to team chat using `/chat team` or `/chat t`. Any messages sent will now automatically be in team chat
    - **NOTE**: To be able to switch to team chat, players must have the `minecraft.command.teammsg` permission!
3. Players can switch back to public chat using `/chat all` or `/chat a`.

## Commands
- `/chat <all|team>`: switches to either ALL chat or TEAM chat.
- `/autoteamchat:reload`: reloads the plugin's config. This command sometimes doesn't work :/

## Configuration
Key | Type | Description | Default
--|--|--|--
`useBuiltInTeamMessages` | bool | If set to true, the plugin will send `/teammsg` commands on behalf of the player instead of using custom-formatted messages. For most purposes, enable this. | false
`teamMessageFormat` | string | The string format of team messages (ignored if `useBuiltInTeamMessages` is set to `true`). Use `&` before color codes. The following placeholders will be replaced with their corresponding values: `%color%` (color of the team), `%team%` (name of the team), `%playername%` (message sender's username), `%message%` (chat message contents) | `teamMessageFormat: -> %color%[%team%]&r <%color%%playername%&r> %message%` (this string emulates the default Minecraft team message format)

## Permissions
- `minecraft.command.teammsg`: giving players this built-in permission will also grant them the ability to use AutoTeamChat.
- `autoteamchat.config`: allows use of the `/reload` command.
