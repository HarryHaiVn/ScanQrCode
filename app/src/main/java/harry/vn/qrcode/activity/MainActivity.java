package harry.vn.qrcode.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import harry.vn.qrcode.utils.PermissionUtils;
import harry.vn.qrcode.R;
import harry.vn.qrcode.fragment.QRScanFragment;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> permissions = new ArrayList<>();
    boolean isPermissionGranted;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        disableShiftMode(bottomNavigationView);
        onPermission();
        showHomeView();
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

    private void showHomeView() {
        Fragment qrScanFragment = new QRScanFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, qrScanFragment).commit();
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