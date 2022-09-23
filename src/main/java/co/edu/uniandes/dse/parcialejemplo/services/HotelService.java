package co.edu.uniandes.dse.parcialejemplo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.HotelRepository;


@Slf4j
@Service
public class HotelService {
    @Autowired
	HotelRepository hotelRepository;

    @Transactional
	public HotelEntity createEditorial(HotelEntity hotelEntity) throws IllegalOperationException {
		log.info("Inicia proceso de creación del hotel");
		if (!hotelRepository.findByName(hotelEntity.getNombre()).isEmpty()) {
			throw new IllegalOperationException("El nombre del hotel ya existe");
		}
		log.info("Termina proceso de creación del hotel");
		return hotelRepository.save(hotelEntity);
	}

}
