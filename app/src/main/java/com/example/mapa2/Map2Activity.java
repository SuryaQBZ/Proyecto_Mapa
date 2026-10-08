package com.example.mapa2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map2Activity extends AppCompatActivity
        implements OnMapReadyCallback {

    private GoogleMap map;
    private Marker markerSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_map2);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        // Obtener el fragmento del mapa
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);

        // Solicitar que Google Maps prepare el mapa
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

        LatLng startPoint =
                new LatLng(-33.498895, -70.616617);

        LatLng punto2 =
                new LatLng(-33.498720, -70.616130);

        LatLng punto3 =
                new LatLng(-33.498561, -70.615666);

        map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                        startPoint,
                        20f
                )
        );



        Bitmap bitmap = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.cuervo
        );

        Bitmap bitmap1 = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.cerdo
        );

        Bitmap bitmapRedimensionado = Bitmap.createScaledBitmap(
                bitmap,
                100,  // ancho en píxeles
                100,  // alto en píxeles
                false
        );

        Bitmap bitmapRedimensionado1 = Bitmap.createScaledBitmap(
                bitmap1,
                100,  // ancho en píxeles
                100,  // alto en píxeles
                false
        );

        // Marcador inicial
        map.addMarker(
                new MarkerOptions()
                        .position(startPoint)
                        .title("Hola")
                        .snippet("Estás aquí")
        );

        // Marcador cerdo
        map.addMarker(
                new MarkerOptions()
                        .position(punto2)
                        .icon(BitmapDescriptorFactory.fromBitmap(bitmapRedimensionado))
                        .title("Hola")
                        .snippet("Repartidor cerca")
        );

        // Marcador cuervo
        map.addMarker(
                new MarkerOptions()
                        .position(punto3)
                        .icon(BitmapDescriptorFactory.fromBitmap(bitmapRedimensionado1))
                        .title("Hola")
                        .snippet("Policía cerca")
        );

        // Crear marcador al hacer clic en el mapa
        map.setOnMapClickListener(latLng -> {

            if (markerSeleccionado != null) {
                markerSeleccionado.remove();
            }

            markerSeleccionado = map.addMarker(
                    new MarkerOptions()
                            .position(latLng)
                            .title("ACA")
            );

            if (markerSeleccionado != null) {
                markerSeleccionado.setAnchor(0.5f, 1.0f);
            }
        });
    }
}