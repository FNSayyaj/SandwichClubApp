package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    TextView ingredientsIv;
    TextView descriptionIv;
    TextView knownAsIv;
    TextView originIv;
    ProgressBar progressBarIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        image_iv =  findViewById(R.id.image_iv);
        ingredientsIv = findViewById(R.id.ingredients_tv);
        descriptionIv = findViewById(R.id.description_tv);
        knownAsIv = findViewById(R.id.also_known_tv);
        originIv = findViewById(R.id.origin_tv);
        progressBarIv = findViewById(R.id.progressBar_tv);



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
        progressBarIv.setVisibility(View.VISIBLE);


        Picasso.with(this).load(sandwich.getImage()).into(image_iv, new Callback.EmptyCallback() {
            //make the progressbar disappear when the image is done loading
            @Override public void onSuccess() {
                progressBarIv.setVisibility(View.INVISIBLE);
                image_iv.setVisibility(View.VISIBLE);
            }
        });

        setTitle(sandwich.getMainName());
        descriptionIv.setText(sandwich.getDescription());
        //check that the array of the alternative names is not empty
        if (sandwich.getAlsoKnownAs().isEmpty()) {
            knownAsIv.setText("No Data Found");

        }else{
            knownAsIv.setText(sandwich.getAlsoKnownAs().toString());
        }

        ingredientsIv.setText(sandwich.getIngredients().toString());
        //check that the object of the origin is not empty
        if(sandwich.getPlaceOfOrigin().equals("")) {
            originIv.setText("No Data Found");

        }else{
            originIv.setText(sandwich.getPlaceOfOrigin());
        }

    }
}
