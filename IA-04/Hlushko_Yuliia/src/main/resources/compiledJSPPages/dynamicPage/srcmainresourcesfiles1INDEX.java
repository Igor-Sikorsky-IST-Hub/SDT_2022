package dynamicPage;
import java.io.*;
import java.util.HashMap;
public class srcmainresourcesfiles1INDEX implements org.example.myServer.pages.GeneralInterfaceForTranslatedPages {
public srcmainresourcesfiles1INDEX (){}
public void toDo(HashMap<String,String> parameters) { 
 try (PrintWriter out = new PrintWriter(new FileWriter("src/main/resources/files/1/index.html"))) {
out.write(String.valueOf(""));
} catch (Exception e) {  throw new RuntimeException(e);}
}
}