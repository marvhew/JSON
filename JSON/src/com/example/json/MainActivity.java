package com.example.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {
	private ProgressDialog progressDialog;
	private PostsBase posts = new PostsBase();
	private JSONObject obj = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		progressDialog = progressDialog.show(this, "", "Loading data...");
		JSONAsyncTask task = new JSONAsyncTask();
		task.execute("http://psporysz.boo.pl/inne/document.json");
	}

	private void Finish(String tmp) {
		try {
			obj = new JSONObject(tmp);
			posts.setPosts(jsonParser.Parse(obj));
			SetListAdapter();
			SetListItemClick();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String loadJSONFromAsset() {
		String json = null;
		try {

			InputStream is = getAssets().open("document.json");

			int size = is.available();

			byte[] buffer = new byte[size];

			is.read(buffer);

			is.close();

			json = new String(buffer, "UTF-8");

		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		return json;

	}

	public void SetListAdapter() {
		ListView list = (ListView) findViewById(R.id.List_Posts);
		ListRowAdapter adapter = new ListRowAdapter(this, R.layout.view_post,
				posts.getPosts());
		list.setAdapter(adapter);
	}

	public void SetListItemClick() {
		final Context context = this;
		ListView list = (ListView) findViewById(R.id.List_Posts);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, PostActivity.class);
				intent.putExtra("Post", posts.getPosts()[position]);
				startActivityForResult(intent, 2);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class JSONAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			InputStream is = null;
			String json = "";
			String stringURL = params[0];
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(stringURL);
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				return e.toString();
			} catch (IOException e) {
				e.printStackTrace();
				return e.toString();
			}

			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "UTF-8"));
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				is.close();
				json = sb.toString();
				return json;
			} catch (Exception e) {
				Log.e("Buffer Error", "error converting result " + e.toString());
				return e.toString();
			}
		}

		@Override
		protected void onPostExecute(String result) {
			Finish(result);
			progressDialog.dismiss();
			super.onPostExecute(result);
		}
	}
}
