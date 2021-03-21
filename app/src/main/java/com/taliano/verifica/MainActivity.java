package com.taliano.verifica;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    String path;
    ArrayList<Persona> personas;
    LinearLayout verticalLayout;
    private static final int DEF_ID = 100;
    private static final int DEF_ID_CHK = 5000;
    Integer max_element;
    private static final int REQUEST_CODE_DETTAGLI = 268;
    private static final int REQUEST_CODE_PHONE = 791;
    Boolean showMaschi;
    Boolean showFemmine;
    Integer test2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        path= MainActivity.this.getFilesDir().getPath();
        path+="/rubrica.txt";
        verticalLayout = findViewById(R.id.vertical);
        personas = new ArrayList<Persona>();
        showMaschi = true;
        showFemmine = true;
        max_element = 0;
        test2 = 0;
        leggiFile();
        caricaLayout(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.maschi:
                alert("maschi");
                item.setChecked(!item.isChecked());
                showMaschi = item.isChecked();
                if(showMaschi && showFemmine){
                    caricaLayout(0);
                } else if (showMaschi && !showFemmine){
                    caricaLayout(1);
                } else if(!showMaschi && showFemmine) {
                    caricaLayout(2);
                } else {
                    caricaLayout(-1);
                }
                break;
            case R.id.femmine:
                alert("femmine");
                item.setChecked(!item.isChecked());
                showFemmine = item.isChecked();
                if(showMaschi && showFemmine){
                    caricaLayout(0);
                } else if (showMaschi && !showFemmine){
                    caricaLayout(1);
                } else if(!showMaschi && showFemmine) {
                    caricaLayout(2);
                } else {
                    caricaLayout(-1);
                }
//                salva();
                break;
                case R.id.ordina:
                    Collections.sort(personas);
                    if(showMaschi && showFemmine){
                        caricaLayout(0);
                    } else if (showMaschi && !showFemmine){
                        caricaLayout(1);
                    } else if(!showMaschi && showFemmine) {
                        caricaLayout(2);
                    } else {
                        caricaLayout(-1);
                    }
                alert("ordina");
                break;
            case R.id.modifica:
                Boolean finded = false;
                Integer index = -1;
                for (Integer i = DEF_ID_CHK; i <= DEF_ID_CHK+max_element; i++){
                    CheckBox chk = findViewById(i);
                    if(chk != null && chk.isChecked()){
                        finded = true;
                        index = i - DEF_ID_CHK;
                        break;
                    }
                }
                if(finded) {
                    alert("trovato " + index);
                    Persona persona = personas.get(index);
                    Intent intent = new Intent(MainActivity.this, ModifyActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("persona", persona);
                    bundle.putSerializable("index", index);
                    intent.putExtras(bundle);
                    MainActivity.this.startActivityForResult(intent, REQUEST_CODE_PHONE);
                } else {
                    alert("non trovato");
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_PHONE && data != null){
            if(resultCode == RESULT_OK){
                alert("salva");
                Bundle bundle = data.getExtras();
                Persona persona = (Persona) bundle.getSerializable("persona");
                Integer index = bundle.getInt("index", 0);
                personas.set(index, persona);
                if(showMaschi && showFemmine){
                    caricaLayout(0);
                } else if (showMaschi && !showFemmine){
                    caricaLayout(1);
                } else if(!showMaschi && showFemmine) {
                    caricaLayout(2);
                } else {
                    caricaLayout(-1);
                }

            } else {
                alert("not ok");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void leggiFile() {
        File file=new File(path);
        if(file.isFile())
        {
            try {
                FileReader reader=new FileReader(file);

                BufferedReader bufferedReader=new BufferedReader(reader); //riga per riga

                String tmp;
                while((tmp=bufferedReader.readLine())!=null)
                {
                    Persona persona =  new Persona();
                    persona.setNome(bufferedReader.readLine().toString());
                    persona.setGender(bufferedReader.readLine().toString());
                    persona.setTelefono(bufferedReader.readLine().toString());
                    persona.setImgI(bufferedReader.readLine());
                    if(persona.getImgI() != null && !persona.getImgI().equals("")) {
                        persona.setImgI(persona.getImgI().substring(0, persona.getImgI().indexOf('.')));
                        persona.setImg(this.getResources().getIdentifier(persona.getImgI(),"mipmap", this.getPackageName()));
                        if(persona.getImg() == 0){
                            persona.setImg(this.getResources().getIdentifier("user","mipmap", this.getPackageName()));
                        }
                    } else {
                        persona.setImg(this.getResources().getIdentifier("user","mipmap", this.getPackageName()));
                    }
                    personas.add(persona);
                    max_element++;
                }

                bufferedReader.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            alert("File non TROVATO!!");
    }

    private void caricaLayout(Integer typeCaricamento) {
        verticalLayout.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 80);
        layoutParams.setMargins(40, 30, 0, 0);
        LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(80,80);
        imageLayoutParams.setMargins(2,2,2,2);
        Integer index = 0;
        Integer test = 0;
        for (final Persona persona:personas) {
            Integer gen = 0;
            test += 1;
            if(persona.getGender().equals("male")){
                gen = 1;
            } else {
                gen = 2;
            }
            if(gen == typeCaricamento || typeCaricamento == 0) {
                LinearLayout horizzontal = new LinearLayout(MainActivity.this);
                horizzontal.setOrientation(LinearLayout.HORIZONTAL);

                ImageView imageView = new ImageView(MainActivity.this);
                imageView.setImageResource(persona.getImg());
                imageView.setLayoutParams(imageLayoutParams);
                horizzontal.addView(imageView);

                final TextView textViewNome = new TextView(MainActivity.this);
                textViewNome.setText(persona.getNome());
                textViewNome.setLayoutParams(layoutParams);
                //TEST PER FIXXARE LA POSIZIONE NELLA LISTA
                //SE DOVESSE CRASHARE SOSTITUIRE CON INDEX ANCHE SE CON I TEST FATTI NON CRASHHA
                textViewNome.setId(DEF_ID + test -1 );
                index++;
                persona.setP(test - 1);
                textViewNome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView txtNome = (TextView) v;
                        alert(txtNome.getId()+"");
                        Persona p = personas.get(txtNome.getId() - DEF_ID);
                        alert(p.getP() + " " + (txtNome.getId() - DEF_ID));
                        Intent intent = new Intent(MainActivity.this, DettagliActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("persona", p);
                        intent.putExtras(bundle);
                        MainActivity.this.startActivityForResult(intent, REQUEST_CODE_DETTAGLI);
                    }
                });
                horizzontal.addView(textViewNome);

                TextView textViewGender = new TextView(MainActivity.this);
                textViewGender.setText(persona.getGender());
                textViewGender.setLayoutParams(layoutParams);
                horizzontal.addView(textViewGender);

                TextView textViewPhone = new TextView(MainActivity.this);
                textViewPhone.setText(persona.getTelefono());
                textViewPhone.setLayoutParams(layoutParams);
                horizzontal.addView(textViewPhone);

                CheckBox checkBox = new CheckBox(MainActivity.this);
                checkBox.setId(DEF_ID_CHK+ test -1 );
                horizzontal.addView(checkBox);
                horizzontal.setGravity(Gravity.CENTER);
                verticalLayout.addView(horizzontal);
            }
        }
        test2 = test;
    }

    public void alert(String s)
    {
        Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
    }
}
