package edu.unizg.foi.uzdiz.askarica20.zadaca_3.command;

public class UpaliKlimuCommand implements VlakCommand {
	private final VlakReceiver receiver;

	public UpaliKlimuCommand(VlakReceiver receiver) {
		this.receiver = receiver;
	}

	@Override
	public void execute() {
		this.receiver.upaliKlimu();
	}
}
