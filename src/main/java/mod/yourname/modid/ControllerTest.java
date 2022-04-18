package mod.yourname.modid;

import net.java.games.input.*;

import java.util.Arrays;

public class ControllerTest {
    public static void main(String[] args) throws InterruptedException {
        Event event = new Event();
        float oldValue = 0;
        /* Get the available controllers */
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        for (Controller controller : controllers) {
            System.out.println(controller.getName());
            /* Remember to poll each one */

            /* Get the controllers event queue */
            EventQueue queue = controller.getEventQueue();
            System.out.println(Arrays.toString(controller.getRumblers()));
            /* For each object in the queue */
            while (true) {
                controller.poll();
                while (queue.getNextEvent(event)) {
                    /* Get event component */
                    Component comp = event.getComponent();
                    if (comp.getIdentifier() == Component.Identifier.Axis.X) {
                        if (oldValue != comp.getPollData()) {
                            oldValue = comp.getPollData();
                            System.out.println(comp.getIdentifier() + ": " + comp.getPollData());
                        }
                    }
                }
                Thread.sleep(500);
            }
        }
    }
}
