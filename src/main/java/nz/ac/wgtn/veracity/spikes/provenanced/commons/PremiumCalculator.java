package nz.ac.wgtn.veracity.spikes.provenanced.commons;


public class PremiumCalculator {

    public static int calculate (Client client) {
        return isYoungDriver(client) ? 300 : 200 ;
    }

    private static boolean isYoungDriver(Client client) {
        return client.getAge() <= 21;
    }
}
