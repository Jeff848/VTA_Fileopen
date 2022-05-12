import java.awt.Desktop;  
import java.io.*; 
import java.util.Scanner; 
import java.nio.charset.StandardCharsets;   
import java.nio.file.*;
import java.util.*;   

class OpenFileExample2  
{     
    public static void test2(String args[])  
    {  
        try  
        {  
            //constructor of file class having file as argument  
            File file=new File("demofile2.txt");   
            FileInputStream fis=new FileInputStream(file);     //opens a connection to an actual file  
            System.out.println("file content: ");  
            int r=0;  
            while((r=fis.read())!=-1)  
            {  
            System.out.print((char)r);      //prints the content of the file  
            }
            fis.close();  
        }  
        catch(Exception e)  
        {  
            e.printStackTrace();  
        }  
    }  
}