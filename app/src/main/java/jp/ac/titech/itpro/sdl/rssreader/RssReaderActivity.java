package jp.ac.titech.itpro.sdl.rssreader;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by daron on 2017/07/06.
 */

public class RssReaderActivity extends ListActivity implements View.OnClickListener{
    private ArrayList mItems;
    private RssListAdapter mAdapter;
    private String RSS_FEED_URL = "http://www.2nn.jp/rss/index.rdf";
    private final static String KEY_NAME = "RSS_FEED_URL";
    private EditText url;
    private InputMethodManager inputMethodManager;
    private ConstraintLayout mainLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            String feed_url = savedInstanceState.getString(KEY_NAME);
            if (feed_url != null){
                this.RSS_FEED_URL = feed_url;
            }
        }

        setContentView(R.layout.activity_main);
        url = (EditText) findViewById(R.id.editText);

        findViewById(R.id.gobtn).setOnClickListener(this);
        findViewById(R.id.rldbtn).setOnClickListener(this);
        findViewById(R.id.pastebtn).setOnClickListener(this);

        mItems = new ArrayList();
        mAdapter = new RssListAdapter(this, mItems);

        RssParserTask task = new RssParserTask(this, mAdapter);
        task.execute(RSS_FEED_URL);

        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        mainLayout = (ConstraintLayout) findViewById(R.id.mainLayout);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rldbtn:
                mItems = new ArrayList();
                mAdapter = new RssListAdapter(this, mItems);
                RssParserTask task = new RssParserTask(this, mAdapter);
                task.execute(RSS_FEED_URL);
                break;
            case R.id.gobtn:
                Log.v("print", url.getText().toString());
                RSS_FEED_URL = url.getText().toString();
                mItems = new ArrayList();
                mAdapter = new RssListAdapter(this, mItems);
                RssParserTask task2 = new RssParserTask(this, mAdapter);
                task2.execute(RSS_FEED_URL);
                inputMethodManager.hideSoftInputFromWindow(mainLayout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                mainLayout.requestFocus();
                break;
            case R.id.pastebtn:
                ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData cd = cm.getPrimaryClip();
                if(cd != null){
                    ClipData.Item item = cd.getItemAt(0);
                    url.setText(item.getText());
                    RSS_FEED_URL = item.getText().toString();
                    mItems = new ArrayList();
                    mAdapter = new RssListAdapter(this, mItems);
                    RssParserTask task3 = new RssParserTask(this, mAdapter);
                    task3.execute(RSS_FEED_URL);
                }

                break;
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Item item = (Item)mItems.get(position);
        //Log.v("print", (String) item.getLink());
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse((String) item.getLink()));
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_NAME, RSS_FEED_URL);
    }
}

