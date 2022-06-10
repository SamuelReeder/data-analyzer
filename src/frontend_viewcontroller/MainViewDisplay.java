package frontend_viewcontroller;

import backend_models.*;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.util.List;

/**
 *
 * @author sam
 */
public class MainViewDisplay extends JFrame {

    BackendModelSetup theBackendModel;

    JLabel textContentLabel;
    JTextArea textContentField, predictionField, predictionInputField, trainingOutputField, progressField;

    JButton infoButton, outputButton, predictButton, trainButton, saveModelToFileButton, importModelFromFileButton, setup;
    JTextField trainingInput, testingInput, respondingInput, epochs, importedModel;

    JScrollPane textContentPane, predictionInputPane;

    JSeparator trainPredict, predictProgress;

    JComboBox alg;

    JProgressBar progressBar;
    
    DefaultBoundedRangeModel model;


//    JComponent[] arr = {trainingOutputField, alg};
    public MainViewDisplay(BackendModelSetup aBackend) {
        this.theBackendModel = aBackend;
        this.initComponents();
    }

    private void initComponents() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Container mainDisplayPane = this.getContentPane();
        mainDisplayPane.setLayout(new GridBagLayout());

        GridBagConstraints c;

        this.setMinimumSize(new Dimension(600, 800));

        this.trainingInput = new JTextField();
        this.trainingInput.setToolTipText("Provide URL or path to training dataset");

        this.testingInput = new JTextField();
        this.testingInput.setToolTipText("Provide URL or path to testing dataset or leave blank if not applicable");

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

        this.trainingOutputField = new JTextArea();
        this.trainingOutputField.setSize(250, 500);
        this.trainingOutputField.setLineWrap(true);
        this.trainingOutputField.setEditable(false);
        this.trainingOutputField.setWrapStyleWord(rootPaneCheckingEnabled);

        this.predictionField = new JTextArea();
        this.predictionField.setSize(250, 500);
        this.predictionField.setLineWrap(true);
        this.predictionField.setEditable(false);
        this.predictionField.setWrapStyleWord(rootPaneCheckingEnabled);
        this.predictionField.setToolTipText("You will see the output for your prediction here");

        this.progressField = new JTextArea();
        this.progressField.setSize(250, 500);
        this.progressField.setLineWrap(true);
        this.progressField.setEditable(false);
        this.progressField.setWrapStyleWord(rootPaneCheckingEnabled);

        this.predictionInputField = new JTextArea();
        this.predictionInputField.setLineWrap(true);
        this.predictionInputField.setEditable(true);
        this.predictionInputField.setWrapStyleWord(rootPaneCheckingEnabled);
        this.predictionInputField.setToolTipText("Provide prediction input here");
        this.predictionInputPane = new JScrollPane(this.predictionInputField);
        this.predictionInputPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.predictionInputPane.setSize(250, 500);

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

        this.predictProgress = new JSeparator();
        this.predictProgress.setOrientation(SwingConstants.HORIZONTAL);

        this.alg = new JComboBox();
        this.alg.addItem("Optimize");
        this.alg.addItem("Classification");
        this.alg.addItem("Regression");

        this.epochs = new JTextField();
        this.respondingInput.setToolTipText("Provide x number of epochs to train with {x| 0 < x < 100, x is int}");

        this.importedModel = new JTextField();
        this.importedModel.setText("No model imported");
        
        model = new DefaultBoundedRangeModel();

        this.progressBar = new JProgressBar(model);
//        this.progressBar.setMinimum(0);
//        this.progressBar.setMaximum(100);
        this.progressBar.setStringPainted(true);
//        this.progressBar.setValue(0);

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
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        mainDisplayPane.add(this.trainingOutputField, c);

        c = new GridBagConstraints();
        c.gridx = 4;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        mainDisplayPane.add(this.alg, c);

        c = new GridBagConstraints();
        c.gridx = 4;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        mainDisplayPane.add(this.epochs, c);

