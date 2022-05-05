import java.awt.Desktop;  
import java.io.*; 
import java.util.Scanner; 
import java.nio.charset.StandardCharsets;   
import java.nio.file.*;
import java.util.*;      

class OpenFileExample5  
{   
    public static void test5(String[] args)   
    {   
        try  
        {  
            File file=new File("demofile5.txt");   
            Scanner sc = new Scanner(file);     //file to be scanned  
            while (sc.hasNextLine())        //returns true if and only if scanner has another token  
                System.out.println(sc.nextLine());    
        }  
        catch(Exception e)  
        {  
            e.printStackTrace();  
        }  
    }   
}  