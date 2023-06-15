package com.ujisistemcassandra.controller;

import com.ujisistemcassandra.entity.Mahasiswa;
import com.ujisistemcassandra.repository.MahasiswaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/mahasiswa")
public class MahasiswaController {
    private final MahasiswaRepository mahasiswaRepository;

    public MahasiswaController(MahasiswaRepository mahasiswaRepository) {
        this.mahasiswaRepository = mahasiswaRepository;
    }

    @GetMapping
    public List<Mahasiswa> getAllMahasiswa() {
        return (List<Mahasiswa>) mahasiswaRepository.findAll();
    }
}
