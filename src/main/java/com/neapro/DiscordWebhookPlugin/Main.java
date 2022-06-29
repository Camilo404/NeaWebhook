package com.neapro.DiscordWebhookPlugin;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static String DsWHurl = "PasteHereYourDiscordWebhook";

    @Override
    public void onEnable(){
        getServer().getPluginManager().registerEvents(new EventListeners(getLogger()), this);

        DiscordWebhook webhook = new DiscordWebhook(DsWHurl);
        webhook.addEmbed(new DiscordWebhook.EmbedObject().setDescription("Server is starting!"));
        try {
            webhook.execute();
        }
        catch (java.io.IOException e) {
            getLogger().severe(e.getStackTrace().toString());
        }

    }

    @Override
    public void onDisable() {
        DiscordWebhook webhook = new DiscordWebhook(DsWHurl);
        webhook.addEmbed(new DiscordWebhook.EmbedObject().setDescription("Server is stopping..."));
        try {
            webhook.execute();
        } catch (java.io.IOException e) {
            getLogger().severe(e.getStackTrace().toString());
        }
    }
}
