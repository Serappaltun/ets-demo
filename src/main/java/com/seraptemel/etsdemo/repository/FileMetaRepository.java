package com.seraptemel.etsdemo.repository;

import com.seraptemel.etsdemo.entity.FileMeta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMetaRepository extends CrudRepository<FileMeta, Long> {
    
}
