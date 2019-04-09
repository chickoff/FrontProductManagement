package ru.a5x5retail.frontproductmanagement.settings;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

import ru.a5x5retail.frontproductmanagement.ProdManApp;
import ru.a5x5retail.frontproductmanagement.R;
import ru.a5x5retail.frontproductmanagement.base.BaseAppCompatActivity;
import ru.a5x5retail.frontproductmanagement.db.mssql.MsSqlConnection;
import ru.a5x5retail.frontproductmanagement.db.query.TestConnectionQuery;
import ru.a5x5retail.frontproductmanagement.diag.FullDiagActivity;

public class SettingsActivity extends BaseAppCompatActivity

implements View.OnClickListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
        loadSettings();
    }

    Button
            a_settings_button_1,
            a_settings_button_2;

    EditText
            a_settings_editText_1,
            a_settings_editText_2,
            a_settings_editText_3,
            a_settings_editText_4;

    CheckBox
            a_settings_checkBox_1;

    @SuppressLint("RestrictedApi")
    private void init(){
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        a_settings_button_1 = findViewById(R.id.a_settings_button_1);
        a_settings_button_1.setOnClickListener(this);

        a_settings_button_2 = findViewById(R.id.a_settings_button_2);
        a_settings_button_2.setOnClickListener(this);

        a_settings_editText_1 = findViewById(R.id.a_settings_editText_1);
        a_settings_editText_2 = findViewById(R.id.a_settings_editText_2);
        a_settings_editText_3 = findViewById(R.id.a_settings_editText_3);
        a_settings_editText_4 = findViewById(R.id.a_settings_editText_4);

        a_settings_checkBox_1 = findViewById(R.id.a_settings_checkBox_1);



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.a_settings_button_1:
                getDialogFragment();
                break;
            case R.id.a_settings_button_2:
                fullDiagActivity();
                break;
        }
    }

    private void getDialogFragment(){

        PwdDialogFragment dialog = new PwdDialogFragment();
        dialog.show(getSupportFragmentManager(),"dsfds");
    }

    private void loadSettings(){
        SharedPreferences prefs = getSharedPreferences("PswdPref", MODE_PRIVATE);
        a_settings_editText_1.setText( prefs.getString("ip",""));
        a_settings_editText_2.setText( prefs.getString("upd_svr_port",""));
        a_settings_editText_3.setText( prefs.getString("login",""));
        a_settings_editText_4.setText( prefs.getString("password",""));
        a_settings_checkBox_1.setChecked(prefs.getBoolean("is_debug",false));
    }

    private void saveSettings(){
        SharedPreferences prefs = getSharedPreferences("PswdPref", MODE_PRIVATE);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putString("ip",a_settings_editText_1.getText().toString());
        prefEdit.putString("upd_svr_port", a_settings_editText_2.getText().toString());
        prefEdit.putString("login", a_settings_editText_3.getText().toString());
        prefEdit.putString("password",a_settings_editText_4.getText().toString());
        prefEdit.putBoolean("is_debug",a_settings_checkBox_1.isChecked());
        prefEdit.commit();
    }


    private void fullDiagActivity() {

        Intent intent = new Intent(this, FullDiagActivity.class);
        startActivity(intent);

    }

    /*private void textConnection() {
        try {
            MsSqlConnection con = new MsSqlConnection();
            TestConnectionQuery q = new TestConnectionQuery(con.getConnection());
            con.CallQuery(q);
            if (q.isSuccessfull()){
                ProdManApp.Alerts.MakeToast("Соединение установлено.", Toast.LENGTH_SHORT);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home :
                finish();
                return true;
                default: return true;
        }
    }

    public void OnSetDialogResult() {
            saveSettings();
            finish();
    }
}
