
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import java.awt.FlowLayout;

import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;

import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class GUI extends JFrame {

	private JPanel contentPane, mapPanel;
	private JLabel timeStepLabel, displayRateLabel;
	private JTextField gridSpacingNum, timeNum, displayRate;
	private JButton runButton, stopButton, resetButton, pauseButton;
	private ImageIcon map;

	private static Buffer buffer;
	private static Earth earth;
	private boolean done = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					earth = new Earth();
					buffer = new Buffer(1);
					GUI frame = new GUI(buffer, earth);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI(Buffer b, Earth e) {
		this.buffer = b;
		this.earth = e;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 700);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		mapPanel = new JPanel();
		mapPanel.setBorder(new LineBorder(Color.WHITE, 1, true));
		mapPanel.setBackground(new Color(0, 0, 102));
		mapPanel.setPreferredSize(new Dimension(830, 430));
		mapPanel.setSize(new Dimension(800, 500));
		mapPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		contentPane.add(mapPanel);

		JPanel titlePanel = new JPanel();
		titlePanel.setBorder(null);
		titlePanel.setBackground(new Color(0, 0, 102));
		titlePanel.setPreferredSize(new Dimension(830, 25));
		contentPane.add(titlePanel);

		JLabel lblSimSettings = new JLabel("Simulation Settings");
		titlePanel.add(lblSimSettings);
		lblSimSettings.setVerticalTextPosition(SwingConstants.TOP);
		lblSimSettings.setVerticalAlignment(SwingConstants.TOP);
		lblSimSettings.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSimSettings.setHorizontalAlignment(SwingConstants.CENTER);
		lblSimSettings.setFont(new Font("Arial", Font.PLAIN, 18));
		lblSimSettings.setForeground(Color.WHITE);

		JPanel settingsPanel = new JPanel();
		settingsPanel.setBackground(new Color(0, 0, 102));
		settingsPanel.setBorder(new LineBorder(Color.WHITE, 1, true));
		settingsPanel.setPreferredSize(new Dimension(830, 170));
		contentPane.add(settingsPanel);

		JLabel lblGridSpacing = new JLabel("Grid Spacing:");
		lblGridSpacing.setMinimumSize(new Dimension(40, 16));
		lblGridSpacing.setFont(new Font("Arial", Font.PLAIN, 18));
		lblGridSpacing.setPreferredSize(new Dimension(50, 18));
		lblGridSpacing.setMaximumSize(new Dimension(76, 10));
		lblGridSpacing.setHorizontalTextPosition(SwingConstants.RIGHT);
		lblGridSpacing.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGridSpacing.setForeground(Color.WHITE);

		gridSpacingNum = new JTextField("15");
		runButton = new JButton("Start");
		runButton.addActionListener(new ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				runButtonActionPerformed(evt);
			}
		});
		
		
		timeStepLabel = new JLabel("Simulation Time Step: ");
		timeStepLabel.setMinimumSize(new Dimension(40, 16));
		timeStepLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		timeStepLabel.setPreferredSize(new Dimension(50, 18));
		timeStepLabel.setMaximumSize(new Dimension(76, 10));
		timeStepLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		timeStepLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		timeStepLabel.setForeground(Color.WHITE);

		displayRateLabel = new JLabel("Display Rate: ");
		displayRateLabel.setMinimumSize(new Dimension(40, 16));
		displayRateLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		displayRateLabel.setPreferredSize(new Dimension(50, 18));
		displayRateLabel.setMaximumSize(new Dimension(76, 10));
		displayRateLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		displayRateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		displayRateLabel.setForeground(Color.WHITE);

		displayRate = new JTextField("1");
		timeNum = new JTextField("1");

		stopButton = new JButton("Stop");
		pauseButton = new JButton("Pause");
		resetButton = new JButton("Reset");

		// Add settings components to settings panel
		GroupLayout gl_settingsPanel = new GroupLayout(settingsPanel);
		gl_settingsPanel.setAutoCreateGaps(true);
		gl_settingsPanel.setAutoCreateContainerGaps(true);

		gl_settingsPanel.setHorizontalGroup(gl_settingsPanel.createParallelGroup(Alignment.LEADING).addGap(100)
				.addGroup(gl_settingsPanel.createSequentialGroup().addGap(1)
						.addComponent(lblGridSpacing, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
						.addComponent(gridSpacingNum, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_settingsPanel.createSequentialGroup().addGap(1)
						.addComponent(timeStepLabel, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
						.addComponent(timeNum, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_settingsPanel.createSequentialGroup().addGap(1)
						.addComponent(displayRateLabel, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
						.addComponent(displayRate, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_settingsPanel.createSequentialGroup().addGap(1)
						.addComponent(runButton, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addComponent(pauseButton, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addComponent(stopButton, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addComponent(resetButton, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))

		);
		gl_settingsPanel.setVerticalGroup(gl_settingsPanel.createParallelGroup(Alignment.LEADING).addGap(1)
				.addGroup(gl_settingsPanel.createSequentialGroup()
						.addComponent(lblGridSpacing, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addComponent(timeStepLabel, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addComponent(displayRateLabel, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addComponent(runButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_settingsPanel.createSequentialGroup()
						.addComponent(gridSpacingNum, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addComponent(timeNum, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addComponent(displayRate, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addComponent(pauseButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_settingsPanel.createSequentialGroup().addGap(123).addComponent(stopButton,
						GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_settingsPanel.createSequentialGroup().addGap(123).addComponent(resetButton,
						GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)));
		settingsPanel.setLayout(gl_settingsPanel);

		// Adding image to map panel
//		map = new ImageIcon("Equirectangular_projection_SW.jpg");
//		JLabel lbMap = new JLabel("",
//				new ImageIcon("C:/Users/laura/workspace/EarthSim/Equirectangular_projection_SW.jpg"), JLabel.CENTER);
//		mapPanel.add(lbMap, BorderLayout.CENTER);

	}

	private void runButtonActionPerformed(final java.awt.event.ActionEvent evt) {
		// start thread for presentation

		Presentation p = new Presentation(Integer.parseInt(gridSpacingNum.getText()), buffer, this);
		mapPanel.add(p.getTable());
		repaint();
		System.out.println("PRES");
		// sim needs to run and fill buffer

//		while (!buffer.isEmpty()) {
//			Cell[][] globe = buffer.remove();
//
//			for (int i = 0; i < globe.length; i++) {
//				for (int j = 0; j < globe[0].length; j++) {
//
//					//tempGrid.setDefaultRenderer(Object.class, new TempCellRenderer());
//					//TableCellRenderer tcr = tempGrid.getCellRenderer(i, j);
//					
//				}
//			}
//
//			repaint();
//		}

		// TableCellRenderer tcr = tempGrid.getCellRenderer(row, column)
		// add to panel
		// repaint
	}

	private void stopButtonActionPerformed(final java.awt.event.ActionEvent evt) {
		done = true;
	}

	private void pauseButtonActionPerformed(final java.awt.event.ActionEvent evt) {
		done = true;
	}

	private void resetButtonActionPerformed(final java.awt.event.ActionEvent evt) {
		// clear buffer

	}


}
