package phoupraw.mcmod.createsdelight.registry;

import com.nhoryzon.mc.farmersdelight.registry.EffectsRegistry;
import com.simibubi.create.content.contraptions.itemAssembly.SequencedAssemblyItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import phoupraw.mcmod.createsdelight.datagen.*;
import phoupraw.mcmod.createsdelight.item.*;

import java.util.List;

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

public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.create(CDIdentifiers.ITEM_GROUP)
  .icon(CDItems::stupidJavaCompiler)
  .appendItems(CDItems::appendItems)
  .build();

//方块
public static final BlockItem CAKE_OVEN = new BlockItem(CDBlocks.CAKE_OVEN, newSettings());

//可食用方块
public static final StatusEffectsBlockItem JELLY_BEANS = new StatusEffectsBlockItem(CDBlocks.JELLY_BEANS, CDItems.newSettings()
  .food(new FoodComponent.Builder()
    .hunger(2)
    .saturationModifier(0.5f)
    .snack()
    .statusEffect(new StatusEffectInstance(CDStatusEffects.SATIATION, 1, 0), 1f)
    .build()
  ));
public static final StatusEffectsBlockItem JELLY_BEANS_CAKE = new StatusEffectsBlockItem(CDBlocks.JELLY_BEANS_CAKE, newSettings()
  .maxCount(16)
  .food(new FoodComponent.Builder()
    .hunger(20)
    .saturationModifier(0.5f)
    .statusEffect(new StatusEffectInstance(CDStatusEffects.SATIATION, 1, 10), 1f)
    .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 5, 0), 1f)
    .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20 * 10, 0), 0.8f)
    .build()
  ));
public static final StatusEffectsBlockItem SWEET_BERRIES_CAKE = new SweetBerriesCakeItem();
public static final StatusEffectsBlockItem BASQUE_CAKE = new StatusEffectsBlockItem(CDBlocks.BASQUE_CAKE, newSettings()
  .maxCount(16)
  .food(new FoodComponent.Builder()
    .hunger(20)
    .saturationModifier(0.5f)
    .statusEffect(new StatusEffectInstance(CDStatusEffects.SATIATION, 1, 10), 1f)
    .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 5, 0), 1f)
    .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20 * 10, 0), 0.8f)
    .build()
  ));
public static final StatusEffectsBlockItem SWEET_BERRIES_CAKE_S = new StatusEffectsBlockItem(CDBlocks.SWEET_BERRIES_CAKE_S, newSettings()
  .maxCount(16)
  .food(new FoodComponent.Builder()
    .hunger(20)
    .saturationModifier(0.5f)
    .statusEffect(new StatusEffectInstance(CDStatusEffects.SATIATION, 1, 10), 1f)
    .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 5, 0), 1f)
    .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20 * 10, 0), 0.8f)
    .build()
  ));
public static final StatusEffectsBlockItem BROWNIE = new StatusEffectsBlockItem(CDBlocks.BROWNIE, newSettings()
  .maxCount(64)
  .food(new FoodComponent.Builder()
    .hunger(5)
    .saturationModifier(0.5f)
    .statusEffect(new StatusEffectInstance(CDStatusEffects.SATIATION, 1, 3), 1f)
    .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 2, 0), 1f)
    .statusEffect(new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 20 * 60 * 2, 0), 1f)
    .build()
  ));
public static final StatusEffectsBlockItem APPLE_CREAM_CAKE = new StatusEffectsBlockItem(CDBlocks.APPLE_CREAM_CAKE, newSettings()
  .maxCount(16)
  .food(new FoodComponent.Builder()
    .hunger(20)
    .saturationModifier(0.5f)
    .statusEffect(new StatusEffectInstance(CDStatusEffects.SATIATION, 1, 10), 1f)
    .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 5, 0), 1f)
    .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20 * 10, 0), 0.8f)
    .build()
  ));
public static final StatusEffectsBlockItem APPLE_CAKE = new StatusEffectsBlockItem(CDBlocks.APPLE_CAKE, newSettings()
  .maxCount(16)
  .food(new FoodComponent.Builder()
    .hunger(20)
    .saturationModifier(0.5f)
    .statusEffect(new StatusEffectInstance(CDStatusEffects.SATIATION, 1, 10), 1f)
    .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 1, 4), 1f)
    .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20 * 10, 0), 0.8f)
    .build()
  ));
public static final StatusEffectsBlockItem CARROT_CREAM_CAKE = new StatusEffectsBlockItem(CDBlocks.CARROT_CREAM_CAKE, newSettings()
  .maxCount(16)
  .food(new FoodComponent.Builder()
    .hunger(20)
    .saturationModifier(0.5f)
    .statusEffect(new StatusEffectInstance(CDStatusEffects.SATIATION, 1, 10), 1f)
    .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 1, 4), 1f)
    .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20 * 10, 0), 0.8f)
    .build()
  ));
