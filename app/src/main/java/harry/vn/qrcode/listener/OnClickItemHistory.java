package harry.vn.qrcode.listener;

import harry.vn.qrcode.model.HistoryModel;
import harry.vn.qrcode.model.MenuModel;

public interface OnClickItemHistory {
    void onClickItem(HistoryModel item);

    void onClickItemMenu(MenuModel item, int position);

    void onRemoveItem(HistoryModel item);
}
