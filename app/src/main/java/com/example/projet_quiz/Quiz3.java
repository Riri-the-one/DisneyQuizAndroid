package com.example.projet_quiz;

public class Quiz3 extends BaseQuizActivity {
    @Override protected int layoutId() { return R.layout.activity_quiz3; }
    @Override protected int correctAnswerId() { return R.id.rdb3; }
    @Override protected int questionNumber() { return 3; }
    @Override protected Class<?> nextScreen() { return Quiz4.class; }
}
