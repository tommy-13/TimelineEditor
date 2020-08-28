package view.general;

import globals.GlobalConstants;
import io.safeLoad.SaveState;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import observer.CommandListObserver;
import language.Messages;
import model.DataBase;
import model.command.CommandList;
import task.LoadDataBaseTask;
import task.RedoTask;
import task.SaveAsDataBaseTask;
import task.SaveDataBaseTask;
import task.Task;
import task.UndoTask;
import task.WaitingDialog;
import view.graphicalElements.JMenuHover;
import view.graphicalElements.JMenuItemLingual;



@SuppressWarnings("serial")
public class MTMenuBar extends JMenuBar implements ActionListener, CommandListObserver {
	
	private MainView mainView;
	
	/* menu */
	private JMenuHover menuFile;
	private JMenuItemLingual menuItemNew;
	private JMenuItemLingual menuItemLoad;
	private JMenuItemLingual menuItemSave;
	private JMenuItemLingual menuItemSaveAs;
	private JMenuItemLingual menuItemEnd;
	
	private JMenuHover menuEdit;
	private JMenuItemLingual menuItemUndo;
	private JMenuItemLingual menuItemRedo;
	

	public MTMenuBar(MainView mainView) {
		this.mainView = mainView;
		CommandList.getInstance().registerObserver(this);

		createMenuFile();
		createMenuEdit();
		
		add(Box.createHorizontalGlue());
		add(new JLabel(GlobalConstants.COPYRIGHT + " by tommy  "));
		
		
		// register menu items as for this action listener
		for(Component c : getComponents()) {
			if(c instanceof JMenuHover) {
				JMenuHover menu = (JMenuHover) c;
				for(Component cmi : menu.getMenuComponents()) {
					if(cmi instanceof JMenuItem) {
						JMenuItem menuItem = (JMenuItem) cmi;
						menuItem.addActionListener(this);
					}
				}
			}
		}
	}
	
	private void createMenuFile() {
		menuFile = new JMenuHover("MenuBar.MenuFile");
		menuFile.setMnemonic(Messages.getString("Mnemonic.File").charAt(0));
		
		menuItemNew  = new JMenuItemLingual("MenuBar.MenuItemNew");
		menuItemNew.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK));
		menuFile.add(menuItemNew);
		
		menuFile.addSeparator();
		
		menuItemLoad = new JMenuItemLingual("MenuBar.MenuItemLoad");
		menuItemLoad.setAccelerator(KeyStroke.getKeyStroke('L', InputEvent.CTRL_DOWN_MASK));
		menuFile.add(menuItemLoad);
		
		menuItemSave = new JMenuItemLingual("MenuBar.MenuItemSave");
		menuItemSave.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK));
		menuFile.add(menuItemSave);
		
		menuItemSaveAs = new JMenuItemLingual("MenuBar.MenuItemSaveAs");
		menuItemSaveAs.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		menuFile.add(menuItemSaveAs);

		menuFile.addSeparator();
		
		menuItemEnd  = new JMenuItemLingual("MenuBar.MenuItemEnd");
		menuItemEnd.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
		menuFile.add(menuItemEnd);
		
		add(menuFile);
	}
	
	private void createMenuEdit() {
		menuEdit = new JMenuHover("MenuBar.MenuEdit");
		menuEdit.setMnemonic(Messages.getString("Mnemonic.Edit").charAt(0));
		
		menuItemUndo = new JMenuItemLingual("MenuBar.MenuItemUndo");
		menuItemUndo.setEnabled(false);
		menuItemUndo.setAccelerator(KeyStroke.getKeyStroke('Z', InputEvent.CTRL_DOWN_MASK));
		menuEdit.add(menuItemUndo);
		
		menuItemRedo = new JMenuItemLingual("MenuBar.MenuItemRedo");
		menuItemRedo.setEnabled(false);
		menuItemRedo.setAccelerator(KeyStroke.getKeyStroke('Y', InputEvent.CTRL_DOWN_MASK));
		menuEdit.add(menuItemRedo);
		
		add(menuEdit);
	}

	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if(actionCommand.equals("MenuBar.MenuItemNew")) {
			createNewFile();
		}
		if(actionCommand.equals("MenuBar.MenuItemSave")) {
			saveFile(false);
		}
		if(actionCommand.equals("MenuBar.MenuItemSaveAs")) {
			saveFile(true);
		}
		if(actionCommand.equals("MenuBar.MenuItemLoad")) {
			loadFile();
		}
		if(actionCommand.equals("MenuBar.MenuItemEnd")) {
			mainView.endProgram();
		}
		
		if(actionCommand.equals("MenuBar.MenuItemUndo")) {
			Task task = new UndoTask(mainView);
			task.execute();
			//new WaitingDialog(task);
		}
		if(actionCommand.equals("MenuBar.MenuItemRedo")) {
			Task task = new RedoTask(mainView);
			task.execute();
			//new WaitingDialog(task);
		}
	}
	
	
	
	
	public void createNewFile() {
		DataBase dataBase = DataBase.getInstance();
		int answer = JOptionPane.OK_OPTION;
		if(SaveState.getInstance().isSavingNecessary()) {
			answer = JOptionPane.showConfirmDialog(mainView,
					Messages.getString("DataLoseWarning.ConfirmText"),
					Messages.getString("DataLoseWarning.ConfirmTitle"),
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE);
		}
		if(answer == JOptionPane.OK_OPTION) {
			dataBase.reset();
			CommandList.getInstance().reset();
		}
	}

	
	public void loadFile() {
		int answer = JOptionPane.OK_OPTION;
		if(SaveState.getInstance().isSavingNecessary()) {
			answer = JOptionPane.showConfirmDialog(mainView,
					Messages.getString("DataLoseWarning.ConfirmText"),
					Messages.getString("DataLoseWarning.ConfirmTitle"),
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE);
		}
		if(answer == JOptionPane.OK_OPTION) {
			Task task = new LoadDataBaseTask(mainView);
			new WaitingDialog(task);
			if(task.isDone()) {
				CommandList.getInstance().reset();
			}
		}
	}
	
	
	public void saveFile(boolean saveAs) {
		if(DataBase.getInstance().isEmpty()) {
			return;
		}
		
		Task task = saveAs ? new SaveAsDataBaseTask(mainView) : new SaveDataBaseTask(mainView);
		new WaitingDialog(task);
	}

	
	
	@Override
	public void fireUndoStackState(boolean isEmpty) {
		menuItemUndo.setEnabled(!isEmpty);
	}

	@Override
	public void fireRedoStackState(boolean isEmpty) {
		menuItemRedo.setEnabled(!isEmpty);
	}
}
