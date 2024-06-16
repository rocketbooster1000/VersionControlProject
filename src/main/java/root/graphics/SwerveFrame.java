package root.graphics;

import javax.swing.JFrame;

import root.Globals;

public class SwerveFrame extends JFrame{
    private static volatile SwerveFrame instance = null;
    public static final String VERSION = "Swerve Visualizer v0.3.0-beta1";

    private SwerveFrame(){
        super(VERSION);
        this.setSize(Globals.WIDTH, Globals.HEIGHT);
        this.setVisible(true);
    }

    public static SwerveFrame getInstance(){
        if (instance == null){
            synchronized(SwerveFrame.class){
                if (instance == null){
                    instance = new SwerveFrame();
                }
            }
        }
        return instance;
    }
}
