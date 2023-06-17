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

    public List<Mahasiswa> getAllMahasiswa() {
        String sql = "SELECT * FROM mahasiswa";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            Mahasiswa mahasiswa = new Mahasiswa();
            mahasiswa.setNim(resultSet.getInt("nim"));
            mahasiswa.setNama(resultSet.getString("nama"));
            mahasiswa.setAlamat(resultSet.getString("alamat"));
            return mahasiswa;
        });
    }
}
