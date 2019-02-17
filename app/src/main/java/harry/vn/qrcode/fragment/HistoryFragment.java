package harry.vn.qrcode.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import harry.vn.qrcode.R;
import harry.vn.qrcode.adapter.HistoryAdapter;
import harry.vn.qrcode.listener.OnClickItemHistory;
import harry.vn.qrcode.model.HistoryModel;

public class HistoryFragment extends BaseFragment implements OnClickItemHistory {
    public static final String KEY_DATA = "DATA";
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
                listData.add(new HistoryModel("https://medium.com/@ajaysaini.official/building-database-with-room-persistence-library-ecf7d0b8f3e9"));
            }
            getActivity().runOnUiThread(() -> mAdapter.setData(listData));
        }).start();
    }

    @Override
    public void onClickItem(HistoryModel item) {
        if (getActivity() == null) return;
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Bundle bundle=new Bundle();
        bundle.putString(KEY_DATA,item.getLink());
        ft.replace(R.id.container, HistoryDetailFragment.newInstance(bundle));
        ft.addToBackStack(HistoryDetailFragment.class.getName());
        ft.commit();
    }
}
