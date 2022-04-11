package frontend_viewcontroller;

import backend_models.*;

import java.awt.*;
import java.io.*;
import javax.swing.*;

/**
 *
 * @author sam
 */
public class MainViewDisplay extends JFrame {

    BackendModelSetup theBackendModel;

//  Declare vars for representing the varioys elements of the GUI

    public MainViewDisplay(BackendModelSetup aBackend) {
        this.theBackendModel = aBackend;
        this.initComponents();
    }

    private void initComponents() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Container mainDisplayPane = this.getContentPane();
        mainDisplayPane.setLayout(new GridBagLayout());
        
        GridBagConstraints c;
        
//      This section will define the layout and properties of each element in the GUI

        this.pack();
    }

    void updateTextContentField() {
//      Will append the contents of the cmd.exe runtime that is training
//      the model and update text field with various pieces of information
//      that will be useful to the user.
    }
    
    void openInfoPanel() {
//      Will open a panel to show information about the software.
//        -  Ideally, this will be a separate GUI.
    }

    String showSaveDialog() {
        return null;
//      This method shows dialog in order to save the model, however,
//      it might be preferable to simply save the model to the directory
//      to make importing easier.
    }

    String showOpenDialog() {
        return null;
//      Will, by default, display the directory to which models are
//      automatically saved to.

//      However, the option will be available to import the model from 
//      any specified directory.
    }
}
