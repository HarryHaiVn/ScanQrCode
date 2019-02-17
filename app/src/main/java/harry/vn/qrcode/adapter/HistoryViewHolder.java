package harry.vn.qrcode.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import harry.vn.qrcode.R;

public class HistoryViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.txtProduct)
    TextView txtProduct;

    public HistoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(itemView);
        itemView.setOnClickListener(v -> {
        });
    }
}
