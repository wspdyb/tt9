package io.github.sspanak.tt9.preferences;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import io.github.sspanak.tt9.R;
import io.github.sspanak.tt9.db.DictionaryLoader;
import io.github.sspanak.tt9.ui.DictionaryLoadingBar;

public class PreferencesActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(SettingsStore.getInstance().getTheme());
		super.onCreate(savedInstanceState);
		buildScreen();
	}


	private void buildScreen() {
		setContentView(R.layout.preferences_container);
		getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.preferences_container, new PreferencesFragment(this))
			.commit();
	}


	DictionaryLoadingBar getDictionaryProgressBar() {
		return DictionaryLoadingBar.getInstance(this);
	}


	DictionaryLoader getDictionaryLoader() {
		return DictionaryLoader.getInstance(this);
	}
}
