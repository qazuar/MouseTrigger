import java.awt.*;
import java.awt.event.InputEvent;
import java.io.Serializable;

public class Trigger implements Runnable, Serializable {

    private Boolean RUN_THREAD = true;

    private Integer CYCLE_TIME = 2;
    private Integer WAIT_TIME = 2000;

    private Robot robot;

    private Point svdPoint;
    private Point tmpPoint;

    private Color svdColor;
    private Color tmpColor;

    @Override
    public void run() {
        try {
            int count = 0;
            long start = System.currentTimeMillis();

            robot = new Robot();
            svdPoint = MouseInfo.getPointerInfo().getLocation();
            svdColor = robot.getPixelColor(svdPoint.x, svdPoint.y);

            while (RUN_THREAD) {
                tmpPoint = MouseInfo.getPointerInfo().getLocation();

                if (tmpPoint.equals(svdPoint)) {
                    tmpColor = robot.getPixelColor(tmpPoint.x, tmpPoint.y);

                    if (!tmpColor.equals(svdColor)) {
                        robot.mousePress(InputEvent.BUTTON1_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_MASK);
                        //System.out.println("BUTTON1");
                        svdColor = tmpColor;
                        Thread.sleep(WAIT_TIME);
                    }
                } else {
                    svdPoint = tmpPoint;
                    svdColor = robot.getPixelColor(tmpPoint.x, tmpPoint.y);
                }

                count++;

                // This is just to determine how efficiency
                if (count == 300) {
                    System.out.println("300 checks done in " + (System.currentTimeMillis() - start) + "ms");
                }

                Thread.sleep(CYCLE_TIME);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void stop() {
        System.out.println("STOP");
        this.RUN_THREAD = false;
    }
}
