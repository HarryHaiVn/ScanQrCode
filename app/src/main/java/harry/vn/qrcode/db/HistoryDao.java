package harry.vn.qrcode.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import harry.vn.qrcode.model.HistoryModel;

@Dao
public interface HistoryDao {
    @Query("SELECT * FROM history")
    List<HistoryModel> getAll();

    @Query("SELECT * FROM history WHERE id IN (:historyIds)")
    List<HistoryModel> loadAllByIds(int[] historyIds);

    @Query("SELECT * FROM history WHERE isLike = :isLike")
    List<HistoryModel> loadFavorite(boolean isLike);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(HistoryModel  historyModels);

    @Query("UPDATE history SET isLike=:isLike WHERE id = :id")
    void update(int id, boolean isLike);

    @Delete
    void delete(HistoryModel historyModel);
}
