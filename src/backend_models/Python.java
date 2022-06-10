/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend_models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author samue
 */
public class Python {
    
    private static String responsive;
    
    private static int progress;
    
    public static void run(String args, boolean isWindows, int epochs) {
        
        String shell;
        String dir;
        if (isWindows) {
            shell = "cmd.exe";
            dir = "/c";
        } else {
            shell = "bash";
            dir = "-c";
        }
        try  {
            String s = null;
            
            Python.setProgress(0);
            
            ProcessBuilder builder = new ProcessBuilder(shell, dir, args);
            builder.redirectErrorStream(true);
            Process p = builder.start();
                        
            BufferedReader input = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));
            
            int epoch = 1;
            String current = "";
            while ((s = input.readLine()) != null) {
                System.out.println(s);
                current += s;
                if (current.contains("Epoch " + epoch + "/" + epochs) && epochs != 0) {
//                    System.out.println("mitght be working" + epoch);
                    double progress = (double)epoch / (double)epochs;
                    System.out.println(progress + " " + epoch + " " + epochs);
                    Python.setProgress(progress);
                    epoch++;
                }
            }
            
            System.out.println("The operation has been completed");
        }
        catch (IOException err) {
            System.out.println(err);
        }
    }
    
    public static void setResponsive(String var) {
        Python.responsive = var;
    }
    
    public static String getResponsive() {
        return Python.responsive;
    }
    
    public static void setProgress(double progress) {
        Python.progress = (int)(Math.round(progress * 100));
        System.out.println("set " + Python.getProgress());
    }
    
    public static int getProgress() {
        return Python.progress;
    }
}
