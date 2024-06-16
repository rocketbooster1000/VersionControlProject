package root;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;

public final class Globals {
    public static int WIDTH = 600;
    public static int HEIGHT = 600;

    public static final int CONTROL_WIDTH = 300;
    public static final int CONTROL_HEIGHT = 50;
    
    public static double REQUESTED_FORWARD = 0;
    public static double REQUESTED_STRAFE = 0;
    public static double REQUESTED_ROTATION = 0;

    public static boolean RESET_REQUESTED = false;

    public static boolean FIELD_CENTRIC = true;
    public static boolean FIELD_ORIENTED = true;

    public static boolean HEADING_CHANGING = false;
    public static boolean CHANGE_REQUESTED = false;

    public static double MAX_ROTATION_SPEED = Math.PI / 180;
    public static double MAX_VELOCITY = 3;

    public static double CURRENT_HEADING = 0;
    public static double BUFFER_HEADING = 0;
    
    public static final int DRAW_VECTOR_LENGTH = 30;
    public static final int DRAW_ARROW_LENGTH = 10;

    public static final int SWERVE_WIDTH = 100;
    public static final int SWERVE_HEIGHT = 100;

    public static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
    public static final int F = JComponent.WHEN_FOCUSED;

    public static final class KeyBindingActions{
        public static final Action RESET_HEADING_ACTION = new AbstractAction() {
            public void actionPerformed(ActionEvent e){
                Globals.RESET_REQUESTED = true;
            }
        };
    
        public static final Action FORWARD_ACTION = new AbstractAction() {
            public void actionPerformed(ActionEvent e){
                Globals.REQUESTED_FORWARD = 1;
            }
        };
    
        public static final Action BACKWARDS_ACTION = new AbstractAction() {
            public void actionPerformed(ActionEvent e){
                Globals.REQUESTED_FORWARD = -1;
            }
        };
    
        public static final Action STRAFE_LEFT_ACTION = new AbstractAction() {
            public void actionPerformed(ActionEvent e){
                Globals.REQUESTED_STRAFE = -1;
            }
        };
    
        public static final Action STRAFE_RIGHT_ACTION = new AbstractAction() {
            public void actionPerformed(ActionEvent e){
                Globals.REQUESTED_STRAFE = 1;
            }
        };
        
        public static final Action TURN_LEFT_ACTION = new AbstractAction() {
            public void actionPerformed(ActionEvent e){
                Globals.REQUESTED_ROTATION = -1;
            }
        };
        public static final Action TURN_RIGHT_ACTION = new AbstractAction() {
            public void actionPerformed(ActionEvent e){
                Globals.REQUESTED_ROTATION = 1;
            }
        };
    
        public static final Action RELEASE_FORWARD_ACTION = new AbstractAction() {
            public void actionPerformed(ActionEvent e){
                Globals.REQUESTED_FORWARD = 0;
            }
        };
    
        public static final Action RELEASE_STRAFE_ACTION = new AbstractAction() {
            public void actionPerformed(ActionEvent e){
                Globals.REQUESTED_STRAFE = 0;
            }
        };
    
        public static final Action RELEASE_ROTATION_ACTION = new AbstractAction() {
            public void actionPerformed(ActionEvent e){
                Globals.REQUESTED_ROTATION = 0;
            }
        };
    
        public static final Action TOGGLE_HEADING_ACTION = new AbstractAction() {
            public void actionPerformed(ActionEvent e){
                Globals.FIELD_CENTRIC = !Globals.FIELD_CENTRIC;
            }
        };

        public static final Action EMPTY_ACTION = new AbstractAction() {
            public void actionPerformed(ActionEvent e){}
        };
    }

    public static final class KeyBindingStrings{
        public static final String REQUEST_FORWARD = "REQUEST_FORWARD";
        public static final String REQUEST_BACK = "REQUEST_BACK";
        public static final String REQUEST_STRAFE_LEFT = "REQUEST_STRAFE_LEFT";
        public static final String REQUEST_STRAFE_RIGHT = "REQUEST_STRAFE_RIGHT";
        public static final String REQUEST_ROTATION_LEFT = "REQUEST_ROTATION_LEFT";
        public static final String REQUEST_ROTATION_RIGHT = "REQUEST_ROTATION_RIGHT";
        public static final String REQUEST_TOGGLE_HEADING = "REQUEST_TOGGLE_HEADING";
        public static final String REQUEST_RESET_HEADING = "REQUEST_RESET_HEADING";
        public static final String REQUEST_RELEASE_FORWARD = "REQUEST_RELEASE_FORWARD";
        public static final String REQUEST_RELEASE_STRAFE = "REQUEST_RELEASE_STRAFE";
        public static final String REQUEST_RELEASE_ROTATION = "REQUEST_RELEASE_ROTATION";

        public static final String REQUEST_EMPTY_ACTION = "REQUEST_EMPTY_ACTION";
    }

}
