package harry.vn.qrcode;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import butterknife.ButterKnife;
import harry.vn.qrcode.fragment.QRScanFragment;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> permissions = new ArrayList<>();
    boolean isPermissionGranted;
    private PermissionUtils permissionUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        onPermission();
        showHomeView();
    }

    private void showHomeView() {
        Fragment qrScanFragment = new QRScanFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, qrScanFragment).commit();
    }

    private void onPermission() {
        permissionUtils = new PermissionUtils(this, new PermissionUtils.PermissionResultCallback() {
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