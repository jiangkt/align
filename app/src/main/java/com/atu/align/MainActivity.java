package com.atu.align;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.atu.aligntext.AlignTextView;

public class MainActivity extends AppCompatActivity {
    private AlignTextView atv1;
    private AlignTextView atv2;
    private AlignTextView atv3;
    private AlignTextView atv4;
    private AlignTextView atv5;
    private AlignTextView atv6;
    private AlignTextView atv7;
    private AlignTextView atv8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        atv1 = findViewById(R.id.atv1);
        atv2 = findViewById(R.id.atv2);
        atv3 = findViewById(R.id.atv3);
        atv4 = findViewById(R.id.atv4);
        atv5 = findViewById(R.id.atv5);
        atv6 = findViewById(R.id.atv6);
        atv7 = findViewById(R.id.atv7);
        atv8 = findViewById(R.id.atv8);

        atv1.setText("我");
        atv2.setText("我国");
        atv3.setText("我爱国");
        atv4.setText("我爱中国");
        atv5.setText("我爱你中国");
        atv6.setText("我只a你2国");
        atv7.setText("我永远爱你中国");
        atv8.setText("我只永远爱你中国");
    }
}
