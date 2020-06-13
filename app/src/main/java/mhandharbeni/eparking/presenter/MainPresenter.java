package mhandharbeni.eparking.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.EditText;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.manishkprboilerplate.base.BasePresenter;
import com.manishkprboilerplate.web_services.RxUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import mhandharbeni.eparking.database.AppDB;
import mhandharbeni.eparking.database.models.response.Kendaraan;
import mhandharbeni.eparking.utilitas.AppPreferences;
import mhandharbeni.eparking.utilitas.Constant;
import mhandharbeni.eparking.view.MainUIView;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenter extends BasePresenter<MainUIView> {
    Activity activity;
    private Subscription mSubscription;
    private LifecycleOwner lifecycleOwner;
    final String TAG = this.getClass().getSimpleName().toString();

    public MainPresenter(Activity activity, LifecycleOwner lifecycleOwner) {
        this.activity = activity;
        this.lifecycleOwner = lifecycleOwner;
    }

    @Override
    public void attachView(MainUIView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void requestPermission() {
//        Dexter.withActivity(activity).withPermission()
        Dexter.withActivity(activity)
                .withPermissions(Manifest.permission.READ_PHONE_STATE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            getMvpView().permissionGranted();
                        } else {
                            getMvpView().permissionDenied();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions
                            , PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .check();
    }

    public void doLogin(EditText username) {
        if (username != null){
            if (username.getText().toString().equals("") || username.getText().toString().isEmpty()){
                getMvpView().showError(username, "Username Tidak Boleh Kosong");
            }else{
                AppPreferences.getInstance(activity.getApplicationContext()).setPref(Constant.USERNAME,
                        username.getText().toString());
                AppPreferences.getInstance(activity.getApplicationContext()).setPref(Constant.STATE_LOGIN,
                        true);

                getMvpView().loginSukses();
            }
        }
    }

    public void doLogout(){
        AppPreferences.getInstance(activity.getApplicationContext()).setPref(Constant.USERNAME,
                "notset");
        AppPreferences.getInstance(activity.getApplicationContext()).setPref(Constant.STATE_LOGIN
                , false);

        getMvpView().logoutSukses();
    }

    public void switchScreen(){
        boolean loginState = AppPreferences.getInstance(activity.getApplicationContext()).getPref(Constant.STATE_LOGIN, false);
        Log.d(TAG, "countDown onFinish: "+loginState);
        if (loginState){
            getMvpView().showMainPage(
                    AppPreferences.getInstance(activity.getApplicationContext()).getPref(Constant.USERNAME, "notset"),
                    AppPreferences.getInstance(activity.getApplicationContext()).getPref(Constant.DEVICE_ID, "notset")
            );
        }else{
            getMvpView().showLoginPage();
        }
    }

    public void initSplashScreen(){
        getMvpView().showSplashScreen();

        new CountDownTimer(Constant.COUNTDOWN, Constant.COUNTDOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG, "countDown onTick: "+String.valueOf(millisUntilFinished / Constant.COUNTDOWN_INTERVAL));
            }

            @Override
            public void onFinish() {
                boolean loginState = AppPreferences.getInstance(activity.getApplicationContext()).getPref(Constant.STATE_LOGIN, false);
                Log.d(TAG, "countDown onFinish: "+loginState);
                if (loginState){
                    getMvpView().showMainPage(
                            AppPreferences.getInstance(activity.getApplicationContext()).getPref(Constant.USERNAME, "notset"),
                            AppPreferences.getInstance(activity.getApplicationContext()).getPref(Constant.DEVICE_ID, "notset")
                    );
                }else{
                    getMvpView().showLoginPage();
                }
            }
        }.start();
    }

    public void fetchKendaraan(){
//        AppDB.getInstance(activity.getApplicationContext()).kendaraan().getAllRealTime().observe(lifecycleOwner, new Observer<List<Kendaraan>>() {
//            @Override
//            public void onChanged(List<Kendaraan> kendaraans) {
//                getMvpView().listDataKendaraan(kendaraans);
//            }
//        });
        Single.fromCallable(() -> AppDB.getInstance(activity.getApplicationContext()).kendaraan().getAll()).subscribe(new SingleSubscriber<List<Kendaraan>>() {
            @Override
            public void onSuccess(List<Kendaraan> kendaraans) {
                getMvpView().listDataKendaraan(kendaraans);
            }

            @Override
            public void onError(Throwable error) {
                getMvpView().listDataKendaraan(new ArrayList<>(), error);
            }
        });
    }

    public void kendaraanMasuk(final Kendaraan kendaraan){
        RxUtil.unSubscribe(mSubscription);
        mSubscription = Single.fromCallable((Callable<Object>) () -> AppDB.getInstance(activity.getApplicationContext()).kendaraan().insert(kendaraan)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        getMvpView().kendaraanMasukSukses(kendaraan);
                    }

                    @Override
                    public void onError(Throwable error) {
                        getMvpView().kendaraanMasukGagal(kendaraan, error);
                    }
                });
    }

    public void kendaraanKeluar(final Kendaraan kendaraan){
        RxUtil.unSubscribe(mSubscription);
        List<Kendaraan> existingKendaraan = new ArrayList<>();;
        existingKendaraan = AppDB.getInstance(activity.getApplicationContext())
                .kendaraan()
                .getKendaraan(kendaraan.getPlatNo(), kendaraan.getTiketNo());
        if (existingKendaraan.size() < 1){
            getMvpView().kendaraanKeluarGagal(kendaraan, new Throwable("Kendaraan Not Found"));
        }else{
            kendaraan.setTimeOut(System.currentTimeMillis());
            mSubscription = Single.fromCallable((Callable<Object>) () -> AppDB.getInstance(activity.getApplicationContext()).kendaraan().insert(kendaraan)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleSubscriber<Object>() {
                        @Override
                        public void onSuccess(Object o) {
                            getMvpView().kendaraanKeluarSukses(kendaraan);
                        }

                        @Override
                        public void onError(Throwable error) {
                            getMvpView().kendaraanKeluarGagal(kendaraan, error);
                        }
                    });
//            AppDB.getInstance(activity.getApplicationContext())
//                    .kendaraan()
//                    .insert(kendaraan)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Action0() {
//                        @Override
//                        public void call() {
//                            getMvpView().kendaraanKeluarSukses(kendaraan);
//                        }
//                    }, new Action1<Throwable>() {
//                        @Override
//                        public void call(Throwable throwable) {
//                            getMvpView().kendaraanKeluarGagal(kendaraan, throwable);
//                        }
//                    });
//
        }
    }
}
