package frontend_viewcontroller;

import backend_models.*;

/**
 * This class and its constructor is used when your program begins running
 * in order to initialize and set up the backend, i.e. the business logic
 * behind the scenes that power the core of your solution to the problem.
 * 
 * There should be no code here directly about painting graphics on the screen,
 * and no code here for directly handling user interaction.
 * 
 * All user interface related code must go in the other frontend classes.
 * 
 * There should also be no code here directly about modeling the problem or 
 * situation your program solves.
 * 
 * All problem or situation modeling related code must go in the backend classes
 * 
 * @author cheng
 */
public class BackendModelSetup {

    Model theTextFile;

    public BackendModelSetup() {
        this.theTextFile = null;
    }
}
