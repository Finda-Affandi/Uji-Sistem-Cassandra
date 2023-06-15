package com.ujisistemcassandra.repository;

import com.ujisistemcassandra.entity.Mahasiswa;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MahasiswaRepository extends CassandraRepository<Mahasiswa, Integer> {

}
