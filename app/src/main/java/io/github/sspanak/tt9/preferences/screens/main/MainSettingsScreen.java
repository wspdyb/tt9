package io.github.sspanak.tt9.preferences.screens.main;

import androidx.preference.Preference;

import java.util.ArrayList;
import java.util.Arrays;

import io.github.sspanak.tt9.R;
import io.github.sspanak.tt9.preferences.PreferencesActivity;
import io.github.sspanak.tt9.preferences.screens.BaseScreenFragment;
import io.github.sspanak.tt9.util.SystemSettings;

public class MainSettingsScreen extends BaseScreenFragment {
	final public static String NAME = "Main";

	public MainSettingsScreen() { init(); }
	public MainSettingsScreen(PreferencesActivity activity) { init(activity); }

	@Override public String getName() { return NAME; }
	@Override protected int getTitle() { return R.string.app_settings;}
	@Override protected int getXml() { return R.xml.prefs; }


	@Override
	public void onCreate() {
		createSettingsSection();
		createAboutSection();
		updateHelpButtonDescription();
		resetFontSize(false);
	}


	@Override
	public void onResume() {
		init(); // changing the theme recreates the PreferencesActivity, making "this.activity" NULL, so we reinitialize it.
		super.onResume();
		createSettingsSection();
		resetFontSize(false);
	}


	private void createAboutSection() {
		Preference donate = findPreference("donate_link");
		if (donate != null) {
			String appName = getString(R.string.app_name_short);
			String url = getString(R.string.donate_url_short);
			donate.setSummary(getString(R.string.donate_summary, appName, url));
		}

		ItemVersionInfo debugOptions = new ItemVersionInfo(findPreference(ItemVersionInfo.NAME), activity);
		debugOptions.populate().enableClickHandler();
	}


	private void updateHelpButtonDescription() {
		Preference help = findPreference("screen_help");
		if (help == null) {
			return;
		}

		String systemLanguage = SystemSettings.getLocale().replaceAll("_\\w+$", "");
		boolean missingLanguage = !systemLanguage.equals("en") && !systemLanguage.equals("es") && !systemLanguage.equals("ru");
		help.setSummary(missingLanguage ? "English only" : "");
	}


	private void createSettingsSection() {
		boolean isTT9On = SystemSettings.isTT9Enabled(activity);

		Preference gotoSetup = findPreference("screen_setup");
		if (gotoSetup != null) {
			gotoSetup.setSummary(isTT9On ? "" : activity.getString(R.string.setup_click_here_to_enable));
		}

		ArrayList<Preference> screens = new ArrayList<>(Arrays.asList(
			findPreference("screen_appearance"),
			findPreference("screen_keypad"),
			findPreference("screen_languages")
		));

		for (Preference goToScreen : screens) {
			if (goToScreen != null) {
				goToScreen.setEnabled(isTT9On || activity.getSettings().getDemoMode());
			}
		}
	}
}
