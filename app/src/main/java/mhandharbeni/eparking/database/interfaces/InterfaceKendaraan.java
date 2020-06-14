package mhandharbeni.eparking.database.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import mhandharbeni.eparking.database.models.response.Kendaraan;
import rx.Completable;
import rx.Subscription;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface InterfaceKendaraan {
    @Insert(onConflict = REPLACE)
    long[] insertAll(List<Kendaraan> listKendaraan);

    @Insert(onConflict = REPLACE)
    long insert(Kendaraan kendaraan);

    @Query("SELECT * FROM Kendaraan")
    List<Kendaraan> getAll();

    @Query("SELECT * FROM Kendaraan")
    LiveData<List<Kendaraan>> getAllRealTime();


    @Query("SELECT * FROM Kendaraan WHERE platNo = :platNo AND tiketNo = :tiketNo")
    List<Kendaraan> getKendaraan(String platNo, String tiketNo);

    @Query("SELECT * FROM Kendaraan WHERE platNo = :platNo OR tiketNo = :tiketNo")
    List<Kendaraan> checkDuplicateKendaraan(String platNo, String tiketNo);


    @Query("DELETE FROM kendaraan")
    void deleteAll();

    @Delete
    void delete(Kendaraan kendaraan);
}
