package com.example.venkatesh.istictactoe;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class WelcomePage extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        imageView = (ImageView) findViewById(R.id.imageView2);
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
        imageView.startAnimation(myFadeInAnimation);



        Button playgame = (Button) findViewById(R.id.btnplay);
        playgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                finish();
                startActivity(i);
            }
        });

    }
}
