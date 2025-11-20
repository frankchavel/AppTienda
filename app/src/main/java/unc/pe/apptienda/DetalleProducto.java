package unc.pe.apptienda;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

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
        // Cambios en cantidad
        binding.etCantidad.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcularTotal();
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        binding.cbInstalacion.setOnCheckedChangeListener((b, v) -> calcularTotal());
        binding.cbMantenimiento.setOnCheckedChangeListener((b, v) -> calcularTotal());
        binding.cbSeguro.setOnCheckedChangeListener((b, v) -> calcularTotal());

        binding.rbTarjeta.setOnCheckedChangeListener((b, v) -> calcularTotal());
        binding.rbSinTarjeta.setOnCheckedChangeListener((b, v) -> calcularTotal());

        recibirDatos();
        mostrarDatos();
        configurarBotonObsequio();
        configurarBotonComprar();
        configurarBotonRegresar();
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
    private void calcularTotal() {

        String txtCant = binding.etCantidad.getText().toString().trim();

        if (txtCant.isEmpty()) {
            binding.txtTotal.setText("S/ 0.00");
            return;
        }

        int cantidad = Integer.parseInt(txtCant);

        double subtotal = precioProducto * cantidad;

        double montoAdicional = 0;

        if (binding.cbInstalacion.isChecked())
            montoAdicional += subtotal * 0.05;

        if (binding.cbMantenimiento.isChecked())
            montoAdicional += subtotal * 0.10;

        if (binding.cbSeguro.isChecked())
            montoAdicional += subtotal * 0.07;

        double montoDescuento = 0;

        if (binding.rbTarjeta.isChecked())
            montoDescuento = subtotal * 0.10;
        else if (binding.rbSinTarjeta.isChecked())
            montoDescuento = subtotal * 0.05;

        double total = subtotal + montoAdicional - montoDescuento;

        binding.txtTotal.setText("S/ " + String.format("%.2f", total));
    }


    private void configurarBotonComprar() {
        binding.btnComprar.setOnClickListener(v -> {

            String textoCantidad = binding.etCantidad.getText().toString().trim();

            if (textoCantidad.isEmpty()) {
                binding.etCantidad.setError("Ingrese una cantidad");
                return;
            }
            int cantidad = Integer.parseInt(textoCantidad);

            double montoAdicional = 0;

            if (binding.cbInstalacion.isChecked())
                montoAdicional += precioProducto * cantidad * 0.05;

            if (binding.cbMantenimiento.isChecked())
                montoAdicional += precioProducto * cantidad * 0.10;

            if (binding.cbSeguro.isChecked())
                montoAdicional += precioProducto * cantidad * 0.07;

            double montoDescuento = 0;

            if (binding.rbTarjeta.isChecked()) {
                montoDescuento = precioProducto * cantidad * 0.10;
            } else if (binding.rbSinTarjeta.isChecked()) {
                montoDescuento = precioProducto * cantidad * 0.05;
            }

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
            finish();
        });
    }
}
