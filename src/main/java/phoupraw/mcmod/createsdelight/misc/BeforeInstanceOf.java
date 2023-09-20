package phoupraw.mcmod.createsdelight.misc;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.spongepowered.asm.mixin.injection.InjectionPoint;

import java.util.Collection;

//@InjectionPoint.AtCode(namespace = CreateSDelight.MOD_ID,value = "instanceof")
//TODO
public class BeforeInstanceOf extends InjectionPoint {
    @Override
    public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
        return false;
    }
}
