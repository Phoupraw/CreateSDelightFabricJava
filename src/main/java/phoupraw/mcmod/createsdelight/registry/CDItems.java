package phoupraw.mcmod.createsdelight.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import phoupraw.mcmod.createsdelight.datagen.CDItemTagProvider;
import phoupraw.mcmod.createsdelight.datagen.CDRecipeProvider;
import phoupraw.mcmod.createsdelight.datagen.client.CDChineseProvider;
import phoupraw.mcmod.createsdelight.datagen.client.CDEnglishProvider;
import phoupraw.mcmod.createsdelight.datagen.client.CDModelProvider;
import phoupraw.mcmod.createsdelight.item.PrintedCakeItem;

/**
 物品编写流程：
 <ol>
 <li>若自定义物品，则在{@link phoupraw.mcmod.createsdelight.item}创建物品类，继承{@link Item}；推荐重载无参构造器。<br/>
 <li>在{@link CDIdentifiers}创建{@link Identifier}。<br/>
 <li>在{@link CDItems}创建物品<b>并注册</b>。<br/>
 <li>若不为{@link BlockItem}，则在{@link CDChineseProvider}和{@link CDEnglishProvider}添加翻译。<br/>
 <li>若不为{@link BlockItem}，则在{@link CDModelProvider}添加模型。<br/>
 <li>在{@link CDRecipeProvider}添加配方。<br/>
 <li>在{@link CDItemTagProvider}添加标签。<br/>
 <li>运行数据生成器。<br/>
 <li>在{@code src/main/resources/assets/createsdelight/textures/item}创建纹理。<br/>
 <li>运行客户端，检查物品效果是否如预期。<br/>
 <li>在{@code ChangeLog.md}添加更新日志。<br/>
 <li>提交git。
 </ol> */
public final class CDItems {

    public static final ItemGroup ITEM_GROUP = ItemGroup.create(ItemGroup.Row.TOP, 0)
      .icon(CDItems::stupidJavaCompiler)
      //.appendItems(CDItems::appendItems)
      .build();
    static {
        Registry.register(Registries.ITEM_GROUP, CDIdentifiers.ITEM_GROUP, ITEM_GROUP);

    }
    //方块
    public static final BlockItem CAKE_OVEN = new BlockItem(CDBlocks.CAKE_OVEN, newSettings());

    public static final BlockItem PRINTED_CAKE = new PrintedCakeItem();

    //不可食用物品
    public static final Item EGG_SHELL = new Item(newSettings());
    public static final Item EGG_DOUGH = new Item(newSettings());

    //public static final SequencedAssemblyItem SWEET_BERRIES_CAKE_S_0 = new SequencedAssemblyItem(newSettings());
    //public static final SequencedAssemblyItem RAW_BASQUE_CAKE_0 = new SequencedAssemblyItem(newSettings());
    //public static final SequencedAssemblyItem BROWNIE_0 = new SequencedAssemblyItem(newSettings());
    //public static final SequencedAssemblyItem APPLE_CREAM_CAKE_0 = new SequencedAssemblyItem(newSettings());
    //public static final SequencedAssemblyItem CARROT_CREAM_CAKE_0 = new SequencedAssemblyItem(newSettings());

