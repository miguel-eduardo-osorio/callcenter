package com.miguelosorio.callcenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class CallGenerator implements Runnable {

	private SimpleDateFormat formatter;
	
	private int numberOfCalls;

	public CallGenerator() {
		formatter = new SimpleDateFormat("HH:mm:ss");
	}

	/**
	 * Método encargado de crear las llamadas y encolarlas para ser atendidas
	 */
	public void run() {
		for (int i = 0; i < numberOfCalls; i++) {
			
			//ThreadLocalRandom es usado para simular una llamada con duración entre 5 y 10 segundos
			int duration = ThreadLocalRandom.current().nextInt(5,10);
			if (duration >= 5) {
				log("Creando una llamada con una duracion de " + duration + " segundos");
				CallQueue.queueCall(duration);
			}
		}
	}

	public void start(int calls) {
		this.numberOfCalls = calls;
		new Thread(this).start();
	}

	private void log(String s) {
		System.out.println("[" + formatter.format(new Date()) + "][CallGenerator] " + s);
	}
}