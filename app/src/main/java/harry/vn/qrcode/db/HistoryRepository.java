package harry.vn.qrcode.db;

import android.app.Application;

import java.util.List;

import harry.vn.qrcode.model.HistoryModel;

public class HistoryRepository {
    private HistoryDao mHistoryDao;

    private static HistoryRepository sInstance;

    public static HistoryRepository getInstance(Application application) {
        if (sInstance == null) {
            synchronized (HistoryRepository.class) {
                if (sInstance == null) {
                    sInstance = new HistoryRepository(application);
                }
            }
        }
        return sInstance;
    }

    private HistoryRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mHistoryDao = db.historyDao();
    }

    public void insertHistory(final HistoryModel historyModel) {
        mHistoryDao.insert(historyModel);
    }

    public List<HistoryModel> getAll() {
        return mHistoryDao.getAll();
    }

    public void onRemoveItem(HistoryModel item) {
        mHistoryDao.delete(item);
    }

}
