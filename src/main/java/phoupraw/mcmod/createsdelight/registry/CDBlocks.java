package phoupraw.mcmod.createsdelight.registry;

import com.nhoryzon.mc.farmersdelight.registry.BlocksRegistry;
import com.simibubi.create.AllBlocks;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import phoupraw.mcmod.common.api.Registries;
import phoupraw.mcmod.createsdelight.block.*;
import phoupraw.mcmod.createsdelight.datagen.*;
/**
 方块编写流程：
 <ol>
 <li>若自定义方块，则在{@link phoupraw.mcmod.createsdelight.block}创建方块类，继承{@link Block}；推荐重载无参构造器。<br/>
 <li>在{@link CDIdentifiers}创建{@link Identifier}。<br/>
 <li>在{@link CDBlocks}创建方块<b>并注册</b>。<br/>
 <li>在{@link CDItems}按照创建物品的流程创建{@link BlockItem}或自定义物品<b>并注册</b>。<br/>
 <li>在{@link MyChineseProvider}和{@link MyEnglishProvider}添加翻译。<br/>
 <li>在{@link MyModelProvider}添加方块状态和物品模型。<br/>
 <li>在{@link MyBlockLootTableProvider}添加战利品表。<br/>
 <li>在{@link MyBlockTagProvider}添加标签。<br/>
 <li>运行数据生成器。<br/>
 <li>在{@code src/main/resources/assets/createsdelight/models/block}创建方块模型。<br/>
 <li>若贴图包含透明像素，需在{@link CDClientModInitializer}添加{@link BlockRenderLayerMap}。<br/>
 <li>运行客户端，检查方块效果是否如预期。<br/>
 <li>在{@code ChangeLog.md}添加更新日志。<br/>
 <li>提交git。
 </ol>
 @see CDBlockEntityTypes */
public final class CDBlocks {
    public static final PanBlock PAN = new PanBlock(FabricBlockSettings.copyOf(BlocksRegistry.SKILLET.get()));
    public static final GrillBlock GRILL = new GrillBlock(FabricBlockSettings.copyOf(PAN).nonOpaque());
    public static final SprinklerBlock SPRINKLER = new SprinklerBlock(FabricBlockSettings.copyOf(AllBlocks.MECHANICAL_PRESS.get()));
    public static final BambooSteamerBlock BAMBOO_STEAMER = new BambooSteamerBlock(FabricBlockSettings.copyOf(BlocksRegistry.BASKET.get()));
    public static final SmartDrainBlock SMART_DRAIN = new SmartDrainBlock(FabricBlockSettings.copyOf(AllBlocks.ITEM_DRAIN.get()));
    public static final CopperTunnelBlock COPPER_TUNNEL = new CopperTunnelBlock(FabricBlockSettings.copyOf(AllBlocks.BRASS_TUNNEL.get()));
    public static final MultifuncBasinBlock MULTIFUNC_BASIN = new MultifuncBasinBlock(FabricBlockSettings.copyOf(AllBlocks.BASIN.get()));
    public static final VerticalCutterBlock VERTICAL_CUTTER = new VerticalCutterBlock(FabricBlockSettings.copyOf(AllBlocks.MECHANICAL_PRESS.get()));
    public static final PressureCookerBlock PRESSURE_COOKER = new PressureCookerBlock(FabricBlockSettings.copyOf(AllBlocks.MECHANICAL_PRESS.get()));
    public static final MincerBlock MINCER = new MincerBlock(FabricBlockSettings.copyOf(AllBlocks.MECHANICAL_MIXER.get()));
    public static final SkewerBlock SKEWER = new SkewerBlock();
    public static final MyBasinBlock BASIN = new MyBasinBlock();
    public static final SkewerPlateBlock SKEWER_PLATE = new SkewerPlateBlock();
    public static final OvenBlock OVEN = new OvenBlock();
    public static final Block IRON_BAR_SKEWER = new IronBarSkewerBlock();

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

    static {
        Registries.register(CDIdentifiers.PAN, PAN);
        Registries.register(CDIdentifiers.GRILL, GRILL);
        Registries.register(CDIdentifiers.SPRINKLER, SPRINKLER);
        Registries.register(CDIdentifiers.BAMBOO_STEAMER, BAMBOO_STEAMER);
        Registries.register(CDIdentifiers.SMART_DRAIN, SMART_DRAIN);
        Registries.register(CDIdentifiers.COPPER_TUNNEL, COPPER_TUNNEL);
        Registries.register(CDIdentifiers.MULTIFUNC_BASIN, MULTIFUNC_BASIN);
        Registries.register(CDIdentifiers.VERTICAL_CUTTER, VERTICAL_CUTTER);
        Registries.register(CDIdentifiers.PRESSURE_COOKER, PRESSURE_COOKER);
        Registries.register(CDIdentifiers.MINCER, MINCER);
        Registries.register(CDIdentifiers.SKEWER, SKEWER);
        Registries.register(CDIdentifiers.BASIN, BASIN);
        Registries.register(CDIdentifiers.SKEWER_PLATE, SKEWER_PLATE);
        Registries.register(CDIdentifiers.OVEN, OVEN);
        Registries.register(CDIdentifiers.IRON_BAR_SKEWER, IRON_BAR_SKEWER);

        Registries.register(CDIdentifiers.JELLY_BEANS, JELLY_BEANS);
        Registries.register(CDIdentifiers.JELLY_BEANS_CAKE, JELLY_BEANS_CAKE);
        Registries.register(CDIdentifiers.SWEET_BERRIES_CAKE, SWEET_BERRIES_CAKE);
        Registries.register(CDIdentifiers.BASQUE_CAKE, BASQUE_CAKE);
        Registries.register(CDIdentifiers.SWEET_BERRIES_CAKE_S, SWEET_BERRIES_CAKE_S);
        Registries.register(CDIdentifiers.BROWNIE, BROWNIE);
        Registries.register(CDIdentifiers.APPLE_CREAM_CAKE, APPLE_CREAM_CAKE);
        Registries.register(CDIdentifiers.APPLE_CAKE, APPLE_CAKE);
        Registries.register(CDIdentifiers.CARROT_CREAM_CAKE, CARROT_CREAM_CAKE);
        Registries.register(CDIdentifiers.SMALL_CHOCOLATE_CREAM_CAKE, SMALL_CHOCOLATE_CREAM_CAKE);
        Registries.register(CDIdentifiers.MEDIUM_CHOCOLATE_CREAM_CAKE, MEDIUM_CHOCOLATE_CREAM_CAKE);
        Registries.register(CDIdentifiers.BIG_CHOCOLATE_CREAM_CAKE, BIG_CHOCOLATE_CREAM_CAKE);
        Registries.register(CDIdentifiers.CHOCOLATE_ANTHEMY_CAKE, CHOCOLATE_ANTHEMY_CAKE);
        Registries.register(CDIdentifiers.PRINTED_CAKE, PRINTED_CAKE);
    }
    private CDBlocks() {
    }
}
