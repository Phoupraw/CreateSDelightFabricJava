package phoupraw.mcmod.createsdelight.model;

import com.google.common.collect.*;
import io.github.tropheusj.milk.Milk;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.data.client.ModelIds;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import phoupraw.mcmod.createsdelight.block.PrintedCakeBlock;
import phoupraw.mcmod.createsdelight.block.entity.PrintedCakeBE;
import phoupraw.mcmod.createsdelight.cake.CakeIngredient;
import phoupraw.mcmod.createsdelight.cake.VoxelCake;
import phoupraw.mcmod.createsdelight.registry.CDBlocks;
import phoupraw.mcmod.createsdelight.registry.CDItems;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;
import java.util.function.Supplier;

public final class PrintedCakeModel implements BakedModel, FabricBakedModel {

public static final Identifier BLOCK_ID = ModelIds.getBlockModelId(CDBlocks.PRINTED_CAKE);
public static final Identifier ITEM_ID = ModelIds.getItemModelId(CDItems.PRINTED_CAKE);
//public static final Map<UUID, List<BakedQuad>> CACHE = new HashMap<>();

//public static @NotNull List<BakedQuad> toBakedQuads(@Nullable Map<FluidVariant, Collection<Box>> content) {
//    if (content == null) return List.of();
//    List<BakedQuad> quads = new ArrayList<>();
//    var builder = IronBowlModel.getMeshBuilder();
//    QuadEmitter emitter = builder.getEmitter();
//    for (Map.Entry<FluidVariant, Collection<Box>> entry : content.entrySet()) {
//        Sprite sprite = FluidVariantRendering.getSprite(entry.getKey());
//        int color = FluidVariantRendering.getColor(entry.getKey());
//        for (Box box : entry.getValue()) {
//            float minX = (float) box.minX;
//            float minY = (float) box.minY;
//            float minZ = (float) box.minZ;
//            float maxX = (float) box.maxX;
//            float maxY = (float) box.maxY;
//            float maxZ = (float) box.maxZ;
//            emitter.square(Direction.DOWN, minX, minZ, maxX, maxZ, minY);
//            quads.add(emitter.toBakedQuad(0, sprite, true));
//            emitter.square(Direction.UP, minX, minZ, maxX, maxZ, 1 - maxY);
//            quads.add(emitter.toBakedQuad(0, sprite, true));
//            emitter.square(Direction.NORTH, minX, minY, maxX, maxY, minZ);
//            quads.add(emitter.toBakedQuad(0, sprite, true));
//            emitter.square(Direction.SOUTH, minX, minY, maxX, maxY, 1 - maxZ);
//            quads.add(emitter.toBakedQuad(0, sprite, true));
//            emitter.square(Direction.WEST, minZ, minY, maxZ, maxY, minX);
//            quads.add(emitter.toBakedQuad(0, sprite, true));
//            emitter.square(Direction.EAST, minZ, minY, maxZ, maxY, 1 - maxX);
//            //                        emitter.spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV);
//            emitter.spriteColor(0, color, color, color, color);
//            //                        emitter.emit();
//            quads.add(emitter.toBakedQuad(0, sprite, true));
//        }
//    }
//    return quads;
//}

//public static void squareEmit(@NotNull QuadEmitter emitter, @NotNull Sprite sprite, int color, @NotNull Box box) {
//    float minX = (float) box.minX;
//    float minY = (float) box.minY;
//    float minZ = (float) box.minZ;
//    float maxX = (float) box.maxX;
//    float maxY = (float) box.maxY;
//    float maxZ = (float) box.maxZ;
//    emitter.square(Direction.WEST, minZ, minY, maxZ, maxY, minX);
//    spriteColorEmit(emitter, sprite, color);
//    emitter.square(Direction.EAST, 1 - maxZ, minY, 1 - minZ, maxY, 1 - maxX);
//    spriteColorEmit(emitter, sprite, color);
//    emitter.square(Direction.DOWN, minX, minZ, maxX, maxZ, minY);
//    spriteColorEmit(emitter, sprite, color);
//    emitter.square(Direction.UP, minX, 1 - maxZ, maxX, 1 - minZ, 1 - maxY);
//    spriteColorEmit(emitter, sprite, color);
//    emitter.square(Direction.NORTH, 1 - maxX, minY, 1 - minX, maxY, minZ);
//    spriteColorEmit(emitter, sprite, color);
//    emitter.square(Direction.SOUTH, minX, minY, maxX, maxY, 1 - maxZ);
//    spriteColorEmit(emitter, sprite, color);
//}

//public static void spriteColorEmit(@NotNull QuadEmitter emitter, @NotNull Sprite sprite, int color) {
//    emitter.spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV);
//    emitter.spriteColor(0, color, color, color, color);
//    emitter.emit();
//}

//public static void emitQuads(RenderContext context, Map<FluidVariant, Collection<Box>> content) {
//    var builder = IronBowlModel.getMeshBuilder();
//    QuadEmitter emitter = builder.getEmitter();
//    for (var entry : content.entrySet()) {
//        @NotNull Sprite sprite = Objects.requireNonNull(FluidVariantRendering.getSprite(entry.getKey()));
//        int color = FluidVariantRendering.getColor(entry.getKey());
//        for (Box box : entry.getValue()) {
//            squareEmit(emitter, sprite, color, box);
//            context.meshConsumer().accept(builder.build());
//        }
//    }
//}

//public static void emitQuads2(RenderContext context, Map<CakeIngredient, Collection<Box>> content) {
//    var builder = IronBowlModel.getMeshBuilder();
//    QuadEmitter emitter = builder.getEmitter();
//    for (var entry : content.entrySet()) {
//        @NotNull Sprite sprite = getSprite(entry.getKey());
//        int color = 0xffffff;
//        for (Box box : entry.getValue()) {
//            squareEmit(emitter, sprite, color, box);
//            context.meshConsumer().accept(builder.build());
//        }
//    }
//}

@SuppressWarnings("deprecation")
public static Sprite getSprite(CakeIngredient cakeIngredient) {
    return MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).apply(cakeIngredient.getTextureId());
}