    //蛋糕材料
    public static final BlockItem MILK = new BlockItem(CDBlocks.MILK, newSettings());
    public static final BlockItem CHOCOLATE = new BlockItem(CDBlocks.CHOCOLATE, newSettings());
    static {

        //register(CDIdentifiers.KELP_ASH, KELP_ASH);
        //register(CDIdentifiers.CAKE_BASE, CAKE_BASE);
        //register(CDIdentifiers.CAKE_BASE_SLICE, CAKE_BASE_SLICE);
        register(CDIdentifiers.CAKE_OVEN, CAKE_OVEN);

        //register(CDIdentifiers.JELLY_BEANS, JELLY_BEANS);
        //register(CDIdentifiers.JELLY_BEANS_CAKE, JELLY_BEANS_CAKE);
        //register(CDIdentifiers.SWEET_BERRIES_CAKE, SWEET_BERRIES_CAKE);
        //register(CDIdentifiers.BASQUE_CAKE, BASQUE_CAKE);
        //register(CDIdentifiers.SWEET_BERRIES_CAKE_S, SWEET_BERRIES_CAKE_S);
        //register(CDIdentifiers.BROWNIE, BROWNIE);
        //register(CDIdentifiers.APPLE_CREAM_CAKE, APPLE_CREAM_CAKE);
        //register(CDIdentifiers.APPLE_CAKE, APPLE_CAKE);
        //register(CDIdentifiers.CARROT_CREAM_CAKE, CARROT_CREAM_CAKE);
        //register(CDIdentifiers.SMALL_CHOCOLATE_CREAM_CAKE, SMALL_CHOCOLATE_CREAM_CAKE);
        //register(CDIdentifiers.MEDIUM_CHOCOLATE_CREAM_CAKE, MEDIUM_CHOCOLATE_CREAM_CAKE);
        //register(CDIdentifiers.BIG_CHOCOLATE_CREAM_CAKE, BIG_CHOCOLATE_CREAM_CAKE);
        //register(CDIdentifiers.CHOCOLATE_ANTHEMY_CAKE, CHOCOLATE_ANTHEMY_CAKE);
        register(CDIdentifiers.PRINTED_CAKE, PRINTED_CAKE);

        //register(CDIdentifiers.BUCKETED_SUNFLOWER_OIL, BUCKETED_SUNFLOWER_OIL);
        //register(CDIdentifiers.BOTTLED_SUNFLOWER_OIL, BOTTLED_SUNFLOWER_OIL);
        register(CDIdentifiers.EGG_SHELL, EGG_SHELL);
        register(CDIdentifiers.EGG_DOUGH, EGG_DOUGH);
        //register(CDIdentifiers.RAW_BASQUE_CAKE, RAW_BASQUE_CAKE);
        //register(CDIdentifiers.BUCKETED_PUMPKIN_OIL, BUCKETED_PUMPKIN_OIL);
        //register(CDIdentifiers.IRON_BOWL, IRON_BOWL);
        //register(CDIdentifiers.CAKE_BLUEPRINT, CAKE_BLUEPRINT);

        //register(CDIdentifiers.CHOCOLATE_CAKE_BASE, CHOCOLATE_CAKE_BASE);
        //
        //register(CDIdentifiers.JELLY_BEANS_CAKE_0, JELLY_BEANS_CAKE_0);
        //register(CDIdentifiers.SWEET_BERRIES_CAKE_0, SWEET_BERRIES_CAKE_0);
        //register(CDIdentifiers.SWEET_BERRIES_CAKE_S_0, SWEET_BERRIES_CAKE_S_0);
        //register(CDIdentifiers.RAW_BASQUE_CAKE_0, RAW_BASQUE_CAKE_0);
        //register(CDIdentifiers.BROWNIE_0, BROWNIE_0);
        //register(CDIdentifiers.APPLE_CREAM_CAKE_0, APPLE_CREAM_CAKE_0);
        //register(CDIdentifiers.CARROT_CREAM_CAKE_0, CARROT_CREAM_CAKE_0);
        //
        register(CDIdentifiers.MILK, MILK);
        register(CDIdentifiers.CHOCOLATE, CHOCOLATE);
        //
        //register(CDIdentifiers.SUNFLOWER_KERNELS, SUNFLOWER_KERNELS);
    }

    @Contract(pure = true, value = "->new")
    @ApiStatus.Internal
    public static FabricItemSettings newSettings() {
        return new FabricItemSettings()/*.group(ITEM_GROUP)*/;
    }

    //private static void appendItems(List<ItemStack> itemStacks, ItemGroup itemGroup) {
    //    for (Item item : new Item[]{
    //      CAKE_OVEN,
    //      MILK, CHOCOLATE,
    //      BUCKETED_SUNFLOWER_OIL, BUCKETED_PUMPKIN_OIL,
    //      BOTTLED_SUNFLOWER_OIL,
    //      KELP_ASH, RAW_BASQUE_CAKE
    //    }) {
    //        itemStacks.add(item.getDefaultStack());
    //    }
    //}

    private static ItemStack stupidJavaCompiler() {
        return Items.CAKE.getDefaultStack();//JELLY_BEANS_CAKE.getDefaultStack();
    }

    @Contract("_, _ -> param2")
    public static <T extends Item> T register(Identifier id, T item) {
        return Registry.register(Registries.ITEM, id, item);
    }

    private CDItems() {
    }

}
