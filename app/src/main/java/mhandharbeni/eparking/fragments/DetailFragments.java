package mhandharbeni.eparking.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mhandharbeni.eparking.R;
import mhandharbeni.eparking.database.models.response.Kendaraan;
import mhandharbeni.eparking.utilitas.BaseBottomFragment;
import mhandharbeni.eparking.utilitas.Utils;

public class DetailFragments extends BaseBottomFragment {
    Activity activity;
    Kendaraan kendaraan;
    DetailFragmentCallback detailFragmentCallback;

    Unbinder unbinder;
    View view;

    @BindView(R.id.detailPlatNo) TextView detailPlatNo;
    @BindView(R.id.detailTiketNo) TextView detailTiketNo;
    @BindView(R.id.detailTanggal) TextView detailTanggal;
    @BindView(R.id.detailJamMasuk) TextView detailJamMasuk;
    @BindView(R.id.detailJamKeluar) TextView detailJamKeluar;
    @BindView(R.id.detailDurasi) TextView detailDurasi;

    @BindView(R.id.btnProsesKendaraanKeluar) AppCompatButton btnProsesKendaraanKeluar;

    public DetailFragments(Activity activity, Kendaraan kendaraan, DetailFragmentCallback detailFragmentCallback) {
        this.activity = activity;
        this.kendaraan = kendaraan;
        this.detailFragmentCallback = detailFragmentCallback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_detail_screen, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();
    }

    @OnClick(R.id.btnProsesKendaraanKeluar)
    public void btnKeluarClicked(){
        detailFragmentCallback.onKeluarClicked(kendaraan);
        dismiss();
    }

    void initData(){
        detailPlatNo.setText(kendaraan.getPlatNo());
        detailTiketNo.setText(kendaraan.getTiketNo());
        detailTanggal.setText(Utils.getDateFromMillis(kendaraan.getDateNow()!=null?
                kendaraan.getDateNow():System.currentTimeMillis()));
        detailJamMasuk.setText(Utils.getTimeFromMillis(kendaraan.getTimeIn()!=null?
                kendaraan.getTimeIn():System.currentTimeMillis()));
        detailJamKeluar.setText(Utils.getTimeFromMillis(kendaraan.getTimeOut()!=null?
                kendaraan.getTimeOut():System.currentTimeMillis()));
    }



    @Override
    public void onDestroy() {
        try {
            unbinder.unbind();
        }catch (Exception ignored){}
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        try {
            unbinder.unbind();
        }catch (Exception ignored){}
        super.onDestroyView();
    }

    public interface DetailFragmentCallback{
        void onKeluarClicked(Kendaraan kendaraan);
    }
}
