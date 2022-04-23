/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frontend_viewcontroller;

import java.awt.*;
import java.io.*;
import javax.swing.*;

/**
 *
 * @author samue
 */
public class InfoViewDisplay extends JFrame {
       
        JLabel infoTitle;
        
        JTextArea infoTextArea;
        
    public InfoViewDisplay() {
        this.initComponents();
    }
    
    private void initComponents() {
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        
        Container infoDisplayPane = this.getContentPane();
        infoDisplayPane.setLayout(new GridBagLayout());
        
        GridBagConstraints c;
        
        this.setMinimumSize(new Dimension(400, 400));
        
        this.infoTitle = new JLabel();
        this.infoTitle.setText("Info"); 
        this.infoTitle.setFont(new Font("Serif", Font.BOLD, 30));
        
        this.infoTextArea = new JTextArea();
        this.infoTextArea.setSize(500, 500);
        this.infoTextArea.setLineWrap(true);
        this.infoTextArea.setEditable(false);
        this.infoTextArea.setWrapStyleWord(rootPaneCheckingEnabled);
        
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(20, 20, 20, 20);
        infoDisplayPane.add(this.infoTitle, c);
        
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.ipady = 50;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        infoDisplayPane.add(this.infoTextArea, c);

        this.pack();        

    }

}
