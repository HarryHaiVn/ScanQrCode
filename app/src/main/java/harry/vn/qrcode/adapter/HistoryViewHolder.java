package harry.vn.qrcode.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import harry.vn.qrcode.HistoryApp;
import harry.vn.qrcode.R;
import harry.vn.qrcode.db.HistoryRepository;
import harry.vn.qrcode.listener.OnClickItemHistory;
import harry.vn.qrcode.model.HistoryModel;

public class HistoryViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.txtProduct)
    TextView txtProduct;
    @BindView(R.id.ivDelete)
    ImageView ivDelete;
    @BindView(R.id.ivLike)
    ImageView ivLike;
    private OnClickItemHistory onClickItemHistory;
    public HistoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void setData(HistoryModel item) {
        if (item.isLike) ivLike.setBackgroundResource(R.drawable.d_favorites_add);
        ivLike.setBackgroundResource(R.drawable.d_favorites);
        itemView.setOnClickListener(v -> {
            if (onClickItemHistory != null) {
                onClickItemHistory.onClickItem(item);
            }
        });
        ivLike.setOnClickListener(v -> {
            if (item.isLike) {
                item.setLike(false);
                ivLike.setBackgroundResource(R.drawable.d_favorites);
            } else {
                ivLike.setBackgroundResource(R.drawable.d_favorites_add);
                item.setLike(true);
            }
            new Thread(() -> {
                HistoryRepository historyRepository = ((HistoryApp) itemView.getContext()).getRepository();
                historyRepository.updateHistory(item.getId(), item.isLike());
            }).start();
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
