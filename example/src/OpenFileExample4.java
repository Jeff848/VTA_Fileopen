import java.awt.Desktop;  
import java.io.*; 
import java.util.Scanner; 
import java.nio.charset.StandardCharsets;   
import java.nio.file.*;
import java.util.*;      

class OpenFileExample4  
{     
    public static void test4(String args[])  
    {  
        try  
        {  
            //constructor of the File class having file as an argument  
            FileReader fr = new FileReader("demofile4.txt");   
            System.out.println("file content: ");  
            int r=0;  
            while((r=fr.read())!=-1)  
            {  
                System.out.print((char)r);  //prints the content of the file   
            }  
        }  
        catch(Exception e)  
        {  
            e.printStackTrace();  
        }  
    }  
}  