package me.mykindos.plugins.discordlootposter;

import com.google.inject.Provides;
import com.openosrs.http.api.discord.DiscordClient;
import com.openosrs.http.api.discord.DiscordEmbed;
import com.openosrs.http.api.discord.DiscordMessage;
import com.openosrs.http.api.discord.embed.ThumbnailEmbed;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.ItemComposition;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.ItemStack;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.loottracker.LootReceived;
import net.runelite.client.plugins.loottracker.LootTrackerPlugin;
import org.pf4j.Extension;

import javax.inject.Inject;
import java.text.DecimalFormat;
import java.util.Collection;

@PluginDependency(LootTrackerPlugin.class)
@PluginDescriptor(name = "Discord loot poster", enabledByDefault = false)
@Extension
@Slf4j
public class DiscordLootPosterPlugin extends Plugin
{

    @Inject
    private DiscordLootPosterConfig config;

    @Inject
    private ConfigManager configManager;

    @Provides
    DiscordLootPosterConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(DiscordLootPosterConfig.class);
    }

    @Inject
    private ItemManager itemManager;

    @Inject
    private Client client;

    @Subscribe
    public void onLootReceived(LootReceived lootReceived)
    {
        processLoot(lootReceived.getName(), lootReceived.getItems());
    }

    private String getItemImageURL(int itemId)
    {
        return "https://static.runelite.net/cache/item/icon/" + itemId + ".png";
    }

    private static final DecimalFormat NUMBER_FORMAT = new DecimalFormat("#,###,###,###");

    private void processLoot(String name, Collection<ItemStack> items)
    {
        if (!config.discordWebhookUrl().equals(""))
        {

            DiscordClient discordClient = new DiscordClient();

            DiscordMessage.DiscordMessageBuilder discordMessageBuilder = DiscordMessage.builder()
                    .username(config.discordWebhookName())
                    .content(client.getLocalPlayer().getName() + " received from " + name + ":");


            long totalValue = 0;
            for (ItemStack item : items)
            {
                int itemId = item.getId();
                int qty = item.getQuantity();

                int price = itemManager.getItemPrice(itemId);
                long total = (long) price * qty;
                if (total >= config.discordMinimumValue())
                {
                    totalValue += total;

                    ItemComposition itemComposition = itemManager.getItemComposition(itemId);
                    DiscordEmbed embedObject = DiscordEmbed.builder()
                            .thumbnail(ThumbnailEmbed.builder().url(getItemImageURL(itemComposition.getId())).build())
                            .title(item.getQuantity() + " x " + itemComposition.getName())
                            .description("Price: " + NUMBER_FORMAT.format(total) + " gp")
                            .build();

                    discordMessageBuilder.embed(embedObject);

                }
            }

            if (config.discordMinimumValue() == 0 || totalValue >= config.discordMinimumValue())
            {
                discordClient.message(config.discordWebhookUrl(), discordMessageBuilder.build());
            }
        }

    }
}
