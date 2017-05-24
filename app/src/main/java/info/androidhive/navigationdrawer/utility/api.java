package info.androidhive.navigationdrawer.utility;

import android.content.Context;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class api {

    public static String startService(Context ctx)
    {
        HashMap<String,String> formData=new HashMap<>();
       // formData.put("q","select item from weather.forecast where woeid=2460286");
       // formData.put("format","json");
//        final app gv = (app) ctx.getApplicationContext();
        System.out.println(app.getCityname());
        String resourcePath="http://api.openweathermap.org/data/2.5/weather?q="+app.getCityname()+"&appid=ace61dfeff9b51609c3a1d1b566dde0f";
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuffer buffer = new StringBuffer("");
        int responseCode=0;

        try {
            URL url = new URL(resourcePath);
            connection = (HttpURLConnection) url.openConnection();
//            String userpass = "dj0yJmk9eVUzVmNmS1BRWDFyJmQ9WVdrOWRHRTJlR0Z2Tm1zbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD0yOQ--"+ ":" + "a1b6e4dae0f22fd613c7a5c1426ff1c91ec6b64f";
//            String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));
//            connection.setRequestProperty ("Authorization", basicAuth);
 //           InputStream in = connection.getInputStream();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
          ;
            if(formData!=null) {
                List<NameValuePair> params = new ArrayList<>();
                for (String key : formData.keySet())
                    params.add(new BasicNameValuePair(key, formData.get(key)));
                System.out.println(getQuery(params));
                wr.writeBytes(getQuery(params));
            }

            wr.flush();
            wr.close();
            responseCode = connection.getResponseCode();
            System.out.println("Response code from service "+responseCode);

            InputStream stream=connection.getInputStream();
            reader=new BufferedReader(new InputStreamReader(stream));

            String line="";
            while ((line= reader.readLine())!=null)
            {
                buffer.append(line);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return buffer.toString();
    }


    public static String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}

