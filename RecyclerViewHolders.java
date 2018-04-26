package ar.com.nbcargo.nbcargo_novedades;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView novedad;
    public TextView id;
    public TextView chofer;
    public TextView unidad;
    public ImageView severidad;
    public TextView fecha;
    public View tarjeta;
    public TextView titId;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        novedad = itemView.findViewById(R.id.tarj_text_novedad);
        id = itemView.findViewById(R.id.tarj_text_id);
        unidad = itemView.findViewById(R.id.tarj_text_unidad);
        chofer = itemView.findViewById(R.id.tarj_text_chofer);
        severidad = itemView.findViewById(R.id.tarj_text_severidad);
        fecha = itemView.findViewById(R.id.tarj_text_fecha);
        tarjeta = itemView.findViewById(R.id.tarjetas_card);
        titId = itemView.findViewById(R.id.tarj_tit_id);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(itemView.getContext(), acciones.class);
        Bundle valores = new Bundle();
        //intent.putExtra("id", id.getText());
        valores.putString("id", id.getText().toString());
        ColorDrawable buttonColor = (ColorDrawable) tarjeta.getBackground();
        int colorId = Integer.valueOf(buttonColor.getColor());
        valores.putInt("color", colorId);
        intent.putExtras(valores);
        Log.d("TAG2", "Color: " + colorId);
        Log.d("TAG", "Valor de id: " + id.getText());
        itemView.getContext().startActivity(intent);
    }

}
