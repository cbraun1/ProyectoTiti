package com.example.proyectotiti;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class download extends AppCompatActivity {

    public void onCheckboxClicked(view view) {
        // Is the view now checked?
        boolean checked = ((checkBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBox_family1:
                if (checked)
                // download
                else
                // don't download
                break;
            case R.id.checkBox_family2:
                if (checked)
                // download
                else
                // don't download
                break;
            case R.id.checkBox_family3:
                if (checked)
                // download
                else
                // don't download
                break;
            case R.id.checkBox_family4:
                if (checked)
                // download
                else
                // don't download
                break;
            case R.id.checkBox_family5:
                if (checked)
                // download
                else
                // don't download
                break;
            case R.id.checkBox_family6:
                if (checked)
                // download
                else
                // don't download
                break;
            case R.id.checkBox_family7:
                if (checked)
                // download
                else
                // don't download
                break;
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
    }
}
