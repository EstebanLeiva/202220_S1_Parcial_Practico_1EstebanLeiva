package co.edu.uniandes.dse.parcialejemplo.entities;

import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;
import javax.persistence.Entity;



@Getter
@Setter
@Entity
public class HabitacionEntity extends BaseEntity {
    private Long nroIdentificacion;
    private Integer nroPersonas;
    private Integer nroCamas;
    private Integer nroBanos;
    
    @PodamExclude
	@ManyToOne
	private HotelEntity hotel;
}
