package phoupraw.mcmod.createsdelight.item;

import com.google.common.base.Predicates;
import com.simibubi.create.content.contraptions.components.deployer.DeployerRecipeSearchEvent;
import com.simibubi.create.content.contraptions.goggles.IHaveGoggleInformation;
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.api.GetWorld;
import phoupraw.mcmod.createsdelight.api.GetWorldContainerItemContext;
import phoupraw.mcmod.createsdelight.api.ItemStorage2;
import phoupraw.mcmod.createsdelight.recipe.ItemBowlRecipe;
import phoupraw.mcmod.createsdelight.registry.CDFluids;
import phoupraw.mcmod.createsdelight.registry.CDItems;
import phoupraw.mcmod.createsdelight.storage.IronBowlFluidStorage;
import phoupraw.mcmod.createsdelight.storage.IronBowlItemStorage;

import java.util.List;
import java.util.Optional;
public class IronBowlItem extends Item implements IHaveGoggleInformation {
    public static void onInitialize() {
        ItemStorage2.ITEM.registerForItems((itemStack, context) -> new IronBowlItemStorage(context, GetWorld.getOrNull(context)), CDItems.IRON_BOWL);
        FluidStorage.ITEM.registerForItems((itemStack, context) -> new IronBowlFluidStorage(context, GetWorld.getOrNull(context)), CDItems.IRON_BOWL);
        DeployerRecipeSearchEvent.EVENT.register(event -> {
            ItemStack bowlStack = event.getInventory().getStack(0);
            ContainerItemContext itemContext = ContainerItemContext.withConstant(bowlStack);
            World world = event.getTileEntity().getWorld();
            if (!bowlStack.isOf(CDItems.IRON_BOWL) || !new IronBowlFluidStorage(itemContext, world).isResourceBlank()) return;
            var itemS = new IronBowlItemStorage(itemContext, world);
            if (!itemS.isResourceBlank()) return;
            event.addRecipe(() -> Optional.of(new ItemBowlRecipe(event)), 200);
        });
    }

    @Contract(pure = true)
    public static @NotNull ItemStack bowl(ItemVariant toBeStored) {
        var ironBowl = CDItems.IRON_BOWL.getDefaultStack();
        var itemS = new IronBowlItemStorage(GetWorldContainerItemContext.of(ironBowl), null);
        TransferUtil.insert(itemS, toBeStored, 1);
        return ironBowl;
    }

    public static String getSuffixKey() {
        return CDItems.IRON_BOWL.getTranslationKey() + ".suffix";
    }

    public IronBowlItem() {
        this(CDItems.newSettings().maxCount(1));
    }

    public IronBowlItem(Settings settings) {
        super(settings);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        ContainerItemContext itemContext = ContainerItemContext.withConstant(stack);
        var itemS = new IronBowlItemStorage(itemContext, null);
        if (itemS.isResourceBlank()) {
            containedFluidTooltip(tooltip, false, new IronBowlFluidStorage(itemContext, null));
        } /*else {
            ItemStack itemStack = itemS.getResource().toStack();
            tooltip.add(Text.empty().append(itemStack.getName()).formatted(itemStack.getRarity().formatting));
        }*/
    }

    @Override
    public Text getName(ItemStack stack) {
        var name = super.getName(stack);
        ContainerItemContext itemContext = ContainerItemContext.withConstant(stack);
        var itemS = new IronBowlItemStorage(itemContext, null);
        if (!itemS.isResourceBlank()) {
            ItemStack itemStack = itemS.getResource().toStack();
            name = Text.translatable(getSuffixKey(), name, Text.empty().append(itemStack.getName()).formatted(itemStack.getRarity().formatting));
        } else {
            var fluidS = new IronBowlFluidStorage(itemContext, null);
            if (!fluidS.isResourceBlank()) {
                name = Text.translatable(getSuffixKey(), name, FluidVariantAttributes.getName(fluidS.getResource()));
            }
        }
        return name;
    }

    /**
     鼠标持{@code otherStack}（其它物品）点击位于{@code slot}的{@code stack}（铁碗）
     <p>
     这破方法名已经让我混淆很多次了。
     */
    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType == ClickType.RIGHT) {
            var ironBowl = new IronBowlItemStorage(ContainerItemContext.ofSingleSlot(storageOf(slot)), player.isCreative() ? null : player.getWorld());
            var other = PlayerInventoryStorage.getCursorStorage(player.currentScreenHandler);
            if (move(ironBowl, other)) return true;
        }
        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }

    /**
     鼠标持{@code stack}（铁碗）点击{@code slot}（其它物品）
     <p>
     这破方法名已经让我混淆很多次了。
     */
    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (clickType == ClickType.RIGHT) {
            var ironBowl = new IronBowlItemStorage(ContainerItemContext.ofPlayerCursor(player, player.currentScreenHandler), player.isCreative() ? null : player.getWorld());
            if (move(ironBowl, storageOf(slot))) return true;
        }
        return super.onStackClicked(stack, slot, clickType, player);
    }

    public static boolean move(Storage<ItemVariant> ironBowl, Storage<ItemVariant> other) {
        return StorageUtil.move(ironBowl, other, Predicates.alwaysTrue(), Long.MAX_VALUE, null) != 0 || StorageUtil.move(other, ironBowl, Predicates.alwaysTrue(), Long.MAX_VALUE, null) != 0;
    }

    public static SingleSlotStorage<ItemVariant> storageOf(Slot slot) {
        return InventoryStorage.of(slot.inventory, null).getSlot(slot.getIndex());
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        super.appendStacks(group, stacks);
        if (group == CDItems.ITEM_GROUP) {
            ItemStack ironBowl = getDefaultStack();
            var fluidS = new IronBowlFluidStorage(GetWorldContainerItemContext.of(ironBowl), null);
            TransferUtil.insert(fluidS, FluidVariant.of(CDFluids.PASTE), Long.MAX_VALUE);
            stacks.add(ironBowl.copy());
            TransferUtil.clearStorage(fluidS);
            TransferUtil.insert(fluidS, FluidVariant.of(CDFluids.APPLE_PASTE), Long.MAX_VALUE);
            stacks.add(ironBowl.copy());
            TransferUtil.clearStorage(fluidS);
            TransferUtil.insert(fluidS, FluidVariant.of(CDFluids.CHOCOLATE_PASTE), Long.MAX_VALUE);
            stacks.add(ironBowl.copy());
        }
    }
}
