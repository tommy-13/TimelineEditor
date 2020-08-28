package si.behaviour;

import view.general.InputView;

public class UpdateBehaviour implements Behaviour {
	
	private InputView inputView;
	
	public UpdateBehaviour(InputView inputView) {
		this.inputView = inputView;
	}

	@Override
	public void execute(int value) {
		inputView.updateCardModel(value);
	}

}
