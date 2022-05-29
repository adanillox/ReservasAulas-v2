/**
 * 
 */
package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.ficheros;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IReservas;

/**
 * @author Adan
 *
 */
public class Reservas implements IReservas {
	private static final float MAX_PUNTOS_PROFESOR_MES = 200.0f;
	private static final String NOMBRE_FICHERO_RESERVAS = "datosAdan/reservas.dat";
	private List<Reserva> coleccionReservas;

	public Reservas() {
		this.coleccionReservas = new ArrayList<>();
	}

	public Reservas(Reservas reservas) {
		if (reservas == null) {
			throw new NullPointerException("ERROR: No se pueden copiar reservas nulas.");
		} else {
			setReservas(reservas);
		}

	}

	private void setReservas(Reservas reservas) {
		if (reservas == null) {
			throw new NullPointerException("ERROR: Las de reservas no pueden ser null.");
		}
		coleccionReservas = copiaProfundaReservas(reservas.coleccionReservas);
	}

	public List<Reserva> getReservas() {//Este metodo fue pasado por Andres
		List<Reserva> reservasOrdenadas = copiaProfundaReservas(coleccionReservas);
		Comparator<Aula> comparadorAula = Comparator.comparing(Aula::getNombre);

		Comparator<Permanencia> comparadorPermanencia = (Permanencia p1, Permanencia p2) -> {
			int comparacion = -1;

			if (p1.getDia().equals(p2.getDia())) {

				if (p1 instanceof PermanenciaPorTramo && p2 instanceof PermanenciaPorTramo) {
					comparacion = Integer.compare(((PermanenciaPorTramo) p1).getTramo().ordinal(),
							((PermanenciaPorTramo) p2).getTramo().ordinal());

				} else if (p1 instanceof PermanenciaPorHora && p2 instanceof PermanenciaPorHora) {
					comparacion = ((PermanenciaPorHora) p1).getHora().compareTo(((PermanenciaPorHora) p2).getHora());
				}
			} else {

				comparacion = p1.getDia().compareTo(p2.getDia());
			}
			return comparacion;
		};

		reservasOrdenadas.sort(Comparator.comparing(Reserva::getAula, comparadorAula)
				.thenComparing(Reserva::getPermanencia, comparadorPermanencia));
		return reservasOrdenadas;
	}

	public int getNumReservas() {
		return coleccionReservas.size();
	}

	private List<Reserva> copiaProfundaReservas(List<Reserva> reserva) {
		List<Reserva> copiaReserva = new ArrayList<>();
		Iterator<Reserva> a = reserva.iterator();

		while (a.hasNext()) {
			Reserva c = a.next();
			copiaReserva.add(new Reserva(c));
		}
		return copiaReserva;
	}

