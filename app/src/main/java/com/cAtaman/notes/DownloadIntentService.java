package com.cAtaman.notes;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadIntentService extends IntentService {

    private static final String TAG = DownloadIntentService.class.getSimpleName();

    public static final String PENDING_RESULT_EXTRA = "pending_result";
    public static final String URL_EXTRA = "url";
    public static final String NOTES_RESULT_EXTRA = "url";

    public static final int RESULT_CODE = 0;
    public static final int INVALID_URL_CODE = 1;
    public static final int ERROR_CODE = 2;


    public DownloadIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        PendingIntent reply = intent.getParcelableExtra(PENDING_RESULT_EXTRA);
        try {
            try {
                URL url = new URL(intent.getStringExtra(URL_EXTRA));
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url).build();
                Gson gson = new GsonBuilder().create();

                Response response = client.newCall(request).execute();
                String resp = response.body().string();
                System.out.println(resp);
                ArrayList notes = gson.fromJson(resp, ArrayList.class);

                Intent result = new Intent();
                result.putExtra(NOTES_RESULT_EXTRA, notes);

                reply.send(this, RESULT_CODE, result);
            } catch (MalformedURLException exc) {
                reply.send(INVALID_URL_CODE);
            } catch (Exception exc) {
                reply.send(ERROR_CODE);
            }

        } catch (PendingIntent.CanceledException exc) {
            Log.i(TAG, "reply cancelled", exc);
        }
    }
}