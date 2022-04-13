/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend_models;

//import java.awt.List;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 *
 * @author samue
 */
public class Console extends SwingWorker<List<Integer>, Integer> {

    Console(JTextArea textArea, int numbersToFind) {
        //initialize
    }

    @Override
    public List<Integer> doInBackground() {
        List<Integer> numbers = new ArrayList<Integer>(25);
        while (!enough && !isCancelled()) {
            number = nextPrimeNumber();
            numbers.add(number);
            publish(number);
            setProgress(100 * numbers.size() / numbersToFind);
        }

        return numbers;
    }

    @Override
    protected void process(List<Integer> chunks) {
        for (int number : chunks) {
            textArea.append(number + "\n");
        }
    }
}
