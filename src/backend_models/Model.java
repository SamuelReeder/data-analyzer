/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend_models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sam
 */
public class Model extends Information {

    public String path;
    public String consoleOutput;
    public String[] vars;
    public String output;
    public String prediction;
    
    public Model(String path) throws IOException {
        this.path = path;
        this.consoleOutput = "";
    }
    
    public void setup() {
         try  {
            String s = null;
            
            ProcessBuilder builder = new ProcessBuilder(
            "cmd.exe", "/c", "python -m venv --system-site-packages .\\venv && .\\venv\\Scripts\\activate && pip install --upgrade pip && pip install -r requirements.txt");
            builder.redirectErrorStream(true);
            Process p = builder.start();
                        
            BufferedReader input = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));
            
            while ((s = input.readLine()) != null) {
                this.consoleOutput += s;
                System.out.println(s);
            }
        }
        catch (IOException err) {
            System.out.println(err);
        }
    }
    
    public void trainModel() {
        try  {
            String s = null;
            
            ProcessBuilder builder = new ProcessBuilder(
            "cmd.exe", "/c", ".\\venv\\Scripts\\activate && python main.py " + super.getTraining() + " " + super.getTesting() + " " + super.getResponding());
            builder.redirectErrorStream(true);
            Process p = builder.start();
                        
            BufferedReader input = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));
            
            while ((s = input.readLine()) != null) {
                this.consoleOutput += s;
                System.out.println(s);
            }
        }
        catch (IOException err) {
            System.out.println(err);
        }
    }
    
    public void predict() {
        try  {
            
            FileWriter fw = new FileWriter("predict.txt");
            PrintWriter pw = new PrintWriter(fw);
            
            pw.print(super.getPrediction());
            
            fw.close();
            pw.close();
            
            String s = null;
            
            ProcessBuilder builder = new ProcessBuilder(
            "cmd.exe", "/c", ".\\venv\\Scripts\\activate && python predict.py " + super.getPath());
            builder.redirectErrorStream(true);
            Process p = builder.start();
                        
            BufferedReader input = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));
            
            while ((s = input.readLine()) != null) {
                this.consoleOutput += s;
                System.out.println(s);
            }

        } catch (IOException err) {
            System.out.println(err);
        }
    }
    
    public void fetchModels() {
        Map<String, File> models = new HashMap<String, File>();
        File modelDir = new File("/models");
        for (File file : modelDir.listFiles()) {
            if (file.isDirectory()) {
                models.put(file.getName(), file);
            }
        }
        
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
}
