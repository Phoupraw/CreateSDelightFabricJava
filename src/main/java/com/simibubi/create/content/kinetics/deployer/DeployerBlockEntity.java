package com.simibubi.create.content.kinetics.deployer;

import static com.simibubi.create.content.kinetics.base.DirectionalKineticBlock.FACING;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.equipment.sandPaper.SandPaperItem;
import com.simibubi.create.content.equipment.sandPaper.SandPaperPolishingRecipe.SandPaperInv;
import com.simibubi.create.content.kinetics.base.IRotate.StressImpact;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.belt.behaviour.BeltProcessingBehaviour;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipe;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.utility.Components;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.NBTHelper;
import com.simibubi.create.foundation.utility.VecHelper;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandlerContainer;
import io.github.fabricators_of_create.porting_lib.util.NBTSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.recipe.Recipe;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DeployerBlockEntity extends KineticBlockEntity implements SidedStorageBlockEntity {

    protected State state;
    protected Mode mode;
    protected ItemStack heldItem = ItemStack.EMPTY;
    protected DeployerFakePlayer player;
    protected int timer;
    protected float reach;
    protected boolean fistBump = false;
    protected List<ItemStack> overflowItems = new ArrayList<>();
    protected FilteringBehaviour filtering;
    protected boolean redstoneLocked;
    private DeployerItemHandler invHandler;
    private NbtList deferredInventoryList;

    private LerpedFloat animatedOffset;

    public BeltProcessingBehaviour processingBehaviour;

    // list holds the held item in index 0, followed by overflow items
    public SnapshotParticipant<List<ItemStack>> snapshotParticipant = new SnapshotParticipant<>() {
        @Override
        protected List<ItemStack> createSnapshot() {
            List<ItemStack> stacks = new ArrayList<>();
            stacks.add(player.getMainHandStack());
            stacks.addAll(overflowItems);
            return stacks;
        }

        @Override
        protected void readSnapshot(List<ItemStack> snapshot) {
            player.setStackInHand(Hand.MAIN_HAND, snapshot.remove(0));
            overflowItems = snapshot;
        }
    };

    public enum State {
        WAITING, EXPANDING, RETRACTING, DUMPING;
    }

    public enum Mode {
        PUNCH, USE
    }

    public DeployerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.state = State.WAITING;
        mode = Mode.USE;
        heldItem = ItemStack.EMPTY;
        redstoneLocked = false;
        animatedOffset = LerpedFloat.linear()
          .startWithValue(0);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        filtering = new FilteringBehaviour(this, new DeployerFilterSlot());
        behaviours.add(filtering);
        processingBehaviour =
          new BeltProcessingBehaviour(this).whenItemEnters((s, i) -> BeltDeployerCallbacks.onItemReceived(s, i, this))
            .whileItemHeld((s, i) -> BeltDeployerCallbacks.whenItemHeld(s, i, this));
        behaviours.add(processingBehaviour);

        registerAwardables(behaviours, AllAdvancements.TRAIN_CASING, AllAdvancements.ANDESITE_CASING,
          AllAdvancements.BRASS_CASING, AllAdvancements.COPPER_CASING, AllAdvancements.FIST_BUMP,
          AllAdvancements.DEPLOYER, AllAdvancements.SELF_DEPLOYING);
    }

    @Override
    public void initialize() {
        super.initialize();
        initHandler();
    }

    private void initHandler() {
        if (invHandler != null)
            return;
        if (world instanceof ServerWorld sLevel) {
            player = new DeployerFakePlayer(sLevel, null);
            if (deferredInventoryList != null) {
                player.getInventory()
                  .readNbt(deferredInventoryList);
                deferredInventoryList = null;
                heldItem = player.getMainHandStack();
                sendData();
            }
            Vec3d initialPos = VecHelper.getCenterOf(pos.offset(getCachedState().get(FACING)));
            player.setPosition(initialPos.x, initialPos.y, initialPos.z);
        }
        invHandler = this.createHandler();
    }

    protected void onExtract(ItemStack stack) {
        player.setStackInHand(Hand.MAIN_HAND, stack.copy());
        sendData();
        markDirty();
    }

    protected int getTimerSpeed() {
        return (int) (getSpeed() == 0 ? 0 : MathHelper.clamp(Math.abs(getSpeed() * 2), 8, 512));
    }

    @Override
    public void tick() {
        super.tick();

        if (!overflowItems.isEmpty()) {
            overflowItems.removeIf(ItemStack::isEmpty);
        }

        if (getSpeed() == 0)
            return;
        if (!world.isClient && player != null && player.blockBreakingProgress != null) {
            if (world.isAir(player.blockBreakingProgress.getKey())) {
                world.setBlockBreakingInfo(player.getId(), player.blockBreakingProgress.getKey(), -1);
                player.blockBreakingProgress = null;
            }
        }
        if (timer > 0) {
            timer -= getTimerSpeed();
            return;
        }
        if (world.isClient)
            return;

        ItemStack stack = player.getMainHandStack();
        if (state == State.WAITING) {
            if (!overflowItems.isEmpty()) {
                timer = getTimerSpeed() * 10;
                return;
            }

            boolean changed = false;
            PlayerInventory inventory = player.getInventory();
            for (int i = 0; i < inventory.size(); i++) {
                if (overflowItems.size() > 10)
                    break;
                ItemStack item = inventory.getStack(i);
                if (item.isEmpty())
                    continue;
                if (item != stack || !filtering.test(item)) {
                    overflowItems.add(item);
                    inventory.setStack(i, ItemStack.EMPTY);
                    changed = true;
                }
            }

            if (changed) {
                sendData();
                timer = getTimerSpeed() * 10;
                return;
            }

            Direction facing = getCachedState().get(FACING);
            if (mode == Mode.USE
              && !DeployerHandler.shouldActivate(stack, world, pos.offset(facing, 2), facing)) {
                timer = getTimerSpeed() * 10;
                return;
            }

            // Check for advancement conditions
            if (mode == Mode.PUNCH && !fistBump && startFistBump(facing))
                return;
            if (redstoneLocked)
                return;

            start();
            return;
        }

        if (state == State.EXPANDING) {
            if (fistBump)
                triggerFistBump();
            activate();

            state = State.RETRACTING;
            timer = 1000;
            sendData();
            return;
        }

        if (state == State.RETRACTING) {
            state = State.WAITING;
            timer = 500;
            sendData();
            return;
        }

    }

    protected void start() {
        state = State.EXPANDING;
        Vec3d movementVector = getMovementVector();
        Vec3d rayOrigin = VecHelper.getCenterOf(pos)
          .add(movementVector.multiply(3 / 2f));
        Vec3d rayTarget = VecHelper.getCenterOf(pos)
          .add(movementVector.multiply(5 / 2f));
        RaycastContext rayTraceContext = new RaycastContext(rayOrigin, rayTarget, ShapeType.OUTLINE, FluidHandling.NONE, player);
        BlockHitResult result = world.raycast(rayTraceContext);
        reach = (float) (.5f + Math.min(result.getPos()
          .subtract(rayOrigin)
          .length(), .75f));
        timer = 1000;
        sendData();
    }

    public boolean startFistBump(Direction facing) {
        int i = 0;
        DeployerBlockEntity partner = null;

        for (i = 2; i < 5; i++) {
            BlockPos otherDeployer = pos.offset(facing, i);
            if (!world.canSetBlock(otherDeployer))
                return false;
            BlockEntity otherTile = world.getBlockEntity(otherDeployer);
            if (otherTile instanceof DeployerBlockEntity dpe) {
                partner = dpe;
                break;
            }
        }

        if (partner == null)
            return false;

        if (world.getBlockState(partner.getPos())
          .get(FACING)
          .getOpposite() != facing || partner.mode != Mode.PUNCH)
            return false;
        if (partner.getSpeed() == 0)
            return false;

        for (DeployerBlockEntity te : Arrays.asList(this, partner)) {
            te.fistBump = true;
            te.reach = ((i - 2)) * .5f;
            te.timer = 1000;
            te.state = State.EXPANDING;
            te.sendData();
        }

        return true;
    }

    public void triggerFistBump() {
        int i = 0;
        DeployerBlockEntity deployerTile = null;
        for (i = 2; i < 5; i++) {
            BlockPos pos = getPos().offset(getCachedState().get(FACING), i);
            if (!world.canSetBlock(pos))
                return;
            if (world.getBlockEntity(pos) instanceof DeployerBlockEntity dpe) {
                deployerTile = dpe;
                break;
            }
        }

        if (deployerTile == null)
            return;
        if (!deployerTile.fistBump || deployerTile.state != State.EXPANDING)
            return;
        if (deployerTile.timer > 0)
            return;

        fistBump = false;
        deployerTile.fistBump = false;
        deployerTile.state = State.RETRACTING;
        deployerTile.timer = 1000;
        deployerTile.sendData();
        award(AllAdvancements.FIST_BUMP);

        BlockPos soundLocation = new BlockPos(Vec3d.ofCenter(pos)
          .add(Vec3d.ofCenter(deployerTile.getPos()))
          .multiply(.5f));
        world.playSound(null, soundLocation, SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, SoundCategory.BLOCKS, .75f, .75f);
    }

    protected void activate() {
        Vec3d movementVector = getMovementVector();
        Direction direction = getCachedState().get(FACING);
        Vec3d center = VecHelper.getCenterOf(pos);
        BlockPos clickedPos = pos.offset(direction, 2);
        player.setPitch(direction == Direction.UP ? -90 : direction == Direction.DOWN ? 90 : 0);
        player.setYaw(direction.asRotation());

        if (direction == Direction.DOWN
          && BlockEntityBehaviour.get(world, clickedPos, TransportedItemStackHandlerBehaviour.TYPE) != null)
            return; // Belt processing handled in BeltDeployerCallbacks

        DeployerHandler.activate(player, center, clickedPos, movementVector, mode);
        award(AllAdvancements.DEPLOYER);

        if (player != null)
            heldItem = player.getMainHandStack();
    }

    protected Vec3d getMovementVector() {
        if (!AllBlocks.DEPLOYER.has(getCachedState()))
            return Vec3d.ZERO;
        return Vec3d.of(getCachedState().get(FACING)
          .getVector());
    }

    @Override
    protected void read(NbtCompound compound, boolean clientPacket) {
        state = NBTHelper.readEnum(compound, "State", State.class);
        mode = NBTHelper.readEnum(compound, "Mode", Mode.class);
        timer = compound.getInt("Timer");
        redstoneLocked = compound.getBoolean("Powered");

        deferredInventoryList = compound.getList("Inventory", NbtElement.COMPOUND_TYPE);
        overflowItems = NBTHelper.readItemList(compound.getList("Overflow", NbtElement.COMPOUND_TYPE));
        if (compound.contains("HeldItem"))
            heldItem = ItemStack.fromNbt(compound.getCompound("HeldItem"));
        super.read(compound, clientPacket);

        if (!clientPacket)
            return;
        fistBump = compound.getBoolean("Fistbump");
        reach = compound.getFloat("Reach");
        if (compound.contains("Particle")) {
            ItemStack particleStack = ItemStack.fromNbt(compound.getCompound("Particle"));
            SandPaperItem.spawnParticles(VecHelper.getCenterOf(pos)
              .add(getMovementVector().multiply(reach + 1)), particleStack, this.world);
        }
    }

    @Override
    public void write(NbtCompound compound, boolean clientPacket) {
        NBTHelper.writeEnum(compound, "Mode", mode);
        NBTHelper.writeEnum(compound, "State", state);
        compound.putInt("Timer", timer);
        compound.putBoolean("Powered", redstoneLocked);

        if (player != null) {
            NbtList invNBT = new NbtList();
            player.getInventory()
              .writeNbt(invNBT);
            compound.put("Inventory", invNBT);
            compound.put("HeldItem", NBTSerializer.serializeNBT(player.getMainHandStack()));
            compound.put("Overflow", NBTHelper.writeItemList(overflowItems));
        } else if (deferredInventoryList != null) {
            compound.put("Inventory", deferredInventoryList);
        }

        super.write(compound, clientPacket);

        if (!clientPacket)
            return;
        compound.putBoolean("Fistbump", fistBump);
        compound.putFloat("Reach", reach);
        if (player == null)
            return;
        compound.put("HeldItem", NBTSerializer.serializeNBT(player.getMainHandStack()));
        if (player.spawnedItemEffects != null) {
            compound.put("Particle", NBTSerializer.serializeNBT(player.spawnedItemEffects));
            player.spawnedItemEffects = null;
        }
    }

    @Override
    public void writeSafe(NbtCompound tag) {
        NBTHelper.writeEnum(tag, "Mode", mode);
        super.writeSafe(tag);
    }

    private DeployerItemHandler createHandler() {
        return new DeployerItemHandler(this);
    }

    public void redstoneUpdate() {
        if (world.isClient)
            return;
        boolean blockPowered = world.isReceivingRedstonePower(pos);
        if (blockPowered == redstoneLocked)
            return;
        redstoneLocked = blockPowered;
        sendData();
    }

    @Environment(EnvType.CLIENT)
    public PartialModel getHandPose() {
        return mode == Mode.PUNCH ? AllPartialModels.DEPLOYER_HAND_PUNCHING
          : heldItem.isEmpty() ? AllPartialModels.DEPLOYER_HAND_POINTING : AllPartialModels.DEPLOYER_HAND_HOLDING;
    }

    @Override
    protected Box createRenderBoundingBox() {
        return super.createRenderBoundingBox().expand(3);
    }

    public void discardPlayer() {
        if (player == null)
            return;
        player.getInventory()
          .dropAll();
        overflowItems.forEach(itemstack -> player.dropItem(itemstack, true, false));
        player.discard();
        player = null;
    }

    @Override
    public void invalidate() {
        super.invalidate();
    }

    public void changeMode() {
        mode = mode == Mode.PUNCH ? Mode.USE : Mode.PUNCH;
        markDirty();
        sendData();
    }

    @Nullable
    @Override
    public Storage<ItemVariant> getItemStorage(@Nullable Direction face) {
        if (invHandler == null)
            initHandler();
        return invHandler;
    }

    @Override
    public boolean addToTooltip(List<Text> tooltip, boolean isPlayerSneaking) {
        if (super.addToTooltip(tooltip, isPlayerSneaking))
            return true;
        if (getSpeed() == 0)
            return false;
        if (overflowItems.isEmpty())
            return false;
        TooltipHelper.addHint(tooltip, "hint.full_deployer");
        return true;
    }

    @Override
    public boolean addToGoggleTooltip(List<Text> tooltip, boolean isPlayerSneaking) {
        Lang.translate("tooltip.deployer.header")
          .forGoggles(tooltip);

        Lang.translate("tooltip.deployer." + (mode == Mode.USE ? "using" : "punching"))
          .style(Formatting.YELLOW)
          .forGoggles(tooltip);

        if (!heldItem.isEmpty())
            Lang.translate("tooltip.deployer.contains",
                Components.translatable(heldItem.getTranslationKey()).getString(), heldItem.getCount())
              .style(Formatting.GREEN)
              .forGoggles(tooltip);

        float stressAtBase = calculateStressApplied();
        if (StressImpact.isEnabled() && !MathHelper.approximatelyEquals(stressAtBase, 0)) {
            tooltip.add(Components.immutableEmpty());
            addStressImpactStats(tooltip, stressAtBase);
        }

        return true;
    }

    @Environment(EnvType.CLIENT)
    public float getHandOffset(float partialTicks) {
        if (isVirtual())
            return animatedOffset.getValue(partialTicks);

        float progress = 0;
        int timerSpeed = getTimerSpeed();
        PartialModel handPose = getHandPose();

        if (state == State.EXPANDING) {
            progress = 1 - (timer - partialTicks * timerSpeed) / 1000f;
            if (fistBump)
                progress *= progress;
        }
        if (state == State.RETRACTING)
            progress = (timer - partialTicks * timerSpeed) / 1000f;
        float handLength = handPose == AllPartialModels.DEPLOYER_HAND_POINTING ? 0
          : handPose == AllPartialModels.DEPLOYER_HAND_HOLDING ? 4 / 16f : 3 / 16f;
        float distance = Math.min(MathHelper.clamp(progress, 0, 1) * (reach + handLength), 21 / 16f);

        return distance;
    }

    public void setAnimatedOffset(float offset) {
        animatedOffset.setValue(offset);
    }

    ItemStackHandlerContainer recipeInv = new ItemStackHandlerContainer(2);
    SandPaperInv sandpaperInv = new SandPaperInv(ItemStack.EMPTY);

    @Nullable
    public Recipe<? extends Inventory> getRecipe(ItemStack stack) {
        if (player == null || world == null)
            return null;

        ItemStack heldItemMainhand = player.getMainHandStack();
        if (heldItemMainhand.getItem() instanceof SandPaperItem) {
            sandpaperInv.setStack(0, stack);
            return AllRecipeTypes.SANDPAPER_POLISHING.find(sandpaperInv, world)
              .orElse(null);
        }

        recipeInv.setStack(0, stack);
        recipeInv.setStack(1, heldItemMainhand);

        DeployerRecipeSearchEvent event = new DeployerRecipeSearchEvent(this, recipeInv);

        event.addRecipe(() -> SequencedAssemblyRecipe.getRecipe(world, event.getInventory(),
          AllRecipeTypes.DEPLOYING.getType(), DeployerApplicationRecipe.class), 100);
        event.addRecipe(() -> AllRecipeTypes.DEPLOYING.find(event.getInventory(), world), 50);
        event.addRecipe(() -> AllRecipeTypes.ITEM_APPLICATION.find(event.getInventory(), world), 50);

        DeployerRecipeSearchEvent.EVENT.invoker().handle(event);
        return event.getRecipe();
    }

    public DeployerFakePlayer getPlayer() {
        return player;
    }

    public List<ItemStack> getOverflowItems() {
        return overflowItems;
    }
}
