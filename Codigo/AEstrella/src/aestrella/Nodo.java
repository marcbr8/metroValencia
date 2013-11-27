/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aestrella;

/**
 *
 * @author Marc
 */
import net.datastructures.NodePositionList;


public class Nodo {

	String nombre;
	double g;
	double h;
	double f;
	int cuadranteX;
	int cuadranteY;
	double coordenadaX;
	double coordenadaY;
	Nodo padre;
	NodePositionList<Nodo> estacionesVecinas;

	public Nodo (String nombre, Nodo padre, int cuadranteX, int cuadranteY, double coordenadaX, double coordenadaY){
		this.nombre=nombre;
		g = 0.0;
		h = 0.0;
		f = 0.0;
		this.cuadranteX = cuadranteX;
		this.cuadranteY = cuadranteY;
		this.coordenadaX = coordenadaX;
		this.coordenadaY = coordenadaY;
		this.padre=padre;
	}

	public Nodo (String nombre, int cuadranteX, int cuadranteY, double coordenadaX, double coordenadaY, Nodo ... vecinos){
		this.nombre=nombre;
		g = 0.0;
		h = 0.0;
		f = 0.0;
		this.cuadranteX = cuadranteX;
		this.cuadranteY = cuadranteY;
		this.coordenadaX = coordenadaX;
		this.coordenadaY = coordenadaY;
		estacionesVecinas = new NodePositionList<Nodo>();
		for (Nodo actual : vecinos){
			estacionesVecinas.addFirst(actual);
		}
	}

}

