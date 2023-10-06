package phoupraw.mcmod.createsdelight.misc;

import org.joml.Math;
import org.joml.*;
import sun.misc.Unsafe;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
@Deprecated
public interface DefaultVector3dc extends Vector3dc {
    static Unsafe unsafe() {
        return Unsafe.getUnsafe();
    }
    @Override
    default ByteBuffer get(ByteBuffer buffer) {
        return get(buffer.position(), buffer);
    }
    @Override
    default ByteBuffer get(int index, ByteBuffer buffer) {
        return buffer
          .putDouble(index, x())
          .putDouble(index + 8, y())
          .putDouble(index + 16, z());
    }
    @Override
    default DoubleBuffer get(DoubleBuffer buffer) {
        return get(buffer.position(), buffer);
    }
    @Override
    default DoubleBuffer get(int index, DoubleBuffer buffer) {
        return buffer
          .put(index, x())
          .put(index + 1, y())
          .put(index + 2, z());
    }
    @Override
    default FloatBuffer get(FloatBuffer buffer) {
        return get(buffer.position(), buffer);
    }
    @Override
    default FloatBuffer get(int index, FloatBuffer buffer) {
        return buffer
          .put(index, (float) x())
          .put(index + 1, (float) y())
          .put(index + 2, (float) z());
    }
    @Override
    default ByteBuffer getf(ByteBuffer buffer) {
        return getf(buffer.position(), buffer);
    }
    @Override
    default ByteBuffer getf(int index, ByteBuffer buffer) {
        return buffer
          .putFloat(index, (float) x())
          .putFloat(index + 4, (float) y())
          .putFloat(index + 8, (float) z());
    }
    @Override
    default Vector3dc getToAddress(long address) {
        if (Options.NO_UNSAFE) {
            throw new UnsupportedOperationException("Not supported when using joml.nounsafe");
        }
        unsafe().putDouble(null, address, x());
        unsafe().putDouble(null, address + 8, y());
        unsafe().putDouble(null, address + 16, z());
        return this;
    }
    @Override
    default Vector3d sub(Vector3dc v, Vector3d dest) {
        return sub(v.x(), v.y(), v.z(), dest);
    }
    @Override
    default Vector3d sub(Vector3fc v, Vector3d dest) {
        return sub(v.x(), v.y(), v.z(), dest);
    }
    @Override
    default Vector3d sub(double x, double y, double z, Vector3d dest) {
        dest.x = x() - x;
        dest.y = y() - y;
        dest.z = z() - z;
        return dest;
    }
    @Override
    default Vector3d add(Vector3dc v, Vector3d dest) {
        return add(v.x(), v.y(), v.z(), dest);
    }
    @Override
    default Vector3d add(Vector3fc v, Vector3d dest) {
        return add(v.x(), v.y(), v.z(), dest);
    }
    @Override
    default Vector3d add(double x, double y, double z, Vector3d dest) {
        dest.x = x() + x;
        dest.y = y() + y;
        dest.z = z() + z;
        return dest;
    }
    @Override
    default Vector3d fma(Vector3dc a, Vector3dc b, Vector3d dest) {
        dest.x = org.joml.Math.fma(a.x(), b.x(), x());
        dest.y = org.joml.Math.fma(a.y(), b.y(), y());
        dest.z = Math.fma(a.z(), b.z(), z());
        return dest;
    }
    @Override
    default Vector3d fma(double a, Vector3dc b, Vector3d dest) {
        dest.x = Math.fma(a, b.x(), x());
        dest.y = Math.fma(a, b.y(), y());
        dest.z = Math.fma(a, b.z(), z());
        return dest;
    }
    @Override
    default Vector3d fma(Vector3dc a, Vector3fc b, Vector3d dest) {
        dest.x = Math.fma(a.x(), b.x(), x());
        dest.y = Math.fma(a.y(), b.y(), y());
        dest.z = Math.fma(a.z(), b.z(), z());
        return dest;
    }
    @Override
    default Vector3d fma(double a, Vector3fc b, Vector3d dest) {
        dest.x = Math.fma(a, b.x(), x());
        dest.y = Math.fma(a, b.y(), y());
        dest.z = Math.fma(a, b.z(), z());
        return dest;
    }
    @Override
    default Vector3d fma(Vector3fc a, Vector3fc b, Vector3d dest) {
        dest.x = Math.fma(a.x(), b.x(), x());
        dest.y = Math.fma(a.y(), b.y(), y());
        dest.z = Math.fma(a.z(), b.z(), z());
        return dest;
    }
    @Override
    default Vector3d mulAdd(Vector3dc a, Vector3dc b, Vector3d dest) {
        dest.x = Math.fma(x(), a.x(), b.x());
        dest.y = Math.fma(y(), a.y(), b.y());
        dest.z = Math.fma(z(), a.z(), b.z());
        return dest;
    }
    @Override
    default Vector3d mulAdd(double a, Vector3dc b, Vector3d dest) {
        dest.x = Math.fma(x(), a, b.x());
        dest.y = Math.fma(y(), a, b.y());
        dest.z = Math.fma(z(), a, b.z());
        return dest;
    }
    @Override
    default Vector3d mulAdd(Vector3fc a, Vector3dc b, Vector3d dest) {
        dest.x = Math.fma(x(), a.x(), b.x());
        dest.y = Math.fma(y(), a.y(), b.y());
        dest.z = Math.fma(z(), a.z(), b.z());
        return dest;
    }
    @Override
    default Vector3d mul(Vector3fc v, Vector3d dest) {
        dest.x = x() * v.x();
        dest.y = y() * v.y();
        dest.z = z() * v.z();
        return dest;
    }
    @Override
    default Vector3d mul(Vector3dc v, Vector3d dest) {
        dest.x = x() * v.x();
        dest.y = y() * v.y();
        dest.z = z() * v.z();
        return dest;
    }
    @Override
    default Vector3d div(Vector3fc v, Vector3d dest) {
        dest.x = x() / v.x();
        dest.y = y() / v.y();
        dest.z = z() / v.z();
        return dest;
    }
    @Override
    default Vector3d div(Vector3dc v, Vector3d dest) {
        dest.x = x() / v.x();
        dest.y = y() / v.y();
        dest.z = z() / v.z();
        return dest;
    }
    //太逆天了，怎么这么多莫名其妙的方法
}
