package com.example.projet_quiz;

public class Quiz1 extends BaseQuizActivity {
    @Override protected int layoutId() { return R.layout.activity_quiz1; }
    @Override protected int correctAnswerId() { return R.id.rdb3; }
    @Override protected int questionNumber() { return 1; }
    @Override protected Class<?> nextScreen() { return Quiz2.class; }
}
