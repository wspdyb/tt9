package io.github.sspanak.tt9.ui.tray;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import io.github.sspanak.tt9.R;
import io.github.sspanak.tt9.ime.modes.InputMode;
import io.github.sspanak.tt9.ime.voice.VoiceInputOps;
import io.github.sspanak.tt9.util.Logger;

public class StatusBar {
	private final TextView statusView;
	private String statusText;


	public StatusBar(View mainView) {
		statusView = mainView.findViewById(R.id.status_bar);
	}


	public boolean isErrorShown() {
		return statusText != null && statusText.startsWith("❌");
	}


	public void setError(String error) {
		setText("❌  " + error);
	}


	public void setText(int stringResourceId) {
		setText(statusView.getContext().getString(stringResourceId));
	}


	public void setText(String text) {
		statusText = text;
		this.render();
	}


	public void setText(InputMode inputMode) {
		setText("[ " + inputMode.toString() + " ]");
	}


	public void setText(VoiceInputOps voiceInputOps) {
		setText("[ " + voiceInputOps.toString() + " ]");
	}


	public void setDarkTheme(boolean darkTheme) {
		if (statusView == null) {
			return;
		}

		Context context = statusView.getContext();

		int backgroundColor = ContextCompat.getColor(
			context,
			darkTheme ? R.color.dark_candidate_background : R.color.candidate_background
		);
		int color = ContextCompat.getColor(
			context,
			darkTheme ? R.color.dark_candidate_color : R.color.candidate_color
		);

		statusView.setBackgroundColor(backgroundColor);
		statusView.setTextColor(color);
		this.render();
	}


	private void render() {
		if (statusView == null) {
			return;
		}

		if (statusText == null) {
			Logger.w("StatusBar.render", "Not displaying NULL status");
			return;
		}

		statusView.setText(statusText);
	}
}
