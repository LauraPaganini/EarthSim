package EarthHeatingGUI;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import earthGrid.Grid;


public class EarthHeatGUI extends JFrame implements IEarthHeatGUIModel {

	final int ROW_SIZE = 12;
	final int COLUMN_SIZE = 25;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private MyJTable table;
	private JTextField txtRate;
	private JTextField textStepSize;
	private JTextField textGridSizeY;
	private EarthHeatGUIPresentation Presentation = null;
	private JTextField txtYSize;
	private JTextField txtStepSize;
	private boolean isGridInitialized = false;
	private JTextField txtSunTemperature;
	private JLabel lblAverageTimeNumber;
	private JLabel lblTotalTimeNumber;
	private JLabel lblIteration;
	private JLabel lblIterationCount;
	private JLabel lblPresentaionIteration;
	private JLabel lblPresIteration;
	private JLabel lblRate;
	
	private double SunTemp;
	private double DefaultTemp;
	private double MultValueForSun;
	private JTextField txtSunAngleY;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EarthHeatGUI window = new EarthHeatGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EarthHeatGUI() {
		initialize();
	}
	
	public EarthHeatGUI CreateAndShowForm(){
		final EarthHeatGUI window = new EarthHeatGUI();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return window;
	}
	
	public void SetupPresentation(EarthHeatGUIPresentation Presentation){
		this.Presentation = Presentation;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		table = new MyJTable();
		table.setBounds(0, 0, 704, 761);
		table.setFillsViewportHeight(true);
		table.setToolTipText("");
		table.setBackground(Color.LIGHT_GRAY);
		table.setForeground(Color.BLACK);
		frame.getContentPane().add(table);
		
		// Run Button pressed event
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtRate.getText();
				//Setup the variables before running
				Presentation.SetGridSize(Integer.parseInt(txtStepSize.getText()), Integer.parseInt(txtYSize.getText()));
				Presentation.SetSunTemperature(Double.parseDouble(txtSunTemperature.getText()));
				//Run it
				Presentation.RaiseRunEvent();
			}
		});
		
		
		btnRun.setBounds(714, 303, 173, 26);
		frame.getContentPane().add(btnRun);
		
		txtRate = new JTextField();
		txtRate.setText("1");
		txtRate.setBounds(856, 6, 96, 20);
		frame.getContentPane().add(txtRate);
		txtRate.setColumns(10);
		
		lblRate = new JLabel("Rate (seconds):");
		lblRate.setBounds(714, 0, 132, 26);
		frame.getContentPane().add(lblRate);
		
		JLabel lblStepSize = new JLabel("Step Size:");
		lblStepSize.setBounds(714, 32, 117, 26);
		frame.getContentPane().add(lblStepSize);
		
		textStepSize = new JTextField();
		textStepSize.setBounds(1607, 237, 186, 32);
		frame.getContentPane().add(textStepSize);
		textStepSize.setColumns(10);
		
		JLabel lblAvgTime = new JLabel("Avg Time:");
		lblAvgTime.setBounds(714, 166, 117, 26);
		frame.getContentPane().add(lblAvgTime);
		
		lblAverageTimeNumber = new JLabel("N/A");
		lblAverageTimeNumber.setBounds(856, 166, 96, 26);
		frame.getContentPane().add(lblAverageTimeNumber);
		
		JLabel lblGridSizeY = new JLabel("Grid Size, Y:");
		lblGridSizeY.setBounds(714, 69, 117, 26);
		frame.getContentPane().add(lblGridSizeY);
		
		textGridSizeY = new JTextField();
		textGridSizeY.setBounds(1607, 473, 186, 32);
		frame.getContentPane().add(textGridSizeY);
		textGridSizeY.setColumns(10);
		
		JLabel lblTotalTime = new JLabel("Total Time");
		lblTotalTime.setBounds(714, 198, 117, 26);
		frame.getContentPane().add(lblTotalTime);
		
		
		// Pause Button pressed event
		JButton btnPause = new JButton("Pause");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Presentation.RaisePauseEvent();
			}
		});
		
		
		btnPause.setBounds(714, 337, 173, 26);
		frame.getContentPane().add(btnPause);
		
		
		// Stop button pressed event
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Presentation.RaiseStopEvent();
			}
		});
		
		
		btnStop.setBounds(714, 411, 173, 26);
		frame.getContentPane().add(btnStop);
		
		JButton btnResume = new JButton("Resume");
		btnResume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Presentation.RaiseResumeEvent();
			}
		});
		btnResume.setBounds(714, 374, 173, 26);
		frame.getContentPane().add(btnResume);
		
		txtYSize = new JTextField();
		txtYSize.setText("20");
		txtYSize.setBounds(856, 72, 96, 20);
		frame.getContentPane().add(txtYSize);
		txtYSize.setColumns(10);
		
		txtStepSize = new JTextField();
		txtStepSize.setText("30");
		txtStepSize.setBounds(856, 35, 96, 20);
		frame.getContentPane().add(txtStepSize);
		txtStepSize.setColumns(10);
		
		JLabel lblSunTemperature = new JLabel("Sun Temperature");
		lblSunTemperature.setBounds(714, 109, 117, 14);
		frame.getContentPane().add(lblSunTemperature);
		
		txtSunTemperature = new JTextField();
		txtSunTemperature.setText("5");
		txtSunTemperature.setBounds(856, 103, 96, 20);
		frame.getContentPane().add(txtSunTemperature);
		txtSunTemperature.setColumns(10);
		
		lblTotalTimeNumber = new JLabel("N/A");
		lblTotalTimeNumber.setBounds(856, 203, 96, 21);
		frame.getContentPane().add(lblTotalTimeNumber);
		
		lblIteration = new JLabel("Simulation Iteration:");
		lblIteration.setBounds(714, 228, 132, 25);
		frame.getContentPane().add(lblIteration);
		
		lblIterationCount = new JLabel("N/A");
		lblIterationCount.setBounds(856, 233, 99, 14);
		frame.getContentPane().add(lblIterationCount);
		
		lblPresentaionIteration = new JLabel("Presentaion Iteration:");
		lblPresentaionIteration.setBounds(714, 258, 132, 20);
		frame.getContentPane().add(lblPresentaionIteration);
		
		lblPresIteration = new JLabel("N/A");
		lblPresIteration.setBounds(856, 261, 81, 14);
		frame.getContentPane().add(lblPresIteration);
		
		JLabel lblSunAngleY = new JLabel("Sun Angle Y");
		lblSunAngleY.setBounds(714, 137, 117, 14);
		frame.getContentPane().add(lblSunAngleY);
		
		txtSunAngleY = new JTextField();
		txtSunAngleY.setText("0");
		txtSunAngleY.setBounds(856, 134, 96, 20);
		frame.getContentPane().add(txtSunAngleY);
		txtSunAngleY.setColumns(10);
	}
	
	public void UpdateGrid(Grid grid){
		if(!isGridInitialized){
			createTable(grid.width, grid.height);
			isGridInitialized = true;
		}
		synchronized(grid){
			//This code was taken and modified from the previous project
			//It was written by Cecil Wilson
			//http://stackoverflow.com/questions/12861402/jtable-cell-color
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.setRowCount(0);
			String[] row = new String[grid.width + 1];
			for(int i = 1; i < grid.height - 1; i++){
					for(int j = 1; j < grid.width - 1; j++){
						row[j - 1] = grid.GetCell(j, i).GetRounded2DecimalsTemperature();
					}
				model.addRow(row);

			}
		}

		this.frame.repaint();
		this.frame.revalidate();
	}
	
	protected Color GetColorByCellValue(double Value){
		//Find the RGB value, default to yellow
		double TempOfMax = this.SunTemp * this.MultValueForSun;
		int R = 255;
		int G = 255;
		int B = 0;
		if(Value > this.DefaultTemp){
			//Remove the "yellow"
			G = (int)(Math.abs(Value - TempOfMax - this.DefaultTemp)/TempOfMax * G);
		}else{
			B = 255;
			//Add the "Blue"
			R = (int)(Math.abs(this.DefaultTemp - Value - TempOfMax)/TempOfMax * R);
			G = (int)(Math.abs(this.DefaultTemp - Value - TempOfMax)/TempOfMax * G);
			B = B - (int)(Math.abs(this.DefaultTemp - Value - TempOfMax)/TempOfMax * B);
		}
		if(R < 0){
			R = 0;
		}
		if(G < 0){
			G = 0;
		}
		if(B < 0){
			B = 0;
		}
		if(R > 255){
			R = 255;
		}
		if(G > 255){
			G = 255;
		}
		if(B > 255){
			B = 255;
		}
		
		return new java.awt.Color(R, G, B);
	}
	
	public class MyJTable extends JTable{
		
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
	    public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
			Component comp = null;
			try{
		        comp = super.prepareRenderer(renderer, row, col);
		        Object value = getModel().getValueAt(row, col);
		        if(value != null){
		        	comp.setBackground(GetColorByCellValue(Double.parseDouble(value.toString())));
		        	//Sets the text to "off"
		        	//comp.setForeground(GetColorByCellValue(Double.parseDouble(value.toString())));
		        }
			}catch (Exception e){
				
			}	
	        return comp;
	    }
	}

	
	private void createTable(int width, int height)
	//This code was taken and modified from the previous project
	//It was written by Cecil Wilson
	{
		table.setRowHeight(ROW_SIZE);
		table.setRowMargin(0);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(MyJTable.AUTO_RESIZE_OFF);
		table.setFont(new Font("Arial", Font.PLAIN, 8));
		table.setModel(new DefaultTableModel(new Object[width][height] ,new String[width]));
		
		// Set width of all columns
		for(int i = 0; i < width; i++)
		{
			table.getColumnModel().setColumnMargin(0);
			table.getColumnModel().getColumn(i).setMinWidth(COLUMN_SIZE);
			table.getColumnModel().getColumn(i).setPreferredWidth(COLUMN_SIZE);
			table.getColumnModel().getColumn(i).setWidth(COLUMN_SIZE);
		}
				
		table.setBorder(new LineBorder(new Color(255, 255, 0)));
		table.setRowSelectionAllowed(false);
		table.setTableHeader(null);
		table.setVisible(true);			
	}
	
	public void UpdateAvarageTime(double Time){
		this.lblAverageTimeNumber.setText(Double.toString(Time));
		this.frame.repaint();
		this.frame.revalidate();
	}
	
	public void UpdateTotalTime(double Time){
		this.lblTotalTimeNumber.setText(Double.toString(Time));
	}
	
	public void BlankTotalTime(){
		this.lblTotalTimeNumber.setText("N/A");
	}
	
	public void SetSimulationIterationCount(int Iteration, int TotalIteration){
		lblIterationCount.setText(Iteration + "/" + TotalIteration);
	}
	
	public double GetIterationTime(){
		return Double.parseDouble(txtRate.getText());
	}
	
	public double GetSunAngleY(){
		return Double.parseDouble(txtSunAngleY.getText());
	}
	
	public void SetPresentationIterationCount(int Iteration, int TotalIteration){
		lblPresIteration.setText(Iteration + "/" + TotalIteration);
	}
	
	public void SetAllValuesForTemperature(double SunTemp, double DefaultTemp, double MultValueForSun){
		this.MultValueForSun = MultValueForSun;
		this.SunTemp = SunTemp;
		this.DefaultTemp = DefaultTemp;
	}
	
	public void EnableDisableRate(boolean Enable){
		txtRate.setVisible(Enable);
		lblRate.setVisible(Enable);
	}
}

