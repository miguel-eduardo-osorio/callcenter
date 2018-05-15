package com.miguelosorio.callcenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Clase que obtiene la llamada y se la asigna a un agente disponible.
 * En caso que los agentes no estén disponibles se enviará a una cola de espera
 * hasta que algún agente esté disponible
 * 
 * @author Miguel Osorio
 *
 */
public class Dispatcher implements Runnable {

	private long callExpiration;

	private SimpleDateFormat formatter;

	private int id;

	private boolean running;

	private LinkedBlockingQueue<Agent> queue;
	
	public Dispatcher() {}

	/**
	 * Constructor del despachador encargado de manejar las llamadas y los agentes.
	 * Se encarga de pre-cargar la cola de agentes disponibles
	 * @param id del despachador
	 * @throws InterruptedException
	 */
	public Dispatcher(int id) throws InterruptedException {
		this.id = id;
		formatter = new SimpleDateFormat("HH:mm:ss");
		this.queue = new LinkedBlockingQueue<Agent>();
		queue.put(new Agent("operador"));
		queue.put(new Agent("supervisor"));
		queue.put(new Agent("director"));
	}

	/**
	 * Método encargado de obtener las llamadas y asignarselas a un agente.
	 * En caso que no hayan agentes disponibles la llamada se colocará en una cola
	 * de prioridad para ser atendida.
	 */
	public void run() {
		while (running) {
			Agent agent = queue.poll();
			Call call = getCall();
			try {
				if (agent.getStatus() == AgentStatus.FREE) {
					if (call != null) {
						log(agent.getName() + " Contestando llamada número " + call.getNumber());
						callExpiration = System.currentTimeMillis() + (call.getDuration() * 1000);
						agent.setStatus(AgentStatus.IN_A_CALL);
						agent.setCallTime(callExpiration);
					} else {
						queue.put(agent);
						continue;
					}
				} else {
					if (System.currentTimeMillis() > callExpiration) {
						log(agent.getName() + " colgando llamada número " + call.getNumber());
						agent.setStatus(AgentStatus.FREE);
					} else {
						System.out.println("No hay agentes disponibles. Colocando música de espera =)");
						CallQueue.queueUnattendedCall(call);
						sleep(500);
					}
				}
				queue.put(agent);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Método que obtiene una llamada.
	 * Primero revisa la cola de llamadas en espera, si está vacía,
	 * obtiene la llamada de la cola normal.
	 * @return Una llamada.
	 */
	private Call getCall() {
		Call call = CallQueue.retrieveHoldedCall();
		
		if (call == null) {
			call = CallQueue.retrieveCall();
		}
		
		return call;
	}

	/**
	 * Método que inicia el proceso de despacho de llamadas a través de concurrencia
	 */
	public void dispatchCall() {
		running = true;
		new Thread(this).start();
	}

	public void stop() {
		running = false;
	}

	/**
	 * Método para loggear todo lo que pasa con el despachador
	 * @param s
	 */
	private void log(String s) {
		System.out.println("[" + formatter.format(new Date()) + "][ServiceAgent][Agent " + id + "] " + s);
	}

	/**
	 * Método para controlar la salida en pantalla y evitar imprimir demasiados mensajes en pantalla
	 * @param duration, diracuón del tiempo de espera
	 */
	private void sleep(int duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}