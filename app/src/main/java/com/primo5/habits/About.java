package com.primo5.habits;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by patrickkeating on 10/25/14.
 */
public class About extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
    public void writingDetail(View view) {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

}

