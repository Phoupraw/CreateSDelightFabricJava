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
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.datagen.*;

import java.util.List;
/**
 * 物品编写流程：
 * <ol>
 * <li>在{@link MyIdentifiers}创建{@link Identifier}<br>
 * <li>在{@link MyItems}创建物品<b>并注册</b><br>
 * <li>在{@link MyChineseProvider}和{@link MyEnglishProvider}添加翻译<br>
 * <li>在{@link MyModelProvider}添加模型<br>
 * <li>在{@link MyRecipeProvider}添加配方<br>
 * <li>在{@link MyItemTagProvider}添加标签<br>
 * <li>运行数据生成器<br>
 * <li>在{@code src/main/resources/assets/createsdelight/textures/item}创建纹理<br>
 * <li>运行客户端，检查物品效果是否如预期<br>
 * <li>在{@code ChangeLog.md}添加更新日志<br>
 * <li>提交git
 * </ol>
 */
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
    public static final Item VANILLA = new ConsumableItem(newSettings()
      .food(new FoodComponent.Builder()
        .hunger(1)
        .saturationModifier(0.5f)
        .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20), 0.25f)
        .build()
      ), true);
    public static final Item VANILLA_SWEET_ROLL = new ConsumableItem(newSettings()
      .food(new FoodComponent.Builder()
        .hunger(6)
        .saturationModifier(0.8f)
        .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 0), 1f)
        .statusEffect(new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 300, 0), 1)
        .build()
      ), true);
    public static final Item STEAMED_BUNS = new ConsumableItem(newSettings()
      .food(new FoodComponent.Builder()
        .hunger(3)
        .saturationModifier(0.5f)
        .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 4), 1f)
        .build()
      ), true);
    public static final Item COOKED_RICE = new ConsumableItem(newSettings()
      .food(new FoodComponent.Builder()
        .hunger(4)
        .saturationModifier(0.6f)
        .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 1), 1f)
        .statusEffect(new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 600, 0), 1)
        .build()
      ), true);
    public static final Item VEGETABLE_BIG_STEW = new ConsumableItem(newSettings()
      .food(new FoodComponent.Builder()
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

    static {
        Registry.register(Registry.ITEM, MyIdentifiers.PAN, PAN);
        Registry.register(Registry.ITEM, MyIdentifiers.GRILL, GRILL);
        Registry.register(Registry.ITEM, MyIdentifiers.SPRINKLER, SPRINKLER);
        Registry.register(Registry.ITEM, MyIdentifiers.BAMBOO_STEAMER, BAMBOO_STEAMER);
        Registry.register(Registry.ITEM, MyIdentifiers.SMART_DRAIN, SMART_DRAIN);
        Registry.register(Registry.ITEM, MyIdentifiers.COPPER_TUNNEL, COPPER_TUNNEL);
        Registry.register(Registry.ITEM, MyIdentifiers.MULTIFUNC_BASIN, MULTIFUNC_BASIN);
        Registry.register(Registry.ITEM, MyIdentifiers.VERTICAL_CUTTER, VERTICAL_CUTTER);
        Registry.register(Registry.ITEM, MyIdentifiers.PRESSURE_COOKER, PRESSURE_COOKER);

        Registry.register(Registry.ITEM, MyIdentifiers.PAN_FRIED_BEEF_PATTY, PAN_FRIED_BEEF_PATTY);
        Registry.register(Registry.ITEM, MyIdentifiers.THICK_PORK_SLICE, THICK_PORK_SLICE);
        Registry.register(Registry.ITEM, MyIdentifiers.PAN_FRIED_PORK_SLICE, PAN_FRIED_PORK_SLICE);
        Registry.register(Registry.ITEM, MyIdentifiers.THIN_PORK_SLICE, THIN_PORK_SLICE);
        Registry.register(Registry.ITEM, MyIdentifiers.GRILLED_PORK_SLICE, GRILLED_PORK_SLICE);
        Registry.register(Registry.ITEM, MyIdentifiers.SUGAR_PORK, SUGAR_PORK);
        Registry.register(Registry.ITEM, MyIdentifiers.LEAVES_RICE, LEAVES_RICE);
        Registry.register(Registry.ITEM, MyIdentifiers.VANILLA, VANILLA);
        Registry.register(Registry.ITEM, MyIdentifiers.VANILLA_SWEET_ROLL, VANILLA_SWEET_ROLL);
        Registry.register(Registry.ITEM, MyIdentifiers.STEAMED_BUNS, STEAMED_BUNS);
        Registry.register(Registry.ITEM, MyIdentifiers.COOKED_RICE, COOKED_RICE);
        Registry.register(Registry.ITEM, MyIdentifiers.VEGETABLE_BIG_STEW, VEGETABLE_BIG_STEW);
    }
    public static Item satiationMeat(int hunger, float saturationModifier, int amplifier) {
        return food(new FoodComponent.Builder().meat().statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, amplifier), 1).alwaysEdible().hunger(hunger).saturationModifier(saturationModifier).build());
    }

    public static Item food(FoodComponent foodComponent) {
        return new ConsumableItem(newSettings().food(foodComponent), true);
    }

    private static FabricItemSettings newSettings() {
        return new FabricItemSettings().group(ITEM_GROUP);
    }

    private static ItemStack stupidJavaCompiler() {
        return PAN.getDefaultStack();
    }

    private MyItems() {}
}
