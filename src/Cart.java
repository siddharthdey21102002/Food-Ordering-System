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
import javax.swing.JSpinner;


public class Cart extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField txtfood_name;
	private JTextField txtfood_price;
	private JTextField txtfood_id;
	private JTextField txtuser_fname;
	private JTextField txtuser_id;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cart frame = new Cart();
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
	public Cart() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1010, 610);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JSpinner txtquantity = new JSpinner();
		txtquantity.setBounds(783, 179, 185, 24);
		contentPane.add(txtquantity);
		
		JLabel lblCartWindow = new JLabel("Edit Cart");
		lblCartWindow.setFont(new Font("Dialog", Font.BOLD, 20));
		lblCartWindow.setBounds(400, 12, 141, 43);
		contentPane.add(lblCartWindow);
		
		JButton btnAddToMenu = new JButton("Add to Cart");
		btnAddToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.setModel(new DefaultTableModel());
				dispose();
				SearchWindow sw= new SearchWindow();
				sw.setVisible(true);
			}
		});
		btnAddToMenu.setBounds(837, 234, 131, 32);
		contentPane.add(btnAddToMenu);
		
		JButton btnRemove = new JButton("Remove from Cart");
		btnRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(table.getSelectedRowCount()==1)
				{
					String user_fname = txtuser_fname.getText();
					String food_name = txtfood_name.getText();
					DefaultTableModel model=(DefaultTableModel)table.getModel();
					model.removeRow(table.getSelectedRow());
					try 
					{
						int food_id = 0; 
						int user_id=0;
						Class.forName("org.postgresql.Driver");
						Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/foodorderingsystem", "postgres","1021");
						
						Statement st = conn.createStatement();
	                    String query1 = "select user_id from user_reg where user_fname = '"+user_fname+"';";
	                    ResultSet rs1=st.executeQuery(query1);
	                    while(rs1.next())
	                    {
	                    	user_id = rs1.getInt(1);
	                    }
	                    
	                    String query2 = "select food_id from food_items where food_name = '"+food_name+"';";
	                    ResultSet rs2=st.executeQuery(query2);
	                    while(rs2.next())
	                    {
	                    	food_id = rs2.getInt(1);
	                    }
						
						
						String query = "delete from cart where food_id = '"+food_id+"' and user_id = '"+user_id+"';";
						PreparedStatement pst = conn.prepareStatement(query);
			            pst.executeUpdate();
			            pst.close();
	                    conn.close();
			            txtfood_id.setText("");
						txtfood_name.setText("");
						txtfood_price.setText("");
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
		btnRemove.setBounds(313, 518, 159, 32);
		contentPane.add(btnRemove);
		
		JButton btnUpdateMenu = new JButton("Update Cart");
		btnUpdateMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try 
				{
					String user_fname = txtuser_fname.getText();
					String food_name = txtfood_name.getText();
					String food_price = txtfood_price.getText();
					String quantity= txtquantity.getValue().toString();
					int user_id=0;
					int food_id=0;
					DefaultTableModel model=(DefaultTableModel)table.getModel();
					if(table.getSelectedRowCount()==1)
					{	
						Class.forName("org.postgresql.Driver");
						Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/foodorderingsystem", "postgres","1021");
						
						Statement st = conn.createStatement();
	                    String query1 = "select user_id from user_reg where user_fname = '"+user_fname+"';";
	                    ResultSet rs1=st.executeQuery(query1);
	                    while(rs1.next())
	                    {
	                    	user_id = rs1.getInt(1);
	                    }
	                    
	                    String query2 = "select food_id from food_items where food_name = '"+food_name+"';";
	                    ResultSet rs2=st.executeQuery(query2);
	                    while(rs2.next())
	                    {
	                    	food_id = rs2.getInt(1);
	                    }
						
						String query = "UPDATE cart SET quantity = '"+quantity+"' WHERE user_id = '"+user_id+"' and food_id = '"+food_id+"';";
			            PreparedStatement pst = conn.prepareStatement(query);
			            pst.executeUpdate();
			            pst.close();
	                    conn.close();
	                    
	                    model.setValueAt(user_fname, table.getSelectedRow(), 1);
						model.setValueAt(food_name, table.getSelectedRow(), 2);
						model.setValueAt(food_price, table.getSelectedRow(), 3);
						model.setValueAt(quantity, table.getSelectedRow(), 4);
						
						txtfood_id.setText("");
						txtfood_name.setText("");
						txtfood_price.setText("");
						txtquantity.setValue(0);
						
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
		btnUpdateMenu.setBounds(170, 518, 131, 32);
		contentPane.add(btnUpdateMenu);
		
		JButton btnShowCart = new JButton("Show Cart");
		btnShowCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user_fname=txtuser_fname.getText();
				int user_id=0;
				if(txtuser_fname.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Enter User Name..");
				}
				else
				{
					try
					{
						table.setModel(new DefaultTableModel());
						Class.forName("org.postgresql.Driver");
						Connection connection=DriverManager.getConnection("jdbc:postgresql://localhost:5432/foodorderingsystem", "postgres","1021");
	                    
						Statement st = connection.createStatement();
	                    String query1 = "select user_id from user_reg where user_fname = '"+user_fname+"';";
	                    ResultSet rs1=st.executeQuery(query1);
	                    while(rs1.next())
	                    {
	                    	user_id = rs1.getInt(1);
	                    }
	                    
	                    String query = "select cart_id, user_fname, food_name, food_price, quantity from cart where user_id = '"+user_id+"';";
	                    ResultSet rs=st.executeQuery(query);
	                    ResultSetMetaData rsmd=rs.getMetaData();
	                    DefaultTableModel model=(DefaultTableModel)table.getModel();
	                    int cols=rsmd.getColumnCount();
	                    String[] colName=new String[cols];
	                    for(int i=0;i<cols;i++)
	                    	colName[i]=rsmd.getColumnName(i+1);
	                    model.setColumnIdentifiers(colName);
	                    String cart_id, food_name, food_price, quantity;
	                    while(rs.next())
	                    {
	                    	cart_id = rs.getString(1);
	                    	user_fname = rs.getString(2); 
	                    	food_name = rs.getString(3); 
	                    	food_price = rs.getString(4);
	                    	quantity= rs.getString(5);
	                    	String[] row = {cart_id, user_fname, food_name, food_price, quantity};
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
		btnShowCart.setBounds(484, 518, 131, 32);
		contentPane.add(btnShowCart);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 67, 590, 428);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel tbModel = (DefaultTableModel) table.getModel();
				String tbuser_fname = tbModel.getValueAt(table.getSelectedRow(), 1).toString();
				String tbfood_name = tbModel.getValueAt(table.getSelectedRow(), 2).toString();
				String tbfood_price = tbModel.getValueAt(table.getSelectedRow(), 3).toString();
				
				txtuser_fname.setText(tbuser_fname);
				txtfood_name.setText(tbfood_name);
				txtfood_price.setText(tbfood_price);
				txtquantity.setValue(0);
				}
		});
		scrollPane.setViewportView(table);
		
		JLabel lblFoodName = new JLabel("Food Name:");
		lblFoodName.setFont(new Font("Dialog", Font.BOLD, 14));
		lblFoodName.setBounds(644, 103, 94, 24);
		contentPane.add(lblFoodName);
		
		JLabel lblFoodPrice = new JLabel("Food Price:");
		lblFoodPrice.setFont(new Font("Dialog", Font.BOLD, 14));
		lblFoodPrice.setBounds(644, 142, 94, 24);
		contentPane.add(lblFoodPrice);
		
		JLabel lblfood_id = new JLabel("Food Id:");
		lblfood_id.setFont(new Font("Dialog", Font.BOLD, 14));
		lblfood_id.setBounds(644, 142, 94, 24);
		lblfood_id.setVisible(false);
		contentPane.add(lblfood_id);
		
		txtfood_name = new JTextField();
		txtfood_name.setBounds(783, 103, 185, 24);
		contentPane.add(txtfood_name);
		txtfood_name.setColumns(10);
		
		txtfood_price = new JTextField();
		txtfood_price.setColumns(10);
		txtfood_price.setBounds(783, 143, 185, 24);
		contentPane.add(txtfood_price);
		
		txtfood_id = new JTextField();
		txtfood_id.setColumns(10);
		txtfood_id.setBounds(783, 143, 185, 24);
		contentPane.add(txtfood_id);
		txtfood_id.setVisible(false);
		
		txtuser_fname = new JTextField();
		txtuser_fname.setColumns(10);
		txtuser_fname.setBounds(783, 67, 185, 24);
		contentPane.add(txtuser_fname);
		
		JLabel lbluser_fname = new JLabel("User Name");
		lbluser_fname.setFont(new Font("Dialog", Font.BOLD, 14));
		lbluser_fname.setBounds(644, 67, 121, 24);
		contentPane.add(lbluser_fname);
		
		JLabel lblquantity = new JLabel("Quantity");
		lblquantity.setFont(new Font("Dialog", Font.BOLD, 14));
		lblquantity.setBounds(644, 178, 131, 24);
		contentPane.add(lblquantity);
		
		txtuser_id = new JTextField();
		txtuser_id.setBounds(783, 103, 185, 24);
		contentPane.add(txtuser_id);
		txtuser_id.setVisible(false);
		txtuser_id.setColumns(10);
		
		JLabel lbluser_id = new JLabel("User id:");
		lbluser_id.setFont(new Font("Dialog", Font.BOLD, 14));
		lbluser_id.setBounds(644, 103, 121, 24);
		lbluser_id.setVisible(false);
		contentPane.add(lbluser_id);
		
		JButton btnBack = new JButton("Back");
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				SearchWindow serachwindow= new SearchWindow();
				serachwindow.setVisible(true);
			}
		});
		btnBack.setBounds(27, 518, 131, 32);
		contentPane.add(btnBack);
		
		JButton btnProceedToPay = new JButton("Proceed to Pay");
		btnProceedToPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Payment pay=new Payment();
				pay.setVisible(true);
			}
		});
		btnProceedToPay.setBounds(644, 234, 147, 32);
		contentPane.add(btnProceedToPay);
		
		JLabel backgroundImg = new JLabel("");
		Image img= new ImageIcon(this.getClass().getResource("polotno(3).png")).getImage();
		backgroundImg.setIcon(new ImageIcon(img));
		backgroundImg.setBounds(0, 0, 1010, 610);
		getContentPane().add(backgroundImg);
	}
}