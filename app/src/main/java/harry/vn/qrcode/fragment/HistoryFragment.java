package harry.vn.qrcode.fragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import butterknife.BindView;
import harry.vn.qrcode.R;
import harry.vn.qrcode.adapter.HistoryAdapter;
import harry.vn.qrcode.listener.OnClickItemHistory;
import harry.vn.qrcode.model.HistoryModel;

public class HistoryFragment extends BaseFragment implements OnClickItemHistory {
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
        mAdapter.setListener(this);
    }

    @Override
    protected void initData() {
        new Thread(() -> {
            if (getActivity() == null) return;
//            HistoryRepository historyRepository = ((HistoryApp) getActivity().getApplication()).getRepository();
//            listData.addAll(historyRepository.getAll());
            Log.i("List Size History :", listData.size() + "");
            for (int i = 0; i < 6; i++) {
                listData.add(new HistoryModel());
            }
            getActivity().runOnUiThread(() -> mAdapter.setData(listData));
        }).start();
    }

    @Override
    public void onClickItem(HistoryModel item) {
        if (getActivity() == null) return;
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, new HistoryDetailFragment());
        ft.addToBackStack(HistoryDetailFragment.class.getName());
        ft.commit();
    }
}
