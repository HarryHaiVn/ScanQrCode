package harry.vn.qrcode;

import android.app.Application;

import net.glxn.qrgen.android.QRCode;

import java.io.File;

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
