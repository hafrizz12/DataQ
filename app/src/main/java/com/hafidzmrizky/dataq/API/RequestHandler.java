package com.hafidzmrizky.dataq.API;

import com.hafidzmrizky.dataq.Model.Siswa;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler {

    String urlFull = "http://10.0.2.2:1500/TambahSiswa.php";

    public String sendPostRequest(Siswa siswa) throws MalformedURLException {

        try {
            URL url = new URL(urlFull);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);

            Map<String, String> param = new HashMap<>();
            param.put("nama", siswa.getNama());
            param.put("nis", siswa.getNis());
            param.put("alamat", siswa.getAlamat());
            param.put("kota", siswa.getKota());
            param.put("jenis_kelamin", siswa.getJenisKelamin());
            param.put("umur", siswa.getUmur());

            OutputStream os = con.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            osw.write(getPostDataString(param));
            os.flush();
            osw.close();
            os.close();

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                return "true";
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "false";
    }

    private String getPostDataString(Map<String, String> param) {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : param.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(entry.getKey());
            result.append("=");
            result.append(entry.getValue());
        }

        return result.toString();
    }


}
