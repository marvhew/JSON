package com.example.json;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListRowAdapter extends ArrayAdapter<Post> {
	private Context context;
	private int layoutResourceID;
	private Post data[];

	public ListRowAdapter(Context _context, int _layoutRes, Post _data[]) {
		super(_context, _layoutRes, _data);
		context = _context;
		layoutResourceID = _layoutRes;
		data = _data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View row = convertView;
		RowHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceID, parent, false);

			holder = new RowHolder();
			holder.RowDate = (TextView) row.findViewById(R.id.Txt_Date);
			holder.RowAuthor = (TextView) row.findViewById(R.id.Txt_Author);
			holder.Topic = (TextView) row.findViewById(R.id.Txt_Topic);
			row.setTag(holder);

			holder.Image = (ImageView) row.findViewById(R.id.imageView1);

		} else {
			holder = (RowHolder) row.getTag();
		}

		Post item = data[position];
		holder.RowDate.setText(item.getDate());
		holder.RowAuthor.setText(item.getAuthor());
		holder.Topic.setText(item.getTopic());
		if (!item.getImage_URL().isEmpty()) {
			DownloadImageTask task = new DownloadImageTask(holder.Image);
			task.execute(item.getImage_URL());
		}
		else
		{
			holder.Image.setImageResource(android.R.color.transparent);
		}
		return row;
	}

	public static Drawable LoadImageFromWebOperations(String url) {
		try {
			InputStream is = (InputStream) new URL(url).getContent();
			Drawable d = Drawable.createFromStream(is, "src name");
			return d;
		} catch (Exception e) {
			return null;
		}
	}

	static class RowHolder {
		TextView RowDate;
		TextView RowAuthor;
		TextView Topic;
		ImageView Image;
	}
	
	public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap>
    {
    	ImageView bmImage;
    	public DownloadImageTask(ImageView bmImage)
    	{
    		this.bmImage = bmImage;
    	}

		@Override
		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap bitmap = null;
			try {
				InputStream is = new java.net.URL(urldisplay).openStream();
				bitmap = BitmapFactory.decodeStream(is);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e("Error",e.getMessage());
				e.printStackTrace();
			}
			return bitmap;
		}
    	@Override
    	protected void onPostExecute(Bitmap result) {
    		// TODO Auto-generated method stub
    		bmImage.setImageBitmap(result);
    		super.onPostExecute(result);
    	}
    } 
}
