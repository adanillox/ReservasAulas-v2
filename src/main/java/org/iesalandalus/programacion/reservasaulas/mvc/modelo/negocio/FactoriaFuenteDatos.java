package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio;


import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IFuenteDatos;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.ficheros.FactoriaFuenteDatosFicheros;


/**
 * @author Adan
 *
 */
public enum FactoriaFuenteDatos {
	

	FICHEROS {
		public IFuenteDatos crear() {
			return new FactoriaFuenteDatosFicheros();
		}
	};

	public abstract IFuenteDatos crear();
}
