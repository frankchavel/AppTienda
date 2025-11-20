package unc.pe.apptienda;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import unc.pe.apptienda.databinding.ActivityResumenCompraBinding;

public class ResumenCompra extends AppCompatActivity {

    ActivityResumenCompraBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityResumenCompraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recibirDatos();
        configurarBotonRegresar();
    }

    private void recibirDatos() {

        String nombre = getIntent().getStringExtra("nombre");
        double precio = getIntent().getDoubleExtra("precio", 0.0);
        int cantidad = getIntent().getIntExtra("cantidad", 1);
        double subtotal = getIntent().getDoubleExtra("subtotal", 0.0);
        double adicionales = getIntent().getDoubleExtra("adicionales", 0.0);
        double descuento = getIntent().getDoubleExtra("descuento", 0.0);
        double total = getIntent().getDoubleExtra("total", 0.0);

        binding.tvNombreProducto.setText("Producto: " + nombre);
        binding.tvPrecioCantidad.setText("Precio: S/ " + precio + "   |   Cantidad: " + cantidad);
        binding.tvSubtotal.setText("Subtotal: S/ " + subtotal);
        binding.tvAdicionales.setText("Adicionales: S/ " + adicionales);
        binding.tvDescuento.setText("Descuento: S/ " + descuento);
        binding.tvTotalPagar.setText("Total a pagar: S/ " + total);
    }

    private void configurarBotonRegresar() {
        binding.btnRegresar.setOnClickListener(v -> finish());
    }
}
