package phoupraw.mcmod.createsdelight;

import com.google.common.collect.*;
import io.github.tropheusj.milk.Milk;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.data.client.ModelIds;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import phoupraw.mcmod.createsdelight.block.PrintedCakeBlock;
import phoupraw.mcmod.createsdelight.block.entity.PrintedCakeBlockEntity;
import phoupraw.mcmod.createsdelight.cake.CakeIngredient;
import phoupraw.mcmod.createsdelight.cake.VoxelCake;
import phoupraw.mcmod.createsdelight.registry.CSDBlocks;
import phoupraw.mcmod.createsdelight.registry.CSDItems;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

public final class PrintedCakeModel implements BakedModel {

    public static final Identifier BLOCK_ID = ModelIds.getBlockModelId(CSDBlocks.PRINTED_CAKE);
    public static final Identifier ITEM_ID = ModelIds.getItemModelId(CSDItems.PRINTED_CAKE);
    public static final Map<VoxelCake, BakedModel> BLOCK_CACHE = new WeakHashMap<>();
    public static final Map<VoxelCake, Map<Direction, BakedModel>> BLOCK_CACHE_2 = new WeakHashMap<>();
    public static final Map<NbtCompound, BakedModel> ITEM_CACHE = new WeakHashMap<>();

    public static @NotNull Map<Direction, BakedModel> BLOCK_CACHE_get(VoxelCake voxelCake) {
        var map = BLOCK_CACHE_2.get(voxelCake);
        if (map == null) {
            map = new EnumMap<>(Direction.class);
            BLOCK_CACHE_2.put(voxelCake, map);
        }
        return map;
    }

    public static @Nullable BakedModel BLOCK_CACHE_get(VoxelCake voxelCake, Direction facing) {
        return BLOCK_CACHE_get(voxelCake).get(facing);
    }

    public static @Nullable BakedModel BLOCK_CACHE_put(VoxelCake voxelCake, Direction facing, BakedModel bakedModel) {
        return BLOCK_CACHE_get(voxelCake).put(facing, bakedModel);
    }

    public static BakedModel content2model(VoxelCake voxelCake, Direction facing) {
        var faceContent = content2faces(voxelCake);
        ListMultimap<@Nullable Direction, BakedQuad> faces2quads = MultimapBuilder.ListMultimapBuilder.hashKeys().linkedListValues().build();
        MeshBuilder meshBuilder = RendererAccess.INSTANCE.getRenderer().meshBuilder();
        QuadEmitter emitter = meshBuilder.getEmitter();
        for (var cell : faceContent.cellSet()) {
            Sprite sprite = getSprite(cell.getRowKey());
            Direction norminalFace = Direction.fromHorizontal(cell.getColumnKey().getHorizontal() + facing.getHorizontal());
            Collection<Box> boxes = cell.getValue();
            switch (norminalFace) {
                case WEST -> {
                    for (Box face : boxes) {
                        square(emitter, norminalFace, face.minZ, face.minY, face.maxZ, face.maxY, face.minX, sprite, faces2quads);
                    }
                }
                case EAST -> {
                    for (Box face : boxes) {
                        square(emitter, norminalFace, 1 - face.maxZ, face.minY, 1 - face.minZ, face.maxY, 1 - face.maxX, sprite, faces2quads);
                    }
                }
                case DOWN -> {
                    for (Box face : boxes) {
                        square(emitter, norminalFace, face.minX, face.minZ, face.maxX, face.maxZ, face.minY, sprite, faces2quads);
                    }
                }
                case UP -> {
                    for (Box face : boxes) {
                        square(emitter, norminalFace, face.minX, 1 - face.maxZ, face.maxX, 1 - face.minZ, 1 - face.maxY, sprite, faces2quads);
                    }
                }
                case NORTH -> {
                    for (Box face : boxes) {
                        square(emitter, norminalFace, 1 - face.maxX, face.minY, 1 - face.minX, face.maxY, face.minZ, sprite, faces2quads);
                    }
                }
                case SOUTH -> {
                    for (Box face : boxes) {
                        square(emitter, norminalFace, face.minX, face.minY, face.maxX, face.maxY, 1 - face.maxZ, sprite, faces2quads);
                    }
                }
            }
        }
        return new SimpleBakedBlockModel(faces2quads, FluidVariantRendering.getSprite(FluidVariant.of(Milk.STILL_MILK)));
    }

