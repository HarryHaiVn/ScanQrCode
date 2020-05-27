package harry.vn.qrcode.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.glxn.qrgen.android.QRCode;

import harry.vn.qrcode.R;

public class GenerateWebLinkView extends LinearLayout implements IView {
    EditText edtLink;

    public GenerateWebLinkView(Context context) {
        super(context);
        initView();
    }

    public GenerateWebLinkView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GenerateWebLinkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        inflate(getContext(), R.layout.generate_weblink, this);
        edtLink = findViewById(R.id.text);
    }

    @Override
    public void onClickDone(IGenQrView iGenQrView) {
        iGenQrView.onGenQr(QRCode.from(edtLink.getText().toString()).withSize(250, 250).file());
        Toast.makeText(getContext(), "GenerateWebLinkView", Toast.LENGTH_SHORT).show();
    }
}
