import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class User_order_status extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField txtuser_fname;
	private JTextField txtclient_cantname;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User_order_status frame = new User_order_status();
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
	public User_order_status() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1010, 610);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMenuManagementSystem = new JLabel("Order Status Management");
		lblMenuManagementSystem.setFont(new Font("Dialog", Font.BOLD, 20));
		lblMenuManagementSystem.setBounds(351, 12, 317, 43);
		contentPane.add(lblMenuManagementSystem);
		
		JButton btnShowOrders = new JButton("Show Orders");
		btnShowOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String client_cantname=txtclient_cantname.getText();
				int client_id=0;
				String user_fname=txtuser_fname.getText();
				int user_id=0;
				if(txtuser_fname.getText().equals("") || txtclient_cantname.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Enter Canteen Name and User Name..");
				}
				else
				{
					try
					{
						table.setModel(new DefaultTableModel());
						Class.forName("org.postgresql.Driver");
						Connection connection=DriverManager.getConnection("jdbc:postgresql://localhost:5432/foodorderingsystem", "postgres","1021");
						Statement st = connection.createStatement();
	                    
						String query1 = "select user_id from order_status where  user_fname = '"+user_fname+"';";
						ResultSet rs1=st.executeQuery(query1);
						while(rs1.next())
	                    {
	                    	user_id = Integer.parseInt(rs1.getString(1));
	                    }
						
						String query2 = "select client_id from client_reg where  client_cantname = '"+client_cantname+"';";
						ResultSet rs2=st.executeQuery(query2);
						while(rs2.next())
	                    {
	                    	client_id = Integer.parseInt(rs2.getString(1));
	                    }
						
						String query = "select user_fname, food_name, quantity, order_time, order_date, order_status from order_status where user_id = "+user_id+" and client_id = "+client_id+";";
	                    ResultSet rs=st.executeQuery(query);
	                    ResultSetMetaData rsmd=rs.getMetaData();
	                    DefaultTableModel model=(DefaultTableModel)table.getModel();
	                    int cols=rsmd.getColumnCount();
	                    String[] colName=new String[cols];
	                    for(int i=0;i<cols;i++)
	                    	colName[i]=rsmd.getColumnName(i+1);
	                    model.setColumnIdentifiers(colName);
	                    String food_name, order_time, order_date, order_status, quantity;
	                    while(rs.next())
	                    {
	                    	user_fname = rs.getString(1);
	                    	food_name = rs.getString(2);
	                    	quantity = rs.getString(3);
	                    	order_time = rs.getString(4); 
	                    	order_date = rs.getString(5);
	                    	order_status = rs.getString(6);
	                    	String[] row = {user_fname, food_name, quantity, order_time, order_date, order_status};
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
		btnShowOrders.setBounds(837, 174, 131, 32);
		contentPane.add(btnShowOrders);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 67, 590, 428);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		txtuser_fname = new JTextField();
		txtuser_fname.setColumns(10);
		txtuser_fname.setBounds(783, 67, 185, 24);
		contentPane.add(txtuser_fname);
		
		JLabel lbluser_fname = new JLabel("User Name:");
		lbluser_fname.setFont(new Font("Dialog", Font.BOLD, 14));
		lbluser_fname.setBounds(644, 63, 121, 24);
		contentPane.add(lbluser_fname);
		
		JLabel lblfood_cate = new JLabel("Canteen Name:");
		lblfood_cate.setFont(new Font("Dialog", Font.BOLD, 14));
		lblfood_cate.setBounds(644, 99, 131, 24);
		contentPane.add(lblfood_cate);
		
		txtclient_cantname = new JTextField();
		txtclient_cantname.setColumns(10);
		txtclient_cantname.setBounds(783, 103, 185, 24);
		contentPane.add(txtclient_cantname);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Payment pay=new Payment();
				pay.setVisible(true);
			}
		});
		btnBack.setBounds(648, 174, 127, 32);
		contentPane.add(btnBack);
		JLabel backgroundImg = new JLabel("");
		Image img= new ImageIcon(this.getClass().getResource("polotno(3).png")).getImage();
		backgroundImg.setIcon(new ImageIcon(img));
		backgroundImg.setBounds(0, 0, 1010, 610);
		getContentPane().add(backgroundImg);
	}
}