        c = new GridBagConstraints();
        c.gridx = 4;
        c.gridy = 4;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        mainDisplayPane.add(this.trainButton, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 5;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        mainDisplayPane.add(this.trainPredict, c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 8;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.ipady = 50;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        mainDisplayPane.add(this.predictionField, c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 3;
        c.gridheight = 2;
        c.ipady = 50;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        mainDisplayPane.add(this.predictionInputPane, c);

        c = new GridBagConstraints();
        c.gridx = 4;
        c.gridy = 6;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        mainDisplayPane.add(this.predictButton, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 1;
        c.gridheight = 1;
        mainDisplayPane.add(this.importModelFromFileButton, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 1;
        c.gridheight = 1;

        mainDisplayPane.add(this.importedModel, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 1;
        c.gridheight = 1;
        mainDisplayPane.add(this.setup, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 5;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        mainDisplayPane.add(this.predictProgress, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 10;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        mainDisplayPane.add(this.progressField, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 11;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        mainDisplayPane.add(this.progressBar, c);

        this.pack();
    }

    public void fillProgress() {
//        GeneralProgressBar worker = new GeneralProgressBar();
//        worker.execute();
        Runnable runner = new Runnable()
        {
            public void run() {
//                int i = Python.getProgress();
//                model.setMinimum(0);
//                model.setMaximum(100);
//                try {
//                    while (i <= 100 || true) {
//                        model.setValue(i);
//                        i = Python.getProgress();
//                        Thread.sleep(50);
//                    }
//                } catch (InterruptedException ex) {
//                    model.setValue(model.getMaximum());
//                } 
                int i = 1;
                model.setMinimum(0);
                model.setMaximum(100);
                try {
                    while (i <= 100 || true) {
                        model.setValue(i);
                        i++;
                        Thread.sleep(50);
                    }
                } catch (InterruptedException ex) {
                    model.setValue(model.getMaximum());
                }
            }
        };
        Thread t = new Thread(runner, "Code Executer");
        t.start();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {}
        t.interrupt();

    }
    
    public void u(int i) {
        this.progressBar.setValue(i);
        this.progressBar.repaint();
        System.out.println("Tis working" + i);
    }

//    public class GeneralProgressBar extends SwingWorker<Void, Integer> {
//                
//
//        @Override
//        protected Void doInBackground() throws Exception {
//
//            int i = Python.getProgress();
//            while (i <= 100) {
//                publish(i);
////                progress.setValue(i);
//                Thread.sleep(100);
//                i = Python.getProgress();
//            }
//            return null;
//        }
//
//        @Override
//        protected void process(List<Integer> chunks) {
//              
//            progressBar.setValue(chunks.get(chunks.size() - 1));
//            System.out.println(progressBar.getValue() + " " + (chunks.get(chunks.size() - 1)));
//            super.process(chunks);
//        }
//
//        @Override
//        protected void done() {
//            progressBar.setValue(100);
//        }
//    }

    

//    void updateProgressBar(int value) {
//        this.progressBar.setValue(value);
//    }

    void updateTextContentField() {
        if (this.theBackendModel.theModel == null) {
            System.out.println("It is null");
            this.textContentField.setText("");
        } else {
            this.predictionField.setText(this.theBackendModel.theModel.output);
        }
    }

    void trainingOutput(String output) {
        System.out.println(output);
        if (this.theBackendModel.theModel == null) {
            System.out.println("It is null");
        } else {
            this.trainingOutputField.setText(output);
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

    void updateImport(String path) {
        this.importedModel.setText(path);
    }

    void updateBackend() {
        if (this.theBackendModel.theModel == null) {
            System.out.println("It is null");
        } else {
            this.theBackendModel.theModel.setTraining(this.trainingInput.getText().trim());
            String temp = this.testingInput.getText().trim();
            if (temp == null || temp.equals("")) {
                this.theBackendModel.theModel.setTesting("none");
            } else {
                this.theBackendModel.theModel.setTesting(temp);
            }
            this.theBackendModel.theModel.setResponding(this.respondingInput.getText().trim());
            this.theBackendModel.theModel.setEpochs(this.epochs.getText().trim());
            this.theBackendModel.theModel.setAlg(this.alg.getSelectedItem().toString().trim());
        }
    }

    public void delete() throws StringIndexOutOfBoundsException {
        String inputText = this.epochs.getText();
        String newStr = inputText.substring(0, -1);
        this.epochs.setText(newStr);
    }

    public boolean isEpochInRange() {
        return this.epochs.getText().length() > 1 ? false : true;
    }

    void errorDisplay(String err) {
        JOptionPane.showMessageDialog(this,
                "The file(s) you provided could not be found. Please ensure the specified path is correct.",
                "RileNotFoundError error",
                JOptionPane.ERROR_MESSAGE);
    }

//    void trainDisplay() {
//        JOptionPane.showMessageDialog(this,
//        this.arr,
//        "The model is currently being trained",
//        JOptionPane.INFORMATION_MESSAGE);
//    }
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
