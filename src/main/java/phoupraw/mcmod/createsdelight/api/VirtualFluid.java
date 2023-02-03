package phoupraw.mcmod.createsdelight.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.transfer.v1.fluid.*;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.EmptyItemFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.FullItemFluidStorage;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
public class VirtualFluid extends AbstractVirtualFluid {
    public static final FluidVariantAttributeHandler ATTRIBUTE_HANDLER = new FluidVariantAttributeHandler() {
        @Override
        public Text getName(FluidVariant fluidVariant) {
            return Text.translatable(getTranslationKey(fluidVariant.getFluid()));
        }
    };

    public static FluidStorage.CombinedItemApiProvider fullProviderOf(Item emptyItem, FluidVariant variant, long amount) {
        return context -> new FullItemFluidStorage(context, emptyItem, variant, amount);
    }

    public static FluidStorage.CombinedItemApiProvider emptyProviderOf(Item fullItem, Fluid fluid, long amount) {
        return context -> new EmptyItemFluidStorage(context, fullItem, fluid, amount);
    }

    public static String getTranslationKey(Fluid fluid) {
        Identifier id = Registry.FLUID.getId(fluid);
        return "fluid" + "." + id.getNamespace() + "." + id.getPath().replace('/', '.');
    }

    private final Item bucket;
    private final Item bottle;

    public VirtualFluid(Item bucket, Item bottle) {
        this.bucket = bucket;
        this.bottle = bottle;
    }

    /**
     * Water and Lava both return 100.0F.
     */
    @Override
    protected float getBlastResistance() {
        return 100.0F;
    }

    @Override
    public boolean isStill(FluidState state) {
        return true;
    }

    @Override
    public Item getBucketItem() {
        return bucket;
    }

    /**
     * Lava returns true if it's FluidState is above a certain height and the
     * Fluid is Water.
     *
     * @return whether the given Fluid can flow into this FluidState
     */
    @Override
    protected boolean canBeReplacedWith(FluidState fluidState, BlockView blockView, BlockPos blockPos, Fluid fluid, Direction direction) {
        return false;
    }

    /**
     * Water returns 5. Lava returns 30 in the Overworld and 10 in the Nether.
     */
    @Override
    public int getTickRate(WorldView worldView) {
        return 5;
    }

    @Override
    public Fluid getStill() {
        return this;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return Blocks.AIR.getDefaultState();
    }

    /**
     * @return whether the given fluid an instance of this fluid
     */
    @Override
    public boolean matchesType(Fluid fluid) {
        return fluid == getStill() || fluid == getFlowing();
    }

    @Override
    public Fluid getFlowing() {
        return this;
    }

    /**
     * @return whether the fluid is infinite like water
     */
    @Override
    protected boolean isInfinite() {
        return false;
    }

    /**
     * Perform actions when the fluid flows into a replaceable block. Water drops
     * the block's loot table. Lava plays the "block.lava.extinguish" sound.
     */
    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        final BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropStacks(state, world, pos, blockEntity);
    }

    /**
     * Possibly related to the distance checks for flowing into nearby holes?
     * Water returns 4. Lava returns 2 in the Overworld and 4 in the Nether.
     */
    @Override
    protected int getFlowSpeed(WorldView worldView) {
        return 4;
    }

    /**
     * Water returns 1. Lava returns 2 in the Overworld and 1 in the Nether.
     */
    @Override
    protected int getLevelDecreasePerBlock(WorldView worldView) {
        return 1;
    }

    @Override
    public int getLevel(FluidState state) {
        return 0;
    }

    public Item getBottle() {
        return bottle;
    }

    public static class Builder {
        private Identifier id;
        private Item bucket;
        private Item bottle;
        private @Nullable ItemGroup itemGroup;
        private int tint;
        private boolean customTexture;

        public Builder withId(Identifier id) {
            this.id = id;
            return this;
        }

        @SuppressWarnings("ConstantConditions")
        public Builder withBucket(@Nullable Item bucket) {
            this.bucket = bucket;
            return this;
        }

        @SuppressWarnings("ConstantConditions")
        public Builder withBottle(@Nullable Item bottle) {
            this.bottle = bottle;
            return this;
        }

        public Builder withItemGroup(@Nullable ItemGroup itemGroup) {
            this.itemGroup = itemGroup;
            return this;
        }

        public Builder withTint(int rgb) {
            this.tint = rgb;
            return this;
        }

        public Builder withCustomTexture() {
            this.customTexture = true;
            return this;
        }

        public VirtualFluid build() {
            if (bucket == null) bucket = new Item(new FabricItemSettings().group(itemGroup).maxCount(1).recipeRemainder(Items.BUCKET));
            if (bottle == null) bottle = new Item(new FabricItemSettings().group(itemGroup).maxCount(16).recipeRemainder(Items.GLASS_BOTTLE));
            return new VirtualFluid(bucket, bottle);
        }

        /**
         * @return
         * @throws NullPointerException 当{@link #id}为{@code null}时
         */
        public VirtualFluid buildAndRegister() {
            var fluid = build();
            Registry.register(Registry.FLUID, Objects.requireNonNull(id, "id cannot be null when invoke buildAndRegister"), fluid);
            if (bucket != Items.AIR) {
                if (Registry.ITEM.getId(bucket).equals(Registry.ITEM.getDefaultId())) {
                    Registry.register(Registry.ITEM, new Identifier(id.getNamespace(), id.getPath() + "_bucket"), bucket);
                }
                FluidStorage.combinedItemApiProvider(bucket).register(fullProviderOf(Items.BUCKET, FluidVariant.of(fluid), FluidConstants.BUCKET));
                FluidStorage.combinedItemApiProvider(Items.BUCKET).register(emptyProviderOf(bucket, fluid, FluidConstants.BUCKET));
            }
            if (bottle != Items.AIR) {
                if (Registry.ITEM.getId(bottle).equals(Registry.ITEM.getDefaultId())) {
                    Registry.register(Registry.ITEM, new Identifier(id.getNamespace(), id.getPath() + "_bottle"), bottle);
                    FluidStorage.combinedItemApiProvider(bottle).register(fullProviderOf(Items.GLASS_BOTTLE, FluidVariant.of(fluid), FluidConstants.BOTTLE));
                    FluidStorage.combinedItemApiProvider(Items.GLASS_BOTTLE).register(emptyProviderOf(bottle, fluid, FluidConstants.BOTTLE));
                }
            }
            FluidVariantAttributes.register(fluid, ATTRIBUTE_HANDLER);
            if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                registerClient(fluid);
            }
            return fluid;
        }
        @Environment(EnvType.CLIENT)
        public void registerClient(VirtualFluid fluid) {
            FluidRenderHandler renderHandler;
            if (customTexture) {
                var textureId = new Identifier(id.getNamespace(), "block/" + id.getPath());
                renderHandler = new SimpleFluidRenderHandler(textureId, textureId);
            } else {
                renderHandler = SimpleFluidRenderHandler.coloredWater(tint);
            }
            FluidRenderHandlerRegistry.INSTANCE.register(fluid, fluid.getFlowing(), renderHandler);
            BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), fluid, fluid.getFlowing());
        }
    }
}
