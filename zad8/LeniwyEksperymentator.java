import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class LeniwyEksperymentator implements LeniwyBadaczKostekDoGry {
    private ExecutorService executorService;
    private int ostatniIdentyfikator = 0;
    private Map<Integer, Future<Map<Integer, Integer>>> wynikiRzutow = new HashMap<>();
    private final Object lock = new Object();

    public synchronized int Identyfikatory() {
        return ostatniIdentyfikator++;
    }

    public void fabrykaWatkow(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public int kostkaDoZbadania(KostkaDoGry kostka, int liczbaRzutow) {
        int identyfikatorZadania = Identyfikatory();
        Future<Map<Integer, Integer>> future = executorService.submit(() -> {
            Map<Integer, Integer> rezultat = new HashMap<>();
            for (int i = 1; i <= liczbaRzutow; i++) {
                int wynikRzutu = kostka.rzut();
                rezultat.put(wynikRzutu, rezultat.getOrDefault(wynikRzutu, 0) + 1);
            }
            return rezultat;
        });

        synchronized (lock) {
            wynikiRzutow.put(identyfikatorZadania, future);
        }

        return identyfikatorZadania;
    }

    public boolean badanieKostkiZakonczono(int identyfikator) {
        synchronized (lock) {
            Future<Map<Integer, Integer>> future = wynikiRzutow.get(identyfikator);
            if (future == null)
                return false;
            return future.isDone();
        }
    }

    public Map<Integer, Integer> histogram(int identyfikator) {
        synchronized (lock) {
            try {
                Future<Map<Integer, Integer>> future = wynikiRzutow.get(identyfikator);
                if (future == null || !future.isDone())
                    return null;
                return future.get();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
