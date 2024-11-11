import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WspanialyEksperymentator implements Eksperymentator {

    private KostkaDoGry kostka;
    private long czasEksperymentu;
    private List<Integer> sekwencja;
    private Set<Integer> oczka;

    @Override
    public void użyjKostki(KostkaDoGry kostka) {
        this.kostka = kostka;
    }

    @Override
    public void czasJednegoEksperymentu(long czasEksperymentu) {
        this.czasEksperymentu = czasEksperymentu;
    }

    @Override
    public Map<Integer, Double> szansaNaWyrzucenieOczek(int liczbaKostek) {

        Map<Integer, Double> wynik = new HashMap<>();
        int sumaOczek;
        int wszytskieRzuty = 0;
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < czasEksperymentu) {
            sumaOczek = 0;
            for (int i = 0; i < liczbaKostek; i++) {
                int wynikRzutu = kostka.rzut();
                sumaOczek += wynikRzutu;
            }
            wynik.put(sumaOczek, wynik.getOrDefault(sumaOczek, 0.0) + 1.0);
            wszytskieRzuty++;
        }
        for (Map.Entry<Integer, Double> entry : wynik.entrySet()) {
            int suma = entry.getKey();
            double liczbaWystapien = entry.getValue();
            double prawdopodobienstwo = liczbaWystapien / wszytskieRzuty;
            wynik.put(suma, prawdopodobienstwo);
        }

        return wynik;

    }

    @Override
    public double szansaNaWyrzucenieKolejno(List<Integer> sekwencja) {
        this.sekwencja = sekwencja;
        long start = System.currentTimeMillis();
        int sukcesy = 0;
        int wszystkieRzuty = 0;
        List<Integer> wyrzucone = new ArrayList<>();
        int rozmiar = sekwencja.size();
        while (System.currentTimeMillis() - start < czasEksperymentu) {
            wyrzucone.clear();
            for (int i = 0; i < rozmiar; i++) {
                int wynikRzutu = kostka.rzut();
                wyrzucone.add(wynikRzutu);
            }
            boolean rowne = true;
            for (int i = 0; i < rozmiar; i++) {
                if (!wyrzucone.get(i).equals(sekwencja.get(i))) {
                    rowne = false;
                    break;
                }
            }
            if (rowne) {
                sukcesy++;
            }

            wszystkieRzuty++;
        }
        return (double) sukcesy / wszystkieRzuty;
    }

    @Override
    public double szansaNaWyrzucenieWDowolnejKolejności(Set<Integer> oczka) {
        long start = System.currentTimeMillis();
        int sukcesy = 0;
        int wszystkieRzuty = 0;
        int rozmiar = oczka.size();
        this.oczka = oczka;
        List<Integer> wyrzucone = new ArrayList<>();
        List<Integer> oczkaList = new ArrayList<>(oczka);

        while (System.currentTimeMillis() - start < czasEksperymentu) {
            wyrzucone.clear();
            for (int i = 0; i < rozmiar; i++) {
                int wynikRzutu = kostka.rzut();
                wyrzucone.add(wynikRzutu);
            }
            Collections.sort(oczkaList);
            Collections.sort(wyrzucone);

            if (wyrzucone.equals(oczkaList)) {
                sukcesy++;
            }
            wszystkieRzuty++;
        }

        return (double) sukcesy / wszystkieRzuty;
    }

}
