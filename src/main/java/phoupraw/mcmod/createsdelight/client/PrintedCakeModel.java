package phoupraw.mcmod.createsdelight.client;

import com.google.common.collect.*;
import io.github.tropheusj.milk.Milk;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
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
import java.util.function.Supplier;

//TODO 大改，看看伪装方块CopycatStepModel
public class PrintedCakeModel implements HasDepthBakedModel, CustomBakedModel {
    public static final Identifier BLOCK_ID = ModelIds.getBlockModelId(CSDBlocks.PRINTED_CAKE);
    public static final Identifier ITEM_ID = ModelIds.getItemModelId(CSDItems.PRINTED_CAKE);
    public static final Map<VoxelCake, Map<Direction, BakedModel>> BLOCK_CACHE = new WeakHashMap<>();
    public static final Map<NbtCompound, BakedModel> ITEM_CACHE = new WeakHashMap<>();
    public static final Map<CakeIngredient, Sprite> SPRITE_CACHE = new WeakHashMap<>();
    public static @NotNull Map<Direction, BakedModel> BLOCK_CACHE_get(VoxelCake voxelCake) {
        var map = BLOCK_CACHE.get(voxelCake);
        if (map == null) {
            map = new EnumMap<>(Direction.class);
            BLOCK_CACHE.put(voxelCake, map);
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
        var faceContent = content2faces(voxelCake, facing);
        ListMultimap<@Nullable Direction, BakedQuad> faces2quads = MultimapBuilder.ListMultimapBuilder.hashKeys().linkedListValues().build();
        @SuppressWarnings("ConstantConditions") MeshBuilder meshBuilder = RendererAccess.INSTANCE.getRenderer().meshBuilder();
        QuadEmitter emitter = meshBuilder.getEmitter();
        for (var cell : faceContent.cellSet()) {
            Sprite sprite = getSprite(cell.getRowKey());
            Direction norminalFace = cell.getColumnKey();
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
        //noinspection ConstantConditions
        return new SimpleBlockBakedModel(faces2quads, FluidVariantRendering.getSprite(FluidVariant.of(Milk.STILL_MILK)));
    }
    public static Table<CakeIngredient, Direction, Collection<Box>> content2faces(VoxelCake cake, Direction facing) {
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
        Vec3i cakeSize = cake.getSize();
        for (var cell : faceContent0.cellSet()) {
            List<Box> list = new LinkedList<>();
            Direction face = cell.getColumnKey();
            if (face.getAxis().isHorizontal()) {
                face = Direction.fromHorizontal(face.getHorizontal() + facing.getHorizontal());
            }
            for (BlockBox box : cell.getValue()) {
                list.add(PrintedCakeBlock.block2box(PrintedCakeBlock.rotate(box, cakeSize, facing), cakeSize));
            }
            faceContent.put(cell.getRowKey(), face, list);
        }
        return faceContent;
    }
    @Environment(EnvType.CLIENT)
    public static @NotNull Sprite getSprite(CakeIngredient cakeIngredient) {
        Sprite sprite = SPRITE_CACHE.get(cakeIngredient);
        if (sprite == null) {
            sprite = MinecraftClient.getInstance().getSpriteAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).apply(cakeIngredient.getTextureId());
            SPRITE_CACHE.put(cakeIngredient, sprite);
        }
        return sprite;
    }
    public static QuadEmitter square(QuadEmitter emitter, Box box, Direction norminalFace) {
        return switch (norminalFace) {
            case WEST -> square(emitter, norminalFace, box.minZ, box.minY, box.maxZ, box.maxY, box.minX);
            case EAST -> square(emitter, norminalFace, 1 - box.maxZ, box.minY, 1 - box.minZ, box.maxY, 1 - box.maxX);
            case DOWN -> square(emitter, norminalFace, box.minX, box.minZ, box.maxX, box.maxZ, box.minY);
            case UP -> square(emitter, norminalFace, box.minX, 1 - box.maxZ, box.maxX, 1 - box.minZ, 1 - box.maxY);
            case NORTH -> square(emitter, norminalFace, 1 - box.maxX, box.minY, 1 - box.minX, box.maxY, box.minZ);
            case SOUTH -> square(emitter, norminalFace, box.minX, box.minY, box.maxX, box.maxY, 1 - box.maxZ);
        };
    }
    public static QuadEmitter square(QuadEmitter emitter, Direction norminalFace, double left, double bottom, double right, double top, double depth) {
        return emitter.square(norminalFace, (float) left, (float) bottom, (float) right, (float) top, (float) depth);
    }
    public static BakedQuad square(QuadEmitter emitter, Direction norminalFace, double left, double bottom, double right, double top, double depth, Sprite sprite) {
        return emitter
          .square(norminalFace, (float) left, (float) bottom, (float) right, (float) top, (float) depth)
          .spriteBake(sprite, MutableQuadView.BAKE_LOCK_UV)
          .color(-1, -1, -1, -1)
          .toBakedQuad(sprite);
    }
    public static void square(QuadEmitter emitter, Direction norminalFace, double left, double bottom, double right, double top, double depth, Sprite sprite, Multimap<@Nullable Direction, BakedQuad> faces2quads) {
        Direction face = depth < QuadEmitter.CULL_FACE_EPSILON ? norminalFace : null;
        BakedQuad quad = square(emitter, norminalFace, left, bottom, right, top, depth, sprite);
        faces2quads.put(face, quad);
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
    public static boolean negativeUv(MutableQuadView quad) {
        for (int i = 0; i < 4; i++) {
            quad.spriteBake(MinecraftClient.getInstance().getBakedModelManager().getMissingModel().getParticleSprite(), MutableQuadView.BAKE_LOCK_UV);
        }
        return true;
    }
    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        if (blockView.getBlockEntity(pos) instanceof PrintedCakeBlockEntity blockEntity) {
            VoxelCake cake = blockEntity.getVoxelCake();
            if (cake != null) {
                Direction facing = state.get(HorizontalFacingBlock.FACING);
                BakedModel bakedModel = BLOCK_CACHE_get(cake, facing);
                if (bakedModel == null) {
                    bakedModel = content2model(cake, facing);
                    BLOCK_CACHE_put(cake, facing, bakedModel);
                }
                bakedModel.emitBlockQuads(blockView, state, pos, randomSupplier, context);
                return;
            }
        }
        context.pushTransform(PrintedCakeModel::negativeUv);
        MinecraftClient.getInstance().getBakedModelManager().getBlockModels().getModel(Blocks.CAKE.getDefaultState()).emitBlockQuads(blockView, state, pos, randomSupplier, context);
        context.popTransform();
    }
    /**
     * 每一帧，游戏都会调用一次这个方法
     */
    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        NbtCompound blockEntityTag = BlockItem.getBlockEntityNbt(stack);
        BakedModel bakedModel = null;
        if (blockEntityTag != null) {
            bakedModel = ITEM_CACHE.get(blockEntityTag);
            if (bakedModel == null) {
                VoxelCake voxelCake = PrintedCakeBlockEntity.nbt2content(blockEntityTag);
                if (voxelCake != null) {
                    bakedModel = content2model(voxelCake, PrintedCakeBlock.defaultFacing());
                    ITEM_CACHE.put(blockEntityTag, bakedModel);
                } else {
                    stack.setNbt(null);//FIXME 如果蛋糕读取报错，那么报错信息会刷屏
                }
            }
        }
        if (bakedModel != null) {
            bakedModel.emitItemQuads(stack, randomSupplier, context);
        } else {
            context.pushTransform(PrintedCakeModel::negativeUv);
            MinecraftClient.getInstance().getBakedModelManager().getBlockModels().getModel(Blocks.CAKE.getDefaultState()).emitItemQuads(stack, randomSupplier, context);
            context.popTransform();
        }
    }
    @Override
    public Sprite getParticleSprite() {
        //noinspection ConstantConditions
        return FluidVariantRendering.getSprite(FluidVariant.of(Milk.STILL_MILK));
    }
}
