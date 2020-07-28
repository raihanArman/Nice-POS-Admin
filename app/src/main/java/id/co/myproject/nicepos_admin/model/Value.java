package id.co.myproject.nicepos_admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Value {
    @SerializedName("value")
    @Expose
    private int value;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("id_supplier")
    @Expose
    private int idSupplier;

    @SerializedName("id_admin")
    @Expose
    private int idAdmin;

    public Value() {
    }

    public Value(int value, String message, int idSupplier, int idAdmin) {
        this.value = value;
        this.message = message;
        this.idSupplier = idSupplier;
        this.idAdmin = idAdmin;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(int idSupplier) {
        this.idSupplier = idSupplier;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }
}
