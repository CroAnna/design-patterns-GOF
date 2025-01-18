package edu.unizg.foi.uzdiz.askarica20.zadaca_3.command;

public class UpaliSvjetlaCommand implements VlakCommand {
	private final VlakReceiver receiver;

	public UpaliSvjetlaCommand(VlakReceiver receiver) {
		this.receiver = receiver;
	}

	@Override
	public void execute() {
		this.receiver.upaliSvjetla();
	}
}
