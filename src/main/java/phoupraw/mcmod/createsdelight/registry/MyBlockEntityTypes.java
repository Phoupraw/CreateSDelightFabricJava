package phoupraw.mcmod.createsdelight.registry;

import com.simibubi.create.foundation.block.ITE;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.renderer.SmartTileEntityRenderer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.Factory;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import phoupraw.mcmod.createsdelight.block.entity.*;
import phoupraw.mcmod.createsdelight.datagen.*;
/**
 * 方块实体及其方块编写流程：
 * <ol>
 * <li>创建方块实体类类（继承{@link SmartTileEntity}），创建符合{@link Factory}的构造器<br>
 * <li>创建方块类，继承{@link Block}，实现{@link ITE}<br>
 * <li>创建方块实体渲染器类（继承{@link SmartTileEntityRenderer}）
 * <li>在{@link MyIdentifiers}创建{@link Identifier}<br>
 * <li>在{@link MyBlocks}创建{@link Block}<b>并注册</b><br>
 * <li>在{@link MyItems}创建{@link BlockItem}<b>并注册</b><br>
 * <li>在{@link MyBlockEntityTypes}创建包含{@link Block}的{@link BlockEntityType}<b>并注册</b><br>
 * <li>补齐方块实体类的构造器中的{@link BlockEntityType}<br>
 * <li>补齐方块类的{@link ITE#getTileEntityClass()}和{@link ITE#getTileEntityType()}<br>
 * <li>在{@link MyChineseProvider}和{@link MyEnglishProvider}添加翻译<br>
 * <li>在{@link MyModelProvider}添加方块状态和物品模型<br>
 * <li>在{@link MyRecipeProvider}添加配方<br>
 * <li>在{@link MyBlockLootTableProvider}添加战利品表<br>
 * <li>在{@link MyBlockTagProvider}添加标签<br>
 * <li>运行数据生成器<br>
 * <li>在{@code src/main/resources/assets/createsdelight/models/block}创建方块模型<br>
 * <li>在{@link MyClientModInitializer}添加{@link BlockEntityRendererRegistry}和{@link BlockRenderLayerMap}
 * <li>运行客户端，检查方块效果是否如预期<br>
 * <li>在{@code ChangeLog.md}添加更新日志<br>
 * <li>提交git
 * </ol>
 */
public final class MyBlockEntityTypes {
    public static final BlockEntityType<PanBlockEntity> PAN = buildType(PanBlockEntity::new, MyBlocks.PAN);
    public static final BlockEntityType<GrillBlockEntity> GRILL = buildType(GrillBlockEntity::new, MyBlocks.GRILL);
    public static final BlockEntityType<SprinklerBlockEntity> SPRINKLER = buildType(SprinklerBlockEntity::new, MyBlocks.SPRINKLER);
    public static final BlockEntityType<BambooSteamerBlockEntity> BAMBOO_STEAMER = buildType(BambooSteamerBlockEntity::new, MyBlocks.BAMBOO_STEAMER);
    public static final BlockEntityType<SmartDrainBlockEntity> SMART_DRAIN = buildType(SmartDrainBlockEntity::new, MyBlocks.SMART_DRAIN);
    public static final BlockEntityType<CopperTunnelBlockEntity> COPPER_TUNNEL = buildType(CopperTunnelBlockEntity::new, MyBlocks.COPPER_TUNNEL);
    public static final BlockEntityType<MultifuncBasinBlockEntity> MULTIFUNC_BASIN = buildType(MultifuncBasinBlockEntity::new, MyBlocks.MULTIFUNC_BASIN);

    static {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, MyIdentifiers.PAN, PAN);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, MyIdentifiers.GRILL, GRILL);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, MyIdentifiers.SPRINKLER, SPRINKLER);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, MyIdentifiers.BAMBOO_STEAMER, BAMBOO_STEAMER);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, MyIdentifiers.SMART_DRAIN, SMART_DRAIN);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, MyIdentifiers.COPPER_TUNNEL, COPPER_TUNNEL);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, MyIdentifiers.MULTIFUNC_BASIN, MULTIFUNC_BASIN);
    }
    public static <T extends BlockEntity> BlockEntityType<T> buildType(FabricBlockEntityTypeBuilder.Factory<T> factory, @NotNull net.minecraft.block.Block... blocks) {
        return FabricBlockEntityTypeBuilder.create(factory, blocks).build();
    }

    private MyBlockEntityTypes() {}


}
