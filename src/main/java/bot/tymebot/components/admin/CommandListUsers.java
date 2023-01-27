package bot.tymebot.components.admin;

import bot.tymebot.core.UtilsUser;
import com.seailz.discordjv.command.annotation.SlashCommandInfo;
import com.seailz.discordjv.command.listeners.slash.SlashCommandListener;
import com.seailz.discordjv.events.model.interaction.command.SlashCommandInteractionEvent;

@SlashCommandInfo(name = "listusers", description = "Lists the amount of users the bot can see")
public class CommandListUsers extends SlashCommandListener {
    @Override
    public void onCommand(SlashCommandInteractionEvent slash) {
        String id = slash.getInteraction().member() == null ?
                slash.getInteraction().user().id() : slash.getInteraction()
                .member().user().id();

        if (!UtilsUser.isDev(id)) {
            return;
        }
        int users = slash.getBot().getUserCache().getCache().size();
        slash.reply("I am currently used by " + users + (users == 1 ? " user." : " users.")).setEphemeral(true).run();
    }
}
