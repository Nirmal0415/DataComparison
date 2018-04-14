package main;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Desktop;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import sun.applet.Main;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import javax.swing.AbstractAction;
import javax.swing.Action;

public class GUI{

	public JFrame frame;
	private JTable table;
	private final Action action = new SwingAction();
		/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
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
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Data Migration");
		//frame.setBackground(Color.WHITE);

		/*try {
			frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("./img/Logo.jpg")))));
		} catch (IOException e2) {
			System.out.println("Image doesn't exists");
			e2.printStackTrace();
		}*/
		 frame.setResizable(true);
		// frame.setSize(621,685);
		// frame.setVisible(true);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setFont(new Font("Times New Roman", Font.PLAIN, 15));
		frame.setBounds(100, 100, 1046, 525);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);


		
		JLabel lblAreYouUpdated = new JLabel("Are you updated the Execution driver sheet");
		lblAreYouUpdated.setForeground(new Color(51, 51, 102));
		lblAreYouUpdated.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblAreYouUpdated.setBounds(22, 134, 300, 26);
		frame.getContentPane().add(lblAreYouUpdated);

		JButton Resultsbutton = new JButton("Results sheet");

		Resultsbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().open(new java.io.File("./data/result.xlsx"));
				} catch (IOException e1) {

					e1.printStackTrace();
				}
			}
		});
		Resultsbutton.setBounds(367, 261, 132, 23);
		frame.getContentPane().add(Resultsbutton);

		JButton HtmlButton = new JButton("HTML Log");
		HtmlButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().open(new java.io.File("./log/HTML_Log.html"));
				} catch (IOException e1) {

					e1.printStackTrace();
				}
			}
		});
		HtmlButton.setBounds(367, 328, 132, 23);
		frame.getContentPane().add(HtmlButton);

		JButton Textbutton = new JButton("Text Log");
		Textbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().open(new java.io.File("./log/testlog.log"));
				} catch (IOException e1) {

					e1.printStackTrace();
				}
			}
		});
		Textbutton.setBounds(367, 395, 132, 23);
		frame.getContentPane().add(Textbutton);

		JLabel lblClickHereTo = new JLabel("Click here to get the execution result");
		lblClickHereTo.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));
		lblClickHereTo.setBounds(22, 261, 231, 23);
		frame.getContentPane().add(lblClickHereTo);

		JLabel lblClickHereTo_1 = new JLabel("Click here to get the HTML Log");
		lblClickHereTo_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));
		lblClickHereTo_1.setBounds(22, 328, 231, 23);
		frame.getContentPane().add(lblClickHereTo_1);

		JLabel lblClickHereTo_2 = new JLabel("Click here to get the execution log file in detail");
		lblClickHereTo_2.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));
		lblClickHereTo_2.setBounds(22, 395, 270, 23);
		frame.getContentPane().add(lblClickHereTo_2);

			
//		JProgressBar progressBar = new JProgressBar();
//		progressBar.setStringPainted(true);
//		progressBar.setBounds(230, 236, 146, 14);
//		frame.getContentPane().add(progressBar);
		
		
		
		JButton comparebutton = new JButton("Compare");
		comparebutton.setForeground(new Color(51, 153, 255));
		comparebutton.setFont(new Font("Arial Black", Font.BOLD, 16));
		comparebutton.setBackground(UIManager.getColor("EditorPane.selectionBackground"));
		comparebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					 
				     
//					progressBar.setVisible(true);
					
					LinkedHashMap<String, String> rSummary = Config_Main.mainmethod();
				
//					progressBar.setVisible(false);
//	Table configuration	
					table = new JTable(toTableModel(rSummary));
					table.setBounds(560, 162, 371, 190);
					frame.getContentPane().add(table);
					frame.setVisible(true);
					table.setEnabled(false);  
//					table.setPreferredScrollableViewportSize(table.getPreferredSize());
//					table.setFillsViewportHeight(true);
					table.setRowHeight(30);
					table.setRowHeight(3, 40);
					Color color = UIManager.getColor("Table.gridColor");
					MatteBorder border = new MatteBorder(1, 1, 1, 1, color);
					table.setBorder(border);
					setCellsAlignment(table, SwingConstants.CENTER);
					
					
//		Jprogress bar
					

					
					
		
					
				} catch (IOException e1) {

					e1.printStackTrace();
				} catch (Exception e1) {

					e1.printStackTrace();
				}
								
		}	
			
			   
			
		});
		
				
		comparebutton.setBounds(230, 188, 142, 36);
		frame.getContentPane().add(comparebutton);

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addItem("Yes");
		comboBox.addItem("No");

		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ("No".equals(comboBox.getSelectedItem())) {
					Textbutton.setEnabled(false);
					HtmlButton.setEnabled(false);
					Resultsbutton.setEnabled(false);
					comparebutton.setEnabled(false);

				} else if ("Yes".equals(comboBox.getSelectedItem())) {
					Textbutton.setEnabled(true);
					HtmlButton.setEnabled(true);
					Resultsbutton.setEnabled(true);
					comparebutton.setEnabled(true);

				}
			}
		});

		comboBox.setBounds(397, 135, 81, 26);
		frame.getContentPane().add(comboBox);

		
		
		JLabel lblDataMigrationValidation = new JLabel("          Data Migration Validation");
		lblDataMigrationValidation.setForeground(new Color(51, 153, 153));
		lblDataMigrationValidation.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblDataMigrationValidation.setBounds(374, 30, 320, 56);
		frame.getContentPane().add(lblDataMigrationValidation);
		
				
		JLabel lblNewLabel = new JLabel("LG logo");
		lblNewLabel.setBackground(Color.BLACK);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setIcon(new ImageIcon("./img/Liberty.jpg"));
		lblNewLabel.setBounds(10, 11, 170, 103);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblExecutionSummary = new JLabel("Execution Summary");
		lblExecutionSummary.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblExecutionSummary.setBounds(700, 109, 132, 36);
		frame.getContentPane().add(lblExecutionSummary);
		
		

	}
	 
	public static DefaultTableModel toTableModel(Map<?,?> map) {
		
	    DefaultTableModel model = new DefaultTableModel(
	        new Object[] { "Key", "Value" }, 0
	    );
	   
	    for (Map.Entry<?,?> entry : map.entrySet()) {
	        model.addRow(new Object[] { entry.getKey(), entry.getValue() });
	        
	    }
	   
	    return model;
	    
	}
	
	
	 public static void setCellsAlignment(JTable table, int alignment)
	    {
	        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
	        rightRenderer.setHorizontalAlignment(alignment);

	        TableModel tableModel = table.getModel();

	        for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++)
	        {
	            table.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
	        }
	    }
	
	public void openWebPage(String url) {
		try {
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
		} catch (java.io.IOException e) {
			System.out.println(e.getMessage());
		}

	}
	
	
	
	
	
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
