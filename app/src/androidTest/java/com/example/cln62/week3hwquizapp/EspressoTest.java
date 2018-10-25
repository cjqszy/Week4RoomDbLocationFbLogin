package com.example.cln62.week3hwquizapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.cln62.week3hwquizapp.main.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class EspressoTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkQuestionInitializer() {

        String questionToBeShowed = "Which countries are European countries ?";

        onView(withId(R.id.questionTextView))
                .check(matches(withText(questionToBeShowed)));
    }

    @Test
    public void checkNextQuestion() {

        String questionToBeShowed = "Which countries are Asian countries ?";

        onView(withId(R.id.nextButton)).perform(click());

        onView(withId(R.id.questionTextView))
                .check(matches(withText(questionToBeShowed)));
    }


    @Test
    public void checkPrevQuestion() {

        String questionToBeShowed = "Which countries are European countries ?";

        onView(withId(R.id.nextButton)).perform(click());

        onView(withId(R.id.prevButton)).perform(click());

        onView(withId(R.id.questionTextView))
                .check(matches(withText(questionToBeShowed)));
    }
}
