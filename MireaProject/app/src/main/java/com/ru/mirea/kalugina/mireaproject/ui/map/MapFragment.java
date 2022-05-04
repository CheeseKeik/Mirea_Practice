package com.ru.mirea.kalugina.mireaproject.ui.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ru.mirea.kalugina.mireaproject.R;
import com.ru.mirea.kalugina.mireaproject.databinding.FragmentMapBinding;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.GeoObject;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.RequestPoint;
import com.yandex.mapkit.RequestPointType;
import com.yandex.mapkit.directions.DirectionsFactory;
import com.yandex.mapkit.directions.driving.DrivingOptions;
import com.yandex.mapkit.directions.driving.DrivingRoute;
import com.yandex.mapkit.directions.driving.DrivingRouter;
import com.yandex.mapkit.directions.driving.DrivingSession;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.GeoObjectTapEvent;
import com.yandex.mapkit.layers.GeoObjectTapListener;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.GeoObjectSelectionMetadata;
import com.yandex.mapkit.map.InputListener;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.Error;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.runtime.network.NetworkError;
import com.yandex.runtime.network.RemoteError;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements DrivingSession.DrivingRouteListener, GeoObjectTapListener, InputListener {
    private FragmentMapBinding binding;
    private final String MAPKIT_API_KEY = "6a3de4d4-b4a5-467c-bf1b-4b7601ea5855";

    private final Point ROUTE_START_LOCATION = new Point(55.670005, 37.479894);
    private final Point ROUTE_END_LOCATION = new Point(55.794229, 37.700772);
    private final Point SCREEN_CENTER = new Point(
            (ROUTE_START_LOCATION.getLatitude() + ROUTE_END_LOCATION.getLatitude()) / 2,
            (ROUTE_START_LOCATION.getLongitude() + ROUTE_END_LOCATION.getLongitude()) /
                    2);

    private MapView mapView;
    private MapObjectCollection mapObjects;
    private DrivingRouter drivingRouter;
    private DrivingSession drivingSession;
    private int[] colors = {0xFFFF0000, 0xFF00FF00, 0x00FFBBBB, 0xFF0000FF};
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 1);
        }

        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(requireContext());
        DirectionsFactory.initialize(requireContext());

        binding = FragmentMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mapView = binding.mapview;

        mapView.getMap().move(new CameraPosition(
                SCREEN_CENTER, 10, 0, 0));
        // Ининциализируем объект для создания маршрута водителя
        drivingRouter = DirectionsFactory.getInstance().createDrivingRouter();
        mapObjects = mapView.getMap().getMapObjects().addCollection();
        submitRequest();

        mapView.getMap().addTapListener(this);
        mapView.getMap().addInputListener(this);
        setMarks();

        return root;
    }

    private void submitRequest() {
        DrivingOptions options = new DrivingOptions();
        // Кол-во альтернативных путей
        options.setAlternativeCount(3);
        ArrayList<RequestPoint> requestPoints = new ArrayList<>();
        // Устанавливаем точки маршрута
        requestPoints.add(new RequestPoint(
                ROUTE_START_LOCATION,
                RequestPointType.WAYPOINT,
                null));
        requestPoints.add(new RequestPoint(
                ROUTE_END_LOCATION,
                RequestPointType.WAYPOINT,
                null));
        // Делаем запрос к серверу
        drivingSession = drivingRouter.requestRoutes(requestPoints, options, this);
    }

    @Override
    public void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
    @Override
    public void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    @Override
    public void onDrivingRoutes(@NonNull List<DrivingRoute> list) {
        int color;
        for (int i = 0; i < list.size(); i++) {
            // настроиваем цвета для каждого маршрута
            color = colors[i];
            // добавляем маршрут на карту
            mapObjects.addPolyline(list.get(i).getGeometry()).setStrokeColor(color);
        }
    }
    @Override
    public void onDrivingRoutesError(@NonNull Error error) {
        String errorMessage = getString(R.string.unknown_error_message);
        if (error instanceof RemoteError) {
            errorMessage = getString(R.string.remote_error_message);
        } else if (error instanceof NetworkError) {
            errorMessage = getString(R.string.network_error_message);
        }
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onObjectTap(@NonNull GeoObjectTapEvent geoObjectTapEvent) {
        final GeoObjectSelectionMetadata selectionMetadata = geoObjectTapEvent
                .getGeoObject()
                .getMetadataContainer()
                .getItem(GeoObjectSelectionMetadata.class);

        String text = geoObjectTapEvent.getGeoObject().getName();
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show();

        if (selectionMetadata != null) {
            mapView.getMap().selectGeoObject(selectionMetadata.getId(), selectionMetadata.getLayerId());
        }

        return selectionMetadata != null;
    }

    @Override
    public void onMapTap(@NonNull Map map, @NonNull Point point) {
        mapView.getMap().deselectGeoObject();
    }

    @Override
    public void onMapLongTap(@NonNull Map map, @NonNull Point point) {

    }

    private void setMarks(){
        mapObjects = mapView.getMap().getMapObjects().addCollection();
        placeNewMark("Вернадка 78", "Основное, самое крутое здание", "г. Москва, Проспект Вернадского, д. 78", new Point(55.669945, 37.479492));
        placeNewMark("Вернадка 86", "Тут чё-то химичат", "г. Москва, Проспект Вернадского, д. 86", new Point(55.661445, 37.477049));
        placeNewMark("Стромынка", "Я обычно туть", "г. Москва, ул. Стромынка, д.20", new Point(55.794259, 37.701448));
    }

    public void placeNewMark(String name, String description, String address, Point place){
        PlacemarkMapObject mark = mapObjects.addPlacemark(place, ImageProvider.fromResource(requireContext(), R.drawable.mark));
        mark.setUserData(new UniversityInfo(name, description, address));
        mark.addTapListener((mapObject, point) -> {
            Toast.makeText(requireContext(), (mapObject.getUserData().toString()), Toast.LENGTH_SHORT).show();
            return true;
        });
    }
}