package com.hafidzmrizky.dataq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.hafidzmrizky.dataq.API.Request;
import com.hafidzmrizky.dataq.API.RequestHandler;
import com.hafidzmrizky.dataq.Model.Siswa;

import java.net.MalformedURLException;
import java.util.HashMap;

public class EditSiswa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_siswa);

        Intent intent = getIntent();
        String nis = intent.getStringExtra("nis");
        String nama = intent.getStringExtra("nama");
        String alamat = intent.getStringExtra("alamat");
        String kota = intent.getStringExtra("kota");
        String gender = intent.getStringExtra("gender");
        String umur = intent.getStringExtra("umur");

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            Intent intent1 = new Intent(EditSiswa.this, LihatSiswa.class);
            startActivity(intent1);
        });

        Button btnUpdate = findViewById(R.id.buttonUpdate);
        Button btnDelete = findViewById(R.id.btnDelete);

        EditText editTextNis, editTextNama, editTextAlamat, editTextUmur;
        editTextNis = findViewById(R.id.editTextNis2);
        editTextNama = findViewById(R.id.editTextNama2);
        editTextAlamat = findViewById(R.id.editTextAlamat2);
        editTextUmur = findViewById(R.id.editTextUmur2);

        RadioGroup jenisKelamin = findViewById(R.id.radioGroupGender2);
        Spinner spinner = findViewById(R.id.spinnerKota2);

        editTextNis.setText(nis);
        editTextNama.setText(nama);
        editTextAlamat.setText(alamat);
        editTextUmur.setText(umur);

        if (gender.equals("Laki-laki")) {
            jenisKelamin.check(R.id.radioButtonLaki2);
        } else {
            jenisKelamin.check(R.id.radioButtonPerempuan2);
        }

        spinner.setSelection(((ArrayAdapter)spinner.getAdapter()).getPosition(kota));

        RequestHandler requestHandler = new RequestHandler();

        btnUpdate.setOnClickListener(v -> {
            String nisSiswa = editTextNis.getText().toString();
            String namaSiswa = editTextNama.getText().toString();
            String alamatSiswa = editTextAlamat.getText().toString();
            String umurSiswa = editTextUmur.getText().toString();
            String kotaSiswa = spinner.getSelectedItem().toString();
            String genderSiswa = ((RadioButton) findViewById(jenisKelamin.getCheckedRadioButtonId())).getText().toString();

            Siswa siswa = new Siswa();
            siswa.setNis(nisSiswa);
            siswa.setNama(namaSiswa);
            siswa.setAlamat(alamatSiswa);
            siswa.setKota(kotaSiswa);
            siswa.setJenisKelamin(genderSiswa);
            siswa.setUmur(umurSiswa);

            try {
                requestHandler.updateSiswa(siswa);

                Toast.makeText(this, "Sukses Mengubah Data", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(EditSiswa.this, LihatSiswa.class);
                startActivity(intent1);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        });

        Request request = new Request();

        btnDelete.setOnClickListener(v -> {
            String nisSiswa = editTextNis.getText().toString();
            HashMap<String, String> params = new HashMap<>();
            params.put("nis", nisSiswa);

            request.sendPostRequest("http://10.0.2.2/deleteSiswa.php", params);

            Toast.makeText(this, "Sukses Menghapus Data", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(EditSiswa.this, LihatSiswa.class);
            startActivity(intent1);
        });

        }

}