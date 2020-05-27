package harry.vn.qrcode.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.scheme.EMail;

import harry.vn.qrcode.R;

public class GenerateEmailView extends LinearLayout implements IView {
    EditText email;
    EditText text;

    public GenerateEmailView(Context context) {
        super(context);
        initView();
    }

    public GenerateEmailView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GenerateEmailView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        inflate(getContext(), R.layout.generate_email, this);
        email = findViewById(R.id.email);
        text = findViewById(R.id.text);
    }

    @Override
    public void onClickDone(IGenQrView iGenQrView) {
        EMail email1 = new EMail();
        email1.setEmail(email.getText().toString());
        iGenQrView.onGenQr(QRCode.from(email1).withSize(250, 250).file());
        Toast.makeText(getContext(), "GenerateEmailView", Toast.LENGTH_SHORT).show();
    }
}
