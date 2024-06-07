package com.example.online_safari.repositories;

import com.example.online_safari.model.Safari;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SafariRepository extends JpaRepository<Safari,Long> {
    Optional<Safari> findFirstByUuid(String uuid);

    Page<Safari> findAllByStartPointAndDeletedFalse(String from, Pageable pageable);

    Page<Safari> findAllByDestinationAndDeletedFalse(String destination, Pageable pageable);

    Page<Safari> findAllByDeletedFalseOrderByCreatedAtDesc(Pageable pageable);


}
