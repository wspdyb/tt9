package io.github.sspanak.tt9.ui.main.keys;

import android.content.Context;
import android.util.AttributeSet;

public class SoftVoiceInputKey extends SoftKey {
	public SoftVoiceInputKey(Context context) { super(context); }
	public SoftVoiceInputKey(Context context, AttributeSet attrs) { super(context, attrs); }
	public SoftVoiceInputKey(Context context, AttributeSet attrs, int defStyleAttr) { super(context, attrs, defStyleAttr); }

	@Override
	protected String getTitle() {
		return "🎤";
	}

	@Override
	public void render() {
		if (tt9 != null && tt9.isVoiceInputMissing()) {
			setVisibility(INVISIBLE);
		} else {
			super.render();
		}
	}
}
