package mhandharbeni.eparking.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mhandharbeni.eparking.R;
import mhandharbeni.eparking.database.models.response.Kendaraan;
import mhandharbeni.eparking.utilitas.Utils;

public class KendaraanAdapter extends RecyclerView.Adapter<KendaraanAdapter.ViewHolder> {
    List<Kendaraan> listKendaraan;
    Context context;
    KendaraanInterface kendaraanInterface;

    public KendaraanAdapter(List<Kendaraan> listKendaraan, Context context, KendaraanInterface kendaraanInterface) {
        this.listKendaraan = listKendaraan;
        this.context = context;
        this.kendaraanInterface = kendaraanInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kendaraan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Kendaraan kendaraan = listKendaraan.get(position);
        holder.bindData(kendaraan);
        holder.itemView.setOnClickListener(v -> kendaraanInterface.onKendaraanClick(kendaraan));
    }

    @Override
    public int getItemCount() {
        return listKendaraan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtItemPlatNo)TextView txtItemPlatNo;
        @BindView(R.id.txtItemTiketNo) TextView txtItemTiketNo;
        @BindView(R.id.txtItemDate) TextView txtItemDate;
        @BindView(R.id.txtItemJamMasuk) TextView txtItemJamMasuk;
        @BindView(R.id.txtItemJamKeluar) TextView txtItemJamKeluar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(Kendaraan kendaraan){

            try {
                txtItemPlatNo.setText(kendaraan.getPlatNo());
                txtItemTiketNo.setText(kendaraan.getTiketNo());

                txtItemDate.setText(
                        String.format(
                                "%s",
                                Utils.getDateFromMillis(kendaraan.getDateNow())
                        )
                );

                txtItemJamMasuk.setText(
                        String.format("%s", Utils.getTimeFromMillis(kendaraan.getTimeIn()))
                );

                txtItemJamKeluar.setText(
                        String.format("%s", Utils.getTimeFromMillis(kendaraan.getTimeOut()))
                );
            } catch (NullPointerException ignored){

            }
        }
    }

    public interface KendaraanInterface{
        void onKendaraanClick(Kendaraan kendaraan);
    }
}
