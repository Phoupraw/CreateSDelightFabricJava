package phoupraw.mcmod.createsdelight.client;

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
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.BakedQuadFactory;
import net.minecraft.client.render.model.ModelRotation;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.joml.*;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.block.PrintedCakeBlock;
import phoupraw.mcmod.createsdelight.block.entity.MadeVoxelBlockEntity;
import phoupraw.mcmod.createsdelight.misc.DefaultedMap;
import phoupraw.mcmod.createsdelight.misc.SupplierDefaultedMap;
import phoupraw.mcmod.createsdelight.misc.VoxelRecord;
import phoupraw.mcmod.createsdelight.mixin.ALocalRandom;

import java.lang.Math;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MadeVoxelModel implements CustomBlockModel {
    public static final @Unmodifiable List<@NotNull Direction> DIRECTIONS = List.of(Direction.values());
    public static final @Unmodifiable List<@Nullable Direction> DIRECTIONS_NULL = Stream.concat(DIRECTIONS.stream(), Stream.of((Direction) null)).toList();
    public static final DefaultedMap<VoxelRecord, BakedModel> VOXEL2MODEL = DefaultedMap.loadingCache(MadeVoxelModel::toBakedModel);
    public static final DefaultedMap<NbtCompound, BakedModel> NBT2MODEL = DefaultedMap.loadingCache(nbtVoxelRecord -> toBakedModel(VoxelRecord.of(MinecraftClient.getInstance().world, nbtVoxelRecord)));
    public static final float MIN_SCALE = 1.0F / (float) Math.cos((float) (Math.PI / 8)) - 1.0F;
    public static final float MAX_SCALE = 1.0F / (float) Math.cos((float) (Math.PI / 4)) - 1.0F;
    public static BakedModel toBakedModel(VoxelRecord voxelRecord) {
        return new SimpleBlockBakedModel(toBakedQuads(toCullFaces(voxelRecord.blocks(), voxelRecord.size()), voxelRecord.size()), MinecraftClient.getInstance().getBakedModelManager().getMissingModel().getParticleSprite());
    }
    @Deprecated
    public static BakedModel loadBakedModel(VoxelRecord voxelRecord) {
        new Thread(() -> {
            BakedModel model = toBakedModel(voxelRecord);
            do {
                VOXEL2MODEL.put(voxelRecord, model);
            } while (VOXEL2MODEL.get(voxelRecord) == SimpleBlockBakedModel.EMPTY);
        }).start();
        return SimpleBlockBakedModel.EMPTY;
    }
    public static Map<Vec3i, Map<Direction, Sprite>> toCullFaces(Map<BlockPos, Block> voxels, Vec3i size) {
        return toCullFaces(voxels, size, 0);
    }
    public static Map<Vec3i, Map<Direction, Sprite>> toCullFaces(Map<BlockPos, Block> voxels, Vec3i size, long randomSeed) {
        DefaultedMap<Vec3i, @NotNull Map<Direction, Sprite>> table = new SupplierDefaultedMap<>(new ConcurrentHashMap<>(), SupplierDefaultedMap.newingEnumMap(Direction.class));
        voxels.entrySet().parallelStream().forEach(entry -> {
            BlockPos pos = entry.getKey();
            BlockState state = entry.getValue().getDefaultState();
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
    public static Map<Vec3i, Map<Direction, Sprite>> toCullFaces(Map<BlockPos, Block> voxels, Vec3i size, Supplier<Random> randomSupplier) {
        long seed = randomSupplier.get() instanceof ALocalRandom localRandom ? localRandom.getSeed() : 1;
        return toCullFaces(voxels, size, seed);
    }
    public static Map<@Nullable Direction, List<BakedQuad>> toBakedQuads(Map<Vec3i, Map<@NotNull Direction, Sprite>> table, Vec3i size) {
        Map<@Nullable Direction, List<BakedQuad>> cullFace2quads = DefaultedMap.arrayListHashMultimap();
        @SuppressWarnings("ConstantConditions") MeshBuilder meshBuilder = RendererAccess.INSTANCE.getRenderer().meshBuilder();
        QuadEmitter emitter = meshBuilder.getEmitter();
        var scale = new Vec3d(1.0 / size.getX(), 1.0 / size.getY(), 1.0 / size.getZ());
        int quadsCount = 0;
        for (var rowEntry : table.entrySet()) {
            var pos = rowEntry.getKey();
            var box = new Box(Vec3d.of(pos).multiply(scale), Vec3d.of(pos).add(1, 1, 1).multiply(scale));
            for (Map.Entry<Direction, Sprite> entry : rowEntry.getValue().entrySet()) {
                Sprite sprite = entry.getValue();
                var quad0 = PrintedCakeModel.square(emitter, box, entry.getKey())
                  .color(-1, -1, -1, -1)
                  .spriteBake(sprite, MutableQuadView.BAKE_LOCK_UV);
                BakedQuad quad = quad0.toBakedQuad(sprite);
                cullFace2quads.get(quad0.cullFace()).add(quad);
                quadsCount++;
            }
        }
        CreateSDelight.LOGGER.debug("MadeVoxelModel.toBakedQuads 一共产生了%d个面".formatted(quadsCount));
        return cullFace2quads;
    }
    public static Map<@Nullable Direction, List<BakedQuad>> toBakedQuads2(Map<Vec3i, Map<@NotNull Direction, Sprite>> table, Vec3i size) {
        Comparator<Vector2ic> comparator = Comparator.comparingInt(Vector2ic::x).thenComparingInt(Vector2ic::y);
        Map<@NotNull Direction, @NotNull Map<Sprite, @NotNull Map<Integer, @NotNull SortedSet<Vector2ic>>>> depth2vecs =
          new SupplierDefaultedMap<>(new HashMap<>(),
            () -> new SupplierDefaultedMap<>(new HashMap<>(),
              () -> new SupplierDefaultedMap<>(new HashMap<>(),
                () -> new TreeSet<>(comparator))));
        for (var rowEntry : table.entrySet()) {
            Vec3i blockPos = rowEntry.getKey();
            Box box = new Box(new BlockPos(blockPos));
            for (Map.Entry<Direction, Sprite> entry : rowEntry.getValue().entrySet()) {
                Direction face = entry.getKey();
                var pair = PrintedCakeModel.square(box, face, size);
                int depth = pair.getRight().intValue();
                int left = (int) pair.getLeft().m00();
                int bottom = (int) pair.getLeft().m01();
                depth2vecs.get(face).get(entry.getValue()).get(depth).add(new Vector2i(left, bottom));
            }
        }
        Map<@NotNull Direction, @NotNull Map<Sprite, @NotNull Map<Integer, @NotNull Collection<Matrix2dc>>>> depth2rects =
          new SupplierDefaultedMap<>(new HashMap<>(),
            () -> new SupplierDefaultedMap<>(new HashMap<>(),
              () -> new SupplierDefaultedMap<>(new HashMap<>(),
                LinkedList::new)));
        int x0 = size.getX();
        int y0 = size.getY();
        Vector2i vec = new Vector2i();
        for (var faceEntry : depth2vecs.entrySet()) {
            Direction face = faceEntry.getKey();
            for (var spriteEntry : faceEntry.getValue().entrySet()) {
                Sprite sprite = spriteEntry.getKey();
                for (var depthEntry : spriteEntry.getValue().entrySet()) {
                    var vecs = depthEntry.getValue();
                    while (!vecs.isEmpty()) {
                        var start = vecs.first();
                        vecs.remove(start);
                        int x1 = start.x();
                        int y1 = start.y();
                        int x2 = x1 + 1;
                        int y2 = y1 + 1;
                        vec.set(start);
                        for (int i = x1 + 1; i <= x0; i++) {
                            vec.x = i;
                            if (!vecs.contains(vec)) {
                                x2 = i;
                                break;
                            }
                            vecs.remove(vec);
                        }
                        vec.set(start);
                        outer:
                        for (int i = y1 + 1; i <= y0; i++) {
                            vec.y = i;
                            for (int j = x1; j < x2; j++) {
                                vec.x = j;
                                if (!vecs.contains(vec)) {
                                    y2 = i;
                                    break outer;
                                }
                                vecs.remove(vec);
                            }
                        }
                        depth2rects.get(face).get(sprite).get(depthEntry.getKey()).add(new Matrix2d((double) x1 / x0, (double) y1 / y0, (double) x2 / x0, (double) y2 / y0));
                    }
                }
            }
        }
        Map<@Nullable Direction, List<BakedQuad>> cullFace2quads = DefaultedMap.arrayListHashMultimap();
        @SuppressWarnings("ConstantConditions") MeshBuilder meshBuilder = RendererAccess.INSTANCE.getRenderer().meshBuilder();
        QuadEmitter emitter = meshBuilder.getEmitter();
        for (var faceEntry : depth2rects.entrySet()) {
            Direction face = faceEntry.getKey();
            for (var spriteEntry : faceEntry.getValue().entrySet()) {
                Sprite sprite = spriteEntry.getKey();
                for (var depthEntry : spriteEntry.getValue().entrySet()) {
                    double depth = (double) depthEntry.getKey() / face.getAxis().choose(size.getX(), size.getY(), size.getZ());
                    for (var rect : depthEntry.getValue()) {
                        var quad0 = PrintedCakeModel.square(emitter, face, rect.m00(), rect.m01(), rect.m10(), rect.m11(), depth)
                          .color(-1, -1, -1, -1)
                          .spriteBake(sprite, MutableQuadView.BAKE_LOCK_UV);
                        Direction cullFace = quad0.cullFace();
                        BakedQuad quad = quad0.toBakedQuad(sprite);
                        cullFace2quads.get(cullFace).add(quad);
                    }
                }
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
    /**
     @param vector
     @param rotation
     @see BakedQuadFactory#rotateVertex
     @see BakedQuadFactory#transformVertex(Vector3f, Vector3f, Matrix4f, Vector3f)
     */
    public static void rotateVertex(Vector3f vector, @Nullable net.minecraft.client.render.model.json.ModelRotation rotation) {
        if (rotation != null) {
            Vector3f vector3f;
            Vector3f vector3f2;
            switch (rotation.axis()) {
                case X -> {
                    vector3f = new Vector3f(1.0F, 0.0F, 0.0F);
                    vector3f2 = new Vector3f(0.0F, 1.0F, 1.0F);
                }
                case Y -> {
                    vector3f = new Vector3f(0.0F, 1.0F, 0.0F);
                    vector3f2 = new Vector3f(1.0F, 0.0F, 1.0F);
                }
                case Z -> {
                    vector3f = new Vector3f(0.0F, 0.0F, 1.0F);
                    vector3f2 = new Vector3f(1.0F, 1.0F, 0.0F);
                }
                default -> throw new IllegalArgumentException("There are only 3 axes");
            }

            Quaternionf quaternionf = new Quaternionf().rotationAxis(rotation.angle() * (float) (Math.PI / 180.0), vector3f);
            if (rotation.rescale()) {
                if (Math.abs(rotation.angle()) == 22.5F) {
                    vector3f2.mul(MIN_SCALE);
                } else {
                    vector3f2.mul(MAX_SCALE);
                }

                vector3f2.add(1.0F, 1.0F, 1.0F);
            } else {
                vector3f2.set(1.0F, 1.0F, 1.0F);
            }

            Vector3f origin = new Vector3f(rotation.origin());
            Vector4f vector4f = new Matrix4f().rotation(quaternionf).transform(new Vector4f(vector.x() - origin.x(), vector.y() - origin.y(), vector.z() - origin.z(), 1.0F));
            vector4f.mul(new Vector4f(vector3f2, 1.0F));
            vector.set(vector4f.x() + origin.x(), vector4f.y() + origin.y(), vector4f.z() + origin.z());
        }
    }
    public static AffineTransformation rotatingTo(Direction horizontal) {
        return ModelRotation.get(0, (PrintedCakeBlock.defaultFacing().getHorizontal() - horizontal.getHorizontal()) * 90).getRotation();
    }
    public static boolean rotate(MutableQuadView quad, AffineTransformation rotation) {
        Direction cullFace = quad.cullFace();
        if (cullFace != null) {
            quad.cullFace(Direction.transform(rotation.getMatrix(), cullFace));
        }
        Vector3f vertexPos = new Vector3f();
        for (int i = 0; i < 4; i++) {
            quad.copyPos(i, vertexPos);
            JsonUnbakedModel.QUAD_FACTORY.transformVertex(vertexPos, rotation);
            quad.pos(i, vertexPos);
        }
        return true;
    }
    @Override
    public Sprite getParticleSprite() {
        return MinecraftClient.getInstance().getBakedModelManager().getBlockModels().getModelParticleSprite(Blocks.CAKE.getDefaultState());
    }
    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        if (!(blockView.getBlockEntity(pos) instanceof MadeVoxelBlockEntity blockEntity)) return;
        VoxelRecord voxelRecord = blockEntity.getVoxelRecord();
        if (voxelRecord == null) return;
        Direction facing = blockEntity.getCachedState().get(HorizontalFacingBlock.FACING);
        if (facing != PrintedCakeBlock.defaultFacing()) {
            AffineTransformation rotation = rotatingTo(facing);
            context.pushTransform(quad -> rotate(quad, rotation));
        }
        long t = System.currentTimeMillis();
        VOXEL2MODEL.get(voxelRecord).emitBlockQuads(blockView, state, pos, randomSupplier, context);
        CreateSDelight.LOGGER.debug("MadeVoxelModel.emitBlockQuads VOXEL2MODEL.get.emitBlockQuads运行了%d毫秒".formatted(System.currentTimeMillis() - t));
        if (facing != PrintedCakeBlock.defaultFacing()) {
            context.popTransform();
        }
        if (voxelRecord.equals(VoxelRecord.EMPTY)) {
            context.pushTransform(PrintedCakeModel::negativeUv);
            MinecraftClient.getInstance().getBakedModelManager().getBlockModels().getModel(Blocks.CAKE.getDefaultState()).emitBlockQuads(blockView, state, pos, randomSupplier, context);
            context.popTransform();
        }
    }
    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        NbtCompound blockEntityNbt = BlockItem.getBlockEntityNbt(stack);
        if (blockEntityNbt != null) {
            NbtCompound nbtVoxelRecord = blockEntityNbt.getCompound("voxelRecord");
            if (NBT2MODEL.containsKey(nbtVoxelRecord)) {
                NBT2MODEL.get(nbtVoxelRecord).emitItemQuads(stack, randomSupplier, context);
            } else {
                VoxelRecord voxelRecord = VoxelRecord.of(MinecraftClient.getInstance().world, nbtVoxelRecord);
                if (VoxelRecord.EMPTY.equals(voxelRecord)) {
                    context.pushTransform(PrintedCakeModel::negativeUv);
                    MinecraftClient.getInstance().getBakedModelManager().getBlockModels().getModel(Blocks.CAKE.getDefaultState()).emitItemQuads(stack, randomSupplier, context);
                    context.popTransform();
                }
            }
        }
    }
}
