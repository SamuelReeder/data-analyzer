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
public class EnDecrypter {
    
    public static char[] encrypt(char[] plainText){
        char[] cipherText = new char[plainText.length]; 
        for (int i = 0; i < plainText.length; i++) {
            cipherText[i] = (char) ((int)plainText[i] + 13); 
        }
        return cipherText;
    }
    
    public static char[] decrypt(char[] cipherText){
        char[] plainText = new char[cipherText.length]; 
        for (int i = 0; i < cipherText.length; i++) {
            plainText[i] = (char) ((int)cipherText[i] - 13); 
        }
        return plainText;
    }  
}
