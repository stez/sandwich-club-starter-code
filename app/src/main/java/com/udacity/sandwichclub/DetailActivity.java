package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView alsoKnownAs;
    TextView origin;
    TextView description;
    TextView ingredients;
    ImageView ingredientsIv;

    Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        alsoKnownAs = findViewById(R.id.also_known_tv);
        origin = findViewById(R.id.origin_tv);
        description = findViewById(R.id.description_tv);
        ingredients = findViewById(R.id.ingredients_tv);
        ingredientsIv = findViewById(R.id.image_iv);

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
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        setTitle(sandwich.getMainName());
        origin.setText(sandwich.getPlaceOfOrigin().isEmpty()?getString(R.string.not_known):sandwich.getPlaceOfOrigin());
        description.setText(sandwich.getDescription());
        alsoKnownAs.setText(fromListToString(sandwich.getAlsoKnownAs(),", ",getString(R.string.empty_placeholder)));
        ingredients.setText(fromListToString(sandwich.getIngredients(),System.getProperty("line.separator"),getString(R.string.none)));
    }

    private String fromListToString(List<String> input, String separator, String placeholder){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i<input.size(); i++){
            sb.append(input.get(i));
            if (i<input.size()-1) sb.append(separator);
        }
        return new String(sb).isEmpty() ? placeholder : new String(sb);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
