/**
 * 
 */
package org.iesalandalus.programacion.reservasaulas.mvc.modelo;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria.FactoriaFuenteDatosMemoria;

/**
 * @author Adan
 *
 */
public enum FactoriaFuenteDatos {
	

	MEMORIA {
		public FactoriaFuenteDatosMemoria() {
		}

		
	};

	public abstract IFuenteDatos crear();
}
