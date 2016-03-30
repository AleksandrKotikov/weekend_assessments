package com.fragilebytes.profile;

import android.app.Activity;
import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.fragilebytes.profile.handlers.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends Activity implements AdapterView.OnItemClickListener {
    ListView profilesListView;
    ImageView profilePicView;
    DatabaseHandler database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profilesListView = (ListView) findViewById(R.id.profilesListView);
        loadListViewData();
        profilesListView.setOnItemClickListener(this);

        profilePicView = (ImageView) findViewById(R.id.profilePicView);
    }

    void loadListViewData() {
        database = new DatabaseHandler(getApplicationContext());
        List<Profile> profiles = database.getAllProfiles();

        List<String> profileList = new ArrayList<String>();
        for (Profile prof : profiles) {
            profileList.add(
                    prof.getID()
                            + " " + prof.getFirstName()
                            + " " + prof.getLastName()
                            + " " + prof.getBirthDate()
                            + " " + prof.getNationality()
                            + " " + prof.getGender()
            );
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, profileList);
        profilesListView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = (String) profilesListView.getAdapter().getItem(position);

        profilePicView.setImageBitmap(database.getProfilePic(position));

        Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
    }
}