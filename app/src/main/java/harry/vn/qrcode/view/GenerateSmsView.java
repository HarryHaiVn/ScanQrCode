package harry.vn.qrcode.view;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.scheme.SMS;

import harry.vn.qrcode.R;

public class GenerateSmsView extends LinearLayout implements IView {
    EditText phone;
    EditText text;
    TextView nameError;

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
        nameError = findViewById(R.id.name_error);
    }

    @Override
    public void onClickDone(IGenQrView iGenQrView) {
        if (text.getText().toString().isEmpty() || phone.getText().toString().isEmpty()) {
            nameError.setVisibility(View.VISIBLE);
        } else {
            SMS sms = new SMS();
            sms.setNumber(phone.getText().toString());
            sms.setSubject(text.getText().toString());
            iGenQrView.onGenQr(QRCode.from(sms).withSize(250, 250).file());
        }
    }
}
