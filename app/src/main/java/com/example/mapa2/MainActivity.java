package com.example.mapa2;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;

public class MainActivity extends AppCompatActivity {

    private MapView map = null;
    private Marker markerSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Configuration.getInstance().setUserAgentValue("Mapa/bastian@gmail.com");

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.WIKIMEDIA);
        map.setMultiTouchControls(true); //

        GeoPoint startPoint = new GeoPoint(-33.498895, -70.616617);
        GeoPoint punto2 = new GeoPoint(-33.498715, -70.616126);
        GeoPoint punto3 = new GeoPoint(-33.498543, -70.615711);
        map.getController().setZoom(20.0);
        Toast.makeText(this, "Tengo el codigo de la discordia", Toast.LENGTH_SHORT).show();

        map.getController().setCenter(startPoint);

        Marker marker = new Marker(map);
        marker.setPosition(startPoint);
        marker.setTitle("Hola");
        marker.setSnippet("Estas aqui");

        Marker marker2 = new Marker(map);
        marker2.setPosition(punto2);
        marker2.setIcon(
                ContextCompat.getDrawable(this, R.mipmap.ic_launcher_repartidor_round)
        );
        marker2.setTitle("Hola");
        marker2.setSnippet("Repartidor cerca");

        Marker marker3 = new Marker(map);
        marker3.setPosition(punto3);
        marker3.setIcon(
                ContextCompat.getDrawable(this, R.mipmap.ic_launcher_poli_round)
        );
        marker3.setTitle("Hola");
        marker3.setSnippet("Policia cerca");

        map.getOverlays().add(marker);
        map.getOverlays().add(marker2);
        map.getOverlays().add(marker3);
        map.invalidate();

        MapEventsReceiver puntoSelecionado = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                double lat = p.getLatitude();
                double lon = p.getLongitude();

                Log.d("MAPA", "Latitud " + lat + " Longitud" + lon);

                if (markerSeleccionado != null ) {
                    map.getOverlays().remove(markerSeleccionado);
                }

                markerSeleccionado = new Marker(map);
                markerSeleccionado.setPosition(p);
                markerSeleccionado.setTitle("ACA");
                markerSeleccionado.setAnchor(
                        Marker.ANCHOR_CENTER,
                        Marker.ANCHOR_BOTTOM
                );
                map.getOverlays().add(markerSeleccionado);
                map.invalidate();

                return true;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        };

        MapEventsOverlay eventsOverlay = new MapEventsOverlay(puntoSelecionado);
        map.getOverlays().add(eventsOverlay);

    }
}