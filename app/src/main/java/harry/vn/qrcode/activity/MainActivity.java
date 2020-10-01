package harry.vn.qrcode.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

import butterknife.ButterKnife;
import harry.vn.qrcode.R;
import harry.vn.qrcode.fragment.CreateQrCodeFragment;
import harry.vn.qrcode.fragment.FavoriteFragment;
import harry.vn.qrcode.fragment.HistoryFragment;
import harry.vn.qrcode.fragment.MyQrCodeFragment;
import harry.vn.qrcode.fragment.QRScanFragment;
import harry.vn.qrcode.fragment.SettingFragment;
import harry.vn.qrcode.utils.FragmentUtil;
import harry.vn.qrcode.utils.PermissionUtils;
import harry.vn.qrcode.utils.PreferencesUtils;

import static harry.vn.qrcode.fragment.QRScanFragment.SELECT_PHOTO;

public class MainActivity extends AppCompatActivity {

    public static final String SELECT_SCREEN = "SELECT_SCREEN";
    private ArrayList<String> permissions = new ArrayList<>();
    boolean isPermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferencesUtils.init(this);
        ButterKnife.bind(this);
        onPermission();
        loadFragment();
    }

    private void loadFragment() {
        int idScreen = getIntent().getIntExtra(SELECT_SCREEN, 0);
        switch (idScreen) {
            case 0:
                Intent photoPic = new Intent(Intent.ACTION_PICK);
                photoPic.setType("image/*");
                startActivityForResult(photoPic, SELECT_PHOTO);
                break;
            case 1:
                FragmentUtil.showFragment(getSupportFragmentManager(), QRScanFragment.newInstance(), true, null, QRScanFragment.class.getName(), false);
                break;
            case 2:
                FragmentUtil.showFragment(getSupportFragmentManager(), HistoryFragment.newInstance(), true, null, HistoryFragment.class.getName(), false);
                break;
            case 3:
                FragmentUtil.showFragment(getSupportFragmentManager(), FavoriteFragment.newInstance(), true, null, FavoriteFragment.class.getName(), false);
                break;
            case 4:
                FragmentUtil.showFragment(getSupportFragmentManager(), MyQrCodeFragment.newInstance(), true, null, MyQrCodeFragment.class.getName(), false);
                break;
            case 5:
                FragmentUtil.showFragment(getSupportFragmentManager(), CreateQrCodeFragment.newInstance(), true, null, CreateQrCodeFragment.class.getName(), false);
                break;
            case 6:
                FragmentUtil.showFragment(getSupportFragmentManager(), SettingFragment.newInstance(), true, null, SettingFragment.class.getName(), false);
                break;
            default:
        }
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

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStack();
        } else {
            finish();
        }
    }

    public String getTagActiveFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }
        return getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
    }
}