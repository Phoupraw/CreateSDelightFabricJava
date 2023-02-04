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
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import phoupraw.mcmod.createsdelight.block.entity.BambooSteamerBlockEntity;
import phoupraw.mcmod.createsdelight.block.entity.GrillBlockEntity;
import phoupraw.mcmod.createsdelight.block.entity.PanBlockEntity;
import phoupraw.mcmod.createsdelight.block.entity.SprinklerBlockEntity;
import phoupraw.mcmod.createsdelight.datagen.*;
/**
 * 方块及其方块实体编写流程：<br>
 * - 创建{@link BlockEntity}类，继承{@link SmartTileEntity}，创建符合{@link Factory}的构造器<br>
 * - 创建{@link Block}类，实现{@link ITE}<br>
 * - 创建{@link BlockEntityRenderer}类，继承{@link SmartTileEntityRenderer}
 * - 在{@link MyIdentifiers}创建{@link Identifier}<br>
 * - 在{@link MyBlocks}创建{@link Block}<b>并注册</b><br>
 * - 在{@link MyItems}创建{@link BlockItem}<b>并注册</b><br>
 * - 在{@link MyBlockEntityTypes}创建包含{@link Block}的{@link BlockEntityType}<b>并注册</b><br>
 * - 补齐方块实体类的构造器中的{@link BlockEntityType}<br>
 * - 补齐方块类的{@link ITE#getTileEntityClass()}和{@link ITE#getTileEntityType()}<br>
 * - 在{@link MyChineseProvider}和{@link MyEnglishProvider}添加翻译<br>
 * - 在{@link MyModelProvider}添加方块状态和物品模型<br>
 * - 在{@link MyRecipeProvider}添加配方<br>
 * - 在{@link MyBlockLootTableProvider}添加战利品表<br>
 * - 在{@link MyBlockTagProvider}添加标签<br>
 * - 运行数据生成器<br>
 * - 在{@code src/main/resources/assets/createsdelight/models/block}创建方块模型<br>
 * - 在{@link MyClientModInitializer}添加{@link BlockEntityRendererRegistry}和{@link BlockRenderLayerMap}
 * - 运行客户端，检查方块效果是否如预期<br>
 * - 在{@code ChangeLog.md}添加更新日志<br>
 * - 提交git
 */
public final class MyBlockEntityTypes {
    public static final BlockEntityType<PanBlockEntity> PAN = FabricBlockEntityTypeBuilder.create(PanBlockEntity::new, MyBlocks.PAN).build();
    public static final BlockEntityType<GrillBlockEntity> GRILL = FabricBlockEntityTypeBuilder.create(GrillBlockEntity::new, MyBlocks.GRILL).build();
    public static final BlockEntityType<SprinklerBlockEntity> SPRINKLER = FabricBlockEntityTypeBuilder.create(SprinklerBlockEntity::new, MyBlocks.SPRINKLER).build();
    public static final BlockEntityType<BambooSteamerBlockEntity> BAMBOO_STEAMER = FabricBlockEntityTypeBuilder.create(BambooSteamerBlockEntity::new, MyBlocks.BAMBOO_STEAMER).build();

    static {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, MyIdentifiers.PAN, PAN);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, MyIdentifiers.GRILL, GRILL);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, MyIdentifiers.SPRINKLER, SPRINKLER);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, MyIdentifiers.BAMBOO_STEAMER, BAMBOO_STEAMER);
    }

    private MyBlockEntityTypes() {}


}
