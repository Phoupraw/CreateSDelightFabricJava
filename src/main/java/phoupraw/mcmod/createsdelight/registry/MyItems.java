package phoupraw.mcmod.createsdelight.registry;

import com.nhoryzon.mc.farmersdelight.registry.EffectsRegistry;
import com.simibubi.create.content.contraptions.components.AssemblyOperatorBlockItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.common.api.Registries;
import phoupraw.mcmod.createsdelight.datagen.*;
import phoupraw.mcmod.createsdelight.item.StatusEffectsBlockItem;
import phoupraw.mcmod.createsdelight.item.StatusEffectsItem;
import phoupraw.mcmod.createsdelight.item.SweetBerriesCakeItem;

import java.util.List;
/**
 物品编写流程：
 <ol>
 <li>若自定义物品，则在{@link phoupraw.mcmod.createsdelight.item}创建物品类，继承{@link Item}；推荐重载无参构造器。<br>
 <li>在{@link MyIdentifiers}创建{@link Identifier}。<br>
 <li>在{@link MyItems}创建物品<b>并注册</b>。<br>
 <li>若不为{@link BlockItem}，则在{@link MyChineseProvider}和{@link MyEnglishProvider}添加翻译。<br>
 <li>若不为{@link BlockItem}，则在{@link MyModelProvider}添加模型。<br>
 <li>在{@link MyRecipeProvider}添加配方。<br>
 <li>在{@link MyItemTagProvider}添加标签。<br>
 <li>运行数据生成器。<br>
 <li>在{@code src/main/resources/assets/createsdelight/textures/item}创建纹理。<br>
 <li>运行客户端，检查物品效果是否如预期。<br>
 <li>在{@code ChangeLog.md}添加更新日志。<br>
 <li>提交git。
 </ol> */
public final class MyItems {
    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(MyIdentifiers.ITEM_GROUP, MyItems::stupidJavaCompiler);

