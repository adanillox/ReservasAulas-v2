/**
 * 
 */
package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.util.Objects;



/**
 * @author Adan
 *
 */
public class Aula {
	private static final float PUNTOS_POR_PUESTO = 0.5f;
	private static final int MN_PUESTOS = 10;
	private static final int MAX_PUESTOS = 100;
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
			setPuestos(aula.getPuestos());
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
	
		if (puestos>=MAX_PUESTOS || puestos<MN_PUESTOS ) {
			throw new IllegalArgumentException("ERROR: El número de puestos no es correcto.");
		}
		else {
			this.puestos = puestos;
		}
	}

	public int getPuestos() {
		return this.puestos;
	}

	public float getPuntos() {
		return PUNTOS_POR_PUESTO * getPuestos();
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
		return "nombre=" + nombre + ", puestos=" + puestos;
	}

}
