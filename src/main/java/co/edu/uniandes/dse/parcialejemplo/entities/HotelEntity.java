package co.edu.uniandes.dse.parcialejemplo.entities;

import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;
import javax.persistence.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;

@Getter
@Setter
@Entity
public class HotelEntity extends BaseEntity {
    private String nombre;
    private String direccion;
    private Integer estrellas;
    private Integer nroHabitaciones;

    @PodamExclude
	@OneToMany(mappedBy = "hotel", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<HabitacionEntity> habitaciones = new ArrayList<>();

}
