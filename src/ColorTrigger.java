import java.awt.*;
import java.awt.event.InputEvent;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ColorTrigger implements Runnable, Serializable {

    private Boolean RUN_THREAD = true;
    private Boolean TRIGGER_ACTIVE = false;

    private Integer CYCLE_TIME = 2;

    private Robot robot;
    private Point point;

    private Color svdColor;
    private Color tmpColor;

    DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm:ss");

    public ColorTrigger() {
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            while (RUN_THREAD) {

                if (TRIGGER_ACTIVE) {
                    point = MouseInfo.getPointerInfo().getLocation();
                    tmpColor = robot.getPixelColor(point.x, point.y);

                    if (!tmpColor.equals(svdColor)) {
                        System.out.println("FIRE");
                        robot.mousePress(InputEvent.BUTTON1_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_MASK);
                        activate();
                    }
                }

                Thread.sleep(CYCLE_TIME);
            }

        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void activate() {
        if (!getStatus()) {
            point = MouseInfo.getPointerInfo().getLocation();
            svdColor = robot.getPixelColor(point.x, point.y);
            this.TRIGGER_ACTIVE = true;
            System.out.println(tf.format(LocalDateTime.now()) + ": Activated on color: [" + svdColor.getRed() + ", " + svdColor.getGreen() + ", " + svdColor.getBlue() + "]");
        } else {
            svdColor = null;
            this.TRIGGER_ACTIVE = false;
            System.out.println(tf.format(LocalDateTime.now()) + ": Deactivated");
        }
    }

    public boolean getStatus() {
        return this.TRIGGER_ACTIVE;
    }

    public void stop() {
        System.out.println("STOP COLORTRIGGER");
        this.RUN_THREAD = false;
    }
}
