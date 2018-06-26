package ar.com.nbcargo.nbcargo_novedades;

import com.google.gson.annotations.SerializedName;

public class ItemObject {

    @SerializedName("novedad")
    private String novedad;
    @SerializedName("id")
    private String id;
    @SerializedName("chofer")
    private String chofer;
    @SerializedName("unidad")
    private String unidad;
    @SerializedName("severidad")
    private String severidad;
    @SerializedName("fecha")
    private String fecha;
    @SerializedName("area")
    private String area;
    @SerializedName("estado")
    private String estados;


    public ItemObject(String novedad, String id, String chofer, String unidad, String severidad, String fecha, String area, String estados) {
        this.novedad = novedad;
        this.id = id;
        this.chofer = chofer;
        this.unidad = unidad;
        this.severidad = severidad;
        this.fecha = fecha;
        this.area = area;
        this.estados = estados;

    }

    public String getNovedad() {
        return novedad;
    }

    public String getId() {
        return id;
    }

    public String getChofer() {
        return chofer;
    }

    public String getunidad() {
        return unidad;
    }

    public String getseveridad() {
        return severidad;
    }

    public String getfecha() {
        return fecha;
    }

    public String getarea() {
        return area;
    }

    public String getEstado() {
        return estados;
    }

}

