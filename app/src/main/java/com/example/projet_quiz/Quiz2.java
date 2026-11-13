package com.example.projet_quiz;

public class Quiz2 extends BaseQuizActivity {
    @Override protected int layoutId() { return R.layout.activity_quiz2; }
    @Override protected int correctAnswerId() { return R.id.rdb1; }
    @Override protected int questionNumber() { return 2; }
    @Override protected Class<?> nextScreen() { return Quiz3.class; }
}
