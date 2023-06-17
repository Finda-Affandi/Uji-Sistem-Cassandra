package com.ujisistemcassandra.controller;

import com.ujisistemcassandra.entity.Mahasiswa;
import com.ujisistemcassandra.service.MahasiswaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MahasiswaController {
    @Autowired
    private final MahasiswaService mahasiswaService;
    @Autowired
    public MahasiswaController(MahasiswaService mahasiswaService) {
        this.mahasiswaService = mahasiswaService;
    }


    @GetMapping("/mahasiswa")
    public ResponseEntity<List<Mahasiswa>> getAllMahasiswa() {
        List<Mahasiswa> entities = mahasiswaService.getAllMahasiswa();
        return ResponseEntity.ok(entities);
    }
}