//@ApiStatus.Internal
//public static void square(QuadEmitter emitter, Direction nominalFace, float left, float bottom, float right, float top, float depth, Sprite sprite, Map<@Nullable Direction, ? extends Collection<BakedQuad>> faces2quads, Collection<Map.@Unmodifiable Entry<BakedQuad, float[]>> vertexes) {
//    final int color = 0xffffff;
//    Direction face = depth < QuadEmitter.CULL_FACE_EPSILON ? nominalFace : null;
//    BakedQuad quad = emitter
//      .square(nominalFace, left, bottom, right, top, depth)
//      .spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV)
//      .spriteColor(0, color, color, color, color)
//      .toBakedQuad(0, sprite, false);
//    if (face == null) {
//        float[] vertex = {left, bottom, right, top, depth};
//        for (Map.Entry<BakedQuad, float[]> entry : vertexes) {
//            Direction nominalFace2 = entry.getKey().getFace();
//            if (nominalFace == nominalFace2.getOpposite()) {
//                float[] vertex1 = entry.getValue();
//
//            }
//        }
//        vertexes.add(Map.entry(quad, vertex));
//    } else {
//        faces2quads.get(face).add(quad);
//    }
//}

public static void square(QuadEmitter emitter, Direction nominalFace, double left, double bottom, double right, double top, double depth, Sprite sprite, Multimap<@Nullable Direction, BakedQuad> faces2quads) {
    final int color = 0xffffff;
    Direction face = depth < QuadEmitter.CULL_FACE_EPSILON ? nominalFace : null;
    BakedQuad quad = emitter
      .square(nominalFace, (float) left, (float) bottom, (float) right, (float) top, (float) depth)
      .spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV)
      .spriteColor(0, color, color, color, color)
      .toBakedQuad(0, sprite, false);
    faces2quads.put(face, quad);
}

public static boolean approx(double a, double b) {
    return Math.abs(a - b) < QuadEmitter.CULL_FACE_EPSILON;
}

//public static @Nullable Box unionBox(Box box1, Box box2) {
//    int bools
//      = (approx(box1.minX, box2.minX) ? 1 : 0)
//      + (approx(box1.minY, box2.minY) ? 1 : 0)
//      + (approx(box1.minZ, box2.minZ) ? 1 : 0)
//      + (approx(box1.maxX, box2.maxX) ? 1 : 0)
//      + (approx(box1.maxY, box2.maxY) ? 1 : 0)
//      + (approx(box1.maxZ, box2.maxZ) ? 1 : 0);
//    if (bools == 4) {
//
//    }
//    Box box3 = box1.union(box2);
//
//    return null;
//}

