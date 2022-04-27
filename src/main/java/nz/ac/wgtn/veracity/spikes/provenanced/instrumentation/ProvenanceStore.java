package nz.ac.wgtn.veracity.spikes.provenanced.instrumentation;

import java.util.ArrayList;
import java.util.List;

public class ProvenanceStore {

    public static ThreadLocal<List<String>> methods = new ThreadLocal<>();

    public static void add(String method) {
        System.out.println("method added to provenance store: " + method);
        synchronized (methods) {
            if (methods.get() == null) {
                methods.set(new ArrayList<>());
            }
            methods.get().add(method);
        }
    }

    public static List<String> getAndReset() {
        List<String> methods2 = new ArrayList<>();
        synchronized (methods) {
            methods2.addAll(methods.get());
            methods.get().clear();
        }
        return methods2;
    }

    public static void reset() {
        synchronized (methods) {
            methods.get().clear();
        }
    }

}
