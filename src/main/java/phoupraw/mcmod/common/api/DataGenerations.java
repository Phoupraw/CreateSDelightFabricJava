package phoupraw.mcmod.common.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.server.BlockLootTableGenerator;
import org.jetbrains.annotations.Contract;
/**
 一些用于数据生成器的快捷方法。
 @since 1.1.0-pre1 */
public final class DataGenerations {
    /**
     方块掉落它本身。{@link BlockLootTableGenerator#addDrop(Block)}的批量调用。
     @param generator 被调用{@link BlockLootTableGenerator#addDrop(Block)}的对象
     @param blocks 作为{@link BlockLootTableGenerator#addDrop(Block)}参数的方块
     @since 1.1.0-pre1
     */
    @Contract(mutates = "param1")
    public static void addDrop(BlockLootTableGenerator generator, Block... blocks) {
        for (Block block : blocks) generator.addDrop(block);
    }

    /**
     不要为方块对应的物品生成直接继承方块模型的物品模型。{@link BlockStateModelGenerator#excludeFromSimpleItemModelGeneration}的批量调用。
     @param generator 被调用{@link BlockStateModelGenerator#excludeFromSimpleItemModelGeneration}的对象
     @param blocks 作为{@link BlockStateModelGenerator#excludeFromSimpleItemModelGeneration}参数的方块
     @since 1.1.0-pre1
     */
    @Contract(mutates = "param1")
    @Environment(EnvType.CLIENT)
    public static void excludeFromSimpleItemModelGeneration(BlockStateModelGenerator generator, Block... blocks) {
        for (Block block : blocks) generator.excludeFromSimpleItemModelGeneration(block);
    }

    /**
     生成最简单的blockstates文件，只有一个状态，只有一个模型，模型id就是block/方块id。{@link BlockStateModelGenerator#registerSimpleState}的批量调用。
     @param generator 被调用{@link BlockStateModelGenerator#registerSimpleState}的对象
     @param blocks 作为{@link BlockStateModelGenerator#registerSimpleState}参数的方块
     @since 1.1.0-pre1
     */
    @Contract(mutates = "param1")
    @Environment(EnvType.CLIENT)
    public static void registerSimpleState(BlockStateModelGenerator generator, Block... blocks) {
        for (Block block : blocks) generator.registerSimpleState(block);
    }

    private DataGenerations() {

    }
}
