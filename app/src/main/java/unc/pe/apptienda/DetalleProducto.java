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

            String textoCantidad = binding.etCantidad.getText().toString().trim();

            if (textoCantidad.isEmpty()) {
                binding.etCantidad.setError("Ingrese una cantidad");
                return;
            }
            int cantidad = Integer.parseInt(textoCantidad);

            // 2) CALCULAR ADICIONALES SEGÚN LA INTERFAZ (PORCENTAJES)
            double montoAdicional = 0;

            if (binding.cbInstalacion.isChecked())
                montoAdicional += precioProducto * cantidad * 0.05;   // 5%

            if (binding.cbMantenimiento.isChecked())
                montoAdicional += precioProducto * cantidad * 0.10;   // 10%

            if (binding.cbSeguro.isChecked())
                montoAdicional += precioProducto * cantidad * 0.07;   // 7%

            // 3) CALCULAR DESCUENTO
            double montoDescuento = 0;

            if (binding.rbTarjeta.isChecked()) {
                montoDescuento = precioProducto * cantidad * 0.10; // 10%
            } else if (binding.rbSinTarjeta.isChecked()) {
                montoDescuento = precioProducto * cantidad * 0.05; // 5%
            }

            // 4) CALCULAR SUBTOTAL Y TOTAL
            double subtotal = precioProducto * cantidad;
            double total = subtotal + montoAdicional - montoDescuento;


            Intent intent = new Intent(DetalleProducto.this, ResumenCompra.class);
            intent.putExtra("nombre", nombreProducto);
            intent.putExtra("precio", precioProducto);
            intent.putExtra("cantidad", cantidad);
            intent.putExtra("subtotal", subtotal);
            intent.putExtra("adicionales", montoAdicional);
            intent.putExtra("descuento", montoDescuento);
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
