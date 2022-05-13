/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend_models;

/**
 *
 * @author samue
 */
public class Information {
    
    private String training, testing, responding, path, prediction, alg;
    
    private boolean isWindows;
    
    public Information() {
       if (System.getProperty("os.name").startsWith("Windows")) {
           this.isWindows = true;
       } else {
           this.isWindows = false;
       }
    }
    
    public void setTraining(String train) {
        this.training = train;
    }
    
    public void setTesting(String test) {
        this.testing = test;
    }
    
    public void setResponding(String responsive) {
        this.responding = responsive;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public void setPrediction(String pred) {
        this.prediction = pred;
    }
    
    public void setAlg(String alg) {
        this.alg = alg;
    }
    
    public String getTraining() {
        return this.training;
    }
    
    public String getTesting() {
        return this.testing;
    }
    
    public String getResponding() {
        return this.responding;
    }
    
    public String getPath() {
        return this.path;
    }
    
    public String getPrediction() {
        return this.prediction;
    }
    
    public boolean getIsWindows() {
        return this.isWindows;
    }
    
    public String getAlg() {
        return this.alg;
    }
}
