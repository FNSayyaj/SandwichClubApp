package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    ImageView image_iv;
    TextView ingredients_tv;
    TextView description_tv;
    TextView knownAs_tv;
    TextView origin_tv;
    ProgressBar progressBar_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        image_iv =  findViewById(R.id.image_iv);
        ingredients_tv = findViewById(R.id.ingredients_tv);
        description_tv = findViewById(R.id.description_tv);
        knownAs_tv = findViewById(R.id.also_known_tv);
        origin_tv = findViewById(R.id.origin_tv);
        progressBar_tv = findViewById(R.id.progressBar_tv);




        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);


    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    //method that populate the UI from the parsed JSON
    private void populateUI(Sandwich sandwich) {
        progressBar_tv.setVisibility(View.VISIBLE);


        Picasso.with(this).load(sandwich.getImage()).into(image_iv, new Callback.EmptyCallback() {
            //make the progressbar disappear when the image is done loading
            @Override public void onSuccess() {
                progressBar_tv.setVisibility(View.INVISIBLE);
                image_iv.setVisibility(View.VISIBLE);
            }
        });

        setTitle(sandwich.getMainName());
        description_tv.setText(sandwich.getDescription());
        //check that the array of the alternative names is not empty
        if (sandwich.getAlsoKnownAs().isEmpty()) {
            knownAs_tv.setText(R.string.data_not_found);

        }else{
            knownAs_tv.setText(TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        }

        ingredients_tv.setText(TextUtils.join(", ", sandwich.getIngredients()));
        //check that the object of the origin is not empty
        if(sandwich.getPlaceOfOrigin().equals("")) {
            origin_tv.setText(R.string.data_not_found);

        }else{
            origin_tv.setText(sandwich.getPlaceOfOrigin());
        }

    }
}
