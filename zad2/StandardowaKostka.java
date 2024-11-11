
import java.util.Random;

public class StandardowaKostka implements KostkaDoGry {
    private Random random;

    public StandardowaKostka() {
        // Inicjalizacja generatora liczb losowych
        random = new Random();
    }

    @Override
    public int rzut() {
        // Generowanie losowej liczby od 1 do 6
        return random.nextInt(6) + 1;
    }

    public static void main(String[] args) {
        KostkaDoGry kostka = new StandardowaKostka();

        // Wykonanie pojedynczego rzutu kostką
        int wynik = kostka.rzut();
        System.out.println("Wynik rzutu kostką: " + wynik);
    }
}
