package com.example.crazydrive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class Adapter_RecordModel extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<TopTenRecord> records;
    private OnItemClickListener mItemClickListener;

    public Adapter_RecordModel(Context context, ArrayList<TopTenRecord> records) {
        this.context = context;
        this.records = records;
    }

    public void updateList(ArrayList<TopTenRecord> records) {
        this.records = records;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.records_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            final TopTenRecord record = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.record_LBL_title.setText(position+1 + ". " + record.toString());
        }
    }

    @Override
    public int getItemCount() {
        if(records == null){
            return 0;
        }
        return records.size();
    }

    private TopTenRecord getItem(int position) {
        return records.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView record_LBL_title;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.record_LBL_title = itemView.findViewById(R.id.record_LBL_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), getItem(getAdapterPosition()));
                }
            });
        }
    }

    public void removeAt(int position) {
        records.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, records.size());
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, TopTenRecord record);
    }

}
