package com.fragilebytes.profile;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.cocosw.bottomsheet.BottomSheet;
import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;
import com.codetroopers.betterpickers.datepicker.DatePickerDialogFragment;
import com.fragilebytes.profile.handlers.DatabaseHandler;


public class CoreActivity extends AppCompatActivity implements  DatePickerDialogFragment.DatePickerDialogHandler {
    TextView firstName;
    TextView lastName;
    TextView birthDate;
    Spinner nationalitySpinner;

    RadioGroup radioSexGroup;
    RadioButton radioSexButton;

    Bitmap profilePic;

    DatabaseHandler dataBaseHandler;
    private static final int REQUEST_CODE = 1;
    private ImageView imageView;

    DatabaseHandler database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core);

        dataBaseHandler = new DatabaseHandler(getApplicationContext());

        firstName = (TextView) findViewById(R.id.firstName);
        lastName = (TextView) findViewById(R.id.lastName);

        birthDate = (Button)findViewById(R.id.birthDate);

        imageView = (ImageView) findViewById(R.id.profilePic);
        imageView.setImageResource(android.R.drawable.ic_menu_camera);

        nationalitySpinner=(Spinner)findViewById(R.id.nationalitySpinner);

        radioSexGroup = (RadioGroup)findViewById(R.id.radioSexGroup);
        //==========================================================================================
       /*/ Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_share);
        //setSupportActionBar(toolbar);


        toolbar.setNavigationIcon(android.R.drawable.ic_menu_share);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CoreActivity.this, "Navigation Clicked", Toast.LENGTH_LONG).show();
            }
        });/*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_cancel) {
            Toast.makeText(CoreActivity.this, "CANCEL", Toast.LENGTH_LONG).show();
            clearFields();
            return true;
        }
        else if (id == R.id.action_save) {
            int selectedId = radioSexGroup.getCheckedRadioButtonId();
            radioSexButton = (RadioButton) findViewById(selectedId);
            addProfile(radioSexButton.getText().toString());
            Toast.makeText(CoreActivity.this, "Profile added", Toast.LENGTH_LONG).show();
            clearFields();
            return true;
        }
        else if (id == R.id.action_profiles) {
            // Toast.makeText(this,"Star Clicked",Toast.LENGTH_LONG).show();
            Intent i=new Intent(CoreActivity.this,ProfileActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void pickBirthDate(View view) {
        DatePickerBuilder dpb = new DatePickerBuilder()
                .setFragmentManager(getSupportFragmentManager())
                .setStyleResId(R.style.BetterPickersDialogFragment)
                .setYearOptional(true);
        dpb.show();
    }

    @Override
    public void onDialogDateSet(int reference, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;
        birthDate.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
    }

    public void showBottomSheet(final View view){
        new BottomSheet.Builder(this)
                .title(R.string.bottomList).grid()
                .sheet(R.menu.bottom_menu_core)
                .listener(new DialogInterface
                        .OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case R.id.profilesBottomScreen:
                                showProfiles();
                                break;
                            case R.id.addBottomScreen:
                                int selectedId = radioSexGroup.getCheckedRadioButtonId();
                                radioSexButton = (RadioButton) findViewById(selectedId);
                                addProfile(radioSexButton.getText().toString());
                                Toast.makeText(CoreActivity.this, "New profile added", Toast.LENGTH_LONG).show();
                                break;
                            case R.id.cancelBottomScreen:
                                Toast.makeText(CoreActivity.this, "CANCEL", Toast.LENGTH_LONG).show();
                                clearFields();
                                break;
                        }
                    }
                }).show();
    }

    public void addProfile(String gender) {
        if ((firstName.getText().toString().trim().length() > 0) &&
                (lastName.getText().toString().trim().length() > 0)) {
            database = new DatabaseHandler(this);
            database.addProfile(new Profile(
                    firstName.getText().toString(),
                    lastName.getText().toString(),
                    birthDate.getText().toString(),
                    nationalitySpinner.getSelectedItem().toString(),
                    gender,
                    profilePic
                    ));
            clearFields();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Please make sure, that you entered all fields", Toast.LENGTH_SHORT).show();
        }
    }

    void showProfiles(){
        Intent intent = new Intent(CoreActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void pickImage(View View) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                profilePic = (Bitmap) extras.get("data");
                imageView.setImageBitmap(profilePic);
            }
        }

    void clearFields(){
        firstName.setText("");
        lastName.setText("");
        birthDate.setText(R.string.birthDate);
        radioSexGroup.clearCheck();
        imageView.setImageResource(android.R.drawable.ic_menu_camera);
    }
}