public static final StatusEffectsBlockItem SMALL_CHOCOLATE_CREAM_CAKE = new StatusEffectsBlockItem(CDBlocks.SMALL_CHOCOLATE_CREAM_CAKE, newSettings()
  .maxCount(16)
  .food(new FoodComponent.Builder()
    .hunger(20)
    .saturationModifier(0.5f)
    .statusEffect(new StatusEffectInstance(CDStatusEffects.SATIATION, 1, 10), 1f)
    .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 1, 4), 1f)
    .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20 * 10, 0), 0.8f)
    .build()
  ));
public static final StatusEffectsBlockItem MEDIUM_CHOCOLATE_CREAM_CAKE = new StatusEffectsBlockItem(CDBlocks.MEDIUM_CHOCOLATE_CREAM_CAKE, newSettings()
  .maxCount(16)
  .food(new FoodComponent.Builder()
    .hunger(20)
    .saturationModifier(0.5f)
    .statusEffect(new StatusEffectInstance(CDStatusEffects.SATIATION, 1, 10), 1f)
    .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 1, 4), 1f)
    .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20 * 10, 0), 0.8f)
    .build()
  ));
public static final StatusEffectsBlockItem BIG_CHOCOLATE_CREAM_CAKE = new StatusEffectsBlockItem(CDBlocks.BIG_CHOCOLATE_CREAM_CAKE, newSettings()
  .maxCount(16)
  .food(new FoodComponent.Builder()
    .hunger(20)
    .saturationModifier(0.5f)
    .statusEffect(new StatusEffectInstance(CDStatusEffects.SATIATION, 1, 10), 1f)
    .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 1, 4), 1f)
    .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20 * 10, 0), 0.8f)
    .build()
  ));
public static final StatusEffectsBlockItem CHOCOLATE_ANTHEMY_CAKE = new StatusEffectsBlockItem(CDBlocks.CHOCOLATE_ANTHEMY_CAKE, newSettings()
  .maxCount(16)
  .food(new FoodComponent.Builder()
    .hunger(20)
    .saturationModifier(0.5f)
    .statusEffect(new StatusEffectInstance(CDStatusEffects.SATIATION, 1, 10), 1f)
    .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 1, 4), 1f)
    .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20 * 10, 0), 0.8f)
    .build()
  ));
public static final BlockItem PRINTED_CAKE = new PrintedCakeItem();

//不可食用物品
public static final Item EGG_SHELL = new Item(newSettings());
public static final Item EGG_DOUGH = new Item(newSettings());
public static final Item KELP_ASH = new Item(newSettings());
public static final Item RAW_BASQUE_CAKE = new Item(newSettings());
public static final Item IRON_BOWL = new IronBowlItem();
public static final Item CAKE_BLUEPRINT = new CakeBlueprintItem();

//食物
public static final StatusEffectsItem CAKE_BASE = new StatusEffectsItem(newSettings()
  .food(new FoodComponent.Builder()
    .hunger(20)
    .saturationModifier(0.5f)
    .build()
  ));
public static final StatusEffectsItem CAKE_BASE_SLICE = new StatusEffectsItem(newSettings()
  .food(new FoodComponent.Builder()
    .hunger(7)
    .saturationModifier(0.5f)
    .build()
  ));
public static final StatusEffectsItem BUCKETED_SUNFLOWER_OIL = new DrinkItem(newSettings()
  .maxCount(1)
  .recipeRemainder(Items.BUCKET)
  .food(new FoodComponent.Builder()
    .hunger(3)
    .saturationModifier(1f)
    .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20 * 18, 0), 0.8f)
    .build()
  ));
public static final StatusEffectsItem BOTTLED_SUNFLOWER_OIL = new DrinkItem(newSettings()
  .maxCount(16)
  .recipeRemainder(Items.BUCKET)
  .food(new FoodComponent.Builder()
    .hunger(1)
    .saturationModifier(1f)
    .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20 * 9, 0), 0.8f)
    .build()
  ));
public static final StatusEffectsItem BUCKETED_PUMPKIN_OIL = new DrinkItem(newSettings()
  .maxCount(1)
  .recipeRemainder(Items.BUCKET)
  .food(new FoodComponent.Builder()
    .hunger(3)
    .saturationModifier(1f)
    .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20 * 18, 0), 0.8f)
    .build()
  ));
public static final StatusEffectsItem CHOCOLATE_CAKE_BASE = new StatusEffectsItem(newSettings()
  .food(new FoodComponent.Builder()
    .hunger(20)
    .saturationModifier(0.5f)
    .build()
  ));
public static final StatusEffectsItem SUNFLOWER_KERNELS = new StatusEffectsItem(newSettings()
  .food(new FoodComponent.Builder()
    .hunger(2)
    .saturationModifier(0.3f)
    .snack()
    .statusEffect(new StatusEffectInstance(CDStatusEffects.SATIATION, 1, 1), 1f)
    .statusEffect(new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 100, 0), 1)
    .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 40), 1)
    .build()
  ));
