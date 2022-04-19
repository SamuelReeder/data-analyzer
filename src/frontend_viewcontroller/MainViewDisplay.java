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
    JButton outputButton;
    JTextArea predictionField;
    
    JTextField trainingInput, testingInput, respondingInput;

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

        this.trainingInput = new JTextField();
        this.trainingInput.setToolTipText("Provide URL or path to training dataset");
        
        this.testingInput = new JTextField();
        this.testingInput.setToolTipText("Provide URL or path to testing dataset");
        
        this.respondingInput = new JTextField();
        this.respondingInput.setToolTipText("Provide name of responsive column");
        
        this.textContentLabel = new JLabel();
        this.textContentLabel.setText("Text content"); 

        this.textContentField = new JTextArea();
        this.textContentField.setSize(250, 500);
        this.textContentField.setLineWrap(true);
        this.textContentField.setEditable(true);
        this.textContentField.setWrapStyleWord(rootPaneCheckingEnabled);
        
        this.predictionField = new JTextArea();
        this.predictionField.setSize(250, 500);
        this.predictionField.setLineWrap(true);
        this.predictionField.setEditable(true);
        this.predictionField.setWrapStyleWord(rootPaneCheckingEnabled);
        
       
        this.importModelFromFileButton = new JButton();
        this.importModelFromFileButton.setText("Import Model");

        this.saveModelToFileButton = new JButton();
        this.saveModelToFileButton.setText("Save Model");

        this.trainAction = new JButton();
        this.trainAction.setText("Train");
        
        this.outputButton = new JButton();
        this.outputButton.setText("Output");


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
        c.gridx = 1;
        c.gridy = 0;
//        c.gridwidth = 1;
//        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        mainDisplayPane.add(this.trainingInput, c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
//        c.gridwidth = 1;
//        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        mainDisplayPane.add(this.testingInput, c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 2;
//        c.gridwidth = 1;
//        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        mainDisplayPane.add(this.respondingInput, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        mainDisplayPane.add(this.textContentLabel, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 2;
        c.fill = GridBagConstraints.VERTICAL;
//        c.ipadx = 300;
//        c.ipady = 200;
        mainDisplayPane.add(this.textContentPane, c);
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 2;
        c.fill = GridBagConstraints.VERTICAL;
//        c.ipadx = 300;
//        c.ipady = 200;
        mainDisplayPane.add(this.predictionField, c);
        
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
        mainDisplayPane.add(this.outputButton, c);

        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 4;
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
              this.textContentField.setText(this.theBackendModel.theModel.consoleOutput);
        }
    }
    
    void getPrediction() {
        if (this.theBackendModel.theModel == null) {
            System.out.println("It is null");
        } else {
//            this.textContentField.setText(this.theBackendModel.theModel.console);
              this.theBackendModel.theModel.prediction = this.textContentField.getText();
        }
        System.out.println(this.theBackendModel.theModel.prediction);
    }
    
    void updateBackend() {
        if (this.theBackendModel.theModel == null) {
            System.out.println("It is null");
        } else {
            this.theBackendModel.theModel.setTraining(this.trainingInput.getText().trim());
            this.theBackendModel.theModel.setTesting(this.testingInput.getText().trim());
            this.theBackendModel.theModel.setResponding(this.respondingInput.getText().trim());
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
