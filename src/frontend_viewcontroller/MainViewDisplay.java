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
    JTextArea textContentField, predictionField, predictionInputField;
    
    JButton infoButton, outputButton, predictButton, trainButton, saveModelToFileButton, importModelFromFileButton, setup;
    JTextField trainingInput, testingInput, respondingInput;

    JScrollPane textContentPane;
    
    JSeparator trainPredict;
    
    public MainViewDisplay(BackendModelSetup aBackend) {
        this.theBackendModel = aBackend;
        this.initComponents();
    }

    private void initComponents() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Container mainDisplayPane = this.getContentPane();
        mainDisplayPane.setLayout(new GridBagLayout());
        
        GridBagConstraints c;
        
        this.setMinimumSize(new Dimension(400, 400));

        this.trainingInput = new JTextField();
        this.trainingInput.setToolTipText("Provide URL or path to training dataset");
        
        this.testingInput = new JTextField();
        this.testingInput.setToolTipText("Provide URL or path to testing dataset");
        
        this.respondingInput = new JTextField();
        this.respondingInput.setToolTipText("Provide name of responsive column");
        
        this.textContentLabel = new JLabel();
        this.textContentLabel.setText("Data Analyzer"); 
        this.textContentLabel.setFont(new Font("Serif", Font.BOLD, 30));

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
        
        this.predictionInputField = new JTextArea();
        this.predictionInputField.setSize(250, 500);
        this.predictionInputField.setLineWrap(true);
        this.predictionInputField.setEditable(true);
        this.predictionInputField.setWrapStyleWord(rootPaneCheckingEnabled);
        
        this.importModelFromFileButton = new JButton();
        this.importModelFromFileButton.setText("Import Model");

        this.saveModelToFileButton = new JButton();
        this.saveModelToFileButton.setText("Save Model");

        this.trainButton = new JButton();
        this.trainButton.setText("Train");
        
        this.outputButton = new JButton();
        this.outputButton.setText("Output");
        
        this.setup = new JButton();
        this.setup.setText("Setup");

        this.infoButton = new JButton();
        this.infoButton.setText("Info");
//        this.infoButton.setFocusPainted(false);
//        this.infoButton.setBackground(Color.BLUE);
//        this.infoButton.setForeground(Color.white);

        this.predictButton = new JButton();
        this.predictButton.setText("Predict");

        this.textContentPane = new JScrollPane(this.textContentField);
        this.textContentPane.setSize(500, 500);
        
        this.trainPredict = new JSeparator();
        this.trainPredict.setOrientation(SwingConstants.HORIZONTAL);


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
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(20, 20, 20, 20);
        mainDisplayPane.add(this.textContentLabel, c);
        
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        mainDisplayPane.add(this.infoButton, c);
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        mainDisplayPane.add(this.trainingInput, c);
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        mainDisplayPane.add(this.testingInput, c);
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        mainDisplayPane.add(this.respondingInput, c);
        
        c = new GridBagConstraints();
        c.gridx = 4;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.CENTER;
        c.insets = new Insets(5, 5, 5, 5);
        mainDisplayPane.add(this.trainButton, c);
        
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 5;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        mainDisplayPane.add(this.trainPredict, c);
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.ipady = 50;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        mainDisplayPane.add(this.predictionField, c);
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.ipady = 50;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        mainDisplayPane.add(this.predictionInputField, c);
        
        c = new GridBagConstraints();
        c.gridx = 4;
        c.gridy = 5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        mainDisplayPane.add(this.predictButton, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 1;
        c.gridheight = 1;
        mainDisplayPane.add(this.importModelFromFileButton, c);
        
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 1;
        c.gridheight = 1;
        mainDisplayPane.add(this.setup, c);

        this.pack();
    }

    void updateTextContentField() {
        if (this.theBackendModel.theModel == null) {
            System.out.println("It is null");
            this.textContentField.setText("");
        } else {
              this.predictionField.setText(this.theBackendModel.theModel.output);
        }
    }
    
    void getPrediction() {
        if (this.theBackendModel.theModel == null) {
            System.out.println("It is null");
        } else {
            System.out.println(this.predictionInputField.getText().trim());
            this.theBackendModel.theModel.setPrediction(this.predictionInputField.getText().trim());
        }
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
        JFileChooser jfc = new JFileChooser();
        int status = jfc.showSaveDialog(this);
        if (status == JFileChooser.APPROVE_OPTION) {
            File theFile = jfc.getSelectedFile();
            String thePath = theFile.getAbsolutePath();
            return thePath;
        }
        
        return null;
    }

    String showOpenDialog() {
        JFileChooser jfc = new JFileChooser();
        jfc.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int status = jfc.showOpenDialog(this);
        if (status == JFileChooser.APPROVE_OPTION) {
            File theFile = jfc.getCurrentDirectory();
            String thePath = theFile.getAbsolutePath();
            return thePath;
        }

        return null;
    }
}
