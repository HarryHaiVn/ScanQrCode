package harry.vn.qrcode.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;

import harry.vn.qrcode.R;

public class GenerateWeblinkView extends LinearLayout {
    EditText edtLink;

    public GenerateWeblinkView(Context context) {
        super(context);
        initView();
    }

    public GenerateWeblinkView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GenerateWeblinkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        inflate(getContext(), R.layout.generate_weblink, this);
        edtLink = findViewById(R.id.text);
    }

    public void onClickDone() {

    }
}
