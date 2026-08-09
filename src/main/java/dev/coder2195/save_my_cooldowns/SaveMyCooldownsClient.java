package dev.coder2195.save_my_cooldowns;

import dev.coder2195.save_my_cooldowns.networking.ClientboundCooldownSyncPayload;
import net.minecraft.world.entity.player.Player;

public class SaveMyCooldownsClient {

  public static void handleSyncCooldowns(Player player, ClientboundCooldownSyncPayload payload) {
    var payloadCooldowns = payload.cooldown();
    var playerCooldowns = player.getCooldowns();
    playerCooldowns.tickCount = payloadCooldowns.tickCount;
    playerCooldowns.cooldowns.clear();
    playerCooldowns.cooldowns.putAll(payloadCooldowns.cooldowns);
  }
}
