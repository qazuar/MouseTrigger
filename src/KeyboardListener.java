import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KeyboardListener implements NativeKeyListener {

    private ColorTrigger colorTrigger;

    public KeyboardListener(ColorTrigger colorTrigger) {
        this.colorTrigger = colorTrigger;
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        //System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        String keyValue = NativeKeyEvent.getKeyText(e.getKeyCode());

        if (keyValue.equalsIgnoreCase("F")) {
            colorTrigger.activate();
        }

        /*
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
                try {
                    GlobalScreen.unregisterNativeHook();
                } catch (NativeHookException ex) {
                    ex.printStackTrace();
                }
            }
        }
        */

    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        //System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        //System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }

    public void enable() {
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new KeyboardListener(this.colorTrigger));

        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        // Don't forget to disable the parent handlers.
        logger.setUseParentHandlers(false);
    }

    public void disable() {
        try {
            GlobalScreen.unregisterNativeHook();
            this.colorTrigger.stop();
        } catch (NativeHookException ex) {
            ex.printStackTrace();
        }
    }
}