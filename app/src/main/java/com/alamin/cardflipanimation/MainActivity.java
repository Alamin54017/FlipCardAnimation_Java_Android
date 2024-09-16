package com.alamin.cardflipanimation;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private boolean isBackVisible = false;
    private AnimatorSet frontAnimator;
    private AnimatorSet backAnimator;

    ImageView frontView, backView;

    Button flipButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frontView = findViewById(R.id.frontView);
        backView = findViewById(R.id.backView);
        flipButton = findViewById(R.id.flipButton);

        // Load animations from XML resources
        frontAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.front_flip);
        backAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.back_flip);

        flipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBackVisible) {
                    frontFlip();
                } else {
                    backFlip();
                }
            }
        });
    }


    void frontFlip(){
        backAnimator.setTarget(frontView);
        backAnimator.start();

        // Toggle visibility after animation
        frontView.setVisibility(View.VISIBLE);
        backView.setVisibility(View.INVISIBLE);
        isBackVisible = false;
    }

    void backFlip(){
        frontAnimator.setTarget(backView);
        frontAnimator.start();


        // Toggle visibility after animation
        frontView.setVisibility(View.INVISIBLE);
        backView.setVisibility(View.VISIBLE);
        isBackVisible = true;
    }
}