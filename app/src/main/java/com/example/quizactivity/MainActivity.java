package com.example.quizactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnTrue, btnFalse, btnNext;
    private TextView textViewQuestion, textViewPoints;
    private ProgressBar mProgressBar;

    private Bundle mSavedInstanceState;

    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_POINTS = "points";
    private static final String KEY_PROGRESS = "progress";
    private static final String KEY_ANSWER = "answer";

    private int Points = 0;
    private boolean answer = false;
    private int mCurrentIndex = 0;
    private final Question[] mQuestions = new Question[]{
            new Question(R.string.question_1, true),
            new Question(R.string.question_2, true),
            new Question(R.string.question_3, false),
            new Question(R.string.question_4, false),
            new Question(R.string.question_5, true),
            new Question(R.string.question_6, true),
            new Question(R.string.question_7, true),
            new Question(R.string.question_8, false),
            new Question(R.string.question_9, true),
            new Question(R.string.question_10, true)
    };

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        textViewQuestion = (TextView) findViewById(R.id.textview_question);
        textViewPoints = (TextView) findViewById(R.id.textview_points);

        updateQuestion();
        totalPoints();

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar_question);
        mProgressBar.setMax(mQuestions.length);
        setProgressBar();

        btnFalse = (Button) findViewById(R.id.btn_false);
        btnTrue = (Button) findViewById(R.id.btn_true);
        btnNext = (Button) findViewById(R.id.btn_next);

        btnFalse.setOnClickListener(this::onClick);
        btnTrue.setOnClickListener(this::onClick);
        btnNext.setOnClickListener(this::onClick);

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
            Points = savedInstanceState.getInt(KEY_POINTS);
            mProgressBar.setProgress(savedInstanceState.getInt(KEY_PROGRESS));
            answer = savedInstanceState.getBoolean(KEY_ANSWER);
        }

    }

    public void setProgressBar(){
        if(mCurrentIndex>mQuestions.length)
            mProgressBar.setProgress(mCurrentIndex);
        else
            mProgressBar.setProgress(mCurrentIndex+1);
    }

    public void setAnswer(){
        if(answer){
            btnTrue.setEnabled(false);
            btnFalse.setEnabled(false);
        }else{
            btnTrue.setEnabled(true);
            btnFalse.setEnabled(true);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");

        if(mSavedInstanceState != null) {
            textViewQuestion.setText(mQuestions[mSavedInstanceState.getInt(KEY_INDEX)].getTextResId());
            mProgressBar.setProgress(mSavedInstanceState.getInt(KEY_PROGRESS));
            Points = mSavedInstanceState.getInt(KEY_POINTS);

            if(mCurrentIndex >= mQuestions.length - 1)
                btnNext.setEnabled(false);

            answer = mSavedInstanceState.getBoolean(KEY_ANSWER);

            setAnswer();
            totalPoints();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    public void onSaveInstanceState (@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putInt(KEY_POINTS, Points);
        savedInstanceState.putInt(KEY_PROGRESS, mProgressBar.getProgress());
        savedInstanceState.putBoolean(KEY_ANSWER, answer);

        mSavedInstanceState = savedInstanceState;
    }

    @SuppressLint("NonConstantResourceId")
    private void onClick(View v){
        switch(v.getId()){
            case R.id.btn_false:
                checkAnswer(false);
                answer = true;
                break;
            case R.id.btn_true:
                checkAnswer(true);
                answer = true;
                break;
            case R.id.btn_next:
                btnTrue.setEnabled(true);
                btnFalse.setEnabled(true);
                answer = false;
                mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
                updateQuestion();
                setProgressBar();
                break;
        }
        setAnswer();

        if(mCurrentIndex >= mQuestions.length - 1)
            btnNext.setEnabled(false);
    }

    private void updateQuestion(){
        int question = mQuestions[mCurrentIndex].getTextResId();
        textViewQuestion.setText(question);
    }

    private void checkAnswer (boolean userPressedTrue){
        boolean answerisTrue = mQuestions[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;

        if(answerisTrue == userPressedTrue) {
            messageResId = R.string.r_correct;
            Points++;
            totalPoints();
        }else
            messageResId = R.string.r_incorrect;

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("SetTextI18n")
    private void totalPoints (){
        textViewPoints.setText("Puntos: "+Points+"/"+mQuestions.length);
    }


}