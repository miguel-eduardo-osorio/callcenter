package com.miguelosorio.callcenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

public class CallQueue {

	private static CallQueue instance;

	private int counter;

	private SimpleDateFormat formatter;

	private LinkedBlockingQueue<Call> queue;
	
	private LinkedBlockingQueue<Call> holdedCall;
	
	/**
	 * Método encargado se encolar las llamadas.
	 * Debe ser estático para poder estar disponible de manera concurrente
	 * @param duration duración de la llamada
	 */
	public static void queueCall(int duration) {
		try {
			Call call = new Call(getInstance().counter++, duration);
			log("Encolando llamada número " + call.getNumber() + " con una duración de " + call.getDuration() + " segundos.");
			getInstance().queue.put(call);
		} catch (InterruptedException e) {
			log("Hubo un error encolando la llamada.");
		}
	}

	/**
	 * Método para obtener la llamada de la cola
	 * @return Una llamada
	 */
	public static Call retrieveCall() {
		Call call = getInstance().queue.poll();
		if (call != null) {
			log("Obteniendo llamada número " + call.getNumber());
		}
		return call;
	}
	
	/**
	 * Método encargado de encolar las llamadas que no pueden ser atendidas por los agentes.
	 * Cuando los agentes no están disponibles, se colocan en una cola de espera que será 
	 * atendida cuando un agente esté libre 
	 * @param call, la llamada que no se pudo atender.
	 */
	public static void queueUnattendedCall(Call call) {
		try {
			if (call != null) {
				log("Encolando llamada en espera número " + call.getNumber() + " con una duración de " + call.getDuration() + " segundos.");
				getInstance().holdedCall.put(call);
			}
		} catch (InterruptedException e) {
			log("Hubo un error encolando la llamada.");
		}
	}
	
	/**
	 * Retorna las llamadas que no han podido ser atendidas cuando los agentes no están disponibles.
	 * @return llamada en espera
	 */
	public static Call retrieveHoldedCall() {
		Call call = getInstance().holdedCall.poll();
		if (call != null) {
			log("Obteniendo llamada en espera número " + call.getNumber());
		}
		return call;
	}

	/**
	 * Obtenemos la instancia de la cola.
	 * La instancia de la cola se maneja con un singleton para evitar llenar la memoria con
	 * muchas instancias vacías
	 * @return La instancia de la cola
	 */
	private static CallQueue getInstance() {
		if (instance == null) {
			instance = new CallQueue();
		}
		return instance;
	}

	/**
	 * Método de loggeo usado para mostrar lo que pasa en la cola
	 * @param s, String con lo que se desea mostrar
	 */
	private static void log(String s) {
		System.out.println("[" + getInstance().formatter.format(new Date()) + "][CallQueue] " + s);
	}

	/**
	 * Constructor
	 */
	private CallQueue() {
		this.queue = new LinkedBlockingQueue<Call>();
		this.holdedCall = new LinkedBlockingQueue<Call>();
		this.counter = 1;
		this.formatter = new SimpleDateFormat("HH:mm:ss");
	}
}
