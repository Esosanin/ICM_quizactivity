package com.example.quizactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnTrue, btnFalse, btnNext;
    private TextView textViewQuestion, textViewPoints;
    private int Points = 0;

    private int mCurrentIndex = 0;
    private Question[] mQuestions = new Question[]{
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
        setContentView(R.layout.activity_main);

        textViewQuestion = (TextView) findViewById(R.id.textview_question);
        textViewPoints = (TextView) findViewById(R.id.textview_points);

        updateQuestion();
        totalPoints();


        btnFalse = (Button) findViewById(R.id.btn_false);
        btnTrue = (Button) findViewById(R.id.btn_true);
        btnNext = (Button) findViewById(R.id.btn_next);

        btnFalse.setOnClickListener(this::onClick);
        btnTrue.setOnClickListener(this::onClick);
        btnNext.setOnClickListener(this::onClick);

    }

    @SuppressLint("NonConstantResourceId")
    private void onClick(View v){
        switch(v.getId()){
            case R.id.btn_false:

                checkAnswer(false);

                btnTrue.setEnabled(false);
                btnFalse.setEnabled(false);
                break;
            case R.id.btn_true:

                checkAnswer(true);

                btnTrue.setEnabled(false);
                btnFalse.setEnabled(false);
                break;
            case R.id.btn_next:
                btnTrue.setEnabled(true);
                btnFalse.setEnabled(true);
                mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
                updateQuestion();
                break;
        }
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