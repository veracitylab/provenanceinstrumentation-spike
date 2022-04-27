package nz.ac.wgtn.veracity.spikes.provenanced.provenanced;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Simple provenance store.
 * Careful -- has a memory leak, for demo purposes only.
 * @author jens dietrich
 */
public class ProvenanceStore {

    private static AtomicInteger counter = new AtomicInteger();
    private static Map<Integer,List<String>> store = new ConcurrentHashMap<>();

    public static int store(List<String> provenanceData) {
        int nextId = counter.incrementAndGet();
        store.put(nextId,provenanceData);
        return nextId;
    }

    public static List<String> get(int id) {
        return store.get(id);
    }

    public static List<String> remove(int id) {
        return store.remove(id);
    }



}
