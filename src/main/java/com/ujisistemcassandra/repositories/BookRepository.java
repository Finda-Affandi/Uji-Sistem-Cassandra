package com.ujisistemcassandra.repositories;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.ujisistemcassandra.entity.Book;

public interface BookRepository extends CassandraRepository<Book, String> {
}
