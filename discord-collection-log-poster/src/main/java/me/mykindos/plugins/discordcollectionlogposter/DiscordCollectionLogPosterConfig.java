package me.mykindos.plugins.discordcollectionlogposter;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("discordcollectionlogposter")
public interface DiscordCollectionLogPosterConfig extends Config
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