package harry.vn.qrcode.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.glxn.qrgen.android.QRCode;

import harry.vn.qrcode.R;

public class GenerateTextView extends LinearLayout implements IView {
    EditText text;
    TextView nameError;

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
        nameError = findViewById(R.id.name_error);
    }

    @Override
    public void onClickDone(IGenQrView iGenQrView) {
        if (text.getText().toString().isEmpty()) {
            nameError.setVisibility(View.VISIBLE);
        } else
            iGenQrView.onGenQr(QRCode.from(text.getText().toString()).file());
    }
}