//public static @Nullable Box unionBox2(Box box1, Box box2) {
//    Box box3 = box1.union(box2);
//    if (box1.getXLength() * box1.getYLength() * box1.getZLength()
//      + box2.getXLength() * box2.getYLength() * box2.getZLength()
//      > box3.getXLength() * box3.getYLength() * box3.getZLength()
//      - QuadEmitter.CULL_FACE_EPSILON * QuadEmitter.CULL_FACE_EPSILON) {
//        return box3;
//    }
//    return null;
//}

//public static @Nullable Box unionBox3(Box box1, Box box2) {
//    boolean
//      x = approx(box1.minX, box2.minX) && approx(box1.maxX, box2.maxX),
//      y = approx(box1.minY, box2.minY) && approx(box1.maxY, box2.maxY),
//      z = approx(box1.minZ, box2.minZ) && approx(box1.maxZ, box2.maxZ);
//    if (box1.minX < box2.maxX && approx(box1.maxX, box2.minX) && y && z
//      || box1.minY < box2.maxY && approx(box1.maxY, box2.minY) && x && z
//      || box1.minZ < box2.maxZ && approx(box1.maxZ, box2.minZ) && x && y) {
//        return box1.union(box2);
//    }
//    Box temp = box1;
//    box1 = box2;
//    box2 = temp;
//    if (box1.minX < box2.maxX && approx(box1.maxX, box2.minX) && y && z
//      || box1.minY < box2.maxY && approx(box1.maxY, box2.minY) && x && z
//      || box1.minZ < box2.maxZ && approx(box1.maxZ, box2.minZ) && x && y) {
//        return box1.union(box2);
//    }
//    return null;
//}

//public static void unionBoxes(Queue<Box> boxes) {
//    int none = 0;
//    while (boxes.size() > 1 && none < boxes.size() * 2) {
//        Box box1 = boxes.poll();
//        none++;
//        for (var iterator = boxes.iterator(); iterator.hasNext(); ) {
//            Box box2 = iterator.next();
//            Box box3 = unionBox(box1, box2);
//            if (box3 != null) {
//                iterator.remove();
//                boxes.offer(box3);
//                none = 0;
//                break;
//            }
//        }
//        if (none > 0) {
//            boxes.offer(box1);
//        }
//    }
//}

//public static @Unmodifiable Collection<BoxFace> toBoxFaces(Box box) {
//    return List.of(
//      new BoxFace(Direction.WEST, box.withMaxX(box.minX)),
//      new BoxFace(Direction.DOWN, box.withMaxY(box.minY)),
//      new BoxFace(Direction.NORTH, box.withMinZ(box.minZ)),
//      new BoxFace(Direction.EAST, box.withMinX(box.maxX)),
//      new BoxFace(Direction.UP, box.withMinY(box.maxY)),
//      new BoxFace(Direction.SOUTH, box.withMinZ(box.maxZ))
//    );
//}

//public static @Unmodifiable Map<Direction, Box> to6Faces(Box box) {
//    return Map.of(
//      Direction.WEST, box.withMaxX(box.minX),
//      Direction.DOWN, box.withMaxY(box.minY),
//      Direction.NORTH, box.withMinZ(box.minZ),
//      Direction.EAST, box.withMinX(box.maxX),
//      Direction.UP, box.withMinY(box.maxY),
//      Direction.SOUTH, box.withMinZ(box.maxZ)
//    );
//}

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

