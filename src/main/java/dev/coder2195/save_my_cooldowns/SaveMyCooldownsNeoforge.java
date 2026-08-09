package dev.coder2195.save_my_cooldowns;

//? if neoforge {

import dev.coder2195.save_my_cooldowns.networking.ClientboundCooldownSyncPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;


@EventBusSubscriber(modid = SaveMyCooldowns.ID)
@Mod(SaveMyCooldowns.ID)
public class SaveMyCooldownsNeoforge {
  public SaveMyCooldownsNeoforge(IEventBus modEventBus, ModContainer modContainer) {
  }

  // In some common event class

  @SubscribeEvent // on the mod event bus
  public static void register(RegisterPayloadHandlersEvent event) {
    final PayloadRegistrar registrar = event.registrar("1");
    registrar.playToClient(
      ClientboundCooldownSyncPayload.TYPE, ClientboundCooldownSyncPayload.CODEC,
      (payload, context) -> {
        SaveMyCooldownsClient.handleSyncCooldowns(context.player(), payload);
      }
    );
  }


  @SubscribeEvent
  public static void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
    var player = event.getEntity();


    SaveMyCooldowns.LOGGER.info("Player login");
    if (player instanceof ServerPlayer serverPlayer) {
      SaveMyCooldowns.LOGGER.info("Player tracking");
      PacketDistributor.sendToPlayer(serverPlayer, new ClientboundCooldownSyncPayload(serverPlayer.getCooldowns()));
    }
  }
}
//?}