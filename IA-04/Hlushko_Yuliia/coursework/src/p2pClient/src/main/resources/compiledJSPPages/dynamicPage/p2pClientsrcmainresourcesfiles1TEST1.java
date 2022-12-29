package dynamicPage;
import java.io.*;import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
public class p2pClientsrcmainresourcesfiles1TEST1 implements org.example.client.pages.GeneralInterfaceForTranslatedPages {
public p2pClientsrcmainresourcesfiles1TEST1 (){}
public String toDo(HashMap<String,String> parameters) { 
 String readyData =  new String(""); 
readyData = readyData + String.valueOf("<!DOCTYPE html><html lang=\"en\"><head>");
readyData = readyData + String.valueOf("");
readyData = readyData + String.valueOf("                <meta charset=\"UTF-8\"><title>Title</title></head><body>");
readyData = readyData + String.valueOf("");
readyData = readyData + String.valueOf("                 <center><h3>Page has been visited ");
readyData = readyData + String.valueOf( parameters.get( "numberOfVisits"));
readyData = readyData + String.valueOf(" times</h3><br>");
readyData = readyData + String.valueOf("");
readyData = readyData + String.valueOf("                 <br>");
readyData = readyData + String.valueOf( "Name : ");
readyData = readyData + String.valueOf("");
readyData = readyData + String.valueOf( parameters.get( "name"));
readyData = readyData + String.valueOf("");
readyData = readyData + String.valueOf("");
readyData = readyData + String.valueOf("                 <br>");
readyData = readyData + String.valueOf( "Message : ");
readyData = readyData + String.valueOf("");
readyData = readyData + String.valueOf( parameters.get( "message"));
readyData = readyData + String.valueOf("");
readyData = readyData + String.valueOf("");
readyData = readyData + String.valueOf("                 </center></body></html>");
 return readyData ;
}
}