//public static Map<CakeIngredient, Map<Direction, Collection<Box>>> simplify2(Map<CakeIngredient, Collection<Box>> content) {
//    Map<CakeIngredient, Map<Direction, Collection<Box>>> faceContent = new HashMap<>();
//    for (Map.Entry<CakeIngredient, Collection<Box>> entry : content.entrySet()) {
//        Map<Direction, Collection<Box>> d2faces = new HashMap<>();
//        faceContent.put(entry.getKey(), d2faces);
//        for (Direction direction : Direction.values()) {
//            d2faces.put(direction, new HashSet<>());
//        }
//        for (Box box : entry.getValue()) {
//            for (Map.Entry<Direction, Box> entry1 : to6Faces(box).entrySet()) {
//                Box face = entry1.getValue();
//                boolean b = true;
//                label1:
//                for (Map<Direction, Collection<Box>> d2faces2 : faceContent.values()) {
//                    for (Collection<Box> faces : d2faces2.values()) {
//                        if (faces.contains(face)) {
//                            faces.remove(face);
//                            b = false;
//                            break label1;
//                        }
//                    }
//                }
//                if (b) {
//                    d2faces.get(entry1.getKey()).add(face);
//                }
//            }
//        }
//    }
//    for (Map<Direction, Collection<Box>> d2faces : faceContent.values()) {
//        for (Map.Entry<Direction, Collection<Box>> entry : d2faces.entrySet()) {
//            Queue<Box> faces = new ArrayDeque<>(entry.getValue());
//            entry.setValue(faces);
//            int none = 0;
//            while (faces.size() > 1 && none < faces.size() * 2) {
//                none++;
//                Box face = faces.poll();
//                for (Iterator<Box> iterator = faces.iterator(); iterator.hasNext(); ) {
//                    Box face1 = iterator.next();
//                    Box face0 = unionBox3(face, face1);
//                    if (face0 != null) {
//                        iterator.remove();
//                        faces.offer(face0);
//                        none = 0;
//                        break;
//                    }
//                }
//                if (none > 0) {
//                    faces.offer(face);
//                }
//            }
//        }
//    }
//    return faceContent;
//}
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

//public static void simplify(Map<CakeIngredient, Collection<Box>> content) {
//    Map<CakeIngredient, Set<BoxFace>> facesMap = new HashMap<>();
//    for (Map.Entry<CakeIngredient, Collection<Box>> entry : content.entrySet()) {
//        Set<BoxFace> faces = new HashSet<>();
//        facesMap.put(entry.getKey(), faces);
//        for (Box box : entry.getValue()) {
//            for (BoxFace boxFace : toBoxFaces(box)) {
//                boolean contained = false;
//                for (Set<BoxFace> faces2 : facesMap.values()) {
//                    if (faces2.contains(boxFace)) {
//                        faces2.remove(boxFace);
//                        contained = true;
//                        break;
//                    }
//                }
//                if (!contained) {
//                    faces.add(boxFace);
//                }
//            }
//        }
//    }
//
//}

