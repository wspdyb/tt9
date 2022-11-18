package io.github.sspanak.tt9.preferences;

import android.content.res.Resources;
import android.os.Build;

import androidx.preference.DropDownPreference;
import androidx.preference.Preference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import io.github.sspanak.tt9.Logger;
import io.github.sspanak.tt9.R;

public class ItemSelectTheme {
	public static final String NAME = "pref_theme";

	private final DropDownPreference item;
	private final PreferencesActivity context;
	private final SettingsStore settings;

	private final LinkedHashMap<String, String> options = new LinkedHashMap<>();

	public ItemSelectTheme(DropDownPreference dropDown, PreferencesActivity preferencesActivity, SettingsStore settings) {
		item = dropDown;
		context = preferencesActivity;
		this.settings = settings;
	}


	private LinkedHashMap<String, String> getOptions() {
		if (options.size() > 0) {
			return options;
		}

		final int DEVICE_DARK = android.R.style.Theme_DeviceDefault;
		final int DEVICE_LIGHT = android.R.style.Theme_DeviceDefault_Light;
		final int ANDROID_DARK;
		final int ANDROID_LIGHT;

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			ANDROID_DARK = android.R.style.Theme_Material;
			ANDROID_LIGHT = android.R.style.Theme_Material_Light;
		} else {
			ANDROID_DARK = android.R.style.Theme_Holo;
			ANDROID_LIGHT = android.R.style.Theme_Holo_Light;
		}


		Resources resources = context.getResources();
		options.put(String.valueOf(DEVICE_LIGHT), Build.MODEL);
		options.put(String.valueOf(DEVICE_DARK), Build.MODEL + " (" + resources.getString(R.string.dark_theme) + ")");
		options.put(String.valueOf(ANDROID_LIGHT), "Android");
		options.put(String.valueOf(ANDROID_DARK), "Android (" + resources.getString(R.string.dark_theme) + ")");

		return options;
	}


	private boolean onChange(Preference p, Object newValue) {
		try {
			context.recreate();
			previewSelection();
			return true;
		} catch (NumberFormatException e) {
			Logger.e("tt9/ItemSelectTheme", "Invalid theme value: '" + newValue + "'. Number expected.");
			return false;
		}
	}


	private void previewSelection() {
		if (item != null) {
			item.setSummary(getOptions().get(item.getValue()));
		}
	}


	public ItemSelectTheme enableChangeHandler() {
		item.setOnPreferenceChangeListener(this::onChange);
		return this;
	}


	public ItemSelectTheme populate() {
		if (item != null) {
			item.setEntries(getOptions().values().toArray(new CharSequence[0]));
			item.setEntryValues(getOptions().keySet().toArray(new CharSequence[0]));
			item.setValue(String.valueOf(settings.getTheme()));
			previewSelection();
		}

		return this;
	}
}
