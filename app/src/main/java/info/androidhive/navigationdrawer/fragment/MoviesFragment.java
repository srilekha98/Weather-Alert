package info.androidhive.navigationdrawer.fragment;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.utility.AlarmReceiver;
import info.androidhive.navigationdrawer.utility.TrackGps;
import info.androidhive.navigationdrawer.utility.api;
import info.androidhive.navigationdrawer.utility.app;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MoviesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView textView4 = null;


    String res="";
    String alertTime="";
    private int ahr=0,amin=0;
    Intent alarmIntent;
    private TextView view1;
    public Button btnClick;
    private int hr;
    private int min;
    private PendingIntent pendingIntent;
    private AlarmManager manager;

    LocationManager locationManager;
    private TrackGps gps;

    private OnFragmentInteractionListener mListener;

    public MoviesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoviesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MoviesFragment newInstance(String param1, String param2) {
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_movies, container, false);
        textView4 = (TextView) view.findViewById(R.id.textView4);
        new Callapi().execute();

        // final app gv = (app) getApplicationContext();
        app.setRes("");

        String locale = getActivity().getResources().getConfiguration().locale.getDisplayCountry();
        System.out.println(locale + ":country");

        double latitude = 0, longitude = 0;

        gps = new TrackGps(getActivity());


        if(gps.canGetLocation()){


            longitude = gps.getLongitude();
            latitude = gps .getLatitude();

            // Toast.makeText(getApplicationContext(),"Longitude:"+Double.toString(longitude)+"\nLatitude:"+Double.toString(latitude),Toast.LENGTH_SHORT).show();
        }
        else
        {

            gps.showSettingsAlert();
        }
           System.out.println(latitude+"   "+longitude);
        String result = null;
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try
        {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            Log.e("Addresses","-->"+addresses);
            result = addresses.get(0).getLocality().toString();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
       System.out.println(result);
        app.setCityname(result);


        view1 = (TextView) view.findViewById(R.id.output);
      final Calendar c = Calendar.getInstance();
        hr = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);
       updateTime(hr, min);
        btnClick = (Button) view.findViewById(R.id.btnClick);
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createdDialog(0).show();
            }
        });
        Button setAlarm= (Button) view.findViewById(R.id.button1);
        setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAlarm();
            }
        });
        Button cancelAlarm= (Button) view.findViewById(R.id.button2);
        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });


        alarmIntent = new Intent(getActivity(), AlarmReceiver.class);

        // manager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        //pendingIntent = PendingIntent.getActivity(this, 0, alarmIntent,PendingIntent.FLAG_CANCEL_CURRENT);
         pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
//    public void addButtonClickListener() {
//        btnClick = (Button) getView().findViewById(R.id.btnClick);
//        btnClick.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                createdDialog(0).show();
//            }
//        });
//    }
    protected Dialog createdDialog(int id) {

        return new TimePickerDialog(getActivity(), timePickerListener, hr, min, false);


    }
    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
