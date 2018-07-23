package young.project.android.com.bakeaide;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import young.project.android.com.bakeaide.ui.MainActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BasicUiTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init(){
        mainActivityActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();

    }

    @Test
    public void checkFragmentContainerIsDisplayedTest(){
        Espresso.onView(ViewMatchers.withId(R.id.portrait_fragment_container)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void checkToolbarShowsTitleTest(){
        Espresso.onView(ViewMatchers.withText(R.string.app_name)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void checkRecyclerViewIsDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.rv_recipe_list)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void checkClickingRecyclerViewOpensOverview(){
        Espresso.onView(ViewMatchers.withText("Cheesecake")).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Ingredients")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

}
