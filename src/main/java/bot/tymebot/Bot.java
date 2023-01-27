package bot.tymebot;

import bot.tymebot.components.admin.CommandListGuilds;
import bot.tymebot.components.admin.CommandListUsers;
import bot.tymebot.components.misc.CommandInfo;
import bot.tymebot.config.TymeConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.seailz.discordjv.DiscordJv;
import com.seailz.discordjv.model.application.Intent;
import com.seailz.discordjv.model.status.Status;
import com.seailz.discordjv.model.status.StatusType;
import com.seailz.discordjv.model.status.activity.Activity;
import com.seailz.discordjv.model.status.activity.ActivityType;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.EnumSet;

@Getter
public class Bot {

    @Getter
    private static final String[] devIds = new String[]{"462296411141177364", "947691195658797167"};

    @Getter
    private static Bot instance;
    private final DiscordJv djv;
    private TymeConfig config = null;

    @Getter
    private static final long startTime = System.currentTimeMillis();

    @SneakyThrows
    public Bot() {
        instance = this;

        // Config loader
        File file = new File("config", "main.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        if (!file.exists()) {
            file.getParentFile().mkdir();
            file.createNewFile();

            this.config = new TymeConfig();
            saveConfig(file, gson);
        }

        try (FileReader reader = new FileReader(file)) {
            config = gson.fromJson(reader, TymeConfig.class);
            saveConfig(file, gson);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Bot builder
        DiscordJv djv = new DiscordJv(config.getBotToken(), EnumSet.of(
                Intent.GUILD_MEMBERS, Intent.MESSAGE_CONTENT, Intent.GUILD_MESSAGES, Intent.DIRECT_MESSAGES, Intent.GUILD_PRESENCES
        ));

        djv.setStatus(new Status(0, new Activity[]{
                new Activity("over the server!", ActivityType.WATCHING)
        }, StatusType.ONLINE, false));

        djv.registerCommands(new CommandListGuilds());
        djv.registerCommands(new CommandInfo());
        djv.registerCommands(new CommandListUsers());
        this.djv = djv;
    }

    @SneakyThrows
    private void saveConfig(File file, Gson gson) {
        Writer writer = new FileWriter(file, false);
        gson.toJson(this.config, writer);
        writer.flush();
        writer.close();
    }

    public void reloadConfig() {
        File file = new File("config", "main.json");
        // Should not be possible but whatever.
        if (!file.exists())
            return;

        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        try (FileReader reader = new FileReader(file)) {
            config = gson.fromJson(reader, TymeConfig.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
