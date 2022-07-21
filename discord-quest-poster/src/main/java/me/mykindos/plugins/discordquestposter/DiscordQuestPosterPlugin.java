package me.mykindos.plugins.discordquestposter;

import com.google.inject.Provides;
import com.openosrs.http.api.discord.DiscordClient;
import com.openosrs.http.api.discord.DiscordEmbed;
import com.openosrs.http.api.discord.DiscordMessage;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.ImageCaptured;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;

import net.runelite.client.plugins.screenshot.ScreenshotPlugin;
import org.pf4j.Extension;

import javax.inject.Inject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@PluginDependency(ScreenshotPlugin.class)
@PluginDescriptor(name = "Discord quest poster", enabledByDefault = false)
@Extension
@Slf4j
public class DiscordQuestPosterPlugin extends Plugin
{

    @Inject
    private DiscordQuestPosterConfig config;

    @Inject
    private ConfigManager configManager;

    @Provides
    DiscordQuestPosterConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(DiscordQuestPosterConfig.class);
    }

    @Inject
    private Client client;

    private static final Pattern questPattern = Pattern.compile("Quest\\((.*)\\)");

    @Subscribe
    public void onImageCaptured(ImageCaptured imageCaptured)
    {
        String imageName = imageCaptured.getFileName();
        Matcher matcher = questPattern.matcher(imageName);
        if (matcher.find())
        {
            String quest = matcher.group(1);

            DiscordClient discordClient = new DiscordClient();

            DiscordMessage discordMessage = DiscordMessage.builder()
                    .username(config.discordWebhookName())
                    .embed(DiscordEmbed.builder().title(client.getLocalPlayer().getName() + " completed " + quest).build())
                    .build();

            discordClient.message(config.discordWebhookUrl(), discordMessage);
        }
    }
}
