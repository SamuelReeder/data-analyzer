/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the_app;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 *
 * @author samue
 */
public class Tester {

    public static void main(String args[]) throws FileNotFoundException, IOException {

        boolean running = true;
//        BufferedInputStream reader = new BufferedInputStream(new FileInputStream("out.txt"));
        BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream("data.json"), StandardCharsets.UTF_8));

        while (running) {
            if (r.read() > 0) {
//                System.out.print((char) reader.read());
                System.out.println(r.lines().collect(Collectors.joining(",")));
                System.out.print("l");
            } else {
                try {
                    sleep(3000);
                } catch (InterruptedException ex) {
                    running = false;
                }
            }
        }
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//            String line;
//            while(true) {
//                line = reader.readLine(); // blocks until next line available
//                // do whatever You want with line
//            }
    }

}
