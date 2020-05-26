package harry.vn.qrcode.fragment;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Switch;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import harry.vn.qrcode.R;
import harry.vn.qrcode.bus.MessageEvent;
import harry.vn.qrcode.utils.PreferencesUtils;
import harry.vn.qrcode.utils.Type;

import static android.content.Context.VIBRATOR_SERVICE;
import static harry.vn.qrcode.utils.PreferencesUtils.KEY_AUTOFOCUS;
import static harry.vn.qrcode.utils.PreferencesUtils.KEY_OPEN_LINK;
import static harry.vn.qrcode.utils.PreferencesUtils.KEY_SOUND;
import static harry.vn.qrcode.utils.PreferencesUtils.KEY_VIBRATE;

public class SettingFragment extends BaseFragment {

    @BindView(R.id.switchAutoOpenLink)
    Switch switchAutoOpenLink;
    @BindView(R.id.switchAutoFocus)
    Switch switchAutoFocus;
    @BindView(R.id.switchSound)
    Switch switchSound;
    @BindView(R.id.switchVibrate)
    Switch switchVibrate;
    AudioManager audioManager;

    public static Fragment newInstance() {
        return new SettingFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initChildView(View mView) {
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        switchAutoOpenLink.setChecked(PreferencesUtils.getBoolean(KEY_OPEN_LINK, true));
        switchAutoOpenLink.setOnCheckedChangeListener((compoundButton, b) -> {
            PreferencesUtils.putBoolean(KEY_OPEN_LINK, b);
            compoundButton.setChecked(b);
        });
        switchAutoFocus.setChecked(PreferencesUtils.getBoolean(KEY_AUTOFOCUS, true));
        switchAutoFocus.setOnCheckedChangeListener((compoundButton, b) -> {
            PreferencesUtils.putBoolean(KEY_AUTOFOCUS, b);
            compoundButton.setChecked(b);
        });
        switchSound.setChecked(PreferencesUtils.getBoolean(KEY_SOUND, true));
        switchSound.setOnCheckedChangeListener((compoundButton, b) -> {
            PreferencesUtils.putBoolean(KEY_SOUND, b);
            compoundButton.setChecked(b);
        });
        switchVibrate.setChecked(PreferencesUtils.getBoolean(KEY_VIBRATE, true));
        switchVibrate.setOnCheckedChangeListener((compoundButton, isVibrate) -> {
            PreferencesUtils.putBoolean(KEY_VIBRATE, isVibrate);
            compoundButton.setChecked(isVibrate);
            if (isVibrate)
                onSettingVibrate();
        });

    }

    @Override
    protected void initData() {

    }

    private void onSettingVibrate() {
        if (getActivity() == null) return;
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) Objects.requireNonNull(getActivity().getSystemService(VIBRATOR_SERVICE))).vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) Objects.requireNonNull(getActivity().getSystemService(VIBRATOR_SERVICE))).vibrate(150);
        }
    }

    @SuppressLint("IntentReset")
    @OnClick(R.id.txtFeedback)
    public void OnClickFeedback() {
        Intent intentSendMail = new Intent(Intent.ACTION_SENDTO);
        intentSendMail.setType("text/plain");
        intentSendMail.putExtra(Intent.EXTRA_SUBJECT, "email_subject");
        intentSendMail.putExtra(Intent.EXTRA_TEXT, "email_body");
        intentSendMail.setData(Uri.parse("mailto:harryhaivn@gmail.com"));
        intentSendMail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intentSendMail, "Send mail..."));
    }

    @OnClick(R.id.ivMenu)
    public void OnClickIvMenu() {
        EventBus.getDefault().post(new MessageEvent(Type.MENU));
    }

    @SuppressLint("IntentReset")
    @OnClick(R.id.txtRateus)
    public void OnClickRate() {
        if (getActivity() == null) return;
        Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
        }
    }

    @SuppressLint("IntentReset")
    @OnClick(R.id.txtAbout)
    public void OnClickAbout() {
        if (getActivity() == null) return;
        Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
        }
    }

}
