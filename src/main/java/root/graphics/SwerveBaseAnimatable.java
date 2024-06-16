package root.graphics;

import java.awt.Color;
import java.awt.Graphics;

import root.Globals;
import root.model.SwerveBase;
import root.util.*;
import root.util.Vector.*;


public class SwerveBaseAnimatable extends SwerveBase implements Animatable{
    private static volatile Animatable instance = null;
    private SwerveModuelWrapperAnimatable wrapperFl, wrapperFR, wrapperBL, wrapperBR;

    private Vector[] modulePositionVectors = new Vector[4];

    public SwerveBaseAnimatable(){
        super();
        this.wrapperFl = new SwerveModuelWrapperAnimatable(this.fL);
        this.wrapperFR = new SwerveModuelWrapperAnimatable(this.fR);
        this.wrapperBL = new SwerveModuelWrapperAnimatable(this.bL);
        this.wrapperBR = new SwerveModuelWrapperAnimatable(this.bR);

    }

    @Override
    public void drive(double forward, double strafe, double rot){
        super.drive(forward, strafe, rot, false);
    }

    @Override
    public void draw(Graphics g){
        g.setColor(Color.RED);
        if (Globals.FIELD_ORIENTED){
            this.drawFieldOriented(g);
        } else {
            this.drawRobotOriented(g);
        }
    }

    @Override
    public void update(){
        this.setMaxRotation(Globals.MAX_ROTATION_SPEED);
        this.setMaxVelocity(Globals.MAX_VELOCITY);

        if (Globals.CHANGE_REQUESTED){
            Globals.CURRENT_HEADING = Globals.BUFFER_HEADING;
            this.setHeading(Globals.CURRENT_HEADING);
            Globals.CHANGE_REQUESTED = false;
            Globals.HEADING_CHANGING = false;
        }
        
        this.drive(Globals.REQUESTED_FORWARD, Globals.REQUESTED_STRAFE, Globals.REQUESTED_ROTATION, true);
    
        if (Globals.RESET_REQUESTED){
            resetHeading();
            Globals.RESET_REQUESTED = false;
        }

        if (Globals.FIELD_CENTRIC) Globals.CURRENT_HEADING = this.getHeading();
        else Globals.CURRENT_HEADING = 0;
    }

    @Override
    protected void updateOdometry(){
        if (!fightingWallX()) updateOdometryX();
        if (!fightingWallY()) updateOdometryY();
    }

    private boolean withinFrame(){
        for (Vector v : this.modulePositionVectors){
            if (Math.round(v.x) + this.x <= 0 || Math.round(v.x) + this.x >= Globals.WIDTH
                || Math.round(v.y) + this.y <= 0 || Math.round(v.y) + this.y >= Globals.HEIGHT)
                return false;
        }
        return true;
    }

    private boolean fightingWallX(){
        for (Vector v : this.modulePositionVectors){
            if (Math.round(v.x) + this.x <= 0 && coeffX.get(0) < 0) return true;
            if (Math.round(v.x) + this.x >= Globals.WIDTH && coeffX.get(0) > 0) return true;
        }
        return false;
    }
    
    private boolean fightingWallY(){
        for (Vector v : this.modulePositionVectors){
            if (Math.round(v.y) + this.y <= 0 && coeffY.get(0) > 0) return true;
            if (Math.round(v.y) + this.y >= Globals.HEIGHT && coeffY.get(0) < 0) return true;
        }
        return false;
    }

    private void drawFieldOriented(Graphics g){
        Vector lFLoc = new Vector(new VectorRectangular(-Globals.SWERVE_WIDTH / 2, -Globals.SWERVE_HEIGHT / 2));
        Vector rFLoc = new Vector(new VectorRectangular(Globals.SWERVE_WIDTH / 2, -Globals.SWERVE_HEIGHT / 2));
        Vector lBLoc = new Vector(new VectorRectangular(-Globals.SWERVE_WIDTH / 2, Globals.SWERVE_HEIGHT / 2));
        Vector rBLoc = new Vector(new VectorRectangular(Globals.SWERVE_WIDTH / 2, Globals.SWERVE_HEIGHT / 2));
        
        lFLoc = lFLoc.rotate(2 * Math.PI - this.getHeading());
        rFLoc = rFLoc.rotate(2 * Math.PI - this.getHeading());
        lBLoc = lBLoc.rotate(2 * Math.PI - this.getHeading());
        rBLoc = rBLoc.rotate(2 * Math.PI - this.getHeading());

        modulePositionVectors[0] = lFLoc;
        modulePositionVectors[1] = rFLoc;
        modulePositionVectors[2] = lBLoc;
        modulePositionVectors[3] = rFLoc;
        
        g.setColor(Color.GREEN);
        g.drawLine((int)Math.round(lFLoc.x) + (int)(this.x), (int)Math.round(lFLoc.y) + (int)(this.y), (int)Math.round(rFLoc.x + (int)(this.x)), (int)Math.round(rFLoc.y) + (int)(this.y));

        g.setColor(Color.RED);
        g.drawLine((int)Math.round(rFLoc.x) + (int)(this.x), (int)Math.round(rFLoc.y) + (int)(this.y), (int)Math.round(rBLoc.x) + (int)(this.x), (int)Math.round(rBLoc.y) + (int)(this.y));
        g.drawLine((int)Math.round(rBLoc.x) + (int)(this.x), (int)Math.round(rBLoc.y) + (int)(this.y), (int)Math.round(lBLoc.x) + (int)(this.x), (int)Math.round(lBLoc.y) + (int)(this.y));
        g.drawLine((int)Math.round(lBLoc.x) + (int)(this.x), (int)Math.round(lBLoc.y) + (int)(this.y), (int)Math.round(lFLoc.x) + (int)(this.x), (int)Math.round(lFLoc.y) + (int)(this.y));

        this.wrapperFl.drawDynamic(g, (int)Math.round(lFLoc.x) + (int)(this.x), (int)Math.round(lFLoc.y) + (int)(this.y), Globals.CURRENT_HEADING);
        this.wrapperFR.drawDynamic(g, (int)Math.round(rFLoc.x) + (int)(this.x), (int)Math.round(rFLoc.y) + (int)(this.y), Globals.CURRENT_HEADING);
        this.wrapperBL.drawDynamic(g, (int)Math.round(lBLoc.x) + (int)(this.x), (int)Math.round(lBLoc.y) + (int)(this.y), Globals.CURRENT_HEADING);
        this.wrapperBR.drawDynamic(g, (int)Math.round(rBLoc.x) + (int)(this.x), (int)Math.round(rBLoc.y) + (int)(this.y), Globals.CURRENT_HEADING);
    }

    private void drawRobotOriented(Graphics g){
        g.drawRect(Globals.WIDTH / 2 - Globals.SWERVE_WIDTH / 2, (int)(this.y) - Globals.SWERVE_HEIGHT / 2, Globals.SWERVE_WIDTH, Globals.SWERVE_HEIGHT);
        this.wrapperFl.draw(g);
        this.wrapperFR.draw(g);
        this.wrapperBL.draw(g);
        this.wrapperBR.draw(g);
    }

    public static Animatable getInstance(){
        if (instance == null){
            synchronized (SwerveBaseAnimatable.class){
                if (instance == null){
                    instance = new SwerveBaseAnimatable();
                }
            }
        }
        return instance;
    }
}
