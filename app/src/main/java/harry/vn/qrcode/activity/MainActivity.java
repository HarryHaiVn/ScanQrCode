package harry.vn.qrcode.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import harry.vn.qrcode.R;
import harry.vn.qrcode.adapter.MenuAdapter;
import harry.vn.qrcode.bus.MessageEvent;
import harry.vn.qrcode.fragment.FavoriteFragment;
import harry.vn.qrcode.fragment.HistoryFragment;
import harry.vn.qrcode.fragment.QRScanFragment;
import harry.vn.qrcode.fragment.SettingFragment;
import harry.vn.qrcode.listener.OnClickItemHistory;
import harry.vn.qrcode.model.HistoryModel;
import harry.vn.qrcode.model.MenuModel;
import harry.vn.qrcode.utils.FragmentUtil;
import harry.vn.qrcode.utils.PermissionUtils;
import harry.vn.qrcode.utils.PreferencesUtils;
import harry.vn.qrcode.utils.Type;

import static harry.vn.qrcode.fragment.QRScanFragment.SELECT_PHOTO;

public class MainActivity extends AppCompatActivity implements OnClickItemHistory {

    private ArrayList<String> permissions = new ArrayList<>();
    boolean isPermissionGranted;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    MenuAdapter menuAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferencesUtils.init(this);
        ButterKnife.bind(this);
        onPermission();
        loadFragment();
        initMenu();
    }

    private void initMenu() {
        List<MenuModel> historyModelList = new ArrayList<>();
        menuAdapter = new MenuAdapter(getApplicationContext());
        historyModelList.add(new MenuModel(R.drawable.d_scan, "Quét"));
        historyModelList.add(new MenuModel(R.drawable.d_scan_image, "Quét hình ảnh"));
        historyModelList.add(new MenuModel(R.drawable.d_favorites, "Yêu thích"));
        historyModelList.add(new MenuModel(R.drawable.d_history, "Lịch sử"));
        historyModelList.add(new MenuModel(R.drawable.d_my_qr, "QR của tôi"));
        historyModelList.add(new MenuModel(R.drawable.d_create, "Tạo QR"));
        historyModelList.add(new MenuModel(R.drawable.d_settings, "Cài đặt"));
        historyModelList.add(new MenuModel(R.drawable.d_share, "Chia sẻ"));
        historyModelList.add(new MenuModel(R.drawable.d_create, "Ứng dụng của chúng tôi"));
        historyModelList.add(new MenuModel(R.drawable.d_favorites, "Loại bỏ quảng cáo"));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setItemAnimator(new DefaultItemAnimator());
        recycleView.setAdapter(menuAdapter);
        menuAdapter.setListener(this);
        menuAdapter.setData(historyModelList);
    }


    private void loadFragment() {
        // load fragment
        FragmentUtil.addFragment(getSupportFragmentManager(), QRScanFragment.newInstance(), true, null, QRScanFragment.class.getName(), false);
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
    public void onClickItem(HistoryModel item) {
        /*not thing to do*/
    }

    @Override
    public void onClickItemMenu(MenuModel item, int position) {
        drawerLayout.closeDrawers();
        switch (position) {
            case 0:
                FragmentManager fm = getSupportFragmentManager();
                int count = fm.getBackStackEntryCount();
                for (int i = 0; i < count - 1; ++i) {
                    fm.popBackStackImmediate();
                }
                break;
            case 1:
                Intent photoPic = new Intent(Intent.ACTION_PICK);
                photoPic.setType("image/*");
                startActivityForResult(photoPic, SELECT_PHOTO);
                break;
            case 2:
                FragmentUtil.addFragment(getSupportFragmentManager(), FavoriteFragment.newInstance(), true, null, FavoriteFragment.class.getName(), false);
                break;
            case 3:
                FragmentUtil.addFragment(getSupportFragmentManager(), HistoryFragment.newInstance(), true, null, HistoryFragment.class.getName(), false);
                break;
            case 6:
                FragmentUtil.addFragment(getSupportFragmentManager(), SettingFragment.newInstance(), true, null, SettingFragment.class.getName(), false);
                break;
            case 7:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/developer?id=com.vista.videos.kids"));
                startActivity(intent);
                break;
            case 8:
            case 9:
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                break;
            default:
        }
    }

    @Override
    public void onRemoveItem(HistoryModel item) {
        /*not thing to do*/
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        /* Do something */
        if (event.getType().equals(Type.MENU)) {
            drawerLayout.openDrawer(Gravity.START);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.container);
        if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStack();
        } else {
            finish();
        }
    }
}