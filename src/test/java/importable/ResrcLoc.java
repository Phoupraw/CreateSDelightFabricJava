package importable;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;
public final class ResrcLoc {
    @ApiStatus.Internal
    public static final Interner<ResrcLoc> INTERNER = Interners.newStrongInterner();
    public static final Pattern PATTERN = Pattern.compile("([a-z0-9_]+):([a-z0-9_]+)");

    @Contract(pure = true)
    public static @NotNull ResrcLoc parseOrThrow(@NotNull String resrcLoc) throws IllegalArgumentException {
        var result = parseOrNull(resrcLoc);
        if (result != null) return result;
        throw new IllegalArgumentException(resrcLoc);
    }

    @Contract(pure = true)
    public static @Nullable ResrcLoc parseOrNull(@NotNull String resrcLoc) {
        var matcher = PATTERN.matcher(resrcLoc);
        return matcher.matches() ? of(matcher.group(1), matcher.group(2)) : null;
    }

    @Contract(pure = true)
    public static @NotNull ResrcLoc parseUnsafe(@NotNull String resrcLoc) throws StringIndexOutOfBoundsException {
        int index = resrcLoc.indexOf(':');
        return of(resrcLoc.substring(0, index), resrcLoc.substring(index + 1));
    }

    @Contract(pure = true)
    public static @NotNull ResrcLoc of(@NotNull String namespace, @NotNull String path) {
        return INTERNER.intern(new ResrcLoc(namespace.intern(), path.intern()));
    }

    private final String namespace;
    private final String path;

    @Contract(pure = true)
    @ApiStatus.Internal
    public ResrcLoc(String namespace, String path) {
        this.namespace = namespace;
        this.path = path;
    }

    @Contract(pure = true)
    public @NotNull String getNamespace() {
        return namespace;
    }

    @Contract(pure = true)
    public @NotNull String getPath() {
        return path;
    }

    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof ResrcLoc that)) return false;
        return namespace.equals(that.namespace) && path.equals(that.path);
    }

    @Contract(pure = true)
    @Override
    public int hashCode() {
        return 31 * (31 + namespace.hashCode()) + path.hashCode();
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return namespace + ":" + path;
    }

}
