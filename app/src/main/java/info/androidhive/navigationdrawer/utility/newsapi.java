package info.androidhive.navigationdrawer.utility;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * @author Arpit Mandliya
 */
public class newsapi {

    private final String USER_AGENT = "Mozilla/5.0";

    public static String startService() throws Exception {

        newsapi http = new newsapi();

        // Sending get request
        String s=http.sendingGetRequest();

        // Sending post request
      //  http.sendingPostRequest();
        return (s);
    }

    // HTTP GET request
    private String sendingGetRequest() throws Exception {

        String urlString = "http://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=3d6e40bb709a413da3b5d1efda512e09&fq=%22"+app.getArt()+"%22&q=%22"+app.getCityname()+"%22";

        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        // By default it is GET request
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("Sending get request : "+ url);
        System.out.println("Response code : "+ responseCode);

        // Reading response from input Stream
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();

        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        //printing result from response
        System.out.println(response.toString());
        return(response.toString());
    }


}
