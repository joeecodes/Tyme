package bot.tymebot.components.misc;

import bot.tymebot.core.Utils;
import com.seailz.discordjv.command.annotation.SlashCommandInfo;
import com.seailz.discordjv.command.listeners.slash.SlashCommandListener;
import com.seailz.discordjv.events.model.interaction.command.SlashCommandInteractionEvent;
import com.seailz.discordjv.model.embed.Embeder;

import java.awt.*;

@SlashCommandInfo(name = "botinfo", description = "Shows information about the bot")
public class CommandInfo extends SlashCommandListener {
    @Override
    public void onCommand(SlashCommandInteractionEvent slash) {
        Embeder eb = Embeder.e();
        eb.title("Tyme Bot Info");
        eb.color(Color.decode("#2f3136"));
        eb.description("""
                This bot is made by the Discord.jar Team!
                Uptime: `%uptime%`
                Servers: `%totalguilds%`
                """.replace("%uptime%", Utils.getUptime()).replace("%totalguilds%", String.valueOf(slash.getBot().getGuildCache().getCache().size())));
        eb.thumbnail(slash.getBot().getSelfUser().imageUrl());
        slash.replyWithEmbeds(eb).run();
    }
}
