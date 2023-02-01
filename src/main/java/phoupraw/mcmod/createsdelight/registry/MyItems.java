package phoupraw.mcmod.createsdelight.registry;

import com.simibubi.create.content.contraptions.components.structureMovement.pulley.PulleyBlock;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.item.TooltipedFoodItem;
public final class MyItems {
    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(MyIdentifiers.ITEM_GROUP, MyItems::stupidJavaCompiler);
    public static final BlockItem PAN = new BlockItem(MyBlocks.PAN, new FabricItemSettings().group(ITEM_GROUP));
    public static final BlockItem GRILL = new BlockItem(MyBlocks.GRILL, new FabricItemSettings().group(ITEM_GROUP));
    public static final Item PAN_FRIED_BEEF_PATTY = new TooltipedFoodItem(new FabricItemSettings().group(ITEM_GROUP).food(new FoodComponent.Builder().statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 15), 1).meat().alwaysEdible().build()));
    static {
        Registry.register(Registry.ITEM, MyIdentifiers.PAN, PAN);
        Registry.register(Registry.ITEM, MyIdentifiers.GRILL, GRILL);
        Registry.register(Registry.ITEM, MyIdentifiers.PAN_FRIED_BEEF_PATTY, PAN_FRIED_BEEF_PATTY);
    }

    private static ItemStack stupidJavaCompiler() {
        return PAN.getDefaultStack();
    }

    private MyItems() {}
}
