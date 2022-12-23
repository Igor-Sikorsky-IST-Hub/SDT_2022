package org.example.client.pages.states;

import org.example.client.pages.pageService.RequestPage;
import org.example.client.pages.pageService.Page;

import java.util.Arrays;

public class TranslatingState implements DynamicPageState {
    private String translatedPage= new String("");
    private String pageData;
    private String newClassName;
    private String fileName;
    private String urlToJSPPage;
    private String path;

    public TranslatingState() {

    }

    @Override
    public void doAction(RequestPage page) {
       page.getStartPage().setTranslatedPage(translate(page.getStartPage()));
      page.changeState(new MakingSourceFileState());
    }
    private String translate(Page page) {


        this.urlToJSPPage = page.getPathToFile();
        this.fileName = urlToJSPPage.substring(urlToJSPPage.lastIndexOf('/') + 1, urlToJSPPage.indexOf("."));
        this.path = urlToJSPPage.substring(0, urlToJSPPage.lastIndexOf('/'));
        this.newClassName = path.replaceAll("/", "") + fileName.toUpperCase();
        this.pageData=page.getPageData();

        translatedPage = translatedPage + "package dynamicPage;\n" +
                "import java.io.*;" +
                "import java.io.FileNotFoundException;\n" +
                "import java.io.IOException;\n" +
                "import java.util.HashMap;\n"+
                "public class " + newClassName + " implements org.example.myServer.pages.GeneralInterfaceForTranslatedPages {\n" +
                "public " + newClassName + " (){}\n" +
                "public void toDo(HashMap<String,String> parameters) { \n" +
                " try (PrintWriter out = new PrintWriter(new FileWriter(\"" + this.path + "/" + this.fileName + ".html" + "\"))) {\n";

        Arrays.stream(pageData.split("%>")).forEach(x -> {
                    x = checkPostVariable(x);


                    if (x.indexOf("<%") == 2) {
                        if (x.indexOf("<%=") == 2) {
                            translatedPage = translatedPage + "out.write(String.valueOf(" + x.substring(5, x.length()) + "));\n";
                        } else {
                            translatedPage = translatedPage + " " + x.substring(4, x.length()) + " ";
                        }

                    } else {
                        String[] spliter = x.split("<%");
                        Arrays.stream(spliter[0].split("\n+|\r")).forEach(y -> {
                            translatedPage = translatedPage + "out.write(String.valueOf(\"" + checkQuotes(y) + "\"));\n";
                        });
                        if (spliter.length > 1) {
                            if (spliter[1].charAt(0) == '=') {
                                translatedPage = translatedPage + "out.write(String.valueOf(" + spliter[1].substring(1) + "));\n";
                            } else {
                                translatedPage = translatedPage + " " + spliter[1] + " ";
                            }
                        }

                    }
                }
        );
        translatedPage = translatedPage + "} catch (Exception e) {  throw new RuntimeException(e);}\n";
        translatedPage = translatedPage + "}\n}";
        return translatedPage;
    }
    private String checkPostVariable(String string) {
        int expressionStart = string.indexOf("request.getParameter");
        if (expressionStart == -1) return string;
        int newExpressionStart = expressionStart + 22;
        int newExpressionEnd;
        int expressionEnd = newExpressionStart;
        while (string.charAt(expressionEnd) != ')') {
            expressionEnd++;
        }
        newExpressionEnd= expressionEnd-1;
        expressionEnd++;
        String expression = string.substring(expressionStart, expressionEnd);
        String newExpression =string.substring(newExpressionStart, newExpressionEnd);
            return string.replace(expression,"parameters.get( \"" +
                    newExpression.replaceAll("\\W","") + "\")" );
    }

    private String checkQuotes(String string) {
        String lineWithCorrectQuotes = "";
        for (char ch : string.toCharArray()) {
            if (ch == '"') lineWithCorrectQuotes = lineWithCorrectQuotes + "\\\"";
            else lineWithCorrectQuotes = lineWithCorrectQuotes + ch;
        }
        return lineWithCorrectQuotes;

    }

}
