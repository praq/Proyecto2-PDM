package ues.edu.sv.petplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public  void registro(View v)
    {
        Intent ints = new Intent(this,RegistroActivity.class);
        startActivity(ints);
    }
}
