import java.awt.EventQueue;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import java.sql.*;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EtchedBorder;

public class DataEntryForms {

	private JFrame frame;
	private JTextField newBrandText;
	private JTextField newTypeText;
	private JTextField newColorText;
	private JTextField newPatternText;
	private JTextField newLogoText;
	private JTable table;

	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	DefaultTableModel model = new DefaultTableModel();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataEntryForms window = new DataEntryForms();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void updateTable() {
		conn = DataEntryClass.ConnectDB();
		if(conn != null) {
			String sql = "SELECT * FROM dataform";
			
			try {
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] columnData = new Object[7];
				
				while(rs.next()) {
					columnData[0] = rs.getString("Brand");
					columnData[1] = rs.getString("Type");
					columnData[2] = rs.getInt("Quantity");
					columnData[3] = rs.getString("Color");
					columnData[4] = rs.getString("Logo");
					columnData[5] = rs.getString("Pattern");
					columnData[6] = rs.getString("BallNumber");
					
					model.addRow(columnData);
				}
			} catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
		
	}

	/**
	 * Create the application.
	 */
	public DataEntryForms() {
		initialize();
		
		Object col[] = {"Brand", "Type", "Quantity", "Color", "Logo", "Pattern", "Ball Number"};
		model.setColumnIdentifiers(col);
		table.setModel(model);
		conn = DataEntryClass.ConnectDB();
		
		updateTable();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 1460, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel InputPanel = new JPanel();
		InputPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(InputPanel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Ball Brand");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JComboBox brandCombo = new JComboBox();
		
		newBrandText = new JTextField();
		newBrandText.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Ball Type");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		JComboBox typeCombo = new JComboBox();
		
		newTypeText = new JTextField();
		newTypeText.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Color");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel ColorPanel = new JPanel();
		ColorPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel_13 = new JPanel();
		panel_13.setBackground(new Color(255, 255, 255));
		ColorPanel.add(panel_13);
		
		JLabel lblNewLabel_6 = new JLabel("W");
		panel_13.add(lblNewLabel_6);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(255, 0, 0));
		ColorPanel.add(panel_5);
		
		JLabel lblNewLabel_7 = new JLabel("R");
		panel_5.add(lblNewLabel_7);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(new Color(255, 128, 0));
		ColorPanel.add(panel_6);
		
		JLabel lblNewLabel_8 = new JLabel("O");
		panel_6.add(lblNewLabel_8);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(new Color(255, 255, 0));
		ColorPanel.add(panel_7);
		
		JLabel lblNewLabel_9 = new JLabel("Y");
		panel_7.add(lblNewLabel_9);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBackground(new Color(198, 255, 0));
		ColorPanel.add(panel_8);
		
		JLabel lblNewLabel_10 = new JLabel("L");
		panel_8.add(lblNewLabel_10);
		
		JPanel panel_9 = new JPanel();
		panel_9.setBackground(new Color(0, 255, 0));
		ColorPanel.add(panel_9);
		
		JLabel lblNewLabel_11 = new JLabel("G");
		panel_9.add(lblNewLabel_11);
		
		JPanel panel_10 = new JPanel();
		panel_10.setBackground(new Color(0, 128, 0));
		ColorPanel.add(panel_10);
		
		JLabel lblNewLabel_12 = new JLabel("F");
		panel_10.add(lblNewLabel_12);
		
		JPanel panel_11 = new JPanel();
		panel_11.setBackground(new Color(0, 206, 230));
		ColorPanel.add(panel_11);
		
		JLabel lblNewLabel_13 = new JLabel("C");
		panel_11.add(lblNewLabel_13);
		
		JPanel panel_12 = new JPanel();
		panel_12.setBackground(new Color(0, 128, 255));
		ColorPanel.add(panel_12);
		
		JLabel lblNewLabel_14 = new JLabel("B");
		panel_12.add(lblNewLabel_14);
		
		JPanel panel_14 = new JPanel();
		panel_14.setBackground(new Color(255, 0, 255));
		ColorPanel.add(panel_14);
		
		JLabel lblNewLabel_15 = new JLabel("P");
		panel_14.add(lblNewLabel_15);
		
		JPanel panel_15 = new JPanel();
		panel_15.setBackground(new Color(128, 0, 255));
		ColorPanel.add(panel_15);
		
		JLabel lblNewLabel_16 = new JLabel("V");
		panel_15.add(lblNewLabel_16);
		
		newColorText = new JTextField();
		newColorText.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Pattern");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		
		JComboBox patternCombo = new JComboBox();
		
		newPatternText = new JTextField();
		newPatternText.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Logo");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		
		JComboBox logoCombo = new JComboBox();
		
		newLogoText = new JTextField();
		newLogoText.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Ball Number");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		InputPanel.setLayout(new GridLayout(0, 6, 20, 5));
		InputPanel.add(lblNewLabel);
		InputPanel.add(brandCombo);
		InputPanel.add(newBrandText);
		InputPanel.add(lblNewLabel_1);
		InputPanel.add(typeCombo);
		InputPanel.add(newTypeText);
		InputPanel.add(lblNewLabel_2);
		InputPanel.add(ColorPanel);
		InputPanel.add(newColorText);
		InputPanel.add(lblNewLabel_4);
		InputPanel.add(patternCombo);
		InputPanel.add(newPatternText);
		InputPanel.add(lblNewLabel_3);
		InputPanel.add(logoCombo);
		InputPanel.add(newLogoText);
		InputPanel.add(lblNewLabel_5);
		
		JPanel BallNumberPanel = new JPanel();
		InputPanel.add(BallNumberPanel);
		BallNumberPanel.setLayout(new GridLayout(1, 0, 10, 0));
		
		JPanel panel_16 = new JPanel();
		panel_16.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_16.setBackground(new Color(255, 255, 255));
		BallNumberPanel.add(panel_16);
		
		JLabel lblNewLabel_17 = new JLabel("1");
		panel_16.add(lblNewLabel_17);
		
		JPanel panel_17 = new JPanel();
		panel_17.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_17.setBackground(new Color(255, 255, 255));
		BallNumberPanel.add(panel_17);
		
		JLabel lblNewLabel_18 = new JLabel("2");
		panel_17.add(lblNewLabel_18);
		
		JPanel panel_18 = new JPanel();
		panel_18.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_18.setBackground(new Color(255, 255, 255));
		BallNumberPanel.add(panel_18);
		
		JLabel lblNewLabel_19 = new JLabel("3");
		panel_18.add(lblNewLabel_19);
		
		JPanel panel_19 = new JPanel();
		panel_19.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_19.setBackground(new Color(255, 255, 255));
		BallNumberPanel.add(panel_19);
		
		JLabel lblNewLabel_20 = new JLabel("4");
		panel_19.add(lblNewLabel_20);
		
		JPanel panel_3 = new JPanel();
		InputPanel.add(panel_3);
		panel_3.setLayout(new GridLayout(1, 0, 10, 0));
		
		JSpinner ballNumberSpinner = new JSpinner();
		panel_3.add(ballNumberSpinner);
		
		JPanel panel_20 = new JPanel();
		panel_3.add(panel_20);
		
		JPanel DatabasePanel = new JPanel();
		DatabasePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(DatabasePanel, BorderLayout.CENTER);
		DatabasePanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		DatabasePanel.add(scrollPane);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		));
		table.setDefaultEditor(Object.class, null); // Makes table not editable
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		DatabasePanel.add(panel_1, BorderLayout.NORTH);
		
		JPanel ActionPanel = new JPanel();
		panel.add(ActionPanel, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("Add Entry");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "INSERT INTO dataform (Brand, Type, Quantity, Color, Logo, Pattern, BallNumber) VALUES (?, ?, ?, ?, ?, ?, ?)";
				try {
					pst = conn.prepareStatement(sql);
					pst.setString(1, newBrandText.getText());
					pst.setString(2, newTypeText.getText());
					pst.setInt(3, 1);
					pst.setString(4, newColorText.getText());
					pst.setString(5, newLogoText.getText());
					pst.setString(6, newPatternText.getText());
					pst.setString(7, ballNumberSpinner.getValue().toString());
					
					
					pst.execute();
					rs.close();
					pst.close();
				} catch(Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		ActionPanel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Update");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		ActionPanel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Delete");
		ActionPanel.add(btnNewButton_2);
	}
}
