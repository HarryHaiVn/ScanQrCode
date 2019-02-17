package harry.vn.qrcode.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import butterknife.BindView;
import harry.vn.qrcode.HistoryApp;
import harry.vn.qrcode.R;
import harry.vn.qrcode.adapter.HistoryAdapter;
import harry.vn.qrcode.db.HistoryRepository;
import harry.vn.qrcode.model.HistoryModel;

public class HistoryFragment extends BaseFragment {
    ArrayList<HistoryModel> listData = new ArrayList<>();
    @BindView(R.id.rvHistory)
    RecyclerView rvHistory;
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_history;
    }

    HistoryAdapter mAdapter;
    @Override
    protected void initChildView() {
        mAdapter = new HistoryAdapter(getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvHistory.setLayoutManager(mLayoutManager);
        rvHistory.setItemAnimator(new DefaultItemAnimator());
        rvHistory.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        new Thread(() -> {
            if (getActivity() == null) return;
            HistoryRepository historyRepository = ((HistoryApp) getActivity().getApplication()).getRepository();
            listData.addAll(historyRepository.getAll());
            Log.i("List Size History :", listData.size() + "");
            getActivity().runOnUiThread(() -> mAdapter.setData(listData));
        }).start();
    }
}
