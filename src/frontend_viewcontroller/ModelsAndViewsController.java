package frontend_viewcontroller;

import backend_models.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
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
//      Allows the user to import a trained model that 
//      the user can use to make predictions.
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

                if (theBackendModel.theModel == null) {
                    System.out.println("Model is null");
                    return;
                }

                theBackendModel.theModel.trainModel();
            } catch (IOException err) {
                System.out.println(err);
            }

        }
    }

    private class PredictAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
//      Call the backend model to perform a prediction.
//      Update the GUI to display prediction.
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
//    public class ConsoleReader extends SwingWorker<String, Integer> {
//
//        ConsoleReader(JTextArea textArea, int numbersToFind) {
//            //initialize
//        }
//
//        @Override
//        public String doInBackground() {
//            
//            try {
//                String s = null;
//            
//                BufferedReader r = theBackendModel.theModel.console;
//
//                while ((s = r.readLine()) != null) {
//                    s += theBackendModel.theModel.console;
//                }
//
//                return s;
//            } catch (IOException err) {
//                System.out.println(err);
//            }
//            
//            return "";
//            
//        }
//
//        @Override
//        protected void process(String chunks) {
//            for (int i = 0; i < chunks.length(); i++) {
//                textArea.append(number + "\n");
//            }
//        }
//    }


    public ModelsAndViewsController(BackendModelSetup aBackend, MainViewDisplay aMainViewDisplay) {
        this.theBackendModel = aBackend;
        this.theMainViewDisplay = aMainViewDisplay;
        this.initController();
    }

    private void initController() {
        this.theMainViewDisplay.trainAction.addActionListener(new TrainAction());
    }
}
