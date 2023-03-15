package phoupraw.mcmod.createsdelight.api;

import com.nhoryzon.mc.farmersdelight.block.BasketBlock;
import com.nhoryzon.mc.farmersdelight.entity.block.BasketBlockEntity;
import com.nhoryzon.mc.farmersdelight.entity.block.CuttingBoardBlockEntity;
import com.nhoryzon.mc.farmersdelight.entity.block.SkilletBlockEntity;
import com.nhoryzon.mc.farmersdelight.entity.block.StoveBlockEntity;
import com.nhoryzon.mc.farmersdelight.registry.BlockEntityTypesRegistry;
import com.simibubi.create.content.contraptions.relays.belt.transport.TransportedItemStack;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.belt.DirectBeltInputBehaviour;
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.CreateSDelight;
/**
 * @see DirectBeltInputBehaviour
 */
public final class DirectBeltInput {
    /**
     * 注册传送带直接输入开放式容器的回调函数。用法与{@link ItemStorage#SIDED}类似，但是{@link BlockApiLookup#find}中的{@code context}参数是多余的，故以{@link Void}作为类型，只能传{@code null}，仅作为占位符，没有用处。
     */
    public static final BlockApiLookup<InsertionHandler, @Nullable Void> LOOKUP = BlockApiLookup.get(new Identifier(CreateSDelight.MOD_ID, "direct_belt_input"), InsertionHandler.class, Void.class);

    static {
        LOOKUP.registerFallback((world, pos, state, blockEntity, v) -> {
            if (!(blockEntity instanceof SmartTileEntity smart)) return null;
            var behaviour = TileEntityBehaviour.get(smart, DirectBeltInputBehaviour.TYPE);
            if (behaviour == null) return null;
            return (InsertionHandler) behaviour;
        });
        LOOKUP.registerForBlockEntities((blockEntity, v) -> {
            var storage = ItemStorage.SIDED.find(blockEntity.getWorld(), blockEntity.getPos(), null, blockEntity, Direction.UP);
            if (storage == null) return null;
            return InsertionHandler.of(storage);
        }, BlockEntityTypesRegistry.COOKING_POT.get());
        LOOKUP.registerForBlockEntity((stove, v) -> InsertionHandler.of(FarmersDelightWrappers.storageOf(stove)), BlockEntityTypesRegistry.STOVE.<StoveBlockEntity>get());
        if (FarmersDelightWrappers.SKILLET) {
            LOOKUP.registerForBlockEntity((skillet, v) -> InsertionHandler.of(FarmersDelightWrappers.storageOf(skillet)), BlockEntityTypesRegistry.SKILLET.<SkilletBlockEntity>get());
        }
        LOOKUP.registerForBlockEntity((board, v) -> InsertionHandler.of(FarmersDelightWrappers.storageOf(board)), BlockEntityTypesRegistry.CUTTING_BOARD.<CuttingBoardBlockEntity>get());
        LOOKUP.registerForBlockEntity((basket, v) -> {
            var facing = basket.getCachedState().get(BasketBlock.FACING);
            var defaultHandler = InsertionHandler.of(InventoryStorage.of(basket, null));
            return switch (facing) {
                case UP -> defaultHandler;
                case DOWN -> null;
                default -> (stack, direction, simulate) -> direction.getOpposite() != facing ? stack.stack : defaultHandler.handleInsertion(stack, direction, simulate);
            };
        }, BlockEntityTypesRegistry.BASKET.<BasketBlockEntity>get());
    }

    private DirectBeltInput() {}

    @FunctionalInterface
    public interface InsertionHandler {
        /**
         * @param stack     要被输入的物品
         * @param direction 要被输入的方向，例如，向<b>东</b>传输的传送带向它<b>东边</b>的置物台传输物品，置物台从<b>西边</b>被输入物品，则此参数为{@link Direction#WEST}
         * @param simulate  如果为{@code true}，则不会发生实际的输入
         * @return 从 {@code stack} 扣除成功输入的物品后剩余的物品
         * @see DirectBeltInputBehaviour#handleInsertion(TransportedItemStack, Direction, boolean)
         */
        ItemStack handleInsertion(TransportedItemStack stack, Direction direction, boolean simulate);
        /**
         * 默认等同于{@code handleInsertion(new TransportedItemStack(stack), direction, simulate)}
         *
         * @see #handleInsertion(TransportedItemStack, Direction, boolean)
         * @see DirectBeltInputBehaviour#handleInsertion(ItemStack, Direction, boolean)
         */
        default ItemStack handleInsertion(ItemStack stack, Direction direction, boolean simulate) {
            return handleInsertion(new TransportedItemStack(stack), direction, simulate);
        }
        /**
         * 包装
         * @see DirectBeltInputBehaviour#defaultInsertionCallback
         */
        static InsertionHandler of(@Nullable Storage<ItemVariant> storage) {
            return (inserted, direction, simulate) -> {
                if (storage == null) return inserted.stack;
                try (Transaction t = TransferUtil.getTransaction()) {
                    long trying = inserted.stack.getCount();
                    long successful = storage.insert(ItemVariant.of(inserted.stack), inserted.stack.getCount(), t);
                    if (trying == successful) {
                        if (!simulate) t.commit();
                        return ItemStack.EMPTY;
                    }
                    ItemStack stack = inserted.stack.copy();
                    stack.setCount((int) (trying - successful));
                    if (!simulate) t.commit();
                    return stack;
                }
            };
        }
//        /**
//         * @return 是否支持安山岩漏斗像置物台、分液池、动力锯那样贴合。默认为 {@code false}
//         * @see DirectBeltInputBehaviour#allowingBeltFunnels()
//         */
//        default boolean isAllowingBeltFunnels() {
//            return false;
//        }
    }

}
