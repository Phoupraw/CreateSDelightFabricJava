package phoupraw.mcmod.createsdelight.exp;

import com.google.gson.*;
import net.minecraft.nbt.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
//TODO 转移到common
public final class JsonNbtConvertions {
    @Contract(pure = true)
    public static @NotNull JsonElement convert(@Nullable NbtElement nbt) {
        if (nbt == null) return JsonNull.INSTANCE;
        if (nbt instanceof AbstractNbtNumber number) return convert(number);
        if (nbt instanceof NbtString string) return convert(string);
        if (nbt instanceof AbstractNbtList<?> list) return convert(list);
        if (nbt instanceof NbtCompound compound) return convert(compound);
        throw new IllegalArgumentException(nbt.toString());
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull JsonPrimitive convert(@NotNull AbstractNbtNumber number) {
        return new JsonPrimitive(number.numberValue());
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull JsonPrimitive convert(@NotNull NbtString string) {
        return new JsonPrimitive(string.asString());
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull JsonArray convert(@NotNull AbstractNbtList<?> list) {
        var array = new JsonArray(list.size());
        for (NbtElement nbtElement : list) array.add(convert(nbtElement));
        return array;
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull JsonObject convert(@NotNull NbtCompound compound) {
        var object = new JsonObject();
        for (String key : compound.getKeys()) object.add(key, convert(compound.get(key)));
        return object;
    }

    @Contract(pure = true)
    public static @Nullable NbtElement convert(@NotNull JsonElement json) {
        if (json instanceof JsonNull) return null;
        if (json instanceof JsonPrimitive primitive) return convert(primitive);
        if (json instanceof JsonArray array) return convert(array);
        if (json instanceof JsonObject object) return convert(object);
        throw new IllegalArgumentException(json.toString());
    }

    @Contract(pure = true)
    public static @NotNull NbtElement convert(@NotNull JsonPrimitive primitive) {
        if (primitive.isBoolean()) return NbtByte.of(primitive.getAsBoolean());
        if (primitive.isNumber()) return NbtDouble.of(primitive.getAsDouble());
        return NbtString.of(primitive.getAsString());
    }

    @Contract(pure = true, value = "_->new")
    public static @NotNull AbstractNbtList<?> convert(@NotNull JsonArray array) {
        var list = new NbtList();
        for (JsonElement element : array) list.add(convert(element));
        return list;
    }

    @Contract(pure = true, value = "_->new")
    public static @NotNull NbtCompound convert(@NotNull JsonObject object) {
        var compound = new NbtCompound();
        for (var entry : object.entrySet()) compound.put(entry.getKey(), convert(entry.getValue()));
        return compound;
    }

    public static final Object INACCESSIBLE = new Object();

    public static @NotNull Map<Field, @Nullable Object> fieldsValueOf(@NotNull Object o) {
        return fieldsValueOf(o, o.getClass());
    }

    public static @NotNull Map<Field, @Nullable Object> fieldsValueOf(@NotNull Object o, @NotNull Class<?> cls) {
        Map<Field, Object> map = new HashMap<>();
        if (cls.getSuperclass() != null) {
            map.putAll(fieldsValueOf(o, cls.getSuperclass()));
        }
        for (Field field : cls.getDeclaredFields()) {
            if (field.trySetAccessible()) {
                try {
                    map.put(field, field.get(o));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } else {
                map.put(field, INACCESSIBLE);
            }
        }
        return map;
    }

    private JsonNbtConvertions() {}
}
