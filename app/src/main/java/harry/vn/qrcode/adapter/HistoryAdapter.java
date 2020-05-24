package harry.vn.qrcode.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import harry.vn.qrcode.R;
import harry.vn.qrcode.listener.OnClickItemHistory;
import harry.vn.qrcode.model.HistoryModel;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {
    private List<HistoryModel> data;
    private LayoutInflater mLayoutInflater;
    private OnClickItemHistory onClickItemHistory;

    public HistoryAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = mLayoutInflater.inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.setData(data.get(position));
        holder.setOnClickItemHistory(onClickItemHistory);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<HistoryModel> listData) {
        if (data == null) {
            data = listData;
            notifyItemRangeInserted(0, listData.size());
        } else {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return data.size();
                }

                @Override
                public int getNewListSize() {
                    return listData.size();
                }

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    HistoryModel old = data.get(oldItemPosition);
                    HistoryModel historyModel = listData.get(newItemPosition);
                    return Objects.equals(old.getId(), historyModel.getId());
                }

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    HistoryModel old = data.get(oldItemPosition);
                    HistoryModel historyModel = listData.get(newItemPosition);
                    return Objects.equals(old.getId(), historyModel.getId())
                            && Objects.equals(old.getLink(), historyModel.getLink());
                }

                @Nullable
                @Override
                public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                    return super.getChangePayload(oldItemPosition, newItemPosition);
                }
            });

            data = listData;
            diffResult.dispatchUpdatesTo(this);
        }
    }

    public void setListener(OnClickItemHistory onClickListener) {
        this.onClickItemHistory=onClickListener;
    }
}
