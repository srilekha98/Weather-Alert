package info.androidhive.navigationdrawer.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

import info.androidhive.navigationdrawer.R;
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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;



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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        new Callapi().execute();
        return inflater.inflate(R.layout.fragment_photos, container, false);
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

            try {

                String titleid,desid,timeid,imgid;
                titleid="news_title";
                desid="news_description";
                timeid="news_time";
                imgid="news_img";
                int p=0;

                JSONObject obj = new JSONObject(s);
                final JSONArray articles=obj.getJSONArray("articles");
                for (int i = 0; i < articles.length(); i++) {

                    JSONObject c = articles.getJSONObject(i);

                    String title = c.getString("title");
                    String des = c.getString("description");
                    String time = c.getString("publishedAt");
                    String imgurl=c.getString("urlToImage");
                    URL url = new URL(imgurl);
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                    titleid = titleid + Integer.toString(p);
                    desid = desid + Integer.toString(p);
                    timeid = timeid+ Integer.toString(p);
                    imgid = imgid + Integer.toString(p);

                   TextView view1 = (TextView) view.findViewById(R.id.output);


                    // imageView.setImageBitmap(bmp);
//
//                    if(i==0) {
//
//                    }
//                    else
//                    {
//                        RelativeLayout.LayoutParams rlay = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 190 );
//                        rlay.setMargins(10,10,10,10);
//
//                        TextView textView = new TextView(this);
//                        textView.setText(title);
//                        rlay.addView(textView);
//
//                    }

//                    System.out.println("humidityyy issss "+title);
//                    System.out.println("temp is"+des);
//                    System.out.println("description is"+time);

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
            System.out.println(s);
            return s;
        }


        protected void onPostExecute(String result) {
            System.out.println("Downloaded " + result + " bytes");
        }
}

}
