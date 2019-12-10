package io.github.arnabmaji19.whatsnow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dinuscxj.progressbar.CircleProgressBar;

import io.github.arnabmaji19.whatsnow.model.Lecture;

public class DashboardFragment extends Fragment {

    private Lecture currentLecture;
    private Lecture nextLecture;
    private double elapsedTimeFraction;

    //Constructor to get current and next lecture details
    DashboardFragment(Lecture currentLecture, Lecture nextLecture, double elapsedTimeFraction) {
        this.currentLecture = currentLecture;
        this.nextLecture = nextLecture;
        this.elapsedTimeFraction = elapsedTimeFraction;
    }
    
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragement_dashboard, container, false);

        if (currentLecture != null) {
            //Linking and updating views of ongoing lecture card
            CircleProgressBar elapsedTimeView = view.findViewById(R.id.currentLectureProgressBar);
            //Calculating elapsed time for current lecture
            elapsedTimeView.setMax(100);
            int progress = (int) Math.floor(elapsedTimeFraction * 100);
            elapsedTimeView.setProgress(progress);
            LectureCard lectureCard = new LectureCard(
                    (TextView) view.findViewById(R.id.currentLectureNumberTextView),
                    (TextView) view.findViewById(R.id.currentCourseNameTextView),
                    (TextView) view.findViewById(R.id.currentFacultyNameTextView),
                    (TextView) view.findViewById(R.id.currentRoomTextView),
                    (TextView) view.findViewById(R.id.currentLectureDurationTextView)
            );
            lectureCard.updateCard(currentLecture);
        }

        if (nextLecture != null) {
            //Linking and updating views of upcoming lecture card
            LectureCard lectureCard = new LectureCard(
                    (TextView) view.findViewById(R.id.nextLectureNumberTextView),
                    (TextView) view.findViewById(R.id.nextCourseNameTextView),
                    (TextView) view.findViewById(R.id.nextFacultyNameTextView),
                    (TextView) view.findViewById(R.id.nextRoomTextView),
                    (TextView) view.findViewById(R.id.nextLectureDurationTextView)
            );
            lectureCard.updateCard(nextLecture);
        }
        return view;
    }

    private static class LectureCard {
        private TextView lectureNumberView;
        private TextView courseNameView;
        private TextView facultyView;
        private TextView roomNoView;
        private TextView lectureDurationView;

        private LectureCard(TextView lectureNumberView,
                            TextView courseNameView,
                            TextView facultyView,
                            TextView roomNoView,
                            TextView lectureDurationView) {
            this.lectureNumberView = lectureNumberView;
            this.courseNameView = courseNameView;
            this.facultyView = facultyView;
            this.roomNoView = roomNoView;
            this.lectureDurationView = lectureDurationView;
        }

        private void updateCard(Lecture lecture) {
            String lectureNo = "Lecture No. " + lecture.getPeriod();
            String courseName = "Course: " + lecture.getCourseName();
            String faculty = "Faculty: " + lecture.getFaculty();
            this.lectureNumberView.setText(lectureNo);
            this.courseNameView.setText(courseName);
            this.facultyView.setText(faculty);
            this.roomNoView.setText(lecture.getRoom());
            this.lectureDurationView.setText(lecture.getDuration());
        }
    }
}
