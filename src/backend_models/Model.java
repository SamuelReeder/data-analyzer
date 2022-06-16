package backend_models;

import frontend_viewcontroller.MainViewDisplay;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sam
 */
public class Model extends Information {

    public String path, consoleOutput, output, prediction, errorText;
    public String[] vars;
    public MainViewDisplay display;
    public Python current;

    public Model(String path, MainViewDisplay display) throws IOException {
        this.path = path;
        this.consoleOutput = "";
        this.display = display;
    }

    public void trainModel() {
        String args;
        if (super.getIsWindows()) {
            args = ".\\venv\\Scripts\\activate && python main.py " + super.getTraining() + " " + super.getTesting() + " " + super.getAlg() + " " + super.getEpochs() + " " + super.getResponding() + " " + super.getSave();
        } else {
            args = "source ./venv/bin/activate && python3 main.py " + super.getTraining() + " " + super.getTesting() + " " + super.getAlg() + " " + super.getEpochs() + " " + super.getResponding() + " " + super.getSave();
        }

        System.out.println(args);

        Python py = new Python(args, super.getIsWindows(), super.getEpochs(), this.display, "train");
        this.current = py;
        py.execute();
    }

    public void predict() {
        try {
            FileWriter fw = new FileWriter("predict.txt");
            PrintWriter pw = new PrintWriter(fw);

            pw.print(super.getPrediction());

            fw.close();
            pw.close();

            String args;
            if (super.getIsWindows()) {
                args = ".\\venv\\Scripts\\activate && python predict.py " + super.getPath();
            } else {
                args = "source ./venv/bin/activate && python3 predict.py " + super.getPath().replace("\\", "/");
            }

            System.out.println(args);

            Python py = new Python(args, super.getIsWindows(), super.getEpochs(), this.display, "predict");
            this.current = py;
            py.execute();

        } catch (IOException err) {
            System.out.println(err);
        }
    }

    public void contBackend() throws IOException {
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Scanner sc = new Scanner(new File("results.txt"));

        String str = "";
        try {
            str = sc.nextLine();
        } catch (NoSuchElementException e) {
            str = "ERROR: an unknown error occured. Please try using another algorithm if possible.";
        }

        if (str.startsWith("ERROR")) {
            super.setError(true);
            super.setErrorText(str);
        } else if (str == null || str.trim().equals("")) {
            super.setError(true);
        } else {
            super.setOutput(str);
        }

        sc.close();

        this.erase();
    }

    public void erase() throws IOException {
        
        try {
            Thread.sleep(150);
        } catch (InterruptedException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        PrintWriter pw = new PrintWriter(new File("results.txt"));
        pw.print("");
        pw.close();
    }
}
