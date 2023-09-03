package phoupraw.mcmod.createsdelight.registry;

import com.mojang.brigadier.context.CommandContext;
import com.simibubi.create.content.schematics.SchematicWorld;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.block.BlockState;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import phoupraw.mcmod.createsdelight.block.entity.PrintedCakeBlockEntity;
import phoupraw.mcmod.createsdelight.cake.CakeIngredient;
import phoupraw.mcmod.createsdelight.cake.VoxelCake;
import phoupraw.mcmod.createsdelight.item.PrintedCakeItem;

import java.util.Map;

public final class CSDCommands {

    /**
     * <a href="https://discord.com/channels/507304429255393322/721100785936760876/1146464345333694605">discord消息</a>
     */
    public static boolean multiBlock(Entity entity) {
        Box box = new Box(entity.getBlockPos());
        Vec3d pos = entity.getPos();
        float expand = entity.getWidth() / 2;
        return !box.intersection(new Box(pos, pos).expand(expand, 0, expand)).equals(box);
    }

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher
          .register(CommandManager.literal("createsdelight")
            .then(CommandManager.literal("getCakeFromWorld")
              .then(CommandManager.argument("vertex1", BlockPosArgumentType.blockPos())
                .then(CommandManager.argument("vertex2", BlockPosArgumentType.blockPos())
                  .executes(context -> {
                      try {
                          BlockPos vertex1 = BlockPosArgumentType.getBlockPos(context, "vertex1");
                          BlockPos vertex2 = BlockPosArgumentType.getBlockPos(context, "vertex2");
                          BlockBox bound = BlockBox.create(vertex1, vertex2);
                          ServerWorld world = context.getSource().getWorld();
                          VoxelCake cake = VoxelCake.of(world, bound);
                          PlayerEntity player = context.getSource().getPlayer();
                          if (player == null) return 0;
                          player.getInventory().offerOrDrop(PrintedCakeItem.of(cake));
                          return 1;
                      } catch (Exception e) {
                          context.getSource().sendError(Text.of(e.toString()));
                          return 0;
                      }
                  }))))
            .then(CommandManager.literal("cake2structure")
              .then(CommandManager.argument("pos", BlockPosArgumentType.blockPos())
                .executes(CSDCommands::cake2structure)))));
    }

    public static int cake2structure(CommandContext<ServerCommandSource> context) {
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

    private CSDCommands() {
    }

}
