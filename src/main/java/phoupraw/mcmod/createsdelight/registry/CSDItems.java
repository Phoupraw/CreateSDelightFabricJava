package phoupraw.mcmod.createsdelight.registry;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import phoupraw.mcmod.createsdelight.datagen.CSDItemTagProvider;
import phoupraw.mcmod.createsdelight.datagen.CSDRecipeProvider;
import phoupraw.mcmod.createsdelight.datagen.client.CSDChineseProvider;
import phoupraw.mcmod.createsdelight.datagen.client.CSDEnglishProvider;
import phoupraw.mcmod.createsdelight.datagen.client.CSDModelProvider;
import phoupraw.mcmod.createsdelight.item.PrintedCakeItem;
import phoupraw.mcmod.createsdelight.item.ThickFluidBucketItem;
import phoupraw.mcmod.createsdelight.misc.MadeVoxels;

/**
 物品编写流程：
 <ol>
 <li>若自定义物品，则在{@link phoupraw.mcmod.createsdelight.item}创建物品类，继承{@link Item}；推荐重载无参构造器。<br/>
 <li>在{@link CSDIdentifiers}创建{@link Identifier}。<br/>
 <li>在{@link CSDItems}创建物品<b>并注册</b>。<br/>
 <li>若不为{@link BlockItem}，则在{@link CSDChineseProvider}和{@link CSDEnglishProvider}添加翻译。<br/>
 <li>若不为{@link BlockItem}，则在{@link CSDModelProvider}添加模型。<br/>
 <li>在{@link CSDRecipeProvider}添加配方。<br/>
 <li>在{@link CSDItemTagProvider}添加标签。<br/>
 <li>运行数据生成器。<br/>
 <li>在{@code src/main/resources/assets/createsdelight/textures/item}创建纹理。<br/>
 <li>运行客户端，检查物品效果是否如预期。<br/>
 <li>在{@code ChangeLog.md}添加更新日志。<br/>
 <li>提交git。
 </ol> */
public final class CSDItems {
    //方块
    public static final BlockItem CAKE_OVEN = new BlockItem(CSDBlocks.CAKE_OVEN, new FabricItemSettings());
    public static final BlockItem VOXEL_MAKER = register(CSDIdentifiers.VOXEL_MAKER, new BlockItem(CSDBlocks.VOXEL_MAKER, new FabricItemSettings()));
    public static final BlockItem MADE_VOXEL = register(CSDIdentifiers.MADE_VOXEL, new BlockItem(CSDBlocks.MADE_VOXEL, new FabricItemSettings()));
    public static final BlockItem PRINTED_CAKE = new PrintedCakeItem();
    //蛋糕材料方块
    public static final BlockItem CHOCOLATE_BLOCK = new BlockItem(CSDBlocks.CHOCOLATE_BLOCK, new FabricItemSettings());
    public static final BlockItem WHEAT_CAKE_BASE_BLOCK = register(CSDIdentifiers.WHEAT_CAKE_BASE_BLOCK, new BlockItem(CSDBlocks.WHEAT_CAKE_BASE_BLOCK, new FabricItemSettings()));
    public static final BlockItem BUTTER_BLOCK = register(CSDIdentifiers.BUTTER_BLOCK, new BlockItem(CSDBlocks.BUTTER_BLOCK, new FabricItemSettings()));
    //桶装类细雪流体
    public static final BlockItem BUCKETED_WHEAT_PASTE = register(CSDIdentifiers.BUCKETED_WHEAT_PASTE, new ThickFluidBucketItem(CSDBlocks.WHEAT_PASTE, new FabricItemSettings().maxCount(8)));
    public static final BlockItem BUCKETED_APPLE_JAM = register(CSDIdentifiers.BUCKETED_APPLE_JAM, new ThickFluidBucketItem(CSDBlocks.APPLE_JAM, new FabricItemSettings().maxCount(8)));
    public static final BlockItem BUCKETED_CREAM = register(CSDIdentifiers.BUCKETED_CREAM, new ThickFluidBucketItem(CSDBlocks.CREAM, new FabricItemSettings().maxCount(8)));
    //桶装虚拟流体
    public static final Item BUCKETED_EGG_LIQUID = new Item(new FabricItemSettings().maxCount(8));
    //纯材料
    public static final Item EGG_SHELL = new Item(new FabricItemSettings());
    public static final Item KELP_ASH = new Item(new FabricItemSettings());
    public static final Item BUTTER_NUGGET = register(CSDIdentifiers.BUTTER_NUGGET, new Item(new FabricItemSettings()));
    public static final Item BUTTER_INGOT = register(CSDIdentifiers.BUTTER_INGOT, new Item(new FabricItemSettings()));
    public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
      .displayName(Text.translatable(CSDIdentifiers.ITEM_GROUP.toTranslationKey("itemGroup")))
      .icon(Items.CAKE::getDefaultStack)
      .entries(CSDItems::addItemGroupEntries)
      .build();
    static {
        register(CSDIdentifiers.CAKE_OVEN, CAKE_OVEN);
        register(CSDIdentifiers.PRINTED_CAKE, PRINTED_CAKE);
        register(CSDIdentifiers.EGG_SHELL, EGG_SHELL);
        register(CSDIdentifiers.CHOCOLATE_BLOCK, CHOCOLATE_BLOCK);
        register(CSDIdentifiers.BUCKETED_EGG_LIQUID, BUCKETED_EGG_LIQUID);
        register(CSDIdentifiers.KELP_ASH, KELP_ASH);
        Registry.register(Registries.ITEM_GROUP, CSDIdentifiers.ITEM_GROUP, ITEM_GROUP);
    }
    @Contract("_, _ -> param2")
    public static <T extends Item> T register(Identifier id, T item) {
        return Registry.register(Registries.ITEM, id, item);
    }
    private static void addItemGroupEntries(ItemGroup.DisplayContext displayContext, ItemGroup.Entries entries) {
        try {
            for (var entry : MadeVoxels.PREDEFINED.entrySet()) {
                NbtCompound blockEntityTag = new StringNbtReader(new StringReader(entry.getValue())).parseCompound();
                ItemStack itemStack = MADE_VOXEL.getDefaultStack();
                BlockItem.setBlockEntityNbt(itemStack, CSDBlockEntityTypes.MADE_VOXEL, blockEntityTag);
                itemStack.setCustomName(Text.translatable(MadeVoxels.getTranslationKey(entry.getKey())).setStyle(Style.EMPTY.withItalic(false)));
                entries.add(itemStack);
            }
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
        for (Item item : new Item[]{
          /*机器*/VOXEL_MAKER,
          /*方块*/CHOCOLATE_BLOCK, WHEAT_CAKE_BASE_BLOCK, BUTTER_BLOCK,
          /*类细雪桶*/BUCKETED_APPLE_JAM, BUCKETED_WHEAT_PASTE, BUCKETED_CREAM,
          /*虚拟流体*/BUCKETED_EGG_LIQUID,
          /*材料*/EGG_SHELL, KELP_ASH, BUTTER_INGOT, BUTTER_NUGGET}) {
            entries.add(item);
        }
    }
    private CSDItems() {
    }
}
