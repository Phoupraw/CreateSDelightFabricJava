package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.content.contraptions.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.contraptions.relays.belt.transport.TransportedItemStack;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.belt.DirectBeltInputBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.fluid.SmartFluidTankBehaviour;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.api.HeatSources;
import phoupraw.mcmod.createsdelight.behaviour.*;
import phoupraw.mcmod.createsdelight.block.CopperTunnelBlock;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
import phoupraw.mcmod.createsdelight.registry.MyBlocks;
import phoupraw.mcmod.createsdelight.registry.MyFluids;

import java.util.List;
import java.util.Objects;
public class SmartDrainBlockEntity extends SmartTileEntity implements SidedStorageBlockEntity, IHaveGoggleInformation {
    public SmartDrainBlockEntity(BlockPos pos, BlockState state) {this(MyBlockEntityTypes.SMART_DRAIN, pos, state);}

    public SmartDrainBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<TileEntityBehaviour> behaviours) {
        var rb = new RollingItemBehaviour(this) {
            @Override
            public ItemStack apply(TransportedItemStack transp, Direction side, boolean simulate) {
                if (side.getAxis().isHorizontal()) {
                    var stateAbove = getWorld().getBlockState(getPos().up());
                    if (stateAbove.isOf(MyBlocks.COPPER_TUNNEL) && !stateAbove.get(CopperTunnelBlock.Model.HORIZONTALS.get(side.getOpposite())).isOpen()) {return transp.stack;}
                }
                return super.apply(transp, side, simulate);
            }

            @Override
            public void afterInsert(Direction insertedFrom) {
                super.afterInsert(insertedFrom);
                if (insertedFrom.getAxis().isVertical()) return;
                var stateAbove = getWorld().getBlockState(getPos().up());
                if (stateAbove.isOf(MyBlocks.COPPER_TUNNEL) && stateAbove.get(CopperTunnelBlock.Model.HORIZONTALS.get(insertedFrom.getOpposite())) == CopperTunnelBlock.Model.CURTAIN) {
                    getWorld().getBlockEntity(getPos().up(), MyBlockEntityTypes.COPPER_TUNNEL).orElseThrow().flap(insertedFrom.getOpposite(), true);
                }
            }

            @Override
            public boolean output(Direction insertedFrom, boolean throwable, boolean simulate) {
                if (insertedFrom.getAxis().isHorizontal()) {
                    var stateAbove = getWorld().getBlockState(getPos().up());
                    if (stateAbove.isOf(MyBlocks.COPPER_TUNNEL)) {
                        if (!stateAbove.get(CopperTunnelBlock.Model.HORIZONTALS.get(insertedFrom)).isOpen()) {
                            return false;
                        } else if (super.output(insertedFrom, throwable, simulate)) {
                            if (!simulate) {
                                getWorld().getBlockEntity(getPos().up(), MyBlockEntityTypes.COPPER_TUNNEL).orElseThrow().flap(insertedFrom, false);
                            }
                            return true;
                        }
                    }
                }
                return super.output(insertedFrom, throwable, simulate);
            }
        };
        behaviours.add(rb);
        behaviours.add(new DirectBeltInputBehaviour(this).setInsertionHandler(rb).allowingBeltFunnels());
        var tb = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.TYPE, this, 1, FluidConstants.BUCKET * 2, false);
        behaviours.add(tb);
        behaviours.add(new TDepotItemBehaviour(this));
        var bb = new BurnerBehaviour(this) {
            @Override
            public void onIgnite() {
                super.onIgnite();
                getWorld().setBlockState(getPos(), getCachedState().with(Properties.LIT, true));
            }

            @Override
            public void onExtinguish() {
                super.onExtinguish();
                getWorld().setBlockState(getPos(), getCachedState().with(Properties.LIT, false));
            }
        };
        behaviours.add(bb);
        behaviours.add(new EmptyingBehaviour(this));
        tb.whenFluidUpdates(() -> {
            if (bb.getFuelTicks() >= 0) {
                var fluid = tb.getPrimaryHandler().getResource();
                if (!fluid.isBlank() && !fluid.isOf(MyFluids.SUNFLOWER_OIL)) {
                    bb.setFuelTicks(-1);
                    getWorld().playSound(null, getPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1, 1);
                }
            }
        });
        var gb = new GrillerBehaviour(this);
        behaviours.add(gb);
        rb.continueRoll.register(() -> gb.getHeat() < 1 || gb.getTicksS().get(0) < 0);
        var sb = new SteamerBehaviour(this) {
            @Override
            public boolean isSteamable() {//计算连通实在是太复杂了，先不弄
//                var stateAbove = getWorld().getBlockState(getPos().up());
//                if (!stateAbove.isOf(MyBlocks.COPPER_TUNNEL)) return false;
//                for (EnumProperty<CopperTunnelBlock.Model> property : CopperTunnelBlock.Model.HORIZONTALS.values()) {
//                    if(stateAbove.isof)
//                }
                return super.isSteamable();
            }
        };
        behaviours.add(sb);
        rb.continueRoll.register(() -> sb.getHeat() < 1 || sb.getTicksS().get(0) < 0);
