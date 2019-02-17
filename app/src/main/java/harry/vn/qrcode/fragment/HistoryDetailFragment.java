package harry.vn.qrcode.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import harry.vn.qrcode.R;

public class HistoryDetailFragment extends BaseFragment {
    @BindView(R.id.txtLink)
    TextView txtLink;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_history_detail;
    }

    @Override
    protected void initChildView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.ivBack)
    public void OnClick() {
        if (getActivity() == null) return;
        FragmentManager fm = getActivity()
                .getSupportFragmentManager();
        fm.popBackStack();
    }

    @OnClick(R.id.llShare)
    public void OnClickShare() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share QR Code");
            String shareMessage = "https://24h.com.vn";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    @OnClick(R.id.llShareon)
    public void OnClickllShareon() {
        final String appPackageName = "com.facebook.orca"; // getPackageName() from Context or Activity object
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent
                .putExtra(Intent.EXTRA_TEXT,
                        txtLink.getText().toString());
        sendIntent.setType("text/plain");
        sendIntent.setPackage(appPackageName);
        try {
            startActivity(sendIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }
    }

    @OnClick(R.id.llCopy)
    public void OnClickCopy() {
        copyToClipboard(getActivity(), txtLink.getText().toString());
    }

    @SuppressWarnings("deprecation")
    public void copyToClipboard(Context context, String text) {
        try {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                if (clipboard != null) {
                    clipboard.setText(text);
                }
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData
                        .newPlainText(
                                context.getResources().getString(
                                        R.string.message), text);
                if (clipboard != null) {
                    clipboard.setPrimaryClip(clip);
                }
            }
            Toast toast = Toast.makeText(getActivity(),
                    "Your Code is copied", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM | Gravity.RIGHT, 50, 50);
            toast.show();
        } catch (Exception e) {
            Toast toast = Toast.makeText(getActivity(),
                    "Your Code can't copy", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM | Gravity.RIGHT, 50, 50);
            toast.show();
        }
    }
}
