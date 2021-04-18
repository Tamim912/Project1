package com.example.socialmedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class ShowStory extends AppCompatActivity implements StoriesProgressView.StoriesListener {


    int counter = 0;
    ImageView imageViewSHowStory,imageViewUrl;
    TextView textView;

    List<String> posturi;
    List<String>url;
    List<String>username;

    String userid;

    StoriesProgressView storiesProgressView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference;

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_story);

        imageViewSHowStory = findViewById(R.id.iv_storyview);
        imageViewUrl = findViewById(R.id.iv_ss);
        textView = findViewById(R.id.tv_uname_ss);
        storiesProgressView = findViewById(R.id.stories);

        View reverse = findViewById(R.id.view_prev);
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storiesProgressView.reverse();
            }
        });
        reverse.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                storiesProgressView.pause();
                return false;
            }
        });
        reverse.setOnTouchListener(onTouchListener);

        View next = findViewById(R.id.view_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storiesProgressView.skip();
            }
        });
        next.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                storiesProgressView.pause();
                return false;
            }
        });
        next.setOnTouchListener(onTouchListener);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            userid = bundle.getString("u");
        }else {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }

        reference = database.getReference("story").child(userid);


    }

    @Override
    protected void onStart() {
        super.onStart();

        getStories(userid);
    }

    private void getStories(String userid) {

        posturi = new ArrayList<>();
        username = new ArrayList<>();
        url = new ArrayList<>();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                posturi.clear();
                url.clear();
                username.clear();

                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    StoryMember member = snapshot1.getValue(StoryMember.class);

                    posturi.add(member.getPostUri());
                    url.add(member.getUrl());
                    username.add(member.getName());


                }

               storiesProgressView.setStoriesCount(posturi.size());
                storiesProgressView.setStoryDuration(5000L);
                storiesProgressView.setStoriesListener(ShowStory.this);
                storiesProgressView.startStories(counter);

                Picasso.get().load(posturi.get(counter)).into(imageViewSHowStory);
                Picasso.get().load(url.get(counter)).into(imageViewUrl);
                textView.setText(username.get(counter));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onNext() {

        Picasso.get().load(posturi.get(++counter)).into(imageViewSHowStory);

    }

    @Override
    public void onPrev() {

        if ((counter-1) <0)return;
        Picasso.get().load(posturi.get(--counter)).into(imageViewSHowStory);

    }

    @Override
    public void onComplete() {

        finish();
    }

    @Override
    protected void onDestroy() {
        storiesProgressView.destroy();
        super.onDestroy();


    }

    @Override
    protected void onPause() {
        storiesProgressView.pause();
        super.onPause();


    }
}