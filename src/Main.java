public class Main {

    public static void main(String[] args) {
        Trigger trigger = new Trigger();
        Thread thread = new Thread(trigger);
        thread.start();
    }

}
