package com.ujisistemcassandra.services;

import com.ujisistemcassandra.entity.Mahasiswa;
import com.ujisistemcassandra.repositories.MahasiswaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mahasiswa")
public class MahasiswaController {
    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    public MahasiswaController(MahasiswaRepository mahasiswaRepository) {
        this.mahasiswaRepository = mahasiswaRepository;
    }

    @GetMapping
    public List<Mahasiswa> getAllMahasiswa() {
        return mahasiswaRepository.findAll();
    }
}
