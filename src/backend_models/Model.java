/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend_models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 *
 * @author sam
 */
public class Model {

    public String path;
    public String consoleOutput;
    public String[] vars;
    public BufferedReader console;
    public String output;
    public String prediction;

    public Model(String path) throws IOException {
        this.path = path;
        this.consoleOutput = "";
//        this.console = 
    }
    
    public void trainModel() {
        String s = null;

        try  {
            ProcessBuilder builder = new ProcessBuilder(
            "cmd.exe", "/c", ".\\venv\\Scripts\\activate && python main.py");
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
            
            Files.write(Path.of("predict.txt"), Arrays.asList(this.prediction.split(",")));
        
            String s = null;
            
            ProcessBuilder builder = new ProcessBuilder(
            "cmd.exe", "/c", ".\\venv\\Scripts\\activate && python predict.py");
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
//      Identify if the user already has models 
//      pretrained in this directory that they can 
//      import.
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
