/**
 * 
 */
package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;

/**
 * @author Adan
 *
 */
public interface IAulas {
	Aula buscar(Aula aula);

	void borrar(Aula aula) throws OperationNotSupportedException;

	List<String> representar();

	List<Aula> getAulas();

	void insertar(Aula aula) throws OperationNotSupportedException;

	int getNumAulas();

}