//public void emitBlockQuads1(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
//    var blockEntity = (PrintedCakeBE) blockView.getBlockEntity(pos);
//    if (blockEntity == null) return;//可能还没初始化，再等等
//    var bakedModel = blockEntity.bakedModel;
//    if (bakedModel == null) {
//        var content = blockEntity._content;
//        if (content == null) return;
//
//        content = new HashMap<>(content);
//        //        Queue<Box> allBoxes = new ArrayDeque<>();
//        //        for (var entry : content.entrySet()) {
//        //            Queue<Box> boxes = new ArrayDeque<>(entry.getValue());
//        //            entry.setValue(boxes);
//        ////            allBoxes.addAll(boxes);
//        //            unionBoxes(boxes);
//        //        }
//        //            unionBoxes(allBoxes);
//        //        Map<Box, Integer> allBoxesFaces0 = new HashMap<>();
//        //        for (Box box : allBoxes) {
//        //            Box boxFace;
//        //            int count;
//        //            boxFace = box.withMaxX(box.minX);
//        //            count = allBoxesFaces0.getOrDefault(boxFace, 0);
//        //            allBoxesFaces0.put(boxFace, count + 1);
//        //            boxFace = box.withMaxY(box.minY);
//        //            count = allBoxesFaces0.getOrDefault(boxFace, 0);
//        //            allBoxesFaces0.put(boxFace, count + 1);
//        //            boxFace = box.withMinZ(box.minZ);
//        //            count = allBoxesFaces0.getOrDefault(boxFace, 0);
//        //            allBoxesFaces0.put(boxFace, count + 1);
//        //            boxFace = box.withMinX(box.maxX);
//        //            count = allBoxesFaces0.getOrDefault(boxFace, 0);
//        //            allBoxesFaces0.put(boxFace, count + 1);
//        //            boxFace = box.withMinY(box.maxY);
//        //            count = allBoxesFaces0.getOrDefault(boxFace, 0);
//        //            allBoxesFaces0.put(boxFace, count + 1);
//        //            boxFace = box.withMinZ(box.maxZ);
//        //            count = allBoxesFaces0.getOrDefault(boxFace, 0);
//        //            allBoxesFaces0.put(boxFace, count + 1);
//        //        }
//        //        for (var iterator = allBoxesFaces0.entrySet().iterator(); iterator.hasNext(); ) {
//        //            Map.Entry<Box, Integer> entry = iterator.next();
//        //            if (entry.getValue() > 1) {
//        //                iterator.remove();
//        //            }
//        //        }
//
//
//        Map<@Nullable Direction, List<BakedQuad>> faces2quads = new HashMap<>();
//        faces2quads.put(null, new ArrayList<>());
//        for (Direction face : Direction.values()) {
//            faces2quads.put(face, new ArrayList<>());
//        }
//        Queue<Map.@Unmodifiable Entry<BakedQuad, float[]>> vertexes = new ArrayDeque<>();
//        var meshBuilder = IronBowlModel.getMeshBuilder();
//        QuadEmitter emitter = meshBuilder.getEmitter();
//        for (var entry : content.entrySet()) {
//            Queue<Box> boxes = new ArrayDeque<>(entry.getValue());
//            entry.setValue(boxes);
//            unionBoxes(boxes);
//            @NotNull Sprite sprite = getSprite(entry.getKey());
//            for (Box box : boxes) {
//                float minX = (float) box.minX;
//                float minY = (float) box.minY;
//                float minZ = (float) box.minZ;
//                float maxX = (float) box.maxX;
//                float maxY = (float) box.maxY;
//                float maxZ = (float) box.maxZ;
//                square(emitter, Direction.WEST, minZ, minY, maxZ, maxY, minX, sprite, faces2quads, vertexes);
//                square(emitter, Direction.EAST, 1 - maxZ, minY, 1 - minZ, maxY, 1 - maxX, sprite, faces2quads, vertexes);
//                square(emitter, Direction.DOWN, minX, minZ, maxX, maxZ, minY, sprite, faces2quads, vertexes);
//                square(emitter, Direction.UP, minX, 1 - maxZ, maxX, 1 - minZ, 1 - maxY, sprite, faces2quads, vertexes);
//                square(emitter, Direction.NORTH, 1 - maxX, minY, 1 - minX, maxY, minZ, sprite, faces2quads, vertexes);
//                square(emitter, Direction.SOUTH, minX, minY, maxX, maxY, 1 - maxZ, sprite, faces2quads, vertexes);
//            }
//        }
//        //            int none = 0;
//        //            while (vertexes.size() > 1 && none < vertexes.size() * 2) {
//        //                none++;
//        //                var entry1 = vertexes.poll();
//        //                Direction nominalFace1 = entry1.getKey().getFace();
//        //                float[] vertex1 = entry1.getValue();
//        //                Box box1 = new Box(vertex1[0], vertex1[1], 0, vertex1[2], vertex1[3], vertex1[4]);
//        //                CreateSDelight.LOGGER.info("box1=" + box1);
//        //                for (var iterator = vertexes.iterator(); iterator.hasNext(); ) {
//        //                    Map.Entry<BakedQuad, float[]> entry2 = iterator.next();
//        //                    Direction nominalFace2 = entry2.getKey().getFace();
//        //                    if (nominalFace1 == nominalFace2.getOpposite()) {
//        //                        float[] vertex2 = entry2.getValue();
//        //                        Box box2 = new Box(vertex2[0], vertex2[1], 0, vertex2[2], vertex2[3], 1 - vertex2[4]);
//        //                        //                        CreateSDelight.LOGGER.info("box2=" + box2);
//        //                        if (box1.equals(box2)) {
//        //                            iterator.remove();
//        //                            none = 0;
//        //                            break;
//        //                        }
//        //                    }
//        //                }
//        //                if (none > 0) {
//        //                    vertexes.offer(entry1);
//        //                }
//        //            }
//        List<BakedQuad> nullQuads = faces2quads.get(null);
//        for (Map.Entry<BakedQuad, float[]> vertex : vertexes) {
//            nullQuads.add(vertex.getKey());
//        }
//        blockEntity.bakedModel = bakedModel = new Baked(faces2quads);
//    }
//    context.bakedModelConsumer().accept(bakedModel);
//}
//
//public void emitBlockQuads2(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
//    var blockEntity = (PrintedCakeBE) blockView.getBlockEntity(pos);
//    if (blockEntity == null) return;//可能还没初始化，再等等
//    var bakedModel = blockEntity.bakedModel;
//    if (bakedModel != null) {
//        context.bakedModelConsumer().accept(bakedModel);
//        return;
//    }
//    var content = blockEntity._content;
//    if (content == null) return;
//    var faceContent = simplify2(content);
//    Map<@Nullable Direction, List<BakedQuad>> faces2quads = new HashMap<>();
//    faces2quads.put(null, new LinkedList<>());
//    for (Direction face : Direction.values()) {
//        faces2quads.put(face, new LinkedList<>());
//    }
//    var meshBuilder = IronBowlModel.getMeshBuilder();
//    QuadEmitter emitter = meshBuilder.getEmitter();
//    for (Map.Entry<CakeIngredient, Map<Direction, Collection<Box>>> entry : faceContent.entrySet()) {
//        Sprite sprite = getSprite(entry.getKey());
//        for (Map.Entry<Direction, Collection<Box>> entry2 : entry.getValue().entrySet()) {
//            Direction norminalFace = entry2.getKey();
//            switch (norminalFace) {
//                case WEST -> {
//                    for (Box face : entry2.getValue()) {
//                        square2(emitter, norminalFace, face.minZ, face.minY, face.maxZ, face.maxY, face.minX, sprite, faces2quads);
//                    }
//                }
//                case EAST -> {
//                    for (Box face : entry2.getValue()) {
//                        square2(emitter, norminalFace, 1 - face.maxZ, face.minY, 1 - face.minZ, face.maxY, 1 - face.maxX, sprite, faces2quads);
//                    }
//                }
//                case DOWN -> {
//                    for (Box face : entry2.getValue()) {
//                        square2(emitter, norminalFace, face.minX, face.minZ, face.maxX, face.maxZ, face.minY, sprite, faces2quads);
//                    }
//                }
//                case UP -> {
//                    for (Box face : entry2.getValue()) {
//                        square2(emitter, norminalFace, face.minX, 1 - face.maxZ, face.maxX, 1 - face.minZ, 1 - face.maxY, sprite, faces2quads);
//                    }
//                }
//                case NORTH -> {
//                    for (Box face : entry2.getValue()) {
//                        square2(emitter, norminalFace, 1 - face.maxX, face.minY, 1 - face.minX, face.maxY, face.minZ, sprite, faces2quads);
//                    }
//                }
//                case SOUTH -> {
//                    for (Box face : entry2.getValue()) {
//                        square2(emitter, norminalFace, face.minX, face.minY, face.maxX, face.maxY, 1 - face.maxZ, sprite, faces2quads);
//                    }
//                }
//            }
//        }
//    }
//    blockEntity.bakedModel = bakedModel = new Baked(faces2quads);
//    context.bakedModelConsumer().accept(bakedModel);
//}

