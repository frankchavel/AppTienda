package unc.pe.apptienda;

public class Producto {
    String nombre;
    double precio;
    int imagen;

    public Producto(String nombre, double precio, int imagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getImagen() {
        return imagen;
    }
}
