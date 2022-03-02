/**
 * 
 */
package org.iesalandalus.programacion.reservasaulas.mvc.vista;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;

/**
 * @author Adan
 *
 */
public interface IVista {
	public void setControlador(IControlador controlador);

	public void comenzar();

	public void salir();
}