//public final ThreadLocal<NbtCompound> theRendering = new ThreadLocal<>();

@Override
public boolean isVanillaAdapter() {
    return false;
}

public static BakedModel content2model(VoxelCake voxelCake) {
    var faceContent = content2faces(voxelCake);
    ListMultimap<@Nullable Direction, BakedQuad> faces2quads = MultimapBuilder.ListMultimapBuilder.hashKeys().linkedListValues().build();
    var meshBuilder = IronBowlModel.getMeshBuilder();
    QuadEmitter emitter = meshBuilder.getEmitter();
    for (var cell : faceContent.cellSet()) {
        Sprite sprite = getSprite(cell.getRowKey());
        Direction norminalFace = cell.getColumnKey();
        var boxes = cell.getValue();
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
    return new Baked(faces2quads);
}

@Override
public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
    var blockEntity = (PrintedCakeBE) blockView.getBlockEntity(pos);
    if (blockEntity == null) return;//可能还没初始化，再等等
    var bakedModel = blockEntity.getBakedModel();
    if (bakedModel == null) {//    if (blockEntity.caching) return;
        var cake = blockEntity.getVoxelCake();
        if (cake == null) return;
        bakedModel = content2model(cake);
        blockEntity.setBakedModel(bakedModel);
        //    blockEntity.caching = true;
        //    new Thread(() -> blockEntity.bakedModel = content2model(content, size)).start();
    }
    context.bakedModelConsumer().accept(bakedModel);
}

