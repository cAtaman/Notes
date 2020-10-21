package com.cAtaman.notes;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static final int DOWNLOAD_REQUEST_CODE = 0;
    private RecyclerView recyclerView;
    private String BASE_URL =  "https://atamanbc.tech";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        PendingIntent pendingResult = createPendingResult(
                DOWNLOAD_REQUEST_CODE, new Intent(), 0);
        final Intent intent = new Intent(getApplicationContext(), DownloadIntentService.class);
        intent.putExtra(DownloadIntentService.URL_EXTRA, BASE_URL + "/notes/v1/");
        intent.putExtra(DownloadIntentService.PENDING_RESULT_EXTRA, pendingResult);
        startService(intent);

        FloatingActionButton fab = findViewById(R.id.plus_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                stopService(intent);
                startService(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DOWNLOAD_REQUEST_CODE) {
            switch (resultCode) {
                case DownloadIntentService.INVALID_URL_CODE | DownloadIntentService.ERROR_CODE:
                    handleError();
                    break;
                case DownloadIntentService.RESULT_CODE:
                    handleNote(data);
                    break;
            }
            handleNote(data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void handleNote(Intent data) {
        ArrayList<String[]> notes = data.getParcelableExtra(DownloadIntentService.NOTES_RESULT_EXTRA);
        System.out.println(notes);

        String[] mDataset = new String[20];
        for (int i = 1; i < 20 + 1; i++) {
            mDataset[i-1] = "Count_Darkula " + i;
        }
        // specify an adapter (see also next example)
        RecyclerView.Adapter mAdapter = new NotesAdapter(mDataset);
        recyclerView.setAdapter(mAdapter);
    }


    private void handleError() {
        System.out.println("Error Occurred");
    }


//    private void handleRSS(Intent data) {
//        IllustrativeRSS rss = data.getParcelableExtra(DownloadIntentService.RSS_RESULT_EXTRA);
//        ViewGroup result = (ViewGroup)findViewById(R.id.results);
//        result.removeAllViews();
//        for (int i=0; i<rss.size(); i++) {
//            IllustrativeRSS.Item item = rss.get(i);
//            TextView v = new TextView(this);
//            v.setText(item.title);
//            result.addView(v);
//        }
//    }


//    ArrayList run(String url) throws IOException {
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().url(url).build();
//        Gson gson = new GsonBuilder().create();
//        String resp = "";
//
//        try {
//            Response response = client.newCall(request).execute();
//            resp = response.body().string();
//            System.out.println(resp);
//        } catch (NullPointerException e){
//            e.printStackTrace();
//        }
//
//        return gson.fromJson(resp, ArrayList.class);
//    }


//    SelectionTracker tracker = new SelectionTracker.Builder<>(
//            "my-selection-id",
//            recyclerView,
//            new StableIdKeyProvider(recyclerView),
//            new NotesDetailsLookup(recyclerView),
//            StorageStrategy.createLongStorage())
//            .withOnItemActivatedListener(myItemActivatedListener)
//            .build();

}
