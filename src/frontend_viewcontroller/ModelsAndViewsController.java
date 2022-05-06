package frontend_viewcontroller;

import backend_models.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

            theBackendModel.theModel.trainModel();

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

    private class SetVarsAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
//      Connects to a text input field such that the user can
//      enter the names of the responding variables fpr the
//      model to optimize its training.
        }
    }
    
//    private class OrderAction implements ActionListener {
//
//        @Override
//        public void actionPerformed(ActionEvent ae) {
////            String petName = theMainViewDisplay.dropdownMenu.getSelectedItem();
//              theMainViewDisplay.dropdownMenu.
////            updateLabel(petName);
//        }
//    }

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
//        this.theMainViewDisplay.dropdownMenu.addActionListener(new OrderAction());
    }
}
