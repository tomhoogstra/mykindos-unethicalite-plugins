package me.mykindos.plugins.discordquestposter;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("discordlootposter")
public interface DiscordQuestPosterConfig extends Config
{

    @ConfigItem(
            keyName = "discordWebhookUrl",
            name = "Discord Webhook URL",
            description = "Webhook to submit received loot to"
    )
    default String discordWebhookUrl()
    {
        return "";
    }

    @ConfigItem(
            keyName = "discordWebhookName",
            name = "Discord Webhook Name",
            description = "Name of the webhook"
    )
    default String discordWebhookName()
    {
        return "Unethicalite";
    }

}