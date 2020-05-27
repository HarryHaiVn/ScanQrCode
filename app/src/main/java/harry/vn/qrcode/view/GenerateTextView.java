package harry.vn.qrcode.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.glxn.qrgen.android.QRCode;

import harry.vn.qrcode.R;

public class GenerateTextView extends LinearLayout implements IView {
    EditText text;

    public GenerateTextView(Context context) {
        super(context);
        initView();
    }

    public GenerateTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GenerateTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        inflate(getContext(), R.layout.generate_text, this);
        text = findViewById(R.id.text);
    }

    @Override
    public void onClickDone(IGenQrView iGenQrView) {
        iGenQrView.onGenQr(QRCode.from(text.getText().toString()).file());
        Toast.makeText(getContext(), "GenerateTextView", Toast.LENGTH_SHORT).show();
    }
}
