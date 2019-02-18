package harry.vn.qrcode.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.IOException;

import butterknife.OnClick;
import harry.vn.qrcode.R;

public class PhotoFragment extends BaseFragment {
    private static final int SELECT_IMAGE = 1900;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_photo;
    }

    @Override
    protected void initChildView() {

    }

    @OnClick(R.id.ivChooseImage)
    public void OnClick() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
    }
    @Override
    protected void initData() {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        if (getActivity() == null) return;
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                        Toast.makeText(getActivity(), "Select image ok", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();

            }
        }
    }
}
