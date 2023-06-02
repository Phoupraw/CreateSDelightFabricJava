package phoupraw.mcmod.createsdelight.mixin;

import net.minecraft.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = Blocks.class, priority = 1500)
public abstract class MixinBlocks {
    //@SuppressWarnings({"MixinAnnotationTarget", "UnresolvedMixinReference", "InvalidInjectorMethodSignature"})
    //@Redirect(method = "<clinit>", slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=iron_bars")), at = @At(value = "NEW", target = "net/minecraft/block/PaneBlock", ordinal = 0), require = 0)
    //private static PaneBlock ironBars(AbstractBlock.Settings settings) {
    //    return new IronBarsBlock(settings);
    //}
}
