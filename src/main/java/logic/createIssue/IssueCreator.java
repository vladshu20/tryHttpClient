package logic.createIssue;

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
import java.util.Date;

public class IssueCreator {
    public static void createIssue() {
        Printer.print("Please enter url of tracker:\t");
        String urlOfProject = Inputter.input();

        Printer.print("Please short name of project:\t");
        String shtNameOfPrj = Inputter.input();

        Printer.print("Enter permament access token");
        String accessToken = Inputter.input();




        StringBuilder fullUrl = new StringBuilder();

        fullUrl.append(urlOfProject);
        fullUrl.replace(fullUrl.indexOf(" "), fullUrl.indexOf(" "), "/admin/projects?fields=name,shortName,issues,id&query=");
        fullUrl.insert(fullUrl.length() - 1, shtNameOfPrj);


        //HttpGet request = new HttpGet("https://tryloadtest.myjetbrains.com/youtrack/api/admin/projects?fields=name,shortName,issues,id&query=TPT");
        HttpGet request = new HttpGet(fullUrl.toString().trim());

        //add request headers
        request.addHeader("accept", "application/json");
        request.addHeader("Cache-Control", "no-cache");
        request.addHeader("Content-Type", "application/json");
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
                //System.out.println(result);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
