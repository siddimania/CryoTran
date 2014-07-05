/*
 * @author: Siddhartha Dimania
 * 
 */
package com.cryo.translator;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

public class CryoTranActivity extends Activity {

	private Spinner spinner1, spinner2;
	private Button btnSubmit;

	private EditText et;
	private TextView et2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cryo_tran);

		boolean isConnectionAvailable = isNetworkAvailable(this);

		addItemsOnSpinner2();
		addListenerOnSpinnerItemSelection();
		addListenerOnButton();

	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();

			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					Log.i("Class", info[i].getState().toString());
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void addItemsOnSpinner2() {

		spinner2 = (Spinner) findViewById(R.id.spinner2);
		List<String> list = new ArrayList<String>();
		list.add("FRENCH");
		list.add("SPANISH");
		list.add("GREEK");
		list.add("ENGLISH");
		list.add("HINDI");
		list.add("RUSSIAN");
		list.add("GERMAN");
		list.add("ITALIAN");
		list.add("JAPANESE");
		list.add("ARABIC");
		list.add("URDU");
		list.add("HUNGARIAN");
		list.add("SWEDISH");
		list.add("PERSIAN");
		list.add("BULGARIAN");
		list.add("DANISH");
		list.add("DUTCH");
		list.add("ESTONIAN");
		list.add("FINNISH");
		list.add("INDONESIAN");
		list.add("KOREAN");
		list.add("POLISH");
		list.add("PORTUGUESE");
		list.add("ROMANIAN");
		list.add("TURKISH");
		list.add("VIETNAMESE");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(dataAdapter);
	}

	public void addListenerOnButton() {

		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		btnSubmit = (Button) findViewById(R.id.btnSubmit);

		et = (EditText) findViewById(R.id.editText1);
		et2 = (TextView) findViewById(R.id.textView1);

		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				boolean isConnectionAvailable = isNetworkAvailable(getApplicationContext());
				if (isConnectionAvailable == true) {

					new AsyncTask<String, Void, String>() {

						@Override
						protected String doInBackground(String... params) {
							String fromLang = String.valueOf(spinner1
									.getSelectedItem());
							String toLang = String.valueOf(spinner2
									.getSelectedItem());

							String engword = et.getText().toString();

							Translate.setClientId("CLIENT_ID_HERE");
							Translate.setClientSecret("CLIENT_SECRET_HERE");
							
							try {
								return Translate.execute(engword,
										Language.valueOf(fromLang),
										Language.valueOf(toLang));
							} catch (Exception e) {

								e.printStackTrace();
							}
							return null;
						}

						protected void onPostExecute(String translatedText) {

							Typeface tf = Typeface.createFromAsset(getAssets(),
									"fonts/mangal.ttf");

							String toLang = String.valueOf(spinner2
									.getSelectedItem());

							if (toLang.equals("HINDI")) {
								et2.setTypeface(tf);
								et2.setText(translatedText);
							}

							else {

								et2.setText(translatedText);

							}

							if (translatedText.equals(null)) {
								// nothing happens
							}

							else {
								Toast.makeText(
										CryoTranActivity.this,
										"CryoTranlator: "
												+ "\nFrom Language: "
												+ String.valueOf(spinner1
														.getSelectedItem())
												+ "\nTo Language: "
												+ String.valueOf(spinner2
														.getSelectedItem())
												+ "\nTranlated Text:"
												+ translatedText,
										Toast.LENGTH_SHORT).show();
							}
						}
					}.execute();

				}

				else {

					addItemsOnSpinner2();
					Toast.makeText(CryoTranActivity.this,
							"Check Your Internet Connection",
							Toast.LENGTH_SHORT).show();

				}

			}

		});

	}

	void kill_activity() {
		finish();
	}

	public void addListenerOnSpinnerItemSelection() {
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	}

	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.activity_cryo_tran, menu);
		return true;
	}

	@SuppressWarnings("unused")
	public static boolean isNetworkAvailable(Activity mActivity) {
		Context context = mActivity.getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
