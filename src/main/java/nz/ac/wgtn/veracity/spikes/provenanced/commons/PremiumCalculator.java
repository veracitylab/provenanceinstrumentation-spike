package nz.ac.wgtn.veracity.spikes.provenanced.commons;


public class PremiumCalculator {

    public static int calculate (int age) {
        return isYoungDriver(age) ? 300 : 200 ;
    }

    private static boolean isYoungDriver(int age) {
        return age <= 21;
    }
}
