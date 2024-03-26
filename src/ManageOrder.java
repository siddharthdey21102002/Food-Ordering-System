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
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

public class ManageOrder extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField txtclient_cantname;
	private JTextField txtuser_fname;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageOrder frame = new ManageOrder();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param String 
	 */
	public ManageOrder() {
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
		
		final JRadioButton rdbtnPending = new JRadioButton("Pending");
		final JRadioButton rdbtnReady = new JRadioButton("Ready");
		final JRadioButton rdbtnDone = new JRadioButton("Done");
		rdbtnPending.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnPending.isSelected())
				{
					rdbtnDone.setSelected(false);
					rdbtnReady.setSelected(false);
				}
			}
		});
		rdbtnPending.setBounds(807, 139, 149, 23);
		contentPane.add(rdbtnPending);
		
		
		rdbtnReady.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnReady.isSelected())
				{
					rdbtnDone.setSelected(false);
					rdbtnPending.setSelected(false);
				}
			}
		});
		rdbtnReady.setBounds(807, 166, 149, 23);
		contentPane.add(rdbtnReady);
		
		
		rdbtnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnDone.isSelected())
				{
					rdbtnPending.setSelected(false);
					rdbtnReady.setSelected(false);
				}
			}
		});
		rdbtnDone.setBounds(807, 193, 149, 23);
		contentPane.add(rdbtnDone);
		
		JButton btnUpdateOrder = new JButton("Update Order");
		btnUpdateOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String status=null;
				int client_id=0, user_id=0;
				String client_cantname=txtclient_cantname.getText();
				String user_fname=txtuser_fname.getText();
				String food_name=null;
				try 
				{
					if(rdbtnPending.isSelected())
					{
						status="pending";
					}
					else if(rdbtnReady.isSelected())
					{
						status="ready";
					}
					else
					{
						status="done";
					}
					DefaultTableModel model=(DefaultTableModel)table.getModel();
					if(table.getSelectedRowCount()==1)
					{
						Class.forName("org.postgresql.Driver");
						Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/foodorderingsystem", "postgres","1021");
						Statement st = conn.createStatement();
						
						String query1 = "select user_id, food_name from order_status where user_fname = '"+user_fname+"';";
		                ResultSet rs1=st.executeQuery(query1);
		                while(rs1.next())
		                {
		                	user_id = rs1.getInt(1);
		                	food_name = rs1.getString(2);
		                }
						
						String query2 = "select client_id from client_reg where  client_cantname = '"+client_cantname+"';";
						ResultSet rs2=st.executeQuery(query2);
						while(rs2.next())
	                    {
	                    	client_id = Integer.parseInt(rs2.getString(1));
	                    }
						if(status=="ready" || status=="pending")
						{
							String query = "UPDATE order_status SET order_status='"+status+"' WHERE user_id = "+user_id+" and client_id = "+client_id+" and food_name = '"+food_name+"';";
				            PreparedStatement pst = conn.prepareStatement(query);
				            pst.executeUpdate();
				            pst.close();
		                    conn.close();
						}
						else
						{
							if(status=="done")
							{
								String query = "delete from order_status WHERE user_id = "+user_id+" and client_id = "+client_id+" and food_name = '\"+food_name+\"';";
					            PreparedStatement pst = conn.prepareStatement(query);
					            pst.executeUpdate();
					            pst.close();
			                    conn.close();
							}
						}
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
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		btnUpdateOrder.setBounds(644, 232, 131, 32);
		contentPane.add(btnUpdateOrder);
		
		JButton btnShowOrders = new JButton("Show Orders");
		btnShowOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String client_cantname=txtclient_cantname.getText();
				int client_id=0;
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
	                    
						String query1 = "select client_id from client_reg where  client_cantname = '"+client_cantname+"';";
						ResultSet rs1=st.executeQuery(query1);
						while(rs1.next())
	                    {
	                    	client_id = Integer.parseInt(rs1.getString(1));
	                    }
						
						String query = "select user_fname, food_name, quantity, order_time, order_date, order_status from order_status where client_id = '"+client_id+"';";
	                    ResultSet rs=st.executeQuery(query);
	                    ResultSetMetaData rsmd=rs.getMetaData();
	                    DefaultTableModel model=(DefaultTableModel)table.getModel();
	                    int cols=rsmd.getColumnCount();
	                    String[] colName=new String[cols];
	                    for(int i=0;i<cols;i++)
	                    	colName[i]=rsmd.getColumnName(i+1);
	                    model.setColumnIdentifiers(colName);
	                    String user_fname, food_name, order_time, order_date, order_status, quantity;
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
		btnShowOrders.setBounds(837, 232, 131, 32);
		contentPane.add(btnShowOrders);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 67, 590, 428);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel tbModel = (DefaultTableModel) table.getModel();
				String tbuser_name = tbModel.getValueAt(table.getSelectedRow(), 0).toString();
				
				txtuser_fname.setText(tbuser_name);
				}
		});
		scrollPane.setViewportView(table);
		
		txtclient_cantname = new JTextField();
		txtclient_cantname.setColumns(10);
		txtclient_cantname.setBounds(783, 67, 185, 24);
		contentPane.add(txtclient_cantname);
		
		JLabel lblclient_cantname = new JLabel("Canteen Name:");
		lblclient_cantname.setFont(new Font("Dialog", Font.BOLD, 14));
		lblclient_cantname.setBounds(644, 63, 121, 24);
		contentPane.add(lblclient_cantname);
		
		JLabel lblfood_cate = new JLabel("User Name:");
		lblfood_cate.setFont(new Font("Dialog", Font.BOLD, 14));
		lblfood_cate.setBounds(644, 103, 131, 24);
		contentPane.add(lblfood_cate);
		
		txtuser_fname = new JTextField();
		txtuser_fname.setColumns(10);
		txtuser_fname.setBounds(783, 103, 185, 24);
		contentPane.add(txtuser_fname);
		
		JLabel lblfood_cate_1 = new JLabel("Set Order Status:");
		lblfood_cate_1.setFont(new Font("Dialog", Font.BOLD, 14));
		lblfood_cate_1.setBounds(644, 139, 149, 24);
		contentPane.add(lblfood_cate_1);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ClientWindow ClientPage= new ClientWindow();
				ClientPage.setVisible(true);
			}
		});
		btnBack.setBounds(644, 291, 131, 32);
		contentPane.add(btnBack);
		
		JLabel backgroundImg = new JLabel("");
		Image img= new ImageIcon(this.getClass().getResource("polotno(3).png")).getImage();
		backgroundImg.setIcon(new ImageIcon(img));
		backgroundImg.setBounds(0, 0, 1010, 610);
		getContentPane().add(backgroundImg);
	}
}