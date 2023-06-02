package phoupraw.mcmod.createsdelight.block;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import com.simibubi.create.foundation.block.ITE;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import phoupraw.mcmod.createsdelight.block.entity.CakeOvenBE;
import phoupraw.mcmod.createsdelight.block.entity.PrintedCakeBE;
import phoupraw.mcmod.createsdelight.cake.CakeIngredient;
import phoupraw.mcmod.createsdelight.registry.CDBETypes;
import phoupraw.mcmod.createsdelight.registry.CDBlocks;
import phoupraw.mcmod.createsdelight.registry.CDCakeIngredients;

import java.util.Comparator;
import java.util.Objects;

public class CakeOvenBlock extends Block implements ITE<CakeOvenBE> {

public static final Comparator<BlockBox> BLOCK_BOX_COMPARATOR = Comparator
  .comparingInt(BlockBox::getMinY)
  .reversed()
  .thenComparingInt(BlockBox::getMinX)
  .thenComparingInt(BlockBox::getMinZ);
public CakeOvenBlock() {
    this(FabricBlockSettings.copyOf(Blocks.COPPER_BLOCK));
}

public CakeOvenBlock(Settings settings) {
    super(settings);
}

@Override
public Class<CakeOvenBE> getTileEntityClass() {
    return CakeOvenBE.class;
}

@Override
public BlockEntityType<? extends CakeOvenBE> getTileEntityType() {
    return CDBETypes.CAKE_OVEN;
}

@SuppressWarnings("deprecation")
@Override
public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
    super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
    neighborUpdate2(state, world, pos, sourceBlock, sourcePos, notify);
}

public static void neighborUpdate2(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
    var be = Objects.requireNonNull((CakeOvenBE) world.getBlockEntity(pos), world + " " + pos);
    if (!world.isReceivingRedstonePower(pos)) {
        if (be.powered) {
            be.powered = false;
        }
        return;
    }
    if (be.powered) return;
    be.powered = true;
    ListMultimap<CakeIngredient, BlockBox> content = MultimapBuilder.hashKeys().arrayListValues().build();
    final int len = 16;
    for (int i = 1; i <= len; i++) {
        for (int j = 1; j <= len; j++) {
            for (int k = 1; k <= len; k++) {
                BlockPos pos1 = pos.add(i, j, k);
                CakeIngredient cakeIngredient = CDCakeIngredients.BLOCK.find(world,pos1,null);
                if (cakeIngredient != null) {
                    content.put(cakeIngredient,new BlockBox(i - 1, j - 1, k - 1, i, j, k));
                }
            }
        }
    }
    if (content.isEmpty()) return;
    for (CakeIngredient key : content.keySet()) {
        content.get(key).sort(BLOCK_BOX_COMPARATOR);
    }
    BlockPos pos1 = pos.up(2);
    if (world.setBlockState(pos1, CDBlocks.PRINTED_CAKE.getDefaultState())) {
        PrintedCakeBE blockEntity = Objects.requireNonNull((PrintedCakeBE) world.getBlockEntity(pos1), content.toString());
        blockEntity.content = content;
        blockEntity.size = new Vec3i(len, len, len);
        blockEntity.sendData();
    }
}

}
