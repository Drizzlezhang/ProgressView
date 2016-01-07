package com.drizzle.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import com.drizzle.progressview.OnProgressChangeListener;
import com.drizzle.progressview.ProgressView;

public class MainActivity extends AppCompatActivity {
	private ProgressView progressView;
	private SeekBar seekBar;
	private Button button;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		progressView = (ProgressView) findViewById(R.id.progressview);
		seekBar = (SeekBar) findViewById(R.id.progressbar);
		button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				//ProgressAnimation animation = new ProgressAnimation(progressView,100);
				//animation.setDuration(2000);
				//progressView.startAnimation(animation);
				progressView.finish();
			}
		});
		progressView.setOnProgressChangeListener(new OnProgressChangeListener() {
			@Override public void onProgressChange(int progress) {
				progressView.setText(progress + "%");
			}
		});
		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				progressView.setProgress(progress);
			}

			@Override public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});
	}
}
