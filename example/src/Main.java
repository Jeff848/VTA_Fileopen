import java.awt.Desktop;  
import java.io.*; 
import java.util.Scanner; 
import java.nio.charset.StandardCharsets;   
import java.nio.file.*;
import java.util.*;   

class Main
{
    public static void main(String[] args) {

        try  
        {  
            File file=new File("demo.txt");   
            FileInputStream fis=new FileInputStream(file);     //opens a connection to an actual file 

            System.out.println("Hello world1");
            System.out.println("Hello world2");
            OpenFileExample1.test1(args);
            OpenFileExample2.test2(args);
            OpenFileExample3.test3(args);
            OpenFileExample4.test4(args);
            OpenFileExample5.test5(args);
            OpenFileExample6.test6(args);
        }
        catch(Exception e)  
        {  
        }
    }
}



// class OpenFileExample1   
// {  
//     public static void test1(String[] args)   
//     {  
//         try  
//         {  
//             //constructor of file class having file as argument  
//             File file = new File("C:\\demo\\demofile1.txt");   
//             if(!Desktop.isDesktopSupported())//check if Desktop is supported by Platform or not  
//             {  
//                 System.out.println("not supported");  
//                 return;  
//             }  
//             Desktop desktop = Desktop.getDesktop();  
//             if(file.exists())         //checks file exists or not  
//                 desktop.open(file);              //opens the specified file  
//             }   
//         catch(Exception e)  
//         {  
//             e.printStackTrace();  
//         }  
//     }  
// }  
  
// class OpenFileExample2  
// {     
//     public static void test2(String args[])  
//     {  
//         try  
//         {  
//             //constructor of file class having file as argument  
//             File file=new File("C:\\demo\\demofile2.txt");   
//             FileInputStream fis=new FileInputStream(file);     //opens a connection to an actual file  
//             System.out.println("file content: ");  
//             int r=0;  
//             while((r=fis.read())!=-1)  
//             {  
//             System.out.print((char)r);      //prints the content of the file  
//             }  
//         }  
//         catch(Exception e)  
//         {  
//             e.printStackTrace();  
//         }  
//     }  
// }

// class OpenFileExample3  
// {     
//     public static void test3(String args[])  
//     {  
//         try  
//         {  
//             //constructor of File class having file as argument  
//             File file=new File("C:\\demo\\demofile3.txt");   
//             //creates a buffer reader input stream  
//             BufferedReader br=new BufferedReader(new FileReader(file));  
//             System.out.println("file content: ");  
//             int r=0;  
//             while((r=br.read())!=-1)  
//             {  
//                 System.out.print((char)r);  
//             }  
//         }  
//         catch(Exception e)  
//         {  
//             e.printStackTrace();  
//         }  
//     }  
// }  

// class OpenFileExample4  
// {     
//     public static void test4(String args[])  
//     {  
//         try  
//         {  
//             //constructor of the File class having file as an argument  
//             FileReader fr = new FileReader("C:\\demo\\demofile4.txt");   
//             System.out.println("file content: ");  
//             int r=0;  
//             while((r=fr.read())!=-1)  
//             {  
//                 System.out.print((char)r);  //prints the content of the file   
//             }  
//         }  
//         catch(Exception e)  
//         {  
//             e.printStackTrace();  
//         }  
//     }  
// }  

 
// class OpenFileExample5  
// {   
//     public static void test5(String[] args)   
//     {   
//         try  
//         {  
//             File file=new File("C:\\demo\\demofile.txt");   
//             Scanner sc = new Scanner(file);     //file to be scanned  
//             while (sc.hasNextLine())        //returns true if and only if scanner has another token  
//                 System.out.println(sc.nextLine());    
//         }  
//         catch(Exception e)  
//         {  
//             e.printStackTrace();  
//         }  
//     }   
// }  

// class OpenFileExample6  
// {   
//     public static List<String> readFileInList(String fileName)   
//     {   
//         List<String> lines = Collections.emptyList();   
//         try  
//         {   
//             lines=Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);   
//         }   
//         catch (IOException e)   
//         {   
//             e.printStackTrace();   
//         }   
//         return lines;   
//     }   

//     public static void test6(String[] args)   
//     {   
//         List l = readFileInList("C:\\demo\\demofile.txt");   
//         Iterator<String> itr = l.iterator();    //access the elements  
//         while (itr.hasNext())       //returns true if and only if scanner has another token  
//             System.out.println(itr.next());      //prints the content of the file  
//     }   
// } 