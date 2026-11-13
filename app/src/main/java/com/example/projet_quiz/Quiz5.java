package com.example.projet_quiz;

public class Quiz5 extends BaseQuizActivity {
    @Override protected int layoutId() { return R.layout.activity_quiz5; }
    @Override protected int correctAnswerId() { return R.id.rdb2; }
    @Override protected int questionNumber() { return 5; }
    @Override protected Class<?> nextScreen() { return Score.class; }
}
