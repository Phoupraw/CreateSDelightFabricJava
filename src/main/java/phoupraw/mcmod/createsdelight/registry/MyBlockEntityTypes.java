package phoupraw.mcmod.createsdelight.registry;

import com.simibubi.create.foundation.block.ITE;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.renderer.SmartTileEntityRenderer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.Factory;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import phoupraw.mcmod.common.api.Registries;
import phoupraw.mcmod.createsdelight.block.entity.*;
import phoupraw.mcmod.createsdelight.datagen.*;
/**
 方块实体及其方块编写流程：
 <ol>
 <li>在{@link phoupraw.mcmod.createsdelight.block.entity}创建方块实体类类（继承{@link SmartTileEntity}），创建符合{@link Factory}的构造器<br>
 <li>在{@link phoupraw.mcmod.createsdelight.block}创建方块类，继承{@link Block}，实现{@link ITE}<br>
 <li>在{@link phoupraw.mcmod.createsdelight.block.entity.renderer}创建方块实体渲染器类（继承{@link SmartTileEntityRenderer}）
 <li>在{@link MyIdentifiers}创建{@link Identifier}<br>
 <li>在{@link MyBlocks}创建{@link Block}<b>并注册</b><br>
 <li>在{@link MyItems}创建{@link BlockItem}<b>并注册</b><br>
 <li>在{@link MyBlockEntityTypes}创建包含{@link Block}的{@link BlockEntityType}<b>并注册</b><br>
 <li>补齐方块实体类的构造器中的{@link BlockEntityType}<br>
 <li>补齐方块类的{@link ITE#getTileEntityClass()}和{@link ITE#getTileEntityType()}<br>
 <li>在{@link MyChineseProvider}和{@link MyEnglishProvider}添加翻译<br>
 <li>在{@link MyModelProvider}添加方块状态和物品模型<br>
 <li>在{@link MyRecipeProvider}添加配方<br>
 <li>在{@link MyBlockLootTableProvider}添加战利品表<br>
 <li>在{@link MyBlockTagProvider}添加标签<br>
 <li>运行数据生成器<br>
 <li>在{@code src/main/resources/assets/createsdelight/models/block}创建方块模型<br>
 <li>在{@link MyClientModInitializer}添加{@link BlockEntityRendererFactories}。<br>
 <li>运行客户端，检查方块效果是否如预期<br>
 <li>在{@code ChangeLog.md}添加更新日志<br>
 <li>提交git
 </ol> */
public final class MyBlockEntityTypes {
    public static final BlockEntityType<PanBlockEntity> PAN = Registries.of(PanBlockEntity::new, MyBlocks.PAN);
    public static final BlockEntityType<GrillBlockEntity> GRILL = Registries.of(GrillBlockEntity::new, MyBlocks.GRILL);
    public static final BlockEntityType<SprinklerBlockEntity> SPRINKLER = Registries.of(SprinklerBlockEntity::new, MyBlocks.SPRINKLER);
    public static final BlockEntityType<BambooSteamerBlockEntity> BAMBOO_STEAMER = Registries.of(BambooSteamerBlockEntity::new, MyBlocks.BAMBOO_STEAMER);
    public static final BlockEntityType<SmartDrainBlockEntity> SMART_DRAIN = Registries.of(SmartDrainBlockEntity::new, MyBlocks.SMART_DRAIN);
    public static final BlockEntityType<CopperTunnelBlockEntity> COPPER_TUNNEL = Registries.of(CopperTunnelBlockEntity::new, MyBlocks.COPPER_TUNNEL);
    public static final BlockEntityType<MultifuncBasinBlockEntity> MULTIFUNC_BASIN = Registries.of(MultifuncBasinBlockEntity::new, MyBlocks.MULTIFUNC_BASIN);
    public static final BlockEntityType<VerticalCutterBlockEntity> VERTICAL_CUTTER = Registries.of(VerticalCutterBlockEntity::new, MyBlocks.VERTICAL_CUTTER);
    public static final BlockEntityType<PressureCookerBlockEntity> PRESSURE_COOKER = Registries.of(PressureCookerBlockEntity::new, MyBlocks.PRESSURE_COOKER);
    public static final BlockEntityType<MincerBlockEntity> MINCER = Registries.of(MincerBlockEntity::new, MyBlocks.MINCER);
    public static final BlockEntityType<SkewerBlockEntity> SKEWER = Registries.of(SkewerBlockEntity::new, MyBlocks.SKEWER);
    public static final BlockEntityType<MyBasinBlockEntity> BASIN = Registries.of(MyBasinBlockEntity::new, MyBlocks.BASIN);
    public static final BlockEntityType<SkewerPlateBlockEntity> SKEWER_PLATE = Registries.of(SkewerPlateBlockEntity::new, MyBlocks.SKEWER_PLATE);

    static {
        Registries.register(MyIdentifiers.PAN, PAN);
        Registries.register(MyIdentifiers.GRILL, GRILL);
        Registries.register(MyIdentifiers.SPRINKLER, SPRINKLER);
        Registries.register(MyIdentifiers.BAMBOO_STEAMER, BAMBOO_STEAMER);
        Registries.register(MyIdentifiers.SMART_DRAIN, SMART_DRAIN);
        Registries.register(MyIdentifiers.COPPER_TUNNEL, COPPER_TUNNEL);
        Registries.register(MyIdentifiers.MULTIFUNC_BASIN, MULTIFUNC_BASIN);
        Registries.register(MyIdentifiers.VERTICAL_CUTTER, VERTICAL_CUTTER);
        Registries.register(MyIdentifiers.PRESSURE_COOKER, PRESSURE_COOKER);
        Registries.register(MyIdentifiers.MINCER, MINCER);
        Registries.register(MyIdentifiers.SKEWER, SKEWER);
        Registries.register(MyIdentifiers.BASIN, BASIN);
        Registries.register(MyIdentifiers.SKEWER_PLATE, SKEWER_PLATE);
    }

    private MyBlockEntityTypes() {}


}
