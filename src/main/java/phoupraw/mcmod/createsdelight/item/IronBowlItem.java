package phoupraw.mcmod.createsdelight.item;

import com.mojang.datafixers.util.Pair;
import com.simibubi.create.content.contraptions.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.config.AllConfigs;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.LangBuilder;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import io.github.fabricators_of_create.porting_lib.util.FluidTextUtil;
import io.github.fabricators_of_create.porting_lib.util.FluidUnit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantItemStorage;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.registry.MyItems;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
public class IronBowlItem extends Item {
    /**
     @see IHaveGoggleInformation#containedFluidTooltip
     */
    @Contract("_, _, null -> false")
    public static boolean containedFluidTooltip(List<Text> tooltip, boolean isPlayerSneaking, @Nullable Storage<FluidVariant> handler) {
        if (handler == null)
            return false;
        FluidUnit unit = AllConfigs.CLIENT.fluidUnitType.get();
        boolean simplify = AllConfigs.CLIENT.simplifyFluidUnit.get();
        LangBuilder mb = Lang.translate(unit.getTranslationKey());
        Lang.translate("gui.goggles.fluid_container")
          .forGoggles(tooltip);

        boolean isEmpty = true;
        int tanks = 0;
        long firstCapacity = -1;

        for (StorageView<FluidVariant> view : handler) {
            if (tanks == 0)
                firstCapacity = view.getCapacity();
            tanks++;
            FluidStack fluidStack = new FluidStack(view);
            if (fluidStack.isEmpty())
                continue;

            Lang.fluidName(fluidStack)
              .style(Formatting.GRAY)
              .forGoggles(tooltip, 1);

            String amount = FluidTextUtil.getUnicodeMillibuckets(fluidStack.getAmount(), unit, simplify);
            Lang.builder()
              .add(Lang.text(amount)
                .add(mb)
                .style(Formatting.GOLD))
              .text(Formatting.GRAY, " / ")
              .add(Lang.text(FluidTextUtil.getUnicodeMillibuckets(view.getCapacity(), unit, simplify))
                .add(mb)
                .style(Formatting.DARK_GRAY))
              .forGoggles(tooltip, 1);

            isEmpty = false;
        }

        if (tanks > 1) {
            if (isEmpty)
                tooltip.remove(tooltip.size() - 1);
            return true;
        }

        if (!isEmpty)
            return true;

        Lang.translate("gui.goggles.fluid_container.capacity")
          .add(Lang.text(FluidTextUtil.getUnicodeMillibuckets(firstCapacity, unit, simplify))
            .add(mb)
            .style(Formatting.GOLD))
          .style(Formatting.GRAY)
          .forGoggles(tooltip, 1);

        return true;
    }

    public IronBowlItem() {
        this(MyItems.newSettings().maxCount(1));
    }

    public IronBowlItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        ContainerItemContext itemContext = ContainerItemContext.withConstant(stack);
        containedFluidTooltip(tooltip, false, new FluidS(itemContext));
        var itemS = new ItemS(itemContext);
        if (!itemS.isResourceBlank()) {
            ItemStack itemStack = itemS.getResource().toStack();
            tooltip.add(Text.empty().append(itemStack.getName()).formatted(itemStack.getRarity().formatting));
        }
    }

    public static class FluidS extends SingleVariantItemStorage<FluidVariant> {

        public FluidS(ContainerItemContext context) {
            super(context);
        }

        @Override
        protected FluidVariant getBlankResource() {
            return FluidVariant.blank();
        }

        @Override
        protected FluidVariant getResource(ItemVariant currentVariant) {
            var nbt = currentVariant.getNbt();
            if (nbt == null) return FluidVariant.blank();
            return FluidVariant.fromNbt(nbt.getCompound("fluid"));
        }

        @Override
        protected long getAmount(ItemVariant currentVariant) {
            var nbt = currentVariant.getNbt();
            if (nbt == null) return 0;
            return nbt.getLong("amount");
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return FluidConstants.BUCKET / 2;
        }

        @Override
        protected ItemVariant getUpdatedVariant(ItemVariant currentVariant, FluidVariant newResource, long newAmount) {
            if (newResource.isBlank() || newAmount == 0) {
                var nbt = currentVariant.copyNbt();
                if (nbt == null) return currentVariant;
                nbt.remove("fluid");
                nbt.remove("amount");
                return ItemVariant.of(currentVariant.getItem(), nbt);
            }
            var nbt = currentVariant.copyOrCreateNbt();
            nbt.put("fluid", newResource.toNbt());
            nbt.putLong("amount", newAmount);
            return ItemVariant.of(currentVariant.getItem(), nbt);
        }
    }

    public static class ItemS extends SingleVariantItemStorage<ItemVariant> {

        public ItemS(ContainerItemContext context) {
            super(context);
        }

        @Override
        protected ItemVariant getBlankResource() {
            return ItemVariant.blank();
        }

        @Override
        protected ItemVariant getResource(ItemVariant currentVariant) {
            var nbt = currentVariant.getNbt();
            if (nbt == null) return ItemVariant.blank();
            return ItemVariant.fromNbt(nbt.getCompound("item"));
        }

        @Override
        protected long getAmount(ItemVariant currentVariant) {
            var nbt = currentVariant.getNbt();
            return nbt == null || !nbt.contains("item", NbtElement.COMPOUND_TYPE) ? 0 : 1;
        }

        @Override
        protected long getCapacity(ItemVariant variant) {
            return 1;
        }

        @Override
        protected ItemVariant getUpdatedVariant(ItemVariant currentVariant, ItemVariant newResource, long newAmount) {
            if (newResource.isBlank() || newAmount == 0) {
                var nbt = currentVariant.copyNbt();
                if (nbt == null) return currentVariant;
                nbt.remove("item");
                return ItemVariant.of(currentVariant.getItem(), nbt);
            }
            var nbt = currentVariant.copyOrCreateNbt();
            nbt.put("item", newResource.toNbt());
            return ItemVariant.of(currentVariant.getItem(), nbt);
        }
    }

    @Environment(EnvType.CLIENT)
    public static class DynamicModel implements UnbakedModel, BakedModel, FabricBakedModel {

        @Override
        public boolean isVanillaAdapter() {
            return false;
        }

        @Override
        public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {

        }

        @Override
        public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
            var renderer = RendererAccess.INSTANCE.getRenderer();
            if (renderer == null) return;
            MeshBuilder builder = renderer.meshBuilder();
            QuadEmitter emitter = builder.getEmitter();
            var fluidS = new FluidS(ContainerItemContext.withConstant(stack));
            if (!fluidS.isResourceBlank()) {

            }
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
            return null;
        }

        @Override
        public boolean useAmbientOcclusion() {
            return false;
        }

        @Override
        public boolean hasDepth() {
            return false;
        }

        @Override
        public boolean isSideLit() {
            return false;
        }

        @Override
        public boolean isBuiltin() {
            return false;
        }

        @Override
        public Sprite getParticleSprite() {
            return null;
        }

        @Override
        public ModelTransformation getTransformation() {
            return null;
        }

        @Override
        public ModelOverrideList getOverrides() {
            return null;
        }

        @Override
        public Collection<Identifier> getModelDependencies() {
            return null;
        }

        @Override
        public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
            return null;
        }

        @Nullable
        @Override
        public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
            return null;
        }
    }
}
