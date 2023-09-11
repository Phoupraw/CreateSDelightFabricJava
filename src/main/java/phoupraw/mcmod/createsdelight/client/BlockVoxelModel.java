package phoupraw.mcmod.createsdelight.client;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import phoupraw.mcmod.createsdelight.mixin.ALocalRandom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class BlockVoxelModel implements HasDepthBakedModel, CustomBakedModel {
    public static final @Unmodifiable List<@NotNull Direction> DIRECTIONS = List.of(Direction.values());
    public static final @Unmodifiable List<@Nullable Direction> DIRECTIONS_N = Stream.concat(DIRECTIONS.stream(), Stream.of((Direction) null)).toList();
    public static final BlockPos SAMPLE_1_SIZE = new BlockPos(1, 1, 1).multiply(64);
    public static final Map<BlockPos, BlockState> SAMPLE_1;
    static {
        SAMPLE_1 = new HashMap<>();
        List<BlockState> blockStates = Stream.of(Blocks.STONE, Blocks.OAK_PLANKS, Blocks.DIRT, Blocks.MELON).map(Block::getDefaultState).toList();
        for (BlockPos pos : BlockPos.iterate(BlockPos.ORIGIN, SAMPLE_1_SIZE.add(-1, -1, -1))) {
            SAMPLE_1.put(pos.toImmutable(), blockStates.get(pos.getY() % blockStates.size()));
        }
    }
    public static void emitBlockQuads(Map<BlockPos, BlockState> map, Vec3i size, Supplier<Random> randomSupplier, RenderContext context) {
        Table<Vec3i, Direction, Sprite> table = HashBasedTable.create();
        long t = (System.currentTimeMillis());
        long seed;
        if (randomSupplier.get() instanceof ALocalRandom localRandom) {
            seed = localRandom.getSeed();
        } else {
            seed = 1;
        }
        map.entrySet().stream().parallel().forEach(entry -> {
            BlockPos pos = entry.getKey();
            BlockState state = entry.getValue();
            BakedModel model = MinecraftClient.getInstance().getBakedModelManager().getBlockModels().getModel(state);
            for (Direction face : DIRECTIONS) {
                BlockPos neighborPos = pos.offset(face);
                if (!map.containsKey(neighborPos)) {
                    Random random = null;
                    while (true) {
                        try {
                            for (BakedQuad quad : model.getQuads(state, face, random)) {
                                synchronized (table) {
                                    table.put(pos, face, quad.getSprite());
                                }
                                break;
                            }
                            break;
                        } catch (NullPointerException e) {
                            if (random == null) {
                                synchronized (randomSupplier) {
                                    random = randomSupplier.get();
                                }
                            } else {
                                throw e;
                            }
                        }
                    }
                }
            }
        });
        //CreateSDelight.LOGGER.warn("emitBlockQuads: " + (System.currentTimeMillis() - t));
        emitQuads(table, size, context);
    }
    public static void emitQuads(Table<Vec3i, Direction, Sprite> table, Vec3i size, RenderContext context) {
        MeshBuilder meshBuilder = RendererAccess.INSTANCE.getRenderer().meshBuilder();
        QuadEmitter emitter = meshBuilder.getEmitter();
        var scale = new Vec3d(1.0 / size.getX(), 1.0 / size.getY(), 1.0 / size.getZ());
        long t = (System.currentTimeMillis());
        for (var rowEntry : table.rowMap().entrySet()) {
            var pos = rowEntry.getKey();
            var box = new Box(Vec3d.of(pos).multiply(scale), Vec3d.of(pos).add(1, 1, 1).multiply(scale));
            for (Map.Entry<Direction, Sprite> entry : rowEntry.getValue().entrySet()) {
                PrintedCakeModel.square(emitter, box, entry.getKey())
                  .color(-1, -1, -1, -1)
                  .spriteBake(entry.getValue(), MutableQuadView.BAKE_LOCK_UV)
                  .emit();
            }
        }
        //CreateSDelight.LOGGER.warn(System.currentTimeMillis() - t);
        meshBuilder.build().outputTo(context.getEmitter());
    }
    public static boolean isValid(BlockState blockState, BlockView world, BlockPos pos) {
        return blockState.getOutlineShape(world, pos).getBoundingBox().equals(VoxelShapes.fullCube().getBoundingBox()) && !blockState.getCollisionShape(world, pos).isEmpty();
    }
    @Override
    public Sprite getParticleSprite() {
        return MinecraftClient.getInstance().getBakedModelManager().getBlockModels().getModelParticleSprite(Blocks.BARRIER.getDefaultState());
    }
    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        long t = (System.currentTimeMillis());
        emitBlockQuads(SAMPLE_1, SAMPLE_1_SIZE, randomSupplier, context);
        //CreateSDelight.LOGGER.warn("outer: " + (System.currentTimeMillis() - t));
    }
}
