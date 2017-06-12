package ru.ikaso63.android.ikaso.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

/*import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;*/

import ru.ikaso63.android.ikaso.R;
import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.MapView;
import ru.yandex.yandexmapkit.OverlayManager;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.overlay.balloon.BalloonItem;
import ru.yandex.yandexmapkit.utils.GeoPoint;

import static android.view.View.GONE;

import android.Manifest;

/**
 * Created by home on 04.12.2016.
 */

public class ContactsFragment extends android.support.v4.app.Fragment{

    private static View view;
    private TextView mStatePhone;
    private TextView mHotPhone;
    private BalloonItem mBalloonItemIkaso = null;
    private GeoPoint mGeoPointIkaso = new GeoPoint(53.217284, 50.157487);
    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1234;
            /*new GeoPoint(53.217251, 50.157600);*/
    /*GoogleMap googleMp;*/

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (view == null){
            view = inflater.inflate(R.layout.activity_contacts, null);
        }

        mStatePhone = (TextView) view.findViewById(R.id.state_phone);
        mHotPhone = (TextView) view.findViewById(R.id.hot_phone);
        final MapView mMapView = (MapView) view.findViewById(R.id.map);

        // Получаем MapController
        MapController mMapController = mMapView.getMapController();

        // Перемещаем карту на заданные координаты
        mMapController.setPositionNoAnimationTo(mGeoPointIkaso);
        mMapController.setZoomCurrent(16);

        OverlayManager mOverlayManager = mMapController.getOverlayManager();
        Overlay mOverlay = new Overlay(mMapController);
        OverlayItem mOverlayItem = new OverlayItem(mGeoPointIkaso, getResources().getDrawable(R.drawable.ic_contact));
        mBalloonItemIkaso = new BalloonItem(getActivity(), mGeoPointIkaso);
        mBalloonItemIkaso.setText("ГКУ СО ИКАСО Ерошевского 3 литера С3, код домофона 17");
        mOverlay.addOverlayItem(mOverlayItem);
        mOverlayItem.setBalloonItem(mBalloonItemIkaso);
        mOverlayManager.addOverlay(mOverlay);
        mStatePhone.setOnClickListener(onClickListener);
        mHotPhone.setOnClickListener(onClickListener);

/*        MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager()
                .findFragmentById(R.id.viewMap);
        mapFragment.getMapAsync(this);*/
        return view;
    }

        View.OnClickListener onClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.state_phone:
                    callPhone(mStatePhone.getText().toString());
                    break;
                case R.id.hot_phone:
                    callPhone(mHotPhone.getText().toString());
                    break;
            }
        }
    };


    public void callPhone(String phone) {

        Intent mIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "88463344700"));
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            try {

                startActivity(mIntent);
            } catch (SecurityException e) {

                startActivity(mIntent);
                e.printStackTrace();
            }
 /*        try {
            mIntent.setPackage("com.android.phone");
            startActivity(mIntent);
        } catch(Exception e) {
            mIntent.setPackage("com.android.server.telecom");
            startActivity(mIntent);
        }*/
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the phone call

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    //Реализация Google map

/*    @Override
    public void onMapReady(GoogleMap map) {
        LatLng gkuIkaso = new LatLng(53.217251, 50.157600);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(gkuIkaso, 17));

        map.addMarker(new MarkerOptions()
                .title("ГКУ СО ИКАСО")
                .snippet("The most populous city in Australia.")
                .position(gkuIkaso));
    }*/

/*    private GoogleMap googleMap;
    MapFragment mapFragment;

*//*    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_contacts, null);
        mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.viewMap);
        mapFragment.getMapAsync(this);
        return view;
    }*//*

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.viewMap);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng ikasoSamara = new LatLng(53.217251, 50.157600);
        Marker markerIkaso = googleMap.addMarker(new MarkerOptions()
                .position(ikasoSamara)
                .title("ГКУ СО ИКАСО"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(ikasoSamara));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(17));

    }*/

}
