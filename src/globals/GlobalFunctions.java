package globals;

import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class GlobalFunctions {
	
	public static List<Image> getIconImages() {
		List<Image> icons = new ArrayList<Image>();
		icons.add(new ImageIcon(GlobalConstants.iconPath64).getImage());
		icons.add(new ImageIcon(GlobalConstants.iconPath32).getImage());
		icons.add(new ImageIcon(GlobalConstants.iconPath16).getImage());
		return icons;
	}
	
	
	public static Dimension getYearDimension() {
		if(OSInformation.nimbusLookAndFeel) {
			return new Dimension(70, 26);
		}
		else {
			return new Dimension(55, 20);
		}
	}
	

	public static String[] stringListToArray(List<String> list) {
		String[] array = new String[list.size()];
		for(int i=0; i<list.size(); i++) {
			array[i] = list.get(i);
		}
		return array;
	}
	
	public static List<String> StringArrayToList(String[] array) {
		List<String> list = new ArrayList<String>();
		for(String s : array) {
			list.add(s);
		}
		return list;
	}

}
