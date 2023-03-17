package phoupraw.mcmod.createsdelight.exp;

import net.minecraft.entity.effect.StatusEffectInstance;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
public interface Consumable {
    Collection<Pair<StatusEffectInstance, Double>> getEffects();
}
