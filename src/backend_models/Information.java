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
    
    public String training, testing, responding, path;
    
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
}
