package com.neapro.DiscordWebhookPlugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.awt.*;
import java.util.logging.Logger;
import static com.neapro.DiscordWebhookPlugin.Main.*;

public class EventListeners implements Listener {

    private Logger logger;

    public EventListeners(Logger Logger){
        this.logger = logger;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        int playerCount = Bukkit.getOnlinePlayers().size();

        DiscordWebhook webhook = new DiscordWebhook(DsWHurl);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setDescription("**" + player.getName() + "** has joined the server! (" + playerCount + "/" + Bukkit.getMaxPlayers() + ")")
                .setAuthor(player.getName(), "", "https://mc-heads.net/avatar/" + player.getName())
                .setColor(new Color( 66, 135, 245))
        );
        try {
            webhook.execute();
        }
        catch (java.io.IOException e) {
            logger.severe(e.getStackTrace().toString());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        int playerCount = Bukkit.getOnlinePlayers().size() - 1;

        DiscordWebhook webhook = new DiscordWebhook(DsWHurl);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setDescription("**" + player.getName() + "** has left the server... (" + playerCount + "/" + Bukkit.getMaxPlayers() + ")")
                .setAuthor(player.getName(), "", "https://mc-heads.net/avatar/" + player.getName())
                .setColor(new Color( 245, 81, 81))
        );
        try {
            webhook.execute();
        }
        catch (java.io.IOException e) {
            logger.severe(e.getStackTrace().toString());
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerChat(AsyncPlayerChatEvent event){
        String eventMessage = event.getMessage();
        Player player = event.getPlayer();

        DiscordWebhook webhook = new DiscordWebhook(DsWHurl);
        eventMessage = eventMessage.replaceAll("@everyone", "`@everyone`").replaceAll("@here", "`@here`");
        webhook.setContent("**" + player.getName() + "**: " + eventMessage);
        try {
            webhook.execute();
        }
        catch (java.io.IOException e) {
            logger.severe(e.getStackTrace().toString());
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onCommand(PlayerCommandPreprocessEvent event){
        String commandMessage = event.getMessage();
        Player player = event.getPlayer();
        DiscordWebhook webhook = new DiscordWebhook(DsWHurl);
        webhook.setContent("**" + player.getName() + "**: " + commandMessage);
        try {
            webhook.execute();
        }
        catch (java.io.IOException e) {
            logger.severe(e.getStackTrace().toString());
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        DiscordWebhook webhook = new DiscordWebhook(DsWHurl);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setDescription(event.getDeathMessage())
                .setAuthor(player.getName(), "", "")
        );
        try {
            webhook.execute();
        }
        catch (java.io.IOException e) {
            logger.severe(e.getStackTrace().toString());
        }
    }
}
