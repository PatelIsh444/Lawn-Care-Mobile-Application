package com.example.lawn_care;


import android.os.SystemClock;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class AdminTests {
    @Rule
    public ActivityTestRule<SignIn> mActivityRule = new ActivityTestRule<>(SignIn.class);

    private void logInAdmin(){
        onView(withId(R.id.ET_loginEmail)).perform(typeText("admin@lawncare.com"));
        onView(withId(R.id.ET_loginPassword)).perform(typeText("admin"));
        onView(withId(R.id.BTN_login)).perform(click());
        SystemClock.sleep(1200);
    }

    private void createDeleteUser(){
        onView(withId(R.id.textView)).perform(click());
        onView(withId(R.id.ET_signupFirstName)).perform(typeText("Delete"));
        onView(withId(R.id.ET_signupLastName)).perform(typeText("Me"));
        onView(withId(R.id.ET_signupEmail)).perform(typeText("deleteme@gmail.com"));
        onView(withId(R.id.ET_signupPass)).perform(typeText("delete"));
        onView(withId(R.id.ET_signupPass2)).perform(typeText("delete"));
        onView(withId(R.id.ET_signupPass)).perform(typeText("1231231234"));
        onView(withId(R.id.ET_loginEmail)).perform(typeText("admin@lawncare.com"));
        onView(withId(R.id.jobSeekerRB)).perform(click());
        onView(withId(R.id.btn_Submit)).perform(click());
    }

    @Test
    public void deleteWorker(){
        createDeleteUser();
        logInAdmin();
        onView(withId(R.id.navigation_jobList)).perform(click());
        onView(withId(R.id.ET_searchWorkerQuery)).perform(typeText("Delete"));
        onView(withId(R.id.BTN_submitSearchWorkerQuery)).perform(click());
        //Wait to populate list
        SystemClock.sleep(1200);
        onView(withText("Delete User")).perform(click());
        SystemClock.sleep(1200);
    }

    @Test
    public void deleteProperty(){
        logInAdmin();
        onView(withId(R.id.btn_addYourData)).perform(click());
        //Wait to see if list populates
        SystemClock.sleep(1200);
        //Delete 1st item on list
        onView(withId(12000)).perform(click());
        //Wait for some time to see if failed
        SystemClock.sleep(1200);
    }

}