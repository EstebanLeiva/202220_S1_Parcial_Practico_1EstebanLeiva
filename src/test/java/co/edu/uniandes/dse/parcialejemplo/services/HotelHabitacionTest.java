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

import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.HabitacionEntity;

import java.util.ArrayList;
import java.util.List;

import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;



@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(HotelHabitacionService.class)
public class HotelHabitacionTest {
    @Autowired
	private HotelHabitacionService hotelHabitacionService;

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
	void testAddHabitacion() throws EntityNotFoundException {
		HotelEntity entity = hotelList.get(0);
		HabitacionEntity habitacionEntity = habitacionList.get(1);
		HabitacionEntity response = hotelHabitacionService.addHabitacion(habitacionEntity.getId(), entity.getId());

		assertNotNull(response);
		assertEquals(habitacionEntity.getId(), response.getId());
	}

    @Test
	void testAddHabitacionInvalidHotel() {
		assertThrows(EntityNotFoundException.class, ()->{
			HabitacionEntity habitacionEntity = habitacionList.get(1);
			hotelHabitacionService.addHabitacion(habitacionEntity.getId(), 0L);
		});
	}

    @Test
	void testAddInvalidHabitacion() {
		assertThrows(EntityNotFoundException.class, ()->{
			HotelEntity entity = hotelList.get(0);
			hotelHabitacionService.addHabitacion(0L, entity.getId());
		});
	}

}
