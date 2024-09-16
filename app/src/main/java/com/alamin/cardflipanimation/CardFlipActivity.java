package com.alamin.cardflipanimation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class CardFlipActivity extends AppCompatActivity {
    CardView frontCard, backCard;
    TextInputEditText cardNumberEditText, cardNameEditText, validDateEditText, cvvEditText;

    TextView cardNumberTextView, cardNameTextView, validDateTextView, cvvTextView;

    AnimatorSet frontAnimator, backAnimator;

    private boolean isBackVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_flip);


        frontCard = findViewById(R.id.frontCard);
        backCard = findViewById(R.id.backCard);
        cardNumberEditText = findViewById(R.id.cardNumberEditText);
        cardNameEditText = findViewById(R.id.cardNameEditText);
        validDateEditText = findViewById(R.id.validDateEditText);
        cvvEditText = findViewById(R.id.cvvEditText);

        cardNumberTextView = findViewById(R.id.accnumber);
        cardNameTextView = findViewById(R.id.accname);
        validDateTextView = findViewById(R.id.accdate);
        cvvTextView = findViewById(R.id.cvv);

        //Setting All Blank
        cardNumberTextView.setText("");
        cardNameTextView.setText("");
        validDateTextView.setText("");
        cvvTextView.setText("");


        // Load animations from XML resources
        frontAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.back_flip);
        backAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.front_flip);

        //EditText Click Listener
        cardNumberEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b && isBackVisible) {
                    frontFlip(); // Flip back to front
                }
            }
        });

        cardNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b && isBackVisible) {
                    frontFlip(); // Flip back to front
                }
            }
        });

        validDateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b && isBackVisible) {
                    frontFlip(); // Flip back to front
                }
            }
        });

        cvvEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b && !isBackVisible) {
                    backFlip(); // Flip fro
                }
            }
        });


        // Text Watcher for All EditText
        cardNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String rawInput = charSequence.toString().replace(" ", ""); // Remove any existing spaces
                StringBuilder formattedText = new StringBuilder(rawInput);

                // Limit the input to 16 digits
                if (formattedText.length() > 16) {
                    formattedText.delete(16, formattedText.length()); // Trim excess characters
                }

                // Insert spaces after every 4 characters
                for (int j = 4; j < formattedText.length(); j += 5) {
                    formattedText.insert(j, " ");
                }

                // Remove the listener temporarily to avoid infinite loop
                cardNumberEditText.removeTextChangedListener(this);

                // Update the text with the formatted version
                cardNumberEditText.setText(formattedText.toString());
                cardNumberEditText.setSelection(formattedText.length());

                // Re-attach the listener after the update
                cardNumberEditText.addTextChangedListener(this);

                // Also update the card number display TextView
                cardNumberTextView.setText(formattedText.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        cardNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                cardNameTextView.setText(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        validDateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Get the raw input (remove any existing slashes)
                String rawInput = charSequence.toString().replace("/", "");

                // Limit input to 4 digits (MMYY)
                if (rawInput.length() > 4) {
                    rawInput = rawInput.substring(0, 4); // Trim extra characters
                }

                // Format the input as MM/YY
                StringBuilder formattedText = new StringBuilder(rawInput);
                if (formattedText.length() > 2) {
                    formattedText.insert(2, "/");
                }

                // Remove the listener temporarily to avoid infinite loop
                validDateEditText.removeTextChangedListener(this);

                // Set the formatted text in the EditText and move the cursor to the correct position
                validDateEditText.setText(formattedText.toString());
                validDateEditText.setSelection(formattedText.length());

                // Re-attach the listener after the update
                validDateEditText.addTextChangedListener(this);
                validDateTextView.setText(formattedText.toString());

                // Validate the month (01-12) and year
                if (formattedText.length() >= 2) {
                    String month = formattedText.substring(0, 2);
                    try {
                        int monthInt = Integer.parseInt(month);
                        if (monthInt < 1 || monthInt > 12) {
                            validDateEditText.setError("Invalid month");
                        }
                    } catch (NumberFormatException e) {
                        validDateEditText.setError("Invalid input");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        cvvEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Convert CharSequence to String
                String input = charSequence.toString();

                // Limit input to 3 characters
                if (input.length() > 3) {
                    input = input.substring(0, 3); // Trim excess characters
                }

                // Remove the listener temporarily to avoid infinite loop
                cvvEditText.removeTextChangedListener(this);

                // Update the EditText with the limited input
                cvvEditText.setText(input);
                cvvEditText.setSelection(input.length()); // Move cursor to end

                // Re-attach the listener
                cvvEditText.addTextChangedListener(this);

                // Update the CVV TextView
                cvvTextView.setText(input);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    // Flip the card from front to back and vice versa
    void frontFlip(){
        backAnimator.setTarget(frontCard);
        backAnimator.start();

        // Toggle visibility after animation
        frontCard.setVisibility(View.VISIBLE);
        backCard.setVisibility(View.INVISIBLE);
        isBackVisible = false;
    }

    void backFlip(){
        frontAnimator.setTarget(backCard);
        frontAnimator.start();


        // Toggle visibility after animation
        frontCard.setVisibility(View.INVISIBLE);
        backCard.setVisibility(View.VISIBLE);
        isBackVisible = true;
    }
}