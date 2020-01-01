package com.example.crazydrive;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FragmentTopTenList extends Fragment {

    private View view;
    private TopTenDBHandler dbHandler;
    private ArrayList<TopTenRecord> records;
    private RecyclerView list_LST_records;
    private Adapter_RecordModel adapter_recordModel;
    private Context context;

    public FragmentTopTenList(Context context){
        super();
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view==null){
            view = inflater.inflate(R.layout.top_ten_list_activity, container, false);
        }
        list_LST_records = view.findViewById(R.id.list_LST_records);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        list_LST_records.setLayoutManager(layoutManager);
        createList();
        initList();
        return view;

    }

    private void createList(){
        dbHandler = new TopTenDBHandler(getContext());
        dbHandler.updateRecordsFromDB();
        records = dbHandler.getRecords();
        adapter_recordModel = new Adapter_RecordModel(getActivity(), records);
    }

    private void initList() {
        list_LST_records.setHasFixedSize(true);
        list_LST_records.setAdapter(adapter_recordModel);

        adapter_recordModel.SetOnItemClickListener(new Adapter_RecordModel.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, TopTenRecord record) {
                clickRecord(record);
            }
        });
    }

    private void clickRecord(TopTenRecord record) {
        ((TopTenActivity)context).updateMap(record);
    }

}

