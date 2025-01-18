package edu.unizg.foi.uzdiz.askarica20.zadaca_3.command;

public class PotrubiCommand implements VlakCommand {
	private final VlakReceiver receiver;

	public PotrubiCommand(VlakReceiver receiver) {
		this.receiver = receiver;
	}

	@Override
	public void execute() {
		this.receiver.potrubi();
	}
}
