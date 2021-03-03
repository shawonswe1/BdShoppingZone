package com.morshed.bdshoppingzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class LiveVideoActivity extends AppCompatActivity {

    String message;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_video);

        //Add back button------------------
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        button = findViewById(R.id.bt_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }
        });
        Bundle bundle = getIntent().getExtras();
        message = bundle.getString("body");
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        if (isValid(message))
        {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(message));
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "This Url is Not Valid", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Url Validation check......
    public static boolean isValid(String url)
    {
        try {
            new URL(url).toURI();
            return true;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return false;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }
    }
}