/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aestrella;

/**
 *
 * @author Marc
 */
import java.util.Iterator;

import net.datastructures.HashTableMap;
import net.datastructures.NodePositionList;
import net.datastructures.Position;

public class Astar {

	double distanciaRecorrida;
	static Nodo destinoGlobal;
	static Nodo origenGlobal;
        static String estaciones = "";
        static String separador = " -> ";
        static double distancia = 0.0;
        static int tiempo = 0;

	public static void calcularAStar(String origen, String destino)
	{
            NodePositionList<Nodo> listaNodos = new NodePositionList<Nodo>();
            listaNodos = crearNodos(listaNodos);

            Nodo nodoOrigen = null;
            Nodo nodoDestino = null;
            for (Nodo aux : listaNodos){
                if (aux.nombre.equals(origen)){
                    nodoOrigen = aux;
                }
            }
            for (Nodo aux : listaNodos){
                if (aux.nombre.equals(destino)){
                    nodoDestino = aux;
                }
            }

            aStar (nodoOrigen, nodoDestino, listaNodos);
	}

	/**
	 * Genera el camino mínimo según el algoritmo A*, usando como heurística la distancia entre estaciones
	 * @param origen
	 * @param destino
	 */
	private static void aStar (Nodo origen, Nodo destino, NodePositionList<Nodo> listaNodos){

		HashTableMap<Nodo, Integer> listaAbierta = new HashTableMap<Nodo, Integer>();
		listaAbierta.put(origen, 0);
		HashTableMap<Nodo, Integer> listaCerrada = new HashTableMap<Nodo, Integer>();
		destinoGlobal=destino;
		origenGlobal=origen;
		origen.h = calcularH (origen, destino);
		origen.f = calcularF (origen);


		while (!listaAbierta.isEmpty()){

			double fMinima = Integer.MAX_VALUE;
			Nodo actual = null;

			for (Nodo estacionActual : listaAbierta.keySet()){
				/**
				 * Recorro la lista abierta, buscando el Nodo con f menor de todos
				 */
				if (estacionActual.f < fMinima){
					fMinima = estacionActual.f;
					actual = estacionActual;
				}
			}

			listaAbierta.remove(actual);
			listaCerrada.put(actual, 0);

			if (actual.nombre.equals(destino.nombre)){
				imprimirCamino();
				break;
			}

			else{
				for(Nodo aux: actual.estacionesVecinas){
					boolean encontrado2=false;
					for (Nodo estacionActualaux : listaCerrada.keySet()){
						if (estacionActualaux.nombre.equals(aux.nombre))					
							encontrado2=true;
					}
					if(encontrado2) //si el nodo con nombre aux.nombre esta en la lista cerrada, salta
						continue;	//a lasiguiente iteracion

					boolean encontrado=false;
					for (Nodo estacionActual : listaAbierta.keySet()){
						if (estacionActual.nombre.equals(aux.nombre))
						{
							aux=estacionActual;
							encontrado=true;
							if(aux.g>(actual.g+calcularG(actual, aux))){
								aux.g=actual.g+calcularG(actual, aux);
								aux.f=calcularF(aux);
								aux.padre=actual;
								break;
							}
						}
					}
					if (!encontrado)
					{
						Iterator<Nodo> iterAux = listaNodos.iterator();
						while (iterAux.hasNext())
						{
							Nodo auxiliar = iterAux.next();
							if ( auxiliar.nombre.equals(aux.nombre))
							{
								auxiliar.f=aux.f;
								auxiliar.g=aux.g;
								auxiliar.h=aux.h;
								auxiliar.padre=aux.padre;
								aux=auxiliar;
								break;
							}
						}
						listaAbierta.put(aux, 0); 
						aux.padre=actual;
						aux.g=actual.g+calcularG(actual, aux);
						aux.h=calcularH(aux, destino);
						aux.f=calcularF(aux);
					}
				}
			}
		}
	}

        /**
         * 
         * @param numero
         * @param numeroDecimales
         * @return 
         */
	public static double redondear(double numero,int numeroDecimales) 
	{
		long mult=(long)Math.pow(10,numeroDecimales);
		double resultado=(Math.round(numero*mult))/(double)mult;
		return resultado;
	}
        
        /**
         * 
         */
	private static void imprimirCamino() {

		Nodo actual= destinoGlobal; //Empiezas por el destino
		distancia=(actual.f)*100;
		tiempo=(int)(tiempo+distancia*3);
                distancia = redondear(distancia,2);
		NodePositionList<Nodo> camino = new NodePositionList<Nodo>(); //camino recorrido
		while(actual.padre!=null){
			if(actual.nombre.equals(origenGlobal.nombre)) //añades todos los nodos recorridos menos el
				break;									 //origen
			camino.addFirst(actual);
			actual=actual.padre;
		}
		camino.addFirst(origenGlobal);					//añades el origen en la primera posicion
		Position<Nodo> inicial=camino.first();			//empiezas a imprimir desde el origen
		while(!inicial.element().nombre.equals(destinoGlobal.nombre)){ //hasta el penúltimo
			estaciones = estaciones+inicial.element().nombre;
			estaciones = estaciones+separador;
			inicial=camino.next(inicial);				//vas avanzando en la lista de nodos recorridos
		}
		estaciones = estaciones+(inicial.element().nombre);		//imprimes el destino		//imprimes el destino
	}
	/**
	 * 
	 * @param origen
	 * @param destino
	 * @return Distancia entre dos nodos
	 */
	private static double calcularG (Nodo origen, Nodo destino){
		double p1 = Math.pow((Math.abs(origen.coordenadaX-destino.coordenadaX)), 2);
		double p2 = Math.pow((Math.abs(origen.coordenadaY-destino.coordenadaY)), 2);
		return Math.sqrt(p1 + p2);
	}

