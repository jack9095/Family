package com.xxzy.family.round.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.xxzy.family.R;

public class RoundActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);
    }

    public void click(View view) {
        Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
    }

    public void bgclick(View view) {
        Toast.makeText(this, "Bg Click", Toast.LENGTH_SHORT).show();
    }
}
