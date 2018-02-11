package com.google.firebase.udacity.friendlychat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<FriendlyMessage> {

    FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mFirebaseAuth.getCurrentUser();
    String userName = user.getDisplayName();

    public interface ListItemClickListener{
        public void onListItemClick(String imageRes);
    }

    ListItemClickListener listener;

    public MessageAdapter(Context context, int resource, List<FriendlyMessage> objects,ListItemClickListener listener) {
        super(context, resource, objects);
        this.listener = listener;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message1, parent, false);
        }

        ImageView photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);
        TextView messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        TextView authorTextView = (TextView) convertView.findViewById(R.id.nameTextView);
        LinearLayout linearLayout = (LinearLayout)convertView.findViewById(R.id.linearlayouttop);
        CardView cardView = (CardView)convertView.findViewById(R.id.cardView);

        final FriendlyMessage message = getItem(position);
        if(userName != null)
            Log.v("UserName : ", userName);
        else
            Log.v("UserNanme : " , "null");
        if(message.getName().equals(userName)) {
            //linearLayout.setGravity(Gravity.RIGHT | Gravity.END);
            cardView.setCardBackgroundColor(cardView.getRootView().getResources().getColor(R.color.cyanA100));
        }else
        {
            //linearLayout.setGravity(Gravity.LEFT | Gravity.START);
            cardView.setCardBackgroundColor(cardView.getRootView().getResources().getColor(R.color.greenA100));
        }

        boolean isPhoto = message.getPhotoUrl() != null;
        if (isPhoto) {
            messageTextView.setVisibility(View.GONE);
            photoImageView.setVisibility(View.VISIBLE);
            Glide.with(photoImageView.getContext())
                    .load(message.getPhotoUrl())
                    .into(photoImageView);
            photoImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onListItemClick(message.getPhotoUrl());
                }
            });
        } else {
            messageTextView.setVisibility(View.VISIBLE);
            photoImageView.setVisibility(View.GONE);
            messageTextView.setText(message.getText());
        }
        authorTextView.setText(message.getName());

        return convertView;
    }
}
