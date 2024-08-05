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
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EtchedBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

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
	ArrayList<String> brandsColumn = new ArrayList<>();
	
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
	
	public void getBrands() {
		String sql = "SELECT * FROM dataform";
		try {
			String storedBrand;
			Boolean flag;
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()) {
				flag = true;
				storedBrand = rs.getString("Brand");
				System.out.println(storedBrand);
				for(int i = 0; i < brandsColumn.size(); i++) {
					if(storedBrand.equals(brandsColumn.get(i))) {
						flag = false;
					}
				}
				if(flag || brandsColumn.size() == 0) {
					brandsColumn.add(storedBrand);
				}
			}
			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public void deleteFromDatabase(String b, String t, String c, String l, String p, String bn) {
    	String sql = "DELETE FROM dataform WHERE Brand = ? AND Type = ? AND Color = ? AND Logo = ? AND Pattern = ? AND BallNumber = ?";
    	try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, b);
			pst.setString(2, t);
			pst.setString(3, c);
			pst.setString(4, l);
			pst.setString(5, p);
			pst.setString(6, bn);
			
			
			pst.execute();
			rs.close();
			pst.close();
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	/**
	 * Create the application.
	 */
	public DataEntryForms() {
		conn = DataEntryClass.ConnectDB();
		getBrands();
		initialize();
		
		Object col[] = {"Brand", "Type", "Quantity", "Color", "Logo", "Pattern", "Ball Number"};
		model.setColumnIdentifiers(col);
		table.setModel(model);
		
		
		model.setRowCount(0);
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
		
		JPanel headerPanel = new JPanel();
		frame.getContentPane().add(panel);
		headerPanel.setLayout(new BorderLayout(0, 0));

		JPanel inputPanel = new JPanel();
		inputPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JPanel previewPanel = new JPanel();
		
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
		
		JPanel colorPanel = new JPanel();
		colorPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel colorTextPanel = new JPanel();
		colorTextPanel.setLayout(new GridLayout(1, 0, 10, 0));
		
		newColorText = new JTextField();
		newColorText.setColumns(10);
		newColorText.setText("WHITE"); // Default Value
		colorTextPanel.add(newColorText);
		colorTextPanel.add(new JPanel());
		
		GifPanel gifPanel = new GifPanel("C:/Users/tec31/eclipse-workspace/GolfBallLog/res/color_preview.gif", 3);
		
		JPanel whitePanel = new JPanel();
		whitePanel.setBackground(new Color(255, 255, 255));
		whitePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Action to perform on click
            	newColorText.setText("WHITE");
            	gifPanel.setColorMask(255, 255, 255);
            }
        });
		colorPanel.add(whitePanel);	
		JLabel lblNewLabel_6 = new JLabel("W");
		whitePanel.add(lblNewLabel_6);
		
		JPanel redPanel = new JPanel();
		redPanel.setBackground(new Color(255, 0, 0));
		redPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Action to perform on click
            	newColorText.setText("RED");
            	gifPanel.setColorMask(255, 0, 0);
            }
        });
		colorPanel.add(redPanel);
		JLabel lblNewLabel_7 = new JLabel("R");
		redPanel.add(lblNewLabel_7);
		
		JPanel orangePanel = new JPanel();
		orangePanel.setBackground(new Color(255, 128, 0));
		orangePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Action to perform on click
            	newColorText.setText("ORANGE");
            	gifPanel.setColorMask(255, 128, 0);
            }
        });
		colorPanel.add(orangePanel);
		JLabel lblNewLabel_8 = new JLabel("O");
		orangePanel.add(lblNewLabel_8);
		
		JPanel yellowPanel = new JPanel();
		yellowPanel.setBackground(new Color(255, 255, 0));
		yellowPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Action to perform on click
            	newColorText.setText("YELLOW");
            	gifPanel.setColorMask(255, 255, 0);
            }
        });
		colorPanel.add(yellowPanel);	
		JLabel lblNewLabel_9 = new JLabel("Y");
		yellowPanel.add(lblNewLabel_9);
		
		JPanel limePanel = new JPanel();
		limePanel.setBackground(new Color(198, 255, 0));
		limePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Action to perform on click
            	newColorText.setText("LIME");
            	gifPanel.setColorMask(198, 255, 0);
            }
        });
		colorPanel.add(limePanel);
		JLabel lblNewLabel_10 = new JLabel("L");
		limePanel.add(lblNewLabel_10);
		
		JPanel greenPanel = new JPanel();
		greenPanel.setBackground(new Color(0, 255, 0));
		greenPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Action to perform on click
            	newColorText.setText("GREEN");
            	gifPanel.setColorMask(0, 255, 0);
            }
        });
		colorPanel.add(greenPanel);
		JLabel lblNewLabel_11 = new JLabel("G");
		greenPanel.add(lblNewLabel_11);
		
		JPanel forestPanel = new JPanel();
		forestPanel.setBackground(new Color(0, 128, 0));
		forestPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Action to perform on click
            	newColorText.setText("FOREST");
            	gifPanel.setColorMask(0, 128, 0);
            }
        });
		colorPanel.add(forestPanel);
		JLabel lblNewLabel_12 = new JLabel("F");
		forestPanel.add(lblNewLabel_12);
		
		JPanel cyanPanel = new JPanel();
		cyanPanel.setBackground(new Color(0, 206, 230));
		cyanPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Action to perform on click
            	newColorText.setText("CYAN");
            	gifPanel.setColorMask(0, 206, 230);
            }
        });
		colorPanel.add(cyanPanel);
		JLabel lblNewLabel_13 = new JLabel("C");
		cyanPanel.add(lblNewLabel_13);
		
		JPanel bluePanel = new JPanel();
		bluePanel.setBackground(new Color(0, 128, 255));
		bluePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Action to perform on click
            	newColorText.setText("BLUE");
            	gifPanel.setColorMask(0, 128, 255);
            }
        });
		colorPanel.add(bluePanel);	
		JLabel lblNewLabel_14 = new JLabel("B");
		bluePanel.add(lblNewLabel_14);
		
		JPanel pinkPanel = new JPanel();
		pinkPanel.setBackground(new Color(255, 0, 255));
		pinkPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Action to perform on click
            	newColorText.setText("PINK");
            	gifPanel.setColorMask(255, 0, 255);
            }
        });
		colorPanel.add(pinkPanel);
		JLabel lblNewLabel_15 = new JLabel("P");
		pinkPanel.add(lblNewLabel_15);
		
		JPanel violetPanel = new JPanel();
		violetPanel.setBackground(new Color(128, 0, 255));
		violetPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Action to perform on click
            	newColorText.setText("VIOLET");
            	gifPanel.setColorMask(128, 0, 255);
            }
        });
		colorPanel.add(violetPanel);
		JLabel lblNewLabel_16 = new JLabel("V");
		violetPanel.add(lblNewLabel_16);
		
		JLabel lblNewLabel_4 = new JLabel("Pattern");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel patternPanel = new JPanel();
		JComboBox<String> patternCombo = new JComboBox<>();
		patternCombo.addItem("NONE");
		patternCombo.setSelectedItem("NONE");
		
		newPatternText = new JTextField();
		newPatternText.setColumns(10);
		
		JCheckBox isPatternCheckbox = new JCheckBox("N/A");
		
		patternPanel.add(newPatternText);
		patternPanel.add(isPatternCheckbox);
		
		JLabel lblNewLabel_3 = new JLabel("Logo");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel logoPanel = new JPanel();
		JComboBox<String> logoCombo = new JComboBox<>();
		logoCombo.addItem("NONE");
		logoCombo.setSelectedItem("NONE");
		
		newLogoText = new JTextField();
		newLogoText.setColumns(10);
		
		JCheckBox isLogoCheckbox = new JCheckBox("N/A");
		
		logoPanel.add(newLogoText);
		logoPanel.add(isLogoCheckbox);
		
		JLabel lblNewLabel_5 = new JLabel("Ball Number");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		inputPanel.setLayout(new GridLayout(0, 6, 20, 5));
		inputPanel.add(lblNewLabel);
		inputPanel.add(brandCombo);
		inputPanel.add(newBrandText);
		inputPanel.add(lblNewLabel_3);
		inputPanel.add(logoCombo);
		inputPanel.add(logoPanel);
		inputPanel.add(lblNewLabel_1);
		inputPanel.add(typeCombo);
		inputPanel.add(newTypeText);
		inputPanel.add(lblNewLabel_4);
		inputPanel.add(patternCombo);
		inputPanel.add(patternPanel);
		inputPanel.add(lblNewLabel_2);
		inputPanel.add(colorPanel);
		inputPanel.add(colorTextPanel);
		inputPanel.add(lblNewLabel_5);
		
		JPanel ballNumberPanel = new JPanel();
		inputPanel.add(ballNumberPanel);
		ballNumberPanel.setLayout(new GridLayout(1, 0, 10, 0));
		
		JSpinner ballNumberSpinner = new JSpinner();
		
		JPanel onePanel = new JPanel();
		onePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		onePanel.setBackground(new Color(255, 255, 255));
		onePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Action to perform on click
            	ballNumberSpinner.setValue(1);
            }
        });
		ballNumberPanel.add(onePanel);
		JLabel lblNewLabel_17 = new JLabel("1");
		onePanel.add(lblNewLabel_17);
		
		JPanel twoPanel = new JPanel();
		twoPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		twoPanel.setBackground(new Color(255, 255, 255));
		twoPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Action to perform on click
            	ballNumberSpinner.setValue(2);
            }
        });
		ballNumberPanel.add(twoPanel);
		JLabel lblNewLabel_18 = new JLabel("2");
		twoPanel.add(lblNewLabel_18);
		
		JPanel threePanel = new JPanel();
		threePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		threePanel.setBackground(new Color(255, 255, 255));
		threePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Action to perform on click
            	ballNumberSpinner.setValue(3);
            }
        });
		ballNumberPanel.add(threePanel);
		JLabel lblNewLabel_19 = new JLabel("3");
		threePanel.add(lblNewLabel_19);
		
		JPanel fourPanel = new JPanel();
		fourPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		fourPanel.setBackground(new Color(255, 255, 255));
		fourPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Action to perform on click
            	ballNumberSpinner.setValue(4);
            }
        });
		ballNumberPanel.add(fourPanel);
		JLabel lblNewLabel_20 = new JLabel("4");
		fourPanel.add(lblNewLabel_20);
		
		JPanel panel_3 = new JPanel();
		inputPanel.add(panel_3);
		panel_3.setLayout(new GridLayout(1, 0, 10, 0));
		
		panel_3.add(ballNumberSpinner);
		
		JPanel panel_20 = new JPanel();
		panel_3.add(panel_20);
		
		previewPanel.add(gifPanel);
		
		headerPanel.add(inputPanel, BorderLayout.CENTER);	
		headerPanel.add(previewPanel,  BorderLayout.EAST);
		
		panel.add(headerPanel, BorderLayout.NORTH);
		
		
		JPanel databasePanel = new JPanel();
		databasePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		databasePanel.setLayout(new BorderLayout(0, 0));
		panel.add(databasePanel, BorderLayout.CENTER);
		databasePanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		databasePanel.add(scrollPane, BorderLayout.CENTER);
		JPanel filterPanel = new JPanel();
		
		filterPanel.add(new JLabel("Filters"));
		
		databasePanel.add(filterPanel, BorderLayout.NORTH);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setDefaultEditor(Object.class, null); // Makes table not editable
		scrollPane.setViewportView(table);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EmptyBorder(0, 10, 10, 10));
		scrollPane.setRowHeaderView(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_21 = new JLabel("Brands Quick Select");
		panel_2.add(lblNewLabel_21, BorderLayout.NORTH);
		
		JPanel brandsPanel = new JPanel();
		brandsPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
		panel_2.add(brandsPanel, BorderLayout.CENTER);
		brandsPanel.setLayout(new BoxLayout(brandsPanel, BoxLayout.Y_AXIS));
		
		for(int i = 0; i < brandsColumn.size(); i++) {
			JLabel brand = new JLabel(brandsColumn.get(i).toString());
			brand.setEnabled(false);
			brandsPanel.add(brand);
		}
		
		JPanel actionPanel = new JPanel();
		panel.add(actionPanel, BorderLayout.SOUTH);
		
		JButton entryButton = new JButton("Add Entry");
		entryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Check to see if exact entry already exists
				String sql = "SELECT Quantity FROM dataform WHERE Brand = ? AND Type = ?";
				try {
					pst = conn.prepareStatement(sql);
					pst.setString(1, newBrandText.getText());
					pst.setString(2, newTypeText.getText());
					
					rs = pst.executeQuery();
					if(rs.next()) {
						sql = "UPDATE dataform SET Quantity = Quantity + 1 WHERE Brand = ? AND Type = ?";
						try {
							pst = conn.prepareStatement(sql);
							pst.setString(1, newBrandText.getText());
							pst.setString(2, newTypeText.getText());
							
							
							pst.execute();
							rs.close();
							pst.close();
						} catch(Exception e1) {
							JOptionPane.showMessageDialog(null, e1);
						}
					} else {
						sql = "INSERT INTO dataform (Brand, Type, Quantity, Color, Logo, Pattern, BallNumber) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
					rs.close();
					pst.close();
				} catch(Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
				
				model.setRowCount(0);
				getBrands();
				brandsPanel.removeAll();
				for(int i = 0; i < brandsColumn.size(); i++) {
					JLabel brand = new JLabel(brandsColumn.get(i));
					brand.setEnabled(false);
					brandsPanel.add(brand);
				}
				updateTable();
			}
		});
		actionPanel.add(entryButton);
		
		JButton refreshButton = new JButton("Refresh Table");
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setRowCount(0);
				updateTable();
			}
		}); 
		actionPanel.add(refreshButton);
		
		JButton deleteButton = new JButton("Delete Selected");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            // Get the selected row index
	            int selectedRow = table.getSelectedRow();
	            if (selectedRow != -1) { // Check if a row is selected
	            	deleteFromDatabase(table.getValueAt(selectedRow, 0).toString(), table.getValueAt(selectedRow, 1).toString(), table.getValueAt(selectedRow, 3).toString(), table.getValueAt(selectedRow, 4).toString(), table.getValueAt(selectedRow, 5).toString(), table.getValueAt(selectedRow, 6).toString());
	                model.removeRow(selectedRow); // Remove the selected row
	            } else {
	                JOptionPane.showMessageDialog(frame, "No Row Selected", "Error", JOptionPane.ERROR_MESSAGE);
	            }
			}
		}); 
		actionPanel.add(deleteButton);
		model.setRowCount(0);
		updateTable();
	}
}
