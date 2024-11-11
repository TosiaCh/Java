
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Tworzenie instancji StandardowaKostka
        KostkaDoGry kostka = new StandardowaKostka();

        // Tworzenie instancji WspanialyEksperymentator
        WspanialyEksperymentator eksperymentator = new WspanialyEksperymentator();

        // Ustawienie kostki w eksperymentatorze
        eksperymentator.użyjKostki(kostka);

        // Ustawienie czasu eksperymentu (np. 10000 milisekund, czyli 10 sekund)
        long czasEksperymentu = 10000;
        eksperymentator.czasJednegoEksperymentu(czasEksperymentu);

        // Przykład 1: Obliczanie szansy na wyrzucenie określonej sekwencji rzutów
        List<Integer> sekwencja = new ArrayList<>();
        sekwencja.add(1);
        sekwencja.add(2);
        sekwencja.add(3);
        double szansa1 = eksperymentator.szansaNaWyrzucenieKolejno(sekwencja);
        System.out.println("Szansa na wyrzucenie sekwencji " + sekwencja + " kolejno: " + szansa1);

        // Przykład 2: Obliczanie szansy na wyrzucenie oczek w dowolnej kolejności
        Map<Integer, Double> wynik2 = eksperymentator.szansaNaWyrzucenieOczek(2);
        System.out.println("Szansa na wyrzucenie oczek w dowolnej kolejności: " + wynik2);

        // Przykład 3: Obliczanie szansy na wyrzucenie określonej kombinacji oczek
        List<Integer> kombinacja = new ArrayList<>();
        kombinacja.add(1);
        kombinacja.add(2);
        kombinacja.add(3);
        double szansa3 = eksperymentator.szansaNaWyrzucenieKolejno(kombinacja);
        System.out.println("Szansa na wyrzucenie kombinacji " + kombinacja + " kolejno: " + szansa3);
    }
}
