package phoupraw.mcmod.createsdelight.registry;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import phoupraw.mcmod.createsdelight.CreateSDelight;
public final class MyItems {
    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(CreateSDelight.MOD_ID, "item_group"), MyItems::stupidJavaCompiler);
    public static final BlockItem PAN = new BlockItem(MyBlocks.PAN, new FabricItemSettings().group(ITEM_GROUP));
    public static final Item PAN_FRIED_BEEF_PATTY = new Item(new FabricItemSettings().group(ITEM_GROUP).food(new FoodComponent.Builder().statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 10), 1).build()));
    static {
        Registry.register(Registry.ITEM, Registry.BLOCK.getId(MyBlocks.PAN), PAN);
        Registry.register(Registry.ITEM, new Identifier(CreateSDelight.MOD_ID,"pan_fried_beef_patty"), PAN_FRIED_BEEF_PATTY);
    }

    private static ItemStack stupidJavaCompiler() {
        return PAN.getDefaultStack();
    }

    private MyItems() {}
}
