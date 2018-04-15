package guo.guo.mainitem._14PreferenceScreen;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.util.Log;

import guo.guo.R;

/**
 * 作者：author
 * 时间：2018/1/4:16:25
 * 说明：  判断状态改变用       onPreferenceTreeClick    实现的那两个接口没用
 */

public class MyPreferenceFragment extends PreferenceFragment {
    private static final String TAG = "MyPreferenceFragment";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.xml.preference1);
        Log.e(TAG, "onCreate: ");
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String str = (String) findPreference("edittext_preference").getTitle();
//        SettingsActivity
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        Log.e(TAG, "onPreferenceTreeClick: ");
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

}
