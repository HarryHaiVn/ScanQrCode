package harry.vn.qrcode.view;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.glxn.qrgen.core.scheme.IEvent;

import harry.vn.qrcode.R;

public class GeneratsEventView extends LinearLayout implements IView {
    EditText edtLink;

    public GeneratsEventView(Context context) {
        super(context);
        initView();
    }

    public GeneratsEventView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GeneratsEventView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        inflate(getContext(), R.layout.generate_event, this);
    }

    @Override
    public void onClickDone(IGenQrView iGenQrView) {
        IEvent iEvent = new IEvent();
//        iEvent.setEnd(":");
//        iGenQrView.onGenQr(QRCode.from(iEvent).withSize(250, 250).file());
        Toast.makeText(getContext(), "GeneratsEventView", Toast.LENGTH_SHORT).show();
    }
}
