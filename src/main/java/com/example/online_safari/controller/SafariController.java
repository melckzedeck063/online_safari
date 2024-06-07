package com.example.online_safari.controller;

import com.example.online_safari.dto.SafariDto;
import com.example.online_safari.model.Safari;
import com.example.online_safari.services.SafariService;
import com.example.online_safari.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/safari")
public class SafariController {

    @Autowired
    private SafariService safariService;

    @PostMapping("/new")
    public ResponseEntity<?>  createRoute(@RequestBody SafariDto safariDto){
        Response<Safari> response = safariService.createRoute(safariDto);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<?>  getAllRoutes(@RequestParam(value = "page", defaultValue = "0")Integer page,
                                           @RequestParam(value = "size", defaultValue = "25")Integer size){
        PageRequest pageRequest =  PageRequest.of(page,size);

        Page<Safari> safariPage =  safariService.getAllRoutes(pageRequest);

        return ResponseEntity.ok().body(safariPage);
    }

    @GetMapping("/all-from/{from}")
    public ResponseEntity<?>  getAllRoutesForm(@PathVariable String from,@RequestParam(value = "page", defaultValue = "0")Integer page,
                                           @RequestParam(value = "size", defaultValue = "25")Integer size){
        PageRequest pageRequest =  PageRequest.of(page,size);

        Page<Safari> safariPage =  safariService.getAllRoutesFrom(from,pageRequest);

        return ResponseEntity.ok().body(safariPage);
    }

    @GetMapping("/by-destination/{dest}")
    public ResponseEntity<?>  getAllRoutesTo(@PathVariable String dest,@RequestParam(value = "page", defaultValue = "0")Integer page,
                                           @RequestParam(value = "size", defaultValue = "25")Integer size){
        PageRequest pageRequest =  PageRequest.of(page,size);

        Page<Safari> safariPage =  safariService.getAllRoutesTo(dest,pageRequest);

        return ResponseEntity.ok().body(safariPage);
    }

    @GetMapping("/get/{uuid}")
    public ResponseEntity<?> getByUuid(@PathVariable String uuid){
        Response<Safari> optionalSafari = safariService.getSafariByUuid(uuid);

        return  ResponseEntity.ok().body(optionalSafari);
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<?> deleteSafari(@PathVariable String uuid){
        Response<Safari> optionalSafari = safariService.getSafariByUuid(uuid);


        return  ResponseEntity.ok().body(optionalSafari);
    }
}
