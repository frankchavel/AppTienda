package unc.pe.apptienda;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import unc.pe.apptienda.databinding.ActivityListaProductosBinding;

public class ListaProductos extends AppCompatActivity {
    ActivityListaProductosBinding binding;
    ArrayList<Producto> listaProductos;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityListaProductosBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_lista_productos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cargarProductos();

    }
    private void cargarProductos() {
        listaProductos = new ArrayList<>();
        listaProductos.add(new Producto("Lavadora", 1100.00, R.drawable.lavadora));
        listaProductos.add(new Producto("Refrigeradora", 1500.00, R.drawable.refrigeradora));
        listaProductos.add(new Producto("Cocina", 850.00, R.drawable.cocina));
        listaProductos.add(new Producto("Rápiducha", 250.00, R.drawable.rapiducha));
    }


}