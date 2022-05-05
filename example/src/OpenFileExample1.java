import java.awt.Desktop;  
import java.io.*; 
import java.util.Scanner; 
import java.nio.charset.StandardCharsets;   
import java.nio.file.*;
import java.util.*;      

class OpenFileExample1   
{  
    public static void test1(String[] args)   
    {  
        try  
        {  
            //constructor of file class having file as argument  
            File file = new File("demofile1.txt");   
            if(!Desktop.isDesktopSupported())//check if Desktop is supported by Platform or not  
            {  
                System.out.println("not supported");  
                return;  
            }  
            Desktop desktop = Desktop.getDesktop();  
            if(file.exists())         //checks file exists or not  
                desktop.open(file);              //opens the specified file  
            }   
        catch(Exception e)  
        {  
            e.printStackTrace();  
        }  
    }  

    public static void main(String[] args) {
        System.out.println("Hello world");
        OpenFileExample1.test1(args);
        OpenFileExample2.test2(args);
        OpenFileExample3.test3(args);
        OpenFileExample4.test4(args);
        OpenFileExample5.test5(args);
        OpenFileExample6.test6(args);
    }
}  