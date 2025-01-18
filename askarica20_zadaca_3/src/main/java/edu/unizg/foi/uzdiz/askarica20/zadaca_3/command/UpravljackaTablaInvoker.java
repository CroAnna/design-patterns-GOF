package edu.unizg.foi.uzdiz.askarica20.zadaca_3.command;

import java.util.ArrayList;
import java.util.List;

public class UpravljackaTablaInvoker {
	private final List<VlakCommand> commands;

	public UpravljackaTablaInvoker() {
		this.commands = new ArrayList<VlakCommand>();
	}

	public void dodajKomandu(VlakCommand command) {
		this.commands.add(command);
	}

	public void izvrsiKomande() {
		for (VlakCommand command : this.commands) {
			command.execute();
		}
		this.commands.clear();
	}
}
