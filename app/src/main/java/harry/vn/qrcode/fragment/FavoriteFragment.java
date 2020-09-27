package harry.vn.qrcode.fragment;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import harry.vn.qrcode.HistoryApp;
import harry.vn.qrcode.R;
import harry.vn.qrcode.adapter.HistoryAdapter;
import harry.vn.qrcode.bus.MessageEvent;
import harry.vn.qrcode.db.HistoryRepository;
import harry.vn.qrcode.listener.OnClickItemHistory;
import harry.vn.qrcode.model.HistoryModel;
import harry.vn.qrcode.model.MenuModel;
import harry.vn.qrcode.utils.FragmentUtil;
import harry.vn.qrcode.utils.Type;

public class FavoriteFragment extends BaseFragment implements OnClickItemHistory {
    public static final String KEY_DATA = "DATA";
    private List<HistoryModel> listData;
    @BindView(R.id.rvHistory)
    RecyclerView rvHistory;
    @BindView(R.id.txtHistory)
    TextView txtHistory;

    public static FavoriteFragment newInstance() {
        Bundle args = new Bundle();
        FavoriteFragment fragment = new FavoriteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_history;
    }

    HistoryAdapter mAdapter;
    @Override
    protected void initChildView(View mView) {
        txtHistory.setText(getString(R.string.favorite));
        mAdapter = new HistoryAdapter(getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvHistory.setLayoutManager(mLayoutManager);
        rvHistory.setItemAnimator(new DefaultItemAnimator());
        rvHistory.setAdapter(mAdapter);
        mAdapter.setListener(this);
    }

    @Override
    protected void initData() {
        if (listData != null) return;
        listData = new ArrayList<>();
        new Thread(() -> {
            if (getActivity() == null) return;
            HistoryRepository historyRepository = ((HistoryApp) getActivity().getApplication()).getRepository();
            listData.addAll(historyRepository.loadFavorite());
            getActivity().runOnUiThread(() -> mAdapter.setData(listData));
        }).start();
    }

    @Override
    public void onClickItem(HistoryModel item) {
        if (getActivity() == null) return;
        Bundle bundle=new Bundle();
        bundle.putString(KEY_DATA,item.getLink());
        FragmentUtil.showFragment(getActivity().getSupportFragmentManager(), HistoryDetailFragment.newInstance(bundle), true, null, HistoryDetailFragment.class.getName(), false);
    }

    @Override
    public void onClickItemMenu(MenuModel item, int position) {
    }

    @Override
    public void onRemoveItem(HistoryModel item) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getActivity());
        }
        builder.setMessage(R.string.content_dialog)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    new Thread(() -> {
                        if (getActivity() == null) return;
                        HistoryRepository historyRepository = ((HistoryApp) getActivity().getApplication()).getRepository();
                        historyRepository.onRemoveItem(item);
                        List<HistoryModel> list = new ArrayList<>(historyRepository.getAll());
                        getActivity().runOnUiThread(() -> mAdapter.setData(list));
                    }).start();
                })
                .setNegativeButton(android.R.string.no, (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }
    @OnClick(R.id.ivMenu)
    public void OnClickIvMenu() {
        EventBus.getDefault().post(new MessageEvent(Type.MENU));
    }
}
