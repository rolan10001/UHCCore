package fr.rolan.core.api.jda.userdata;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.rolan.core.api.jda.ConfigState;

public class Host {
	private boolean hascreatehost = false;
	private final Long id;
	private ConfigState state;
	private LocalDateTime date;
	private final List<String> server = new ArrayList<String>();
	private int slot = 0;
	private String message;
	private boolean config = false;
	private String idmessageconfirm;
	public int armor;
	public int diamond_prot;
	public int iron_prot;
	public int diamond_sharp;
	public int iron_sharp;
	public int power;
	public int pvp;
	public int roles;
	public int timer_border;
	public int size_border;
	public int size_final_border;
	
	public Host(Long id) {
		this.id = id;
		state = ConfigState.NONE;
	}

	public Long getId() {
		return id;
	}

	public ConfigState getState() {
		return state;
	}

	public void setState(ConfigState state) {
		this.state = state;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public List<String> getServer() {
		return server;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean hasConfig() {
		return config;
	}

	public void setConfig(boolean config) {
		this.config = config;
	}

	public boolean hasHascreatehost() {
		return hascreatehost;
	}

	public void setHascreatehost(boolean hascreatehost) {
		this.hascreatehost = hascreatehost;
	}

	public String getIDMessageConfirm() {
		return idmessageconfirm;
	}

	public void setIDMessageConfirm(String idmessageconfirm) {
		this.idmessageconfirm = idmessageconfirm;
	}
}
