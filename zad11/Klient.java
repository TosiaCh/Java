import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;

public class Klient implements NetConnection {
    public String host = "172.30.24.12";
    public int port = 9090;
    public String authenticPassword;

    public void password(String password) {
        this.authenticPassword = password;
    }

    public void connect(String host, int port) {
        try (
                Socket socket = new Socket(host, port);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {

            out.println("Program");

            for (int i = 0; i < 6; i++) {
                String line1 = in.readLine();
                System.out.println(line1);
            }

            BigInteger sum = BigInteger.ZERO;

            while (true) {
                String line = in.readLine();
                System.out.println(line);
                if (!line.equals("EOD")) {
                    BigInteger liczby = new BigInteger(line);
                    sum = sum.add(liczby);
                } else {
                    break;
                }

            }
            sum = sum.add(new BigInteger(authenticPassword));
            String cos = in.readLine();
            String kolejnaLinia = in.readLine();
            String[] elementy = kolejnaLinia.split(" ");
            String wynik = elementy[2];

            String wynikSumy = sum.toString();
            System.out.println(wynikSumy);
            System.out.println("dodaj do sumy haslo " + cos);
            System.out.println("mnie wszylo" + kolejnaLinia);
            System.out.println("wynik komputera" + wynik);
            if (wynikSumy.equals(wynik)) {
                out.println(sum);
                System.out.println("ok");
            } else {
                out.println(NetConnection.ODPOWIEDZ_DLA_OSZUSTA);
                System.out.println("Figa");
            }
            String osatnie = in.readLine();
            System.out.println(osatnie);
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
