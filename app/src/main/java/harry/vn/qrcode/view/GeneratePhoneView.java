package harry.vn.qrcode.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.scheme.Telephone;

import harry.vn.qrcode.R;

public class GeneratePhoneView extends LinearLayout implements IView {
    EditText phone;
    TextView nameError;

    public GeneratePhoneView(Context context) {
        super(context);
        initView();
    }

    public GeneratePhoneView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GeneratePhoneView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        inflate(getContext(), R.layout.generate_phone, this);
        phone = findViewById(R.id.phone);
        nameError = findViewById(R.id.name_error);
    }

    @Override
    public void onClickDone(IGenQrView iGenQrView) {
        if (phone.getText().toString().isEmpty()) {
            nameError.setVisibility(View.VISIBLE);
        } else {
            Telephone telephone = new Telephone();
            telephone.setTelephone(phone.getText().toString());
            iGenQrView.onGenQr(QRCode.from(telephone).withSize(250, 250).file());
        }
    }
}
