package com.miguelosorio.callcenter;

import java.io.Serializable;

/**
 * Clase que modela la llamada que entra al call center
 * @author Miguel Osorio
 *
 */
public class Call implements Serializable {

	private static final long serialVersionUID = 1L;

	private int duration;

	private int number;

	public Call(int number, int duration) {
		this.number = number;
		this.duration = duration;
	}

	public int getDuration() {
		return duration;
	}

	public int getNumber() {
		return number;
	}
}