/**
 * 
 */
package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.util.Objects;

import org.apache.commons.math3.ml.neuralnet.twod.util.HitHistogram;

/**
 * @author Adan
 *
 */
public class Aula {
	private static final float PUNTOS_POR_PUESTO = 200;
	private static final int MN_PUESTOS = 0;
	private static final int MAX_PUESTOS = 200;
	private String nombre;
	private int puestos;
	

	public Aula(String nombre, int puestos) {
		setNombre(nombre);
		setPuestos(puestos);
	}

	public Aula(Aula aula) {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede copiar un aula nula.");
		} else {
			setNombre(aula.getNombre());
		}
	}

	private void setNombre(String nombre) {
		if (nombre == null) {
			throw new NullPointerException("ERROR: El nombre del aula no puede ser nulo.");
		}
		if (nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("ERROR: El nombre del aula no puede estar vacío.");
		} else {
			this.nombre = nombre;
		}
	}

	public String getNombre() {
		return this.nombre;
	}

	private void setPuestos(int puestos) {
		if (puestos == 0 || puestos<0) {
			throw new IllegalArgumentException("ERROR: El puesto del aula no puede ser 0 o un numero negativo.");
		} else {
			this.puestos = puestos;
		}
	}

	public int getPuestos() {
		return this.puestos;
	}

	public float getPuntos() {
		return this.PUNTOS_POR_PUESTO;
	}

	public static Aula getAulaFicticia(String nombre) {
		if (nombre == null) {
			throw new NullPointerException("ERROR: El nombre del aula no puede ser nulo.");
		} else {
			return new Aula(nombre, 25);

		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombre, puestos);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aula other = (Aula) obj;
		return Objects.equals(nombre, other.nombre) && puestos == other.puestos;
	}

	@Override
	public String toString() {
		return "Aula [nombre=" + nombre + ", puestos=" + puestos + "]";
	}

}
