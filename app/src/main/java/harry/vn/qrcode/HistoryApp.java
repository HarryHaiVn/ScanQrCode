package harry.vn.qrcode;

import android.app.Application;

import harry.vn.qrcode.db.HistoryRepository;

public class HistoryApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        getRepository();
    }

    public HistoryRepository getRepository() {
        return HistoryRepository.getInstance(this);
    }
}
