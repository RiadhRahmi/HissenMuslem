package com.wridmob.hissenmuslem.activites;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;

import com.wridmob.hissenmuslem.R;
import com.wridmob.hissenmuslem.adapters.FullScreenImageAdapter;

import java.util.ArrayList;


public class FullScreenViewActivity extends Activity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_view);
        FullScreenImageAdapter adapter;


        Bundle bundle = this.getIntent().getExtras();
        String position = bundle.getString("position");

        viewPager = (ViewPager) findViewById(R.id.pager);
        ArrayList<String> data = new ArrayList<>();
        for (int i = 154; i > 0; i--) {
            data.add("p" + i + ".jpg");
        }
        adapter = new FullScreenImageAdapter(FullScreenViewActivity.this, data);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(154 - Integer.parseInt(position)); //8


    }


    public void onBackPressed() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("atPage", 154 - viewPager.getCurrentItem()); //146
        editor.apply();
        this.finish();

    }


}
