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

    //TODO: add a constructor to get these value while initialization
    
    
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragement_dashboard, container, false);

        //Linking and updating views of ongoing lecture card
        TextView lectureNumberView = view.findViewById(R.id.currentLectureNumberTextView);
        TextView courseNameView = view.findViewById(R.id.currentCourseNameTextView);
        TextView facultyView = view.findViewById(R.id.currentFacultyNameTextView);
        TextView roomNoView = view.findViewById(R.id.currentRoomTextView);
        CircleProgressBar elapsedTimeView = view.findViewById(R.id.currentLectureProgressBar);
        LectureCard lectureCard = new LectureCard(
                lectureNumberView,
                courseNameView,
                facultyView,
                roomNoView,
                elapsedTimeView
        );
        lectureCard.updateCard(currentLecture);

        //Linking and updating views of upcoming lecture card
        lectureNumberView = view.findViewById(R.id.nextLectureNumberTextView);
        courseNameView = view.findViewById(R.id.nextCourseNameTextView);
        facultyView = view.findViewById(R.id.nextFacultyNameTextView);
        roomNoView = view.findViewById(R.id.nextRoomTextView);
        elapsedTimeView = view.findViewById(R.id.nextLectureProgressBar);
        lectureCard = new LectureCard(
                lectureNumberView,
                courseNameView,
                facultyView,
                roomNoView,
                elapsedTimeView
        );
        lectureCard.updateCard(nextLecture);
        return view;
    }

    private static class LectureCard {
        TextView lectureNumberView;
        TextView courseNameView;
        TextView facultyView;
        TextView roomNoView;
        CircleProgressBar elapsedTimeView;

        private LectureCard(TextView lectureNumberView,
                            TextView courseNameView,
                            TextView facultyView,
                            TextView roomNoView,
                            CircleProgressBar elapsedTimeView) {
            this.lectureNumberView = lectureNumberView;
            this.courseNameView = courseNameView;
            this.facultyView = facultyView;
            this.roomNoView = roomNoView;
            this.elapsedTimeView = elapsedTimeView;
        }

        private void updateCard(Lecture lecture) {
            lectureNumberView.setText(lecture.getPeriod());
            courseNameView.setText(lecture.getCourseName());
            facultyView.setText(lecture.getFaculty());
            roomNoView.setText(lecture.getRoom());
        }
    }
}
