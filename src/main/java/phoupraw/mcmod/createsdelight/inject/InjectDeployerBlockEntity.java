package phoupraw.mcmod.createsdelight.inject;

import com.simibubi.create.content.kinetics.deployer.DeployerBlockEntity;

import java.lang.reflect.Field;
public interface InjectDeployerBlockEntity {
    enum StupidJavaCompiler {}

    interface State {
        Class<StupidJavaCompiler> CLASS = stupidJavaCompiler_ClassForName("com.simibubi.create.content.contraptions.components.deployer.DeployerBlockEntity$State");
        //        Field FIELD = stupidJavaCompiler_getDeclaredField(DeployerBlockEntity.class, "state");
        Enum<?> WAITING = Enum.valueOf(CLASS, "WAITING");
        Enum<?> EXPANDING = Enum.valueOf(CLASS, "EXPANDING");
        Enum<?> RETRACTING = Enum.valueOf(CLASS, "RETRACTING");
        Enum<?> DUMPING = Enum.valueOf(CLASS, "DUMPING");
    }

    interface Mode {
        Class<StupidJavaCompiler> CLASS = stupidJavaCompiler_ClassForName("com.simibubi.create.content.contraptions.components.deployer.DeployerBlockEntity$Mode");
        //        Field FIELD = stupidJavaCompiler_getDeclaredField(DeployerBlockEntity.class, "mode");
        Enum<?> PUNCH = Enum.valueOf(CLASS, "PUNCH");
        Enum<?> USE = Enum.valueOf(CLASS, "USE");
    }
    @SuppressWarnings("unchecked")
    static <T> Class<T> stupidJavaCompiler_ClassForName(String className) {
        try {
            return (Class<T>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    static Field stupidJavaCompiler_getDeclaredField(Class<?> cls, String name) {
        try {
            return cls.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
    static void tickBeforeCheckSpeed(Object subject) {
        var deployer = (DeployerBlockEntity & InjectDeployerBlockEntity) subject;
//        deployer.setState(deployer.getState());
    }
    Enum<?> getState();
    void setState(Enum<?> name);
    Enum<?> getMode();
    void setMode(Enum<?> name);
}
