package com.appsdevelopersblogs.spa.spa_example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface FileStoreRepository extends JpaRepository<FileStorage, Long> {

    FileStorage findByFilename(String filename);

}
