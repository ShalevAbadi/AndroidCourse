package com.example.crazydrive;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TopTenListActivity extends AppCompatActivity {
    private TopTenDBHandler dbHandler;
    private ArrayList<TopTenRecord> records;
    private RecyclerView list_LST_records;
    private Adapter_RecordModel adapter_recordModel;
    ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        super.onCreate(savedInstanceState);
        dbHandler = new TopTenDBHandler(this);
        dbHandler.updateRecordsFromDB();
        records = dbHandler.getRecords();
        setContentView(R.layout.top_ten_list_activity);
        list_LST_records = findViewById(R.id.list_LST_records);
        initList();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private void initList() {
        records = dbHandler.getRecords();
        adapter_recordModel = new Adapter_RecordModel(this, records);
        list_LST_records.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list_LST_records.setLayoutManager(layoutManager);
        list_LST_records.setAdapter(adapter_recordModel);

        adapter_recordModel.SetOnItemClickListener(new Adapter_RecordModel.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, TopTenRecord record) {
                openRecord(record);
            }
        });
    }

    private void openRecord(TopTenRecord record) {
        Toast.makeText(this, record.getName(), Toast.LENGTH_SHORT).show();
    }

}