	public void insertar(Reserva reserva) throws OperationNotSupportedException {

		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede realizar una reserva nula.");
		}
		if (!coleccionReservas.contains(reserva)) {
			coleccionReservas.add(reserva);

		} else {
			throw new OperationNotSupportedException("ERROR: La reserva ya existe.");
		}
	}

	private boolean esMesSiguienteOPosterior(Reserva reserva) {
		int mRes = reserva.getPermanencia().getDia().getMonthValue();
		int mAct = LocalDate.now().getMonthValue();
		int aRes = reserva.getPermanencia().getDia().getYear();
		int aAct = LocalDate.now().getYear();

		boolean valido = false;

		if (aRes - aAct == 1 && (mRes - mAct == -11 || mRes - mAct == -10)) {
			valido = true;
		}
		if (aRes == aAct && (mRes - mAct == 1 || mRes - mAct == 2)) {
			valido = true;
		}
		return valido;
	}

	private List<Reserva> getReservasProfesorMes(Profesor profesor, LocalDate mesFecha) {

		List<Reserva> reservasMes = new ArrayList<>();

		Month mesReserva = mesFecha.getMonth();

		for (Iterator<Reserva> remes = getReservas().iterator(); remes.hasNext();) {

			Reserva res = remes.next();

			if (res.getProfesor().equals(profesor) && res.getPermanencia().getDia().getMonth().equals(mesReserva)) {

				reservasMes.add(new Reserva(res));
			}

		}

		return reservasMes;

	}

	private Reserva getReservaAulaDia(Aula aula, LocalDate diaFecha) {

		Reserva reserva = null;

		int diaReserva = diaFecha.getDayOfMonth();

		for (Iterator<Reserva> it = getReservas().iterator(); it.hasNext();) {

			reserva = it.next();
		}
		if (reserva.getAula().equals(aula) && reserva.getPermanencia().getDia().getDayOfMonth() == diaReserva) {

			return new Reserva(reserva);

		} else {

			return null;
		}

	}

	public Reserva buscar(Reserva reserva) {
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede buscar un reserva nula.");
		}
		if (coleccionReservas.contains(reserva)) {
			int i = coleccionReservas.indexOf(reserva);
			reserva = coleccionReservas.get(i);
			return new Reserva(reserva);

		} else {
			return null;
		}
	}

	public void borrar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede anular una reserva nula.");
		}
		if (coleccionReservas.contains(reserva)) {
			coleccionReservas.remove(reserva);
		} else {
			throw new OperationNotSupportedException("ERROR: La reserva a anular no existe.");
		}

	}

	public List<String> representar() {
		System.out.println("Listado de reservas");
		List<String> representa = new ArrayList<>();
		for (Reserva a : coleccionReservas) {
			representa.add(a.toString());

		}
		return representa;
	}

	public List<Reserva> getReservasProfesor(Profesor profesor) {
		List<Reserva> res = new ArrayList<>();
		for (Iterator<Reserva> r = getReservas().iterator(); r.hasNext();) {
			Reserva reserva = r.next();
			if (reserva.getProfesor().equals(profesor)) {
				res.add(new Reserva(reserva));
			}
		}
		return res;
	}

	public List<Reserva> getReservasAula(Aula aula) {
		List<Reserva> res = new ArrayList<>();
		for (Iterator<Reserva> r = getReservas().iterator(); r.hasNext();) {
			Reserva reserva = r.next();
			if (reserva.getAula().equals(aula)) {
				res.add(new Reserva(reserva));
			}
		}
		return res;
	}

	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		List<Reserva> res = new ArrayList<>();
		for (Iterator<Reserva> r = getReservas().iterator(); r.hasNext();) {
			Reserva reserva = r.next();

			if (reserva.getPermanencia().equals(permanencia)) {
				res.add(new Reserva(reserva));
			}
		}
		return res;
	}

	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de un aula nula.");

		}
		if (permanencia == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de una permanencia nula.");
		}

		for (Iterator<Reserva> r = getReservas().iterator(); r.hasNext();) {
			Reserva reserva = r.next();
			if (reserva.getAula().equals(aula) && reserva.getPermanencia().equals(permanencia)) {

				return false;
			}

		}

		return true;

	}

	

	public void comenzar() {
		leer();
	}

	private void leer() {
		File ficheroAulas = new File(NOMBRE_FICHERO_RESERVAS);
		// Indicamos que se trata de un flujo de entrada para leer objetos
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ficheroAulas))) {
			Reserva reserva = null;
			do {
				// Casting
				reserva = (Reserva) entrada.readObject();
				insertar(reserva);
			} while (reserva != null);
			// Cerramos el flujo
			entrada.close();
		} catch (ClassNotFoundException e) {
			System.out.println("No puedo encontrar la clase que tengo que leer.");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo abrir el fihero de reservas.");
		} catch (EOFException e) {
			System.out.println("Fichero reservas leído satisfactoriamente.");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida.");
		} catch (OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}


	public void terminar() {
		escribir();
	}

	private void escribir() {
		File ficheroAulas = new File(NOMBRE_FICHERO_RESERVAS);
		// Indicamos que se trata de un flujo de salida para escribir objetos
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ficheroAulas))) {
			for (Reserva reserva : coleccionReservas) {
				salida.writeObject(reserva);
			}
			System.out.println("Fichero reservas escrito satisfactoriamente.");
			// Cerramos el flujo
			salida.close();
		} catch (FileNotFoundException e) {
			System.out.println("No puedo crear el fichero de reservas.");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida.");
		}
	}

	private void setReservas(IReservas reservas) {
		if (reservas == null) {
			throw new NullPointerException("ERROR: No se pueden copiar reservas nulas.");
		}
		coleccionReservas = copiaProfundaReservas(reservas.getReservas());
	}
}
