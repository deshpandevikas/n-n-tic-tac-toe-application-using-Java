package com.example.venkatesh.istictactoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Created by Venkatesh on 11/24/2016.
 */
public class SettingsPreference extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        ListPreference listPreference = (ListPreference) findPreference("algoPreference");

        final ListPreference listPreferencematrix = (ListPreference) findPreference("matrixPreference");

        ListPreference listPreferencesymbol = (ListPreference) findPreference("symbolPreference");

        ListPreference listPreferencefirstplayer = (ListPreference) findPreference("playfirstPreference");

        final EditTextPreference editcustommatrixsize = (EditTextPreference) findPreference("custommatrixsize");

        listPreferencefirstplayer.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                if(newValue.equals("computer"))
                {
                    Toast.makeText(getApplicationContext(),"Computer will make the first move. You play after computer's turn",Toast.LENGTH_LONG).show();
                    Intent i;

                    i = new Intent(getApplicationContext(),MainActivity.class);

                    finish();
                    startActivity(i);


                }else if(newValue.equals("player")){

                    Toast.makeText(getApplicationContext(),"You will make the first move. Computer will play after your turn",Toast.LENGTH_LONG).show();
                    Intent i;
                    i = new Intent(getApplicationContext(),MainActivity.class);

                    finish();
                    startActivity(i);

                }

                return true;
            }
        });

        listPreferencesymbol.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                if(newValue.equals("X"))
                {
                    Toast.makeText(getApplicationContext(),"Your symbol changed to X, Computer's symbol is O now",Toast.LENGTH_LONG).show();
                    Intent i;

                    i = new Intent(getApplicationContext(),MainActivity.class);

                    finish();
                    startActivity(i);
                }
                else if(newValue.equals("O"))
                {
                    Toast.makeText(getApplicationContext(),"Your symbol changed to O, Computer's symbol is X now",Toast.LENGTH_LONG).show();
                    Intent i;

                    i = new Intent(getApplicationContext(),MainActivity.class);

                    finish();
                    startActivity(i);
                }

                return true;
            }
        });



        listPreferencematrix.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String tempType = pref.getString("matrixPreference","");


                if(newValue.equals("three"))
                {
                    Toast.makeText(getApplicationContext(),"Selected grid size is 3",Toast.LENGTH_LONG).show();


                    editcustommatrixsize.setText("3");



                    Intent i;

                    i = new Intent(getApplicationContext(),MainActivity.class);

                    finish();
                    startActivity(i);
                }
                else if(newValue.equals("four"))
                {
                    Toast.makeText(getApplicationContext(),"Selected grid size is 4",Toast.LENGTH_LONG).show();
                    Intent i;

                    editcustommatrixsize.setText("4");

                    i = new Intent(getApplicationContext(),MainActivity.class);

                    finish();
                    startActivity(i);
                }
                else if(newValue.equals("five"))
                {
                    Toast.makeText(getApplicationContext(),"Selected grid size is 5",Toast.LENGTH_LONG).show();
                    Intent i;

                    editcustommatrixsize.setText("5");

                    i = new Intent(getApplicationContext(),MainActivity.class);

                    finish();
                    startActivity(i);
                }
                else if(newValue.equals("six"))
                {
                    Toast.makeText(getApplicationContext(),"Selected grid size is 6",Toast.LENGTH_LONG).show();
                    Intent i;

                    editcustommatrixsize.setText("6");

                    i = new Intent(getApplicationContext(),MainActivity.class);

                    finish();
                    startActivity(i);
                }
                else if(newValue.equals("seven"))
                {
                    Toast.makeText(getApplicationContext(),"Selected grid size is 7",Toast.LENGTH_LONG).show();
                    Intent i;

                    editcustommatrixsize.setText("7");

                    i = new Intent(getApplicationContext(),MainActivity.class);

                    finish();
                    startActivity(i);
                }

                return true;
            }
        });





        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String tempType = pref.getString("algoPreference","");


                if(newValue.equals("AD"))
                {
                    Toast.makeText(getApplicationContext(),"Selected algorithm is MiniMax Alpha Beta with CutOff",Toast.LENGTH_LONG).show();
                    Intent i;

                    i = new Intent(getApplicationContext(),MainActivity.class);

                    finish();
                    startActivity(i);
                }
                else if(newValue.equals("AN"))
                {
                    Toast.makeText(getApplicationContext(),"Selected algorithm is MiniMax Alpha Beta without CutOff",Toast.LENGTH_LONG).show();
                    Intent i;

                    i = new Intent(getApplicationContext(),MainActivity.class);

                    finish();
                    startActivity(i);
                }
                else if(newValue.equals("MD"))
                {
                    Toast.makeText(getApplicationContext(),"Selected algorithm is MiniMax with CutOff",Toast.LENGTH_LONG).show();
                    Intent i;

                    i = new Intent(getApplicationContext(),MainActivity.class);

                    finish();
                    startActivity(i);
                }
                else if(newValue.equals("MN"))
                {
                    Toast.makeText(getApplicationContext(),"Selected algorithm is MiniMax without CutOff",Toast.LENGTH_LONG).show();
                    Intent i;

                    i = new Intent(getApplicationContext(),MainActivity.class);

                    finish();
                    startActivity(i);
                }
                else if(newValue.equals("ADD"))
                {
                    Toast.makeText(getApplicationContext(),"Selected algorithm is MiniMax Alpha Beta with CutOff 3",Toast.LENGTH_LONG).show();
                    Intent i;

                    i = new Intent(getApplicationContext(),MainActivity.class);

                    finish();
                    startActivity(i);
                }

                return true;
            }
        });

        editcustommatrixsize.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {


                Toast.makeText(getApplicationContext(),"Selected grid size is "+newValue.toString(),Toast.LENGTH_LONG).show();

                editcustommatrixsize.setText(newValue.toString());
                


                Intent i;

                i = new Intent(getApplicationContext(),MainActivity.class);

                finish();
                startActivity(i);


                return false;
            }
        });





    }

    public void onBackPressed()
    {


        Intent i;
            i = new Intent(getApplicationContext(),MainActivity.class);

        finish();
        startActivity(i);

        return;
    }



}