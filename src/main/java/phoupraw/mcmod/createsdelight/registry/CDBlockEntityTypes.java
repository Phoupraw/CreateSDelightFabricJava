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
 <li>在{@link phoupraw.mcmod.createsdelight.block.entity}创建方块实体类类（继承{@link SmartTileEntity}），创建符合{@link Factory}的构造器<br/>
 <li>在{@link phoupraw.mcmod.createsdelight.block}创建方块类，继承{@link Block}，实现{@link ITE}<br/>
 <li>在{@link phoupraw.mcmod.createsdelight.block.entity.renderer}创建方块实体渲染器类（继承{@link SmartTileEntityRenderer}）
 <li>在{@link CDIdentifiers}创建{@link Identifier}<br/>
 <li>在{@link CDBlocks}创建{@link Block}<b>并注册</b><br/>
 <li>在{@link CDItems}创建{@link BlockItem}<b>并注册</b><br/>
 <li>在{@link CDBlockEntityTypes}创建包含{@link Block}的{@link BlockEntityType}<b>并注册</b><br/>
 <li>补齐方块实体类的构造器中的{@link BlockEntityType}<br/>
 <li>补齐方块类的{@link ITE#getTileEntityClass()}和{@link ITE#getTileEntityType()}<br/>
 <li>在{@link MyChineseProvider}和{@link MyEnglishProvider}添加翻译<br/>
 <li>在{@link MyModelProvider}添加方块状态和物品模型<br/>
 <li>在{@link MyRecipeProvider}添加配方<br/>
 <li>在{@link MyBlockLootTableProvider}添加战利品表<br/>
 <li>在{@link MyBlockTagProvider}添加标签<br/>
 <li>运行数据生成器<br/>
 <li>在{@code src/main/resources/assets/createsdelight/models/block}创建方块模型<br/>
 <li>在{@link CDClientModInitializer}添加{@link BlockEntityRendererFactories}。<br/>
 <li>运行客户端，检查方块效果是否如预期<br/>
 <li>在{@code ChangeLog.md}添加更新日志<br/>
 <li>提交git
 </ol> */
public final class CDBlockEntityTypes {
    public static final BlockEntityType<PanBlockEntity> PAN = Registries.of(PanBlockEntity::new, CDBlocks.PAN);
    public static final BlockEntityType<GrillBlockEntity> GRILL = Registries.of(GrillBlockEntity::new, CDBlocks.GRILL);
    public static final BlockEntityType<SprinklerBlockEntity> SPRINKLER = Registries.of(SprinklerBlockEntity::new, CDBlocks.SPRINKLER);
    public static final BlockEntityType<BambooSteamerBlockEntity> BAMBOO_STEAMER = Registries.of(BambooSteamerBlockEntity::new, CDBlocks.BAMBOO_STEAMER);
    public static final BlockEntityType<SmartDrainBlockEntity> SMART_DRAIN = Registries.of(SmartDrainBlockEntity::new, CDBlocks.SMART_DRAIN);
    public static final BlockEntityType<CopperTunnelBlockEntity> COPPER_TUNNEL = Registries.of(CopperTunnelBlockEntity::new, CDBlocks.COPPER_TUNNEL);
    public static final BlockEntityType<MultifuncBasinBlockEntity> MULTIFUNC_BASIN = Registries.of(MultifuncBasinBlockEntity::new, CDBlocks.MULTIFUNC_BASIN);
    public static final BlockEntityType<VerticalCutterBlockEntity> VERTICAL_CUTTER = Registries.of(VerticalCutterBlockEntity::new, CDBlocks.VERTICAL_CUTTER);
    public static final BlockEntityType<PressureCookerBlockEntity> PRESSURE_COOKER = Registries.of(PressureCookerBlockEntity::new, CDBlocks.PRESSURE_COOKER);
    public static final BlockEntityType<MincerBlockEntity> MINCER = Registries.of(MincerBlockEntity::new, CDBlocks.MINCER);
    public static final BlockEntityType<SkewerBlockEntity> SKEWER = Registries.of(SkewerBlockEntity::new, CDBlocks.SKEWER);
    public static final BlockEntityType<MyBasinBlockEntity> BASIN = Registries.of(MyBasinBlockEntity::new, CDBlocks.BASIN);
    public static final BlockEntityType<SkewerPlateBlockEntity> SKEWER_PLATE = Registries.of(SkewerPlateBlockEntity::new, CDBlocks.SKEWER_PLATE);
    public static final BlockEntityType<OvenBlockEntity> OVEN = Registries.of(OvenBlockEntity::new, CDBlocks.OVEN);
    public static final BlockEntityType<IronBarSkewerBlockEntity> IRON_BAR_SKEWER = Registries.of(IronBarSkewerBlockEntity::new, CDBlocks.IRON_BAR_SKEWER);
    public static final BlockEntityType<PrintedCakeBE> PRINTED_CAKE = Registries.of(PrintedCakeBE::new, CDBlocks.PRINTED_CAKE);
    static {
        Registries.register(CDIdentifiers.PAN, PAN);
        Registries.register(CDIdentifiers.GRILL, GRILL);
        Registries.register(CDIdentifiers.SPRINKLER, SPRINKLER);
        Registries.register(CDIdentifiers.BAMBOO_STEAMER, BAMBOO_STEAMER);
        Registries.register(CDIdentifiers.SMART_DRAIN, SMART_DRAIN);
        Registries.register(CDIdentifiers.COPPER_TUNNEL, COPPER_TUNNEL);
        Registries.register(CDIdentifiers.MULTIFUNC_BASIN, MULTIFUNC_BASIN);
        Registries.register(CDIdentifiers.VERTICAL_CUTTER, VERTICAL_CUTTER);
        Registries.register(CDIdentifiers.PRESSURE_COOKER, PRESSURE_COOKER);
        Registries.register(CDIdentifiers.MINCER, MINCER);
        Registries.register(CDIdentifiers.SKEWER, SKEWER);
        Registries.register(CDIdentifiers.BASIN, BASIN);
        Registries.register(CDIdentifiers.SKEWER_PLATE, SKEWER_PLATE);
        Registries.register(CDIdentifiers.OVEN, OVEN);
        Registries.register(CDIdentifiers.IRON_BAR_SKEWER, IRON_BAR_SKEWER);
        Registries.register(CDIdentifiers.PRINTED_CAKE, PRINTED_CAKE);
    }

    private CDBlockEntityTypes() {
    }


}
