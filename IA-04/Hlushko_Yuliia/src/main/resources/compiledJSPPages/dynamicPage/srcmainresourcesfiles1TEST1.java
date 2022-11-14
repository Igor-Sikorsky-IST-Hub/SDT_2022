package dynamicPage;
import java.io.*;import java.io.FileNotFoundException;
import java.io.IOException;
public class srcmainresourcesfiles1TEST1 implements org.example.myServer.dynamicPage.GeneralInterfaceForTranslatedPages {
public srcmainresourcesfiles1TEST1 (){}
public void toDo() { 
 try (PrintWriter out = new PrintWriter(new FileWriter("src/main/resources/files/1/test1.html"))) {
out.write(String.valueOf("<!DOCTYPE html>"));
out.write(String.valueOf(""));
out.write(String.valueOf("<html lang=\"en\">"));
out.write(String.valueOf(""));
out.write(String.valueOf("<head>"));
out.write(String.valueOf(""));
out.write(String.valueOf("    <meta charset=\"UTF-8\">"));
out.write(String.valueOf(""));
out.write(String.valueOf("    <title>Title</title>"));
out.write(String.valueOf(""));
out.write(String.valueOf("</head>"));
out.write(String.valueOf(""));
out.write(String.valueOf("<body>"));
     out.write("Name : "); out.write(String.valueOf( "Yulia"));
out.write(String.valueOf(""));
out.write(String.valueOf(""));
out.write(String.valueOf("<br>"));
out.write(String.valueOf(""));
out.write(String.valueOf("<br>"));
  for (int i=0;i<5;i++) out.write(String.valueOf( i));
out.write(String.valueOf(""));
out.write(String.valueOf(""));
out.write(String.valueOf("<br>"));
out.write(String.valueOf(""));
out.write(String.valueOf("<br>"));
     out.write("Message : "); out.write(String.valueOf( "Hello"));
out.write(String.valueOf(""));
out.write(String.valueOf(""));
out.write(String.valueOf(""));
out.write(String.valueOf(""));
out.write(String.valueOf("<center>"));
out.write(String.valueOf(""));
out.write(String.valueOf("<div>"));
out.write(String.valueOf(""));
out.write(String.valueOf("<h3>Fill in the forms</h3>"));
out.write(String.valueOf(""));
out.write(String.valueOf("<form method=\"POST\" >"));
out.write(String.valueOf(""));
out.write(String.valueOf("<label for=\"name\">Name</label>"));
out.write(String.valueOf(""));
out.write(String.valueOf("<input type=\"text\" name=\"name\" id=\"name\" placeholder=\"Write the name\" >"));
out.write(String.valueOf(""));
out.write(String.valueOf("<br>"));
out.write(String.valueOf(""));
out.write(String.valueOf("<label for=\"message\">Message</label>"));
out.write(String.valueOf(""));
out.write(String.valueOf("<textarea name=\"message\"  id=\"message\"  placeholder=\"Write the message\"></textarea>"));
out.write(String.valueOf(""));
out.write(String.valueOf("<br>"));
out.write(String.valueOf(""));
out.write(String.valueOf("<input type=\"submit\"  value=\"Send\" >"));
out.write(String.valueOf(""));
out.write(String.valueOf("</form>"));
out.write(String.valueOf(""));
out.write(String.valueOf("</div>"));
out.write(String.valueOf(""));
out.write(String.valueOf("</center>"));
out.write(String.valueOf(""));
out.write(String.valueOf("</body>"));
out.write(String.valueOf(""));
out.write(String.valueOf("</html>"));
} catch (Exception e) {  throw new RuntimeException(e);}
}
}