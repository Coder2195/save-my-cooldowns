package dev.coder2195.save_my_cooldowns;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemCooldowns;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class SaveMyCooldowns {
  /**
   * This logger is used to write text to the console and the log file.
   * It is considered best practice to use your mod id as the logger's name.
   * That way, it's clear which mod wrote info, warnings, and errors.
   */
  public static final Logger LOGGER = LoggerFactory.getLogger("save_my_cooldowns");
  public static final String VERSION = /*$ mod_version*/ "0.1.0";
  public static final String MINECRAFT = /*$ minecraft*/ "26.2";
  public static final String ID = "save_my_cooldowns";

  /**
   * Adapts to the {@link Identifier} changes introduced in 1.21.
   */
  public static Identifier id(String namespace, String path) {
    //? if <1.21 {
    /*return new Identifier(namespace, path);
     *///?} else
    return Identifier.fromNamespaceAndPath(namespace, path);
  }

  public static Codec<ItemCooldowns.CooldownInstance> COOLDOWN_INSTANCE_CODEC = RecordCodecBuilder.create(instance -> instance.group(
    Codec.INT.fieldOf("start_time").forGetter(ItemCooldowns.CooldownInstance::startTime),
    Codec.INT.fieldOf("end_time").forGetter(ItemCooldowns.CooldownInstance::endTime)
  ).apply(instance, ItemCooldowns.CooldownInstance::new));

  public static Codec<ItemCooldowns> COOLDOWN_CODEC = RecordCodecBuilder.create(instance -> instance.group(
    Codec.INT.fieldOf("tick_count").forGetter((ItemCooldowns i) -> i.tickCount),
    Codec.unboundedMap(Identifier.CODEC, COOLDOWN_INSTANCE_CODEC).fieldOf("cooldowns").forGetter((ItemCooldowns i) -> i.cooldowns)
  ).apply(instance, (tickCount, cooldowns) -> {
    var newCooldowns = new ItemCooldowns();
    newCooldowns.tickCount = tickCount;
    newCooldowns.cooldowns = cooldowns;
    return newCooldowns;
  }));

  public static StreamCodec<RegistryFriendlyByteBuf, ItemCooldowns.CooldownInstance> COOLDOWN_INSTANCE_STREAM_CODEC = StreamCodec.composite(
    ByteBufCodecs.INT, ItemCooldowns.CooldownInstance::startTime,
    ByteBufCodecs.INT, ItemCooldowns.CooldownInstance::endTime,
    ItemCooldowns.CooldownInstance::new
  );

  public static StreamCodec<RegistryFriendlyByteBuf, ItemCooldowns> COOLDOWN_STREAM_CODEC = StreamCodec.composite(
    ByteBufCodecs.map(HashMap::new, Identifier.STREAM_CODEC, COOLDOWN_INSTANCE_STREAM_CODEC), (ItemCooldowns cooldowns) -> cooldowns.cooldowns,
    ByteBufCodecs.INT, (ItemCooldowns cooldowns) -> cooldowns.tickCount,
    (map, tickCount) -> {
      var newCooldowns = new ItemCooldowns();
      newCooldowns.cooldowns = map;
      newCooldowns.tickCount = tickCount;
      return newCooldowns;
    }
  );

  public static Identifier id(String cooldownSync) {
    return Identifier.fromNamespaceAndPath(ID, cooldownSync);
  }
}
