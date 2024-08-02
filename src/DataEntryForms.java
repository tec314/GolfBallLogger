import java.awt.EventQueue;

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
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new GridLayout(0, 6, 10, 5));
		
		JLabel lblNewLabel = new JLabel("Ball Brand");
		panel_2.add(lblNewLabel);
		
		JComboBox brandCombo = new JComboBox();
		panel_2.add(brandCombo);
		
		newBrandText = new JTextField();
		panel_2.add(newBrandText);
		newBrandText.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Ball Type");
		panel_2.add(lblNewLabel_1);
		
		JComboBox typeCombo = new JComboBox();
		panel_2.add(typeCombo);
		
		newTypeText = new JTextField();
		panel_2.add(newTypeText);
		newTypeText.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Color");
		panel_2.add(lblNewLabel_2);
		
		JComboBox colorCombo = new JComboBox();
		panel_2.add(colorCombo);
		
		newColorText = new JTextField();
		panel_2.add(newColorText);
		newColorText.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Pattern");
		panel_2.add(lblNewLabel_4);
		
		JComboBox patternCombo = new JComboBox();
		panel_2.add(patternCombo);
		
		newPatternText = new JTextField();
		panel_2.add(newPatternText);
		newPatternText.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Logo");
		panel_2.add(lblNewLabel_3);
		
		JComboBox logoCombo = new JComboBox();
		panel_2.add(logoCombo);
		
		newLogoText = new JTextField();
		panel_2.add(newLogoText);
		newLogoText.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Ball Number");
		panel_2.add(lblNewLabel_5);
		
		JSpinner ballNumberSpinner = new JSpinner();
		panel_2.add(ballNumberSpinner);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_3.add(scrollPane);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		));
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.SOUTH);
		
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
		panel_1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Update");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel_1.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Delete");
		panel_1.add(btnNewButton_2);
	}
}
