package dev.coder2195.save_my_cooldowns;

//? if fabric {

/*import dev.coder2195.save_my_cooldowns.networking.ClientboundCooldownSyncPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class SaveMyCooldownsFabricClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    ClientPlayNetworking.registerGlobalReceiver(ClientboundCooldownSyncPayload.TYPE, (payload, context) -> {
      var player = context.client().player;
      if (player == null) {
        SaveMyCooldowns.LOGGER.info("Desync detected");
        return;
      }
      SaveMyCooldownsClient.handleSyncCooldowns(player, payload);
    });
  }
}
*///?}