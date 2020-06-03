package ng.hotels.telexlibraries;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class TelexManager {

    private Activity activity;
    private String organization;
    private String team;
    private int theme = R.style.DefaultTheme;
    private int frameLayout = 0;

    public static String TELEX = "telex";

    public static String organizations;

    public static String teams;



    public TelexManager(Activity activity) {
        this.activity = activity;
    }


    public void initializeAsActivity() {

        if (activity != null) {

            Intent intent = new Intent(activity, SupportActivity.class);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            Log.d(TELEX, "Context is required!");
        }

    }

    public void initializeAsFragment() {

        if (activity != null) {

            FragmentTransaction transaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
            transaction.replace(frameLayout, new SupportFragment())
                    .commitNow();
        } else {
            Log.d(TELEX, "Context is required!");
        }

    }



    public TelexManager setOrganization(String organization) {
        this.organization = organization;
        organizations = organization;
        return this;
    }

    public TelexManager setTeam(String team) {
        this.team = team;
        teams = team;
        return this;
    }

    public TelexManager setTheme(int theme) {
        this.theme = theme;
        return this;
    }

    public TelexManager setFrameLayout(int frameLayout) {
        this.frameLayout = frameLayout;
        return this;
    }



    public String getOrganization() {
        return organization;
    }

    public int getTheme() {
        return theme;
    }

    public int getFrameLayout() {
        return frameLayout;
    }

    public String getTeam() {
        return this.team;
    }

}
