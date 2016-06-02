package com.example.admin.drivingroutes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_simple).setOnClickListener(this);
        findViewById(R.id.btn_transit).setOnClickListener(this);
        findViewById(R.id.btn_alternative).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_simple) {
            goToSimpleDirection();
        } else if (id == R.id.btn_transit) {
            //goToTransitDirection();
        } else if (id == R.id.btn_alternative) {
           // goToAlternativeDirection();
        }
    }


    public void goToSimpleDirection() {
        Intent intent=new Intent(MainActivity.this,SimpleDirectionActivity.class);
        startActivity(intent);
        //openActivity(SimpleDirectionActivity.class);
    }


}
