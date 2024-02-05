package com.hafidzmrizky.dataq.Service;

import com.hafidzmrizky.dataq.Model.Siswa;

import java.util.ArrayList;
import java.util.Collection;

public class SiswaService extends Siswa {

    private static SiswaService instance;
    private Collection<Siswa> siswa;

    public static SiswaService getInstance() {
        if (instance == null) {
            instance = new SiswaService();
        }
        return instance;
    }

    public SiswaService() {
        siswa = new ArrayList<>();
    }

    public void addSiswa(Siswa siswa) {
        this.siswa.add(siswa);
    }

    public void removeSiswa(Siswa siswa) {
        this.siswa.remove(siswa);
    }

    public Collection<Siswa> getSiswa() {
        return siswa;
    }

    public void setSiswa(Collection<Siswa> siswa) {
        this.siswa = siswa;
    }

    public void clearSiswa() {
        siswa.clear();
    }

    public void updateSiswa(Siswa siswa) {
        this.siswa.add(siswa);
    }

    public Collection<Siswa> getSiswa(int id) {
            Collection<Siswa> matchingSiswa = new ArrayList<>();

            for (Siswa siswa : this.siswa) {
                if (siswa.getId() == id) {
                    matchingSiswa.add(siswa);
                }
            }
            return matchingSiswa;
        }


}
