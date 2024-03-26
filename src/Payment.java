import java.awt.EventQueue;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;

public class Payment extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCard_no;
	private JTextField txtCVV;
	private JTextField txtuser_fname;
	private JTextField txtExp;
	private JTextField txtcard_name;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Payment frame = new Payment();
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
	public Payment() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1010, 610);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPaymentMethod = new JLabel("Payment Method");
		lblPaymentMethod.setFont(new Font("Dialog", Font.BOLD, 20));
		lblPaymentMethod.setBounds(400, 12, 220, 43);
		contentPane.add(lblPaymentMethod);
		
		final String CVV_pattern="^[0-9]+$";
		final String ACC_pattern="^[0-9]+$";
		final String EXP_pattern="^[0-9]+[/]+[0-9]+$";
		
		final JTextArea txtbill = new JTextArea();
		txtbill.setBounds(625, 73, 326, 440);
		contentPane.add(txtbill);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Card Payment", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(32, 73, 552, 440);
		contentPane.add(panel);
		panel.setLayout(null);
		
		txtuser_fname = new JTextField();
		txtuser_fname.setBounds(28, 108, 227, 27);
		panel.add(txtuser_fname);
		txtuser_fname.setColumns(10);
		
		JLabel lbluser_fname = new JLabel("User Name:");
		lbluser_fname.setBounds(28, 66, 111, 27);
		panel.add(lbluser_fname);
		lbluser_fname.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JLabel lblCard_no = new JLabel("Card Number:");
		lblCard_no.setBounds(28, 147, 111, 27);
		panel.add(lblCard_no);
		lblCard_no.setFont(new Font("Dialog", Font.BOLD, 14));
		
		txtCard_no = new JTextField();
		txtCard_no.setBounds(28, 189, 227, 27);
		panel.add(txtCard_no);
		txtCard_no.setColumns(10);
		
		JLabel lblCVV = new JLabel("CVV ");
		lblCVV.setBounds(28, 228, 111, 27);
		panel.add(lblCVV);
		lblCVV.setFont(new Font("Dialog", Font.BOLD, 14));
		
		txtCVV = new JTextField();
		txtCVV.setBounds(28, 270, 227, 27);
		panel.add(txtCVV);
		txtCVV.setColumns(10);
		
		JLabel lblCard_name = new JLabel("Card Name:");
		lblCard_name.setBounds(292, 147, 111, 27);
		panel.add(lblCard_name);
		lblCard_name.setFont(new Font("Dialog", Font.BOLD, 14));
		
		txtcard_name = new JTextField();
		txtcard_name.setBounds(292, 189, 227, 27);
		panel.add(txtcard_name);
		txtcard_name.setColumns(10);
		
		JLabel lblExp = new JLabel("Expiry (MM/YY)");
		lblExp.setBounds(292, 228, 123, 27);
		panel.add(lblExp);
		lblExp.setFont(new Font("Dialog", Font.BOLD, 14));
		
		txtExp = new JTextField();
		txtExp.setBounds(292, 270, 227, 27);
		panel.add(txtExp);
		txtExp.setColumns(10);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Cart cart= new Cart();
				cart.setVisible(true);
			}
		});
		btnBack.setBounds(28, 359, 140, 34);
		panel.add(btnBack);
		
		JButton btnShowBill = new JButton("Show Bill");
		btnShowBill.setBounds(205, 359, 140, 34);
		panel.add(btnShowBill);
		
		JLabel backgroundImg = new JLabel("");
		Image img= new ImageIcon(this.getClass().getResource("polotno(3).png")).getImage();
		backgroundImg.setIcon(new ImageIcon(img));
		backgroundImg.setBounds(0, 0, 1010, 610);
		getContentPane().add(backgroundImg);
		
		JButton btnPay = new JButton("Pay ");
		btnPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user_fname=txtuser_fname.getText();
				int user_id=0;
				String food_name, client_cantname="", status="pending", quantity="";
                int food_id =0;
                int client_id=0;
				if(txtCard_no.getText().equals("") || txtCVV.getText().equals("") || txtCVV.getText().equals("") || txtcard_name.getText().equals("") || txtExp.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Enter Payment Details..");
				}
				else
				{
					try
					{
						String acc=txtCard_no.getText();
						int lenacc=acc.length();
						String cvv=txtCVV.getText();
						int lencvv=cvv.length();
						String exp=txtExp.getText();
						int lenexp=exp.length();
						if(lenacc != 12 && !(acc.matches(ACC_pattern)))
						{
							JOptionPane.showMessageDialog(null, "Invalid Card Number Credentials ..");
						}
						else if(lencvv != 3 && !(acc.matches(CVV_pattern)))
						{
							JOptionPane.showMessageDialog(null, "Invalid CVV Credentials ..");
						}
						else if(lenexp != 5 && !(acc.matches(EXP_pattern)))
						{
							JOptionPane.showMessageDialog(null, "Invalid CVV Credentials ..");
						}
						else
						{
							Class.forName("org.postgresql.Driver");
							Connection connection=DriverManager.getConnection("jdbc:postgresql://localhost:5432/foodorderingsystem", "postgres","1021");
							Statement st = connection.createStatement();
			                
							String query1 = "select user_id from user_reg where user_fname = '"+user_fname+"';";
			                ResultSet rs1=st.executeQuery(query1);
			                while(rs1.next())
			                {
			                	user_id = rs1.getInt(1);
			                }
			                
			                String query2 = "select food_id from cart where user_fname = '"+user_fname+"';";
			                ResultSet rs2=st.executeQuery(query2);
			                while(rs2.next())
			                {
			                	food_id=rs2.getInt(1);
			                }
			                
			                String query3 = "select client_cantname from food_items where food_id = '"+food_id+"';";
			                ResultSet rs3=st.executeQuery(query3);
			                while(rs3.next())
			                {
			                	client_cantname=rs3.getString(1);
			                }
			                
			                String query4 = "select client_id from client_reg where client_cantname = '"+client_cantname+"';";
			                ResultSet rs4=st.executeQuery(query4);
			                while(rs4.next())
			                {
			                	client_id=rs4.getInt(1);
				         	}
			                
			                Date date1 = new Date();
			                Date time1 = new Date();
			                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy/MM/dd");
			                SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss");
			                String date = formatter1.format(date1);
			                String time = formatter2.format(time1);
			                
			                String query = "select food_name, quantity, food_id from cart where user_id = '"+user_id+"';";
			                ResultSet rs=st.executeQuery(query);
			                while(rs.next())
			                {
			                	food_name = rs.getString(1);
			                	quantity= rs.getString(2);
			                	food_id= rs.getInt(3);
			                	String query5 = "INSERT INTO order_status (user_fname, food_name, quantity, order_date, order_time, order_status, user_id, food_id, client_id) VALUES (?,?,?,'"+date+"','"+time+"',?,?,?,?);";
			                	PreparedStatement pst1 = connection.prepareStatement(query5);
					            
					            pst1.setString(1, user_fname);
					            pst1.setString(2, food_name);
					            pst1.setString(3, quantity);
					            pst1.setString(4, status);
					            pst1.setInt(5, user_id);
					            pst1.setInt(6, food_id);
					            pst1.setInt(7, client_id);
					            pst1.executeUpdate();
					            pst1.close();
			                }
			                JOptionPane.showMessageDialog(null, "Payment seccessfull");
			                st.close();
			                connection.close();
			                dispose();
			                User_order_status User_order_status_Page= new User_order_status();
			                User_order_status_Page.setVisible(true);
						}
					}
					catch(Exception e1)
					{
						JOptionPane.showMessageDialog(null, e1);
					}
				}
			}
		});
		btnPay.setBounds(379, 359, 140, 34);
		panel.add(btnPay);
		
		JButton btnOrderStatus = new JButton("Order Status");
		btnOrderStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				User_order_status uos=new User_order_status();
				uos.setVisible(true);
			}
		});
		btnOrderStatus.setBounds(32, 524, 145, 38);
		contentPane.add(btnOrderStatus);
		btnShowBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user_fname=txtuser_fname.getText();
				int user_id=0;
				String food_name, client_cantname="";
                int food_id =0, quantity =0, food_price =0, sum=0, amount =0;
				if(txtuser_fname.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Enter User Name..");
				}
				else
				{
					try
					{
						
						Class.forName("org.postgresql.Driver");
						Connection connection=DriverManager.getConnection("jdbc:postgresql://localhost:5432/foodorderingsystem", "postgres","1021");
						Statement st = connection.createStatement();
		                
						String query1 = "select user_id from user_reg where user_fname = '"+user_fname+"';";
		                ResultSet rs1=st.executeQuery(query1);
		                while(rs1.next())
		                {
		                	user_id = rs1.getInt(1);
		                }
		                
		                String query2 = "select food_id from cart where user_fname = '"+user_fname+"';";
		                ResultSet rs2=st.executeQuery(query2);
		                while(rs2.next())
		                {
		                	food_id=rs2.getInt(1);
		                }
		                
		                String query3 = "select client_cantname from food_items where food_id = '"+food_id+"';";
		                ResultSet rs3=st.executeQuery(query3);
		                while(rs3.next())
		                {
		                	client_cantname=rs3.getString(1);
		                }
		                
		                String query = "select food_name, food_price, quantity from cart where user_id = '"+user_id+"';";
		                ResultSet rs=st.executeQuery(query);
		                
		                Date date = new Date();
		                Date time = new Date();
		                SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yy");
		                SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss");
		                String str1 = formatter1.format(date);
		                String str2 = formatter2.format(time);
		                
		                txtbill.setText(txtbill.getText()+ "\n\t             INVOICE\n");
		                txtbill.setText(txtbill.getText()+ "\n*****************************************************\n");
		                txtbill.setText(txtbill.getText()+ "\n  Canteen Name: "+client_cantname+"\n");
		                txtbill.setText(txtbill.getText()+ "  Date: "+ str1 +"\n");
		                txtbill.setText(txtbill.getText()+ "  Time: "+ str2 +"\n");
		                txtbill.setText(txtbill.getText()+ "\n*****************************************************\n");
		                txtbill.setText(txtbill.getText()+ "\n  Food Name\tFood Price\tQuantity\tAmount\n\n");
		                while(rs.next())
		                {
		                	food_name = rs.getString(1);
		                	txtbill.setText(txtbill.getText()+"  " + food_name +"\t");
		                	food_price = Integer.parseInt(rs.getString(2));
		                	txtbill.setText(txtbill.getText() + food_price +"\t");
		                	quantity= Integer.parseInt(rs.getString(3));
		                	txtbill.setText(txtbill.getText() + quantity +"\t");
		                	amount=(quantity*food_price);
		                	txtbill.setText(txtbill.getText() + amount +"\n");
		                	sum=sum+(quantity*food_price);	
		                }
		                txtbill.setText(txtbill.getText()+ "\n  Total Amount\t\t\t"+ sum +"\n");
		                txtbill.setText(txtbill.getText()+ "\n*****************************************************\n");
		                txtbill.setText(txtbill.getText()+ "\n\t  Thanks Visit Again\n");
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
	}
}
