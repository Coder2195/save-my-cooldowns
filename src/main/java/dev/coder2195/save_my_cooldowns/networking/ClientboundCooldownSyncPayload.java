package dev.coder2195.save_my_cooldowns.networking;

import dev.coder2195.save_my_cooldowns.SaveMyCooldowns;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemCooldowns;
import org.jspecify.annotations.NonNull;

public record ClientboundCooldownSyncPayload(ItemCooldowns cooldown) implements CustomPacketPayload {

  public static final CustomPacketPayload.@NonNull Type<ClientboundCooldownSyncPayload> TYPE = new CustomPacketPayload.Type<>(SaveMyCooldowns.id("cooldown_sync"));
  public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundCooldownSyncPayload> CODEC = StreamCodec.composite(
    SaveMyCooldowns.COOLDOWN_STREAM_CODEC, ClientboundCooldownSyncPayload::cooldown,
    ClientboundCooldownSyncPayload::new
  );

  @Override
  public @NonNull Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}
