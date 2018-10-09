import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        TableImpl tab = new TableImpl();



        System.out.println("1 - initialization From Terminal");
        System.out.println("2 - encription to standard output");
        System.out.println("3 - decription to standard output");
        System.out.println("4 - initialization From File");
        System.out.println("5 - To File");
        while (true) {
            Scanner in = new Scanner(System.in);
            int obrot = in.nextInt();
            switch (obrot) {
                case 1: {
                    tab.initFromTerminal();

                    break;
                }

                case 2: {
                    System.out.println("szyfrogram=" + tab.encript());
                    break;
                }

                case 3: {
                    System.out.println("wiadomosc=" + tab.decript());
                    break;
                }
                case 4: {
                    tab.initFromFile();
                    break;
                }
                case 5: {
                    tab.toFile();
                    break;
                }
            }

        }

    }
}
