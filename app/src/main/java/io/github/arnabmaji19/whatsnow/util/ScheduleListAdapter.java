package io.github.arnabmaji19.whatsnow.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.github.arnabmaji19.whatsnow.R;
import io.github.arnabmaji19.whatsnow.model.ScheduleData;

public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ScheduleCardViewHolder> {

    private List<ScheduleData> scheduleDataList;
    private OnItemClickListener onItemClickListener;

    public ScheduleListAdapter(List<ScheduleData> scheduleDataList, OnItemClickListener onItemClickListener) {
        this.scheduleDataList = scheduleDataList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ScheduleCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate custom layout for list row
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.schedule_card, parent, false);
        return new ScheduleCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleCardViewHolder holder, int position) {
        holder.bind(scheduleDataList.get(position), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return scheduleDataList.size();
    }

    static class ScheduleCardViewHolder extends RecyclerView.ViewHolder {

        private TextView departmentTextView;
        private TextView batchTextView;
        private TextView sectionTextView;
        private TextView semesterTextView;
        private TextView lastUpdatedTextView;


        public ScheduleCardViewHolder(@NonNull View itemView) {
            super(itemView);
            //Linking view of lecture card
            departmentTextView = itemView.findViewById(R.id.departmentTextView);
            batchTextView = itemView.findViewById(R.id.batchTextView);
            sectionTextView = itemView.findViewById(R.id.sectionTextView);
            semesterTextView = itemView.findViewById(R.id.semesterTextView);
            lastUpdatedTextView = itemView.findViewById(R.id.lastUpdatedTextView);
        }

        public void bind(final ScheduleData data, final OnItemClickListener onItemClickListener) {
            departmentTextView.setText(data.getDepartment());
            batchTextView.setText(data.getBatch());
            sectionTextView.setText(data.getSection());
            semesterTextView.setText(data.getSemester());
            lastUpdatedTextView.setText(data.getLastUpdated());
            itemView.setOnClickListener(new View.OnClickListener() { //Set Click Listener for each row
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClicked(data);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(ScheduleData data);
    }
}
