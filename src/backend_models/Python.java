package backend_models;

import frontend_viewcontroller.MainViewDisplay;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.SwingWorker;

/**
 *
 * @author sam
 */
public class Python extends SwingWorker<Integer, String> {

    private int progress, epochs;
    private String args, type;
    private boolean isWindows;
    private MainViewDisplay display;
    private int step;

    public Python(String args, boolean isWindows, int epochs, MainViewDisplay display, String type) {
        this.args = args;
        this.isWindows = isWindows;
        this.display = display;
        this.epochs = epochs;
        this.step = 1;
        this.type = type;
    }

    public void setCurrentProgress(int progress) {
        this.progress = progress;
    }

    public int getCurrentProgress() {
        return this.progress;
    }

    @Override
    protected Integer doInBackground() throws Exception {
        
        String shell;
        String dir;
        if (this.isWindows) {
            shell = "cmd.exe";
            dir = "/c";
        } else {
            shell = "bash";
            dir = "-c";
        }
        try {
            String s = null;

            this.setCurrentProgress(0);

            ProcessBuilder builder = new ProcessBuilder(shell, dir, this.args);
            builder.redirectErrorStream(true);
            Process p = builder.start();

            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

            while ((s = input.readLine()) != null) {
                publish(s + "");
            }

            p.getInputStream().close();
            p.getOutputStream().close();
            p.getErrorStream().close();
            p.destroy();

        } catch (IOException err) {
            System.out.println(err);
        }
        return 0;
    }

    @Override
    protected void process(java.util.List<String> messages) {
        if (this.type.equals("train")) {
            for (String message : messages) {
                display.updateProgress(message);
                if (message.contains("Epoch " + step + "/" + this.epochs) && this.epochs != 0) {
                    double progress = (double) step / (double) this.epochs;
                    int realProg = (int) (Math.round(progress * 100));
                    this.setCurrentProgress(realProg);
                    this.display.updateProgressBar(realProg);
                    this.step++;
                }
            }
        } else if (this.type.equals("setup")) {
            int start = 15;
            for (String message : messages) {
                display.updateProgress(message);
                if (message.contains("pip in")) {
                    this.display.updateProgressBar(start);
                } else if (message.contains(this.step + "))") || message.contains("Collecting")) {
                    double progress = (double)this.step / 58.0;
                    this.display.updateProgressBar(start + (int)Math.round(progress * 85));
                    this.step++;
                }
            }
        } else if (this.type.equals("predict")) {
            for (String message : messages) {
                display.updateProgress(message);
                if (message.contains("Collection of input completed")) {
                    this.display.updateProgressBar(33);
                } else if (message.contains("Preprocessing of input completed")) {
                    this.display.updateProgressBar(66);
                }
            }
        }
    }

    @Override
    protected void done() {
        this.display.updateProgress("\n\nThe operation has completed.");
        this.display.updateProgressBar(100);
    }
}
