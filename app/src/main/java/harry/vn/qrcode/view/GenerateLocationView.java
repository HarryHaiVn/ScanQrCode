package harry.vn.qrcode.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.scheme.GeoInfo;

import java.util.ArrayList;
import java.util.List;

import harry.vn.qrcode.R;

public class GenerateLocationView extends LinearLayout implements IView {
    EditText lat;
    EditText lng;

    public GenerateLocationView(Context context) {
        super(context);
        initView();
    }

    public GenerateLocationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GenerateLocationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        inflate(getContext(), R.layout.generate_location, this);
        lat = findViewById(R.id.lat);
        lng = findViewById(R.id.lng);
    }

    @Override
    public void onClickDone(IGenQrView iGenQrView) {
        GeoInfo geoInfo = new GeoInfo();
        List<String> list = new ArrayList<>();
        list.add(lat.getText().toString());
        list.add(lng.getText().toString());
        geoInfo.setPoints(list);
        iGenQrView.onGenQr(QRCode.from(geoInfo).withSize(250, 250).file());
        Toast.makeText(getContext(), "GenerateLocationView", Toast.LENGTH_SHORT).show();
    }
}
