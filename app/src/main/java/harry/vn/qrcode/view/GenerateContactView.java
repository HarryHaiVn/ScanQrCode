package harry.vn.qrcode.view;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Toast;

import harry.vn.qrcode.R;

public class GenerateContactView extends LinearLayout implements IView {

    public GenerateContactView(Context context) {
        super(context);
        initView();
    }

    public GenerateContactView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GenerateContactView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        inflate(getContext(), R.layout.generate_contact, this);
    }

    @Override
    public void onClickDone(IGenQrView iGenQrView) {
        Toast.makeText(getContext(), "GenerateContactView", Toast.LENGTH_SHORT).show();
    }
}
