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
        // try  
        // {  
        //     File file=new File("demo.txt");   
        //     FileInputStream fis=new FileInputStream(file);     //opens a connection to an actual file 

            // System.out.println("Hello world1");
            // System.out.println("Hello world2");
            // OpenFileExample1.test1(args);
            // OpenFileExample2.test2(args);
            // OpenFileExample3.test3(args);
            // OpenFileExample4.test4(args);
            // OpenFileExample5.test5(args);
            // OpenFileExample6.test6(args);
        // }
        // catch(Exception e)  
        // {  
        // }
        try  
        {  
            //constructor of file class having file as argument  
            File file=new File("demofile2.txt");   
            FileInputStream fis=new FileInputStream(file);     //opens a connection to an actual file  
            System.out.println("file content: ");  
            // int r=0;  
            // while((r=fis.read())!=-1)  
            // {  
            //     System.out.print((char)r);      //prints the content of the file  
            // }  
            // fis.close();
        }  
        catch(Exception e)  
        {  
            e.printStackTrace();  
        }  
    }
}  