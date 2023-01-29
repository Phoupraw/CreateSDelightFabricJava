package phoupraw.mcmod.createsdelight.registry;

import com.nhoryzon.mc.farmersdelight.entity.block.CuttingBoardBlockEntity;
import com.nhoryzon.mc.farmersdelight.entity.block.SkilletBlockEntity;
import com.nhoryzon.mc.farmersdelight.registry.BlocksRegistry;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPointType;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.DefaultArmInteractionPointType;
import phoupraw.mcmod.createsdelight.FarmersDelightWrappers;
import phoupraw.mcmod.createsdelight.StovePoint;

import java.util.Objects;
public final class MyArmInteractionPointTypes {
    public static final ArmInteractionPointType STOVE = new DefaultArmInteractionPointType(new Identifier(CreateSDelight.MOD_ID, "stove"), BlocksRegistry.STOVE.get()) {
        @Override
        public @NotNull ArmInteractionPoint createPoint(World level, BlockPos pos, BlockState state) {
            return new StovePoint(this, level, pos, state);
        }
    };
    public static final ArmInteractionPointType COOKING_POT = new DefaultArmInteractionPointType(new Identifier(CreateSDelight.MOD_ID, "cooking_pot"), BlocksRegistry.COOKING_POT.get());
    public static final ArmInteractionPointType BASKET = new DefaultArmInteractionPointType(new Identifier(CreateSDelight.MOD_ID, "basket"), BlocksRegistry.BASKET.get());
    public static final ArmInteractionPointType CUTTING_BOARD = new DefaultArmInteractionPointType(new Identifier(CreateSDelight.MOD_ID, "cutting_board"), BlocksRegistry.CUTTING_BOARD.get()) {
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
    public static final ArmInteractionPointType SKILLET = new DefaultArmInteractionPointType(new Identifier(CreateSDelight.MOD_ID, "skillet"), BlocksRegistry.SKILLET.get()) {
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
    static {
        for (ArmInteractionPointType type : new ArmInteractionPointType[]{STOVE,COOKING_POT,BASKET,CUTTING_BOARD,SKILLET}) {
            ArmInteractionPointType.register(type);
        }
    }
    private MyArmInteractionPointTypes() {}
}
