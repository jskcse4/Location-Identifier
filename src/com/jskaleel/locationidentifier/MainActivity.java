package com.jskaleel.locationidentifier;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends Activity {

	private TextView txtDetails;
	private LatLng latLng;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		txtDetails	=	(TextView) findViewById(R.id.txtDetails);

		GPSTracker gpsTracker = new GPSTracker(getApplicationContext());

		if(gpsTracker.canGetLocation) {
			latLng	=	new LatLng(gpsTracker.latitude, gpsTracker.longitude);
			txtDetails.setText("Latitude-->"+gpsTracker.latitude);
			txtDetails.append("\nLongitude-->"+gpsTracker.longitude);
			getAddress(latLng.latitude, latLng.longitude);
		}else {
			txtDetails.setText("Unable to fetch location...");	
		}
	}

	private void getAddress(double lat, double lng) {
		// TODO Auto-generated method stub
		Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
		try {
			List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
			Address obj = addresses.get(0);
			String add = obj.getAddressLine(0);

			add = add + "\n" + obj.getSubAdminArea();
			add = add + "\n" + obj.getAdminArea();
			add = add + "\n" + obj.getCountryName();
			add = add + "\n" + obj.getCountryCode();
			add = add + "\n" + obj.getPostalCode();
			add = add + "\n" + obj.getLocality();

			Log.v("IGA", "Address" + add);
			txtDetails.append("\n--------------------------------------------\n");
			txtDetails.append("\n\nLocation Details-->\n"+add.toString());
			// Toast.makeText(this, "Address=>" + add,
			// Toast.LENGTH_SHORT).show();

			// TennisAppActivity.showDialog(add);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
}
