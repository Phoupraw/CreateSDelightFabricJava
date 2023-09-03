package phoupraw.mcmod.createsdelight.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.Factory;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import phoupraw.mcmod.createsdelight.block.entity.*;

///**
// * 方块实体及其方块编写流程：
// * <ol>
// * <li>在{@link phoupraw.mcmod.createsdelight.block.entity}创建方块实体类类（继承{@link SmartBlockEntity}），创建符合{@link Factory}的构造器<br/>
// * <li>在{@link phoupraw.mcmod.createsdelight.block}创建方块类，继承{@link Block}，实现{@link IBE}<br/>
// * <li>在{@link phoupraw.mcmod.createsdelight.block.entity.renderer}创建方块实体渲染器类（继承{@link SmartBlockEntityRenderer}）
// * <li>在{@link CSDIdentifiers}创建{@link Identifier}<br/>
// * <li>在{@link CSDBlocks}创建{@link Block}<b>并注册</b><br/>
// * <li>在{@link CSDItems}创建{@link BlockItem}<b>并注册</b><br/>
// * <li>在{@link CSDBlockEntityTypes}创建包含{@link Block}的{@link BlockEntityType}<b>并注册</b><br/>
// * <li>补齐方块实体类的构造器中的{@link BlockEntityType}<br/>
// * <li>补齐方块类的{@link IBE#getBlockEntityClass()}和{@link IBE#getBlockEntityType()}<br/>
// * <li>在{@link CSDChineseProvider}和{@link CSDEnglishProvider}添加翻译<br/>
// * <li>在{@link CSDModelProvider}添加方块状态和物品模型<br/>
// * <li>在{@link CSDRecipeProvider}添加配方<br/>
// * <li>在{@link CSDBlockLootTableProvider}添加战利品表<br/>
// * <li>在{@link CSDBlockTagProvider}添加标签<br/>
// * <li>运行数据生成器<br/>
// * <li>在{@code src/main/resources/assets/createsdelight/models/block}创建方块模型<br/>
// * <li>在{@link CSDClientModInitializer}添加{@link BlockEntityRendererFactories}。<br/>
// * <li>运行客户端，检查方块效果是否如预期<br/>
// * <li>在{@code ChangeLog.md}添加更新日志<br/>
// * <li>提交git
// * </ol>
// */
public final class CSDBlockEntityTypes {

    public static final BlockEntityType<PrintedCakeBlockEntity> PRINTED_CAKE = of(PrintedCakeBlockEntity::new, CSDBlocks.PRINTED_CAKE);
    public static final BlockEntityType<CakeOvenBlockEntity> CAKE_OVEN = of(CakeOvenBlockEntity::new, CSDBlocks.CAKE_OVEN);
    public static final BlockEntityType<ReadyCakeBlockEntity> READY_CAKE = of(ReadyCakeBlockEntity::new, CSDBlocks.READY_CAKE);
    public static final BlockEntityType<ShrinkingCakeBlockEntity> SHRINKING_CAKE = of(ShrinkingCakeBlockEntity::new, CSDBlocks.SHRINKING_CAKE);
    public static final BlockEntityType<MovingCakeBlockEntity> MOVING_CAKE = of(MovingCakeBlockEntity::new, CSDBlocks.MOVING_CAKE);
    static {
        register(CSDIdentifiers.PRINTED_CAKE, PRINTED_CAKE);
        register(CSDIdentifiers.CAKE_OVEN, CAKE_OVEN);
        register(CSDIdentifiers.READY_CAKE, READY_CAKE);
        register(CSDIdentifiers.SHRINKING_CAKE, SHRINKING_CAKE);
        register(CSDIdentifiers.MOVING_CAKE, MOVING_CAKE);
    }
    @Contract("_, _ -> param2")
    public static <T extends BlockEntity> BlockEntityType<T> register(Identifier id, BlockEntityType<T> blockEntityType) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, blockEntityType);
    }

    /**
     * @since 1.0.0
     */
    @Contract(value = "_,_->new", pure = true)
    public static <T extends BlockEntity> BlockEntityType<T> of(Factory<T> factory, Block... blocks) {
        return FabricBlockEntityTypeBuilder.create(factory, blocks).build();
    }

    private CSDBlockEntityTypes() {
    }

}
