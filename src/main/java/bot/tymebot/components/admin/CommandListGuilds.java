package bot.tymebot.components.admin;

import bot.tymebot.core.UtilsUser;
import com.seailz.discordjv.command.annotation.SlashCommandInfo;
import com.seailz.discordjv.command.listeners.slash.SlashCommandListener;
import com.seailz.discordjv.events.model.interaction.command.SlashCommandInteractionEvent;

@SlashCommandInfo(name = "listguilds", description = "Lists the amount of guilds the bot is in")
public class CommandListGuilds extends SlashCommandListener {

    @Override
    public void onCommand(SlashCommandInteractionEvent slash) {
        String id = slash.getInteraction().member() == null ?
                slash.getInteraction().user().id() : slash.getInteraction()
                .member().user().id();

        if (!UtilsUser.isDev(id)) {
            slash.reply("This command is limited.").setEphemeral(true).run();
        }
        int guilds = slash.getBot().getGuildCache().getCache().size();
        slash.reply("I am in " + guilds + (guilds == 1 ? " guild." : " guilds.")).setEphemeral(true).run();
    }
}
