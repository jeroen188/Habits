package com.primo5.habits;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DailyTasks.class);
        startActivity(intent);
    }

    public void addTask(View view) {
        Intent intent = new Intent(this, NewTask.class);
        startActivity(intent);
    }

    public void sendMental(View view) {
        Intent intent = new Intent(this, DimensionMental.class);
        startActivity(intent);
    }

    public void sendPhysical(View view) {
        Intent intent = new Intent(this, DimensionPhysical.class);
        startActivity(intent);
    }

    public void sendSpirituality(View view) {
        Intent intent = new Intent(this, DimensionSpirituality.class);
        startActivity(intent);
    }

    public void sendSocial(View view) {
        Intent intent = new Intent(this, DimensionSocial.class);
        startActivity(intent);
    }

    public void sendAbout(View view) {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

    public void sendBalance(View view) {
        Intent intent = new Intent(this, Balance.class);
        startActivity(intent);


    }
}