//public static final Map<NbtCompound, Object> CACHING = new WeakHashMap<>();
public static final Map<NbtCompound, BakedModel> CACHE = new WeakHashMap<>();

/**
 每一帧，游戏都会调用一次这个方法 */
@Override
public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
    var blockEntityTag = BlockItem.getBlockEntityNbt(stack);
    if (blockEntityTag == null) return;
    var bakedModel = CACHE.get(blockEntityTag);
    if (bakedModel == null) {
        var pair = PrintedCakeBE.nbt2content(blockEntityTag);
        if (pair == null) return;
        bakedModel = content2model(pair);
        CACHE.put(blockEntityTag, bakedModel);
    }
    context.bakedModelConsumer().accept(bakedModel);
}

/**
 当{@link #isVanillaAdapter()}返回{@code true}时，这个方法根本不会被游戏主动调用。 */
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

public static final class Unbaked implements UnbakedModel {

    @Override
    public Collection<Identifier> getModelDependencies() {
        return List.of();
    }

    @Override
    public void setParents(Function<Identifier, UnbakedModel> modelLoader) {

    }

    @Override
    public @NotNull BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        return new PrintedCakeModel();
    }
}

public static final class Baked implements BakedModel {

    public ListMultimap<@Nullable Direction, BakedQuad> faces2quads;

    public Baked(ListMultimap<Direction, BakedQuad> faces2quads) {
        this.faces2quads = faces2quads;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return faces2quads.get(face);
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
//@Deprecated
//public static final class BoxFace {
//
//    public final Direction face;
//    public final Box plane;
//
//    public BoxFace(Direction face, Box plane) {
//        this.face = face;
//        this.plane = plane;
//    }
//
//    @Override
//    public int hashCode() {
//        return plane.hashCode();
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        BoxFace that = (BoxFace) o;
//        return plane.equals(that.plane);
//    }
//
//}
//@Deprecated
//public static final class QuadData {
//
//    private final Direction nominalFace;
//    private final float left;
//    private final float bottom;
//    private final float right;
//    private final float top;
//    private final float depth;
//
//    public QuadData(Direction nominalFace, float left, float bottom, float right, float top, float depth) {
//        this.nominalFace = nominalFace;
//        this.left = left;
//        this.bottom = bottom;
//        this.right = right;
//        this.top = top;
//        this.depth = depth;
//    }
//
//    public Direction getNominalFace() {
//        return nominalFace;
//    }
//
//    public float getLeft() {
//        return left;
//    }
//
//    public float getBottom() {
//        return bottom;
//    }
//
//    public float getRight() {
//        return right;
//    }
//
//    public float getTop() {
//        return top;
//    }
//
//    public float getDepth() {
//        return depth;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (obj == this) return true;
//        if (obj == null || obj.getClass() != this.getClass()) return false;
//        var that = (QuadData) obj;
//        return /*Objects.equals(this.nominalFace, that.nominalFace) &&*/
//          Float.floatToIntBits(this.left) == Float.floatToIntBits(that.left) &&
//            Float.floatToIntBits(this.bottom) == Float.floatToIntBits(that.bottom) &&
//            Float.floatToIntBits(this.right) == Float.floatToIntBits(that.right) &&
//            Float.floatToIntBits(this.top) == Float.floatToIntBits(that.top) &&
//            Float.floatToIntBits(this.depth) == Float.floatToIntBits(that.depth);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(nominalFace, left, bottom, right, top, depth);
//    }
//
//    @Override
//    public String toString() {
//        return "QuadData[" +
//          "nominalFace=" + nominalFace + ", " +
//          "left=" + left + ", " +
//          "bottom=" + bottom + ", " +
//          "right=" + right + ", " +
//          "top=" + top + ", " +
//          "depth=" + depth + ']';
//    }
//
//
//}

}
