package phoupraw.mcmod.createsdelight.client;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.SpriteFinder;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.texture.Sprite;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.joml.Vector3f;
import phoupraw.mcmod.createsdelight.block.entity.MadeVoxelBlockEntity;
import phoupraw.mcmod.createsdelight.misc.DefaultedMap;
import phoupraw.mcmod.createsdelight.misc.SupplierDefaultedMap;
import phoupraw.mcmod.createsdelight.misc.VoxelRecord;
import phoupraw.mcmod.createsdelight.mixin.ALocalRandom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MadeVoxelModel implements HasDepthBakedModel, CustomBakedModel {
    public static final @Unmodifiable List<@NotNull Direction> DIRECTIONS = List.of(Direction.values());
    public static final @Unmodifiable List<@Nullable Direction> DIRECTIONS_NULL = Stream.concat(DIRECTIONS.stream(), Stream.of((Direction) null)).toList();
    public static final DefaultedMap<VoxelRecord, BakedModel> MODEL_CACHE = DefaultedMap.loadingCache(MadeVoxelModel::toBakedModel);
    public static final BlockPos SAMPLE_1_SIZE = new BlockPos(1, 1, 1).multiply(64);
    public static final Map<BlockPos, BlockState> SAMPLE_1;
    static {
        SAMPLE_1 = new HashMap<>();
        List<BlockState> blockStates = Stream.of(Blocks.STONE, Blocks.OAK_PLANKS, Blocks.DIRT, Blocks.MELON).map(Block::getDefaultState).toList();
        for (BlockPos pos : BlockPos.iterate(BlockPos.ORIGIN, SAMPLE_1_SIZE.add(-1, -1, -1))) {
            SAMPLE_1.put(pos.toImmutable(), blockStates.get(pos.getY() % blockStates.size()));
        }
    }
    public static BakedModel toBakedModel(VoxelRecord voxelRecord) {
        return new SimpleBlockBakedModel(toBakedQuads(toCullFaces(voxelRecord.blocks(), voxelRecord.size()), voxelRecord.size()), MinecraftClient.getInstance().getBakedModelManager().getMissingModel().getParticleSprite());
    }
    public static void emitBlockQuads(Map<BlockPos, BlockState> map, Vec3i size, Supplier<Random> randomSupplier, RenderContext context) {
        Table<Vec3i, Direction, Sprite> table = Tables.synchronizedTable(HashBasedTable.create());
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
                    for (BakedQuad quad : model.getQuads(state, face, Random.create(seed))) {
                        table.put(pos, face, quad.getSprite());
                        break;
                    }
                }
            }
        });
        //CreateSDelight.LOGGER.warn("emitBlockQuads: " + (System.currentTimeMillis() - t));
        emitQuads(table, size, context);
    }
    public static Map<Vec3i, Map<Direction, Sprite>> toCullFaces(Map<BlockPos, BlockState> voxels, Vec3i size) {
        return toCullFaces(voxels, size, 1);
    }
    public static Map<Vec3i, Map<Direction, Sprite>> toCullFaces(Map<BlockPos, BlockState> voxels, Vec3i size, long randomSeed) {
        Map<Vec3i, Map<Direction, Sprite>> table = new SupplierDefaultedMap<>(new ConcurrentHashMap<>(), SupplierDefaultedMap.newingEnumMap(Direction.class));
        voxels.entrySet().stream().parallel().forEach(entry -> {
            BlockPos pos = entry.getKey();
            BlockState state = entry.getValue();
            BakedModel model = MinecraftClient.getInstance().getBakedModelManager().getBlockModels().getModel(state);
            for (Direction face : DIRECTIONS) {
                BlockPos neighborPos = pos.offset(face);
                if (!voxels.containsKey(neighborPos)) {
                    for (BakedQuad quad : model.getQuads(state, face, Random.create(randomSeed))) {
                        table.get(pos).put(face, quad.getSprite());
                        break;
                    }
                }
            }
        });
        return table;
    }
    public static Map<Vec3i, Map<Direction, Sprite>> toCullFaces(Map<BlockPos, BlockState> voxels, Vec3i size, Supplier<Random> randomSupplier) {
        long seed = randomSupplier.get() instanceof ALocalRandom localRandom ? localRandom.getSeed() : 1;
        return toCullFaces(voxels, size, seed);
    }
    public static void emitQuads(Table<Vec3i, Direction, Sprite> table, Vec3i size, RenderContext context) {
        QuadEmitter emitter = context.getEmitter();
        var scale = new Vec3d(1.0 / size.getX(), 1.0 / size.getY(), 1.0 / size.getZ());
        for (var rowEntry : table.rowMap().entrySet()) {
            var pos = rowEntry.getKey();
            var box = new Box(Vec3d.of(pos).multiply(scale), Vec3d.of(pos).add(1, 1, 1).multiply(scale));
            for (Map.Entry<Direction, Sprite> entry : rowEntry.getValue().entrySet()) {
                Sprite sprite = entry.getValue();
                PrintedCakeModel.square(emitter, box, entry.getKey())
                  .color(-1, -1, -1, -1)
                  .spriteBake(sprite, MutableQuadView.BAKE_LOCK_UV)
                  .emit();
            }
        }
    }
    public static Map<@Nullable Direction, List<BakedQuad>> toBakedQuads(Map<Vec3i, Map<Direction, Sprite>> table, Vec3i size) {
        Map<@Nullable Direction, List<BakedQuad>> cullFace2quads = DefaultedMap.arrayListHashMultimap();
        MeshBuilder meshBuilder = RendererAccess.INSTANCE.getRenderer().meshBuilder();
        QuadEmitter emitter = meshBuilder.getEmitter();
        var scale = new Vec3d(1.0 / size.getX(), 1.0 / size.getY(), 1.0 / size.getZ());
        for (var rowEntry : table.entrySet()) {
            var pos = rowEntry.getKey();
            var box = new Box(Vec3d.of(pos).multiply(scale), Vec3d.of(pos).add(1, 1, 1).multiply(scale));
            for (Map.Entry<Direction, Sprite> entry : rowEntry.getValue().entrySet()) {
                Sprite sprite = entry.getValue();
                var quad0 = PrintedCakeModel.square(emitter, box, entry.getKey())
                  .color(-1, -1, -1, -1)
                  .spriteBake(sprite, MutableQuadView.BAKE_LOCK_UV);
                Direction cullFace = quad0.cullFace();
                BakedQuad quad = quad0.toBakedQuad(sprite);
                cullFace2quads.get(cullFace).add(quad);
            }
        }
        return cullFace2quads;
    }
    public static boolean isValid(BlockState blockState, BlockView world, BlockPos pos) {
        return blockState.getOutlineShape(world, pos).getBoundingBox().equals(VoxelShapes.fullCube().getBoundingBox()) && !blockState.getCollisionShape(world, pos).isEmpty();
    }
    public static Map<@Nullable Direction, List<BakedQuad>> collect(Mesh mesh) {
        Map<@Nullable Direction, List<BakedQuad>> cullFaces2quads = DefaultedMap.arrayListHashMultimap();
        SpriteFinder finder = SpriteFinder.get(MinecraftClient.getInstance().getBakedModelManager().getAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE));
        mesh.forEach(quad -> cullFaces2quads.get(quad.cullFace()).add(quad.toBakedQuad(finder.find(quad))));
        return cullFaces2quads;
    }
    @Override
    public Sprite getParticleSprite() {
        return MinecraftClient.getInstance().getBakedModelManager().getBlockModels().getModelParticleSprite(Blocks.BARRIER.getDefaultState());
    }
    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        if (!(blockView.getBlockEntity(pos) instanceof MadeVoxelBlockEntity blockEntity)) return;
        VoxelRecord voxelRecord = blockEntity.voxelRecord;
        if (voxelRecord == null) return;
        context.pushTransform(quad -> {
            Direction nominalFace = quad.nominalFace();
            if (nominalFace != null && nominalFace.getAxis().isVertical()) {
                quad.nominalFace(nominalFace.getOpposite());
            }
            Vector3f vp = new Vector3f();
            for (int i = 0; i < 4; i++) {
                quad.copyPos(i, vp);
                vp.set(vp.x(), 1 - vp.y(), vp.z());
                quad.pos(i, vp);
            }
            return true;
        });//上下颠倒
        MODEL_CACHE.get(voxelRecord).emitBlockQuads(blockView, state, pos, randomSupplier, context);
        //emitBlockQuads(SAMPLE_1, SAMPLE_1_SIZE, randomSupplier, context);
        context.popTransform();
    }
    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        HasDepthBakedModel.super.emitItemQuads(stack, randomSupplier, context);
    }
}
