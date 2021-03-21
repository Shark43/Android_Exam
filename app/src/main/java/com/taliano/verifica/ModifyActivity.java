package com.taliano.verifica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ModifyActivity extends AppCompatActivity {

    Persona persona;
    Integer index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        Button buttonChiudi = findViewById(R.id.buttonChiudiModify);
        buttonChiudi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModifyActivity.this, MainActivity.class);
                ModifyActivity.this.setResult(RESULT_CANCELED, intent);
                ModifyActivity.this.finish();
            }
        });

        Button buttonSalva = findViewById(R.id.buttonSalvaModify);
        buttonSalva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModifyActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();

                TextView editTextNome = findViewById(R.id.textViewNome);
                EditText editTextPhone = findViewById(R.id.editTextPhoneModify);

                persona.setTelefono(editTextPhone.getText().toString());
                bundle.putSerializable("persona", persona);
                bundle.putInt("index", index);
                intent.putExtras(bundle);
                ModifyActivity.this.setResult(RESULT_OK, intent);
                ModifyActivity.this.finish();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Persona persona = (Persona) bundle.getSerializable("persona");
            Integer index = bundle.getInt("index", 0);
            ModifyActivity.this.persona = persona;
            ModifyActivity.this.index = index;
            TextView editTextNome = findViewById(R.id.textViewNome);
            EditText editTextPhone = findViewById(R.id.editTextPhoneModify);
            if (persona != null) {
                editTextNome.setText(persona.getNome().toString());
                editTextPhone.setText(persona.getTelefono().toString());
            } else {
                alert("persona null");
            }
        } else {
            alert("bundle null");
        }
    }

    public void alert(String s)
    {
        Toast.makeText(ModifyActivity.this,s,Toast.LENGTH_LONG).show();
    }
}
