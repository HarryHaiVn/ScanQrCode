package harry.vn.qrcode.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import harry.vn.qrcode.R;
import harry.vn.qrcode.listener.OnClickItemHistory;
import harry.vn.qrcode.model.HistoryModel;

public class HistoryViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.txtProduct)
    TextView txtProduct;
    OnClickItemHistory onClickItemHistory;
    public HistoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(itemView);
    }

    public void setData(HistoryModel item) {
        itemView.setOnClickListener(v -> {
            if (onClickItemHistory != null) {
                onClickItemHistory.onClickItem(item);
            }
        });
    }

    public void setOnClickItemHistory(OnClickItemHistory onClickItemHistory) {
        this.onClickItemHistory = onClickItemHistory;
    }
}
