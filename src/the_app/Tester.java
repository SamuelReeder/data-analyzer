/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the_app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author samue
 */
public class Tester {

    public static void main(String args[]) throws FileNotFoundException {

//        boolean running = true;
//        BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream("data.json"), StandardCharsets.UTF_8));
//
//        while (running) {
//            if (r.read() > 0) {
//                System.out.println(r.lines().collect(Collectors.joining(",")));
//                System.out.print("l");
//            } else {
//                try {
//                    sleep(3000);
//                } catch (InterruptedException ex) {
//                    running = false;
//                }
//            }
//        }

//        Scanner sf = new Scanner(new File("requirements.txt"));
//        System.out.println(sf.nextLine());

        
        String s = null;

        try  {
            ProcessBuilder builder = new ProcessBuilder(
            "cmd.exe", "/c", ".\\venv\\Scripts\\activate && python main.py");
            builder.redirectErrorStream(true);
            Process proc = builder.start();
            
            System.out.println(proc.info());
            
            BufferedReader stdInput = new BufferedReader(new 
                 InputStreamReader(proc.getInputStream()));

            BufferedReader stdError = new BufferedReader(new 
                 InputStreamReader(proc.getErrorStream()));

            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            
            System.exit(0);
        }
        catch (IOException e) {
            System.out.println(e);
            System.exit(-1);
        }
    }

}
