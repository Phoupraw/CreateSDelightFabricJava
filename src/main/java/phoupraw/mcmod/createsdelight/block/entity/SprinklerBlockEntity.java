package phoupraw.mcmod.createsdelight.block.entity;

import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.contraptions.base.HalfShaftInstance;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.relays.belt.transport.TransportedItemStack;
import com.simibubi.create.foundation.item.SmartInventory;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.belt.BeltProcessingBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.belt.TransportedItemStackHandlerBehaviour;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.recipe.RecipeConditions;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import phoupraw.mcmod.createsdelight.recipe.SprinklingRecipe;
import phoupraw.mcmod.createsdelight.registry.CDBlockEntityTypes;
import phoupraw.mcmod.createsdelight.registry.CDRecipeTypes;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
public class SprinklerBlockEntity extends KineticTileEntity implements SidedStorageBlockEntity {
    public static @Nullable SprinklingRecipe filterRecipe(Collection<SprinklingRecipe> firstly, ItemStack held) {
        return firstly.stream().filter(r -> r.getIngredients().get(1).test(held)).findFirst().orElse(null);
    }

    public static class Instance extends HalfShaftInstance {

        public Instance(MaterialManager modelManager, KineticTileEntity tile) {
            super(modelManager, tile);
        }

        @Override
        protected Direction getShaftDirection() {
            return Direction.UP;
        }
    }

    public static final int DURATION = 10;
    private final SmartInventory inventory = new SmartInventory(1, this);
    public double prevAngle;
    public double nowAngle;
    public int elapsed = -1;
//    public boolean working;

    public SprinklerBlockEntity(BlockPos pos, BlockState state) {
        this(CDBlockEntityTypes.SPRINKLER, pos, state);
    }

    public SprinklerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<TileEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        behaviours.add(new BeltProcessingBehaviour(this).whenItemEnters(this::whenItemEnters).whileItemHeld(this::whileItemHeld));
    }

    @Override
    public @Nullable Storage<ItemVariant> getItemStorage(Direction side) {
        return inventory;
    }

    public BeltProcessingBehaviour.ProcessingResult whenItemEnters(TransportedItemStack beltHeld, TransportedItemStackHandlerBehaviour inventory) {
        var recipes = getCandidateRecipes(beltHeld.stack);
        if (getSpeed() == 0 || recipes.isEmpty()) return BeltProcessingBehaviour.ProcessingResult.PASS;
        var recipe = filterRecipe(recipes, getStack());
        if (recipe != null) {
            elapsed = 0;
            sendData();
        }
        return BeltProcessingBehaviour.ProcessingResult.HOLD;
    }

    public BeltProcessingBehaviour.ProcessingResult whileItemHeld(TransportedItemStack beltHeld, TransportedItemStackHandlerBehaviour inventory) {
        var recipes = getCandidateRecipes(beltHeld.stack);
        if (getSpeed() == 0 || recipes.isEmpty()) return BeltProcessingBehaviour.ProcessingResult.PASS;
        var recipe = filterRecipe(recipes, getStack());
        if (recipe == null) return BeltProcessingBehaviour.ProcessingResult.HOLD;
        if (elapsed == -1) {
            elapsed = 0;
            sendData();
        }
        if (elapsed % 5 == 0) {
            getWorld().playSound(null, getPos(), SoundEvents.BLOCK_SAND_BREAK, SoundCategory.BLOCKS, 0.1f, 1);
        }
        elapsed++;
        if (elapsed < DURATION) {
            return BeltProcessingBehaviour.ProcessingResult.HOLD;
        }
        elapsed = -1;
        beltHeld.stack.decrement(1);
        getStack().decrement(1);
        var result = beltHeld.copy();
        result.stack = recipe.getOutput().copy();
        inventory.handleProcessingOnItem(beltHeld, TransportedItemStackHandlerBehaviour.TransportedResult.convertToAndLeaveHeld(Collections.singletonList(result), beltHeld.copy()));
        return BeltProcessingBehaviour.ProcessingResult.HOLD;
    }

    @Override
    protected void write(NbtCompound tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        tag.put("item", getStack().writeNbt(new NbtCompound()));
//        if (clientPacket) {
//            tag.putBoolean("working", working);
//        } else {
        tag.putInt("elapsed", elapsed);
//        }
    }

    @Override
    protected void read(NbtCompound tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        setStack(ItemStack.fromNbt(tag.getCompound("item")));
//        if (clientPacket) {
//            working = tag.getBoolean("working");
//        } else {
        elapsed = tag.getInt("elapsed");
//        }
    }
    @Override
    public boolean addToGoggleTooltip(List<Text> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        if (getStack().isEmpty()) {
            Lang.builder().add(Text.translatable("empty")).forGoggles(tooltip);
        } else {
            Lang.itemName(getStack())
              .add(Lang.builder().space())
              .add(Lang.number(getStack().getCount()).style(Formatting.GOLD))
              .add(Lang.text(" / ").style(Formatting.GRAY))
              .add(Lang.number(getStack().getMaxCount()).style(Formatting.DARK_GRAY))
              .forGoggles(tooltip);
        }
        return true;
    }

    public ItemStack getStack() {
        return inventory.getStack(0);
    }

    public void setStack(ItemStack itemStack) {
        inventory.setStack(0, itemStack);
    }

    @NotNull
    @Override
    public World getWorld() {
        return Objects.requireNonNull(super.getWorld());
    }

    public @UnmodifiableView Collection<SprinklingRecipe> getCandidateRecipes(ItemStack beltItem) {
        return getWorld().getRecipeManager().listAllOfType(CDRecipeTypes.SPRINKLING.getRecipeType()).parallelStream().filter(RecipeConditions.firstIngredientMatches(beltItem)).toList();
    }

    public @Nullable SprinklingRecipe getRecipe(ItemStack beltItem) {
        return getCandidateRecipes(beltItem).stream().filter(r -> r.getIngredients().get(1).test(getStack())).findFirst().orElse(null);
    }

    @Override
    public void destroy() {
        super.destroy();
        ItemScatterer.spawn(getWorld(), getPos(), inventory);
    }

    @Override
    public void tick() {
        super.tick();
        if (getWorld().isClient()) {
            prevAngle = nowAngle;
            if (elapsed >= 0) {
                elapsed++;
                if (elapsed >= DURATION) {
                    elapsed = -1;
                }

                nowAngle += 0.05;
                if (nowAngle >= 4) {
                    prevAngle -= Math.PI * 2;
                    nowAngle -= Math.PI * 2;
                }
                var random = getWorld().getRandom();
                getWorld().addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, getStack()), getPos().getX() + 0.25 + random.nextDouble() / 2, getPos().getY(), getPos().getZ() + 0.25 + random.nextDouble() / 2, 0, 0, 0);
            }
        }
    }
}
