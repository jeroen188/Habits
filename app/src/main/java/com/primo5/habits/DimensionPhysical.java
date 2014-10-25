package com.primo5.habits;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class DimensionPhysical extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimension_physical);
    }

    public void writingDetail(View view) {
        Intent intent = new Intent(this, DimensionPhysical.class);
        startActivity(intent);
    }

}