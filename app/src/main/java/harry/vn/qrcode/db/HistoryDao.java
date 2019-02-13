package harry.vn.qrcode.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import harry.vn.qrcode.model.HistoryModel;

@Dao
public interface HistoryDao {
    @Query("SELECT * FROM history")
    List<HistoryModel> getAll();

    @Query("SELECT * FROM history WHERE id IN (:historyIds)")
    List<HistoryModel> loadAllByIds(int[] historyIds);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(HistoryModel  historyModels);

    @Delete
    void delete(HistoryModel historyModel);
}
