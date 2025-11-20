package unc.pe.apptienda;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class DetalleProducto extends AppCompatActivity {

    ImageView productImage, giftImage;
    TextView productName, productPrice, tvCantidad, tvRegalo;

    CheckBox cbInstalacion, cbMantenimiento, cbSeguro;
    RadioButton rbTarjeta, rbSinTarjeta;

    Button btnObsequio;

    // Nombres de los regalos
    String[] nombresRegalos = {
            "Set de tazas x 6 und.",
            "Individuales x 12 und.",
            "Bowls x 6 und.",
            "Set de vasos x 6 und.",
            "Cucharitas de té x 12 und."
    };

    // Imágenes de los regalos (asegúrate que existen en drawable)
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
        setContentView(R.layout.activity_detalle_producto);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        // Enlazar vistas del producto
        productImage = findViewById(R.id.product_image);
        productName  = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_price);
        tvCantidad   = findViewById(R.id.etCantidad);

        cbInstalacion  = findViewById(R.id.cbInstalacion);
        cbMantenimiento = findViewById(R.id.cbMantenimiento);
        cbSeguro       = findViewById(R.id.cbSeguro);

        rbTarjeta    = findViewById(R.id.rbTarjeta);
        rbSinTarjeta = findViewById(R.id.rbSinTarjeta);

        // Vistas de obsequio
        giftImage = findViewById(R.id.ivRegalo);
        tvRegalo = findViewById(R.id.tvRegalo);
        btnObsequio = findViewById(R.id.btnObsequio);

        // Recibir datos desde la lista
        String nombre = getIntent().getStringExtra("nombre");
        double precio = getIntent().getDoubleExtra("precio", 0.0);
        int imagen = getIntent().getIntExtra("imagen", 0);

        // Mostrar datos del producto
        productName.setText(nombre);
        productPrice.setText("S/ " + precio);
        productImage.setImageResource(imagen);

        // Acción del botón Obsequio
        btnObsequio.setOnClickListener(v -> generarObsequio());
    }

    private void generarObsequio() {
        Random random = new Random();
        int index = random.nextInt(nombresRegalos.length);

        giftImage.setImageResource(imagenesRegalos[index]);
        tvRegalo.setText(nombresRegalos[index]);
    }
}
