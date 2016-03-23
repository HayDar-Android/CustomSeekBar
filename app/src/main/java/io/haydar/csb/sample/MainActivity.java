package io.haydar.csb.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import io.haydar.csb.CustomSeekBar;


public class MainActivity extends AppCompatActivity {

    private CustomSeekBar csb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.init();
        csb = (CustomSeekBar) findViewById(R.id.csb);
        csb.setOnValueChanged(new CustomSeekBar.OnValueChanged() {
            @Override
            public void onValueChanged(int value) {
                Toast.makeText(MainActivity.this, value + "", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
