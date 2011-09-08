package com.javamonkey;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.TimePicker;

public class PasswordGeneratorActivity extends Activity {

	private static final PasswordGenerator PASSWORD_GENERATOR = new PasswordGenerator();

	private final Set<String> computerNames = new HashSet<String>();

	private static final String COMPUTER_NAMES_FILE = "computerNames.txt";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		Button pwButton = (Button) findViewById(R.id.newComputerButton);
		pwButton.setOnClickListener(new PasswordButtonClickListener(
				((EditText) findViewById(R.id.newComputerText))));

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

		tl.addView(createTableRow(computerName), new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	}

	private TableRow createTableRow(final String computerName) {
		TableRow tr = new TableRow(this);
		tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));

		ImageButton deleteButton = createDeleteButton(computerName);

		tr.addView(deleteButton);

		TextView computerNameTextView = createComputerNameTextView(computerName);

		tr.addView(computerNameTextView);

		Button genPasswordButton = createGeneratePasswordButton(computerNameTextView);

		tr.addView(genPasswordButton);
		return tr;
	}

	private Button createGeneratePasswordButton(
			final TextView computerNameTextView) {
		Button genPasswordButton = new Button(this);
		genPasswordButton.setText(R.string.get_password_button);
		genPasswordButton.setId(Math.abs((int) System.currentTimeMillis()));
		genPasswordButton.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		genPasswordButton.setOnClickListener(new PasswordButtonClickListener(
				computerNameTextView));
		genPasswordButton.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				createTimePickerDialog(computerNameTextView);

				return false;
			}
		});

		return genPasswordButton;
	}

	private TextView createComputerNameTextView(final String computerName) {
		TextView computerNameTextView = new TextView(this);
		computerNameTextView.setText(computerName);
		computerNameTextView.setId(Math.abs((int) System.currentTimeMillis()));

		LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		layoutParams.gravity = Gravity.CENTER;
		computerNameTextView.setLayoutParams(layoutParams);

		return computerNameTextView;
	}

	private ImageButton createDeleteButton(final String computerName) {
		ImageButton deleteButton = new ImageButton(this);
		deleteButton.setImageResource(android.R.drawable.ic_delete);
		deleteButton.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		deleteButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				removeComputerName(computerName);
			}
		});
		deleteButton.setId(Math.abs((int) System.currentTimeMillis()));

		return deleteButton;
	}

	private void createPasswordDialog(final String seedText, final int hourToUse) {
		AlertDialog alertDialog = new AlertDialog.Builder(
				PasswordGeneratorActivity.this).create();

		TextView alertDialogTextView = new TextView(this);
		alertDialogTextView.setText(PASSWORD_GENERATOR.generatePasswordForSeed(
				seedText, hourToUse));
		alertDialogTextView.setGravity(Gravity.CENTER_HORIZONTAL);

		alertDialog.setView(alertDialogTextView);

		alertDialog.setTitle("Password Generated");
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
				intent.putExtra("sms_body", PASSWORD_GENERATOR
						.generatePasswordForSeed(seedText, hourToUse));
				intent.putExtra("compose_mode", true);

				startActivity(intent);
			}
		});

		alertDialog.show();
	}

	private final class PasswordButtonClickListener implements OnClickListener {
		private final TextView _computerNameTextView;

		public PasswordButtonClickListener(TextView computerName) {
			_computerNameTextView = computerName;
		}

		@Override
		public void onClick(View v) {
			String computerName = _computerNameTextView.getText().toString();

			if (_computerNameTextView instanceof EditText) {
				saveComputerName(computerName);
			} else {
				createPasswordDialog(computerName, -1);
			}
		}
	}

	private void saveComputerName(String computerName) {
		computerNames.add(computerName);

		saveComputerNamesFile();
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

		finish();
		startActivity(getIntent());
	}

	private void createTimePickerDialog(final TextView computerNameTextView) {
		TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				createPasswordDialog(computerNameTextView.getText().toString(),
						hourOfDay);
			}
		};

		final Calendar c = Calendar.getInstance();
		int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		final TimePickerDialog timePickerDialog = new TimePickerDialog(this,
				mTimeSetListener, hourOfDay, minute, true);

		timePickerDialog.show();
	}

}