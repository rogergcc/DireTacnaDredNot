package com.educaciontacna.drednot.ui.documentodetalle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.educaciontacna.drednot.R;
import com.educaciontacna.drednot.databinding.ActivityGuardarDocumentoBinding;
import com.educaciontacna.drednot.ui.helpers.MyUtilsApp;
import com.educaciontacna.drednot.ui.model.DocumentModel;
import com.educaciontacna.drednot.ui.utils.MyConstants;

public class GuardarDocumentoActivity extends AppCompatActivity {

    ActivityGuardarDocumentoBinding binding;
    Context mcontext;
    String estadoPendienteONotificado = "";
    String tipoNotificacion = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_guardar_documento);
        binding = ActivityGuardarDocumentoBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);
        mcontext = this;
//        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(binding.toolbar);

        Intent intent = getIntent();
        DocumentModel document = intent.getParcelableExtra(MyUtilsApp.EXTRA_DOCUMENTO);

        if (document == null) {
            return;
        }

        switch (document.getDocumentoEstado()) {
            case MyConstants.TIPO_ESTADO_PENDIENTE:
                estadoPendienteONotificado = "Pendiente";
                binding.contetLayout.tvEstado.setBackgroundColor(ContextCompat.getColor(this, R.color.color4));
                binding.contetLayout.switchEstado.setChecked(false);
                break;
            case MyConstants.TIPO_ESTADO_NOTIFICADO:
                binding.contetLayout.tvEstado.setBackgroundColor(ContextCompat.getColor(this, R.color.color2));
                binding.contetLayout.switchEstado.setChecked(true);
                estadoPendienteONotificado = "Notificado";
                break;
            default:

                break;

        }

        binding.contetLayout.tvEstado.setText(estadoPendienteONotificado);
        binding.contetLayout.tvDocumento.setText(document.getDocumentName());
        binding.contetLayout.tvNombre.setText(document.getEncargadoDocumento());
        binding.contetLayout.tvfecha.setText(document.getDocumentoFecha());

        binding.contetLayout.switchEstado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.contetLayout.tvEstado.setText("Notificado");
                    binding.contetLayout.tvEstado.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.color2));

                } else {
                    binding.contetLayout.tvEstado.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.color4));
                    binding.contetLayout.tvEstado.setText("Pendiente");
                }
            }
        });
        binding.contetLayout.rGroupNotificaciones.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                RadioButton checkedRadioButton = (RadioButton) radioGroup.findViewById(checkedId);
                if (checkedRadioButton != null)
                    if (checkedRadioButton.getTag() != null) {
                        int CorrectAnswer = Integer.parseInt(
                                checkedRadioButton.getTag().toString());

                        document.setRadioTipoNotificado(CorrectAnswer);
                        tipoNotificacion = checkedRadioButton.getText().toString();
                    }


            }
        });

        tipoNotificacion = binding.contetLayout.rbNotificacionActa.getText().toString();

        binding.fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String output = String.format(
                        "Documento=>[%s, %s, %s, %s,%s]",
                        document.getId(),
                        document.getDocumentName(),
                        document.getEncargadoDocumento(),
                        estadoPendienteONotificado,
                        tipoNotificacion

                );

                Toast.makeText(GuardarDocumentoActivity.this, output, Toast.LENGTH_LONG).show();

                //Snackbar.make(view, "GUARDAR", Snackbar.LENGTH_LONG).setAction("ok" + output, null).show();
            }
        });

    }
}