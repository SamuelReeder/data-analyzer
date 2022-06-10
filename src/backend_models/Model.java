/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend_models;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author sam
 */
public class Model extends Information {

    public String path, consoleOutput, output, prediction, errorText;
    public String[] vars;

    public Model(String path) throws IOException {
        this.path = path;
        this.consoleOutput = "";
    }

    public void trainModel() throws IOException {

        String args;
        if (super.getIsWindows()) {
            args = ".\\venv\\Scripts\\activate && python main.py " + super.getTraining() + " " + super.getTesting() + " " + super.getAlg() + " " + super.getEpochs() + " " + super.getResponding();
        } else {
            args = "source ./venv/bin/activate && python3 main.py " + super.getTraining() + " " + super.getTesting() + " " + super.getAlg() + " " + super.getEpochs() + " " + super.getResponding();
        } 
        
        System.out.println(args);
        
        Python.setResponsive(super.getResponding());
        
        Python.run(args, super.getIsWindows(), super.getEpochs());
        
        File file = new File("results.txt");
        Scanner sc = new Scanner(file);
        
        String str = sc.nextLine();
        
        System.out.println(str);
        if (str.startsWith("ERROR")) {
            super.setError(true);
            super.setErrorText(str);
        } else if (str == null || str.trim().equals("")) {
            super.setError(true);
        } else {
            super.setLoss(str);
            super.setAccuracy(sc.nextLine());
        }
        
        sc.close();
        
//        PrintWriter pw = new PrintWriter(file);
//        pw.print("");
//        pw.close();
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

        Python.run(args, super.getIsWindows(), super.getEpochs());
        } catch (IOException err) {
            System.out.println(err);
        }
    }

    public void fetchModels() {
        
    }

    public void setVars(String[] vars) {
//      Sets the responding variables of the data
    }

    public String[] getVars() {
//      A method to access relevant variables   
        return this.vars;
    }

//    The above methods are very general, I would like to implement others that have more specific use cases too
    public void saveModel(String path) throws IOException {
//      Saves model by calling a python function
    }

    public static String importModel(String path) throws IOException {
//      Imports a specific model to be used
        return "";
    }
    
        
    public String getResponsive() {
        return Python.getResponsive();
    }
}
