package phoupraw.mcmod.createsdelight.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import phoupraw.mcmod.createsdelight.datagen.CSDItemTagProvider;
import phoupraw.mcmod.createsdelight.datagen.CSDRecipeProvider;
import phoupraw.mcmod.createsdelight.datagen.client.CSDChineseProvider;
import phoupraw.mcmod.createsdelight.datagen.client.CSDEnglishProvider;
import phoupraw.mcmod.createsdelight.datagen.client.CSDModelProvider;
import phoupraw.mcmod.createsdelight.item.PrintedCakeItem;

/**
 * 物品编写流程：
 * <ol>
 * <li>若自定义物品，则在{@link phoupraw.mcmod.createsdelight.item}创建物品类，继承{@link Item}；推荐重载无参构造器。<br/>
 * <li>在{@link CSDIdentifiers}创建{@link Identifier}。<br/>
 * <li>在{@link CSDItems}创建物品<b>并注册</b>。<br/>
 * <li>若不为{@link BlockItem}，则在{@link CSDChineseProvider}和{@link CSDEnglishProvider}添加翻译。<br/>
 * <li>若不为{@link BlockItem}，则在{@link CSDModelProvider}添加模型。<br/>
 * <li>在{@link CSDRecipeProvider}添加配方。<br/>
 * <li>在{@link CSDItemTagProvider}添加标签。<br/>
 * <li>运行数据生成器。<br/>
 * <li>在{@code src/main/resources/assets/createsdelight/textures/item}创建纹理。<br/>
 * <li>运行客户端，检查物品效果是否如预期。<br/>
 * <li>在{@code ChangeLog.md}添加更新日志。<br/>
 * <li>提交git。
 * </ol>
 */
public final class CSDItems {

    //方块
    public static final BlockItem CAKE_OVEN = new BlockItem(CSDBlocks.CAKE_OVEN, new FabricItemSettings());
    public static final BlockItem PRINTED_CAKE = new PrintedCakeItem();

    //不可食用物品
    public static final Item EGG_SHELL = new Item(new FabricItemSettings());
    public static final Item BUCKETED_EGG_LIQUID = new Item(new FabricItemSettings().maxCount(1));

    //蛋糕材料
    public static final BlockItem MILK = new BlockItem(CSDBlocks.MILK, new FabricItemSettings());
    public static final BlockItem CHOCOLATE_BLOCK = new BlockItem(CSDBlocks.CHOCOLATE_BLOCK, new FabricItemSettings());
    public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
      .displayName(Text.translatable(CSDIdentifiers.ITEM_GROUP.toTranslationKey("itemGroup")))
      .icon(Items.CAKE::getDefaultStack)
      .entries(CSDItems::addItemGroupEntries)
      .build();
    static {


        register(CSDIdentifiers.CAKE_OVEN, CAKE_OVEN);
        register(CSDIdentifiers.PRINTED_CAKE, PRINTED_CAKE);
        register(CSDIdentifiers.EGG_SHELL, EGG_SHELL);
        register(CSDIdentifiers.MILK, MILK);
        register(CSDIdentifiers.CHOCOLATE_BLOCK, CHOCOLATE_BLOCK);
        register(CSDIdentifiers.BUCKETED_EGG_LIQUID, BUCKETED_EGG_LIQUID);
        Registry.register(Registries.ITEM_GROUP, CSDIdentifiers.ITEM_GROUP, ITEM_GROUP);
    }

    @Contract("_, _ -> param2")
    public static <T extends Item> T register(Identifier id, T item) {
        return Registry.register(Registries.ITEM, id, item);
    }

    private static void addItemGroupEntries(ItemGroup.DisplayContext displayContext, ItemGroup.Entries entries) {
        for (Item item : new Item[]{CSDItems.CAKE_OVEN, CSDItems.CHOCOLATE_BLOCK, CSDItems.BUCKETED_EGG_LIQUID, CSDItems.EGG_SHELL}) {
            entries.add(item);
        }
    }

    private CSDItems() {
    }

}
