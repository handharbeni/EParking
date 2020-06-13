package mhandharbeni.eparking.view;

import android.widget.EditText;

import com.manishkprboilerplate.base.UiView;

import java.util.List;

import mhandharbeni.eparking.database.models.response.Kendaraan;

public interface MainUIView extends UiView {
    void listDataKendaraan(List<Kendaraan> kendaraanList);
    void listDataKendaraan(List<Kendaraan> kendaraanList, Throwable e);

    void kendaraanMasukSukses(Kendaraan kendaraan);
    void kendaraanKeluarSukses(Kendaraan kendaraan);

    void kendaraanMasukGagal(Kendaraan kendaraan, Throwable throwable);
    void kendaraanKeluarGagal(Kendaraan kendaraan, Throwable throwable);

    void showSplashScreen();
    void showLoginPage();
    void showMainPage(String username, String deviceId);

    void loginSukses();
    void logoutSukses();

    void permissionGranted();
    void permissionDenied();

    void showError(EditText editText, String message);
}
