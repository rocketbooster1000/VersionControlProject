package main.model;

import main.model.SwerveBase.ModuleLocation;
import main.util.Vector;

public class SwerveModule{
    public double power, rotate;
    public ModuleLocation location;

    public SwerveModule(ModuleLocation location){
        this.location = location;
    }

    public void setState(Vector vector){
        this.power = vector.r;
        this.rotate = vector.theta;
    }

    public String toString(){
        return "Power: " + power + " | Angle: " + Math.toDegrees(rotate);
    }
}
