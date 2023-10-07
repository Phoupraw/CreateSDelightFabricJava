package phoupraw.mcmod.createsdelight.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@Environment(EnvType.CLIENT)
public final class CDREIClientPlugin implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        //EntryStack<ItemStack> depot = EntryStacks.of(AllBlocks.DEPOT.get()), belt = EntryStacks.of(AllBlocks.BELT.get());
        //registry.addWorkstations(CategoryIdentifier.of(Create.asResource("sequenced_assembly")), depot, belt, EntryStacks.of(AllBlocks.DEPLOYER.get()), EntryStacks.of(AllBlocks.MECHANICAL_PRESS.get()), EntryStacks.of(AllBlocks.MECHANICAL_SAW.get()), EntryStacks.of(AllBlocks.SPOUT.get()));//给序列组装加上工作站
    }
    @Override
    public void registerDisplays(DisplayRegistry registry) {
        //registry.registerRecipeFiller(PanFryingRecipe.class, CDRecipeTypes.PAN_FRYING.getRecipeType(), PanFryingDisplay::new);
        //registry.registerRecipeFiller(GrillingRecipe.class, CDRecipeTypes.GRILLING.getRecipeType(), GrillingDisplay::new);
        //registry.registerRecipeFiller(SprinklingRecipe.class, CDRecipeTypes.SPRINKLING.getRecipeType(), SprinklingDisplay::new);
        //registry.registerRecipeFiller(SteamingRecipe.class, CDRecipeTypes.STEAMING.getRecipeType(), SteamingDisplay::new);
        //registry.registerRecipeFiller(VerticalCuttingRecipe.class, CDRecipeTypes.VERTICAL_CUTTING.getRecipeType(), VerticalCuttingDisplay::new);
        //registry.registerRecipeFiller(PressureCookingRecipe.class, CDRecipeTypes.PRESSURE_COOKING.getRecipeType(), PressureCookingDisplay::new);
        //registry.registerRecipeFiller(MincingRecipe.class, CDRecipeTypes.MINCING.getRecipeType(), MincingDisplay::new);
        //registry.registerRecipeFiller(BakingRecipe.class, CDRecipeTypes.BAKING.getRecipeType(), BakingDisplay::new);
        //        registry.registerFiller(LootTable);
        //        registry.registerDisplayGenerator(LootTableCategory.ID, new DynamicDisplayGenerator<>() {
        //            @Override
        //            public Optional<List<LootTableDisplay>> getRecipeFor(EntryStack<?> entry) {
        //                return DynamicDisplayGenerator.super.getRecipeFor(entry);
        //            }
        //
        //            @Override
        //            public Optional<List<LootTableDisplay>> getUsageFor(EntryStack<?> entry) {
        //                return DynamicDisplayGenerator.super.getUsageFor(entry);
        //            }
        //
        //            @Override
        //            public Optional<List<LootTableDisplay>> generate(ViewSearchBuilder builder) {
        //
        //                return DynamicDisplayGenerator.super.generate(builder);
        //            }
        //        });
    }
}
