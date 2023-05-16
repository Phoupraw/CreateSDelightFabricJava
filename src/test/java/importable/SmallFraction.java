package importable;

import java.util.Scanner;
public class SmallFraction {
    public static void main(String[] args) {
        var sc = new Scanner(System.in);
        System.out.println(gcd(sc.nextLong(), sc.nextLong()));
    }

    public static long gcd(long a, long b) {
        if (a < b) {
            long t = a;
            a = b;
            b = t;
        }
        while (b != 0) {
            long t = a;
            a = b;
            b = t % b;
        }
        return a;
    }

    public final long nu;
    public final long de;

    public SmallFraction(long nu, long de) {
        this.nu = nu;
        this.de = de;
    }

    public SmallFraction reduce() {
        long gcd = gcd(nu, de);
        int sign = Long.signum(nu) * Long.signum(de);
        return new SmallFraction(sign * Math.abs(nu) / gcd, Math.abs(de) / gcd);
    }

    public SmallFraction plus(SmallFraction fr) {
        return new SmallFraction(this.nu * fr.de + this.de * fr.nu, this.de * fr.de).reduce();
    }

    public SmallFraction unaryMinus() {
        return new SmallFraction(-this.nu, this.de);
    }

    public SmallFraction minus(SmallFraction fr) {
        return plus(fr.unaryMinus());
    }
}
