package com.taliano.verifica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class DettagliActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettagli);

        Button buttonChiudi = findViewById(R.id.buttonChiudi);
        buttonChiudi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DettagliActivity.this, MainActivity.class);
                DettagliActivity.this.setResult(RESULT_CANCELED, intent);
                DettagliActivity.this.finish();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Persona persona = (Persona) bundle.getSerializable("persona");
            EditText editTextNome = findViewById(R.id.editTextNome);
            EditText editTextGender = findViewById(R.id.editTextGender);
            EditText editTextPhone = findViewById(R.id.editTextPhone);
            ImageView imageView = findViewById(R.id.imageView);
            if (persona != null) {
                editTextNome.setText(persona.getNome().toString());
                editTextGender.setText(persona.getGender().toString());
                editTextPhone.setText(persona.getTelefono().toString());
                imageView.setImageResource(persona.getImg());
            } else {
                alert("disco null");
            }
        } else {
            alert("bundle null");
        }
    }

    public void alert(String s)
    {
        Toast.makeText(DettagliActivity.this,s,Toast.LENGTH_LONG).show();
    }
}
