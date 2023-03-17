package phoupraw.mcmod.createsdelight.registry;

import com.nhoryzon.mc.farmersdelight.entity.block.CuttingBoardBlockEntity;
import com.nhoryzon.mc.farmersdelight.entity.block.SkilletBlockEntity;
import com.nhoryzon.mc.farmersdelight.entity.block.StoveBlockEntity;
import com.nhoryzon.mc.farmersdelight.registry.BlocksRegistry;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPointType;
import com.simibubi.create.foundation.item.ItemHelper;
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemHandlerHelper;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.api.FarmersDelightWrappers;
import phoupraw.mcmod.createsdelight.block.entity.SkewerPlateBlockEntity;

import java.util.Objects;
public final class MyArmPointTypes {
    public static final ArmInteractionPointType STOVE = new DefaultPointType(new Identifier(CreateSDelight.MOD_ID, "stove"), BlocksRegistry.STOVE.get()) {
        @Override
        public @NotNull ArmInteractionPoint createPoint(World level, BlockPos pos, BlockState state) {
            return new ArmInteractionPoint(this, level, pos, state) {
                private final Storage<ItemVariant> handler = FarmersDelightWrappers.storageOf((StoveBlockEntity) Objects.requireNonNull(level.getBlockEntity(pos)));

                @Override
                protected @NotNull Storage<ItemVariant> getHandler() {
                    return handler;
                }
            };
        }
    };
    public static final ArmInteractionPointType COOKING_POT = new DefaultPointType(new Identifier(CreateSDelight.MOD_ID, "cooking_pot"), BlocksRegistry.COOKING_POT.get());
    public static final ArmInteractionPointType BASKET = new DefaultPointType(new Identifier(CreateSDelight.MOD_ID, "basket"), BlocksRegistry.BASKET.get());
    public static final ArmInteractionPointType CUTTING_BOARD = new DefaultPointType(new Identifier(CreateSDelight.MOD_ID, "cutting_board"), BlocksRegistry.CUTTING_BOARD.get()) {
        @Override
        public @NotNull ArmInteractionPoint createPoint(World level, BlockPos pos, BlockState state) {
            return new ArmInteractionPoint(this, level, pos, state) {
                private final Storage<ItemVariant> handler = FarmersDelightWrappers.storageOf((CuttingBoardBlockEntity) Objects.requireNonNull(level.getBlockEntity(pos)));

                @Override
                public @NotNull Storage<ItemVariant> getHandler() {
                    return handler;
                }
            };
        }
    };
    /**
     @see FarmersDelightWrappers#SKILLET
     */
    public static final ArmInteractionPointType SKILLET;

    public static final ArmInteractionPointType PAN = new DefaultPointType(MyIdentifiers.PAN, MyBlocks.PAN);
    public static final ArmInteractionPointType GRILL = new DefaultPointType(MyIdentifiers.GRILL, MyBlocks.GRILL) /*{
        @Override
        public @NotNull ArmInteractionPoint createPoint(World level, BlockPos pos, BlockState state) {
            return new ArmInteractionPoint(this, level, pos, state) {
                @Override
                public ItemStack extract(int amount, TransactionContext ctx) {
                    ItemStack extracted;
                    try (var nested = ctx.openNested()) {
                        extracted = super.extract(amount, nested);
                        boolean flipped = extracted.getOrCreateNbt().getBoolean("flipped");
                        if (flipped) {
                            nested.commit();
                        } else {
                            nested.abort();
                            ctx.addOuterCloseCallback(result -> {
                                TileEntityBehaviour.get(getLevel(), getPos(), BlockingTransportedBehaviour.TYPE).getStorage().getStack().getOrCreateNbt().putBoolean("flipped", true);
                            });
                        }
                    }
                    return extracted;
                }
            };
        }
    }*/;
    public static final ArmInteractionPointType SKEWER_PLATE = new DefaultPointType(MyIdentifiers.SKEWER_PLATE, MyBlocks.SKEWER_PLATE) {
        @Override
        public @NotNull ArmInteractionPoint createPoint(World level, BlockPos pos, BlockState state) {
            return new ArmInteractionPoint(this, level, pos, state) {
                private SkewerPlateBlockEntity skewerPlate;

                @Override
                public ItemStack insert(ItemStack stack, TransactionContext ctx) {
                    long inserted = getSkewerPlate().skewerInventory.insert(ItemVariant.of(stack), stack.getCount(), ctx);
                    return ItemHandlerHelper.copyStackWithSize(stack, ItemHelper.truncateLong(stack.getCount() - inserted));
                }

                @Override
                public ItemStack extract(int amount, TransactionContext ctx) {
                    return TransferUtil.extractAnyItem(getSkewerPlate().plateInventory, amount);
                }

                @Contract(mutates = "this")
                public @NotNull SkewerPlateBlockEntity getSkewerPlate() {
                    if (skewerPlate == null) {
                        setSkewerPlate((SkewerPlateBlockEntity) Objects.requireNonNull(getLevel().getBlockEntity(getPos())));
                    }
                    return skewerPlate;
                }

                @Contract(mutates = "this")
                public void setSkewerPlate(@NotNull SkewerPlateBlockEntity skewerPlate) {
                    this.skewerPlate = skewerPlate;
                }
            };
        }
    };
    static {
        if (FarmersDelightWrappers.SKILLET) {
            SKILLET = new DefaultPointType(new Identifier(CreateSDelight.MOD_ID, "skillet"), BlocksRegistry.SKILLET.get()) {
                @Override
                public @NotNull ArmInteractionPoint createPoint(World level, BlockPos pos, BlockState state) {
                    return new ArmInteractionPoint(this, level, pos, state) {
                        private final Storage<ItemVariant> handler = FarmersDelightWrappers.storageOf((SkilletBlockEntity) Objects.requireNonNull(level.getBlockEntity(pos)));

                        @Override
                        public @NotNull Storage<ItemVariant> getHandler() {
                            return handler;
                        }
                    };
                }
            };
            ArmInteractionPointType.register(SKILLET);
        } else {
            SKILLET = null;
        }
        for (ArmInteractionPointType type : new ArmInteractionPointType[]{STOVE, COOKING_POT, BASKET, CUTTING_BOARD, PAN, GRILL, SKEWER_PLATE}) {
            ArmInteractionPointType.register(type);
        }
    }
    private MyArmPointTypes() {}

    /**
     只要{@link #canCreatePoint(World, BlockPos, BlockState)}的{@code state}符合{@link #getBlock()}，就可以创建，创建的是{@link ArmInteractionPoint}
     @see MyArmPointTypes
     */
    public static class DefaultPointType extends ArmInteractionPointType {
        private final Block block;

        /**
         @param id {@link ArmInteractionPointType#ArmInteractionPointType(Identifier)}
         @param block {@link #getBlock()}
         */
        public DefaultPointType(Identifier id, Block block) {
            super(id);
            this.block = block;
        }

        @Override
        public boolean canCreatePoint(World level, BlockPos pos, BlockState state) {
            return state.isOf(getBlock());
        }

        @Override
        public @NotNull ArmInteractionPoint createPoint(World level, BlockPos pos, BlockState state) {
            return new ArmInteractionPoint(this, level, pos, state);
        }

        public Block getBlock() {
            return block;
        }
    }
}
