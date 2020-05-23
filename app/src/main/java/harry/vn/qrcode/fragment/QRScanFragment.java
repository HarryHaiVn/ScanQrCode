package harry.vn.qrcode.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import org.greenrobot.eventbus.EventBus;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import harry.vn.qrcode.HistoryApp;
import harry.vn.qrcode.R;
import harry.vn.qrcode.bus.MessageEvent;
import harry.vn.qrcode.db.HistoryRepository;
import harry.vn.qrcode.model.HistoryModel;
import harry.vn.qrcode.utils.PreferencesUtils;
import harry.vn.qrcode.utils.Type;
import harry.vn.qrcode.view.CustomZXingScannerView;
import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.VIBRATOR_SERVICE;
import static harry.vn.qrcode.utils.PreferencesUtils.KEY_OPEN_LINK;
import static harry.vn.qrcode.utils.PreferencesUtils.KEY_VIBRATE;

public class QRScanFragment extends BaseFragment implements ZXingScannerView.ResultHandler {

    private static final String HTTPS = "https://";
    private static final String HTTP = "http://";
    public static final String TAG = "QRScanFragment";

    private ZXingScannerView mScannerView;
    public static final int SELECT_PHOTO = 100;

    @BindView(R.id.content_frame)
    ViewGroup contentFrame;
    @BindView(R.id.rlProgressBar)
    RelativeLayout rlProgressBar;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_qrscan;
    }

    @Override
    protected void initChildView() {
        mScannerView = new ZXingScannerView(getActivity()) {
            @Override
            protected IViewFinder createViewFinderView(Context context) {
                CustomZXingScannerView customZXingScannerView = new CustomZXingScannerView(context);
                customZXingScannerView.setBorderColor(ContextCompat.getColor(context, R.color.colorOrange));
                customZXingScannerView.setBorderStrokeWidth(10);
                customZXingScannerView.setLaserEnabled(true);
                customZXingScannerView.setSquareViewFinder(true);
//                customZXingScannerView.setMaskColor(Color.RED);
//                customZXingScannerView.setBorderCornerRadius(20);
//                customZXingScannerView.setLaserColor(Color.GREEN);
//                customZXingScannerView.setBorderLineLength(40);
                return customZXingScannerView;
            }
        };
        contentFrame.addView(mScannerView);
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.ivChooseImage)
    public void OnClick() {
        Intent photoPic = new Intent(Intent.ACTION_PICK);
        photoPic.setType("image/*");
        startActivityForResult(photoPic, SELECT_PHOTO);
    }

    @OnClick(R.id.ivMenu)
    public void OnClickMenu() {
        EventBus.getDefault().post(new MessageEvent(Type.MENU));
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    InputStream imageStream = null;
                    if (getContext() == null || selectedImage == null) return;
                    try {
                        //getting the image
                        imageStream = getContext().getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        Toast.makeText(getContext(), R.string.not_found, Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    //decoding bitmap
                    Bitmap bMap = BitmapFactory.decodeStream(imageStream);
                    rlProgressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), R.string.scan_qr, Toast.LENGTH_SHORT).show();
                    new MyAsyncTask(bMap).execute();
//                    qrCode = readQRImage(bMap);
//                    if (qrCode != null) {
//                        showDialog(qrCode + "", getString(R.string.ok));
//                    } else {
//                        showDialog("Nothing found try a different image or try again", "Error");
//
//                    }
                }
        }
    }

    public static String readQRImage(Bitmap bMapOld) {
        String contents = null;
        Bitmap bMap = getResizedBitmap(bMapOld, 320, 480);
        int[] intArray = new int[bMap.getWidth() * bMap.getHeight()];
        //copy pixel data from the Bitmap into the 'intArray' array
        bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());
        LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(), intArray);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Reader reader = new MultiFormatReader();// use this otherwise ChecksumException
        try {
            Result result = reader.decode(bitmap);
            contents = result.getText();
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        return contents;
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
    }

    @Override
    public void onPause() {
        mScannerView.stopCamera();
        super.onPause();
    }

    @Override
    public void handleResult(Result rawResult) {
        if (PreferencesUtils.getBoolean(KEY_VIBRATE, true)) {
            onSettingVibrate();
        }
        if (PreferencesUtils.getBoolean(KEY_OPEN_LINK, true)) {
            if (isValidateURl(rawResult.getText())) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.google.com.vn/search?q=" + rawResult.getText()));
                startActivity(i);
            } else {
                openBrowser(rawResult.getText());
            }
        } else {
            showDialog(rawResult.getText() + "", getString(R.string.ok));
        }
        if (getActivity() == null) return;
        new Thread(() -> {
            HistoryRepository historyRepository = ((HistoryApp) getActivity().getApplication()).getRepository();
            historyRepository.insertHistory(new HistoryModel(rawResult.getText()));
            List<HistoryModel> list = historyRepository.getAll();
            Log.i("Hainm-------------", list.size() + "");
            for (int i = 0; i < list.size(); i++) {
                Log.i("Hainm-------------", list.get(i).getLink() + "");
            }
        }).start();
    }
    private void onSettingVibrate() {
        if (getActivity() == null) return;
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) Objects.requireNonNull(getActivity().getSystemService(VIBRATOR_SERVICE))).vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) Objects.requireNonNull(getActivity().getSystemService(VIBRATOR_SERVICE))).vibrate(150);
        }
    }
    public void showDialog(final String msg, final String status) {
        if (getActivity() == null) return;
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        TextView txtContent = dialog.findViewById(R.id.txtContent);
        txtContent.setText(msg);
        if (status.equals(getString(R.string.ok))) {
            SpannableString content = new SpannableString(msg);
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            txtContent.setText(content);
        }

        txtContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (isValidateURl(msg)) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://www.google.com.vn/search?q=" + msg));
                    startActivity(i);
                } else {
                    if (status.equals(getString(R.string.ok))) {
                        openBrowser(msg);
                    }
                }
            }
        });

        TextView cancel = dialog.findViewById(R.id.txtCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mScannerView.resumeCameraPreview(QRScanFragment.this);
            }
        });
        TextView txtSearch = dialog.findViewById(R.id.txtSearch);
        if (!isValidateURl(msg)) {
            txtSearch.setText(R.string.open_url);
        }
        txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (isValidateURl(msg)) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://www.google.com.vn/search?q=" + msg));
                    startActivity(i);
                } else {
                    if (status.equals(getString(R.string.ok))) {
                        openBrowser(msg);
                    }
                }
            }
        });
        dialog.show();
    }

    public boolean isValidateURl(String msg) {
        return !msg.startsWith(HTTP) && !msg.startsWith(HTTPS);
    }

    public void openBrowser(String url) {
        if (getActivity() == null) return;
        if (!url.startsWith(HTTP) && !url.startsWith(HTTPS)) {
            url = HTTP + url;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        getActivity().startActivity(Intent.createChooser(intent, getString(R.string.choose_brower)));// Choose browser is arbitrary :)
    }

    @OnClick(R.id.ivFlash)
    public void onFlashClick() {
        if (mScannerView.getFlash()) {
            mScannerView.setFlash(false);
        } else {
            mScannerView.setFlash(true);
        }
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, String> {
        private final Bitmap bMap;

        public MyAsyncTask(Bitmap bMap) {
            this.bMap = bMap;
        }

        @Override
        protected String doInBackground(Void... voids) {
            return readQRImage(bMap);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            rlProgressBar.setVisibility(View.GONE);
            if (result != null) {
                showDialog(result + "", getString(R.string.ok));
            } else {
                showDialog(getString(R.string.nothing_try), getString(R.string.error));
            }
        }
    }
}