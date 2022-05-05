import java.awt.Desktop;  
import java.io.*; 
import java.util.Scanner; 
import java.nio.charset.StandardCharsets;   
import java.nio.file.*;
import java.util.*;      

class OpenFileExample6  
{   
    public static List<String> readFileInList(String fileName)   
    {   
        List<String> lines = Collections.emptyList();   
        try  
        {   
            lines=Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);   
        }   
        catch (IOException e)   
        {   
            e.printStackTrace();   
        }   
        return lines;   
    }   

    public static void test6(String[] args)   
    {   
        List l = readFileInList("demofile6.txt");   
        Iterator<String> itr = l.iterator();    //access the elements  
        while (itr.hasNext())       //returns true if and only if scanner has another token  
            System.out.println(itr.next());      //prints the content of the file  
    }   
}  