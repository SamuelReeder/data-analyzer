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
                theBackendModel.theModel.output = sc.nextLine();
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
                String s = null;

                ProcessBuilder builder = new ProcessBuilder(
                        "cmd.exe", "/c", "python -m venv --system-site-packages .\\venv && .\\venv\\Scripts\\activate && pip install --upgrade pip && pip install -r requirements.txt");
                builder.redirectErrorStream(true);
                Process p = builder.start();

                BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

                while ((s = input.readLine()) != null) {
                    System.out.println(s);
                }
            } catch (IOException err) {
                System.out.println(err);
            }
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
    }
}