//        behaviours.add(new PanFrierBehaviour(this){
//            @Override
//            public Storage<ItemVariant> getItemS() {
//                return getRolling().extraction;
//            }
//        });
    }

    @Override
    public void tick() {
        super.tick();

    }

    @NotNull
    @Override
    public World getWorld() {
        return Objects.requireNonNull(super.getWorld());
    }

    @Override
    public @Nullable Storage<ItemVariant> getItemStorage(@Nullable Direction side) {
        if (side == Direction.UP) return getRolling().get(side);
        if (side == null) side = Direction.DOWN;
        return getBehaviour(DepotItemBehaviour.TYPE).get(side);
    }

    @Override
    public @Nullable Storage<FluidVariant> getFluidStorage(@Nullable Direction side) {
        return side == Direction.UP ? null : getTank().getCapability();
    }

    @Override
    public boolean addToGoggleTooltip(List<Text> tooltip, boolean isPlayerSneaking) {
        return IHaveGoggleInformation.super.containedFluidTooltip(tooltip, isPlayerSneaking, getTank().getCapability()) | getBurner().addToGoggleTooltip(tooltip, isPlayerSneaking);
    }

    public RollingItemBehaviour getRolling() {
        return getBehaviour(RollingItemBehaviour.TYPE);
    }

    public SmartFluidTankBehaviour getTank() {
        return getBehaviour(SmartFluidTankBehaviour.TYPE);
    }

    public BurnerBehaviour getBurner() {
        return getBehaviour(BurnerBehaviour.TYPE);
    }

    /**
     * @see HeatSources
     */
    public @Nullable Double getSelfHeat(@Nullable Direction side) {
        if (side != Direction.UP && side != null) return null;
        if (FluidVariantAttributes.getTemperature(getTank().getPrimaryHandler().getResource()) >= 400) return 1.0;
        if (getBurner().getFuelTicks() > 0) return 1.0;
        return null;
    }

    public static class TDepotItemBehaviour extends DepotItemBehaviour {
        public TDepotItemBehaviour(SmartTileEntity te) {
            super(te);
        }

        @Override
        public Storage<ItemVariant> newInsertionStorage(Direction side) {
            return new TInsertionStorage(side);
        }

        public class TInsertionStorage extends InsertionStorage {
            public TInsertionStorage(Direction side) {
                super(side);
            }

            @Override
            public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
//                var recipeS = CacheCollections.top(getWorld().getRecipeManager().listAllOfType(MyRecipeTypes.PAN_FRYING.getRecipeType()), r -> r.getIngredients().get(0).test(resource.toStack()), 1);
//                if (!recipeS.isEmpty()) {
////                    if (!getMain())
//maxAmount = Math.min(maxAmount,1);
//                }
                return super.insert(resource, maxAmount, transaction);
            }
        }
    }
}