	/**
	 * 
	 * @param origen
	 * @param destino
	 * @return heuristica entre dos puntos
	 */
	private static double calcularH(Nodo origen, Nodo destino) {
		int cuadranteOrigenX = origen.cuadranteX;
		int cuadranteDestX = destino.cuadranteX;
		int cuadranteOrigenY = origen.cuadranteY;
		int cuadranteDestY = destino.cuadranteY;
		int x = (Math.abs(cuadranteDestX-cuadranteOrigenX));
		int y = (Math.abs(cuadranteDestY-cuadranteOrigenY));
		return (x*0.5 + y*0.5);
	}

	/**
	 * 
	 * @param estacion
	 * @return suma de distancia + heuristica
	 */
	private static double calcularF (Nodo estacion){
		return estacion.g + estacion.h;
	}

	/**
	 * 
	 * @param listaNodos
	 * @return NodePositionList<Nodo> con todas las estaciones de la red
	 */
	private static NodePositionList<Nodo> crearNodos (NodePositionList<Nodo> listaNodos){
			/*
		 * Alberto
		 */
		Nodo lliria= new Nodo("Lliria", 1, 1, 39.6228408813, -0.5902777910);
		Nodo benaguasil_2n = new Nodo ("Benaguasil 2n",1,1,39.5988807678,-0.5839277506);
		Nodo benaguasil_1r = new Nodo ("Benaguasil 1r",1,1,39.5927238464,-0.5780805349);
		Nodo la_pobla_de_vallbona = new Nodo ("La Pobla de Vallbona",1,1,39.5827636719,-0.5622777939);
		Nodo torre_del_virrey = new Nodo ("Torre del Virrey",1,1,39.5686645508,-0.5419166684);
		Nodo leliana = new Nodo ("L'Eliana",1,1,39.5618324280,-0.5359194279);
		Nodo montesol = new Nodo ("Montesol", 1,1,39.5555725098,-0.5316972136);
		Nodo el_clot = new Nodo("El Clot", 1,1,39.5498657227,-0.5279861093);
		Nodo entrepins = new Nodo("Entrepins", 1, 1,39.5433349609,-0.5139539838);
		Nodo la_vallesa= new Nodo("La Vallesa", 1, 1, 39.5378417969,-0.4979777634);
		Nodo la_canyada = new Nodo ("La Canyada",1,1,39.5268058777,-0.4871222079);
		Nodo fuente_del_jarro = new Nodo ("Fuente del Jarro",1,1,39.5111732483,-0.4645166695);
		Nodo santa_rita = new Nodo ("Santa Rita",1,2,39.5056571960,-0.4551055431);
		Nodo paterna = new Nodo ("Paterna",1,2,39.4988098145,-0.4419719875);
		Nodo campament = new Nodo ("Campament",1,2,39.4958457947,-0.4353230000);
		Nodo les_carolines_fira = new Nodo ("Les Carolines-Fira",1,2,39.4987373352,-0.4254194498);
		Nodo benimamet = new Nodo ("Benimamet",1,2,39.5018768311,-0.4194111228);
		Nodo cantereria = new Nodo ("Cantereria",1,2,39.5023803711,-0.4120722115);
		//
		Nodo betera = new Nodo ("Betera",2,1,39.5906066895,-0.4575305581);
		Nodo s_psiquiatric = new Nodo ("S. Psiquiatric",2,1,39.5819320679,-0.4431444407);
		Nodo masies = new Nodo ("Masies",2,1,39.5665817261,-0.4053777754);
		Nodo seminari_ceu = new Nodo ("Seminari-CEU",2,1,39.5499877930,-0.3897444308);
		Nodo montcada_alfara = new Nodo ("Montcada Alfara",2,1,39.5435295105,-0.3884611130);
		Nodo massarrojos = new Nodo ("Massarrojos",2,1,39.5366096497,-0.4028777778);
		Nodo rocafort = new Nodo ("Rocafor",2,1,39.5287094116,-0.4075777829);
		Nodo godella = new Nodo ("Godella",2,1,39.5195045471,-0.4144555628);
		Nodo burjassot_godella = new Nodo ("Burjassot-Godella",2,1,39.5135307312,-0.4119972289);
		Nodo burjassot = new Nodo ("Burjassot",2,1,39.5084152222,-0.4067361057);
		//
		Nodo empalme = new Nodo ("Empalme",2,2,39.4995765686,-0.4021083415);
		Nodo beniferri = new Nodo ("Beniferri",2,2,39.4910316467,-0.3993369341);
		Nodo campanar_la_fe = new Nodo ("Campanar-La Fe",2,2,39.4846305847,-0.3950361013);
		Nodo turia = new Nodo ("Turia",2,2,39.4788665771,-0.3912055492);
		Nodo a_guimera = new Nodo ("A. Guimera",2,3,39.4703025818,-0.3850361109);
		Nodo pl_espanya = new Nodo ("Pl. Espanya",2,3,39.4662017822,-0.3816333413);
		Nodo joaquin_sorolla = new Nodo ("Joaquin Sorolla",2,3,39.4592018127,-0.3845416605);
		Nodo patraix = new Nodo ("Patraix",2,3,39.4564514160,-0.3905083239);
		Nodo safranar = new Nodo ("Safranar",2,3,39.4545707703,-0.3983833194);
		Nodo sant_isidre = new Nodo ("Sant Isidre",2,3,39.4510345459,-0.4028027654);
		Nodo valencia_sud = new Nodo ("Valencia Sud",2,3,39.4408111572,-0.4106472135);
		Nodo paiporta = new Nodo ("Paiporta",2,3,39.4322624207,-0.4180611074);
		Nodo bailen = new Nodo ("Bailen",3,3,39.4639778137,-0.3794222176);
		Nodo torrent_avinguda = new Nodo ("Torrent Avinguda",2,4,39.4318122864,-0.4728333354);
		Nodo picanya = new Nodo ("Picanya",2,4,39.4331207275,-0.4371583462);
		Nodo torrent = new Nodo ("Torrent",2,4,39.4346466064,-0.4609861076);
		Nodo collegi_el_vedat = new Nodo ("Col·legi El Vedat",2,4,39.4231338501,-0.4606527686);
		Nodo realon = new Nodo ("Realon",2,4,39.4080123901,-0.4609111249);
		Nodo sant_ramon = new Nodo ("Sant Ramon",2,4,39.3848648071,-0.4672611058);
		Nodo picassent = new Nodo ("Picassent",2,4,39.3630371094,-0.4648583233);
		Nodo omet = new Nodo ("Omet",2,4,39.3550071716,-0.4736138880);
		Nodo espioca = new Nodo ("Espioca",2,4,39.3218841553,-0.4667472243);
		Nodo font_almaguer = new Nodo ("Font Almaguer",2,4,39.2894058228,-0.4640861154);
		Nodo alginet = new Nodo ("Alginet",2,4,39.2647552490,-0.4745583236);
		Nodo ausias_march = new Nodo ("Ausias March",2,4,39.2509994507,-0.4916388988);
		Nodo carlet = new Nodo ("Carlet",2,4,39.2267990112,-0.5249416828);
		Nodo benimodo = new Nodo ("Benimodo",2,5,39.2162933350,-0.5196194649);
		Nodo lalcudia = new Nodo ("L'Alcudia",2,5,39.1938133240,-0.5102638602);
		Nodo montortal = new Nodo ("Montortal",2,5,39.1737976074,-0.5158861279);
		Nodo massalaves = new Nodo ("Massalaves",2,5,39.1438064575,-0.5187610984);
		Nodo alberic = new Nodo ("Alberic",2,5,39.1167755127,-0.5213361382);
		Nodo villanueva_de_castellon = new Nodo ("Villanueva de Castellon",2,5,39.0840072632,-0.5160138607);
		/*
		 * Marc
		 */
		Nodo aeroport = new Nodo("Aeroport", 1, 3, 39.4923667908, -0.4749194384);
		Nodo rosas = new Nodo("Rosas", 1, 3,39.4926490784,-0.4672361016);
		Nodo manises = new Nodo("Manises", 1, 3, 39.4895896912, -0.4590654373);
		Nodo salt_de_laigua = new Nodo("Salt de l'Aigua", 1, 3, 39.4848327637,-0.4505681992);
		Nodo quart_de_poblet = new Nodo("Quart de Poblet", 1, 3, 39.4810867310, -0.4418805540);
		Nodo faitanar = new Nodo("Faitanar", 2, 3, 39.4776191711, -0.4331833422);
		Nodo mislata_almassil = new Nodo("Mislata-Almassil", 2, 3, 39.4760093689, -0.4243694544);
		Nodo mislata = new Nodo("Mislata", 2, 3, 39.4738235474, -0.4183055460);
		Nodo nou_doctubre = new Nodo("Nou d'Octubre", 2, 3, 39.4706573486, -0.4076305628);
		Nodo av_del_cid = new Nodo("Av. del Cid", 2, 3, 39.4682197571, -0.3975749910);
		Nodo xativa = new Nodo("Xativa", 3, 3, 39.4671859741, -0.3773750067);
		Nodo colon = new Nodo("Colon", 3, 3, 39.4701461792, -0.3709277809);
		Nodo alameda = new Nodo("Alameda", 3, 3, 39.4731559753, -0.3653166592);
		Nodo aragon = new Nodo("Aragon", 3, 3, 39.4726257324, -0.3581166565);
		Nodo amistat = new Nodo("Amistat", 3, 3, 39.4703330994, -0.3503944576);
		Nodo ayora = new Nodo("Ayora", 3, 3, 39.4664268494, 0.3429694474);
		Nodo maritim_serreria = new Nodo("Maritim-Serreria", 4, 3, 39.4649391174, -0.3382369876);
		Nodo francesc_cubells = new Nodo("Francesc Cubells", 4, 3, 39.4632453918, -0.3339729905);
		Nodo grau_canyamelar = new Nodo("Grau-Canyamelar", 4, 3, 39.4631004333, -0.3294720054);
		Nodo neptu = new Nodo("Neptu", 4, 3, 39.4632530212, -0.3258508444);
		Nodo mediterrani = new Nodo("Mediterrani", 4, 3, 39.4673843384,-0.3273027837);
		Nodo facultats = new Nodo("Facultats", 3, 2, 39.4780044556, -0.3619055450);
		/*
		 * Patricio
		 */
		Nodo mas_del_rosari= new Nodo ("Mas Del Rosari",1,1,39.5249595642,-0.4358249903);
		Nodo la_coma = new Nodo ("La Coma", 1,1, 39.5215721130, -0.4317069948);
		Nodo tomas_y_valiente = new Nodo ("Tomas Y Valiente",1,1,39.5197715759,-0.4256361127);
		Nodo santa_gemma = new Nodo ("Santa Gemma Parc Cientific UV", 1,1, 39.5151405334, -0.4226219952);
		Nodo ll_llarga = new Nodo ("Ll. Llarga Terramelar",1,1,39.5098991394,-0.4304472208);
		Nodo tvv = new Nodo ("TVV",1,1,39.5122032166,-0.4247489870);
		Nodo fira_valencia = new Nodo ("Fira Valencia",1,2,39.5041427612,-0.4254944324);
		Nodo v_andres = new Nodo ("V. Andre E.",1,2,39.5085601807,-0.4198839962);
		Nodo campus = new Nodo ("Campus",1,2,39.5072212219,-0.4174583256);
		Nodo sant_joan = new Nodo ("Sant Joan",1,2,39.5052909851,-0.4163239896);
		Nodo la_granja = new Nodo ("La Granja",1,2,39.5040321350, -0.4124779999);
		Nodo palau_de_congressos = new Nodo ("Palau de Congressos",2,2,39.4971809387,-0.4001416564);
		Nodo florista = new Nodo ("Florista",2,2,39.4944343567, -0.3968166709);
		Nodo garbi = new Nodo ("Garbi",2,2,39.4922904968,-0.3945111036);
		Nodo benicalap = new Nodo ("Benicalap",2,2,39.4900283813, -0.3909277916);
		Nodo transits = new Nodo ("Transits",2,2,39.4895629883,-0.3872583210);
		Nodo marxalenes = new Nodo ("Marxalenes",3,2,39.4885406494, -0.3844027817);
		Nodo reus = new Nodo ("Reus",3,2,39.4861793518, -0.3791472316);
		Nodo sagunt = new Nodo ("Sagunt",3,2,39.4864997864,-0.3749722242);
		Nodo pont_de_fusta = new Nodo ("Pont de Fusta",3,2,39.4817810059, -0.3731805682);
		Nodo dr_lluch = new Nodo ("Dr. Lluch",4,2,39.4693069458, -0.3281527758);
		Nodo les_arenes = new Nodo ("Les Arenes",4,2,39.4689292908,-0.3257277906);
		Nodo eugenia_vinel = new Nodo ("Eugenio Viñel",4,2,39.4736900330, -0.3257277906);
		Nodo la_cadena = new Nodo ("La Cadena",4,2,39.4752044678, -0.3293749988);
		/*
		 * Carlos
		 */
		Nodo rafaelbunyol = new Nodo ("Rafelbunyol",3,1,39.5885238647,-0.3310583234);
		Nodo la_pobla_de_farnals = new Nodo ("La Pobla de Farnals",3,1,39.5794181824,-0.3304361105);
		Nodo massamagrell = new Nodo ("Massamagrell",3,1,39.5705604553,-0.3330333233);
		Nodo museros = new Nodo ("Museros",3,1,39.5615768433,-0.3408555686);
		Nodo albalat_dels_sorells = new Nodo ("Albalat dels Sorells",3,1,39.5452651978,-0.3482888937);
		Nodo foios = new Nodo ("Foios",3,1,39.5372238159,-0.3538694382);
		Nodo meliana = new Nodo ("Meliana",3,1,39.5280303955,-0.3518194556);
		Nodo almassera = new Nodo ("Almassera",3,1,39.5122566223,-0.3542666733);
		Nodo alboraya_peris = new Nodo ("Alboraya-Peris Arago",3,2,39.5005912781,-0.3525277674);
		Nodo alboraya_palmaret = new Nodo ("Alboraya-Palmaret",3,2,39.4957351685,-0.3553166687);
		Nodo machado = new Nodo ("Machado",3,2,39.4924316406,-0.3587944508);
		Nodo benimaclet = new Nodo ("Benimaclet",3,2,39.4848518372,-0.3623333275);
		Nodo tossal_del_rei = new Nodo ("Tossal del Rei",3,2,39.4959526062,-0.3725369871);
		Nodo sant_miquel_dels_reis = new Nodo ("Sant Miquel dels Reis",3,2,39.4972190857,-0.3684949875);
		Nodo estadi_del_llevant = new Nodo ("Estadi del Llevant",3,2,39.4949188232,-0.3655419946);
		Nodo orriols = new Nodo ("Orriols",3,2,39.4931488037,-0.3676636219);
		Nodo alfauir = new Nodo ("Alfauir",3,2,39.4892997742,-0.3660329878);
		Nodo primat_reig = new Nodo ("Primat Reig",3,2,39.4862709045,-0.3677638769);
		Nodo v_zaragoza = new Nodo ("V. Zaragoza",3,2,39.4833717346,-0.3579472303);
		Nodo universitat_politecnica = new Nodo ("Universitat Politecnica",4,2,39.4813156128,-0.3504999876);
		Nodo la_carrasca = new Nodo ("La Carrasca",4,2,39.4796600342,-0.3448249996);
		Nodo tarongers = new Nodo ("Tarongers",4,2,39.4781379700,-0.3396222293);
		Nodo serreria = new Nodo ("Serreria",4,2,39.4765930176,-0.3342027664);
		Nodo la_marina = new Nodo ("La Marina",4,2,39.4728546143,-0.3275833428);

		/*
		 * Alberto
		 */
		lliria= new Nodo("Lliria", 1, 1, 39.6228408813, -0.5902777910, benaguasil_2n);
		benaguasil_2n = new Nodo ("Benaguasil 2n",1,1,39.5988807678,-0.5839277506, lliria, benaguasil_1r);
		benaguasil_1r = new Nodo ("Benaguasil 1r",1,1,39.5927238464,-0.5780805349, benaguasil_2n, la_pobla_de_vallbona);
		la_pobla_de_vallbona = new Nodo ("La Pobla de Vallbona",1,1,39.5827636719,-0.5622777939, benaguasil_1r, torre_del_virrey);
		torre_del_virrey = new Nodo ("Torre del Virrey",1,1,39.5686645508,-0.5419166684, la_pobla_de_vallbona, leliana);
		leliana = new Nodo ("L'Eliana",1,1,39.5618324280,-0.5359194279, torre_del_virrey, montesol);
		montesol = new Nodo ("Montesol", 1,1,39.5555725098,-0.5316972136, el_clot, leliana);
		el_clot = new Nodo("El Clot", 1,1,39.5498657227,-0.5279861093, montesol, entrepins);
		entrepins = new Nodo("Entrepins", 1, 1,39.5433349609,-0.5139539838, el_clot, la_vallesa);
		la_vallesa= new Nodo("La Vallesa", 1, 1, 39.5378417969,-0.4979777634, entrepins, la_canyada);
		la_canyada = new Nodo ("La Canyada",1,1,39.5268058777,-0.4871222079, la_vallesa, fuente_del_jarro);
		fuente_del_jarro = new Nodo ("Fuente del Jarro",1,1,39.5111732483,-0.4645166695, la_canyada, santa_rita);
		santa_rita = new Nodo ("Santa Rita",1,2,39.5056571960,-0.4551055431, fuente_del_jarro, paterna);
		paterna = new Nodo ("Paterna",1,2,39.4988098145,-0.4419719875, santa_rita, campament);
		campament = new Nodo ("Campament",1,2,39.4958457947,-0.4353230000, paterna, les_carolines_fira);
		les_carolines_fira = new Nodo ("Les Carolines-Fira",1,2,39.4987373352,-0.4254194498, campament, benimamet);
		benimamet = new Nodo ("Benimamet",1,2,39.5018768311,-0.4194111228, les_carolines_fira, cantereria);
		cantereria = new Nodo ("Cantereria",1,2,39.5023803711,-0.4120722115, benimamet, empalme);
		betera = new Nodo ("Betera",2,1,39.5906066895,-0.4575305581, s_psiquiatric);
		s_psiquiatric = new Nodo ("S. Psiquiatric",2,1,39.5819320679,-0.4431444407, betera, masies);
		masies = new Nodo ("Masies",2,1,39.5665817261,-0.4053777754, s_psiquiatric, seminari_ceu);
		seminari_ceu = new Nodo ("Seminari-CEU",2,1,39.5499877930,-0.3897444308, masies, montcada_alfara);
		montcada_alfara = new Nodo ("Montcada Alfara",2,1,39.5435295105,-0.3884611130, seminari_ceu, massarrojos);
		massarrojos = new Nodo ("Massarrojos",2,1,39.5366096497,-0.4028777778, montcada_alfara, rocafort);
		rocafort = new Nodo ("Rocafor",2,1,39.5287094116,-0.4075777829, massarrojos, godella);
		godella = new Nodo ("Godella",2,1,39.5195045471,-0.4144555628, rocafort, burjassot_godella);
		burjassot_godella = new Nodo ("Burjassot-Godella",2,1,39.5135307312,-0.4119972289, godella, burjassot);
		burjassot = new Nodo ("Burjassot",2,1,39.5084152222,-0.4067361057, burjassot_godella, empalme);
		empalme = new Nodo ("Empalme",2,2,39.4995765686,-0.4021083415, cantereria, la_granja, burjassot, palau_de_congressos, beniferri);
		beniferri = new Nodo ("Beniferri",2,2,39.4910316467,-0.3993369341, empalme, campanar_la_fe);
		campanar_la_fe = new Nodo ("Campanar-La Fe",2,2,39.4846305847,-0.3950361013, beniferri, turia);
		turia = new Nodo ("Turia",2,2,39.4788665771,-0.3912055492, campanar_la_fe, a_guimera);
		a_guimera = new Nodo ("A. Guimera",2,3,39.4703025818,-0.3850361109, turia, joaquin_sorolla, xativa, av_del_cid, pl_espanya);
		pl_espanya = new Nodo ("Pl. Espanya",2,3,39.4662017822,-0.3816333413, a_guimera, joaquin_sorolla);
		joaquin_sorolla = new Nodo ("Joaquin Sorolla",2,3,39.4592018127,-0.3845416605, pl_espanya, patraix, bailen);
		patraix = new Nodo ("Patraix",2,3,39.4564514160,-0.3905083239, joaquin_sorolla, safranar);
		safranar = new Nodo ("Safranar",2,3,39.4545707703,-0.3983833194, patraix, sant_isidre);
		sant_isidre = new Nodo ("Sant Isidre",2,3,39.4510345459,-0.4028027654, safranar, valencia_sud);
		valencia_sud = new Nodo ("Valencia Sud",2,3,39.4408111572,-0.4106472135, sant_isidre, paiporta);
		paiporta = new Nodo ("Paiporta",2,3,39.4322624207,-0.4180611074, valencia_sud, picanya);
		bailen = new Nodo ("Bailen",3,3,39.4639778137,-0.3794222176, pl_espanya, colon);
		torrent_avinguda = new Nodo ("Torrent Avinguda",2,4,39.4318122864,-0.4728333354, torrent);
		picanya = new Nodo ("Picanya",2,4,39.4331207275,-0.4371583462, torrent, paiporta);
		torrent = new Nodo ("Torrent",2,4,39.4346466064,-0.4609861076, torrent_avinguda, picanya, collegi_el_vedat);
		collegi_el_vedat = new Nodo ("Col·legi El Vedat",2,4,39.4231338501,-0.4606527686, torrent, realon);
		realon = new Nodo ("Realon",2,4,39.4080123901,-0.4609111249, collegi_el_vedat, sant_ramon);
		sant_ramon = new Nodo ("Sant Ramon",2,4,39.3848648071,-0.4672611058, realon, picassent);
		picassent = new Nodo ("Picassent",2,4,39.3630371094,-0.4648583233, sant_ramon, omet);
		omet = new Nodo ("Omet",2,4,39.3550071716,-0.4736138880, picassent, espioca);
		espioca = new Nodo ("Espioca",2,4,39.3218841553,-0.4667472243, omet, font_almaguer);
		font_almaguer = new Nodo ("Font Almaguer",2,4,39.2894058228,-0.4640861154, espioca, alginet);
		alginet = new Nodo ("Alginet",2,4,39.2647552490,-0.4745583236, font_almaguer, ausias_march);
		ausias_march = new Nodo ("Ausias March",2,4,39.2509994507,-0.4916388988, alginet, carlet);
		carlet = new Nodo ("Carlet",2,4,39.2267990112,-0.5249416828, ausias_march, benimodo);
		benimodo = new Nodo ("Benimodo",2,5,39.2162933350,-0.5196194649, carlet, lalcudia);
		lalcudia = new Nodo ("L'Alcudia",2,5,39.1938133240,-0.5102638602, benimodo, montortal);
		montortal = new Nodo ("Montortal",2,5,39.1737976074,-0.5158861279, lalcudia, massalaves);
		massalaves = new Nodo ("Massalaves",2,5,39.1438064575,-0.5187610984, montortal, alberic);
		alberic = new Nodo ("Alberic",2,5,39.1167755127,-0.5213361382, massalaves, villanueva_de_castellon);
		villanueva_de_castellon = new Nodo ("Villanueva de Castellon",2,5,39.0840072632,-0.5160138607, alberic);
		/*
		 * Marc
		 */
		aeroport = new Nodo("Aeroport", 1, 3, 39.4923667908, -0.4749194384, rosas);
		rosas = new Nodo("Rosas", 1, 3,39.4926490784,-0.4672361016, aeroport, manises);
		manises = new Nodo("Manises", 1, 3, 39.4895896912, -0.4590654373, rosas, salt_de_laigua);
		salt_de_laigua = new Nodo("Salt de l'Aigua", 1, 3, 39.4848327637,-0.4505681992, manises, quart_de_poblet);
		quart_de_poblet = new Nodo("Quart de Poblet", 1, 3, 39.4810867310, -0.4418805540, salt_de_laigua, faitanar);
		faitanar = new Nodo("Faitanar", 2, 3, 39.4776191711, -0.4331833422, quart_de_poblet, mislata_almassil);
		mislata_almassil = new Nodo("Mislata-Almassil", 2, 3, 39.4760093689, -0.4243694544, faitanar, mislata);
		mislata = new Nodo("Mislata", 2, 3, 39.4738235474, -0.4183055460, mislata_almassil, nou_doctubre);
		nou_doctubre = new Nodo("Nou d'Octubre", 2, 3, 39.4706573486, -0.4076305628, mislata, av_del_cid);
		av_del_cid = new Nodo("Av. del Cid", 2, 3, 39.4682197571, -0.3975749910, nou_doctubre, a_guimera);
		xativa = new Nodo("Xativa", 3, 3, 39.4671859741, -0.3773750067,a_guimera, colon);
		colon = new Nodo("Colon", 3, 3, 39.4701461792, -0.3709277809, xativa, alameda, bailen);
		alameda = new Nodo("Alameda", 3, 3, 39.4731559753, -0.3653166592, colon, facultats, aragon);
		aragon = new Nodo("Aragon", 3, 3, 39.4726257324, -0.3581166565, alameda, amistat);
		amistat = new Nodo("Amistat", 3, 3, 39.4703330994, -0.3503944576, aragon, ayora);
		ayora = new Nodo("Ayora", 3, 3, 39.4664268494, 0.3429694474, amistat, maritim_serreria);
		maritim_serreria = new Nodo("Maritim-Serreria", 4, 3, 39.4649391174, -0.3382369876, ayora, francesc_cubells);
		francesc_cubells = new Nodo("Francesc Cubells", 4, 3, 39.4632453918, -0.3339729905, maritim_serreria, grau_canyamelar);
		grau_canyamelar = new Nodo("Grau-Canyamelar", 4, 3, 39.4631004333, -0.3294720054, francesc_cubells, mediterrani, neptu);
		neptu = new Nodo("Neptu", 4, 3, 39.4632530212, -0.3258508444, grau_canyamelar);
		mediterrani = new Nodo("Mediterrani", 4, 3, 39.4673843384,-0.3273027837, grau_canyamelar, dr_lluch, les_arenes);
		facultats = new Nodo("Facultats", 3, 2, 39.4780044556, -0.3619055450, v_zaragoza, alameda);
		/*
		 * Patricio
		 */
		mas_del_rosari= new Nodo ("Mas Del Rosari",1,1,39.5249595642,-0.4358249903, la_coma);
		la_coma = new Nodo ("La Coma", 1,1, 39.5215721130, -0.4317069948,tomas_y_valiente,mas_del_rosari);
		tomas_y_valiente = new Nodo ("Tomas Y Valiente",1,1,39.5197715759,-0.4256361127,santa_gemma,la_coma);
		santa_gemma = new Nodo ("Santa Gemma Parc Cientific UV", 1,1, 39.5151405334, -0.4226219952,ll_llarga,tvv,tomas_y_valiente);
		ll_llarga = new Nodo ("Ll. Llarga Terramelar",1,1,39.5098991394,-0.4304472208,santa_gemma,tvv);
		tvv = new Nodo ("TVV",1,1,39.5122032166,-0.4247489870,santa_gemma,ll_llarga,fira_valencia,v_andres);
		fira_valencia = new Nodo ("Fira Valencia",1,2,39.5041427612,-0.4254944324,tvv,v_andres);
		v_andres = new Nodo ("V. Andre E.",1,2,39.5085601807,-0.4198839962,tvv,fira_valencia,campus);
		campus = new Nodo ("Campus",1,2,39.5072212219,-0.4174583256,v_andres,sant_joan);
		sant_joan = new Nodo ("Sant Joan",1,2,39.5052909851,-0.4163239896,campus,la_granja);
		la_granja = new Nodo ("La Granja",1,2,39.5040321350, -0.4124779999,sant_joan,empalme);
		palau_de_congressos = new Nodo ("Palau de Congressos",2,2,39.4971809387,-0.4001416564,empalme,florista);
		florista = new Nodo ("Florista",2,2,39.4944343567, -0.3968166709,palau_de_congressos,garbi);
		garbi = new Nodo ("Garbi",2,2,39.4922904968,-0.3945111036,florista,benicalap);
		benicalap = new Nodo ("Benicalap",2,2,39.4900283813, -0.3909277916,garbi,transits);
		transits = new Nodo ("Transits",2,2,39.4895629883,-0.3872583210,benicalap,marxalenes);
		marxalenes = new Nodo ("Marxalenes",3,2,39.4885406494, -0.3844027817,transits,reus);
		reus = new Nodo ("Reus",3,2,39.4861793518, -0.3791472316,marxalenes,sagunt);
		sagunt = new Nodo ("Sagunt",3,2,39.4864997864,-0.3749722242,reus, pont_de_fusta);
		pont_de_fusta = new Nodo ("Pont de Fusta",3,2,39.4817810059, -0.3731805682,sagunt, primat_reig);
		dr_lluch = new Nodo ("Dr. Lluch",4,2,39.4693069458, -0.3281527758, la_marina);
		les_arenes = new Nodo ("Les Arenes",4,2,39.4689292908,-0.3257277906,dr_lluch,mediterrani);
		eugenia_vinel = new Nodo ("Eugenio Viñel",4,2,39.4736900330, -0.3257277906,les_arenes);
		la_cadena = new Nodo ("La Cadena",4,2,39.4752044678, -0.3293749988,eugenia_vinel,serreria);
		/*
		 * Carlos
		 */
		rafaelbunyol = new Nodo ("Rafelbunyol",3,1,39.5885238647,-0.3310583234,la_pobla_de_farnals);
		la_pobla_de_farnals = new Nodo ("La Pobla de Farnals",3,1,39.5794181824,-0.3304361105,rafaelbunyol,massamagrell);
		massamagrell = new Nodo ("Massamagrell",3,1,39.5705604553,-0.3330333233,la_pobla_de_farnals,museros);
		museros = new Nodo ("Museros",3,1,39.5615768433,-0.3408555686,massamagrell,albalat_dels_sorells);
		albalat_dels_sorells = new Nodo ("Albalat dels Sorells",3,1,39.5452651978,-0.3482888937,museros,foios);
		foios = new Nodo ("Foios",3,1,39.5372238159,-0.3538694382,albalat_dels_sorells,meliana);
		meliana = new Nodo ("Meliana",3,1,39.5280303955,-0.3518194556,foios,almassera);
		almassera = new Nodo ("Almassera",3,1,39.5122566223,-0.3542666733,meliana,alboraya_peris);
		alboraya_peris = new Nodo ("Alboraya-Peris Arago",3,2,39.5005912781,-0.3525277674,almassera,alboraya_palmaret);
		alboraya_palmaret = new Nodo ("Alboraya-Palmaret",3,2,39.4957351685,-0.3553166687,alboraya_peris,machado);
		machado = new Nodo ("Machado",3,2,39.4924316406,-0.3587944508,alboraya_palmaret,benimaclet);
		benimaclet = new Nodo ("Benimaclet",3,2,39.4848518372,-0.3623333275,machado,primat_reig,v_zaragoza,facultats);
		tossal_del_rei = new Nodo ("Tossal del Rei",3,2,39.4959526062,-0.3725369871,sant_miquel_dels_reis);
		sant_miquel_dels_reis = new Nodo ("Sant Miquel dels Reis",3,2,39.4972190857,-0.3684949875,tossal_del_rei,estadi_del_llevant);
		estadi_del_llevant = new Nodo ("Estadi del Llevant",3,2,39.4949188232,-0.3655419946,sant_miquel_dels_reis,orriols);
		orriols = new Nodo ("Orriols",3,2,39.4931488037,-0.3676636219,estadi_del_llevant,alfauir);
		alfauir = new Nodo ("Alfauir",3,2,39.4892997742,-0.3660329878,orriols,primat_reig);
		primat_reig = new Nodo ("Primat Reig",3,2,39.4862709045,-0.3677638769,alfauir,benimaclet,pont_de_fusta);
		v_zaragoza = new Nodo ("V. Zaragoza",3,2,39.4833717346,-0.3579472303,benimaclet,universitat_politecnica);
		universitat_politecnica = new Nodo ("Universitat Politecnica",4,2,39.4813156128,-0.3504999876,v_zaragoza,la_carrasca);
		la_carrasca = new Nodo ("La Carrasca",4,2,39.4796600342,-0.3448249996,universitat_politecnica,tarongers);
		tarongers = new Nodo ("Tarongers",4,2,39.4781379700,-0.3396222293,la_carrasca,serreria);
		serreria = new Nodo ("Serreria",4,2,39.4765930176,-0.3342027664,tarongers,la_cadena);
		la_marina = new Nodo ("La Marina",4,2,39.4728546143,-0.3275833428,la_cadena);


		/*
		 * Alberto
		 */
		listaNodos.addLast(lliria);
		listaNodos.addLast(benaguasil_2n);
		listaNodos.addLast(benaguasil_1r);
		listaNodos.addLast(la_pobla_de_vallbona);
		listaNodos.addLast(torre_del_virrey);
		listaNodos.addLast(leliana);
		listaNodos.addLast(montesol);
		listaNodos.addLast(el_clot);
		listaNodos.addLast(entrepins);
		listaNodos.addLast(la_vallesa);
		listaNodos.addLast(la_canyada);
		listaNodos.addLast(fuente_del_jarro);
		listaNodos.addLast(santa_rita);
		listaNodos.addLast(paterna);
		listaNodos.addLast(campament);
		listaNodos.addLast(les_carolines_fira);
		listaNodos.addLast(benimamet);
		listaNodos.addLast(cantereria);
		//
		listaNodos.addLast(betera);
		listaNodos.addLast(s_psiquiatric);
		listaNodos.addLast(masies);
		listaNodos.addLast(seminari_ceu);
		listaNodos.addLast(montcada_alfara);
		listaNodos.addLast(massarrojos);
		listaNodos.addLast(rocafort);
		listaNodos.addLast(godella);
		listaNodos.addLast(burjassot_godella);
		listaNodos.addLast(burjassot);
		//
		listaNodos.addLast(empalme);
		listaNodos.addLast(beniferri);
		listaNodos.addLast(campanar_la_fe);
		listaNodos.addLast(turia);
		listaNodos.addLast(a_guimera);
		listaNodos.addLast(pl_espanya);
		listaNodos.addLast(joaquin_sorolla);
		listaNodos.addLast(patraix);
		listaNodos.addLast(safranar);
		listaNodos.addLast(sant_isidre);
		listaNodos.addLast(valencia_sud);
		listaNodos.addLast(paiporta);
		listaNodos.addLast(bailen);
		listaNodos.addLast(torrent_avinguda);
		listaNodos.addLast(picanya);
		listaNodos.addLast(torrent);
		listaNodos.addLast(collegi_el_vedat);
                listaNodos.addLast(realon);
		listaNodos.addLast(sant_ramon);
		listaNodos.addLast(picassent);
		listaNodos.addLast(omet);
		listaNodos.addLast(espioca);
		listaNodos.addLast(font_almaguer);
		listaNodos.addLast(alginet);
		listaNodos.addLast(ausias_march);
		listaNodos.addLast(carlet);
		listaNodos.addLast(benimodo);
		listaNodos.addLast(lalcudia);
		listaNodos.addLast(montortal);
		listaNodos.addLast(massalaves);
		listaNodos.addLast(alberic);
		listaNodos.addLast(villanueva_de_castellon);
		/*
		 * Marc
		 */
		listaNodos.addLast(facultats);
		listaNodos.addLast(mediterrani);
		listaNodos.addLast(neptu);
		listaNodos.addLast(grau_canyamelar);
		listaNodos.addLast(francesc_cubells);
		listaNodos.addLast(maritim_serreria);
		listaNodos.addLast(ayora);
		listaNodos.addLast(amistat);
		listaNodos.addLast(aragon);
		listaNodos.addLast(alameda);
		listaNodos.addLast(colon);
		listaNodos.addLast(xativa);
		listaNodos.addLast(av_del_cid);
		listaNodos.addLast(nou_doctubre);
		listaNodos.addLast(mislata);
		listaNodos.addLast(mislata_almassil);
		listaNodos.addLast(faitanar);
		listaNodos.addLast(quart_de_poblet);
		listaNodos.addLast(salt_de_laigua);
		listaNodos.addLast(manises);
		listaNodos.addLast(rosas);
		listaNodos.addLast(aeroport);
		/*
		 * Patricio
		 */
		listaNodos.addLast(mas_del_rosari);
		listaNodos.addLast(la_coma);
		listaNodos.addLast(tomas_y_valiente);
		listaNodos.addLast(santa_gemma);
		listaNodos.addLast(ll_llarga);
		listaNodos.addLast(tvv);
		listaNodos.addLast(fira_valencia);
		listaNodos.addLast(v_andres);
		listaNodos.addLast(campus);
		listaNodos.addLast(sant_joan);
		listaNodos.addLast(la_granja);
		listaNodos.addLast(palau_de_congressos);
		listaNodos.addLast(florista);
		listaNodos.addLast(garbi);
		listaNodos.addLast(benicalap);
		listaNodos.addLast(transits);
		listaNodos.addLast(marxalenes);
		listaNodos.addLast(reus);
		listaNodos.addLast(sagunt);
		listaNodos.addLast(pont_de_fusta);
		listaNodos.addLast(dr_lluch);
		listaNodos.addLast(les_arenes);
		listaNodos.addLast(eugenia_vinel);
		listaNodos.addLast(la_cadena);
		/*
		 * Carlos
		 */
		listaNodos.addLast(rafaelbunyol);
		listaNodos.addLast(la_pobla_de_farnals);
		listaNodos.addLast(massamagrell);
		listaNodos.addLast(museros);
		listaNodos.addLast(albalat_dels_sorells);
		listaNodos.addLast(foios);
		listaNodos.addLast(meliana);
		listaNodos.addLast(almassera);
		listaNodos.addLast(alboraya_peris);
		listaNodos.addLast(alboraya_palmaret);
		listaNodos.addLast(machado);
		listaNodos.addLast(benimaclet);
		listaNodos.addLast(tossal_del_rei);
		listaNodos.addLast(sant_miquel_dels_reis);
		listaNodos.addLast(estadi_del_llevant);
		listaNodos.addLast(orriols);
		listaNodos.addLast(alfauir);
		listaNodos.addLast(primat_reig);
		listaNodos.addLast(v_zaragoza);
		listaNodos.addLast(universitat_politecnica);
		listaNodos.addLast(la_carrasca);
		listaNodos.addLast(tarongers);
		listaNodos.addLast(serreria);
		listaNodos.addLast(la_marina);

		return listaNodos;
	}
}
