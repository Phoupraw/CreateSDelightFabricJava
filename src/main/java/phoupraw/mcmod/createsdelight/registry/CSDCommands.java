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
import net.minecraft.structure.Structure;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.block.entity.MadeVoxelBlockEntity;
import phoupraw.mcmod.createsdelight.item.VoxelCakeItem;
import phoupraw.mcmod.createsdelight.misc.VoxelRecord;

public final class CSDCommands {
    /**
     <a href="https://discord.com/channels/507304429255393322/721100785936760876/1146464345333694605">discord消息</a>
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
              .requires(source -> source.hasPermissionLevel(2) && source.getPlayer() != null)
              .then(CommandManager.argument("vertex1", BlockPosArgumentType.blockPos())
                .then(CommandManager.argument("vertex2", BlockPosArgumentType.blockPos())
                  .executes(CSDCommands::getCakeFromWorld))))
            .then(CommandManager.literal("cake2structure")
              .then(CommandManager.argument("pos", BlockPosArgumentType.blockPos())
                .executes(CSDCommands::cake2structure)))));
    }
    public static int cake2structure(CommandContext<ServerCommandSource> context) {
        BlockPos pos = BlockPosArgumentType.getUnvalidatedBlockPos(context, "pos");
        if (context.getSource().getWorld().getBlockEntity(pos) instanceof MadeVoxelBlockEntity be) {
            //if (be.predefined != null) {
            //    context.getSource().sendError(Text.of("此蛋糕已经是预定义的！"));
            //    return 0;
            //}
            if (be.getVoxelRecord() == null) {
                context.getSource().sendError(Text.translatableWithFallback("command.createsdelight.cake2structure.empty", "此蛋糕没有内容！"));
                return 0;
            }
            VoxelRecord voxelCake = be.getVoxelRecord();
            SchematicWorld sw = new SchematicWorld(context.getSource().getWorld());
            for (var entry : voxelCake.blocks().entrySet()) {
                BlockState blockState = entry.getValue().getDefaultState();
                sw.setBlockState(entry.getKey(), blockState, 0);
            }
            Structure st = new Structure();
            st.saveFromWorld(sw, BlockPos.ORIGIN, voxelCake.size(), false, null);
            return 1;
        }
        return 0;
    }
    public static int getCakeFromWorld(CommandContext<ServerCommandSource> context) {
        try {
            BlockPos vertex1 = BlockPosArgumentType.getUnvalidatedBlockPos(context, "vertex1");
            BlockPos vertex2 = BlockPosArgumentType.getUnvalidatedBlockPos(context, "vertex2");
            ServerWorld world = context.getSource().getWorld();
            var result = VoxelRecord.of(world, BlockBox.create(vertex1, vertex2), BlockPos.iterate(vertex1, vertex2));
            VoxelRecord voxelRecord = result.getLeft();
            if (voxelRecord.blocks().isEmpty()) {
                context.getSource().sendError(Text.translatableWithFallback("command.createsdelight.cake2structure.empty", "此蛋糕没有内容！"));
                return 0;
            }
            PlayerEntity player = context.getSource().getPlayer();
            if (player == null) return 0;
            player.getInventory().offerOrDrop(VoxelCakeItem.of(voxelRecord));
            return 1;
        } catch (Throwable e) {
            CreateSDelight.LOGGER.catching(e);
            context.getSource().sendError(Text.of(e.toString()));
            return 0;
        }
    }
    private CSDCommands() {
    }
}
