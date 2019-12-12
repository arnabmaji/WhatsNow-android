package io.github.arnabmaji19.whatsnow.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dinuscxj.progressbar.CircleProgressBar;

import java.util.List;

import io.github.arnabmaji19.whatsnow.R;
import io.github.arnabmaji19.whatsnow.model.Lecture;

public class LecturesListAdapter extends RecyclerView.Adapter<LecturesListAdapter.LectureCardViewHolder> {

    private static final String TAG = "LecturesListAdapter";

    private List<Lecture> lecturesList;
    private DateTimeManager dateTimeManager;
    private String dayString;

    public LecturesListAdapter(List<Lecture> lecturesList, DateTimeManager dateTimeManager, String dayString) {
        this.lecturesList = lecturesList;
        this.dateTimeManager = dateTimeManager;
        this.dayString = dayString;
    }

    @NonNull
    @Override
    public LectureCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate custom layout for list row
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.lecture_card, parent, false);
        return new LectureCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LectureCardViewHolder holder, int position) {
        Lecture lecture = lecturesList.get(position);
        String lectureNo = "Lecture No. " + lecture.getPeriod();
        String courseName = "Course: " + lecture.getCourseName();
        String faculty = "Faculty: " + lecture.getFaculty();
        holder.lectureNumberView.setText(lectureNo);
        holder.courseNameView.setText(courseName);
        holder.facultyView.setText(faculty);
        holder.roomNoView.setText(lecture.getRoom());
        holder.lectureDurationView.setText(lecture.getDuration());

        //Update Lecture Progress bar for each lecture
        DateTimeManager.ProgressStatus status = dateTimeManager
                .getLectureProgressStatus(dayString, lecture.getPeriod());

        if (status.equals(DateTimeManager.ProgressStatus.COMPLETED)) {
            holder.progressBar.setProgress(100);
        } else if (status.equals(DateTimeManager.ProgressStatus.ONGOING)) {
            int elapsedTime = (int) Math.floor(dateTimeManager.getElapsedTimeFraction() * 100);
            holder.progressBar.setProgress(elapsedTime);
        } else {
            //For Upcoming classes it must be zero
            holder.progressBar.setProgress(0);
        }
    }

    @Override
    public int getItemCount() {
        return lecturesList.size();
    }

    public class LectureCardViewHolder extends RecyclerView.ViewHolder {

        TextView lectureNumberView;
        TextView courseNameView;
        TextView facultyView;
        TextView roomNoView;
        TextView lectureDurationView;
        CircleProgressBar progressBar;

        public LectureCardViewHolder(@NonNull View itemView) {
            super(itemView);
            //Initializing View fields
            this.lectureNumberView = itemView.findViewById(R.id.lectureNumberTextView);
            this.courseNameView = itemView.findViewById(R.id.courseNameTextView);
            this.facultyView = itemView.findViewById(R.id.facultyNameTextView);
            this.roomNoView = itemView.findViewById(R.id.roomTextView);
            this.lectureDurationView = itemView.findViewById(R.id.lectureDurationTextView);
            this.progressBar = itemView.findViewById(R.id.lectureProgressBar);
            this.progressBar.setMax(100);
        }
    }
}