// TODO Auto-generated method stub
            hr = hourOfDay;
            min = minutes;
            updateTime(hr, min);
        }
    };
    private static String utilTime(int value) {
        if (value < 10) return "0" + String.valueOf(value); else return String.valueOf(value); }

    private void updateTime(int hours, int mins) { String timeSet = ""; if (hours > 12) {
        hours -= 12;
        timeSet = "PM";
    } else if (hours == 0) {
        hours += 12;
        timeSet = "AM";
    } else if (hours == 12)
        timeSet = "PM";
    else
        timeSet = "AM";
        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);
        String aTime = new StringBuilder().append(hours).append(':').append(minutes).append(" ").append(timeSet).toString();
        view1.setText(aTime);
        alertTime=aTime;
        convertAlertTime();
    }

    public void convertAlertTime()
    {
        int idxsc, idxsp;
        idxsc=alertTime.indexOf(':');
        idxsp=alertTime.indexOf(' ');
        ahr= Integer.parseInt(alertTime.substring(0,idxsc));
        amin= Integer.parseInt(alertTime.substring(idxsc+1,idxsp));
        String apm=alertTime.substring(idxsp+1,alertTime.length());
        if(apm.equals("PM"))
        {
            if(ahr!=12) {
                ahr = ahr + 12;
            }
        }
        else
        {
            if(ahr==12)
            {
                ahr=0;
            }
        }
        System.out.println(ahr+"   "+amin+"   "+apm);

    }

    public void startAlarm() {
        manager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        int interval = 24*60*60*1000;
        // long frst=System.currentTimeMillis();

        long millis=System.currentTimeMillis();
        Date date = new Date(millis);
        int shr=date.getHours();
        int smin=date.getMinutes();

        System.out.println("sys"+shr+"hh"+smin);
        //System.out.println("set"+dt);
        int ihr=0,imin=0;

        if(ahr>=shr)
        {
            ihr=ahr-shr;
        }
        else
        {
            ihr=24+(ahr-shr);
        }

        if(amin>=smin)
        {
            imin=amin-smin;
        }
        else
        {
            imin=60+(amin-smin);
        }
        System.out.println("int"+ihr+"hh"+imin);

        long start= millis+ihr*60*60*1000+imin*60*1000;

        System.out.println("current ms"+System.currentTimeMillis());
        System.out.println("start"+start);
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP,start,interval, pendingIntent);
        Toast.makeText(getActivity(), "Alarm Set", Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm() {
        if (manager != null) {
            manager.cancel(pendingIntent);
            Toast.makeText(getActivity(), "Alarm Canceled", Toast.LENGTH_SHORT).show();
        }
    }

    private class Callapi extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... data) {
            String s= api.startService(getActivity());

            try {
                JSONObject obj = new JSONObject(s);
                final JSONObject main1 = obj.getJSONObject("main");
                final JSONArray weather=obj.getJSONArray("weather");
                final JSONObject des=weather.getJSONObject(0);

                final double temp= Double.parseDouble(main1.getString("temp"));
                final String descrip=des.getString("description");
                final int id= Integer.parseInt(des.getString("id"));

                // System.out.println("humidityyy issss "+main1);
                //System.out.println("temp is"+main1.getString("temp"));
                // System.out.println("description is"+des.getString("description"));

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        printInTextView(temp,descrip,id);
//stuff that updates ui

                    }
                });


            }
            catch(Exception e)
            {
                System.out.println("gone");
                e.printStackTrace();
            }
            System.out.println(s);
            return s;
        }


        protected void onPostExecute(String result) {
            System.out.println("Downloaded " + result + " bytes");
        }
        void printInTextView(double temp, String des, int id)
        {

            if((id>950 && id<=957)||(id==905))
            {
                res="It's breezy Macha, Wear full sleeves and full pants";
            }
            else
            {
                if((id>957)||((id>=900)&&(id<=906))) {
                    if (id == 904) {
                        res = "Very hot outside.Try Sleveless dude!";
                    }
                    else {
                        res = "Severe weather conditions dude. Stay indoors.";
                    }
                }
                else
                {
                    if(id>=800 && id<850)
                    {
                        res="just cloudy.Short hand shirt and shorts is fine dude!";
                    }
                    else
                    {
                        if(id>=600 && id<650) {
                            if (id == 600 || id == 615 || id == 620) {
                                res = "Light Snow and rain. Be careful and wear warm clothes Macha :p";
                            }
                            else {
                                res = "Heavy Snow and rain. Carry umbrellas, raincoats and wear warm clothes Macha :p";
                            }
                        }
                        else
                        {
                            if(id>=500 && id<550)
                            {
                                if(id==500 || id==520 || id==531)
                                {
                                    res = "Light  rain. An umbrella should do it bro.";
                                }
                                else
                                {
                                    res = "Heavy rain. Warm clothes and raincoats.";
                                }
                            }
                            else
                            {
                                if(id>=300 && id<350)
                                {
                                    res = "Watchout for Drizzles. An umbrella should do it bro.";
                                }
                                else
                                {
                                    if(id>=200 && id<=250)
                                    {
                                        res = "Thunderstorm. Stay indoors.We love you bro.";
                                    }
                                    else
                                    {
                                        if(id>700 && id<790)
                                        {
                                            res ="dusty weather(mist,smoke,sand winds,fog.";
                                        }
                                    }
                                }
                            }

                        }
                    }

                }
            }
            res=res+"\nTemp="+temp;
            textView4.setText(res);
//            Bundle bundle = new Bundle();
//            bundle.putString("r",res.toString());
//            alarmIntent.putExtras(bundle);

            app.setRes(res);
            System.out.println("calling"+res);
        }

    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
