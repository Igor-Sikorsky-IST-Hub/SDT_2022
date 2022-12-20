package dynamicPage;
import java.io.*;import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
public class p2pClientsrcmainresourcesfiles1TEST1 implements org.example.client.pages.GeneralInterfaceForTranslatedPages {
public p2pClientsrcmainresourcesfiles1TEST1 (){}
public String toDo(HashMap<String,String> parameters) { 
 String readyData =  new String(""); 
readyData = readyData + String.valueOf("<!DOCTYPE html>");
readyData = readyData + String.valueOf("<html lang=\"en\">");
readyData = readyData + String.valueOf("<head>");
readyData = readyData + String.valueOf("    <meta charset=\"UTF-8\">");
readyData = readyData + String.valueOf("    <title>Title</title>");
readyData = readyData + String.valueOf("</head>");
readyData = readyData + String.valueOf("<body>");
readyData = readyData + String.valueOf("<center>");
readyData = readyData + String.valueOf("<h3>Page has been visited ");
readyData = readyData + String.valueOf( parameters.get( "numberOfVisits"));
readyData = readyData + String.valueOf(" times</h3><br>");
readyData = readyData + String.valueOf("<br>");
readyData = readyData + String.valueOf( "Name : ");
readyData = readyData + String.valueOf( parameters.get( "name"));
readyData = readyData + String.valueOf("");
readyData = readyData + String.valueOf("<br>");
readyData = readyData + String.valueOf("<br>");
  for (int i=0;i<5;i++) readyData = readyData + String.valueOf( i);
readyData = readyData + String.valueOf("");
readyData = readyData + String.valueOf("<br>");
readyData = readyData + String.valueOf("<br>");
readyData = readyData + String.valueOf( "Message : ");
readyData = readyData + String.valueOf( parameters.get( "message"));
readyData = readyData + String.valueOf("");
readyData = readyData + String.valueOf("</center>");
readyData = readyData + String.valueOf("</body>");
readyData = readyData + String.valueOf("</html>");
 return readyData ;
}
}