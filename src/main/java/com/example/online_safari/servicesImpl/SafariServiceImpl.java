package com.example.online_safari.servicesImpl;

import com.example.online_safari.dto.SafariDto;
import com.example.online_safari.model.Safari;
import com.example.online_safari.model.UserAccount;
import com.example.online_safari.repositories.SafariRepository;
import com.example.online_safari.services.SafariService;
import com.example.online_safari.utils.Response;
import com.example.online_safari.utils.ResponseCode;
import com.example.online_safari.utils.userextractor.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
public class SafariServiceImpl implements SafariService {

    @Autowired
    private SafariRepository safariRepository;

    @Autowired
    private LoggedUser loggedUser;


    @Override
    public Response<Safari> createRoute(SafariDto safariDto) {
        try {
            UserAccount  user = loggedUser.getUser();

            Safari safari =  new Safari();

            if(user  ==  null){
                return new Response<>(false, ResponseCode.UNAUTHORIZED,"Unauthorizeed");
            }

            if(safariDto.getDestination() == null){
                return new Response<>(true,ResponseCode.NULL_ARGUMENT,"destination point can not be null");
            }

            if(safariDto.getStartPoint() == null){
                return new Response<>(true,ResponseCode.NULL_ARGUMENT,"destination point can not be null");
            }

            if(safariDto.getPrice() == null){
                return new Response<>(true,ResponseCode.NULL_ARGUMENT,"route price can not be null");
            }
            if(safariDto.getDeparture() == null){
                return new Response<>(true,ResponseCode.NULL_ARGUMENT,"departure time can not be null");
            }

            if(!safariDto.getDeparture().isBlank() &&  !Objects.equals(safariDto.getDeparture(), safari.getDeparture())){
                System.out.println("DEPART : " + safariDto.getDeparture());
                safari.setDeparture(safariDto.getDeparture());
            }


            if(!safariDto.getStartPoint().isBlank() &&  !Objects.equals(safariDto.getStartPoint(), safari.getStartPoint())){
                safari.setStartPoint(safariDto.getStartPoint());
            }

            if(!safariDto.getDestination().isBlank() &&  !Objects.equals(safariDto.getDestination(), safari.getDestination())){
                safari.setDestination(safariDto.getDestination());
            }

            if(!safariDto.getPrice().isBlank() &&  !Objects.equals(safariDto.getPrice(), safari.getPrice())){
                safari.setPrice(safariDto.getPrice());
            }

            if(!safariDto.getDeparture().isBlank() &&  !Objects.equals(safariDto.getDeparture(), safari.getDeparture())){
                System.out.println("DEPART : " + safariDto.getDeparture());
                safari.setDeparture(safariDto.getDeparture());
            }

            safari.setAddedBy(user);

            Safari safari1 =  safariRepository.save(safari);

            return new Response<>(false,ResponseCode.SUCCESS,safari1,"Route registered successfully");


        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new Response<>(true,ResponseCode.FAIL,"Operation  failed");
    }

    @Override
    public Response<Safari> getSafariByUuid(String uuid) {
        try {
            Optional<Safari> optionalSafari = safariRepository.findFirstByUuid(uuid);
            return optionalSafari.map(safari -> new Response<>(false, ResponseCode.SUCCESS, safari, "Record found")).orElseGet(() -> new Response<>(true, ResponseCode.NO_RECORD_FOUND, "No record found"));

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new Response<>(true,ResponseCode.FAIL,"Operation  failed");
    }

    @Override
    public Response<Safari> deleteRoute(String uuid) {
        try {
            Optional<Safari> optionalSafari = safariRepository.findFirstByUuid(uuid);
            if(optionalSafari.isEmpty()){
                return new Response<>(true,ResponseCode.NO_RECORD_FOUND,"No record found");
            }

            Safari safari =  optionalSafari.get();
            safariRepository.delete(safari);

            return new Response<>(false,ResponseCode.SUCCESS,"Record deleted successfully");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new Response<>(true,ResponseCode.FAIL,"Operation  failed");
    }

    @Override
    public Page<Safari> getAllRoutes(Pageable pageable) {
        try {
            Page<Safari> safariPage =  safariRepository.findAllByDeletedFalseOrderByCreatedAtDesc(pageable);
            return  safariPage;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new PageImpl<>(new ArrayList<>());
    }

    @Override
    public Page<Safari> getAllRoutesFrom(String from, Pageable pageable) {
        try {
            Page<Safari> safariPage =  safariRepository.findAllByStartPointAndDeletedFalse(from,pageable);
            return  safariPage;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new PageImpl<>(new ArrayList<>());
    }

    @Override
    public Page<Safari> getFilteredRoutes(String start, String dest, Pageable pageable) {
        try {
            Page<Safari> safariPage =  safariRepository.findAllByStartPointAndDestinationAndDeletedFalse(start,dest,pageable);
            return  safariPage;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new PageImpl<>(new ArrayList<>());
    }

    @Override
    public Page<Safari> getAllRoutesTo(String dest, Pageable pageable) {
        try {
            Page<Safari> safariPage =  safariRepository.findAllByDestinationAndDeletedFalse(dest,pageable);
            return  safariPage;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new PageImpl<>(new ArrayList<>());
    }


}
