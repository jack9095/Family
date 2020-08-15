package com.maxxipoint.layoutmanager;

import android.app.Activity;
import android.os.Bundle;
import androidx.recyclerview.widget.*;

import com.maxxipoint.layoutmanager.adapter.SlideAdapter;
import com.maxxipoint.layoutmanager.widget.MyRecyclerView;


public class StackActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack);
        initView();
    }

    private void initView() {
        final MyRecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new SlideAdapter());
    }

}
