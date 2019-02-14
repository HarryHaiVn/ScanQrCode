package harry.vn.qrcode.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import harry.vn.qrcode.R;
import harry.vn.qrcode.fragment.HistoryFragment;
import harry.vn.qrcode.fragment.PhotoFragment;
import harry.vn.qrcode.fragment.QRScanFragment;
import harry.vn.qrcode.fragment.SettingFragment;
import harry.vn.qrcode.utils.PermissionUtils;
import harry.vn.qrcode.utils.PreferencesUtils;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> permissions = new ArrayList<>();
    boolean isPermissionGranted;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferencesUtils.init(this);
//        PreferencesUtils.putBoolean(KEY_ASSISTANT, b);
        ButterKnife.bind(this);
        disableShiftMode(bottomNavigationView);
        onPermission();
        loadFragment(new PhotoFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @SuppressLint("RestrictedApi")
    private void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);

                Field shiftAmount = item.getClass().getDeclaredField("mShiftAmount");
                shiftAmount.setAccessible(true);
                shiftAmount.setInt(item, 0);
                shiftAmount.setAccessible(false);

                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e(String.valueOf(e), "Unable to get fields");
        } catch (IllegalAccessException e) {
            Log.e(String.valueOf(e), "Unable to change values");
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.menu_photo:
                    fragment = new PhotoFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.menu_scanner:
                    fragment = new QRScanFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.menu_setting:
                    fragment = new SettingFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.menu_history:
                    fragment = new HistoryFragment();
                    loadFragment(fragment);
                    return true;
            }

            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).commit();
    }

    private void onPermission() {
        PermissionUtils permissionUtils = new PermissionUtils(this, new PermissionUtils.PermissionResultCallback() {
            @Override
            public void PermissionGranted(int request_code) {
                Log.i("PERMISSION", "GRANTED");
                isPermissionGranted = true;
            }

            @Override
            public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
                Log.i("PERMISSION PARTIALLY", "GRANTED");
            }

            @Override
            public void PermissionDenied(int request_code) {
                Log.i("PermissionDenied", request_code + "");
            }

            @Override
            public void NeverAskAgain(int request_code) {
                Log.i("PERMISSION", "NEVER ASK AGAIN");
            }
        });

        permissions.add(Manifest.permission.CAMERA);

        permissionUtils.check_permission(permissions, "Need Camera permission for scan QR code", 1);
    }
}