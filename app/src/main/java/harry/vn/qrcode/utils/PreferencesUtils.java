package harry.vn.qrcode.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesUtils {
    private static PreferencesUtils mInstance = null;
    private static SharedPreferences.Editor editor;
    private static Context mContext;
    public static final String KEY_OPEN_LINK= "key_open_link";
    public static final String KEY_SOUND = "key_sound";
    public static final String KEY_AUTOFOCUS = "key_autofocus";
    public static final String KEY_VIBRATE = "key_vibrate";

    private PreferencesUtils(Context context) {
        mContext = context;
    }

    @SuppressLint("CommitPrefEdits")
    public static PreferencesUtils init(Context context) {
        if (mInstance == null) {
            mInstance = new PreferencesUtils(context.getApplicationContext());
            editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        }
        return mInstance;
    }

    public static void putString(String key, String value) {
        editor.putString(key, value).apply();
    }

    public static String getString(String key, String defValue) {
        return PreferenceManager.getDefaultSharedPreferences(mContext).getString(key, defValue);
    }

    public static void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean(key, defValue);
    }

    public static void putInt(String key, int value) {
        editor.putInt(key, value).apply();
    }

    public static int getInt(String key, int defValue) {
        return PreferenceManager.getDefaultSharedPreferences(mContext).getInt(key, defValue);
    }

}

