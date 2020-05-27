package harry.vn.qrcode.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.scheme.SMS;

import harry.vn.qrcode.R;

public class GenerateSmsView extends LinearLayout implements IView {
    EditText phone;
    EditText text;

    public GenerateSmsView(Context context) {
        super(context);
        initView();
    }

    public GenerateSmsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GenerateSmsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        inflate(getContext(), R.layout.generate_sms, this);
        phone = findViewById(R.id.phone);
        text = findViewById(R.id.text);
    }

    @Override
    public void onClickDone(IGenQrView iGenQrView) {
        SMS sms = new SMS();
        sms.setNumber(phone.getText().toString());
        sms.setSubject(text.getText().toString());
        iGenQrView.onGenQr(QRCode.from(sms).withSize(250, 250).file());
        Toast.makeText(getContext(), "GenerateSmsView", Toast.LENGTH_SHORT).show();
    }
}
