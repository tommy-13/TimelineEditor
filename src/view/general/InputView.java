package view.general;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import observer.DataObserver;
import language.Messages;
import model.DataBase;
import model.CardModel;
import model.command.ChangeCardModel;
import model.command.Command;
import model.command.DeleteCardModel;
import model.command.NewCardModel;
import si.behaviour.NoBehaviour;
import si.behaviour.UpdateBehaviour;
import si.nextIntCalculator.IdFieldCalculator;
import si.nextIntCalculator.YearFieldCalculator;
import task.CommandTask;
import task.Task;
import view.graphicalElements.JFormatedStringArea;
import view.graphicalElements.JSpinnerInteger;
import globals.GlobalConstants;
import globals.GlobalFunctions;

@SuppressWarnings("serial")
public class InputView extends JPanel implements DataObserver {
	
	private DataBase dataBase;
	
	private JLabel lblId;
	private JLabel lblYear;
	private JLabel lblEvent;
	private JSpinnerInteger siId;
	private JSpinnerInteger siYear;
	private JFormatedStringArea tfEvent;
	private JButton bSaveChange;
	private JButton bDelete;
	private JButton bNew;
	
	
	public InputView(MainView mainView) {
		this.dataBase = DataBase.getInstance();
		dataBase.registerObserver(this);
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		/* buttons */
		Container north = new Container();
		north.setLayout(new BorderLayout());
		add(north, BorderLayout.NORTH);
		
		north.add(getInputContainer(), BorderLayout.WEST);
	}
	
