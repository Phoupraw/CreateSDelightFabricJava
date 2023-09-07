package phoupraw.mcmod.createsdelight.misc;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
public @interface TypeVarName {
    String value();
}
