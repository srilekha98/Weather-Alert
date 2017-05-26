package info.androidhive.navigationdrawer.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import info.androidhive.navigationdrawer.R;

import static info.androidhive.navigationdrawer.R.drawable.opinion;

/**
 * Created by mkukunooru on 5/25/2017.
 */public class CustomListAdapter extends BaseAdapter {
    private ArrayList<NewsItem> listData;
    private LayoutInflater layoutInflater;


    public CustomListAdapter(Context aContext, ArrayList<NewsItem> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.listnews, null);
            holder = new ViewHolder();
            holder.headlineView = (TextView) convertView.findViewById(R.id.title);
            holder.reporterNameView = (TextView) convertView.findViewById(R.id.description);
            holder.reportedDateView = (TextView) convertView.findViewById(R.id.time);
            holder.imgView=(ImageView)convertView.findViewById(R.id.img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.headlineView.setText(listData.get(position).getHeadline());
        holder.headlineView.setTextColor(Color.BLACK);
        holder.reporterNameView.setText(listData.get(position).getReporterName());
        holder.reporterNameView.setTextColor(Color.GRAY);
        holder.reporterNameView.setTextSize(10);
        holder.reportedDateView.setText(listData.get(position).getDate());
        holder.reportedDateView.setTextColor(Color.GRAY);
        holder.reportedDateView.setTextSize(8);
        String imgurl=listData.get(position).getImg();
        if(app.getArt().equals("opinion"))
        {
            holder.imgView.setImageResource(opinion);
        }
        else   if(app.getArt().equals("arts"))
        {
            holder.imgView.setImageResource(R.drawable.arts);
        }
        else   if(app.getArt().equals("automobiles"))
        {
            holder.imgView.setImageResource(R.drawable.automobiles);
        }
        else   if(app.getArt().equals("business"))
        {
            holder.imgView.setImageResource(R.drawable.business);
        }
        else   if(app.getArt().equals("food"))
        {
            holder.imgView.setImageResource(R.drawable.food);
        }
        else   if(app.getArt().equals("health"))
        {
            holder.imgView.setImageResource(R.drawable.health);
        }
        else   if(app.getArt().equals("insider"))
        {
            holder.imgView.setImageResource(R.drawable.insider);
        }
        else   if(app.getArt().equals("movies"))
        {
            holder.imgView.setImageResource(R.drawable.movies);
        }
        else   if(app.getArt().equals("national"))
        {
            holder.imgView.setImageResource(R.drawable.national);
        }
        else   if(app.getArt().equals("obituaries"))
        {
            holder.imgView.setImageResource(R.drawable.obituaries);
        }
        else   if(app.getArt().equals("politics"))
        {
            holder.imgView.setImageResource(R.drawable.politics);
        }
        else   if(app.getArt().equals("realestate"))
        {
            holder.imgView.setImageResource(R.drawable.realestate);
        }
        else   if(app.getArt().equals("science"))
        {
            holder.imgView.setImageResource(R.drawable.science);
        }
        else   if(app.getArt().equals("sports"))
        {
            holder.imgView.setImageResource(R.drawable.sports);
        }
        else   if(app.getArt().equals("technology"))
        {
            holder.imgView.setImageResource(R.drawable.technology);
        }
        else   if(app.getArt().equals("travel"))
        {
            holder.imgView.setImageResource(R.drawable.travel);
        }
        else   if(app.getArt().equals("upshot"))
        {
            holder.imgView.setImageResource(R.drawable.upshot);
        }
        else   if(app.getArt().equals("world"))
        {
            holder.imgView.setImageResource(R.drawable.world);
        }

        URL url = null;
        try {
            System.out.println(imgurl);
            url = new URL(imgurl);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            holder.imgView.setImageBitmap(bmp);

        } catch (MalformedURLException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }

        return convertView;
    }

    static class ViewHolder {
        TextView headlineView;
        TextView reporterNameView;
        TextView reportedDateView;
        ImageView imgView;
    }
}