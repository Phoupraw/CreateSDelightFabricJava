package phoupraw.mcmod.createsdelight.registry;

import com.nhoryzon.mc.farmersdelight.item.ConsumableItem;
import com.nhoryzon.mc.farmersdelight.registry.EffectsRegistry;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.contraptions.components.AssemblyOperatorBlockItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import phoupraw.mcmod.createsdelight.datagen.MyChineseProvider;
import phoupraw.mcmod.createsdelight.datagen.MyEnglishProvider;
import phoupraw.mcmod.createsdelight.datagen.MyItemTagProvider;
import phoupraw.mcmod.createsdelight.datagen.MyModelProvider;
import phoupraw.mcmod.createsdelight.datagen.MyRecipeProvider;
/**
 * 物品编写流程：<br>
 * 1. 在{@link MyIdentifiers}创建{@link Identifier}<br>
 * 2. 在{@link MyItems}创建物品<b>并注册</b><br>
 * 3. 在{@link MyChineseProvider}和{@link MyEnglishProvider}添加翻译<br>
 * 4. 在{@link MyModelProvider}添加模型<br>
 * 5. 在{@link MyRecipeProvider}添加配方<br>
 * 6. 在{@link MyItemTagProvider}添加标签<br>
 * 7. 运行数据生成器<br>
 * 8. 在{@code src/main/resources/assets/createsdelight/textures/item}创建纹理<br>
 * 9. 运行客户端，检查物品效果是否如预期<br>
 * 10. 在{@code ChangeLog.md}添加更新日志<br>
 * 11. 提交git
 */
public final class MyItems {
    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(MyIdentifiers.ITEM_GROUP, MyItems::stupidJavaCompiler);

    public static final BlockItem PAN = new BlockItem(MyBlocks.PAN, new FabricItemSettings().group(ITEM_GROUP));
    public static final BlockItem GRILL = new BlockItem(MyBlocks.GRILL, new FabricItemSettings().group(ITEM_GROUP));
    public static final BlockItem SPRINKLER = new AssemblyOperatorBlockItem(MyBlocks.SPRINKLER, new FabricItemSettings().group(ITEM_GROUP));
    public static final BlockItem BAMBOO_STEAMER = new BlockItem(MyBlocks.BAMBOO_STEAMER, new FabricItemSettings().group(ITEM_GROUP));
    public static final BlockItem SMART_DRAIN = new BlockItem(MyBlocks.SMART_DRAIN, new FabricItemSettings().group(ITEM_GROUP));

    public static final Item PAN_FRIED_BEEF_PATTY = satiationMeat(4, 0.8f, 2);
    public static final Item THICK_PORK_SLICE = new ConsumableItem(new FabricItemSettings().group(ITEM_GROUP).food(new FoodComponent.Builder().meat().hunger(2).saturationModifier(0.3f).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 40, 0), 0.05f).build()), true);
    public static final Item PAN_FRIED_PORK_SLICE = satiationMeat(4, 0.6f, 0);
    public static final Item THIN_PORK_SLICE = new ConsumableItem(new FabricItemSettings().group(ITEM_GROUP).food(new FoodComponent.Builder().meat().hunger(1).saturationModifier(0.3f).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 40, 0), 0.05f).build()), true);
    public static final Item GRILLED_PORK_SLICE = satiationMeat(2, 0.6f, 0);
    public static final Item SUGAR_PORK = satiationMeat(4, 0.8f, 2);
    public static final Item LEAVES_RICE = new ConsumableItem(new FabricItemSettings()
      .group(ITEM_GROUP)
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
    public static final Item VANILLA = new ConsumableItem(new FabricItemSettings()
      .group(ITEM_GROUP)
      .food(new FoodComponent.Builder()
        .hunger(1)
        .saturationModifier(0.5f)
        .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20), 0.25f)
        .build()
      ), true);
    public static final Item VANILLA_SWEET_ROLL = new ConsumableItem(new FabricItemSettings()
      .group(ITEM_GROUP)
      .food(new FoodComponent.Builder()
        .hunger(6)
        .saturationModifier(0.8f)
        .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION,1,0), 1f)
        .statusEffect(new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 300, 0), 1)
        .build()
      ), true);
    public static final Item STEAMED_BUNS = new ConsumableItem(new FabricItemSettings()
      .group(ITEM_GROUP)
      .food(new FoodComponent.Builder()
        .hunger(3)
        .saturationModifier(0.5f)
        .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION,1,4), 1f)
        .build()
      ), true);
    static {
        Registry.register(Registry.ITEM, MyIdentifiers.PAN, PAN);
        Registry.register(Registry.ITEM, MyIdentifiers.GRILL, GRILL);
        Registry.register(Registry.ITEM, MyIdentifiers.SPRINKLER, SPRINKLER);
        Registry.register(Registry.ITEM, MyIdentifiers.BAMBOO_STEAMER, BAMBOO_STEAMER);
        Registry.register(Registry.ITEM, MyIdentifiers.SMART_DRAIN, SMART_DRAIN);

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
    }
    public static Item satiationMeat(int hunger, float saturationModifier, int amplifier) {
        return food(new FoodComponent.Builder().meat().statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, amplifier), 1).alwaysEdible().hunger(hunger).saturationModifier(saturationModifier).build());
    }

    public static Item food(FoodComponent foodComponent) {
        return new ConsumableItem(new FabricItemSettings().group(ITEM_GROUP).food(foodComponent), true);
    }

    private static ItemStack stupidJavaCompiler() {
        return PAN.getDefaultStack();
    }

    private MyItems() {}
}
