import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.BoxLayout;

import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
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
	private JTextField newBrandText = new JTextField();
	private JTextField newTypeText = new JTextField();
	private JTextField newColorText1 = new JTextField();
	private JTextField newColorText2 = new JTextField();
	private JTextField newPatternText;
	private JTextField newLogoText;
	private JTable table;
	
	private JPanel colorPanel = new JPanel();
	private GifPanel gifPanel = new GifPanel("C:/Users/tec31/eclipse-workspace/GolfBallLog/res/color_preview.gif", 3);
	
	private JTextField brandCombo = new JTextField();
	private JTextField typeCombo = new JTextField();
	private JPanel brandPanel = new JPanel();
	private JPanel typePanel = new JPanel();

	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	DefaultTableModel model = new DefaultTableModel();
	
	ArrayList<String> brandsColumn = new ArrayList<>();
	ArrayList<String> typesColumn = new ArrayList<>();
	ArrayList<String> logos = new ArrayList<>();
	ArrayList<String> patterns = new ArrayList<>();
	
	private String selectedBrand;
	private String selectedType;
	
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
	
	/*
	// NOT NEEDED SOON
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
	*/
	
	// Find way to use this to replace updateTable()?
	public void updateTableByBrandType(String brand, String type) {
		conn = DataEntryClass.ConnectDB();
		if(conn != null) {
			model.setRowCount(0);
			
			String sql = "SELECT * FROM dataform WHERE Brand = ? AND Type = ?";
			
			try {
				pst = conn.prepareStatement(sql);
	
				pst.setString(1, brand);
				pst.setString(2, type);
				
				rs = pst.executeQuery();
				Object[] columnData = new Object[5];
				
				while(rs.next()) {
					columnData[0] = rs.getInt("Quantity");
					columnData[1] = rs.getString("Color");
					columnData[2] = rs.getString("Logo");
					columnData[3] = rs.getString("Pattern");
					columnData[4] = rs.getString("BallNumber");
					
					model.addRow(columnData);
				}
			} catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		} else {
			JOptionPane.showMessageDialog(null, "conn is null!");
		}
	}
	
	public void getBrands() {
		String sql = "SELECT * FROM dataform";
		try {
			String storedBrand = "";
			Boolean flag;
			pst = conn.prepareStatement(sql);
			
			rs = pst.executeQuery();
			
			brandsColumn.clear();
			while(rs.next()) {
				flag = true;
				storedBrand = rs.getString("Brand");
				for(int i = 0; i < brandsColumn.size(); i++) {
					if(storedBrand.equals(brandsColumn.get(i))) {
						flag = false;
					}
				}
				if(flag || brandsColumn.size() == 0) {
					brandsColumn.add(storedBrand);
				}
			}
			brandsColumn.sort(String::compareToIgnoreCase);		
			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void getTypes(String brand) {
		String sql = "SELECT * FROM dataform WHERE Brand = ?";
		try {
			String storedType;
			Boolean flag;
			pst = conn.prepareStatement(sql);
			
			pst.setString(1, brand);
			
			rs = pst.executeQuery();
			
			typesColumn.clear(); // Resets types column for new query
			while(rs.next()) {
				flag = true;
				storedType = rs.getString("Type");
				for(int i = 0; i < typesColumn.size(); i++) {
					if(storedType.equals(typesColumn.get(i))) {
						flag = false;
					}
				}
				if(flag || typesColumn.size() == 0) {
					typesColumn.add(storedType);
				}
			}
			typesColumn.sort(String::compareToIgnoreCase);
			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void refreshBrandType() {
		// Gets the ball brands/types in the database and displays relevant info in table based on brand/type selection button combo
		brandPanel.removeAll();
		getBrands();
		for(int i = 0; i < brandsColumn.size(); i++) {
			JButton brand = new JButton(brandsColumn.get(i));
			brand.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					brandCombo.setText(brand.getText());
					newBrandText.setText(brand.getText());
					selectedBrand = brand.getText();
					
					typePanel.removeAll();
					getTypes(brand.getText());
					for(int j = 0; j < typesColumn.size(); j++) {
						JButton type = new JButton(typesColumn.get(j));
						type.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent d) {
								typeCombo.setText(type.getText());
								newTypeText.setText(type.getText());
								selectedType = type.getText();
								
								updateTableByBrandType(brand.getText(), type.getText());
							}
						});
						typePanel.add(type);
						typePanel.revalidate();
						typePanel.repaint();
						
					}
				}
			});
			brandPanel.add(brand);
			brandPanel.revalidate();
			brandPanel.repaint();
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
		refreshBrandType();
		
		Object col[] = {"Quantity", "Color", "Logo", "Pattern", "Ball Number"};
		model.setColumnIdentifiers(col);
		table.setModel(model);
		
		
		model.setRowCount(0);
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
		
		newBrandText.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Ball Type");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		newTypeText.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Color");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel colorInfoPanel = new JPanel();
		JPanel colorTextPanel = new JPanel();
		JPanel colorCheckboxPanel = new JPanel();
		
		//newColorText1 = new JTextField();
		//newColorText1.setColumns(10);
		//newColorText1.setText("WHITE"); // Default Value
		
		newColorText1.setColumns(10);
		newColorText2.setColumns(10);
		
		brandCombo.setEditable(false);
		typeCombo.setEditable(false);
		
		JCheckBox isHalfAndHalf = new JCheckBox("Half & Half");
		
		colorInfoPanel.setLayout(new GridLayout(0, 2, 0, 0));
		colorCheckboxPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		ColorBarPanel colorBar1 = new ColorBarPanel(gifPanel, newColorText1, "");
		ColorBarPanel colorBar2 = new ColorBarPanel(gifPanel, newColorText1, "th");
		ColorBarPanel colorBar3 = new ColorBarPanel(gifPanel, newColorText2, "bh");
		
		colorPanel.setLayout(new GridLayout(1, 0, 0, 0));
		colorPanel.add(colorBar1);
		
		colorPanel.setLayout(new GridLayout(1, 0, 0, 0));
		colorPanel.add(colorBar1);
		colorTextPanel.setLayout(new GridLayout(1, 0, 10, 0));
		colorTextPanel.add(newColorText1);
		
		isHalfAndHalf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				colorPanel.removeAll();
				colorTextPanel.removeAll();
				newColorText1.setText("");
				newColorText1.setText("");
				
				if(isHalfAndHalf.isSelected()) {
					colorPanel.setLayout(new GridLayout(2, 0, 0, 0));
					colorPanel.add(colorBar2);
					colorPanel.add(colorBar3);
					colorTextPanel.setLayout(new GridLayout(2, 0, 10, 0));
					colorTextPanel.add(newColorText1);
					colorTextPanel.add(newColorText2);
					inputPanel.revalidate();
					inputPanel.repaint();
				} else {
					colorPanel.setLayout(new GridLayout(1, 0, 0, 0));
					colorPanel.add(colorBar1);
					colorTextPanel.setLayout(new GridLayout(1, 0, 10, 0));
					colorTextPanel.add(newColorText1);
					inputPanel.revalidate();
					inputPanel.repaint();
				}
			}
		});
		
		colorCheckboxPanel.add(isHalfAndHalf);
		
		colorInfoPanel.add(colorTextPanel);
		colorInfoPanel.add(colorCheckboxPanel);
		
		JLabel lblNewLabel_4 = new JLabel("Pattern");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel patternPanel = new JPanel();
		JComboBox<String> patternCombo = new JComboBox<>();
		patternCombo.addItem("NONE");
		patternCombo.setSelectedItem("NONE");
		
		newPatternText = new JTextField();
		newPatternText.setColumns(10);
		
		JCheckBox isPatternCheckbox = new JCheckBox("N/A");
		isPatternCheckbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isPatternCheckbox.isSelected()) {
					newPatternText.setText("N/A");
				} else {
					newPatternText.setText("");
				}
			}
		});
		
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
		isLogoCheckbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isLogoCheckbox.isSelected()) {
					newLogoText.setText("N/A");
				} else {
					newLogoText.setText("");
				}
			}
		});
		
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
		inputPanel.add(colorInfoPanel);
		inputPanel.add(lblNewLabel_5);
		
		JPanel ballNumberPanel = new JPanel();
		inputPanel.add(ballNumberPanel);
		ballNumberPanel.setLayout(new GridLayout(1, 0, 10, 0));
		
		JTextField ballNumberText = new JTextField();
		
		JPanel onePanel = new JPanel();
		onePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		onePanel.setBackground(new Color(255, 255, 255));
		onePanel.setLayout(new GridBagLayout());
		onePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Action to perform on click
            	ballNumberText.setText("1");
            }
        });
		ballNumberPanel.add(onePanel);
		JLabel lblNewLabel_17 = new JLabel("1");
		onePanel.add(lblNewLabel_17);
		
		JPanel twoPanel = new JPanel();
		twoPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		twoPanel.setBackground(new Color(255, 255, 255));
		twoPanel.setLayout(new GridBagLayout());
		twoPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Action to perform on click
            	ballNumberText.setText("2");
            }
        });
		ballNumberPanel.add(twoPanel);
		JLabel lblNewLabel_18 = new JLabel("2");
		twoPanel.add(lblNewLabel_18);
		
		JPanel threePanel = new JPanel();
		threePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		threePanel.setBackground(new Color(255, 255, 255));
		threePanel.setLayout(new GridBagLayout());
		threePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Action to perform on click
            	ballNumberText.setText("3");
            }
        });
		ballNumberPanel.add(threePanel);
		JLabel lblNewLabel_19 = new JLabel("3");
		threePanel.add(lblNewLabel_19);
		
		JPanel fourPanel = new JPanel();
		fourPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		fourPanel.setBackground(new Color(255, 255, 255));
		fourPanel.setLayout(new GridBagLayout());
		fourPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Action to perform on click
            	ballNumberText.setText("4");
            }
        });
		ballNumberPanel.add(fourPanel);
		JLabel lblNewLabel_20 = new JLabel("4");
		fourPanel.add(lblNewLabel_20);
		
		JPanel panel_3 = new JPanel();
		inputPanel.add(panel_3);
		panel_3.setLayout(new GridLayout(1, 0, 10, 0));
		
		panel_3.add(ballNumberText);
		
		JPanel panel_20 = new JPanel();
		panel_3.add(panel_20);
		
		previewPanel.add(gifPanel);
		
		JPanel titlePanel = new JPanel();
		titlePanel.add(new JLabel("New Ball Entry"));
		
		headerPanel.add(titlePanel, BorderLayout.NORTH);	
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
		
		filterPanel.add(new JLabel("Filters       "));
		
		JCheckBox isLogoEnabled = new JCheckBox("Disable Logos");
		JCheckBox isPatternsEnabled = new JCheckBox("Disable Patterns");
		
		filterPanel.add(isLogoEnabled);
		filterPanel.add(isPatternsEnabled);
		
		isLogoEnabled.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isLogoEnabled.isSelected()) {
					System.out.println("EEEEEE");
				} else {
					System.out.println("AAAAAA");
				}
			}
		});
		
		databasePanel.add(filterPanel, BorderLayout.NORTH);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setDefaultEditor(Object.class, null); // Makes table not editable
		scrollPane.setViewportView(table);
		
		JPanel brandTypePanel = new JPanel();
		brandTypePanel.setLayout(new GridLayout(0, 2, 0, 0));
		scrollPane.setRowHeaderView(brandTypePanel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EmptyBorder(0, 10, 10, 10));
		panel_2.setLayout(new BorderLayout(0, 0));
		JPanel panel_44 = new JPanel();
		panel_44.setBorder(new EmptyBorder(0, 10, 10, 10));
		panel_44.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_21 = new JLabel("Brand Select");
		panel_2.add(lblNewLabel_21, BorderLayout.NORTH);
		JLabel lblNewLabel_22 = new JLabel("Type Select");
		panel_44.add(lblNewLabel_22, BorderLayout.NORTH);
		
		brandPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
		panel_2.add(brandPanel, BorderLayout.CENTER);
		brandPanel.setLayout(new BoxLayout(brandPanel, BoxLayout.Y_AXIS));
		
		typePanel.setBorder(new EmptyBorder(10, 0, 0, 0));
		panel_44.add(typePanel, BorderLayout.CENTER);
		typePanel.setLayout(new BoxLayout(typePanel, BoxLayout.Y_AXIS));
		
		brandTypePanel.add(panel_2);	
		brandTypePanel.add(panel_44);
        
		JPanel actionPanel = new JPanel();
		panel.add(actionPanel, BorderLayout.SOUTH);
		
		JButton entryButton = new JButton("Add Entry");
		entryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Special logic for colors
				String colorEntry;
				if(newColorText2.getText().equals("")) {
					colorEntry = newColorText1.getText();
				} else {
					colorEntry = newColorText1.getText() + "/" + newColorText2.getText();
				}
				
				// Check to see if exact entry already exists
				String sql = "SELECT Quantity FROM dataform WHERE Brand = ? AND Type = ? AND Color = ? AND Logo = ? AND Pattern = ? AND BallNumber = ?";
				try {
					pst = conn.prepareStatement(sql);
					pst.setString(1, newBrandText.getText());
					pst.setString(2, newTypeText.getText());
					pst.setString(3, colorEntry);
					pst.setString(4, newLogoText.getText());
					pst.setString(5, newPatternText.getText());
					pst.setString(6, ballNumberText.getText());
					
					rs = pst.executeQuery();
					if(rs.next()) {
						sql = "UPDATE dataform SET Quantity = Quantity + 1 WHERE Brand = ? AND Type = ? AND Color = ? AND Logo = ? AND Pattern = ? AND BallNumber = ?";
						try {
							pst = conn.prepareStatement(sql);
							pst.setString(1, newBrandText.getText());
							pst.setString(2, newTypeText.getText());
							pst.setString(3, colorEntry);
							pst.setString(4, newLogoText.getText());
							pst.setString(5, newPatternText.getText());
							pst.setString(6, ballNumberText.getText());
							
							
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
							pst.setString(4, colorEntry);
							pst.setString(5, newLogoText.getText());
							pst.setString(6, newPatternText.getText());
							pst.setString(7, ballNumberText.getText());
							
							
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
				
				refreshBrandType();
				
				// If extra types were added, find a way to refresh them without needing to click the already selected brand
			}
		});
		actionPanel.add(entryButton);
		
		JButton refreshButton = new JButton("Refresh Table");
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setRowCount(0);
			}
		}); 
		actionPanel.add(refreshButton);
		
		JButton deleteButton = new JButton("Delete Selected");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            // Get the selected row index
	            int selectedRow = table.getSelectedRow();
	            if (selectedRow != -1) { // Check if a row is selected
	            	deleteFromDatabase(selectedBrand, selectedType, table.getValueAt(selectedRow, 1).toString(), table.getValueAt(selectedRow, 2).toString(), table.getValueAt(selectedRow, 3).toString(), table.getValueAt(selectedRow, 4).toString());
	                model.removeRow(selectedRow); // Remove the selected row
	            } else {
	                JOptionPane.showMessageDialog(frame, "No Row Selected", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	            
	            // Not as used, may consider reworking duplicate section?
				brandPanel.removeAll();
				getBrands();
				refreshBrandType();
			}
		}); 
		actionPanel.add(deleteButton);
	}
}
