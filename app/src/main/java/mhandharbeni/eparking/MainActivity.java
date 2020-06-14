package mhandharbeni.eparking;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mhandharbeni.eparking.adapters.KendaraanAdapter;
import mhandharbeni.eparking.database.models.response.Kendaraan;
import mhandharbeni.eparking.fragments.DetailFragments;
import mhandharbeni.eparking.presenter.MainPresenter;
import mhandharbeni.eparking.utilitas.AppPreferences;
import mhandharbeni.eparking.utilitas.BaseActivity;
import mhandharbeni.eparking.utilitas.Constant;
import mhandharbeni.eparking.view.MainUIView;

public class MainActivity extends BaseActivity implements MainUIView, KendaraanAdapter.KendaraanInterface, DetailFragments.DetailFragmentCallback {
    String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.loginScreen) ConstraintLayout loginLayout;
    @BindView(R.id.splashScreen) ConstraintLayout splashScreen;
    @BindView(R.id.mainScreen) ConstraintLayout mainScreen;
    @BindView(R.id.username) TextInputLayout username;
    @BindView(R.id.btnLogin) AppCompatButton btnLogin;
    @BindView(R.id.btnLogout) AppCompatButton btnLogout;


    @BindView(R.id.txtUsername) TextView txtUsername;
    @BindView(R.id.txtDeviceID) TextView txtDeviceId;
    @BindView(R.id.platNo) TextInputLayout platNo;
    @BindView(R.id.tiketNo) TextInputLayout tiketNo;
    @BindView(R.id.btnMasuk) AppCompatButton btnMasuk;

    @BindView(R.id.listKendaraan)RecyclerView listKendaraan;
    KendaraanAdapter kendaraanAdapter;
    LinearLayoutManager linearLayoutManager;
    GridLayoutManager gridLayoutManager;


    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }


    void init(){
        ButterKnife.bind(this);
        mainPresenter = new MainPresenter(this, this);
        mainPresenter.attachView(this);

        mainPresenter.initSplashScreen();
    }

    @OnClick(R.id.btnLogin)
    public void doLogin(){
        mainPresenter.doLogin(username.getEditText());
    }

    @OnClick(R.id.btnLogout)
    public void doLogout(){
        mainPresenter.doLogout();
    }

    @OnClick(R.id.btnMasuk)
    public void clickBtnMasuk(){
        mainPresenter.validateKendaraanInput(
                platNo.getEditText(),
                tiketNo.getEditText(),
                Constant.STATE_VALIDATED_MASUK
        );
    }

    @Override
    public void listDataKendaraan(List<Kendaraan> kendaraanList) {
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        kendaraanAdapter = new KendaraanAdapter(kendaraanList, getApplicationContext(), this);

        listKendaraan.setLayoutManager(gridLayoutManager);
        listKendaraan.setAdapter(kendaraanAdapter);

        listKendaraan.scrollToPosition(kendaraanList.size() - 1);
    }

    @Override
    public void listDataKendaraan(List<Kendaraan> kendaraanList, Throwable e) {

    }

    @Override
    public void kendaraanMasukSukses(Kendaraan kendaraan) {
        mainPresenter.fetchKendaraan();
    }

    @Override
    public void kendaraanKeluarSukses(Kendaraan kendaraan) {

    }

    @Override
    public void kendaraanMasukGagal(Kendaraan kendaraan, Throwable throwable) {

    }

    @Override
    public void kendaraanKeluarGagal(Kendaraan kendaraan, Throwable throwable) {

    }

    @Override
    public void showSplashScreen() {
        mainPresenter.requestPermission();
        splashScreen.setVisibility(View.VISIBLE);
        loginLayout.setVisibility(View.GONE);
        mainScreen.setVisibility(View.GONE);
    }

    @Override
    public void showLoginPage() {
        mainPresenter.requestPermission();
        splashScreen.setVisibility(View.GONE);
        loginLayout.setVisibility(View.VISIBLE);
        mainScreen.setVisibility(View.GONE);
    }

    @Override
    public void showMainPage(String username, String deviceId) {
        mainPresenter.requestPermission();
        splashScreen.setVisibility(View.GONE);
        loginLayout.setVisibility(View.GONE);
        mainScreen.setVisibility(View.VISIBLE);

        txtUsername.setText(username);
        txtDeviceId.setText(deviceId);

        mainPresenter.fetchKendaraan();
    }

    @Override
    public void loginSukses() {
        mainPresenter.switchScreen();
    }

    @Override
    public void logoutSukses() {
        mainPresenter.switchScreen();
    }

    @SuppressLint("HardwareIds")
    @Override
    public void permissionGranted() {
        TelephonyManager telephonyManager =
                (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            mainPresenter.requestPermission();
            return;
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            AppPreferences.getInstance(getApplicationContext()).setPref(Constant.DEVICE_ID,
                    Settings.Secure.getString(
                        getContentResolver(),Settings.Secure.ANDROID_ID));
        }else{
            AppPreferences.getInstance(getApplicationContext()).setPref(Constant.DEVICE_ID,
                    telephonyManager != null ? telephonyManager.getDeviceId() : "notset");
        }
    }

    @Override
    public void permissionDenied() {

    }

    @Override
    public void showError(EditText editText, String message) {
        editText.setError(message);
    }

    @Override
    public void validated(int state) {
        switch (state){
            case Constant.STATE_VALIDATED_MASUK :
                Kendaraan kendaraan = new Kendaraan();
                kendaraan.setPlatNo(platNo.getEditText().getText().toString());
                kendaraan.setTiketNo(tiketNo.getEditText().getText().toString());
                kendaraan.setDateNow(System.currentTimeMillis());
                kendaraan.setTimeIn(System.currentTimeMillis());

                mainPresenter.kendaraanMasuk(kendaraan);
                break;
            case Constant.STATE_VALIDATED_KELUAR :
                Kendaraan kendaraanKeluar = new Kendaraan();
                kendaraanKeluar.setPlatNo(platNo.getEditText().getText().toString());
                kendaraanKeluar.setTiketNo(tiketNo.getEditText().getText().toString());
                kendaraanKeluar.setDateNow(System.currentTimeMillis());
                kendaraanKeluar.setTimeIn(System.currentTimeMillis());

                mainPresenter.kendaraanKeluar(kendaraanKeluar);
                break;
        }
    }

    @Override
    public void onKendaraanClick(Kendaraan kendaraan) {
        platNo.getEditText().setText(kendaraan.getPlatNo());
        tiketNo.getEditText().setText(kendaraan.getTiketNo());

        DetailFragments detailFragments = new DetailFragments(this, kendaraan, this);
        detailFragments.show(getSupportFragmentManager(), detailFragments.getTag());
    }

    @Override
    public void onKeluarClicked(Kendaraan kendaraan) {
        kendaraan.setTimeOut(System.currentTimeMillis());
        mainPresenter.kendaraanKeluar(kendaraan);
    }
}