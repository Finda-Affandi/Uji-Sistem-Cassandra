package com.ujisistemcassandra.controller;


import com.ujisistemcassandra.entity.Mahasiswa;
import com.ujisistemcassandra.repository.MahasiswaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ServiceController {

    private final MahasiswaRepository mahasiswaRepository;
    @Autowired
    public ServiceController(MahasiswaRepository mahasiswaRepository) {
        this.mahasiswaRepository = mahasiswaRepository;
    }

    @GetMapping("/mahasiswa")
    public List<Mahasiswa> getAllMahasiswa() {
        return mahasiswaRepository.findAll();
    }

    @PostMapping("/mahasiswa")
    public ResponseEntity<Void> saveMahasiswa(@RequestBody Mahasiswa mahasiswa) {
        mahasiswaRepository.saveMahasiswa(mahasiswa);
        return ResponseEntity.ok().build();
    }


}
