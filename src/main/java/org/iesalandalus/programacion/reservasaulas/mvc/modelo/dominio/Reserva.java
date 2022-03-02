/**
 * 
 */
package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.util.Objects;

/**
 * @author Adan
 *
 */
public class Reserva {
	private Profesor profesor;
	private Aula aula;
	private Permanencia permanencia;

	public	Reserva(Profesor profesor, Aula aula,Permanencia permanencia) {
		setProfesor(profesor);
		setAula(aula);
		setPermanencia(permanencia);
	}
	public Reserva(Reserva reserva) {
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede copiar una reserva nula.");
		}
		setProfesor(reserva.profesor);
		setAula(reserva.aula);
		setPermanencia(reserva.permanencia);
	}
		

	private void setProfesor(Profesor profesor) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: La reserva debe estar a nombre de un profesor.");
		}
		else {
			this.profesor= new Profesor(getProfesor());
		}
		
	}
	public Profesor getProfesor() {
		return  profesor ;
	}
	private void setAula(Aula aula) {
		if (aula == null) {
			throw new NullPointerException("ERROR: La reserva debe ser para un aula concreta.");
		}
		else {
			this.aula= new Aula(getAula());
		}
		
	}
	public Aula getAula() {
		return aula;
	}
	public Permanencia getPermanencia() {
		return permanencia;
	}
	public void setPermanencia(Permanencia permanencia){
		if(permanencia==null) {
			throw new NullPointerException("ERROR: La reserva se debe hacer para una permanencia concreta.");
		}
		if(permanencia instanceof PermanenciaPorTramo) {
			this.permanencia= new PermanenciaPorTramo((PermanenciaPorTramo)permanencia);
		}
		if(permanencia instanceof PermanenciaPorHora) {
			this.permanencia =new PermanenciaPorHora((PermanenciaPorHora) permanencia);
		}
		
	}
	public static Reserva getReservaFicticia(Aula aula, Permanencia permanencia) {
		if(aula==null ) {
			throw new NullPointerException("ERROR: La reserva debe ser para un aula concreta.");
		}
		if(permanencia==null) {
			throw new NullPointerException("ERROR: La reserva se debe hacer para una permanencia concreta.");
		}
		return new Reserva(Profesor.getProfesorFicticio("Adan@gmail.com"),Aula.getAulaFicticia(aula.getNombre()),permanencia);
	}
	public float getPuntos() {
		return permanencia.getPuntos()+ aula.getPuntos();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(aula, permanencia, profesor);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reserva other = (Reserva) obj;
		return Objects.equals(aula, other.aula) && Objects.equals(permanencia, other.permanencia)
				&& Objects.equals(profesor, other.profesor);
	}
	@Override
	public String toString() {
		return String.format("%s, %s, %s, puntos=%.1f", profesor, aula, permanencia, getPuntos());
	}
	
	
}