//序列装配中间体
public static final SequencedAssemblyItem JELLY_BEANS_CAKE_0 = new SequencedAssemblyItem(newSettings());
public static final SequencedAssemblyItem SWEET_BERRIES_CAKE_0 = new SequencedAssemblyItem(newSettings());
public static final SequencedAssemblyItem SWEET_BERRIES_CAKE_S_0 = new SequencedAssemblyItem(newSettings());
public static final SequencedAssemblyItem RAW_BASQUE_CAKE_0 = new SequencedAssemblyItem(newSettings());
public static final SequencedAssemblyItem BROWNIE_0 = new SequencedAssemblyItem(newSettings());
public static final SequencedAssemblyItem APPLE_CREAM_CAKE_0 = new SequencedAssemblyItem(newSettings());
public static final SequencedAssemblyItem CARROT_CREAM_CAKE_0 = new SequencedAssemblyItem(newSettings());

//蛋糕材料
public static final BlockItem MILK = new BlockItem(CDBlocks.MILK, newSettings());
public static final BlockItem CHOCOLATE = new BlockItem(CDBlocks.CHOCOLATE, newSettings());
static {

    register(CDIdentifiers.KELP_ASH, KELP_ASH);
    register(CDIdentifiers.CAKE_BASE, CAKE_BASE);
    register(CDIdentifiers.CAKE_BASE_SLICE, CAKE_BASE_SLICE);
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

    register(CDIdentifiers.BUCKETED_SUNFLOWER_OIL, BUCKETED_SUNFLOWER_OIL);
    register(CDIdentifiers.BOTTLED_SUNFLOWER_OIL, BOTTLED_SUNFLOWER_OIL);
    register(CDIdentifiers.EGG_SHELL, EGG_SHELL);
    register(CDIdentifiers.EGG_DOUGH, EGG_DOUGH);
    register(CDIdentifiers.RAW_BASQUE_CAKE, RAW_BASQUE_CAKE);
    register(CDIdentifiers.BUCKETED_PUMPKIN_OIL, BUCKETED_PUMPKIN_OIL);
    register(CDIdentifiers.IRON_BOWL, IRON_BOWL);
    register(CDIdentifiers.CAKE_BLUEPRINT, CAKE_BLUEPRINT);

    register(CDIdentifiers.CHOCOLATE_CAKE_BASE, CHOCOLATE_CAKE_BASE);

    register(CDIdentifiers.JELLY_BEANS_CAKE_0, JELLY_BEANS_CAKE_0);
    register(CDIdentifiers.SWEET_BERRIES_CAKE_0, SWEET_BERRIES_CAKE_0);
    register(CDIdentifiers.SWEET_BERRIES_CAKE_S_0, SWEET_BERRIES_CAKE_S_0);
    register(CDIdentifiers.RAW_BASQUE_CAKE_0, RAW_BASQUE_CAKE_0);
    register(CDIdentifiers.BROWNIE_0, BROWNIE_0);
    register(CDIdentifiers.APPLE_CREAM_CAKE_0, APPLE_CREAM_CAKE_0);
    register(CDIdentifiers.CARROT_CREAM_CAKE_0, CARROT_CREAM_CAKE_0);

    register(CDIdentifiers.MILK, MILK);
    register(CDIdentifiers.CHOCOLATE, CHOCOLATE);

    register(CDIdentifiers.SUNFLOWER_KERNELS,SUNFLOWER_KERNELS);
}
public static StatusEffectsItem satiationMeat(int hunger, float saturationModifier, int amplifier) {
    return food(new FoodComponent.Builder()
      .meat()
      .statusEffect(new StatusEffectInstance(CDStatusEffects.SATIATION, 1, amplifier), 1)
      .alwaysEdible()
      .hunger(hunger)
      .saturationModifier(saturationModifier)
      .build());
}

public static StatusEffectsItem food(FoodComponent foodComponent) {
    return new StatusEffectsItem(newSettings().food(foodComponent));
}

@Contract(pure = true, value = "->new")
@ApiStatus.Internal
public static FabricItemSettings newSettings() {
    return new FabricItemSettings().group(ITEM_GROUP);
}

private static void appendItems(List<ItemStack> itemStacks, ItemGroup itemGroup) {
    for (Item item : new Item[]{
      CAKE_OVEN,
      MILK, CHOCOLATE,
      BUCKETED_SUNFLOWER_OIL, BUCKETED_PUMPKIN_OIL,
      BOTTLED_SUNFLOWER_OIL,
      KELP_ASH, RAW_BASQUE_CAKE
    }) {
        itemStacks.add(item.getDefaultStack());
    }
}

private static ItemStack stupidJavaCompiler() {
    return JELLY_BEANS_CAKE.getDefaultStack();
}

@Contract("_, _ -> param2")
public static <T extends Item> T register(Identifier id, T item) {
    return Registry.register(Registry.ITEM, id, item);
}

private CDItems() {
}

}
