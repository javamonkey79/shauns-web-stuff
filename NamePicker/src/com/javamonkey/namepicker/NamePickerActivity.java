package com.javamonkey.namepicker;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class NamePickerActivity extends Activity {

	// TODO - fix the icon & name

	// TODO - lock the flipping

	// TODO - multiple lists of names

	// TODO - cleanup all of the magic numbers

	// TODO - fix the angling on the top and bottom paths

	private List<String> _pickerItems;
	private GestureDetector gestureDetector;

	private boolean inEditMode = false;

	private static final String PICKER_FILE = "picker-items.txt";
	private static final Handler HANDLER = new Handler();

	@Override
	public boolean onCreatePanelMenu(int featureId, Menu menu) {
		menu.add(1, Menu.FIRST, Menu.FIRST, "Configure Names");

		return super.onCreatePanelMenu(featureId, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		inEditMode = true;

		_editTextView = new EditText(this);

		StringBuilder text = new StringBuilder();
		List<String> pickerItems = getPickerItems();
		for (String pickerItem : pickerItems) {
			text.append(pickerItem);
			text.append("\n");
		}

		_editTextView.setText(text.toString());

		setContentView(_editTextView);

		return super.onOptionsItemSelected(item);
	}

	private List<String> getPickerItems() {
		List<String> pickerItems = new ArrayList<String>();
		try {
			Scanner scanner = new Scanner(openFileInput(PICKER_FILE));
			while (scanner.hasNext()) {
				pickerItems.add(scanner.nextLine());
			}
		} catch (Exception fileNotFoundException) {
			fileNotFoundException.printStackTrace();

			return Arrays.asList("Shaun", "Greg", "Dan O.", "Dan W.", "Paul", "Jim");
		}

		return pickerItems;
	}

	@Override
	public void onBackPressed() {
		if (inEditMode) {
			savePickerItems();
			setContentView(new GraphicsView(this));
		} else {
			super.onBackPressed();
		}
	}

	private void savePickerItems() {
		try {
			FileOutputStream fos = openFileOutput(PICKER_FILE, Context.MODE_PRIVATE);

			String text = _editTextView.getText().toString();
			Scanner textScanner = new Scanner(text);
			while (textScanner.hasNext()) {
				String line = textScanner.nextLine();
				fos.write((line + "\n").getBytes());
			}

			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class GraphicsView extends View {

		public GraphicsView(Context context) {
			super(context);

			inEditMode = false;

			_pickerItems = getPickerItems();

			gestureDetector = new GestureDetector(NamePickerActivity.this, new GestureDetector.SimpleOnGestureListener() {
				@Override
				public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, final float velocityY) {

					System.out.println(String.format("velocityY:%s", velocityY));
					// System.out.println(String.format("e1.x: %s, e1.y:%s, e2.x:%s, e2.y:%s", e1.getX(), e1.getY(), e2.getX(), e2.getY()));

					double deltaX = Math.abs(e1.getX() - e2.getX());
					double deltaY = e1.getY() - e2.getY();

					// System.out.println(String.format("deltaX:%s, deltaY:%s", deltaX, deltaY));

					if (deltaX < 100 && deltaY < -100) {
						final long startTime = System.currentTimeMillis();
						Thread thread = new Thread(new Runnable() {
							@Override
							public void run() {
								long timeRunning = System.currentTimeMillis() - startTime;
								long sleepTime = getInitialWheelThreadSleepTime(velocityY);
								while (timeRunning < 3000) {

									try {
										Thread.sleep(sleepTime);
									} catch (InterruptedException interruptedException) {
										interruptedException.printStackTrace();
									}

									HANDLER.post(new Runnable() {
										@Override
										public void run() {
											pathCount++;
											invalidate();
										}
									});

									timeRunning = System.currentTimeMillis() - startTime;
									sleepTime = getSleepTimeForTimeRunning(sleepTime, timeRunning);
								}
							}

						});

						thread.start();
					}

					return true;
				}

				private long getSleepTimeForTimeRunning(long sleepTime, long timeRunning) {
					int timeRunningHundreths = (int) (timeRunning / 100);
					long slowingSleepTime = sleepTime + timeRunningHundreths;

					return slowingSleepTime;
				}

				private long getInitialWheelThreadSleepTime(float velocityY) {
					int velocityThousands = (int) (velocityY / 1000);
					switch (velocityThousands) {
					case 0:
						return 300;
					case 1:
						return 200;
					case 2:
						return 100;
					case 3:
						return 50;
					default:
						return 0;
					}
				}

				@Override
				public boolean onDown(MotionEvent e) {
					return true;
				}
			});
		}

		@Override
		protected void onDraw(final Canvas canvas) {
			super.onDraw(canvas);

			drawPickerWheel(canvas);
		}

		private void drawPickerWheel(final Canvas canvas) {
			int centerX = canvas.getWidth() / 2;
			int centerY = canvas.getHeight() / 2;

			final Paint paint = new Paint();
			paint.setColor(Color.BLACK);
			paint.setAntiAlias(true);
			paint.setStyle(Paint.Style.FILL);
			paint.setStrokeWidth(3);

			canvas.drawARGB(255, 255, 255, 255);

			float left = centerX - (centerX / 2);
			float top = centerY - 300;
			float right = left + 50;
			float bottom = top + 600;

			RectF leftOval = new RectF(left, top, right, bottom);
			canvas.drawOval(leftOval, paint);

			RectF rightOval = new RectF(left + 250, top, right + 250, bottom);

			// FIXME - let the loop draw the first line
			Line lastLine = new Line((left + right) / 2, top, (rightOval.left + rightOval.right) / 2, top);
			canvas.drawLine(lastLine.x1, lastLine.y1, lastLine.x2, lastLine.y2, paint);

			float leftXCenter = (leftOval.right + leftOval.left) / 2;
			float rightXCenter = (rightOval.right + rightOval.left) / 2;

			for (int yOffset = 50; yOffset <= 600; yOffset += 50) {
				Line curLine = drawLineBetweenOvals(canvas, paint, top, leftOval, rightOval, leftXCenter, rightXCenter, yOffset);

				if (lastLine != null) {
					drawWheelCell(curLine, lastLine, canvas, paint, _pickerItems);
				}
				lastLine = curLine;
			}

			drawSideArrow(canvas, paint, top, right, bottom);
		}

		private void drawSideArrow(final Canvas canvas, final Paint paint, float top, float right, float bottom) {
			float arrowOriginY = ((top + bottom) / 2) + 30;
			float arrowOriginX = right + 260;

			canvas.drawLine(arrowOriginX, arrowOriginY, arrowOriginX + 75, arrowOriginY, paint);
			canvas.drawLine(arrowOriginX, arrowOriginY, arrowOriginX + 25, arrowOriginY + 25, paint);
			canvas.drawLine(arrowOriginX, arrowOriginY, arrowOriginX + 25, arrowOriginY - 25, paint);
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			return gestureDetector.onTouchEvent(event);
		}
	}

	private Line drawLineBetweenOvals(Canvas canvas, Paint paint, float top, RectF leftOval, RectF rightOval, float leftXCenter, float rightXCenter, float widthFromTop) {
		float y = top + widthFromTop;
		float x1 = (float) getXForOval(leftOval, y);
		float y1 = y;
		float x2 = (float) getXForOval(rightOval, y);
		float y2 = y1;

		Line line = new Line(x1 + leftXCenter, y1, x2 + rightXCenter, y2);

		canvas.drawLine(line.x1, line.y1, line.x2, line.y2, paint);

		return line;
	}

	private final List<Path> paths = new ArrayList<Path>();
	private int pathCount = 0;
	private EditText _editTextView;

	private void drawWheelCell(Line curLine, Line lastLine, Canvas canvas, Paint paint, List<String> pickerItems) {

		float centerTextX = (curLine.x2 + curLine.x1) / 2;
		float centerTextY = (lastLine.y1 + curLine.y1) / 2;

		Path path = new Path();

		path.moveTo(lastLine.x1, lastLine.y1);

		path.lineTo(lastLine.x2, lastLine.y2);
		path.lineTo(curLine.x2, curLine.y2);
		path.lineTo(curLine.x1, curLine.y1);
		path.lineTo(lastLine.x1, lastLine.y1);

		path.close();

		paint.setColor(getColorForPath());
		paint.setStyle(Style.FILL);

		canvas.drawPath(path, paint);

		paint.setColor(Color.BLACK);
		paint.setStyle(Style.STROKE);

		final Paint textPaint = new Paint();
		textPaint.setColor(Color.BLACK);
		textPaint.setAntiAlias(true);
		textPaint.setStyle(Paint.Style.FILL);
		textPaint.setTextSize(25);
		textPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

		String text = pickerItems.get(pathCount % pickerItems.size());
		Rect bounds = new Rect();
		textPaint.getTextBounds(text, 0, text.length(), bounds);
		int height = bounds.height();
		int width = bounds.width();

		canvas.drawText(text, centerTextX - (width / 2), centerTextY + (height / 2), textPaint);

		paths.add(path);

		pathCount++;
	}

	private int getColorForPath() {

		// TODO - have names paired with colors

		System.out.println("color item: " + pathCount % _pickerItems.size());

		switch (pathCount % 4) {
		case 0:
			return Color.RED;
		case 1:
			return Color.BLUE;
		case 2:
			return Color.YELLOW;
		case 3:
			return Color.GREEN;
		default:
			return Color.BLACK;
		}
	}

	private double getXForOval(RectF oval, float y) {
		// a should be half the width
		float a = (oval.right - oval.left) / 2;
		// and b half the height
		float b = (oval.bottom - oval.top) / 2;

		float yCenter = (oval.top + oval.bottom) / 2;

		// calculation based on values relative to the center
		float yOffset = y - yCenter;

		// sqrt ( a^2 * (1 - y^2 / b^2) )
		return Math.sqrt(Math.abs(a * a * (1 - ((yOffset * yOffset) / (b * b)))));
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new GraphicsView(this));
	}

	private class Line {
		float x1;
		float y1;
		float x2;
		float y2;

		public Line(float x1, float y1, float x2, float y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}

	}
}