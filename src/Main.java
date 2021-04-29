public class Main {

    static boolean isRunning = true;
    static Controller c = new Controller();

    public static void main(String[] args) {

        while (isRunning) {
            new Controller();
        }
    }
}
