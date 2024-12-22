import java.util.Random;

public class App {
    public static void main(String[] args) throws Exception {
        for (int index = 0; index < 6; index++) {
            
            Random random = new Random();
                int numero = random.nextInt(36);
            System.out.println(numero);
        }
    }
}
