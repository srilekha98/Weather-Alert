package info.androidhive.navigationdrawer.utility; /**
 * Created by mkukunooru on 5/19/2017.
 */

import android.app.Application;

public class app extends Application {
    private static String res;
    private static String cityname;
    private static String art;
    private static String lat;
    private static String lng;

    public static String getRes(){
        return(res);
    }        //weather -dress words

    public static void setRes(String result)
    {
        res=result;
    }

    public static String getCityname(){
        return(cityname);
    }

    public static void setCityname(String result)
    {
        cityname=result;
    }

    public static String getArt(){
        return(art);
    }

    public static void setArt(String result)
    {
        art=result;
    }

    public static String getLat(){
        return(lat);
    }

    public static void setLat(String result)
    {
        lat=result;
    }

    public static String getLng(){
        return(lng);
    }

    public static void setLng(String result)
    {
        lng=result;
    }
}
