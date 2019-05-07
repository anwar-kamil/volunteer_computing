package com.app.clientapp;

import java.io.*;
import java.util.Scanner;


public class Filing {

    int read_clientid_from_file(String filename) throws FileNotFoundException{
        
        Scanner input = new Scanner (new File(filename));
        int i =0;
     while(input.hasNextInt())  {
         //System.out.println(input.nextInt());
           i = input.nextInt();
         if ( isNumeric( i ) ) {
                   break;
              }
     }  
      
    input.close();
    return i; 
    }
    
    void write_to_file(String filename,int b) throws FileNotFoundException{
    
         OutputStream os = new FileOutputStream(filename);
             try {
                os.write(b);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } // writes the bytes
        
            
            try {
            os.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    } 
    
    
    
    public static boolean isNumeric(int str)  
    {  
      try  
      {  
        int d = (str);  
      }  
      catch(NumberFormatException nfe)  
      {  
        return false;  
      }  
      return true;  
    }
    
}