package com.javamonkey;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class PasswordGeneratorActivity extends Activity {

	private static final PasswordGenerator PASSWORD_GENERATOR = new PasswordGenerator();

	private final Set<String> computerNames = new HashSet<String>();

	private static final String COMPUTER_NAMES_FILE = "computerNames.txt";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		Button pwButton = (Button) findViewById(R.id.newPasswordButton);
		pwButton.setOnClickListener(new PasswordButtonClickListener(
				((EditText) findViewById(R.id.newPasswordText))));

		loadComputerNames();

		for (String name : computerNames) {
			createComputerNameRow(name);
		}
	}

	private void loadComputerNames() {
		try {
			Scanner scanner = new Scanner(openFileInput(COMPUTER_NAMES_FILE));
			while (scanner.hasNext()) {
				computerNames.add(scanner.nextLine());
			}
		} catch (FileNotFoundException fileNotFoundException) {
			fileNotFoundException.printStackTrace();
		}
	}

	private void createComputerNameRow(final String computerName) {

		TableLayout tl = (TableLayout) findViewById(R.id.tableLayout);

		TableRow tr = new TableRow(this);
		tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));

		ImageButton imageButton = new ImageButton(this);
		imageButton.setImageResource(android.R.drawable.ic_delete);
		imageButton.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		imageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				removeComputerName(computerName);
			}
		});

		tr.addView(imageButton);

		TextView textView = new TextView(this);
		textView.setText(computerName);
		textView.setId(Math.abs((int) System.currentTimeMillis()));

		LayoutParams buttonLayoutParams = new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		buttonLayoutParams.column = 1;
		textView.setLayoutParams(buttonLayoutParams);

		tr.addView(textView);

		Button genPasswordButton = new Button(this);
		genPasswordButton.setText(R.string.button_text);
		genPasswordButton.setId(Math.abs((int) System.currentTimeMillis()));
		genPasswordButton.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		genPasswordButton.setOnClickListener(new PasswordButtonClickListener(
				textView));

		tr.addView(genPasswordButton);

		tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
	}

	private void createPasswordDialog(final String seedText) {
		AlertDialog alertDialog = new AlertDialog.Builder(
				PasswordGeneratorActivity.this).create();
		alertDialog.setTitle("Password Generated");
		alertDialog.setMessage(getPasswordForEntry(seedText));
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		});

		alertDialog.setButton2("Text", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String uri = "smsto:" + "";
				Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(uri));
				intent.putExtra("sms_body",
						PASSWORD_GENERATOR.generatePasswordForSeed(seedText));
				intent.putExtra("compose_mode", true);
				startActivity(intent);
			}
		});

		alertDialog.show();
	}

	private String getPasswordForEntry(final String seedText) {
		return PASSWORD_GENERATOR.generatePasswordForSeed(seedText);
	}

	private final class PasswordButtonClickListener implements OnClickListener {
		private final TextView _computerNameTextView;

		public PasswordButtonClickListener(TextView computerName) {
			_computerNameTextView = computerName;
		}

		@Override
		public void onClick(View v) {
			String computerName = _computerNameTextView.getText().toString();
			createPasswordDialog(computerName);

			if (_computerNameTextView instanceof EditText) {
				saveComputerName(computerName);
			}
		}
	}

	private void saveComputerName(String computerName) {
		computerNames.add(computerName);

		saveComputerNamesFile();

		finish();
		startActivity(getIntent());
	}

	private void removeComputerName(String computerName) {
		computerNames.remove(computerName);

		saveComputerNamesFile();

		finish();
		startActivity(getIntent());
	}

	private void saveComputerNamesFile() {
		try {
			FileOutputStream fos = openFileOutput(COMPUTER_NAMES_FILE,
					Context.MODE_PRIVATE);

			for (String name : computerNames) {
				fos.write((name + "\n").getBytes());
			}

			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}