    //方块
    public static final BlockItem PAN = new BlockItem(MyBlocks.PAN, newSettings());
    public static final BlockItem GRILL = new BlockItem(MyBlocks.GRILL, newSettings());
    public static final BlockItem SPRINKLER = new AssemblyOperatorBlockItem(MyBlocks.SPRINKLER, newSettings());
    public static final BlockItem BAMBOO_STEAMER = new BlockItem(MyBlocks.BAMBOO_STEAMER, newSettings());
    public static final BlockItem SMART_DRAIN = new BlockItem(MyBlocks.SMART_DRAIN, newSettings());
    public static final BlockItem COPPER_TUNNEL = new BlockItem(MyBlocks.COPPER_TUNNEL, newSettings());
    public static final BlockItem MULTIFUNC_BASIN = new BlockItem(MyBlocks.MULTIFUNC_BASIN, newSettings()) {
        @Override
        public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
            super.appendTooltip(stack, world, tooltip, context);
            tooltip.add(Text.literal("In dev, No using. 开发中，请勿使用。"));
        }
    };
    public static final BlockItem VERTICAL_CUTTER = new AssemblyOperatorBlockItem(MyBlocks.VERTICAL_CUTTER, newSettings());
    public static final BlockItem PRESSURE_COOKER = new AssemblyOperatorBlockItem(MyBlocks.PRESSURE_COOKER, newSettings());
    public static final BlockItem MINCER = new AssemblyOperatorBlockItem(MyBlocks.MINCER, newSettings());
    public static final BlockItem SKEWER = new AssemblyOperatorBlockItem(MyBlocks.SKEWER, newSettings());
    public static final BlockItem BASIN = new BlockItem(MyBlocks.BASIN, newSettings());
    public static final BlockItem SKEWER_PLATE = new BlockItem(MyBlocks.SKEWER_PLATE, newSettings());

    //可食用方块
    public static final StatusEffectsBlockItem JELLY_BEANS = new StatusEffectsBlockItem(MyBlocks.JELLY_BEANS, MyItems.newSettings()
      .food(new FoodComponent.Builder()
        .hunger(1)
        .saturationModifier(0.5f)
        .snack()
        .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 0), 1f)
        .build()
      ));
    public static final StatusEffectsBlockItem JELLY_BEANS_CAKE = new StatusEffectsBlockItem(MyBlocks.JELLY_BEANS_CAKE, newSettings()
      .maxCount(16)
      .food(new FoodComponent.Builder()
        .hunger(9)
        .saturationModifier(0.5f)
        .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 16), 1f)
        .build()
      ));
    public static final StatusEffectsBlockItem SWEET_BERRIES_CAKE = new SweetBerriesCakeItem();
    public static final StatusEffectsBlockItem BASQUE_CAKE = new StatusEffectsBlockItem(MyBlocks.BASQUE_CAKE, newSettings()
      .maxCount(16)
      .food(new FoodComponent.Builder()
        .hunger(9)
        .saturationModifier(0.5f)
        .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 16), 1f)
        .build()
      ));
    public static final StatusEffectsBlockItem SWEET_BERRIES_CAKE_S = new StatusEffectsBlockItem(MyBlocks.SWEET_BERRIES_CAKE_S, newSettings()
      .maxCount(16)
      .food(new FoodComponent.Builder()
        .hunger(9)
        .saturationModifier(0.5f)
        .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 16), 1f)
        .build()
      ));
    public static final StatusEffectsBlockItem BROWNIE = new StatusEffectsBlockItem(MyBlocks.BROWNIE, newSettings()
      .maxCount(64)
      .food(new FoodComponent.Builder()
        .hunger(9)
        .saturationModifier(0.5f)
        .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 16), 1f)
        .build()
      ));
    public static final StatusEffectsBlockItem APPLE_CREAM_CAKE = new StatusEffectsBlockItem(MyBlocks.APPLE_CREAM_CAKE, newSettings()
      .maxCount(16)
      .food(new FoodComponent.Builder()
        .hunger(9)
        .saturationModifier(0.5f)
        .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 16), 1f)
        .build()
      ));

    //不可食用物品
    public static final Item BUCKETED_SUNFLOWER_OIL = new Item(newSettings().maxCount(1));
    public static final Item BOTTLED_SUNFLOWER_OIL = new Item(newSettings().maxCount(16));
    public static final Item EGG_SHELL = new Item(newSettings());
    public static final Item EGG_DOUGH = new Item(newSettings());
    public static final Item CRUSHED_ICE = new Item(newSettings());
    public static final Item SALT = new Item(newSettings());
    public static final Item KELP_ASH = new Item(newSettings());
    public static final Item YEAST = new Item(newSettings());
    public static final Item CAKE_BASE = new Item(newSettings());
    public static final Item CAKE_BASE_SLICE = new Item(newSettings());
    public static final Item RAW_BASQUE_CAKE = new Item(newSettings());

    //食物
    public static final StatusEffectsItem PAN_FRIED_BEEF_PATTY = satiationMeat(4, 0.8f, 2);
    public static final StatusEffectsItem THICK_PORK_SLICE = new StatusEffectsItem(newSettings().food(new FoodComponent.Builder().meat().hunger(2).saturationModifier(0.3f).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 40, 0), 0.05f).build()));
    public static final StatusEffectsItem PAN_FRIED_PORK_SLICE = satiationMeat(4, 0.6f, 0);
    public static final StatusEffectsItem THIN_PORK_SLICE = new StatusEffectsItem(newSettings().food(new FoodComponent.Builder().meat().hunger(1).saturationModifier(0.3f).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 40, 0), 0.05f).build()));
    public static final StatusEffectsItem GRILLED_PORK_SLICE = satiationMeat(2, 0.6f, 0);
    public static final StatusEffectsItem SUGAR_PORK = satiationMeat(4, 0.8f, 2);
    public static final StatusEffectsItem LEAVES_RICE = new StatusEffectsItem(newSettings()
      .maxCount(16)
      .recipeRemainder(Items.BOWL)
      .food(new FoodComponent.Builder()
        .hunger(6)
        .saturationModifier(0.4f)
        .statusEffect(new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 600, 0), 1)
        .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20), 0.25f)
        .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 0), 1)
        .build()
      ));
    public static final StatusEffectsItem VANILLA = new StatusEffectsItem(newSettings().food(new FoodComponent.Builder()
      .hunger(1)
      .saturationModifier(0.5f)
      .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20), 0.25f)
      .build()
    ));
    public static final StatusEffectsItem VANILLA_SWEET_ROLL = new StatusEffectsItem(newSettings().food(new FoodComponent.Builder()
      .hunger(6)
      .saturationModifier(0.8f)
      .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 0), 1f)
      .statusEffect(new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 300, 0), 1)
      .build()
    ));
    public static final StatusEffectsItem STEAMED_BUNS = new StatusEffectsItem(newSettings().food(new FoodComponent.Builder()
      .hunger(3)
      .saturationModifier(0.5f)
      .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 4), 1f)
      .build()
    ));
    public static final StatusEffectsItem COOKED_RICE = new StatusEffectsItem(newSettings().food(new FoodComponent.Builder()
      .hunger(4)
      .saturationModifier(0.6f)
      .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 1), 1f)
      .statusEffect(new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 600, 0), 1)
      .build()
    ));
    public static final StatusEffectsItem VEGETABLE_BIG_STEW = new StatusEffectsItem(newSettings().food(new FoodComponent.Builder()
      .hunger(6)
      .saturationModifier(0.5f)
      .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 20), 1f)
      .statusEffect(new StatusEffectInstance(EffectsRegistry.NOURISHMENT.get(), 20 * 120, 0), 1)
      .build()
    ));
    public static final StatusEffectsItem ROSE_MILK_TEA = new StatusEffectsItem(newSettings()
      .maxCount(16)
      .recipeRemainder(Items.GLASS_BOTTLE)
      .food(new FoodComponent.Builder()
        .hunger(2)
        .saturationModifier(0.3f)
        .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 1), 1f)
        .statusEffect(new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 100, 0), 1)
        .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 40), 1)
        .build()
      ));
    public static final StatusEffectsItem CORAL_COLORFULS = new StatusEffectsItem(newSettings()
      .food(new FoodComponent.Builder()
        .hunger(6)
        .saturationModifier(0.5f)
        .snack()
        .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 2), 1f)
        .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 300, 0), 1)
        .statusEffect(new StatusEffectInstance(StatusEffects.HASTE, 20 * 200), 1)
        .build()
      ));
    public static final StatusEffectsItem POPPY_RUSSIAN_SOUP = new StatusEffectsItem(newSettings()
      .maxCount(16)
      .recipeRemainder(Items.BOWL)
      .food(new FoodComponent.Builder()
        .hunger(6)
        .saturationModifier(0.5f)
        .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 20), 1f)
        .statusEffect(new StatusEffectInstance(EffectsRegistry.NOURISHMENT.get(), 20 * 180, 0), 1)
        .build()
      ));
    public static final StatusEffectsItem WHEAT_BLACK_TEA = new StatusEffectsItem(newSettings()
      .maxCount(16)
      .recipeRemainder(Items.GLASS_BOTTLE)
      .food(new FoodComponent.Builder()
        .hunger(2)
        .saturationModifier(0.3f)
        .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 1), 1f)
        .statusEffect(new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 100, 0), 1)
        .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 40), 1)
        .build()
      ));
    public static final StatusEffectsItem ICED_MELON_JUICE = new StatusEffectsItem(newSettings()
      .maxCount(16)
      .recipeRemainder(Items.GLASS_BOTTLE)
      .food(new FoodComponent.Builder()
        .hunger(2)
        .saturationModifier(0.3f)
        .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 1), 1f)
        .statusEffect(new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 100, 0), 1)
        .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 40), 1)
        .build()
      ));
    public static final StatusEffectsItem THICK_HOT_COCOA = new StatusEffectsItem(newSettings()
      .maxCount(16)
      .recipeRemainder(Items.GLASS_BOTTLE)
      .food(new FoodComponent.Builder()
        .hunger(2)
        .saturationModifier(0.3f)
        .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 1), 1f)
        .statusEffect(new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 100, 0), 1)
        .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 40), 1)
        .build()
      ));

    static {
        Registries.register(MyIdentifiers.PAN, PAN);
        Registries.register(MyIdentifiers.GRILL, GRILL);
        Registries.register(MyIdentifiers.SPRINKLER, SPRINKLER);
        Registries.register(MyIdentifiers.BAMBOO_STEAMER, BAMBOO_STEAMER);
        Registries.register(MyIdentifiers.SMART_DRAIN, SMART_DRAIN);
        Registries.register(MyIdentifiers.COPPER_TUNNEL, COPPER_TUNNEL);
        Registries.register(MyIdentifiers.MULTIFUNC_BASIN, MULTIFUNC_BASIN);
        Registries.register(MyIdentifiers.VERTICAL_CUTTER, VERTICAL_CUTTER);
        Registries.register(MyIdentifiers.PRESSURE_COOKER, PRESSURE_COOKER);
        Registries.register(MyIdentifiers.MINCER, MINCER);
        Registries.register(MyIdentifiers.SKEWER, SKEWER);
        Registries.register(MyIdentifiers.BASIN, BASIN);
        Registries.register(MyIdentifiers.SKEWER_PLATE, SKEWER_PLATE);
        Registries.register(MyIdentifiers.KELP_ASH, KELP_ASH);
        Registries.register(MyIdentifiers.YEAST, YEAST);
        Registries.register(MyIdentifiers.CAKE_BASE, CAKE_BASE);
        Registries.register(MyIdentifiers.CAKE_BASE_SLICE, CAKE_BASE_SLICE);

        Registries.register(MyIdentifiers.JELLY_BEANS, JELLY_BEANS);
        Registries.register(MyIdentifiers.JELLY_BEANS_CAKE, JELLY_BEANS_CAKE);
        Registries.register(MyIdentifiers.SWEET_BERRIES_CAKE, SWEET_BERRIES_CAKE);
        Registries.register(MyIdentifiers.BASQUE_CAKE, BASQUE_CAKE);
        Registries.register(MyIdentifiers.SWEET_BERRIES_CAKE_S, SWEET_BERRIES_CAKE_S);
        Registries.register(MyIdentifiers.BROWNIE, BROWNIE);
        Registries.register(MyIdentifiers.APPLE_CREAM_CAKE, APPLE_CREAM_CAKE);

        Registries.register(MyIdentifiers.BUCKETED_SUNFLOWER_OIL, BUCKETED_SUNFLOWER_OIL);
        Registries.register(MyIdentifiers.BOTTLED_SUNFLOWER_OIL, BOTTLED_SUNFLOWER_OIL);
        Registries.register(MyIdentifiers.EGG_SHELL, EGG_SHELL);
        Registries.register(MyIdentifiers.EGG_DOUGH, EGG_DOUGH);
        Registries.register(MyIdentifiers.CRUSHED_ICE, CRUSHED_ICE);
        Registries.register(MyIdentifiers.SALT, SALT);
        Registries.register(MyIdentifiers.RAW_BASQUE_CAKE, RAW_BASQUE_CAKE);

        Registries.register(MyIdentifiers.PAN_FRIED_BEEF_PATTY, PAN_FRIED_BEEF_PATTY);
        Registries.register(MyIdentifiers.THICK_PORK_SLICE, THICK_PORK_SLICE);
        Registries.register(MyIdentifiers.PAN_FRIED_PORK_SLICE, PAN_FRIED_PORK_SLICE);
        Registries.register(MyIdentifiers.THIN_PORK_SLICE, THIN_PORK_SLICE);
        Registries.register(MyIdentifiers.GRILLED_PORK_SLICE, GRILLED_PORK_SLICE);
        Registries.register(MyIdentifiers.SUGAR_PORK, SUGAR_PORK);
        Registries.register(MyIdentifiers.LEAVES_RICE, LEAVES_RICE);
        Registries.register(MyIdentifiers.VANILLA, VANILLA);
        Registries.register(MyIdentifiers.VANILLA_SWEET_ROLL, VANILLA_SWEET_ROLL);
        Registries.register(MyIdentifiers.STEAMED_BUNS, STEAMED_BUNS);
        Registries.register(MyIdentifiers.COOKED_RICE, COOKED_RICE);
        Registries.register(MyIdentifiers.VEGETABLE_BIG_STEW, VEGETABLE_BIG_STEW);
        Registries.register(MyIdentifiers.ROSE_MILK_TEA, ROSE_MILK_TEA);
        Registries.register(MyIdentifiers.CORAL_COLORFULS, CORAL_COLORFULS);
        Registries.register(MyIdentifiers.POPPY_RUSSIAN_SOUP, POPPY_RUSSIAN_SOUP);
        Registries.register(MyIdentifiers.WHEAT_BLACK_TEA, WHEAT_BLACK_TEA);
        Registries.register(MyIdentifiers.ICED_MELON_JUICE, ICED_MELON_JUICE);
        Registries.register(MyIdentifiers.THICK_HOT_COCOA, THICK_HOT_COCOA);
    }
    public static StatusEffectsItem satiationMeat(int hunger, float saturationModifier, int amplifier) {
        return food(new FoodComponent.Builder().meat().statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, amplifier), 1).alwaysEdible().hunger(hunger).saturationModifier(saturationModifier).build());
    }

    public static StatusEffectsItem food(FoodComponent foodComponent) {
        return new StatusEffectsItem(newSettings().food(foodComponent));
    }

    @Contract(pure = true, value = "->new")
    @ApiStatus.Internal
    public static FabricItemSettings newSettings() {
        return new FabricItemSettings().group(ITEM_GROUP);
    }

    private static ItemStack stupidJavaCompiler() {
        return JELLY_BEANS_CAKE.getDefaultStack();
    }

    private MyItems() {}
}
