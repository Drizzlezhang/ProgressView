package com.drizzle.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import com.drizzle.progressview.ProgressView;

public class MainActivity extends AppCompatActivity {
	private ProgressView progressView;
	private SeekBar seekBar;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		progressView = (ProgressView) findViewById(R.id.progressview);
		seekBar = (SeekBar) findViewById(R.id.progressbar);
		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				progressView.setProgress(progress);
				progressView.setText(progress + "%");
			}

			@Override public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});
	}
}
