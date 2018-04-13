package ar.com.nbcargo.nbcargo_novedades;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<ItemObject> itemList;
    private Context context;


    public RecyclerViewAdapter(Context context, List<ItemObject> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.novedad.setText(itemList.get(position).getNovedad());
        holder.id.setText(itemList.get(position).getId());
        holder.titId.setText("N° de Incidente: " + itemList.get(position).getId());
        holder.chofer.setText(itemList.get(position).getChofer());
        holder.unidad.setText(itemList.get(position).getunidad());
        holder.fecha.setText(itemList.get(position).getfecha());
        switch (itemList.get(position).getarea()) {
            case "Taller": {
                holder.tarjeta.setBackgroundColor(Color.parseColor("#227585"));
                break;
            }
            case "Patrimonial": {
                holder.tarjeta.setBackgroundColor(Color.parseColor("#FF8A80"));
                break;
            }
            case "Documentacion": {
                holder.tarjeta.setBackgroundColor(Color.parseColor("#2D566B"));
                break;
            }
            case "Trafico": {
                holder.tarjeta.setBackgroundColor(Color.parseColor("#B388FF"));
                break;
            }
            case "Conformes": {
                holder.tarjeta.setBackgroundColor(Color.parseColor("#E91E63"));
                break;
            }
        }

        switch (itemList.get(position).getseveridad()) {
            case "Leve": {
                holder.severidad.setImageResource(R.drawable.ok);
                break;
            }
            case "Moderada": {
                holder.severidad.setImageResource(R.drawable.atencion);
                break;
            }
            case "Grave": {
                holder.severidad.setImageResource(R.drawable.alerta);
                break;
            }
        }


    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }


}
