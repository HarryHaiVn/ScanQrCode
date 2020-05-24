package harry.vn.qrcode.fragment;

import android.support.v4.app.Fragment;

import harry.vn.qrcode.R;
import harry.vn.qrcode.utils.FragmentUtil;

public class HistoryContainerFragment extends BaseFragment {

    public static Fragment newInstance() {
        return new HistoryContainerFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_history_container;
    }

    @Override
    protected void initChildView() {
        if (getActivity() == null) return;
        FragmentUtil.addFragment(getActivity().getSupportFragmentManager(), HistoryFragment.newInstance(), true, null, HistoryFragment.class.getName(), false);
    }

    @Override
    protected void initData() {

    }
}
