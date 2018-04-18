package com.wridmob.hissenmuslem.activites;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.widget.ShareDialog;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.wridmob.hissenmuslem.R;
import com.wridmob.hissenmuslem.adapters.SummaryAdapter;
import com.wridmob.hissenmuslem.entities.Summary;
import com.wridmob.hissenmuslem.receivers.ScheduleDiker;

import java.util.ArrayList;
import java.util.List;

public class SummaryActivity extends AppCompatActivity {

    ListView listViewSummary;
    SummaryAdapter summaryAdapter;
    List<Summary> data;
    ShareDialog shareDialog;
    private MaterialSearchView searchView;
    String[] atPage, name;
    Toolbar toolbar;
    private ScheduleDiker scheduleDiker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        FacebookSdk.sdkInitialize(getApplicationContext());
        shareDialog = new ShareDialog(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        centerToolbarTitle(toolbar);

        scheduleDiker = new ScheduleDiker(this);
        scheduleDiker.doBindService();

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.cursor);
        searchView.setEllipsize(true);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getInt("atPage", 0) != 0) {
            Intent intent = new Intent(SummaryActivity.this, FullScreenViewActivity.class);
            Bundle b = new Bundle();
            b.putString("position", "" + preferences.getInt("atPage", 0));
            intent.putExtras(b);
            startActivity(intent);
        }

        data = new ArrayList<>();
        atPage = getResources().getStringArray(R.array.atPage);
        name = getResources().getStringArray(R.array.name);
        for (int i = 0; i < atPage.length; i++) {
            Summary s = new Summary();
            s.atPage = Integer.parseInt(atPage[i]);
            s.name = name[i];
            data.add(s);
        }

        listViewSummary = (ListView) findViewById(R.id.list_summary);
        summaryAdapter = new SummaryAdapter(this, data);

        listViewSummary.setAdapter(summaryAdapter);
        listViewSummary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SummaryActivity.this, FullScreenViewActivity.class);
                Bundle b = new Bundle();
                b.putString("position", "" + summaryAdapter.getItem(position).atPage);
                intent.putExtras(b);
                startActivityForResult(intent, 9999);
            }
        });

        searchView.setSuggestions(getResources().getStringArray(R.array.name));

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                summaryAdapter.getFilter().filter(query);
                toolbar.setTitle(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                summaryAdapter.getFilter().filter("");
                toolbar.setTitle(getResources().getString(R.string.name_summary));

            }

            @Override
            public void onSearchViewClosed() {
            }
        });

        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SummaryActivity.this, FullScreenViewActivity.class);
                Bundle b = new Bundle();
                b.putString("position", "" + atPage[getPosition((String) parent.getItemAtPosition(position))]);
                intent.putExtras(b);
                startActivity(intent);
                searchView.closeSearch();
            }
        });

    }

    static void centerToolbarTitle(Toolbar toolbar) {
        final CharSequence title = toolbar.getTitle();
        final ArrayList<View> outViews = new ArrayList<>(1);
        toolbar.findViewsWithText(outViews, title, View.FIND_VIEWS_WITH_TEXT);
        if (!outViews.isEmpty()) {
            final TextView titleView = (TextView) outViews.get(0);
            titleView.setGravity(Gravity.RIGHT);
            final Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) titleView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            toolbar.requestLayout();
        }
    }

    private int getPosition(String string) {
        int i = 0;
        while (!name[i].equals(string) && i < atPage.length) {
            i++;
        }
        return i;
    }

    public void share(View view) {
        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/jpg");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
        shareIntent.putExtra(Intent.EXTRA_TEXT,"http://play.google.com/store/apps/details?id=" + getPackageName());
        startActivity(Intent.createChooser(shareIntent, getResources().getString(R.string.share)));
    }

      /*
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle(getResources().getString(R.string.app_name))
                    .setContentDescription(getResources().getString(R.string.content))
                    .setContentUrl(Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName()))
                    .build();
            shareDialog.show(linkContent);
        }
        */

    public void rating(View view) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    public void myApplications(View view) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://developer?id=WridMob")));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=WridMob")));
        }


    }



    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.summary, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }



    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }
            return;
        } else if (requestCode == 9999) {
            summaryAdapter.getFilter().filter("");
            toolbar.setTitle(getResources().getString(R.string.name_summary));
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}

/*
else if (requestCode == 1) {
           SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

            Calendar c = Calendar.getInstance();
            int year = Calendar.getInstance().get(Calendar.YEAR);
            int month = Calendar.getInstance().get(Calendar.MONTH);
            int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

            c.set(Calendar.YEAR,year);
            c.set(Calendar.MONTH,month);
            c.set(Calendar.DAY_OF_MONTH,day);
            c.set(Calendar.MINUTE, 40);
            c.set(Calendar.SECOND, 00);
            c.set(Calendar.MILLISECOND, 00);

            Date cd =c.getTime();

            if (preferences.getBoolean("title1", false)) {
                c.set(Calendar.HOUR_OF_DAY, 4);
                scheduleDiker.setAlarmForNotification(c, 12);
            }

            if (preferences.getBoolean("title2", false)) {
                c.set(Calendar.HOUR_OF_DAY, 4);
                scheduleDiker.setAlarmForNotification(c, 13);
            }

            if (preferences.getBoolean("title3", false)) {
                c.set(Calendar.HOUR_OF_DAY, 4);
                scheduleDiker.setAlarmForNotification(c, 14);
            }


 */
