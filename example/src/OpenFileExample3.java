import java.awt.Desktop;  
import java.io.*; 
import java.util.Scanner; 
import java.nio.charset.StandardCharsets;   
import java.nio.file.*;
import java.util.*;      

class OpenFileExample3  
{     
    public static void test3(String args[])  
    {  
        try  
        {  
            //constructor of File class having file as argument  
            File file=new File("demofile3.txt");   
            //creates a buffer reader input stream  
            BufferedReader br=new BufferedReader(new FileReader(file));  
            System.out.println("file content: ");  
            int r=0;  
            while((r=br.read())!=-1)  
            {  
                System.out.print((char)r);  
            }  
        }  
        catch(Exception e)  
        {  
            e.printStackTrace();  
        }  
    }  
} 