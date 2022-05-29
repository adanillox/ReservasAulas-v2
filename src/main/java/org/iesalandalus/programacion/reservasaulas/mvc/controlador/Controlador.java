/**
 * 
 */
package org.iesalandalus.programacion.reservasaulas.mvc.controlador;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IModelo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.Modelo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.Vista;

/**
 * @author Adan
 *
 */
public class Controlador implements IControlador {
	private IModelo modelo;
	private IVista vista;

	public Controlador(Modelo modelo, Vista vista) {
		if (modelo == null) {
			throw new NullPointerException("ERROR: El modelo no puede ser nulo.");
		}
		
		if (vista == null) {
			throw new NullPointerException("ERROR: La vista no puede ser nula.");
		}
		
		this.modelo = modelo;
		this.vista = vista;
		this.vista.setControlador(this);
		
	}
	
	public void comenzar() {
		modelo.comenzar();
		vista.comenzar();
	}
	
	public void terminar() {
		modelo.terminar();
	}
	
	public void insertarAula(Aula aula) throws OperationNotSupportedException {
		modelo.insertarAula(aula);
	}
	
	public void insertarProfesor(Profesor profesor) throws OperationNotSupportedException {
		modelo.insertarProfesor(profesor);
	}
	
	public void borrarAula(Aula aula) throws OperationNotSupportedException {
		modelo.borrarAula(aula);
	}
	public void borrarProfesor(Profesor profesor) throws OperationNotSupportedException {
		modelo.borrarProfesor(profesor);
	}
	
	public Aula buscarAula(Aula aula) {
		return modelo.buscarAula(aula);
		
	}
	
	public Profesor buscarProfesor(Profesor profesor) {
		return modelo.buscarProfesor(profesor);
		
	}
	
	public List<String> representarAulas() {
		return modelo.representarAulas();
		
	}
	
	public List<String> representarProfesores() {
		return modelo.representarProfesores();
		
	}
	
	public List<String> representarRerservas() {
		return modelo.representarReservas();
		
	}
	
	public void realizarReserva(Reserva reserva) throws OperationNotSupportedException {
		modelo.realizarReserva(reserva);
	}
	
	public void anularReserva(Reserva reserva) throws OperationNotSupportedException {
		modelo.anularReserva(reserva);
		
	}

	public  List<Reserva> getReservasAula(Aula aula) {
		return modelo.getReservasAulas(aula);
		
	}
	
	public  List<Reserva> getReservasProfesor(Profesor profesor) {
		return modelo.getReservasProfesor(profesor);
		
	}
	
	public  List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		return modelo.getReservasPermanencia(permanencia);
		
	}
	
	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		return modelo.consultarDisponibilidad(aula, permanencia);
	}

	@Override
	public List<String> representarReservas() {
		// TODO Auto-generated method stub
		return null;
	}
}
