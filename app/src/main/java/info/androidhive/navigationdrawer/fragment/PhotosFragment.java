package info.androidhive.navigationdrawer.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.utility.CustomListAdapter;
import info.androidhive.navigationdrawer.utility.NewsItem;
import info.androidhive.navigationdrawer.utility.app;
import info.androidhive.navigationdrawer.utility.newsapi;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PhotosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PhotosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ListView lv1 = null;
    ArrayList<NewsItem> results = new ArrayList<NewsItem>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    CustomListAdapter adap;

    private OnFragmentInteractionListener mListener;

    View vieww;

    public PhotosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhotosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhotosFragment newInstance(String param1, String param2) {
        PhotosFragment fragment = new PhotosFragment();
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


    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        new Callapi().execute();
      vieww=inflater.inflate(R.layout.fragment_photos, container, false);
//String[] value={"zfghrzd","fdxgrydf","zdrfthydrf","fdhydffr"};
//        List<String> list = new ArrayList<String>();
//        list.add("list 1");
//        list.add("list 2");

//        list.add("list 3");


        Spinner spinner = (Spinner)vieww.findViewById(R.id.artspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.articles_array, android.R.layout.simple_spinner_item);
//// Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String art = (String) parent.getItemAtPosition(position);
                System.out.println(art);
                app.setArt(art);
                new Callapi().execute();



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lv1 = (ListView)vieww.findViewById(R.id.custom_list);


        return vieww;
    }



    private ArrayList getListData() {

        // Add some more dummy data for testing
        return results;
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

    private class Callapi extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... data) {
            String s= null;
            try {
              s=  newsapi.startService();
            } catch (Exception e) {
                e.printStackTrace();
            }


            return s;
        }


        protected void onPostExecute(String result) {
            System.out.println("Downloaded " + result + " bytes");
            results.clear();
            try {
                JSONObject obj = new JSONObject(result);

                JSONObject obj1=obj.getJSONObject("response");
                final JSONArray articles=obj1.getJSONArray("docs");

                for (int i = 0; i < articles.length(); i++) {

                    String title="",des="",time="", imgurl="";

                    JSONObject c = articles.getJSONObject(i);

                    JSONObject hd =  c.getJSONObject("headline");

                    if(!(c.isNull("byline"))) {
                        JSONObject  hd1 = c.getJSONObject("byline");
                        time = hd1.getString("original");
                    }

                    if(!(c.isNull("multimedia"))) {
                        JSONArray mm = c.getJSONArray("multimedia");
                        for (int j = 0; j < mm.length(); j++) {
                            JSONObject b=mm.getJSONObject(j);
                             imgurl =b.getString("url");
//                            URL url = new URL(imgurl);
//                            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                        }

                    }

                    title = hd.getString("main");
                    des = c.getString("lead_paragraph");
                    if(des!=null) {

                        NewsItem newsData = new NewsItem();

                        newsData.setHeadline(title);
                        newsData.setReporterName(des);
                        newsData.setDate(time);
                        newsData.setImg(imgurl);

                        results.add(newsData);
                    }

                    ArrayList image_details = getListData();
                    adap=new CustomListAdapter(getActivity().getApplicationContext(), image_details);
                    lv1.setAdapter(adap);
                    adap.notifyDataSetChanged();
                    lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                            Object o = lv1.getItemAtPosition(position);
                            NewsItem newsData = (NewsItem) o;
                            Toast.makeText(getActivity().getApplicationContext(), "Selected :" + " " + newsData, Toast.LENGTH_LONG).show();
                        }
                    });


//                   System.out.println("humidityyy issss "+title);
//                    System.out.println("temp is"+des);
//                   System.out.println("description is"+time);

                }


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //printInTextView(temp,descrip,id);
//stuff that updates ui

                    }
                });


            }
            catch(Exception e)
            {
                System.out.println("gone");
                e.printStackTrace();
            }
            System.out.println(result);

        }
}
}

