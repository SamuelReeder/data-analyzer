package frontend_viewcontroller;

import backend_models.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sam
 */
public class ModelsAndViewsController {

    BackendModelSetup theBackendModel;
    MainViewDisplay theMainViewDisplay;

    private class ClearAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                theBackendModel.theModel = new Model("", theMainViewDisplay);
                theMainViewDisplay.clear();
            } catch (IOException ex) {
                Logger.getLogger(ModelsAndViewsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class ImportModelAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                theBackendModel.theModel = new Model("", theMainViewDisplay);
                theBackendModel.theModel.setPath(theMainViewDisplay.showOpenDialog());
                theMainViewDisplay.updateImport(theBackendModel.theModel.getPath());
            } catch (IOException ex) {
                Logger.getLogger(ModelsAndViewsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class TrainAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            theMainViewDisplay.clearForTraining();
            theMainViewDisplay.disableFunctionality();
            try {
                theBackendModel.theModel = new Model("", theMainViewDisplay);
            } catch (IOException ex) {
                Logger.getLogger(ModelsAndViewsController.class.getName()).log(Level.SEVERE, null, ex);
            }

            theMainViewDisplay.updateBackend();
            theBackendModel.theModel.trainModel();
            theBackendModel.theModel.current.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    String name = evt.getPropertyName();
                    if (name.equals("state")) {
                        switch (theBackendModel.theModel.current.getState()) {
                            case DONE:
                                try {
                                    cont();
                                } catch (IOException ex) {
                                    Logger.getLogger(ModelsAndViewsController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                        }
                    }
                }
            });
        }

        public void cont() throws IOException {
            theBackendModel.theModel.contBackend();
            if (theBackendModel.theModel.getError()) {
                String text = theBackendModel.theModel.getErrorText().trim();
                String error = (text.equals("") || text == null) ? "An unknown error occured" : text;
                theMainViewDisplay.errorDisplay(error);
                theMainViewDisplay.trainingOutput(error);
            } else {
                theMainViewDisplay.trainingOutput(theBackendModel.theModel.getOutput());
            }
            theMainViewDisplay.enableFunctionality();
        }
    }

    private class PredictAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            theMainViewDisplay.clearForPrediction();
            theMainViewDisplay.disableFunctionality();
            if (theBackendModel.theModel == null) {
                System.out.println("Model is null");
                return;
            }
            theMainViewDisplay.getPrediction();
            if (theBackendModel.theModel.getPath().equals("") || theBackendModel.theModel.getPath() == null) {
                theBackendModel.theModel.setPath(theMainViewDisplay.importedModel.getText());
            }
            theBackendModel.theModel.predict();
            theBackendModel.theModel.current.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    String name = evt.getPropertyName();
                    if (name.equals("state")) {
                        switch (theBackendModel.theModel.current.getState()) {
                            case DONE:
                                try {
                                    cont();
                                } catch (IOException ex) {
                                    Logger.getLogger(ModelsAndViewsController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                        }
                    }
                }
            });
        }
        
        public void cont() throws IOException {            
            theBackendModel.theModel.contBackend();
            if (theBackendModel.theModel.getError()) {
                String text = theBackendModel.theModel.getErrorText();
                String error = (text.equals("") || text == null) ? "An unknown error occured" : text;
                theMainViewDisplay.errorDisplay(error);
                theMainViewDisplay.predictionOutput(error);
            } else {
                theMainViewDisplay.predictionOutput(theBackendModel.theModel.getOutput());
            }
            theMainViewDisplay.enableFunctionality();
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
            theMainViewDisplay.clearForSetup();
            theMainViewDisplay.disableFunctionality();
            try {
                theBackendModel.theModel = new Model("", theMainViewDisplay);
            } catch (IOException ex) {
                Logger.getLogger(ModelsAndViewsController.class.getName()).log(Level.SEVERE, null, ex);
            }

            String args;
            if (theBackendModel.theModel.getIsWindows()) {
                args = "python -m venv --without-pip .\\venv && .\\venv\\Scripts\\activate && pip install --upgrade pip && pip install -r requirements.txt";;
            } else {
                args = "python3 -m venv --without-pip ./venv && source ./venv/bin/activate && pip install --upgrade pip && pip install -r requirements.txt";
            }

            System.out.println(args);
            Python py = new Python(args, theBackendModel.theModel.getIsWindows(), 0, theMainViewDisplay, "setup");
            py.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    String name = evt.getPropertyName();
                    if (name.equals("state")) {
                        switch (py.getState()) {
                            case DONE:
                                theMainViewDisplay.enableFunctionality();
                        }
                    }
                }
            });
            py.execute();
        }
    }

    private class Key implements KeyListener {

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() < '0' || e.getKeyChar() > '9' || !theMainViewDisplay.isEpochInRange()) {
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
        this.theMainViewDisplay.clear.addActionListener(new ClearAction());
        this.theMainViewDisplay.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });  
    }
}
