package com.example.socialmedia;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class ProfileViewholder extends RecyclerView.ViewHolder {

    TextView textViewName,textViewProfession,viewUserprofile;
    ImageView imageView;
    CardView cardView;

    public ProfileViewholder(@NonNull View itemView) {
        super(itemView);
    }

    public void setProfile(FragmentActivity fragmentActivity, String name, String uid, String prof,
                           String url){

        cardView = itemView.findViewById(R.id.cardview_profile);
        textViewName = itemView.findViewById(R.id.tv_name_profile);
        textViewProfession = itemView.findViewById(R.id.tv_profession_profile);
        viewUserprofile = itemView.findViewById(R.id.viewUser_profile);
        imageView = itemView.findViewById(R.id.profile_imageview);



        Picasso.get().load(url).into(imageView);
        textViewProfession.setText(prof);
        textViewName.setText(name);

    }

}
