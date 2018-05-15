package com.miguelosorio.callcenter;

/**
 * Clase que representa el agente que atiende las llamadas
 * @author Miguel Osorio
 *
 */
public class Agent {

	private String name;
	
	private AgentStatus status;
	
	private long callTime;

	public Agent(String name) {
		this.name = name;
		this.status = AgentStatus.FREE;
	}

	public AgentStatus getStatus() {
		return status;
	}

	public void setStatus(AgentStatus status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public long getCallTime() {
		return callTime;
	}

	public void setCallTime(long callTime) {
		this.callTime = callTime;
	}
	
}
