package root.model;

import java.util.ArrayList;

import root.Globals;
import root.util.Algorithims;
import root.util.Vector;

public class SwerveBase{
    protected SwerveModule fL, fR, bL, bR;

    private double heading;
    private double maxRotation;

    protected double width = Globals.SWERVE_WIDTH, 
                    height = Globals.SWERVE_HEIGHT;

    protected ArrayList<Double> coeffX = new ArrayList<>(),
                                coeffY = new ArrayList<>();

    protected double x = Globals.WIDTH / 2;
    protected double y = Globals.HEIGHT / 2;

    private double maxVelocity = 3;

    public enum ModuleLocation{
        FL, FR, BL, BR
    }

    public SwerveBase(double maxRot){
        fL = new SwerveModule(ModuleLocation.FL);
        fR = new SwerveModule(ModuleLocation.FR);
        bL = new SwerveModule(ModuleLocation.BL);
        bR = new SwerveModule(ModuleLocation.BR);

        this.maxRotation = maxRot;

        this.heading = 0;

        coeffX.add(0d);
        coeffY.add(0d);
    }

    public SwerveBase(){
        this(Math.toRadians(1));
    }

    public void drive(double forward, double strafe, double rot, boolean inverted){
        Vector[] vectors = Algorithims.returnSwerve(new Vector(new Vector.VectorRectangular(forward, strafe)), rot, heading);

        fL.setState(vectors[0]);
        fR.setState(vectors[1]);
        bL.setState(vectors[2]);
        bR.setState(vectors[3]);

        if (inverted) this.heading -= maxRotation * rot;
        else this.heading += maxRotation * rot;

        this.heading = Math.toRadians((Math.toDegrees(heading) % 360 + 360) % 360);

        coeffX.set(0, strafe);
        coeffY.set(0, forward);

        updateOdometry();
    }

    public void drive(double forward, double strafe, double rot){
        this.drive(forward, strafe, rot, false);
    }

    protected void updateOdometry(){
        // double tempX = 0;
        // for (int i = 1; i < coeffX.size(); i++){
        //     int factorial = 1;
        //     for (int j = 1; j <= i; j++){
        //         factorial *= j;
        //     }
        //     tempX += coeffX.get(i) * 
        // }
        updateOdometryX();
        updateOdometryY();
    }

    protected void updateOdometryX(){
        this.x += coeffX.get(0) * maxVelocity;
    }

    protected void updateOdometryY(){
        this.y -= coeffY.get(0) * maxVelocity;
    }

    public void setMaxRotation(double newRot){
        this.maxRotation = newRot;
    }

    public void setHeading(double newHeading){
        this.heading = newHeading;
    }

    public void setMaxVelocity(double velocity){
        this.maxVelocity = velocity;
    }

    public void resetHeading(){
        this.heading = 0;
    }

    public double getMaxRotation(){
        return this.maxRotation;
    }

    public double getHeading(){
        return this.heading;
    }

    public double getHeadingDegrees(){
        return Math.toDegrees(this.heading);
    }

    public String getFormatHeading(){
        return "Heading: " + SwerveBase.getFormatHeading(this.heading);
    }

    public String toString(){
        return "fl: " + fL.toString() + "\nfr: " + fR.toString() + "\nbl: " + bL.toString() + "\nbr: " + bR.toString();
    }

    public static String getFormatHeading(double heading){
        if (Double.compare(heading, 0) == 0){
            return "" + 0.0;
        }
        return String.format("%,.1f", Math.toDegrees(2 * Math.PI - heading));
    }
}
