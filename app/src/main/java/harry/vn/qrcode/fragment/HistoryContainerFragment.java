package harry.vn.qrcode.fragment;

import android.support.v4.app.FragmentTransaction;

import harry.vn.qrcode.R;

public class HistoryContainerFragment extends BaseFragment {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_history_container;
    }

    @Override
    protected void initChildView() {
       // Begin the transaction
        if (getActivity() == null) return;
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container, new HistoryFragment());
        ft.addToBackStack(HistoryFragment.class.getName());
        ft.commit();
    }

    @Override
    protected void initData() {

    }
}
