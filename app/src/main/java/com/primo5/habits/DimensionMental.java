package com.primo5.habits;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class DimensionMental extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimension_mental);
    }

    public void writingDetail(View view) {
        Intent intent = new Intent(this, DimensionMental.class);
        startActivity(intent);
    }
    public void addTask(View view) {
        Intent intent = new Intent(this, NewTask.class);
        startActivity(intent);
    }

}
