package com.example.proyectotiti;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class continuePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_page);
    }
}

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_family1:
                if (checked)
                // download
            else
                // don't download
                break;
            case R.id.checkbox_family2:
                if (checked)
                // download
            else
                // don't download
                break;
            case R.id.checkbox_family3:
                if (checked)
                // download
            else
                // don't download
                break;
            case R.id.checkbox_family4:
                if (checked)
                // download
            else
                // don't download
                break;
            case R.id.checkbox_family5:
                if (checked)
                // download
            else
                // don't download
                break;
        }
    }
