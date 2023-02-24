package phoupraw.mcmod.createsdelight.block.entity;

import com.nhoryzon.mc.farmersdelight.registry.SoundsRegistry;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.content.contraptions.relays.belt.transport.TransportedItemStack;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.belt.BeltProcessingBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.belt.TransportedItemStackHandlerBehaviour;
import com.simibubi.create.foundation.utility.recipe.RecipeConditions;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.recipe.VerticalCuttingRecipe;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
import phoupraw.mcmod.createsdelight.registry.MyRecipeTypes;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static phoupraw.mcmod.createsdelight.instance.VerticalCutterInstance.ALTITUDE;
public class VerticalCutterBlockEntity extends KineticTileEntity implements InstanceOffset {
    /**
     * 当{@link #extending}为{@code false}时，{@link #extension}至少减小到此值，才允许将{@link #extending}设为{@code true}。
     */
    public static final double MIDFIELD = 0.7;

    public static void apply(@NotNull TransportedItemStack beltHeld, TransportedItemStackHandlerBehaviour inventory, @NotNull ProcessingRecipe<?> recipe) {
        List<TransportedItemStack> outputs = new LinkedList<>();
        for (ItemStack rollResult : recipe.rollResults()) {
            var transp = beltHeld.copy();
            transp.stack = rollResult;
            outputs.add(transp);
        }
        var leftover = beltHeld.copy();
        leftover.stack.decrement(1);
        inventory.handleProcessingOnItem(beltHeld, TransportedItemStackHandlerBehaviour.TransportedResult.convertToAndLeaveHeld(outputs, leftover));
    }

    /**
     * 刀片伸出的程度，范围为[0,1]。
     *
     * @see #extending
     */
    private double extension;
    /**
     * {@link #extension}的渲染配套。无论有无动力，都与{@link #extension}在1游戏刻前的值保持一致。
     */
    private double prevExtention;
    /**
     * 为{@code true}时，除非无动力或{@link #extension}达到最大值，否则{@link #extension}持续增加；为{@code false}时，除非无动力或{@link #extension}达到最小值，否则{@link #extension}持续减小。当{@link #extension}达到最大值时，自动变为{@code false}。
     *
     * @see #MIDFIELD
     */
    private boolean extending;
    /**
     * 已经切了的次数，即{@link #extension}到达最大值的次数。
     *
     * @see VerticalCuttingRecipe#knives
     */
    private int chopped;

    public VerticalCutterBlockEntity(BlockPos pos, BlockState state) {this(MyBlockEntityTypes.VERTICAL_CUTTER, pos, state);}

    public VerticalCutterBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    public void addBehaviours(List<TileEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        behaviours.add(new BeltProcessingBehaviour(this).whenItemEnters(this::whenItemEnters).whileItemHeld(this::whileItemHeld));
    }

    @Override
    protected void write(NbtCompound compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        compound.putBoolean("extending", isExtending());
        compound.putDouble("extension", getExtension());
        compound.putInt("chopped", getChopped());
    }

    @Override
    protected void read(NbtCompound compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        setExtending(compound.getBoolean("extending"));
        setExtension(compound.getDouble("extension"));
        setChopped(compound.getInt("chopped"));
    }

    /**
     * 仅在服务端被调用
     */
    public BeltProcessingBehaviour.ProcessingResult whenItemEnters(TransportedItemStack beltHeld, TransportedItemStackHandlerBehaviour inventory) {
        var recipe = findRecipe(beltHeld.stack);
        return recipe == null ? BeltProcessingBehaviour.ProcessingResult.PASS : BeltProcessingBehaviour.ProcessingResult.HOLD;
    }

    /**
     * 仅在服务端被调用
     */
    public BeltProcessingBehaviour.ProcessingResult whileItemHeld(TransportedItemStack beltHeld, TransportedItemStackHandlerBehaviour inventory) {
        var recipe = findRecipe(beltHeld.stack);
        if (recipe == null) {
            setExtending(false);
            setChopped(0);
            return BeltProcessingBehaviour.ProcessingResult.PASS;
        }
        if (getSpeed() == 0) return BeltProcessingBehaviour.ProcessingResult.HOLD;
        if (getChopped() >= recipe.getKnives()) {
            apply(beltHeld, inventory, recipe);
            setChopped(0);
        } else if (!isExtending() && getExtension() <= MIDFIELD) {
            setExtending(true);
            sendData();
        }
        return BeltProcessingBehaviour.ProcessingResult.HOLD;
    }

    @Override
    @NotNull
    public World getWorld() {
        return Objects.requireNonNull(super.getWorld());
    }

    public @Nullable VerticalCuttingRecipe findRecipe(ItemStack ingredient) {
        return getWorld().getRecipeManager().listAllOfType(MyRecipeTypes.VERTICAL_CUTTING.getRecipeType()).stream().filter(RecipeConditions.firstIngredientMatches(ingredient)).findFirst().orElse(null);
    }

    @Override
    public void tick() {
        super.tick();
        setPrevExtention(getExtension());
        if (getSpeed() == 0) return;
        double step = Math.abs(getSpeed()) / 500;
        if (isExtending()) {
            setExtension(getExtension() + step);
            if (getExtension() >= 1) {
                setExtending(false);//刀片到达最下部，收刀
                setChopped(getChopped() + 1);
                getWorld().playSound(null, getPos(), SoundsRegistry.BLOCK_CUTTING_BOARD_KNIFE.get(), SoundCategory.BLOCKS, 0.25f, 1);
            }
        } else {
            setExtension(getExtension() - step);
        }
    }

    @Override
    public double getOffset(float partialTicks) {
        return -0.001/*防止完全收回时深度冲突*/ - ALTITUDE * MathHelper.lerp(partialTicks, getPrevExtention(), getExtension());
    }

    /**
     * @see #extension
     */
    public double getExtension() {
        return extension;
    }

    /**
     * @see #extension
     */
    public void setExtension(double extension) {
        this.extension = MathHelper.clamp(extension, 0, 1);
    }

    /**
     * @see #prevExtention
     */
    public double getPrevExtention() {
        return prevExtention;
    }

    /**
     * @see #prevExtention
     */
    public void setPrevExtention(double prevExtention) {
        this.prevExtention = prevExtention;
    }

    /**
     * @see #extending
     */
    public boolean isExtending() {
        return extending;
    }

    /**
     * @see #extending
     */
    public void setExtending(boolean extending) {
        this.extending = extending;
    }

    /**
     * @see #chopped
     */
    public int getChopped() {
        return chopped;
    }

    /**
     * @see #chopped
     */
    public void setChopped(int chopped) {
        this.chopped = chopped;
    }
}
