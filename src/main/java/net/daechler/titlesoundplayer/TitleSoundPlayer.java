package net.daechler.titlesoundplayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TitleSoundPlayer extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        // Registering this class as the command executor for /tsp
        this.getCommand("tsp").setExecutor(this);

        // Sending a green message on enable
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + getName() + " has been enabled!");
    }

    @Override
    public void onDisable() {
        // Sending a red message on disable
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + getName() + " has been disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Check if the command is /tsp and if it has correct number of arguments
        if (cmd.getName().equalsIgnoreCase("tsp") && args.length == 6) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Player not found!");
                return true;
            }

            ChatColor titleColor;
            try {
                titleColor = ChatColor.valueOf(args[1].toUpperCase());
            } catch (IllegalArgumentException e) {
                sender.sendMessage(ChatColor.RED + "Invalid color!");
                return true;
            }

            String title = args[3];

            // Check for bold formatting
            if(args[2].equalsIgnoreCase("YES")) {
                title = ChatColor.BOLD + title;
            }

            title = titleColor + title;

            Sound sound;
            try {
                sound = Sound.valueOf(args[4].toUpperCase());
            } catch (IllegalArgumentException e) {
                sender.sendMessage(ChatColor.RED + "Invalid sound!");
                return true;
            }

            float volume;
            try {
                volume = Math.min(1.0F, Float.parseFloat(args[5])); // Ensuring the volume does not exceed 1.0F
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Invalid volume!");
                return true;
            }

            // Play the sound to the target player
            target.playSound(target.getLocation(), sound, volume, 1);

            // Display the title to the target player
            target.sendTitle(title, "", 10, 70, 20);

            return true;
        }

        // Incorrect usage message
        sender.sendMessage(ChatColor.RED + "Usage: /tsp [TARGET PLAYER NAME] [COLOR OF TITLE] [FAT YES/NO] [TITLE] [SOUND PLAYING] [VOLUME]");
        return false;
    }
}