package main.util;

public class Globals {
    public static int WIDTH = 500;
    public static int HEIGHT = 500;
    public static final int SWERVE_WIDTH = 100;
    public static final int SWERVE_HEIGHT = 100;

    public static double REQUESTED_FORWARD = 0;
    public static double REQUESTED_STRAFE = 0;
    public static double REQUESTED_ROTATION = 0;
    public static boolean RESET_REQUESTED = false;

    public static final double MAX_ROTATION_SPEED = Math.PI / 180;

    public static final int DRAW_VECTOR_LENGTH = 30;
    public static final int DRAW_ARROW_LENGTH = 10;
}
