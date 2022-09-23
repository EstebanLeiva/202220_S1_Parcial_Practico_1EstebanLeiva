package co.edu.uniandes.dse.parcialejemplo.services;
import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import co.edu.uniandes.dse.parcialejemplo.entities.HabitacionEntity;

import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;

import java.util.ArrayList;
import java.util.List;

import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(HabitacionService.class)

public class HabitacionTest {
    @Autowired
	private HabitacionService habitacionService;

	@Autowired
	private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<HotelEntity> hotelList = new ArrayList<>();
    private List<HabitacionEntity> habitacionList = new ArrayList<>();

    @BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

	
	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from HotelEntity");
		entityManager.getEntityManager().createQuery("delete from HabitacionEntity");

	}

	private void insertData() {
		for (int i = 0; i < 3; i++) {
			HotelEntity hotelEntity = factory.manufacturePojo(HotelEntity.class);
			entityManager.persist(hotelEntity);
			hotelList.add(hotelEntity);
		}
        for (int i = 0; i < 3; i++) {
			HabitacionEntity habitacionEntity = factory.manufacturePojo(HabitacionEntity.class);
			habitacionEntity.setHotel(hotelList.get(i));
            entityManager.persist(habitacionEntity);
			habitacionList.add(habitacionEntity);
		}
    
	
	}

    @Test
	void testCreateHabitacion() throws EntityNotFoundException, IllegalOperationException {
		HabitacionEntity newEntity = factory.manufacturePojo(HabitacionEntity.class);
		newEntity.setHotel(hotelList.get(0));
		newEntity.setNroBanos(2);
        newEntity.setNroCamas(5);
		HabitacionEntity result = habitacionService.createHabitacion(newEntity);
		assertNotNull(result);
		HabitacionEntity entity = entityManager.find(HabitacionEntity.class, result.getId());
		assertEquals(newEntity.getId(), entity.getId());
		assertEquals(newEntity.getHotel(), entity.getHotel());
		assertEquals(newEntity.getNroBanos(), entity.getNroBanos());
		assertEquals(newEntity.getNroCamas(), entity.getNroCamas());
		assertEquals(newEntity.getNroIdentificacion(), entity.getNroIdentificacion());
		assertEquals(newEntity.getNroPersonas(), entity.getNroPersonas());
	}

    @Test
	void testCreateHabitacionWithNroBanosMayorCamas() {
		assertThrows(IllegalOperationException.class, () -> {
			HabitacionEntity newEntity = factory.manufacturePojo(HabitacionEntity.class);
			newEntity.setNroBanos(2);
            newEntity.setNroCamas(0);
            newEntity.setHotel(hotelList.get(0));
			habitacionService.createHabitacion(newEntity);
		});
	}
}
