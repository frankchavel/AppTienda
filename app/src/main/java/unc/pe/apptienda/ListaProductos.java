package unc.pe.apptienda;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
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
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cargarProductos();
        mostrarEnListView();
        detectarClicks();
    }

    private void cargarProductos() {
        listaProductos = new ArrayList<>();
        listaProductos.add(new Producto("Lavadora", 1100.00, R.drawable.lavadora));
        listaProductos.add(new Producto("Refrigeradora", 1500.00, R.drawable.refrigeradora));
        listaProductos.add(new Producto("Cocina", 850.00, R.drawable.cocina));
        listaProductos.add(new Producto("Rápiducha", 250.00, R.drawable.rapiducha));
    }

    private void mostrarEnListView() {
        ArrayList<String> nombres = new ArrayList<>();
        for (Producto p : listaProductos) {
            nombres.add(p.getNombre());
        }

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                nombres);

        binding.lvListaProductos.setAdapter(adapter);
    }

    private void detectarClicks() {
        binding.lvListaProductos.setOnItemClickListener((adapterView, view, position, id) -> {
            Producto p = listaProductos.get(position);

            Intent intent = new Intent(ListaProductos.this, DetalleProducto.class);
            intent.putExtra("nombre", p.getNombre());
            intent.putExtra("precio", p.getPrecio());
            intent.putExtra("imagen", p.getImagen());
            startActivity(intent);
        });
    }
}
