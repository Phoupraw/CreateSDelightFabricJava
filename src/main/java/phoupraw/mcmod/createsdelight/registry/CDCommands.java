package phoupraw.mcmod.createsdelight.registry;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.simibubi.create.content.schematics.SchematicWorld;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.block.BlockState;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import phoupraw.mcmod.createsdelight.block.entity.PrintedCakeBlockEntity;
import phoupraw.mcmod.createsdelight.cake.CakeIngredient;
import phoupraw.mcmod.createsdelight.cake.VoxelCake;

import java.util.Map;

public final class CDCommands {

public static void register() {
    CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
        dispatcher.register(CommandManager.literal("createsdelight")
          .then(CommandManager.literal("cake2structure")
            .then(CommandManager.argument("pos", BlockPosArgumentType.blockPos())
              .executes(CDCommands::cake2structure))));
    });
}

//public static int cake2java(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
//    NbtHelper.fromNbtProviderString()
//}
public static int cake2structure(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
    BlockPos pos = BlockPosArgumentType.getBlockPos(context, "pos");
    if (context.getSource().getWorld().getBlockEntity(pos) instanceof PrintedCakeBlockEntity be) {
        if (be.predefined != null) {
            context.getSource().sendError(Text.of("此蛋糕已经是预定义的！"));
            return 0;
        }
        if (be.getVoxelCake() == null) {
            context.getSource().sendError(Text.of("此蛋糕没有内容！"));
            return 0;
        }
        VoxelCake voxelCake = be.getVoxelCake();
        SchematicWorld sw = new SchematicWorld(context.getSource().getWorld());
        for (Map.Entry<CakeIngredient, BlockBox> entry : voxelCake.getContent().entries()) {
            BlockState blockState = CSDCakeIngredients.BLOCK.inverse().get(entry.getKey()).getDefaultState();
            BlockBox box = entry.getValue();
            for (int i = box.getMinX(); i < box.getMaxX(); i++) {
                for (int j = box.getMinY(); j < box.getMaxY(); j++) {
                    for (int k = box.getMinZ(); k < box.getMaxZ(); k++) {
                        sw.setBlockState(new BlockPos(i, j, k), blockState, 0);
                    }
                }
            }
        }
        StructureTemplate st = new StructureTemplate();
        st.saveFromWorld(sw, BlockPos.ORIGIN, voxelCake.getSize(), false, null);
    }
    return 0;
}

private CDCommands() {
}

}
