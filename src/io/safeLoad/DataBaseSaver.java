package io.safeLoad;

import io.parseTree.TreeElement;
import io.parseTreeWriterReader.XMLTreeWriter;

import java.awt.Component;
import java.io.IOException;

import javax.swing.JOptionPane;

import language.Messages;

public class DataBaseSaver {

	public static boolean safe(boolean saveAs, Component parent) {
		String savePath = "";
		SaveState saveState = SaveState.getInstance();
		
		if(saveState.getSavePath() != null && !saveAs) {
			savePath = saveState.getSavePath();
		}
		else {
			savePath = PathChooser.displayExportFileChooser(parent, EnumFileType.DATA_BASE);
		}

		if(savePath == null) {
			return false;
		}

		TreeElement treeRoot = ParseTreeXMLDataBaseTranslator.createDataBaseTree();

		if(savePath.toLowerCase().endsWith(EnumFileType.getFileExtension(EnumFileType.DATA_BASE))) {
			XMLTreeWriter treeWriter = new XMLTreeWriter(savePath, treeRoot);
			try {
				treeWriter.write();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(parent,
						Messages.getString("DataBaseSaver.SavingErrorText"),
						Messages.getString("DataBaseSaver.SavingErrorTitle"),
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
			saveState.setSavePath(savePath);
			saveState.setSavingNecessary(false);
			return true;
		}
		return false;
	}

}
