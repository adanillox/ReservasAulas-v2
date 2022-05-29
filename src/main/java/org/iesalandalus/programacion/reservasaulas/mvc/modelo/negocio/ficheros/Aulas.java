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

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IAulas;

/**
 * @author Adan
 *
 */
public class Aulas implements IAulas {
	private static final String NOMBRE_FICHERO_AULAS = "datosAdan/aulas.dat";
private  List<Aula> coleccionAulas; 
	
	public Aulas() {
		 this.coleccionAulas= new ArrayList<>();
	}
	
	
	public Aulas(Aulas aulas) {
		if(aulas==null) {
			throw new NullPointerException("ERROR: No se pueden copiar aulas nulas.");
		}
		else{
			setAulas(aulas);
		}

	}
	

	
	
	
	private void setAulas(Aulas aulas) {
		if(aulas==null) {
			throw new NullPointerException("ERROR: Las de aulas no pueden ser null.");
		}
		coleccionAulas=copiaProfundaAulas(aulas.coleccionAulas);
	}
	
	
	public List<Aula> getAulas(){
	List<Aula> aulasOrdenadas= copiaProfundaAulas(coleccionAulas) ;
	aulasOrdenadas.sort(Comparator.comparing(Aula::getNombre));
	return aulasOrdenadas;
	}
	
	
	public int getNumAulas() {
		return coleccionAulas.size();
	}
	
	private List<Aula> copiaProfundaAulas(List<Aula> aula) {
		List<Aula> copiaAula= new ArrayList<>();
		Iterator<Aula> a=aula.iterator();
		
		while(a.hasNext()) {
			Aula c =a.next();
			copiaAula.add(new Aula(c));
		}
		return copiaAula;
	}
	




	public void insertar(Aula aula) throws OperationNotSupportedException {

		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede insertar un aula nula.");
		}if(!coleccionAulas.contains(aula)){	 
					coleccionAulas.add(aula);
				
			}else {
				throw new OperationNotSupportedException("ERROR: Ya existe un aula con ese nombre.");
			}
	}

	public Aula buscar(Aula aula)  { 
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede buscar un aula nula.");
		}
		if(coleccionAulas.contains(aula)) {
			int i=coleccionAulas.indexOf(aula);
			aula=coleccionAulas.get(i);
			return new Aula(aula);

		}else {
			return null;
		}
			}
	
	
	public void borrar(Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede borrar un aula nula.");
		}
		if(coleccionAulas.contains(aula)) {
			coleccionAulas.remove(aula);
		}
			else {
				throw new OperationNotSupportedException("ERROR: No existe ningún aula con ese nombre.");
			}
		
	}	
	public List<String> representar(){
		System.out.println("Listado de aulas");
		List<String> representa = new ArrayList<>();
		for(Aula a : coleccionAulas) {
			representa.add(a.toString());
		 
		}if (!representa.isEmpty()) {
			
			return representa ;
			}
		throw new NullPointerException("ERROR: Inserte primero un aula.");
		
	}
	
	
	
	
	
	
	
	public void comenzar() {
		leer();
	}

	private void leer() {
		File ficheroAulas = new File(NOMBRE_FICHERO_AULAS);
		// Indicamos que se trata de un flujo de entrada para leer objetos
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ficheroAulas))) {
			Aula aula = null;
			do {
				// Casting
				aula = (Aula) entrada.readObject();
				insertar(aula);
			} while (aula != null);
			// Cerramos el flujo
			entrada.close();
		} catch (ClassNotFoundException e) {
			System.out.println("No puedo encontrar la clase que tengo que leer.");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo abrir el fihero de aulas.");
		} catch (EOFException e) {
			System.out.println("Fichero aulas leído satisfactoriamente.");
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
		File ficheroAulas = new File(NOMBRE_FICHERO_AULAS);
		// Indicamos que se trata de un flujo de salida para escribir objetos
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ficheroAulas))) {
			for (Aula aula : coleccionAulas) {
				salida.writeObject(aula);
			}
			System.out.println("Fichero aulas escrito satisfactoriamente.");
			// Cerramos el flujo
			salida.close();
		} catch (FileNotFoundException e) {
			System.out.println("No puedo crear el fichero de aulas.");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida.");
		}
	}
}
