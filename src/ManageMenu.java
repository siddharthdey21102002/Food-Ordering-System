import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import java.awt.Color;


public class ManageMenu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField txtfood_name;
	private JTextField txtfood_price;
	private JTextArea txtfood_des;
	private JTextField txtfood_id;
	private JTextField txtclient_cantname;
	private JTextField txtfood_category;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageMenu frame = new ManageMenu();
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
	public ManageMenu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1010, 610);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMenuManagementSystem = new JLabel("Menu Management System");
		lblMenuManagementSystem.setFont(new Font("Dialog", Font.BOLD, 20));
		lblMenuManagementSystem.setBounds(351, 12, 317, 43);
		contentPane.add(lblMenuManagementSystem);
		
		JButton btnAddToMenu = new JButton("Add to Menu");
		btnAddToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.setModel(new DefaultTableModel());
				if(txtfood_name.getText().equals("") || txtfood_des.getText().equals("") || txtfood_price.getText().equals("") || txtfood_category.getText().equals("") || txtclient_cantname.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Please Enter all the Details:");
				}
				else
				{
					String client_cantname=txtclient_cantname.getText();
					String food_category=txtfood_category.getText();
					String food_name=txtfood_name.getText();
					String food_price=txtfood_price.getText();
					String food_des=txtfood_des.getText();
					//String client_id=null;
					try 
					{
						Class.forName("org.postgresql.Driver");
						Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/foodorderingsystem", "postgres","1021");
						String query = "INSERT INTO food_items (food_category, food_name, food_des, food_price, client_cantname) VALUES (?, ?, ?, ?, ?);";
						

			            PreparedStatement pst1 = conn.prepareStatement(query);
			            
			            pst1.setString(1, food_category);
			            pst1.setString(2, food_name);
			            pst1.setString(3, food_des);
			            pst1.setString(4, food_price);
			            pst1.setString(5, client_cantname);
			            pst1.executeUpdate();
			            pst1.close();
	                    conn.close();
	                    
						txtfood_name.setText("");
						txtfood_des.setText("");
						txtfood_price.setText("");
						txtfood_category.setText("");
						JOptionPane.showMessageDialog(null, "Data added seccessfully");
					}
					catch(Exception ex)
					{
						JOptionPane.showMessageDialog(null, ex);
					}
				}
			}
		});
		btnAddToMenu.setBounds(441, 518, 131, 32);
		contentPane.add(btnAddToMenu);
		
		JButton btnRemove = new JButton("Remove item");
		btnRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(table.getSelectedRowCount()==1)
				{
					DefaultTableModel model=(DefaultTableModel)table.getModel();
					model.removeRow(table.getSelectedRow());
					try 
					{
						int food_id = Integer.parseInt(txtfood_id.getText());
						Class.forName("org.postgresql.Driver");
						Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/foodorderingsystem", "postgres","1021");
						String query = "delete from food_items where food_id = "+food_id+";";
						PreparedStatement pst = conn.prepareStatement(query);
			            pst.executeUpdate();
			            pst.close();
	                    conn.close();
			            txtfood_id.setText("");
						txtfood_name.setText("");
						txtfood_des.setText("");
						txtfood_price.setText("");
						txtfood_category.setText("");
						JOptionPane.showMessageDialog(null, "Data Droped seccessfully");
					}
					catch(Exception ex)
					{
						JOptionPane.showMessageDialog(null, ex);
					}
					table.setModel(new DefaultTableModel());
				}
				else
				{
					if(table.getSelectedRowCount()==0)
					{
						JOptionPane.showMessageDialog(null, "Table is Empty..");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Please select Single Row to Delete..");
					}
				}
			}
		});
		btnRemove.setBounds(298, 518, 131, 32);
		contentPane.add(btnRemove);
		
		JButton btnUpdateMenu = new JButton("Update Menu");
		btnUpdateMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try 
				{
					DefaultTableModel model=(DefaultTableModel)table.getModel();
					if(table.getSelectedRowCount()==1)
					{
						int food_id = Integer.parseInt(txtfood_id.getText());
						String food_name = txtfood_name.getText();
						String food_price = txtfood_price.getText();
						String food_des= txtfood_des.getText();
						String food_category=txtfood_category.getText();
						String client_cantname=txtclient_cantname.getText();
						
						Class.forName("org.postgresql.Driver");
						Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/foodorderingsystem", "postgres","1021");
						String query = "UPDATE food_items SET food_category='"+food_category+"', food_name = '"+food_name+"', food_des = '"+food_des+"', food_price = '"+food_price+"', client_cantname = '"+client_cantname+"' WHERE food_id = '"+food_id+"';";
			            PreparedStatement pst = conn.prepareStatement(query);
	                    
	                    model.setValueAt(food_category, table.getSelectedRow(), 0);
						model.setValueAt(food_id, table.getSelectedRow(), 1);
						model.setValueAt(food_name, table.getSelectedRow(), 2);
						model.setValueAt(food_des, table.getSelectedRow(), 3);
						model.setValueAt(food_price, table.getSelectedRow(), 4);
						model.setValueAt(client_cantname, table.getSelectedRow(), 5);
						
						txtfood_id.setText("");
						txtfood_name.setText("");
						txtfood_des.setText("");
						txtfood_price.setText("");
						txtfood_category.setText("");
						
						pst.executeUpdate();
			            pst.close();
	                    conn.close();
						
						JOptionPane.showMessageDialog(null, "Updated Successfully");
					}
					else
					{
						if(table.getRowCount()==0)
						{
							JOptionPane.showMessageDialog(null, "Table is Empty..");
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Please select Single Row to Update");
						}
					}
					table.setModel(new DefaultTableModel());
				}
				catch(Exception er)
				{
					JOptionPane.showMessageDialog(null,er);
				}
			}
		});
		btnUpdateMenu.setBounds(155, 518, 131, 32);
		contentPane.add(btnUpdateMenu);
		
		JButton btnShowMenu = new JButton("Show Menu");
		btnShowMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String client_cantname=txtclient_cantname.getText();
				if(txtclient_cantname.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Enter Canteen Name..");
				}
				else
				{
					try
					{
						table.setModel(new DefaultTableModel());
						Class.forName("org.postgresql.Driver");
						Connection connection=DriverManager.getConnection("jdbc:postgresql://localhost:5432/foodorderingsystem", "postgres","1021");
						Statement st = connection.createStatement();
	                    String query = "select food_id, food_category, food_name, food_des, food_price, client_cantname from food_items where client_cantname = '"+client_cantname+"';";
	                    ResultSet rs=st.executeQuery(query);
	                    ResultSetMetaData rsmd=rs.getMetaData();
	                    DefaultTableModel model=(DefaultTableModel)table.getModel();
	                    int cols=rsmd.getColumnCount();
	                    String[] colName=new String[cols];
	                    for(int i=0;i<cols;i++)
	                    	colName[i]=rsmd.getColumnName(i+1);
	                    model.setColumnIdentifiers(colName);
	                    String food_id, food_category, food_name, food_des, food_price;
	                    while(rs.next())
	                    {
	                    	food_id = rs.getString(1);
	                    	food_category = rs.getString(2); 
	                    	food_name = rs.getString(3); 
	                    	food_des = rs.getString(4);
	                    	food_price = rs.getString(5);
	                    	client_cantname = rs.getString(6);
	                    	String[] row = {food_id, food_category, food_name, food_des, food_price, client_cantname};
	                    	model.addRow(row);
	                    }
	                    st.close();
	                    connection.close();
					}
					catch(Exception e1)
					{
						JOptionPane.showMessageDialog(null, e1);
					}
				}
			}
		});
		btnShowMenu.setBounds(12, 518, 131, 32);
		contentPane.add(btnShowMenu);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 67, 590, 428);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel tbModel = (DefaultTableModel) table.getModel();
				String tbfood_id = tbModel.getValueAt(table.getSelectedRow(), 0).toString();
				String tbfood_category = tbModel.getValueAt(table.getSelectedRow(), 1).toString();
				String tbfood_name = tbModel.getValueAt(table.getSelectedRow(), 2).toString();
				String tbfood_des= tbModel.getValueAt(table.getSelectedRow(), 3).toString();
				String tbfood_price = tbModel.getValueAt(table.getSelectedRow(), 4).toString();
				
				
				txtfood_id.setText(tbfood_id);
				txtfood_category.setText(tbfood_category);
				txtfood_name.setText(tbfood_name);
				txtfood_des.setText(tbfood_des);
				txtfood_price.setText(tbfood_price);
				}
		});
		scrollPane.setViewportView(table);
		
		JLabel lblFoodName = new JLabel("Food Name:");
		lblFoodName.setFont(new Font("Dialog", Font.BOLD, 14));
		lblFoodName.setBounds(644, 175, 94, 24);
		contentPane.add(lblFoodName);
		
		JLabel lblFoodDescrption = new JLabel("Food Descrption:");
		lblFoodDescrption.setFont(new Font("Dialog", Font.BOLD, 14));
		lblFoodDescrption.setBounds(644, 259, 136, 24);
		contentPane.add(lblFoodDescrption);
		
		JLabel lblFoodPrice = new JLabel("Food Price:");
		lblFoodPrice.setFont(new Font("Dialog", Font.BOLD, 14));
		lblFoodPrice.setBounds(644, 210, 94, 24);
		contentPane.add(lblFoodPrice);
		
		JLabel lblfood_id = new JLabel("Food Id:");
		lblfood_id.setFont(new Font("Dialog", Font.BOLD, 14));
		lblfood_id.setBounds(644, 138, 94, 24);
		contentPane.add(lblfood_id);
		
		txtfood_name = new JTextField();
		txtfood_name.setBounds(783, 175, 185, 24);
		contentPane.add(txtfood_name);
		txtfood_name.setColumns(10);
		
		txtfood_price = new JTextField();
		txtfood_price.setColumns(10);
		txtfood_price.setBounds(783, 211, 185, 24);
		contentPane.add(txtfood_price);
		
		txtfood_id = new JTextField();
		txtfood_id.setColumns(10);
		txtfood_id.setBounds(783, 139, 185, 24);
		contentPane.add(txtfood_id);
		txtfood_id.setVisible(true);
		
		txtfood_des = new JTextArea();
		txtfood_des.setBorder(new LineBorder(new Color(119, 118, 123)));
		txtfood_des.setBounds(644, 295, 324, 152);
		contentPane.add(txtfood_des);	
		
		txtclient_cantname = new JTextField();
		txtclient_cantname.setColumns(10);
		txtclient_cantname.setBounds(783, 67, 185, 24);
		contentPane.add(txtclient_cantname);
		
		JLabel lblclient_cantname = new JLabel("Canteen Name:");
		lblclient_cantname.setFont(new Font("Dialog", Font.BOLD, 14));
		lblclient_cantname.setBounds(644, 63, 121, 24);
		contentPane.add(lblclient_cantname);
		
		JLabel lblfood_cate = new JLabel("Food Category:");
		lblfood_cate.setFont(new Font("Dialog", Font.BOLD, 14));
		lblfood_cate.setBounds(644, 99, 131, 24);
		contentPane.add(lblfood_cate);
		
		txtfood_category = new JTextField();
		txtfood_category.setColumns(10);
		txtfood_category.setBounds(783, 103, 185, 24);
		contentPane.add(txtfood_category);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ClientWindow ClientPage= new ClientWindow();
				ClientPage.setVisible(true);
			}
		});
		btnBack.setBounds(755, 478, 131, 32);
		contentPane.add(btnBack);
		
		JLabel backgroundImg = new JLabel("");
		Image img= new ImageIcon(this.getClass().getResource("polotno(3).png")).getImage();
		backgroundImg.setIcon(new ImageIcon(img));
		backgroundImg.setBounds(0, 0, 1010, 610);
		getContentPane().add(backgroundImg);
	}
}
