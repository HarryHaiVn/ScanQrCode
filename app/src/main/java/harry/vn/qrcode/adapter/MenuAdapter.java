package harry.vn.qrcode.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import harry.vn.qrcode.R;
import harry.vn.qrcode.listener.OnClickItemHistory;
import harry.vn.qrcode.model.MenuModel;

public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {
    private List<MenuModel> data = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private OnClickItemHistory onClickItemHistory;

    public MenuAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = mLayoutInflater.inflate(R.layout.item_history, parent, false);
        return new MenuViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        holder.setData(data.get(position),position);
        holder.setOnClickItemHistory(onClickItemHistory);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<MenuModel> listData) {
        data.clear();
        data = listData;
        notifyDataSetChanged();
    }

    public void setListener(OnClickItemHistory onClickListener) {
        this.onClickItemHistory = onClickListener;
    }
}
