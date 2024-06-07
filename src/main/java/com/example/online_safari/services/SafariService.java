package com.example.online_safari.services;

import com.example.online_safari.dto.SafariDto;
import com.example.online_safari.model.Safari;
import com.example.online_safari.utils.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SafariService {

    Response<Safari> createRoute(SafariDto safariDto);

    Response<Safari> getSafariByUuid(String uuid);
    Response<Safari> deleteRoute(String uuid);

    Page<Safari> getAllRoutes(Pageable  pageable);

    Page<Safari> getAllRoutesFrom(String from, Pageable pageable);

    Page<Safari> getAllRoutesTo(String dest, Pageable pageable);

}
