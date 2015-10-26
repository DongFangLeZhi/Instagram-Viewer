package com.example.xmeng.insta_viewer;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by xmeng on 10/25/15.
 */
public class PhotoAdapter extends ArrayAdapter<InstagramPhoto>{

    public PhotoAdapter(Context context, List objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        TextView tvCaption = (TextView)convertView.findViewById(R.id.tvCaption);
        TextView tvUserName = (TextView)convertView.findViewById(R.id.tvUserName);
        TextView tvLikesCount= (TextView)convertView.findViewById(R.id.tvLikesCount);
        ImageView ivPhoto = (ImageView)convertView.findViewById(R.id.ivPhoto);
        ImageView ivHeart = (ImageView)convertView.findViewById(R.id.ivHeart);
        RoundedImageView ivProfile = (RoundedImageView)convertView.findViewById(R.id.ivUserProfile);
        String captionUserName = "<font color='#125688'><b>"+ photo.username + "</b></font> ";
        tvCaption.setText(Html.fromHtml(captionUserName + photo.caption));
        tvUserName.setText(photo.username);

        tvLikesCount.setText(photo.likesCount + " likes");
        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto);
        Picasso.with(getContext()).load(photo.userProfile).into(ivProfile);
        Picasso.with(getContext()).load("http://assets.strikingly.com/assets/themes/app/heart.png").into(ivHeart);

        return convertView;
    }
}
