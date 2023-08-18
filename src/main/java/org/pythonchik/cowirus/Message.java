package org.pythonchik.cowirus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Message {

    private final CoWirus cowir;
    public Message (CoWirus cowir) {this.cowir = cowir;}
    public void send(CommandSender sender,String message) {
        sender.sendMessage(recreator(message));
    }
    public String recreator(String message) {
        return ChatColor.translateAlternateColorCodes('&',"&d[CoWirus]&f " + message);
    }
}
