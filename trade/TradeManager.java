package com.havoccore.trade;

import com.havoccore.HavocCore;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.UUID;

public class TradeManager {

    private final HavocCore plugin;
    // Tracks active trade requests (Target Player -> Sender Player)
    private final HashMap<UUID, UUID> incomingRequests = new HashMap<>();

    public TradeManager(HavocCore plugin) {
        this.plugin = plugin;
    }

    public void sendTradeRequest(Player sender, Player target) {
        incomingRequests.put(target.getUniqueId(), sender.getUniqueId());
        sender.sendMessage("§aTrade request sent to " + target.getName() + ".");
        target.sendMessage("§a" + sender.getName() + " sent you a trade request. Type §e/trade accept §ato open the secure trading menu.");
    }

    public void acceptTrade(Player target) {
        UUID senderUUID = incomingRequests.remove(target.getUniqueId());
        if (senderUUID == null) {
            target.sendMessage("§cYou do not have any pending trade requests.");
            return;
        }
        
        target.sendMessage("§aOpening secure trade interface...");
    }
}
