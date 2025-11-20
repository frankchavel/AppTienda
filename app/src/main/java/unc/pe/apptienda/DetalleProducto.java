package unc.pe.apptienda;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

import unc.pe.apptienda.databinding.ActivityDetalleProductoBinding;

public class DetalleProducto extends AppCompatActivity {

    private ActivityDetalleProductoBinding binding;

    // Datos enviados desde la lista
    private String nombreProducto;
    private double precioProducto;
    private int imagenProducto;

    // Datos de obsequios
    String[] nombresRegalos = {
            "Set de tazas x 6 und.",
            "Individuales x 12 und.",
            "Bowls x 6 und.",
            "Set de vasos x 6 und.",
            "Cucharitas de té x 12 und."
    };

    int[] imagenesRegalos = {
            R.drawable.tazas,
            R.drawable.individuales,
            R.drawable.bowls,
            R.drawable.vasos,
            R.drawable.cucharas
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDetalleProductoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        recibirDatos();
        mostrarDatos();
        configurarBotonObsequio();
        configurarBotonComprar();
        configurarBotonRegresar();   // ← agregado
    }

    private void recibirDatos() {
        nombreProducto = getIntent().getStringExtra("nombre");
        precioProducto = getIntent().getDoubleExtra("precio", 0.0);
        imagenProducto = getIntent().getIntExtra("imagen", 0);
    }

    private void mostrarDatos() {
        binding.productName.setText(nombreProducto);
        binding.productPrice.setText("S/ " + precioProducto);
        binding.productImage.setImageResource(imagenProducto);
    }

    private void configurarBotonObsequio() {
        binding.btnObsequio.setOnClickListener(v -> generarObsequio());
    }

    private void generarObsequio() {
        Random random = new Random();
        int index = random.nextInt(nombresRegalos.length);

        binding.ivRegalo.setImageResource(imagenesRegalos[index]);
        binding.tvRegalo.setText(nombresRegalos[index]);
    }

    private void configurarBotonComprar() {
        binding.btnComprar.setOnClickListener(v -> {

            int cantidad = 1;   // si todavía no usas un selector, va fijo
            double subtotal = precioProducto * cantidad;

            double adicionales = 0;  // si después agregas, ya está listo
            double descuento = 0;    // igual
            double total = subtotal + adicionales - descuento;

            Intent intent = new Intent(DetalleProducto.this, ResumenCompra.class);
            intent.putExtra("nombre", nombreProducto);
            intent.putExtra("precio", precioProducto);
            intent.putExtra("cantidad", cantidad);
            intent.putExtra("subtotal", subtotal);
            intent.putExtra("adicionales", adicionales);
            intent.putExtra("descuento", descuento);
            intent.putExtra("total", total);

            startActivity(intent);
        });
    }


    private void configurarBotonRegresar() {
        binding.btnRegresar.setOnClickListener(v -> {
            finish(); // Regresa a ListaProductos
        });
    }
}
