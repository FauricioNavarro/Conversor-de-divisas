package com.example.fauricio.convertidor_divisas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import org.json.*;
import com.android.volley.toolbox.*;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;

public class MainActivity extends AppCompatActivity{
    private TextView mTxtDisplay;
    private RadioButton rdbtn_colones,rdbtn_dolares;
    private EditText editText;
    public static final String url = "https://openexchangerates.org/api/latest.json?app_id=6de8f1ac1d0341fb87e7058f7fcddb47";
    public static String TIPO_CAMBIO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTxtDisplay = (TextView) findViewById(R.id.texto1);
        rdbtn_colones = (RadioButton) findViewById(R.id.rdbtn_colones);
        rdbtn_dolares = (RadioButton) findViewById(R.id.rdbtn_dolares);
        editText = (EditText) findViewById(R.id.valor);
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject n = new JSONObject(String.valueOf(response.getJSONObject("rates")));
                            TIPO_CAMBIO = n.getString("CRC");
                            //mTxtDisplay.setText(response.getString("rates"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mTxtDisplay.setText("Error de conexion");
                    }
                }
                );
        queue.add(jsonRequest);
    }


    public void onRadioButtonClicked(View view){
        boolean estado = ((RadioButton) view).isChecked();
        String valor = "";
        valor = editText.getText().toString();
        double cambio = Double.parseDouble(TIPO_CAMBIO);
        if(!valor.equals("")){
            switch (view.getId()){
                case R.id.rdbtn_colones:{
                    if(estado){
                        double resultado = Double.parseDouble(valor) * (1/cambio);
                        String m = String.format("%.4f",resultado);
                        mTxtDisplay.setText(m);
                        rdbtn_dolares.setChecked(false);
                    }
                }break;
                case R.id.rdbtn_dolares:{
                    if(estado){
                        double resultado = Double.parseDouble(valor) * cambio;
                        String m = String.format("%.4f",resultado);
                        mTxtDisplay.setText(m);
                        rdbtn_colones.setChecked(false);
                    }
                }break;
            }
        }
    }
}