    public static Table<CakeIngredient, Direction, Collection<Box>> content2faces(VoxelCake cake) {
        Table<CakeIngredient, Direction, Collection<BlockBox>> faceContent0 = HashBasedTable.create(cake.getContent().size(), Direction.values().length);
        for (Map.Entry<CakeIngredient, BlockBox> entry : cake.getContent().entries()) {
            var box = entry.getValue();
            for (var entry1 : to6Faces(box).entrySet()) {
                BlockBox face = entry1.getValue();
                boolean b = true;
                for (var faces : faceContent0.values()) {
                    if (faces.contains(face)) {
                        faces.remove(face);
                        b = false;
                        break;
                    }
                }
                if (b) {
                    Collection<BlockBox> faces = faceContent0.get(entry.getKey(), entry1.getKey());
                    if (faces == null) {
                        faces = new HashSet<>();
                        faceContent0.put(entry.getKey(), entry1.getKey(), faces);
                    }
                    faces.add(face);
                }
            }
        }
        for (Table.Cell<CakeIngredient, Direction, Collection<BlockBox>> cell : faceContent0.cellSet()) {
            Queue<BlockBox> faces = new ArrayDeque<>(cell.getValue());
            faceContent0.put(cell.getRowKey(), cell.getColumnKey(), faces);
            int none = 0;
            while (faces.size() > 1 && none < faces.size() * 2) {
                none++;
                BlockBox face = faces.poll();
                for (var iterator = faces.iterator(); iterator.hasNext(); ) {
                    BlockBox face1 = iterator.next();
                    BlockBox face0 = PrintedCakeBlock.unionBox(face, face1);
                    if (face0 != null) {
                        iterator.remove();
                        faces.offer(face0);
                        none = 0;
                        break;
                    }
                }
                if (none > 0) {
                    faces.offer(face);
                }
            }
        }
        Table<CakeIngredient, Direction, Collection<Box>> faceContent = HashBasedTable.create();
        for (var cell : faceContent0.cellSet()) {
            List<Box> list = new LinkedList<>();
            for (BlockBox box : cell.getValue()) {
                list.add(PrintedCakeBlock.block2box(box, cake.getSize()));
            }
            faceContent.put(cell.getRowKey(), cell.getColumnKey(), list);
        }
        return faceContent;
    }

