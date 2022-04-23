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
    
    public static void run(String args, boolean isWindows) {
        
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
            
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", args);
            builder.redirectErrorStream(true);
            Process p = builder.start();
                        
            BufferedReader input = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));
            
            while ((s = input.readLine()) != null) {
                System.out.println(s);
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
}
