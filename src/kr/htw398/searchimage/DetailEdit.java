/*
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kr.htw398.searchimage;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;

public class DetailEdit extends Activity {

	private TextView mTitleText;
	private ImageView image;
	private EditText edittext;
	private Long mRowId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		setContentView(R.layout.detailed_view);
		setTitle("Test");

		mTitleText = (TextView) findViewById(R.id.textView);
		image = (ImageView) findViewById(R.id.imageView);
		//eText = (EditText) findViewById(R.id.editText);

		Bundle extras = getIntent().getExtras();
		String url =  extras.getString(CustomizedListView.KEY_LINK);
		String title =  extras.getString(CustomizedListView.KEY_TITLE);
		if (title !=null){
			Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
			mTitleText.setText(title, BufferType.NORMAL);
		}
		//String url = "http://blog.joins.com/usr/f/o/forum1004/54/%EC%9A%94%EB%A6%AC.jpg";
		if (url !=null)
			new DownloadImageTask(image).execute(url);;

			addKeyListener();
	}



	public void addKeyListener() {

		edittext = (EditText) findViewById(R.id.editText);

		// add a keylistener to keep track user input
		edittext.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				// if keydown and "enter" is pressed
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {

					// display a floating message
				//	Toast.makeText(getApplicationContext(),
					//		edittext.getText(), Toast.LENGTH_SHORT).show();
					Intent i = getIntent();
	                i.putExtra( CustomizedListView.KEY_SEARCH, edittext.getText().toString());
	                setResult(RESULT_OK, i);
	                overridePendingTransition(R.anim.leftin, R.anim.leftout);
	                finish();
					return true;

				} else if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_9)) {

					// display a floating message
					Toast.makeText(getApplicationContext(),
							"Number 9 is pressed!", Toast.LENGTH_SHORT).show();
					return true;
				}

				return false;
			}
		});
	}
	











		@Override
		protected void onSaveInstanceState(Bundle outState) {
			super.onSaveInstanceState(outState);
			saveState();

		}

		@Override
		protected void onPause() {
			super.onPause();
			saveState();
		}

		@Override
		protected void onResume() {
			super.onResume();

		}

		private void saveState() {
			String title = mTitleText.getText().toString();



		}

}
