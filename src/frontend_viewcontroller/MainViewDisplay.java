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
    
    JLabel textContentLabel;
    JTextArea textContentField;
    JButton importModelFromFileButton;
    JButton saveModelToFileButton;
    JButton trainAction;
    JButton predictButton;
    JButton infoAction;
    JScrollPane textContentPane;
    
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
        
//this.setMinimumSize(new Dimension(600, 200));
        
        this.textContentLabel = new JLabel();
        this.textContentLabel.setText("Text content"); 

        this.textContentField = new JTextArea();
        this.textContentField.setSize(500, 500);
        this.textContentField.setLineWrap(true);
        this.textContentField.setEditable(true);
        this.textContentField.setWrapStyleWord(rootPaneCheckingEnabled);
        
       
        this.importModelFromFileButton = new JButton();
        this.importModelFromFileButton.setText("Import Model");

        this.saveModelToFileButton = new JButton();
        this.saveModelToFileButton.setText("Save Model");

        this.trainAction = new JButton();
        this.trainAction.setText("Train");

        this.predictButton = new JButton();
        this.predictButton.setText("Predict");

        this.textContentPane = new JScrollPane(this.textContentField);
        this.textContentPane.setSize(500, 500);


        /*
         * Choose your LayoutManager for the mainDisplayPane here. See:
         * http://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html
         *
         * I suggest GridBagLayout. For more details, see:
         * http://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
         */
        
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        mainDisplayPane.add(this.textContentLabel, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.gridheight = 2;
        c.fill = GridBagConstraints.BOTH;
        c.ipadx = 300;
        c.ipady = 200;
        mainDisplayPane.add(this.textContentPane, c);
        
        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        mainDisplayPane.add(this.importModelFromFileButton, c);

        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.insets = new Insets(50, 0, 0, 0);
        mainDisplayPane.add(this.trainAction, c);

        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        mainDisplayPane.add(this.predictButton, c);

        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 1;
        mainDisplayPane.add(this.saveModelToFileButton, c);
        
        
        this.pack();
    }

    void updateTextContentField() {
        if (this.theBackendModel.theModel == null) {
            System.out.println("It is null");
            this.textContentField.setText("");
        } else {
//            this.textContentField.setText(this.theBackendModel.theModel.console);
              this.textContentField.setText(this.theBackendModel.theModel.output);
        }
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
