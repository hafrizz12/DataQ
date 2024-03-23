package com.hafidzmrizky.dataq;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.hafidzmrizky.dataq.API.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LihatSiswa extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_siswa);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ImageView back = findViewById(R.id.imageView);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        getAndAssign();



    }

    private void getAndAssign() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        RequestHandler request = new RequestHandler();
        String result = request.sendGetRequest("http://10.0.2.2/getAllSiswa.php");
        System.out.println(result);
        progressDialog.dismiss();
        if (result != null) {
            showSiswa(result);
        }
    }

    private void showSiswa(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray("result");
            ArrayList<HashMap<String, String>> list = new ArrayList<>();
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String nis = jo.getString("nis");
                String nama = jo.getString("nama");
                String alamat = jo.getString("alamat");
                String kota = jo.getString("kota");
                String gender = jo.getString("gender");
                String umur = jo.getString("umur");
                HashMap<String, String> siswa = new HashMap<>();
                siswa.put("nis", nis);
                siswa.put("nama", nama);
                siswa.put("alamat", alamat);
                siswa.put("kota", kota);
                siswa.put("gender", gender);
                siswa.put("umur", umur);
                list.add(siswa);
            }
            ListAdapter adapter = new SimpleAdapter(
                    LihatSiswa.this, list, R.layout.list_item,
                    new String[]{"nis", "nama", "alamat", "kota", "gender", "umur"},
                    new int[]{R.id.nisList, R.id.namaList, R.id.alamatList2, R.id.kotaList3, R.id.genderList, R.id.umurList2});
            listView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        HashMap<String, String> map = (HashMap<String, String>) parent.getItemAtPosition(position);
        String siswaNis = map.get("nis");
        String siswaNama = map.get("nama");
        String siswaAlamat = map.get("alamat");
        String siswaKota = map.get("kota");
        String siswaGender = map.get("gender");
        String siswaUmur = map.get("umur");

        Intent intent = new Intent(this, EditSiswa.class);
        intent.putExtra("nis", siswaNis);
        intent.putExtra("nama", siswaNama);
        intent.putExtra("alamat", siswaAlamat);
        intent.putExtra("kota", siswaKota);
        intent.putExtra("gender", siswaGender);
        intent.putExtra("umur", siswaUmur);
        startActivity(intent);

    }
}