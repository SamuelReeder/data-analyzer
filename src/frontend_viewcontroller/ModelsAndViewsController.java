package frontend_viewcontroller;

import backend_models.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sam
 */
public class ModelsAndViewsController {

    BackendModelSetup theBackendModel;
    MainViewDisplay theMainViewDisplay;

    private class ImportModelAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                theBackendModel.theModel = new Model("");
                theBackendModel.theModel.setPath(theMainViewDisplay.showOpenDialog());
                System.out.println(theBackendModel.theModel.getPath());
                theMainViewDisplay.updateImport(theBackendModel.theModel.getPath());
            } catch (IOException ex) {
                Logger.getLogger(ModelsAndViewsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class SaveModelToFileAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
//      Saves the trained model, such that it can be
//      imported and used again without any extra 
//      training necessary.
        }
    }

    private class TrainAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            try {
                theBackendModel.theModel = new Model("");
            } catch (IOException ex) {
                Logger.getLogger(ModelsAndViewsController.class.getName()).log(Level.SEVERE, null, ex);
            }

            theMainViewDisplay.updateBackend();
            
            try {
                theMainViewDisplay.trainingOutput("The model is currently training through 10 epochs...");
                theBackendModel.theModel.trainModel();
                if (theBackendModel.theModel.getError()) {
                    String text = theBackendModel.theModel.getErrorText();
                    theMainViewDisplay.errorDisplay((text.equals("") || text == null) ? "An unknown error occured" : text);
                } else {
                    theMainViewDisplay.trainingOutput("The model has completed training with an accuracy of " + theBackendModel.theModel.getLoss());
                }
            } catch (IOException err) {
                theMainViewDisplay.trainingOutput("An error has occured");
            }
            

        }
    }

    private class PredictAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (theBackendModel.theModel == null) {
                System.out.println("Model is null");
                return;
            }
            theMainViewDisplay.getPrediction();
            theBackendModel.theModel.predict();
            
            try {
                Scanner sc = new Scanner(new File("results.txt"));
                theBackendModel.theModel.output = "Variable: "  + (theBackendModel.theModel.getResponsive() != null ? theBackendModel.theModel.getResponsive() : "unknown") + ", has a " + sc.nextLine() + "% probability.";
                theMainViewDisplay.updateTextContentField();
            } catch (IOException err) {
                System.out.println(err);
            }
        }
    }

    private class InfoAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            InfoViewDisplay theInfoViewDisplay = new InfoViewDisplay();
            theInfoViewDisplay.setVisible(true);
        }
    }

    private class SetupAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
             try {
                theBackendModel.theModel = new Model("");
            } catch (IOException ex) {
                Logger.getLogger(ModelsAndViewsController.class.getName()).log(Level.SEVERE, null, ex);
            }
             
            String args;
            if (theBackendModel.theModel.getIsWindows()) {
                args = "python -m venv .\\venv && .\\venv\\Scripts\\activate && pip install --upgrade pip && pip install -r requirements.txt";;
            } else {
                args = "python3 -m venv ./venv && source ./venv/bin/activate && pip install --upgrade pip && pip install -r requirements.txt";
            }
            
            System.out.println(args);
            Python.run(args, theBackendModel.theModel.getIsWindows());
        }
    }
    
    private class Key implements KeyListener {

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() < 48 || e.getKeyChar() > 57 || !theMainViewDisplay.isEpochInRange()) {
                theMainViewDisplay.delete();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    public ModelsAndViewsController(BackendModelSetup aBackend, MainViewDisplay aMainViewDisplay) {
        this.theBackendModel = aBackend;
        this.theMainViewDisplay = aMainViewDisplay;
        this.initController();
    }

    private void initController() {
        this.theMainViewDisplay.trainButton.addActionListener(new TrainAction());
        this.theMainViewDisplay.importModelFromFileButton.addActionListener(new ImportModelAction());
        this.theMainViewDisplay.predictButton.addActionListener(new PredictAction());
        this.theMainViewDisplay.infoButton.addActionListener(new InfoAction());
        this.theMainViewDisplay.setup.addActionListener(new SetupAction());
        this.theMainViewDisplay.epochs.addKeyListener(new Key());
//        this.theMainViewDisplay.alg.addActionListener(new AlgAction());
    }
}
