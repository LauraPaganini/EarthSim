import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class Presentation {

	private JTable tempGrid;
	private JPanel gridPanel;

	public Presentation(int gridSpacing, Buffer buffer, GUI gui) {

		tempGrid = new JTable(180 / gridSpacing, 180 / gridSpacing);
		tempGrid.setPreferredSize(new Dimension(600, 430));
		tempGrid.setBackground(new Color(255, 255, 255, 10));
		tempGrid.setVisible(true);
		
		gridPanel = new JPanel();
		gridPanel.setBorder(new LineBorder(Color.WHITE, 1, true));
		//gridPanel.setBackground(new Color(0, 0, 0, 0));
		gridPanel.setPreferredSize(new Dimension(600, 430));
		gridPanel.setSize(new Dimension(600, 500));
		gridPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		//contentPane.add(mapPanel);
		
		gridPanel.add(tempGrid);
		
		
		
		//tempGrid.setDefaultRenderer(Object.class, new TempCellRenderer());
		
//		while (!buffer.isEmpty()) {
//			Cell[][] globe = buffer.remove();
//
//			for (int i = 0; i < globe.length; i++) {
//				for (int j = 0; j < globe[0].length; j++) {
//
//					//TableCellRenderer tcr = tempGrid.getCellRenderer(i, j);
//					
//					
//				}
//			}
//			gui.repaint();
//		}
	}

	public JTable getTable() {
		return tempGrid;
	}
	
	public JPanel getPanel() {
		return gridPanel;
	}

	// custom cell renderer for temperature grid

	public class TempCellRenderer extends DefaultTableCellRenderer {

		public Component getTableCellRendererComponent(JTable table, Cell[][] globe, Object value, boolean isSelected,
				boolean hasFocus, int row, int col) {

			// Cells are by default rendered as a JLabel
			JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
			l.setText("BBBB");
//			double[] tempRange = earth.minMaxTemp();
//			// color = temp % ((max-min)/3)
		//	int colorInt = (int) (globe[row][col].getTemp() % ((tempRange[1] - tempRange[0]) / 4));
			Color color;
			//switch (colorInt) {
//			case 0: // blue
//				color = new Color(0, 0, 255, 50);
//				break;
//			case 1: // green
//				color = new Color(0, 255, 0, 50);
//				break;
//			case 2: // yellow
//				color = new Color(255, 255, 0, 50);
//				break;
//			case 3: // orange
//				color = new Color(255, 100, 0, 50);
//				break;
//			case 4: // red
//				color = new Color(255, 0, 0, 50);
//				break;
			//default:
				color = Color.BLACK;
		//	}

			setBackground(color);
			// Return the JLabel that renders the cell
			return l;

		}
	}
}
