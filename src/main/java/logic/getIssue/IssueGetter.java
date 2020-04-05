package logic.getIssue;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import view.Inputter;
import view.Printer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class IssueGetter {
    private static Scanner scanner;

    public static List<String> getIssuesOfProject() throws IOException {
        List<String> info = new ArrayList<>();

        Printer.print("Please enter url of tracker:\t");
        String urlOfProject = Inputter.input();

        Printer.print("Please short name of project:\t");
        String shtNameOfPrj = Inputter.input();

        Printer.print("Enter permament access token");
        String accessToken = Inputter.input();

        info.add(urlOfProject);
        info.add(accessToken);


        StringBuilder fullUrl = new StringBuilder();

        fullUrl.append(urlOfProject);
        fullUrl.replace(fullUrl.indexOf(" "), fullUrl.indexOf(" "), "/api/issues?fields=idReadable,summary,project&query=");
        fullUrl.insert(fullUrl.length() - 1, shtNameOfPrj);

        //HttpGet request = new HttpGet("https://tryloadtest.myjetbrains.com/youtrack/api/issues?fields=idReadable,summary,project&query=TPT))");
        //HttpGet request = new HttpGet(urlOfProject+"/api/issues?fields=idReadable,summary,project&query=" + shtNameOfPrj);
        HttpGet request = new HttpGet(fullUrl.toString().trim());
        //add request headers
        request.addHeader("accept", "application/json");
        request.addHeader("Cache-Control", "no-cache");
        request.addHeader("Content-Type", "application/json");
        //request.setHeader("Authorization", "Bearer " + "perm:cm9vdA==.NDQtMA==.4Mw0QQH3AX7Vsk4WpWkPPAhNwQQKs6");
        request.setHeader("Authorization", "Bearer " + accessToken);
        request.setHeader(HttpHeaders.ACCEPT_LANGUAGE, "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");


        try {
            Date start = new Date();
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(request);

            System.out.println(new Date().getTime() - start.getTime());
            System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);

                //reading ids of issues of project
                findIDsOFIssues(result);
               Printer.print("Enter short name of issue");
               String shtNameOfIss = Inputter.input();
               info.add(shtNameOfIss);
            }


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return info;
    }

    //"(idReadable\":)\"[A-Za-z0-9-]+\""
    private static void findIDsOFIssues(String result) {

        int lastIndex = 0;
        List<String> shortNamesOfIssues = new ArrayList<>();

        while (lastIndex != -1) {
            lastIndex = result.indexOf("idReadable", lastIndex);

            if (lastIndex != -1) {
                shortNamesOfIssues.add(result.substring(lastIndex + "idReadable".length() + 3,
                        result.indexOf("\",", lastIndex + "idReadable".length() + 2)));
                lastIndex++;
            }
        }
        for (String val : shortNamesOfIssues
        ) {
            System.out.println(val);
        }
    }

    public static long getIssueById(List<String> info) throws IOException {

        StringBuilder fullUrl = new StringBuilder();
        fullUrl.append(info.get(0));
        fullUrl.replace(fullUrl.indexOf(" "), fullUrl.indexOf(" "), "/api/issues?/"+info.get(2));
        fullUrl.replace(fullUrl.indexOf(" "), fullUrl.indexOf(" "),"?fields=$type,id,summary,customFields(value($type,avatarUrl,buildLink,color(id),fullName,id,isResolved,localizedName,login,minutes,name,presentation,text))");


        //HttpGet request = new HttpGet("https://tryloadtest.myjetbrains.com/youtrack/api/issues/TPT-1?fields=$type,id,summary,customFields(value($type,avatarUrl,buildLink,color(id),fullName,id,isResolved,localizedName,login,minutes,name,presentation,text))");
        HttpGet request = new HttpGet(fullUrl.toString().trim());

        //add request headers
        request.addHeader("accept", "application/json");
        request.addHeader("Cache-Control", "no-cache");
        request.addHeader("Content-Type", "application/json");
        request.setHeader("Authorization", "Bearer " + info.get(1));
        request.setHeader(HttpHeaders.ACCEPT_LANGUAGE, "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");

        long time = 0L;

        try {
            Date start = new Date();
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(request);

             time = new Date().getTime() - start.getTime();
            System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);

                //reading ids of issues of project

                //System.out.println(result);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
       return time;
    }


}
