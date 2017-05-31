package info.androidhive.navigationdrawer.fragment;

import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.utility.TrackGps;
import info.androidhive.navigationdrawer.utility.app;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnPoiClickListener,OnStreetViewPanoramaReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GoogleMap mMap;
    Button nv,sv;
    String tileType;
    TileOverlay tileOverlay;


    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    View view ;
    Spinner spinner;


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


        view=inflater.inflate(R.layout.fragment_home, container, false);

       nv = (Button) view.findViewById(R.id.button1);
        sv=(Button)view.findViewById(R.id.button2);

        FragmentManager fm = getFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();
        fm.beginTransaction();

        final SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //StreetViewPanoramaFragment streetViewPanoramaFragment = (StreetViewPanoramaFragment) getFragmentManager().findFragmentById(R.id.map1);
       // streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);

        nv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click


            }
        });
        sv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                ft.hide(mapFragment);

            }
        });


        spinner = (Spinner) view.findViewById(R.id.tileType);

        String[] tileName = new String[]{"Clouds", "Temperature", "Precipitations", "Snow", "Rain", "Wind", "Sea level press."};

        ArrayAdapter adpt = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, tileName);

        spinner.setAdapter(adpt);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onNothingSelected(AdapterView parent) {

            }

            @Override
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                // Check click
                ((TextView) view).setTextColor(Color.BLACK);
                switch (position) {
                    case 0:
                        tileType = "clouds";
                        break;
                    case 1:
                        tileType = "temp";
                        break;
                    case 2:
                        tileType = "precipitation";
                        break;
                    case 3:
                        tileType = "snow";
                        break;
                    case 4:
                        tileType = "rain";
                        break;
                    case 5:
                        tileType = "wind";
                        break;
                    case 6:
                        tileType = "pressure";
                        break;

                }

                if (mMap != null) {
                    tileOverlay.remove();
                    onMapReady(mMap);
                    System.out.println("mMap is null");
                }

            }
        });




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

    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        TrackGps gps = new TrackGps(getActivity());

        double lat=0,lng=0;
        if(gps.canGetLocation()){

            lng = gps.getLongitude();
            lat = gps .getLatitude();

            // Toast.makeText(getApplicationContext(),"Longitude:"+Double.toString(longitude)+"\nLatitude:"+Double.toString(latitude),Toast.LENGTH_SHORT).show();
        }
        else
        {
            gps.showSettingsAlert();
        }

        String result = null;
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try
        {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Log.e("Addresses","-->"+addresses);
            result = addresses.get(0).getLocality().toString();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println(result);
        app.setCityname(result);

        LatLng TutorialsPoint = new LatLng(lat, lng);
        mMap.addMarker(new
                MarkerOptions().position(TutorialsPoint).title("Your position"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));
        mMap.setOnPoiClickListener(this);

        UiSettings mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.isCompassEnabled();
        mUiSettings.isMyLocationButtonEnabled();
        mUiSettings.setAllGesturesEnabled(true);


        TileProvider tileProvider = new UrlTileProvider(256, 256) {
            @Override
            public URL getTileUrl(int x, int y, int zoom) {

    /* Define the URL pattern for the tile images */
    System.out.println("x y zoom "+x+"    "+y+"    "+zoom);
                String s = String.format("http://tile.openweathermap.org/map/%s/%d/%d/%d.png?appid=ace61dfeff9b51609c3a1d1b566dde0f",tileType,
                        zoom, x, y);


                try {
                    return new URL(s);
                } catch (MalformedURLException e) {
                    throw new AssertionError(e);
                }
            }
        };

        tileOverlay = mMap.addTileOverlay(new TileOverlayOptions()
                .tileProvider(tileProvider));
        tileOverlay.setVisible(true);
        tileOverlay.setTransparency(0.5f);


    }

    @Override
    public void onPoiClick(PointOfInterest poi) {
        LatLng TutorialsPoint = new LatLng(poi.latLng.latitude, poi.latLng.longitude);
        mMap.addMarker(new
                MarkerOptions().position(TutorialsPoint).title(poi.name));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));

    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
        // Add a marker in Sydney and move the camera
        TrackGps gps = new TrackGps(getActivity());

        double lat=0,lng=0;
        if(gps.canGetLocation()){

            lng = gps.getLongitude();
            lat = gps .getLatitude();

            // Toast.makeText(getApplicationContext(),"Longitude:"+Double.toString(longitude)+"\nLatitude:"+Double.toString(latitude),Toast.LENGTH_SHORT).show();
        }
        else
        {
            gps.showSettingsAlert();
        }

        String result = null;
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try
        {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Log.e("Addresses","-->"+addresses);
            result = addresses.get(0).getLocality().toString();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println(result);
        app.setCityname(result);
        panorama.setPosition(new LatLng(lat,lng));

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
