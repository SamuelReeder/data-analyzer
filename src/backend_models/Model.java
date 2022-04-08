/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend_models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author sam
 */
public class Model {

    public String path;
    public String fileContent;
    public String consoleOutput;

    public Model(String path) throws IOException {
        this.path = path;
        this.fileContent = Model.importModel(this.path);
    }
    
    public void trainModel() {
        String s = null;

        try  {
            ProcessBuilder builder = new ProcessBuilder(
            "cmd.exe", "/c", ".\\venv\\Scripts\\activate && python main.py");
            builder.redirectErrorStream(true);
            Process p = builder.start();
            
            System.out.println(p.info());
            
            BufferedReader input = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));

            while ((s = input.readLine()) != null) {
                System.out.println(s);
            }
        }
        catch (IOException err) {
            System.out.println(err);
        }
//      Calls to python to train a machine learning
//      model, and provides necessary parameters.

//      Will append console output to a text field 
//      so the user can observe the training process.
    }
    
    public void predict() {
//      Uses the saved model to predict an outcome 
//      with user-inputted data.
    }
    
    public void fetchModels() {
//      Identify if the user already has models 
//      pretrained in this directory that they can 
//      import.
    }
    
//    The above methods are very general, I would like to implement others that have more specific use cases too
    
    public void saveModel(String path) throws IOException {
//        Saves model by calling a python function
    }

    public static String importModel(String path) throws IOException {
//        Imports a specific model to be used
        return "";
    }
}
