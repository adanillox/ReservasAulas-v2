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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;

/**
 * @author Adan
 *
 */
public class Profesores implements IProfesores{
	private static final String NOMBRE_FICHERO_PROFESORES = "datosAdan/profesores.dat";
	private List<Profesor> coleccionProfesores;


	public Profesores() {
		 this.coleccionProfesores= new ArrayList<>();
	}


	public Profesores(Profesores profesores) {
		if(profesores==null) {
			throw new NullPointerException("ERROR: No se pueden copiar profesores nulos.");
		}
		else{
			setProfesores(profesores);
		}

	}
	private void setProfesores(Profesores profesores) {
		if(profesores==null) {
			throw new NullPointerException("ERROR: Las de profesores no pueden ser null.");
		}
		coleccionProfesores=copiaProfundaProfesores(profesores.coleccionProfesores);
	}


	public List<Profesor> getProfesores(){
		List<Profesor> profesoresOrdenados= copiaProfundaProfesores(coleccionProfesores) ;
		profesoresOrdenados.sort(Comparator.comparing(Profesor::getNombre));
		return profesoresOrdenados;
	}


	public int getNumProfesores() {
		return coleccionProfesores.size();
	}

	private List<Profesor> copiaProfundaProfesores(List<Profesor> profesor) {
		List<Profesor> copiaProfesor= new ArrayList<>();
		Iterator<Profesor> a=profesor.iterator();
		
		while(a.hasNext()) {
			Profesor c =a.next();
			copiaProfesor.add(new Profesor(c));
		}
		return copiaProfesor;
	}





	public void insertar(Profesor profesor) throws OperationNotSupportedException {

		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede insertar un profesor nulo.");
		}if(!coleccionProfesores.contains(profesor)){	 
					coleccionProfesores.add(profesor);
				
			}else {
				throw new OperationNotSupportedException("ERROR: Ya existe un profesor con ese nombre.");
			}
	}

	public Profesor buscar(Profesor profesor)  { 
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede buscar un profesor nulo.");
		}
		if(coleccionProfesores.contains(profesor)) {
			int i=coleccionProfesores.indexOf(profesor);
			profesor=coleccionProfesores.get(i);
			return new Profesor(profesor);

		}else {
			return null;
		}
			}


	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede borrar un profesor nulo.");
		}
		if(coleccionProfesores.contains(profesor)) {
			coleccionProfesores.remove(profesor);
		}
			else {
				throw new OperationNotSupportedException("ERROR: No existe ningún profesor con ese nombre.");
			}
		
	}	
	public List<String> representar(){
		System.out.println("Listado de profesores");
		List<String> representa = new ArrayList<>();
		for(Profesor a : coleccionProfesores) {
			representa.add(a.toString());
		 
		}
		return representa ;
	}
	

	public void comenzar() {
		leer();
	}

	private void leer() {
		File ficheroProfesores = new File(NOMBRE_FICHERO_PROFESORES);
		// Indicamos que se trata de un flujo de entrada para leer objetos
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ficheroProfesores))) {
			Profesor profesor = null;
			do {
				// Casting
				profesor = (Profesor) entrada.readObject();
				insertar(profesor);
			} while (profesor != null);
			// Cerramos el flujo
			entrada.close();
		} catch (ClassNotFoundException e) {
			System.out.println("No puedo encontrar la clase que tengo que leer.");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo abrir el fihero de profesores.");
		} catch (EOFException e) {
			System.out.println("Fichero profesores leído satisfactoriamente.");
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
		File ficheroAulas = new File(NOMBRE_FICHERO_PROFESORES);
		// Indicamos que se trata de un flujo de salida para escribir objetos
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ficheroAulas))) {
			for (Profesor profesor : coleccionProfesores) {
				salida.writeObject(profesor);
			}
			System.out.println("Fichero profesores escrito satisfactoriamente.");
			// Cerramos el flujo
			salida.close();
		} catch (FileNotFoundException e) {
			System.out.println("No puedo crear el fichero de profesores.");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida.");
		}
	}
}
