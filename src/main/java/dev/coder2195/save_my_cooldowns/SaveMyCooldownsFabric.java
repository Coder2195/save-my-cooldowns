package dev.coder2195.save_my_cooldowns;

//? if fabric {

/*import dev.coder2195.save_my_cooldowns.networking.ClientboundCooldownSyncPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class SaveMyCooldownsFabric implements ModInitializer {
  @Override
  public void onInitialize() {
    PayloadTypeRegistry.clientboundPlay().register(ClientboundCooldownSyncPayload.TYPE, ClientboundCooldownSyncPayload.CODEC);

    ServerPlayConnectionEvents.JOIN.register((listener, sender, server) -> {
      var player = listener.player;
      ServerPlayNetworking.send(listener.player, new ClientboundCooldownSyncPayload(player.getCooldowns()));
    });
  }
}
*///?}