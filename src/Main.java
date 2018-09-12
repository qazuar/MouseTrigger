public class Main {

    public static void main(String[] args) {
        ColorTrigger colorTrigger = new ColorTrigger();
        KeyboardListener kbl = new KeyboardListener(colorTrigger);

        kbl.enable();

        Thread thread = new Thread(colorTrigger);
        thread.start();
    }

}