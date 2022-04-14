package frontend_viewcontroller;

import backend_models.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import static java.lang.Thread.sleep;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
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
            
                if (theBackendModel.theModel == null) {
                    System.out.println("Model is null");
                    return;
                }

                theBackendModel.theModel.trainModel();
      

        }
    }
    
    private class OutputAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {

                String s = null;
                            
//                BufferedReader r = theBackendModel.theModel.console;

                System.out.println("Testing");
                while (true) {
                    sleep(1000);
                    System.out.println("THIS IS IT");
//                    while ((s = r.readLine()) != null) {
//                        theBackendModel.theModel.output += s;
                        theMainViewDisplay.updateTextContentField();
//                        System.out.println(theBackendModel.theModel.cons);

//                    }
                }
                
//            } catch (IOException err) {
//                System.out.println(err);
            } catch (InterruptedException ex) {
                Logger.getLogger(ModelsAndViewsController.class.getName()).log(Level.SEVERE, null, ex);
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
        }
    }

    private class InfoAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
//      Will display a panel that provides a description of the
//      software and how it generally works.
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
    
    private class ReadConsoleOutput implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                String s = null;
                            
                BufferedReader r = theBackendModel.theModel.console;

                while ((s = r.readLine()) != null) {
                    theBackendModel.theModel.output += s;
                    theMainViewDisplay.updateTextContentField();

                }

            } catch (IOException err) {
                System.out.println(err);
            }
        }
    }

    public ModelsAndViewsController(BackendModelSetup aBackend, MainViewDisplay aMainViewDisplay) {
        this.theBackendModel = aBackend;
        this.theMainViewDisplay = aMainViewDisplay;
        this.initController();
    }

    private void initController() {
        this.theMainViewDisplay.trainAction.addActionListener(new TrainAction());
        this.theMainViewDisplay.outputButton.addActionListener(new OutputAction());
        this.theMainViewDisplay.importModelFromFileButton.addActionListener(new ImportModelAction());
        this.theMainViewDisplay.predictButton.addActionListener(new PredictAction());
    }
}
