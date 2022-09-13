package com.bridgelabz.bookstorebackend.service;

import com.bridgelabz.bookstorebackend.dto.DeliveryDTO;
import com.bridgelabz.bookstorebackend.model.Delivery;
import com.bridgelabz.bookstorebackend.repository.DeliveryRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class DeliveryService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DeliveryRepository iDeliveryRepository;



    public Delivery addDeliveryDetails(DeliveryDTO deliveryDTO) {
        Delivery  deliveryDetails= modelMapper.map(deliveryDTO, Delivery.class);
        iDeliveryRepository.save(deliveryDetails);
        return deliveryDetails;
    }



    public List<Delivery> getAllDeliveryDetails() {
        List<Delivery> DeliveryDetails = iDeliveryRepository.findAll();
       return DeliveryDetails;
    }

    public Delivery deleteDeliveryDetails(Integer id) {
        iDeliveryRepository.deleteAll();
        return null;
    }
}
