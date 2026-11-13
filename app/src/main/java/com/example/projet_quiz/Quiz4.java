package com.example.projet_quiz;

public class Quiz4 extends BaseQuizActivity {
    @Override protected int layoutId() { return R.layout.activity_quiz4; }
    @Override protected int correctAnswerId() { return R.id.rdb3; }
    @Override protected int questionNumber() { return 4; }
    @Override protected Class<?> nextScreen() { return Quiz5.class; }
}
