package br.com.dlweb.maternidade;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.google.android.material.internal.ContextUtils.getActivity;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.is;

import android.content.Context;
import android.view.View;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

@RunWith(AndroidJUnit4.class)
public class MedicoInstrumentedTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    private View mDecorView;

    @Before
    public void setUp() {
        Intents.init();
        activityScenarioRule.getScenario().onActivity(activity -> mDecorView = activity.getWindow().getDecorView());
    }

    @Test
    public void adicionar() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Médico")).perform(click());
        onView(withText("Adicionar")).perform(click());

        onView(withId(R.id.editTextNome)).perform(click());
        onView(withId(R.id.editTextNome)).perform(typeText("Lucas"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.editTextEspecialidade)).perform(click());
        onView(withId(R.id.editTextEspecialidade)).perform(typeText("Obstetra"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.editTextCelular)).perform(click());
        onView(withId(R.id.editTextCelular)).perform(typeText("47123451234"));
        Espresso.closeSoftKeyboard();

        onView(withText("Salvar")).perform(click());

        onView(withText("Médico salvo!")).inRoot(withDecorView(not(mDecorView))).check(matches(isDisplayed()));
    }

    @Test
    public void editar() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Médico")).perform(click());

        onView(withId(R.id.recyclerViewMedicos))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.editTextNome)).perform(click());
        onView(withId(R.id.editTextNome)).perform(typeText(" Debatin"));
        Espresso.closeSoftKeyboard();

        onView(withText("Salvar")).perform(click());

        onView(withText("Médico editado!")).inRoot(withDecorView(not(mDecorView))).check(matches(isDisplayed()));
    }

    @Test
    public void excluir() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Médico")).perform(click());

        onView(withId(R.id.recyclerViewMedicos))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withText("Excluir")).perform(click());

        onView(withText("Sim")).perform(click());

        onView(withText("Médico excluído!")).inRoot(withDecorView(not(mDecorView))).check(matches(isDisplayed()));
    }
}
