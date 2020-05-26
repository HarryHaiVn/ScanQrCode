package harry.vn.qrcode.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import harry.vn.qrcode.R;
import harry.vn.qrcode.listener.OnClickItemHistory;
import harry.vn.qrcode.model.MenuModel;

public class MenuViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.txtProduct)
    TextView txtProduct;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.ivDelete)
    ImageView ivDelete;
    @BindView(R.id.ivQRCode)
    ImageView ivQRCode;
    @BindView(R.id.ivLike)
    ImageView ivLike;
    OnClickItemHistory onClickItemHistory;

    public MenuViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @SuppressLint("ResourceType")
    public void setData(MenuModel item, int position) {
        ivDelete.setVisibility(View.GONE);
        txtProduct.setVisibility(View.GONE);
        txtName.setText(item.getName());
        txtName.setTextColor(itemView.getContext().getResources().getColor(R.color.colorButtonGrey));
        ivQRCode.setImageResource(item.getResId());
        itemView.setOnClickListener(v -> {
            if (onClickItemHistory != null) {
                onClickItemHistory.onClickItemMenu(item,position);
            }
        });
    }

    public void setOnClickItemHistory(OnClickItemHistory onClickItemHistory) {
        this.onClickItemHistory = onClickItemHistory;
    }
}
