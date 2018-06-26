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
    public TextView estadotxt;
    public Intent intent;


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
        estadotxt = itemView.findViewById(R.id.tarj_estado);

    }

    @Override
    public void onClick(View view) {

        String valorEstado;
        valorEstado = estadotxt.getText().toString();
        Log.d("Tag2", "Estado: " + valorEstado);

        if (valorEstado.equals("Realizada")) {
            intent = new Intent(itemView.getContext(), direccion.class);
            Log.d("Tag2", "intent dirección");
        } else {
            intent = new Intent(itemView.getContext(), acciones.class);
            Log.d("Tag2", "intent acciones");
        }

        Bundle valores = new Bundle();
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
