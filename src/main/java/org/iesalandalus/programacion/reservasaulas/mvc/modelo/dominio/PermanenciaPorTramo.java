/**
 * 
 */
package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Adan
 *
 */
public class PermanenciaPorTramo extends Permanencia {
public static final int PUNTOS=10;
private Tramo tramo;
public PermanenciaPorTramo(LocalDate fecha, Tramo tramo) {
	super(fecha);
	setTramo(tramo);
	
}
public PermanenciaPorTramo(PermanenciaPorTramo PPT) {
	super(PPT);
	if(PPT==null) {
		throw new NullPointerException("ERROR:no pueder ser nulo una PermaneniaPorTramo");
	}
	setTramo(PPT.getTramo());
	
}
/**
 * @return the tramo
 */
public Tramo getTramo() {
	return this.tramo;
}
/**
 * @param tramo the tramo to set
 */
public void setTramo(Tramo tramo) {
	if (tramo == null) {
		throw new NullPointerException("ERROR: El tramo de una permanencia no puede ser nulo.");
	}
	this.tramo = tramo;
}
/**
 * @return the puntos
 */
public  int getPuntos() {
	return PUNTOS;
}
@Override
public int hashCode() {
	return Objects.hash(tramo);
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	PermanenciaPorTramo other = (PermanenciaPorTramo) obj;
	return tramo == other.tramo;
}
@Override
public String toString() {
	return super.toString() + ", tramo=" + getTramo();
}

	
}
