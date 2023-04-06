package phoupraw.mcmod.createsdelight.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.PaneBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import phoupraw.mcmod.createsdelight.block.IronBarsBlock;
@Mixin(value = Blocks.class, priority = 1500)
public abstract class MixinBlocks {
    @Redirect(method = "<clinit>", slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=iron_bars")), at = @At(value = "NEW", target = "net/minecraft/block/PaneBlock", ordinal = 0), require = 0)
    private static PaneBlock ironBars(AbstractBlock.Settings settings) {
        return new IronBarsBlock(settings);
    }
}
