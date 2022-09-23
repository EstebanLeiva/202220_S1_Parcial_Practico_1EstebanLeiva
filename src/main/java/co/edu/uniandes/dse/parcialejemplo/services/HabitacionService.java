package co.edu.uniandes.dse.parcialejemplo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import co.edu.uniandes.dse.parcialejemplo.entities.HabitacionEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.HabitacionRepository;
import co.edu.uniandes.dse.parcialejemplo.repositories.HotelRepository;


@Slf4j
@Service
public class HabitacionService {
    @Autowired
	HabitacionRepository habitacionRepository;

    @Autowired
	HotelRepository hotelRepository;


    @Transactional
	public HabitacionEntity createHabitacion(HabitacionEntity habitacionEntity) throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de creación de la habitacion");
		
		Optional<HotelEntity> hotelEntity = hotelRepository.findById(habitacionEntity.getHotel().getId());
		if (hotelEntity.isEmpty())
			throw new IllegalOperationException("Hotel no es valido");

		if (habitacionEntity.getNroBanos()>habitacionEntity.getNroCamas())
			throw new IllegalOperationException("Hay mas banos que camas");

		
		habitacionEntity.setHotel(hotelEntity.get());
		log.info("Termina proceso de creación de la habitacion");
		return habitacionRepository.save(habitacionEntity);
	}
}
