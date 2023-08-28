package phoupraw.mcmod.createsdelight.item;

import com.mojang.datafixers.util.Pair;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;
public class StatusEffectsBlockItem extends BlockItem {

    public static boolean apply(LivingEntity object, StatusEffectInstance effect) {
        StatusEffect type = effect.getEffectType();
        if (!type.isInstant()) return object.addStatusEffect(effect);
        int duration = effect.getDuration();
        int amplifier = effect.getAmplifier();
        for (int i = 0; i < duration; i++) type.applyInstantEffect(null, null, object, amplifier, 1);
        return true;
    }

    /**
     @see LivingEntity#spawnItemParticles(ItemStack, int)
     */
    public static void particle(Entity entity, ItemStack stack, int count) {
        World world = entity.getWorld();
        Random random = world.getRandom();
        for (int i = 0; i < count; ++i) {
            Vec3d vec3d = new Vec3d((random.nextDouble() - 0.5) * 0.1, random.nextDouble() * 0.1 + 0.1, 0.0);
            vec3d = vec3d.rotateX(-entity.getPitch() * (float) (Math.PI / 180.0));
            vec3d = vec3d.rotateY(-entity.getYaw() * (float) (Math.PI / 180.0));
            double d = -random.nextDouble() * 0.6 - 0.3;
            Vec3d vec3d2 = new Vec3d((random.nextDouble() - 0.5) * 0.3, d, 0.6);
            vec3d2 = vec3d2.rotateX(-entity.getPitch() * (float) (Math.PI / 180.0));
            vec3d2 = vec3d2.rotateY(-entity.getYaw() * (float) (Math.PI / 180.0));
            vec3d2 = vec3d2.add(entity.getX(), entity.getEyeY(), entity.getZ());
            world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, stack), vec3d2.x, vec3d2.y, vec3d2.z, vec3d.x, vec3d.y + 0.05, vec3d.z);
        }
    }

    public static void eat(World world, BlockPos blockPos, BlockState blockState, Vec3d hitPos, PlayerEntity player, int food, float saturationModifier, List<Pair<StatusEffectInstance, Float>> effects) {
        player.getHungerManager().add(food, saturationModifier);
        for (Pair<StatusEffectInstance, Float> pair : effects) {
            if (world.getRandom().nextFloat() < pair.getSecond()) {
                apply(player, pair.getFirst());
            }
        }
        Item item = blockState.getBlock().asItem();
        player.incrementStat(Stats.BROKEN.getOrCreateStat(item));
        player.emitGameEvent(GameEvent.EAT);
        Random random = world.random;
        player.playSound(item.getEatSound(), 0.5F + 0.5F * random.nextInt(2), (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        particle(player, item.getDefaultStack(), 10);
        for (int i = 0; i < 10; i++) {
            world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState), hitPos.getX() + (random.nextDouble() - 0.5) / 10, hitPos.getY() + (random.nextDouble() - 0.5) / 10, hitPos.getZ() + (random.nextDouble() - 0.5) / 10, (random.nextDouble() - 0.5) / 5, (random.nextDouble() - 0.5) / 5, (random.nextDouble() - 0.5) / 5);
        }
        if (player instanceof ServerPlayerEntity serverPlayer) {
            Criteria.CONSUME_ITEM.trigger(serverPlayer, item.getDefaultStack());
        }
    }

    public StatusEffectsBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    //@Override
    //public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
    //    return StatusEffectsItem.finishUsing(this, stack, world, user);
    //}

    //@Environment(EnvType.CLIENT)
    //@Override
    //public Optional<TooltipData> getTooltipData(ItemStack stack) {
    //    return StatusEffectsItem.getTooltipData(this, stack);
    //}

}
