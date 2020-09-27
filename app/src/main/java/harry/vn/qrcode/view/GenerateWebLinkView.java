package harry.vn.qrcode.view;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.glxn.qrgen.android.QRCode;

import harry.vn.qrcode.R;

public class GenerateWebLinkView extends LinearLayout implements IView {
    EditText edtLink;
    TextView nameError;

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
        nameError = findViewById(R.id.name_error);
    }

    @Override
    public void onClickDone(IGenQrView iGenQrView) {
        if (edtLink.getText().toString().equals(getContext().getString(R.string.http))) {
            nameError.setVisibility(View.VISIBLE);
        } else
            iGenQrView.onGenQr(QRCode.from(edtLink.getText().toString()).withSize(250, 250).file());
    }
}
