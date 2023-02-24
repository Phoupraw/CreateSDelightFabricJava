package phoupraw.mcmod.createsdelight.registry;

import com.nhoryzon.mc.farmersdelight.item.ConsumableItem;
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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.common.Registries;
import phoupraw.mcmod.createsdelight.datagen.*;

import java.util.List;
/**
 物品编写流程：
 <ol>
 <li>在{@link MyIdentifiers}创建{@link Identifier}<br>
 <li>在{@link MyItems}创建物品<b>并注册</b><br>
 <li>在{@link MyChineseProvider}和{@link MyEnglishProvider}添加翻译<br>
 <li>在{@link MyModelProvider}添加模型<br>
 <li>在{@link MyRecipeProvider}添加配方<br>
 <li>在{@link MyItemTagProvider}添加标签<br>
 <li>运行数据生成器<br>
 <li>在{@code src/main/resources/assets/createsdelight/textures/item}创建纹理<br>
 <li>运行客户端，检查物品效果是否如预期<br>
 <li>在{@code ChangeLog.md}添加更新日志<br>
 <li>提交git
 </ol> */
public final class MyItems {
    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(MyIdentifiers.ITEM_GROUP, MyItems::stupidJavaCompiler);

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

    public static final Item PAN_FRIED_BEEF_PATTY = satiationMeat(4, 0.8f, 2);
    public static final Item THICK_PORK_SLICE = new ConsumableItem(newSettings().food(new FoodComponent.Builder().meat().hunger(2).saturationModifier(0.3f).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 40, 0), 0.05f).build()), true);
    public static final Item PAN_FRIED_PORK_SLICE = satiationMeat(4, 0.6f, 0);
    public static final Item THIN_PORK_SLICE = new ConsumableItem(newSettings().food(new FoodComponent.Builder().meat().hunger(1).saturationModifier(0.3f).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 40, 0), 0.05f).build()), true);
    public static final Item GRILLED_PORK_SLICE = satiationMeat(2, 0.6f, 0);
    public static final Item SUGAR_PORK = satiationMeat(4, 0.8f, 2);
    public static final Item LEAVES_RICE = new ConsumableItem(newSettings()
      .maxCount(16)
      .recipeRemainder(Items.BOWL)
      .food(new FoodComponent.Builder()
        .hunger(6)
        .saturationModifier(0.4f)
        .statusEffect(new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 600, 0), 1)
        .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20), 0.25f)
        .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 0), 1)
        .build()
      ), true);
    public static final Item VANILLA = new ConsumableItem(newSettings().food(new FoodComponent.Builder()
      .hunger(1)
      .saturationModifier(0.5f)
      .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20), 0.25f)
      .build()
    ), true);
    public static final Item VANILLA_SWEET_ROLL = new ConsumableItem(newSettings().food(new FoodComponent.Builder()
      .hunger(6)
      .saturationModifier(0.8f)
      .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 0), 1f)
      .statusEffect(new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 300, 0), 1)
      .build()
    ), true);
    public static final Item STEAMED_BUNS = new ConsumableItem(newSettings().food(new FoodComponent.Builder()
      .hunger(3)
      .saturationModifier(0.5f)
      .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 4), 1f)
      .build()
    ), true);
    public static final Item COOKED_RICE = new ConsumableItem(newSettings().food(new FoodComponent.Builder()
      .hunger(4)
      .saturationModifier(0.6f)
      .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 1), 1f)
      .statusEffect(new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 600, 0), 1)
      .build()
    ), true);
    public static final Item VEGETABLE_BIG_STEW = new ConsumableItem(newSettings().food(new FoodComponent.Builder()
      .hunger(6)
      .saturationModifier(0.5f)
      .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 20), 1f)
      .statusEffect(new StatusEffectInstance(EffectsRegistry.NOURISHMENT.get(), 20 * 120, 0), 1)
      .build()
    ), true) {
        @Override
        public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
            super.appendTooltip(stack, world, tooltip, context);
            tooltip.add(Text.literal("texture wanted"));
            tooltip.add(Text.literal("征稿纹理"));
        }
    };
    public static final Item ROSE_MILK_TEA = new ConsumableItem(newSettings()
      .maxCount(16)
      .recipeRemainder(Items.GLASS_BOTTLE)
      .food(new FoodComponent.Builder()
        .hunger(2)
        .saturationModifier(0.3f)
        .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 1), 1f)
        .statusEffect(new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 100, 0), 1)
        .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 40), 1)
        .build()
      ), true);
    public static final Item BUCKETED_SUNFLOWER_OIL = new Item(newSettings().maxCount(1));
    public static final Item BOTTLED_SUNFLOWER_OIL = new Item(newSettings().maxCount(1));

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
        Registries.register(MyIdentifiers.BUCKETED_SUNFLOWER_OIL, BUCKETED_SUNFLOWER_OIL);
        Registries.register(MyIdentifiers.BOTTLED_SUNFLOWER_OIL, BOTTLED_SUNFLOWER_OIL);
        Registries.register(MyIdentifiers.ROSE_MILK_TEA, ROSE_MILK_TEA);
    }
    public static Item satiationMeat(int hunger, float saturationModifier, int amplifier) {
        return food(new FoodComponent.Builder().meat().statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, amplifier), 1).alwaysEdible().hunger(hunger).saturationModifier(saturationModifier).build());
    }

    public static Item food(FoodComponent foodComponent) {
        return new ConsumableItem(newSettings().food(foodComponent), true);
    }

    @Contract(pure = true, value = "->new")
    private static FabricItemSettings newSettings() {
        return new FabricItemSettings().group(ITEM_GROUP);
    }

    private static ItemStack stupidJavaCompiler() {
        return PAN.getDefaultStack();
    }

    private MyItems() {}
}
