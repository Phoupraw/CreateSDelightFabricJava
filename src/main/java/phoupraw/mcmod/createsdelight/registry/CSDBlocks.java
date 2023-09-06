package phoupraw.mcmod.createsdelight.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import phoupraw.mcmod.createsdelight.block.CakeOvenBlock;
import phoupraw.mcmod.createsdelight.block.InProdCakeBlock;
import phoupraw.mcmod.createsdelight.block.PrintedCakeBlock;

///**
// * 方块编写流程：
// * <ol>
// * <li>若自定义方块，则在{@link phoupraw.mcmod.createsdelight.block}创建方块类，继承{@link Block}；推荐重载无参构造器。<br/>
// * <li>在{@link CSDIdentifiers}创建{@link Identifier}。<br/>
// * <li>在{@link CSDBlocks}创建方块<b>并注册</b>。<br/>
// * <li>在{@link CSDItems}按照创建物品的流程创建{@link BlockItem}或自定义物品<b>并注册</b>。<br/>
// * <li>在{@link CSDChineseProvider}和{@link CSDEnglishProvider}添加翻译。<br/>
// * <li>在{@link CSDModelProvider}添加方块状态和物品模型。<br/>
// * <li>在{@link CSDBlockLootTableProvider}添加战利品表。<br/>
// * <li>在{@link CSDBlockTagProvider}添加标签。<br/>
// * <li>运行数据生成器。<br/>
// * <li>在{@code src/main/resources/assets/createsdelight/models/block}创建方块模型。<br/>
// * <li>若贴图包含透明像素，需在{@link CSDClientModInitializer}添加{@link BlockRenderLayerMap}。<br/>
// * <li>运行客户端，检查方块效果是否如预期。<br/>
// * <li>在{@code ChangeLog.md}添加更新日志。<br/>
// * <li>提交git。
// * </ol>
// * @see CSDBlockEntityTypes
// */
public final class CSDBlocks {

    //机器
    public static final Block CAKE_OVEN = new CakeOvenBlock();
    public static final Block PRINTED_CAKE = new PrintedCakeBlock();
    public static final Block IN_PROD_CAKE = new InProdCakeBlock(FabricBlockSettings.copyOf(PRINTED_CAKE).noCollision());

    //蛋糕材料
    public static final Block CHOCOLATE_BLOCK = new Block(FabricBlockSettings.create().hardness(1).mapColor(MapColor.BROWN));
    public static final Block CREAM_BLOCK = new Block(FabricBlockSettings.create().breakInstantly().mapColor(MapColor.WHITE));
    static {
        register(CSDIdentifiers.CAKE_OVEN, CAKE_OVEN);
        register(CSDIdentifiers.PRINTED_CAKE, PRINTED_CAKE);
        register(CSDIdentifiers.IN_PROD_CAKE, IN_PROD_CAKE);
        register(CSDIdentifiers.CHOCOLATE_BLOCK, CHOCOLATE_BLOCK);
        register(CSDIdentifiers.CREAM_BLOCK, CREAM_BLOCK);
    }

    private static <T extends Block> void register(Identifier id, T block) {
        Registry.register(Registries.BLOCK, id, block);
    }

    private CSDBlocks() {
    }

}
