package com.ujisistemcassandra.repository;

import com.ujisistemcassandra.entity.Mahasiswa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MahasiswaRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MahasiswaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveMahasiswa(Mahasiswa mahasiswa) {
        jdbcTemplate.update("INSERT INTO ujisistemc.mahasiswa (nim, nama, alamat) VALUES (?, ?, ?)",
                mahasiswa.getNim(), mahasiswa.getNama(), mahasiswa.getAlamat());
    }
    public List<Mahasiswa> findAll(){
        return jdbcTemplate.query("SELECT * FROM ujisistemc.mahasiswa",
                (resultSet, rowNum) -> {
                    Mahasiswa mahasiswa = new Mahasiswa();
                    mahasiswa.setNim(resultSet.getString("nim"));
                    mahasiswa.setNama(resultSet.getString("nama"));
                    mahasiswa.setAlamat(resultSet.getString("alamat"));
                    return mahasiswa;
                });
    }
}
