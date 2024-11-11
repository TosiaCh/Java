import java.util.Map;
import java.util.HashMap;

public class WatkowyEksperymentator implements BadaczKostekDoGry {
    private int limit;
    private ThreadFactory watki;
    private int ostatniIdentyfikator = 0;
    private Map<Integer, Map<Integer, Integer>> wynikiRzutow = new HashMap<>();
    private final Object lock = new Object();
    private Map<Integer, Integer> watekZamkniety = new HashMap<>();
    int otwarteWatki = 0;

    public synchronized int Identyfikatory() {
        return ostatniIdentyfikator++;
    }

    public void dozwolonaLiczbaDzialajacychWatkow(int limitWatkow) {
        this.limit = limitWatkow;
    }

    public void fabrykaWatkow(ThreadFactory fabryka) {
        this.watki = fabryka;
    }

    public int kostkaDoZbadania(KostkaDoGry kostka, int liczbaRzutow) {
        int identyfikatorZadania = Identyfikatory();
        Thread nowyWatek = watki.getThread(() -> {
            try {
                KostkaDoGry Gra = kostka;
                int rzuty = liczbaRzutow;
                synchronized (lock) {
                    while (otwarteWatki >= limit) {
                        lock.wait();
                    }
                    otwarteWatki++;
                    watekZamkniety.put(identyfikatorZadania, 0);
                    lock.notifyAll();
                }
                Map<Integer, Integer> rezultat = new HashMap<>();
                for (int i = 1; i <= rzuty; i++) {
                    int wynikRzutu = Gra.rzut();
                    rezultat.put(wynikRzutu, rezultat.getOrDefault(wynikRzutu, 0) + 1);
                }
                synchronized (lock) {
                    wynikiRzutow.put(identyfikatorZadania, rezultat);
                    watekZamkniety.put(identyfikatorZadania, 1);
                    otwarteWatki--;
                    lock.notifyAll();
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }

        });

        Thread watek = watki.getThread(nowyWatek);
        watek.start();
        return identyfikatorZadania;

    }

    public boolean badanieKostkiZakonczono(int identyfikator) {
        return watekZamkniety.getOrDefault(identyfikator, 0) == 1;
    }

    public Map<Integer, Integer> histogram(int identyfikator) {
        return wynikiRzutow.get(identyfikator);

    }

}
