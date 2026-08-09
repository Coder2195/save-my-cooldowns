package dev.coder2195.save_my_cooldowns.mixin;

import com.mojang.authlib.GameProfile;
import dev.coder2195.save_my_cooldowns.SaveMyCooldowns;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {
  public ServerPlayerMixin(Level level, GameProfile gameProfile) {
    super(level, gameProfile);
  }

  @Inject(method = "readAdditionalSaveData", at = @At("HEAD"))
  private void readCooldowns(ValueInput input, CallbackInfo ci) {
    input.read("save_my_cooldowns:cooldowns", SaveMyCooldowns.COOLDOWN_CODEC).ifPresent(cooldowns -> {
      this.cooldowns.tickCount = cooldowns.tickCount;
      this.cooldowns.cooldowns.clear();
      this.cooldowns.cooldowns.putAll(cooldowns.cooldowns);
    });
  }

  @Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
  private void writeCooldowns(ValueOutput output, CallbackInfo ci) {
    output.store("save_my_cooldowns:cooldowns", SaveMyCooldowns.COOLDOWN_CODEC, this.cooldowns);
  }

}