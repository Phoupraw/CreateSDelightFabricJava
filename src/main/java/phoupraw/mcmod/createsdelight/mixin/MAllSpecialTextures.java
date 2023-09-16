package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.AllSpecialTextures;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import phoupraw.mcmod.createsdelight.registry.CSDIdentifiers;

import java.util.Arrays;

@Mixin(value = AllSpecialTextures.class, remap = false)
class MAllSpecialTextures {
    private MAllSpecialTextures(String name, int ordinal, String filename) {
        throw new AssertionError("MAllSpecialTextures");
    }
    @Mutable
    @Shadow
    @Final
    private static AllSpecialTextures[] $VALUES;
    @Shadow private Identifier location;
    static {
        int ordinal = $VALUES.length;
        $VALUES = Arrays.copyOf($VALUES, ordinal + 1);
        var myTexture = new MAllSpecialTextures(CSDIdentifiers.VOXEL_MAKER.toString(), ordinal, "");
        $VALUES[ordinal] = (AllSpecialTextures) (Object) myTexture;
        myTexture.location = CSDIdentifiers.VOXEL_MAKER.withPrefixedPath(AllSpecialTextures.ASSET_PATH).withSuffixedPath(".png");
    }
}