    @Environment(EnvType.CLIENT)
    public static Sprite getSprite(CakeIngredient cakeIngredient) {
        return MinecraftClient.getInstance().getSpriteAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).apply(cakeIngredient.getTextureId());
    }

    public static void square(QuadEmitter emitter, Direction nominalFace, double left, double bottom, double right, double top, double depth, Sprite sprite, @Nullable Multimap<@Nullable Direction, BakedQuad> faces2quads) {
        final int color = 0xffffff;
        Direction face = depth < QuadEmitter.CULL_FACE_EPSILON ? nominalFace : null;
        BakedQuad quad = emitter
          .square(nominalFace, (float) left, (float) bottom, (float) right, (float) top, (float) depth)
          .spriteBake(sprite, MutableQuadView.BAKE_LOCK_UV)
          .color(color, color, color, color)
          .toBakedQuad(sprite);
        if (faces2quads != null) {
            faces2quads.put(face, quad);
        }
    }

    public static @Unmodifiable Map<Direction, BlockBox> to6Faces(BlockBox box) {
        return Map.of(
          Direction.WEST, new BlockBox(box.getMinX(), box.getMinY(), box.getMinZ(), box.getMinX(), box.getMaxY(), box.getMaxZ()),
          Direction.DOWN, new BlockBox(box.getMinX(), box.getMinY(), box.getMinZ(), box.getMaxX(), box.getMinY(), box.getMaxZ()),
          Direction.NORTH, new BlockBox(box.getMinX(), box.getMinY(), box.getMinZ(), box.getMaxX(), box.getMaxY(), box.getMinZ()),
          Direction.EAST, new BlockBox(box.getMaxX(), box.getMinY(), box.getMinZ(), box.getMaxX(), box.getMaxY(), box.getMaxZ()),
          Direction.UP, new BlockBox(box.getMinX(), box.getMaxY(), box.getMinZ(), box.getMaxX(), box.getMaxY(), box.getMaxZ()),
          Direction.SOUTH, new BlockBox(box.getMinX(), box.getMinY(), box.getMaxZ(), box.getMaxX(), box.getMaxY(), box.getMaxZ())
        );
    }

    public static boolean approx(double a, double b) {
        return Math.abs(a - b) < QuadEmitter.CULL_FACE_EPSILON;
    }

    public static Table<CakeIngredient, Direction, Collection<Box>> content2faces2(Multimap<CakeIngredient, BlockBox> content, Vec3i size) {
        Table<CakeIngredient, Direction, Collection<BlockBox>> faceContent0 = HashBasedTable.create(content.size(), Direction.values().length);
        ReadWriteLock lock = new ReentrantReadWriteLock();
        content.entries().parallelStream().forEach(entry -> {
            var box = entry.getValue();
            for (var entry1 : to6Faces(box).entrySet()) {
                BlockBox face = entry1.getValue();
                boolean b = true;
                lock.readLock().lock();
                for (var cell : faceContent0.cellSet()) {
                    var faces = cell.getValue();
                    if (faces.contains(face)) {
                        faces.remove(face);
                        b = false;
                        break;
                    }
                }
                lock.readLock().unlock();
                if (b) {
                    Collection<BlockBox> faces;
                    while (true) {
                        lock.readLock().lock();
                        faces = faceContent0.get(entry.getKey(), entry1.getKey());
                        if (faces != null) break;
                        lock.readLock().unlock();
                        if (lock.writeLock().tryLock()) {
                            faces = Collections.synchronizedCollection(new HashSet<>());
                            faceContent0.put(entry.getKey(), entry1.getKey(), faces);
                            lock.writeLock().unlock();
                            break;
                        }
                    }
                    faces.add(face);
                }
            }
        });
        faceContent0.cellSet().parallelStream().forEach(cell -> {
            Queue<BlockBox> faces = new ArrayDeque<>(cell.getValue());
            faceContent0.put(cell.getRowKey(), cell.getColumnKey(), faces);
            int none = 0;
            while (faces.size() > 1 && none < faces.size() * 2) {
                none++;
                BlockBox face = faces.poll();
                for (var iterator = faces.iterator(); iterator.hasNext(); ) {
                    BlockBox face1 = iterator.next();
                    BlockBox face0 = PrintedCakeBlock.unionBox(face, face1);
                    if (face0 != null) {
                        iterator.remove();
                        faces.offer(face0);
                        none = 0;
                        break;
                    }
                }
                if (none > 0) {
                    faces.offer(face);
                }
            }
        });
        Table<CakeIngredient, Direction, Collection<Box>> faceContent = HashBasedTable.create();
        for (var cell : faceContent0.cellSet()) {
            List<Box> list = new LinkedList<>();
            for (BlockBox box : cell.getValue()) {
                list.add(PrintedCakeBlock.block2box(box, size));
            }
            faceContent.put(cell.getRowKey(), cell.getColumnKey(), list);
        }
        return faceContent;
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        if (blockView.getBlockEntity(pos) instanceof PrintedCakeBlockEntity blockEntity) {
            VoxelCake cake = blockEntity.getVoxelCake();
            if (cake == null) return;
            Direction facing = state.get(HorizontalFacingBlock.FACING);
            BakedModel bakedModel = BLOCK_CACHE_get(cake, facing);
            if (bakedModel == null) {
                bakedModel = content2model(cake, facing);
                BLOCK_CACHE_put(cake, facing, bakedModel);
            }
            bakedModel.emitBlockQuads(blockView, state, pos, randomSupplier, context);
        }
    }

    /**
     * 每一帧，游戏都会调用一次这个方法
     */
    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        var blockEntityTag = BlockItem.getBlockEntityNbt(stack);
        if (blockEntityTag == null) return;
        var bakedModel = ITEM_CACHE.get(blockEntityTag);
        if (bakedModel == null) {
            var pair = PrintedCakeBlockEntity.nbt2content(blockEntityTag);
            if (pair == null) return;
            bakedModel = content2model(pair, Direction.SOUTH);
            ITEM_CACHE.put(blockEntityTag, bakedModel);
        }
        bakedModel.emitItemQuads(stack, randomSupplier, context);
    }

    /**
     * 当{@link #isVanillaAdapter()}返回{@code true}时，这个方法根本不会被游戏主动调用。
     */
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return List.of();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean hasDepth() {
        return true;
    }

    @Override
    public boolean isSideLit() {
        return true;
    }

    @Override
    public boolean isBuiltin() {
        return false;
    }

    @Override
    public Sprite getParticleSprite() {
        return Objects.requireNonNull(FluidVariantRendering.getSprite(FluidVariant.of(Milk.STILL_MILK)));
    }

    @Override
    public ModelTransformation getTransformation() {
        return ModelHelper.MODEL_TRANSFORM_BLOCK;
    }

    @Override
    public ModelOverrideList getOverrides() {
        return ModelOverrideList.EMPTY;
    }

}
