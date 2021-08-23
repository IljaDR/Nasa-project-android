package be.idr.nasaproject;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import be.idr.nasaproject.DB.EarthData;
import be.idr.nasaproject.UI.EarthActivity;
import be.idr.nasaproject.UI.MainActivity;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.intent.Intents.intended;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RatingRegisteredTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Assert that the ratings get saved
     * Open earth, open first image, click dislike, click like, go back twice, open earth, open first image, assert that it is still liked
     * This test could fail if a new image gets uploaded during the test
     */
    @Test
    public void rateImage() {
        onView(withId(R.id.EarthImageButton))
                .perform(click());
        onView(withId(R.id.recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.btnDislike))
                .perform(click());
        onView(withId(R.id.btnLike))
                .perform(click());
        Intents.init();
        Espresso.pressBack();
        Espresso.pressBack();
        onView(withId(R.id.EarthImageButton))
                .perform(click());
        onView(withId(R.id.recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasExtra(EarthActivity.EARTH_RATING, EarthData.Rating.LIKE));
    }
}