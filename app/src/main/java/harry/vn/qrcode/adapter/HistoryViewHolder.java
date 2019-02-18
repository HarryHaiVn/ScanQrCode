package harry.vn.qrcode.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import harry.vn.qrcode.R;
import harry.vn.qrcode.listener.OnClickItemHistory;
import harry.vn.qrcode.model.HistoryModel;

public class HistoryViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.txtProduct)
    TextView txtProduct;
    @BindView(R.id.ivDelete)
    ImageView ivDelete;
    OnClickItemHistory onClickItemHistory;
    public HistoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void setData(HistoryModel item) {
        itemView.setOnClickListener(v -> {
            if (onClickItemHistory != null) {
                onClickItemHistory.onClickItem(item);
            }
        });
        ivDelete.setOnClickListener(v -> {
            if (onClickItemHistory != null) {
                onClickItemHistory.onRemoveItem(item);
            }
        });
    }

    public void setOnClickItemHistory(OnClickItemHistory onClickItemHistory) {
        this.onClickItemHistory = onClickItemHistory;
    }
}
