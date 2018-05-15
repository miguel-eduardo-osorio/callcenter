package com.miguelosorio.callcenter;

/**
 * Programa encargado de atender las llamadas que llegan en una cola de llamadas y asignarselas a
 * los agentes que están disponibles en el call center.
 * 
 * -Está compuesto por una clase que genera la cantidad de llamadas que van a ser atendidas por el 
 *  call center.
 * 
 * -Una clase dispatcher que se encarga de tomar la llamada y asignarsela a un agente disponible.
 *  En caso que no hayan agentes disponibles, el despachador pondra la llamada en una cola de espera
 *  prioritaria y la asignará a un agente cuando este se encuentre disponible.
 *  
 * @author Miguel Osorio
 *
 */
public class CallCenter {

	public static void main(String... args) throws InterruptedException {
				
		//Simulo la cantidad de llamadas que entran al call center
		new CallGenerator().start(10);
		
		//Espero un segundo para tener tiempo de crear todas las llamadas antes que empiecen a contestar
		Thread.currentThread().sleep(1000);
		
		System.out.println("Call center está disponible para atender llamadas");
		
		//Creo varias instancias de despachadores para simular la concurrencia en los despachadores
		new Dispatcher(1).dispatchCall();
		new Dispatcher(2).dispatchCall();
		new Dispatcher(3).dispatchCall();
		
	}
}
