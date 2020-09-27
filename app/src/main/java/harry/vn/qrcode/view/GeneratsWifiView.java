package harry.vn.qrcode.view;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.scheme.Wifi;

import harry.vn.qrcode.R;

public class GeneratsWifiView extends LinearLayout implements IView {
    EditText ssid;
    EditText pass;
    CheckBox is_hidden;

    public GeneratsWifiView(Context context) {
        super(context);
        initView();
    }

    public GeneratsWifiView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GeneratsWifiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        inflate(getContext(), R.layout.generate_wifi, this);
        ssid = findViewById(R.id.ssid);
        pass = findViewById(R.id.pass);
        is_hidden = findViewById(R.id.is_hidden);
    }

    @Override
    public void onClickDone(IGenQrView iGenQrView) {
        Wifi wifi = new Wifi();
        wifi.setSsid(ssid.getText().toString());
        wifi.setPsk(pass.getText().toString());
        wifi.setHidden(is_hidden.isChecked());
        iGenQrView.onGenQr(QRCode.from(wifi).withSize(250, 250).file());
        Toast.makeText(getContext(), "GeneratsWifiView", Toast.LENGTH_SHORT).show();
    }
}
