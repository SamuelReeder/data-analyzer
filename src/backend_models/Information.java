/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend_models;

import java.text.NumberFormat;

/**
 *
 * @author samue
 */
public class Information {
    
    private String training, testing, responding, path, prediction, alg, loss, accuracy, errorText, savedName;
        
    private int epochs; 
    
    private boolean isWindows, error = false;
    
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
    
    public void setLoss(String loss) {
        NumberFormat fmt = NumberFormat.getPercentInstance();
        fmt.setMaximumFractionDigits(1);
        this.loss = fmt.format(Double.parseDouble(loss));
    }
    
    public void setAccuracy(String accuracy) {
        NumberFormat fmt = NumberFormat.getPercentInstance();
        fmt.setMaximumFractionDigits(1);
        this.accuracy = fmt.format(Double.parseDouble(accuracy));
    }
    
    public void setEpochs(String epochs) {
        try {
            this.epochs = Integer.parseInt(epochs);
        } catch (NumberFormatException e) {
            this.epochs= 10;
        }
    }
    
    public void setError(boolean err) {
        this.error = err;
    }
    
    public void setErrorText(String errText) {
        this.errorText = errText;
    }
    
    public void setSave(String name) {
        if (name.trim() == null || name.trim() == "") {
            this.savedName = "my_model";
        } else {
            this.savedName = name;
        }
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
    
    public String getLoss() {
        return this.loss;
    }
    
    public String getAccuracy() {
        return this.accuracy;
    }
    
    public int getEpochs() {
        return this.epochs;
    }
    
    public boolean getError() {
        return this.error;
    }
    
    public String getErrorText(){
        return this.errorText;
    }
    
    public String getSave(){
        return this.savedName;
    }
}
