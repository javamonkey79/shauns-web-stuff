package com.javamonkey.namepicker;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class NamePickerActivity extends Activity {

	// TODO - draw the cell alphanumeric inputs (eg: names)

	private static final Handler HANDLER = new Handler();
	private GestureDetector gestureDetector;

	private class GraphicsView extends View {
		// private Canvas canvas;
		// private final boolean redrawing = false;

		public GraphicsView(Context context) {
			super(context);

			gestureDetector = new GestureDetector(NamePickerActivity.this, new GestureDetector.SimpleOnGestureListener() {
				@Override
				public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {

					// TODO - velocity logic: figure out how long to spin based on velocity
					// TODO - gesture fix: only a down gesture should activate
					final long startTime = System.currentTimeMillis();
					Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							while (System.currentTimeMillis() - startTime < 2000) {
								HANDLER.post(new Runnable() {
									@Override
									public void run() {
										pathCount++;
										invalidate();
									}
								});
							}
						}
					});

					thread.start();

					return true;
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

			// this.canvas = canvas;
		}

		private void drawPickerWheel(final Canvas canvas) {

			System.out.println("drawPickerWheel");

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
			// canvas.drawArc(rightOval, 270, 180, false, paint);

			// FIXME - let the loop draw the first line
			Line lastLine = new Line((left + right) / 2, top, (rightOval.left + rightOval.right) / 2, top);
			canvas.drawLine(lastLine.x1, lastLine.y1, lastLine.x2, lastLine.y2, paint);

			float leftXCenter = (leftOval.right + leftOval.left) / 2;
			float rightXCenter = (rightOval.right + rightOval.left) / 2;

			for (int yOffset = 50; yOffset <= 600; yOffset += 50) {
				Line curLine = drawLineBetweenOvals(canvas, paint, top, leftOval, rightOval, leftXCenter, rightXCenter, yOffset);

				if (lastLine != null) {
					drawPathForLines(curLine, lastLine, canvas, paint);
				}

				lastLine = curLine;
			}
		}

		// private synchronized void spinWheel() {
		// if (!redrawing) {
		// Handler handler = new Handler();
		// handler.postDelayed(new Runnable() {
		// @Override
		// public void run() {
		// redrawing = true;
		//
		// pathCount++;
		// drawPickerWheel(GraphicsView.this.canvas);
		// invalidate();
		//
		// redrawing = false;
		// }
		// }, 100);
		// }
		// }

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

	private void drawPathForLines(Line curLine, Line lastLine, Canvas canvas, Paint paint) {

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

		paths.add(path);

		pathCount++;
	}

	private int getColorForPath() {
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