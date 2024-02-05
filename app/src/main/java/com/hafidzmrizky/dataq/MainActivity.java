package com.hafidzmrizky.dataq;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.hafidzmrizky.dataq.API.RequestHandler;
import com.hafidzmrizky.dataq.Model.Siswa;
import com.hafidzmrizky.dataq.Service.SiswaService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Spinner spinner = findViewById(R.id.spinnerKota);
        EditText nis, nama, alamat, umur;
        RadioGroup jenisKelamin = findViewById(R.id.jenisKelaminG);
        nis = findViewById(R.id.editTextNis);
        nama = findViewById(R.id.editTextNama);
        alamat = findViewById(R.id.address);
        umur = findViewById(R.id.umur);

        Button simpan = findViewById(R.id.simpan);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.kota, R.layout.spinnercolor);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        simpan.setOnClickListener(v -> {
            String nisSiswa = nis.getText().toString();
            String namaSiswa = nama.getText().toString();
            String alamatSiswa = alamat.getText().toString();
            String umurSiswa = umur.getText().toString();
            String kotaSiswa = spinner.getSelectedItem().toString();
            String jenisKelaminSiswa = jenisKelamin.toString();
            Toast.makeText(this, nisSiswa, Toast.LENGTH_SHORT).show();
            if (nisSiswa.isEmpty()) {
                nis.setError("NIS tidak boleh kosong");
                nis.requestFocus();
                return;
            }

            if (namaSiswa.isEmpty()) {
                nama.setError("Nama tidak boleh kosong");
                nama.requestFocus();
                return;
            }

            if (alamatSiswa.isEmpty()) {
                alamat.setError("Alamat tidak boleh kosong");
                alamat.requestFocus();
                return;
            }

            if (umurSiswa.isEmpty()) {
                umur.setError("Umur tidak boleh kosong");
                umur.requestFocus();
                return;
            }

            if (kotaSiswa.equals("Pilih Kota")) {
                spinner.requestFocus();
                return;
            }

            if (jenisKelaminSiswa.isEmpty()) {
                jenisKelamin.requestFocus();
                return;
            }

            int getChoice = jenisKelamin.getCheckedRadioButtonId();
            RadioButton choosen = findViewById(getChoice);
            String jk_user = choosen.getText().toString();

            Siswa siswa = new Siswa();
            siswa.setNis(nis.getText().toString());
            siswa.setNama(nama.getText().toString());
            siswa.setAlamat(alamat.getText().toString());
            siswa.setKota(spinner.getSelectedItem().toString());
            siswa.setUmur(umur.getText().toString());
            siswa.setJenisKelamin(jk_user);
            siswa.setUmur(umur.getText().toString());

            SiswaService siswaService = SiswaService.getInstance();
            siswaService.addSiswa(siswa);

            RequestHandler requestHandler = new RequestHandler();
            try {
                String response  = requestHandler.sendPostRequest(siswa);
                if (!response.equals("true")) {
                    Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show();
                    return;
                }
                siswaService.removeSiswa(siswa);
                Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show();
            }

        });







    }
}