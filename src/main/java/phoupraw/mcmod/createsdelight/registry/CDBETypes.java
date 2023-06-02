package phoupraw.mcmod.createsdelight.registry;

import com.simibubi.create.foundation.block.ITE;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.renderer.SmartTileEntityRenderer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.Factory;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Contract;
import phoupraw.mcmod.common.api.Registries;
import phoupraw.mcmod.createsdelight.block.entity.CakeOvenBE;
import phoupraw.mcmod.createsdelight.block.entity.PrintedCakeBE;
import phoupraw.mcmod.createsdelight.datagen.*;
/**
 方块实体及其方块编写流程：
 <ol>
 <li>在{@link phoupraw.mcmod.createsdelight.block.entity}创建方块实体类类（继承{@link SmartTileEntity}），创建符合{@link Factory}的构造器<br/>
 <li>在{@link phoupraw.mcmod.createsdelight.block}创建方块类，继承{@link Block}，实现{@link ITE}<br/>
 <li>在{@link phoupraw.mcmod.createsdelight.block.entity.renderer}创建方块实体渲染器类（继承{@link SmartTileEntityRenderer}）
 <li>在{@link CDIdentifiers}创建{@link Identifier}<br/>
 <li>在{@link CDBlocks}创建{@link Block}<b>并注册</b><br/>
 <li>在{@link CDItems}创建{@link BlockItem}<b>并注册</b><br/>
 <li>在{@link CDBETypes}创建包含{@link Block}的{@link BlockEntityType}<b>并注册</b><br/>
 <li>补齐方块实体类的构造器中的{@link BlockEntityType}<br/>
 <li>补齐方块类的{@link ITE#getTileEntityClass()}和{@link ITE#getTileEntityType()}<br/>
 <li>在{@link CDChineseProvider}和{@link CDEnglishProvider}添加翻译<br/>
 <li>在{@link CDModelProvider}添加方块状态和物品模型<br/>
 <li>在{@link CDRecipeProvider}添加配方<br/>
 <li>在{@link CDBlockLootTableProvider}添加战利品表<br/>
 <li>在{@link CDBlockTagProvider}添加标签<br/>
 <li>运行数据生成器<br/>
 <li>在{@code src/main/resources/assets/createsdelight/models/block}创建方块模型<br/>
 <li>在{@link CDClientModInitializer}添加{@link BlockEntityRendererFactories}。<br/>
 <li>运行客户端，检查方块效果是否如预期<br/>
 <li>在{@code ChangeLog.md}添加更新日志<br/>
 <li>提交git
 </ol> */
public final class CDBETypes {
    public static final BlockEntityType<PrintedCakeBE> PRINTED_CAKE = Registries.of(PrintedCakeBE::new, CDBlocks.PRINTED_CAKE);
    public static final BlockEntityType<CakeOvenBE> CAKE_OVEN = Registries.of(CakeOvenBE::new, CDBlocks.CAKE_OVEN);
    static {
        register(CDIdentifiers.PRINTED_CAKE, PRINTED_CAKE);
        register(CDIdentifiers.CAKE_OVEN, CAKE_OVEN);
    }

    @Contract("_, _ -> param2")
    public static <T extends BlockEntity> BlockEntityType<T> register(Identifier id, BlockEntityType<T> blockEntityType) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, id, blockEntityType);
    }

    private CDBETypes() {
    }


}
