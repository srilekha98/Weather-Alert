package info.androidhive.navigationdrawer.utility; /**
 * Created by mkukunooru on 5/19/2017.
 */

import android.app.Application;

public class app extends Application {
    private static String res;
    private static String cityname;

    public static String getRes(){
        return(res);
    }

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
}
