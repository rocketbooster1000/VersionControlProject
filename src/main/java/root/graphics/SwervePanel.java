package root.graphics;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import root.Globals;
import root.Globals.KeyBindingActions;
import root.Globals.KeyBindingStrings;
import root.model.SwerveBase;
import root.util.Animatable;

public class SwervePanel extends JPanel implements ActionListener{
    private Animatable base;
    private ControlPanel rotationControlPanel, headingControlPanel, veolictyControlPanel;
    
    public SwervePanel(){
        super();
        this.setLayout(null);
        this.base = SwerveBaseAnimatable.getInstance();

        this.getInputMap(Globals.IFW).put(KeyStroke.getKeyStroke("pressed W"), KeyBindingStrings.REQUEST_FORWARD);
        this.getInputMap(Globals.IFW).put(KeyStroke.getKeyStroke("pressed S"), KeyBindingStrings.REQUEST_BACK);
        this.getInputMap(Globals.IFW).put(KeyStroke.getKeyStroke("pressed A"), KeyBindingStrings.REQUEST_STRAFE_LEFT);
        this.getInputMap(Globals.IFW).put(KeyStroke.getKeyStroke("pressed D"), KeyBindingStrings.REQUEST_STRAFE_RIGHT);
        this.getInputMap(Globals.IFW).put(KeyStroke.getKeyStroke("pressed J"), KeyBindingStrings.REQUEST_ROTATION_LEFT);
        this.getInputMap(Globals.IFW).put(KeyStroke.getKeyStroke("pressed L"), KeyBindingStrings.REQUEST_ROTATION_RIGHT);
        
        this.getInputMap(Globals.IFW).put(KeyStroke.getKeyStroke("released W"), KeyBindingStrings.REQUEST_RELEASE_FORWARD);
        this.getInputMap(Globals.IFW).put(KeyStroke.getKeyStroke("released S"), KeyBindingStrings.REQUEST_RELEASE_FORWARD);
        this.getInputMap(Globals.IFW).put(KeyStroke.getKeyStroke("released A"), KeyBindingStrings.REQUEST_RELEASE_STRAFE);
        this.getInputMap(Globals.IFW).put(KeyStroke.getKeyStroke("released D"), KeyBindingStrings.REQUEST_RELEASE_STRAFE);
        this.getInputMap(Globals.IFW).put(KeyStroke.getKeyStroke("released J"), KeyBindingStrings.REQUEST_RELEASE_ROTATION);
        this.getInputMap(Globals.IFW).put(KeyStroke.getKeyStroke("released L"), KeyBindingStrings.REQUEST_RELEASE_ROTATION);
        
        this.getInputMap(Globals.IFW).put(KeyStroke.getKeyStroke("B"), KeyBindingStrings.REQUEST_RESET_HEADING);
        this.getInputMap(Globals.IFW).put(KeyStroke.getKeyStroke("H"), KeyBindingStrings.REQUEST_TOGGLE_HEADING);

        this.getActionMap().put(KeyBindingStrings.REQUEST_FORWARD, KeyBindingActions.FORWARD_ACTION);
        this.getActionMap().put(KeyBindingStrings.REQUEST_BACK, KeyBindingActions.BACKWARDS_ACTION);
        this.getActionMap().put(KeyBindingStrings.REQUEST_STRAFE_LEFT, KeyBindingActions.STRAFE_LEFT_ACTION);
        this.getActionMap().put(KeyBindingStrings.REQUEST_STRAFE_RIGHT, KeyBindingActions.STRAFE_RIGHT_ACTION);
        this.getActionMap().put(KeyBindingStrings.REQUEST_ROTATION_LEFT, KeyBindingActions.TURN_LEFT_ACTION);
        this.getActionMap().put(KeyBindingStrings.REQUEST_ROTATION_RIGHT, KeyBindingActions.TURN_RIGHT_ACTION);
        
        this.getActionMap().put(KeyBindingStrings.REQUEST_RELEASE_FORWARD, KeyBindingActions.RELEASE_FORWARD_ACTION);
        this.getActionMap().put(KeyBindingStrings.REQUEST_RELEASE_STRAFE, KeyBindingActions.RELEASE_STRAFE_ACTION);
        this.getActionMap().put(KeyBindingStrings.REQUEST_RELEASE_ROTATION, KeyBindingActions.RELEASE_ROTATION_ACTION);
        
        this.getActionMap().put(KeyBindingStrings.REQUEST_RESET_HEADING, KeyBindingActions.RESET_HEADING_ACTION);
        this.getActionMap().put(KeyBindingStrings.REQUEST_TOGGLE_HEADING, KeyBindingActions.TOGGLE_HEADING_ACTION);
        
        this.veolictyControlPanel = new ControlPanel(
            "Velocity",
            String.format("%,.1f", Globals.MAX_VELOCITY),
            () -> {},
            (e) -> {
                try{
                    Globals.MAX_VELOCITY = Double.parseDouble(veolictyControlPanel.getText());
                  } catch (Exception exception){}
                  
                veolictyControlPanel.setText(String.format("%.1f", Globals.MAX_VELOCITY));
            },
            Globals.CONTROL_WIDTH,
            Globals.CONTROL_HEIGHT,
            2 * Globals.CONTROL_HEIGHT
        );

        this.rotationControlPanel = new ControlPanel(
            "Rotation Speed",
            String.format("%,.1f", Math.toDegrees(Globals.MAX_ROTATION_SPEED)),
            () -> {},
            (e) -> {
                try{
                    Globals.MAX_ROTATION_SPEED = Math.toRadians(Double.parseDouble(rotationControlPanel.getText()));
                  } catch (Exception exception){}
                  
                rotationControlPanel.setText(String.format("%.1f", Math.toDegrees(Globals.MAX_ROTATION_SPEED)));
            },
            Globals.CONTROL_WIDTH,
            Globals.CONTROL_HEIGHT,
            Globals.CONTROL_HEIGHT
        );

        headingControlPanel = new ControlPanel(
            "Heading",
            SwerveBase.getFormatHeading(Globals.CURRENT_HEADING),
            () ->{
                Globals.HEADING_CHANGING = (Globals.HEADING_CHANGING) ? true : headingControlPanel.isInFocus();
                if (!Globals.HEADING_CHANGING && !headingControlPanel.isInFocus()){
                    headingControlPanel.setText(SwerveBase.getFormatHeading(Globals.CURRENT_HEADING));
                    
                }
                
            },
            (e) -> {
                try {
                    System.out.println("text: " + headingControlPanel.getText());
                    Globals.BUFFER_HEADING = Math.toRadians(360 - Double.parseDouble(headingControlPanel.getText()));
                    System.out.println(Globals.BUFFER_HEADING);
                    Globals.CHANGE_REQUESTED = true;
                } catch (Exception ex){System.out.println("exception");}
            },
            Globals.CONTROL_WIDTH,
            Globals.CONTROL_HEIGHT,
            0
        );




        this.add(rotationControlPanel, JLayeredPane.PALETTE_LAYER);
        this.add(headingControlPanel, JLayeredPane.PALETTE_LAYER);
        this.add(veolictyControlPanel, JLayeredPane.PALETTE_LAYER);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        this.base.draw(g);

        this.rotationControlPanel.refresh();
        this.headingControlPanel.refresh();
        this.veolictyControlPanel.refresh();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.base.update();
        repaint();

        Globals.WIDTH = this.getWidth();
        Globals.HEIGHT = this.getHeight();

    }
}
