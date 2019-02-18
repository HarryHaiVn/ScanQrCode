package harry.vn.qrcode.fragment;

import harry.vn.qrcode.R;
import harry.vn.qrcode.utils.FragmentUtil;

public class HistoryContainerFragment extends BaseFragment {

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
