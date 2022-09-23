package co.edu.uniandes.dse.parcialejemplo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import co.edu.uniandes.dse.parcialejemplo.entities.HabitacionEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.repositories.HabitacionRepository;
import co.edu.uniandes.dse.parcialejemplo.repositories.HotelRepository;

@Slf4j
@Service
public class HotelHabitacionService {
    @Autowired
	HabitacionRepository habitacionRepository;

    @Autowired
	HotelRepository hotelRepository;

    @Transactional
	public HabitacionEntity addHabitacion(Long habitacionId, Long hotelId) throws EntityNotFoundException {
		log.info("Inicia proceso de agregarle una habitacion al hotel con id = {0}", hotelId);
		
		Optional<HabitacionEntity> habitacionEntity = habitacionRepository.findById(habitacionId);
		if(habitacionEntity.isEmpty())
			throw new EntityNotFoundException("habitacion no es valida");
		
		Optional<HotelEntity> hotelEntity = hotelRepository.findById(hotelId);
		if(hotelEntity.isEmpty())
			throw new EntityNotFoundException("hotel no es valido");
		
		hotelEntity.get().getHabitaciones().add(habitacionEntity.get());
		log.info("Termina proceso de agregarle una habitacion al hotel con id = {0}", hotelId);
		return habitacionEntity.get();
	}
}
