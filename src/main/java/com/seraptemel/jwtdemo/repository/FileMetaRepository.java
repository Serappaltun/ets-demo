package com.seraptemel.jwtdemo.repository;

import com.seraptemel.jwtdemo.entity.FileMeta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMetaRepository extends CrudRepository<FileMeta, Long> {
    
}
