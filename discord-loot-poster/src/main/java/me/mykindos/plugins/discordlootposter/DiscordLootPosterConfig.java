package me.mykindos.plugins.discordlootposter;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("discordlootposter")
public interface DiscordLootPosterConfig extends Config
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

    @ConfigItem(
            keyName = "discordMinimumValue",
            name = "Discord minimum value",
            description = "Minimum value of an item to be posted to discord"
    )
    default int discordMinimumValue()
    {
        return 100000;
    }

}