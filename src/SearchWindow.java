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

public class SearchWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField txtfood_name;
	private JTextField txtfood_price;
	private JTextField txtfood_id;
	private JTextField txtclient_cantname;
	private JTextField txtfood_category;
	private JTextField txtuser_fname;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchWindow frame = new SearchWindow();
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
	public SearchWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1010, 610);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMenuManagementSystem = new JLabel("User Window");
		lblMenuManagementSystem.setFont(new Font("Dialog", Font.BOLD, 20));
		lblMenuManagementSystem.setBounds(426, 12, 156, 43);
		contentPane.add(lblMenuManagementSystem);
		
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
	                    String food_id, food_category, food_name, food_price;
	                    while(rs.next())
	                    {
	                    	food_id = rs.getString(1);
	                    	food_category = rs.getString(2); 
	                    	food_name = rs.getString(3); 
	                    	food_price = rs.getString(4);
	                    	client_cantname = rs.getString(5);
	                    	String[] row = {food_id, food_category, food_name, food_price, client_cantname};
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
		btnShowMenu.setBounds(648, 293, 131, 32);
		contentPane.add(btnShowMenu);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 67, 590, 464);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel tbModel = (DefaultTableModel) table.getModel();
				String tbfood_id = tbModel.getValueAt(table.getSelectedRow(), 0).toString();
				String tbfood_category = tbModel.getValueAt(table.getSelectedRow(), 1).toString();
				String tbfood_name = tbModel.getValueAt(table.getSelectedRow(), 2).toString();
				String tbfood_price = tbModel.getValueAt(table.getSelectedRow(), 4).toString();
				
				
				txtfood_id.setText(tbfood_id);
				txtfood_category.setText(tbfood_category);
				txtfood_name.setText(tbfood_name);
				txtfood_price.setText(tbfood_price);
				}
		});
		scrollPane.setViewportView(table);
		
		JLabel lblFoodName = new JLabel("Food Name:");
		lblFoodName.setFont(new Font("Dialog", Font.BOLD, 14));
		lblFoodName.setBounds(648, 179, 94, 24);
		contentPane.add(lblFoodName);
		
		JLabel lblFoodDescrption = new JLabel("Food Descrption:");
		lblFoodDescrption.setFont(new Font("Dialog", Font.BOLD, 14));
		lblFoodDescrption.setBounds(648, 300, 136, 24);
		contentPane.add(lblFoodDescrption);
		lblFoodDescrption.setVisible(false);
		
		JLabel lblFoodPrice = new JLabel("Food Price:");
		lblFoodPrice.setFont(new Font("Dialog", Font.BOLD, 14));
		lblFoodPrice.setBounds(648, 216, 94, 24);
		contentPane.add(lblFoodPrice);
		
		JLabel lblfood_id = new JLabel("Food Id:");
		lblfood_id.setFont(new Font("Dialog", Font.BOLD, 14));
		lblfood_id.setBounds(648, 179, 94, 24);
		contentPane.add(lblfood_id);
		lblfood_id.setVisible(false);
		
		txtfood_name = new JTextField();
		txtfood_name.setBounds(787, 180, 185, 24);
		contentPane.add(txtfood_name);
		txtfood_name.setColumns(10);
		
		txtfood_price = new JTextField();
		txtfood_price.setColumns(10);
		txtfood_price.setBounds(787, 216, 185, 24);
		contentPane.add(txtfood_price);
		
		txtfood_id = new JTextField();
		txtfood_id.setColumns(10);
		txtfood_id.setBounds(787, 180, 185, 24);
		contentPane.add(txtfood_id);
		txtfood_id.setVisible(false);
		
		txtclient_cantname = new JTextField();
		txtclient_cantname.setColumns(10);
		txtclient_cantname.setBounds(787, 105, 185, 24);
		contentPane.add(txtclient_cantname);
		
		JLabel lblclient_cantname = new JLabel("Search Canteen:");
		lblclient_cantname.setFont(new Font("Dialog", Font.BOLD, 14));
		lblclient_cantname.setBounds(648, 104, 136, 24);
		contentPane.add(lblclient_cantname);
		
		JLabel lblfood_cate = new JLabel("Food Category:");
		lblfood_cate.setFont(new Font("Dialog", Font.BOLD, 14));
		lblfood_cate.setBounds(648, 140, 131, 24);
		contentPane.add(lblfood_cate);
		lblfood_cate.setVisible(false);
		
		txtfood_category = new JTextField();
		txtfood_category.setColumns(10);
		txtfood_category.setBounds(787, 140, 185, 24);
		contentPane.add(txtfood_category);
		txtfood_category.setVisible(false);
		
		txtuser_fname = new JTextField();
		txtuser_fname.setBounds(787, 140, 185, 24);
		contentPane.add(txtuser_fname);
		txtuser_fname.setColumns(10);
		
		JLabel lbluser_fname = new JLabel("User Name:");
		lbluser_fname.setFont(new Font("Dialog", Font.BOLD, 14));
		lbluser_fname.setBounds(648, 140, 131, 24);
		contentPane.add(lbluser_fname);
		
		JLabel lblquantity = new JLabel("Quantity:");
		lblquantity.setFont(new Font("Dialog", Font.BOLD, 14));
		lblquantity.setBounds(648, 252, 94, 24);
		contentPane.add(lblquantity);
		
		final JSpinner txtquantity = new JSpinner();
		txtquantity.setBounds(787, 253, 185, 24);
		contentPane.add(txtquantity);
		
		JButton btnAddtoCart = new JButton("Add to Cart");
		btnAddtoCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.setModel(new DefaultTableModel());
				if(txtfood_name.getText().equals("") || txtquantity.getValue().equals("") || txtfood_price.getText().equals("") || txtuser_fname.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Please Enter all the Details:");
				}
				else
				{
					String user_fname=txtuser_fname.getText();
					String quantity= txtquantity.getValue().toString();
					String food_name=txtfood_name.getText();
					String food_price=txtfood_price.getText();
					int food_id=Integer.parseInt(txtfood_id.getText());
					int user_id=0;
					//String client_id=null;
					try 
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
						
						String query = "INSERT INTO cart (user_fname, food_name, food_price, quantity, user_id, food_id) VALUES (?, ?, ?, ?, ?, ?);";
						PreparedStatement pst1 = conn.prepareStatement(query);
			            
			            pst1.setString(1, user_fname);
			            pst1.setString(2, food_name);
			            pst1.setString(3, food_price);
			            pst1.setString(4, quantity);
			            pst1.setInt(5, user_id);
			            pst1.setInt(6, food_id);
			            pst1.executeUpdate();
			            pst1.close();
	                    conn.close();
	                    
						txtfood_name.setText("");
						txtfood_id.setText("");
						txtfood_price.setText("");
						txtquantity.setValue(0);
						JOptionPane.showMessageDialog(null, "Data added seccessfully");
					}
					catch(Exception ex)
					{
						JOptionPane.showMessageDialog(null, ex);
					}
				}
			}
		});
		btnAddtoCart.setBounds(841, 293, 131, 32);
		contentPane.add(btnAddtoCart);
		
		JButton btnViewCart = new JButton("ViewCart");
		btnViewCart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				Cart cart= new Cart();
				cart.setVisible(true);
			}
		});
		btnViewCart.setBounds(648, 337, 131, 32);
		contentPane.add(btnViewCart);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				LoginPage LogPage= new LoginPage();
				LogPage.setVisible(true);
			}
		});
		btnBack.setBounds(841, 337, 131, 32);
		contentPane.add(btnBack);
		
		JLabel backgroundImg = new JLabel("");
		Image img= new ImageIcon(this.getClass().getResource("polotno(3).png")).getImage();
		backgroundImg.setIcon(new ImageIcon(img));
		backgroundImg.setBounds(0, 0, 1010, 610);
		getContentPane().add(backgroundImg);
	}
}