	private Container getInputContainer() {
		final int RIGHT_GAP = 5;
		final int DOWN_GAP  = 10;
		final Dimension dimYear = GlobalFunctions.getYearDimension();
		
		Container c = new Container();
		c.setLayout(new GridBagLayout());

		lblId = new JLabel(Messages.getString("Card.Id") + ":");
		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.gridx = 0;
		gbc_lblId.gridy = 0;
		gbc_lblId.insets = new Insets(0, 0, DOWN_GAP, RIGHT_GAP);
		gbc_lblId.anchor = GridBagConstraints.WEST;
		c.add(lblId, gbc_lblId);
		
		siId = new JSpinnerInteger(
				new UpdateBehaviour(this),
				new IdFieldCalculator(1, dataBase.getNextId()));
		siId.setPreferredSize(dimYear);
		GridBagConstraints gbc_id = new GridBagConstraints();
		gbc_id.gridx = 1;
		gbc_id.gridy = 0;
		gbc_id.insets = new Insets(0, 0, DOWN_GAP, 0);
		gbc_id.anchor = GridBagConstraints.WEST;
		c.add(siId, gbc_id);
		
		
		lblYear = new JLabel(Messages.getString("Card.Year") + ":");
		GridBagConstraints gbc_lblYear = new GridBagConstraints();
		gbc_lblYear.gridx = 0;
		gbc_lblYear.gridy = 1;
		gbc_lblYear.insets = new Insets(0, 0, DOWN_GAP, RIGHT_GAP);
		gbc_lblYear.anchor = GridBagConstraints.WEST;
		c.add(lblYear, gbc_lblYear);
		
		siYear = new JSpinnerInteger(
				new NoBehaviour(),
				new YearFieldCalculator());
		siYear.setValue(GlobalConstants.DEFAULT_YEAR);
		siYear.setPreferredSize(dimYear);
		GridBagConstraints gbc_year = new GridBagConstraints();
		gbc_year.gridx = 1;
		gbc_year.gridy = 1;
		gbc_year.insets = new Insets(0, 0, DOWN_GAP, 0);
		gbc_year.anchor = GridBagConstraints.WEST;
		c.add(siYear, gbc_year);
		
		
		lblEvent = new JLabel(Messages.getString("Card.Event") + ":");
		GridBagConstraints gbc_lblEvent = new GridBagConstraints();
		gbc_lblEvent.gridx = 2;
		gbc_lblEvent.gridy = 0;
		gbc_lblEvent.insets = new Insets(0, 20, 0, RIGHT_GAP);
		gbc_lblEvent.anchor = GridBagConstraints.NORTHWEST;
		c.add(lblEvent, gbc_lblEvent);
		
		tfEvent = new JFormatedStringArea(GlobalConstants.MAX_EVENTLENGHT);
		tfEvent.setLineWrap(true);
		tfEvent.setWrapStyleWord(true);
		tfEvent.setColumns(GlobalConstants.EVENT_COLUMNS);
		tfEvent.setRows(GlobalConstants.EVENT_ROWS);
		GridBagConstraints gbc_tfComment = new GridBagConstraints();
		gbc_tfComment.gridx = 3;
		gbc_tfComment.gridy = 0;
		gbc_tfComment.gridheight = 3;
		gbc_tfComment.insets = new Insets(0, 0, 0, 0);
		gbc_tfComment.anchor = GridBagConstraints.NORTHWEST;
		c.add(tfEvent, gbc_tfComment);
		
		
		Container cButtons = new Container();
		cButtons.setLayout(new GridBagLayout());
		GridBagConstraints gbc_cButtons = new GridBagConstraints();
		gbc_cButtons.gridx = 0;
		gbc_cButtons.gridy = 2;
		gbc_cButtons.gridwidth = 2;
		gbc_cButtons.insets = new Insets(10, 0, 0, 0);
		gbc_cButtons.anchor = GridBagConstraints.CENTER;
		c.add(cButtons, gbc_cButtons);
		
		bNew = new JButton(Messages.getString("Button.New"));
		bNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if(siId.getInt() != dataBase.getNextId()) {
					updateCardModel(dataBase.getNextId());
				}
			}
		});
		GridBagConstraints gbc_bNew = new GridBagConstraints();
		gbc_bNew.gridx = 0;
		gbc_bNew.gridy = 0;
		gbc_bNew.insets = new Insets(0, 0, 5, 0);
		gbc_bNew.fill = GridBagConstraints.HORIZONTAL;
		gbc_bNew.anchor = GridBagConstraints.CENTER;
		cButtons.add(bNew, gbc_bNew);
		
		bSaveChange = new JButton(Messages.getString("Button.Save"));
		bSaveChange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if(siId.getInt() == dataBase.getNextId()) {
					safeNewCardModel();
				}
				else {
					changeCardModel();
				}
			}
		});
		GridBagConstraints gbc_bSaveChange = new GridBagConstraints();
		gbc_bSaveChange.gridx = 0;
		gbc_bSaveChange.gridy = 1;
		gbc_bSaveChange.insets = new Insets(0, 0, 5, 0);
		gbc_bSaveChange.fill = GridBagConstraints.HORIZONTAL;
		gbc_bSaveChange.anchor = GridBagConstraints.CENTER;
		cButtons.add(bSaveChange, gbc_bSaveChange);
		
		
		bDelete = new JButton(Messages.getString("Button.Delete"));
		bDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				deleteCardModel();
			}
		});
		GridBagConstraints gbc_bDelete = new GridBagConstraints();
		gbc_bDelete.gridx = 0;
		gbc_bDelete.gridy = 2;
		gbc_bDelete.insets = new Insets(0, 0, 0, 0);
		gbc_bDelete.fill = GridBagConstraints.HORIZONTAL;
		gbc_bDelete.anchor = GridBagConstraints.CENTER;
		cButtons.add(bDelete, gbc_bDelete);
		
		
		return c;
	}
	
	
	
	
	

	
	
	public void updateCardModel(int id) {
		int nextId = dataBase.getNextId();
		siId.setUpperBound(nextId);
		id = (id > nextId) ? nextId : id;
		siId.setValue(id);
		if(id == nextId) {
			siYear.setValue(GlobalConstants.DEFAULT_YEAR);
			tfEvent.setText("");
			bSaveChange.setText(Messages.getString("Button.Save"));
		}
		else {
			CardModel cm = dataBase.getCardModel(id);
			siYear.setValue(cm.getYear());
			tfEvent.setText(cm.getEvent());
			bSaveChange.setText(Messages.getString("Button.Change"));
		}
	}
	
	
	private void safeNewCardModel() {
		int year = siYear.getInt();
		String event = tfEvent.getText().trim();
		if(event.isEmpty()) {
			handleEmptyText();
			return;
		}
		handleCommand(new NewCardModel(year, event));
	}
	
	private void changeCardModel() {
		int id = siId.getInt();
		if(id == dataBase.getNextId()) {
			return;
		}
		int year = siYear.getInt();
		String event = tfEvent.getText().trim();
		if(event.isEmpty()) {
			handleEmptyText();
			return;
		}
		
		// check if change was made
		CardModel cm = dataBase.getCardModel(id);
		if(year == cm.getYear() && event.equals(cm.getEvent())) {
			return;
		}
		
		handleCommand(new ChangeCardModel(id, year, event));
	}
	
	
	private void deleteCardModel() {
		int id = siId.getInt();
		if(id == dataBase.getNextId()) {
			siYear.setValue(GlobalConstants.DEFAULT_YEAR);
			tfEvent.setText("");
			return;
		}
		handleCommand(new DeleteCardModel(id));
	}
	
	private void handleCommand(Command command) {
		Task task = new CommandTask(this, command);
		task.execute();
	}
	
	
	private void handleEmptyText() {
		JOptionPane.showMessageDialog(this,
				Messages.getString("EmptyEvent.Text"),
				Messages.getString("EmptyEvent.Title"),
				JOptionPane.ERROR_MESSAGE);
	}

	
	
	@Override
	public void fireNewCardModel(CardModel cm) {
		updateCardModel(dataBase.getNextId());
	}
	@Override
	public void fireChangedCardModel(CardModel cm) {
		updateCardModel(siId.getInt());
	}
	@Override
	public void fireDeletedCardModel(CardModel cm) {
		updateCardModel(siId.getInt());
	}
	@Override
	public void fireReseted() {
		updateCardModel(dataBase.getNextId());
	}

}
