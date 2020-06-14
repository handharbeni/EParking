package mhandharbeni.eparking.database.models.response;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Entity(indices = { @Index(value = {"platNo", "tiketNo"}, unique = true) })
public class Kendaraan {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String platNo;
    private String tiketNo;
    private Long timeIn;
    private Long timeOut;
    private Long dateNow;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlatNo() {
        return platNo;
    }

    public void setPlatNo(String platNo) {
        this.platNo = platNo;
    }

    public String getTiketNo() {
        return tiketNo;
    }

    public void setTiketNo(String tiketNo) {
        this.tiketNo = tiketNo;
    }

    public Long getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Long timeIn) {
        this.timeIn = timeIn;
    }

    public Long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Long timeOut) {
        this.timeOut = timeOut;
    }

    public Long getDateNow() {
        return dateNow;
    }

    public void setDateNow(Long dateNow) {
        this.dateNow = dateNow;
    }
}
