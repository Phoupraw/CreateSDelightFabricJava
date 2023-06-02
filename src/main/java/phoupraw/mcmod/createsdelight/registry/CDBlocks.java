package phoupraw.mcmod.createsdelight.registry;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import phoupraw.mcmod.createsdelight.block.*;
import phoupraw.mcmod.createsdelight.datagen.*;
/**
 方块编写流程：
 <ol>
 <li>若自定义方块，则在{@link phoupraw.mcmod.createsdelight.block}创建方块类，继承{@link Block}；推荐重载无参构造器。<br/>
 <li>在{@link CDIdentifiers}创建{@link Identifier}。<br/>
 <li>在{@link CDBlocks}创建方块<b>并注册</b>。<br/>
 <li>在{@link CDItems}按照创建物品的流程创建{@link BlockItem}或自定义物品<b>并注册</b>。<br/>
 <li>在{@link CDChineseProvider}和{@link CDEnglishProvider}添加翻译。<br/>
 <li>在{@link CDModelProvider}添加方块状态和物品模型。<br/>
 <li>在{@link CDBlockLootTableProvider}添加战利品表。<br/>
 <li>在{@link CDBlockTagProvider}添加标签。<br/>
 <li>运行数据生成器。<br/>
 <li>在{@code src/main/resources/assets/createsdelight/models/block}创建方块模型。<br/>
 <li>若贴图包含透明像素，需在{@link CDClientModInitializer}添加{@link BlockRenderLayerMap}。<br/>
 <li>运行客户端，检查方块效果是否如预期。<br/>
 <li>在{@code ChangeLog.md}添加更新日志。<br/>
 <li>提交git。
 </ol>
 @see CDBETypes */
public final class CDBlocks {
    //机器
    public static final Block CAKE_OVEN = new CakeOvenBlock();

    // 蛋糕
    public static final JellyBeansBlock JELLY_BEANS = new JellyBeansBlock();
    public static final JellyBeansCakeBlock JELLY_BEANS_CAKE = new JellyBeansCakeBlock();
    public static final SweetBerriesCakeBlock SWEET_BERRIES_CAKE = new SweetBerriesCakeBlock();
    public static final Block BASQUE_CAKE = new BasqueCakeBlock();
    public static final Block SWEET_BERRIES_CAKE_S = new SweetBerriesCakeSBlock();
    public static final Block BROWNIE = new BrownieBlock();
    public static final Block APPLE_CREAM_CAKE = new AppleCreamCakeBlock();
    public static final Block APPLE_CAKE = new AppleCakeBlock();
    public static final Block CARROT_CREAM_CAKE = new CarrotCreamCakeBlock();
    public static final Block SMALL_CHOCOLATE_CREAM_CAKE = new SmallChocolateCreamCakeBlock();
    public static final Block MEDIUM_CHOCOLATE_CREAM_CAKE = new MediumChocolateCreamCakeBlock();
    public static final Block BIG_CHOCOLATE_CREAM_CAKE = new BigChocolateCreamCakeBlock();
    public static final Block CHOCOLATE_ANTHEMY_CAKE = new ChocolateAnthemyCakeBlock();
    public static final Block PRINTED_CAKE = new PrintedCakeBlock();

    //蛋糕材料
    public static final Block MILK = new Block(FabricBlockSettings.of(Material.ORGANIC_PRODUCT, MapColor.WHITE).breakInstantly());
    public static final Block CHOCOLATE = new Block(FabricBlockSettings.copyOf(MILK));
    static {
        register(CDIdentifiers.CAKE_OVEN, CAKE_OVEN);

        register(CDIdentifiers.JELLY_BEANS, JELLY_BEANS);
        register(CDIdentifiers.JELLY_BEANS_CAKE, JELLY_BEANS_CAKE);
        register(CDIdentifiers.SWEET_BERRIES_CAKE, SWEET_BERRIES_CAKE);
        register(CDIdentifiers.BASQUE_CAKE, BASQUE_CAKE);
        register(CDIdentifiers.SWEET_BERRIES_CAKE_S, SWEET_BERRIES_CAKE_S);
        register(CDIdentifiers.BROWNIE, BROWNIE);
        register(CDIdentifiers.APPLE_CREAM_CAKE, APPLE_CREAM_CAKE);
        register(CDIdentifiers.APPLE_CAKE, APPLE_CAKE);
        register(CDIdentifiers.CARROT_CREAM_CAKE, CARROT_CREAM_CAKE);
        register(CDIdentifiers.SMALL_CHOCOLATE_CREAM_CAKE, SMALL_CHOCOLATE_CREAM_CAKE);
        register(CDIdentifiers.MEDIUM_CHOCOLATE_CREAM_CAKE, MEDIUM_CHOCOLATE_CREAM_CAKE);
        register(CDIdentifiers.BIG_CHOCOLATE_CREAM_CAKE, BIG_CHOCOLATE_CREAM_CAKE);
        register(CDIdentifiers.CHOCOLATE_ANTHEMY_CAKE, CHOCOLATE_ANTHEMY_CAKE);
        register(CDIdentifiers.PRINTED_CAKE, PRINTED_CAKE);

        register(CDIdentifiers.MILK, MILK);
        register(CDIdentifiers.CHOCOLATE, CHOCOLATE);
    }

    private static <T extends Block> void register(Identifier id, T block) {
        Registry.register(Registry.BLOCK, id, block);
    }

    private CDBlocks() {
    }
}
