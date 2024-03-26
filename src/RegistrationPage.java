import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegistrationPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)throws SQLException, Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistrationPage frame = new RegistrationPage();
					frame.setVisible(true);
				} catch (Exception e) {
					System.out.println(e);
					e.printStackTrace();
				}
			}
		});
	}
	String query;
	String user_phone_no, user_email, user_fname, user_mname, user_lname, user_place, user_city, user_state, user_country;
	String user_pass;
	int user_id;
	String client_phone_no, client_email, client_fname, client_cantname, client_lname, client_place, client_city, client_state, client_country;
	String client_pass;
	int client_id;
	Connection connection=null;
	/**
	 * Create the frame.
	 */
	public RegistrationPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1010, 610);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		final String emailPattern="^[a-zA-Z0-9]+[@]+[a-zA-Z0-9]+[.]+[a-zA-Z0-9]+$";
		final String Phone_no_Pattern="^[0-9]+$";

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0));
		panel.setBounds(12, 12, 459, 495);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblUserRegistration = new JLabel("User Registration");
		lblUserRegistration.setForeground(new Color(255, 255, 255));
		lblUserRegistration.setBounds(12, 12, 207, 24);
		panel.add(lblUserRegistration);
		lblUserRegistration.setFont(new Font("Dialog", Font.BOLD, 20));
		
		JLabel lblName = new JLabel("First Name:");
		lblName.setForeground(new Color(255, 255, 255));
		lblName.setBounds(22, 48, 95, 24);
		panel.add(lblName);
		lblName.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Dialog", Font.BOLD, 14));
		lblEmail.setBounds(22, 266, 95, 24);
		panel.add(lblEmail);
		
		JLabel lblPassoword = new JLabel("Passoword:");
		lblPassoword.setForeground(new Color(255, 255, 255));
		lblPassoword.setBounds(22, 338, 95, 24);
		panel.add(lblPassoword);
		lblPassoword.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JLabel lblConf_pass = new JLabel("Confirm Password:");
		lblConf_pass.setForeground(new Color(255, 255, 255));
		lblConf_pass.setBounds(22, 410, 157, 24);
		panel.add(lblConf_pass);
		lblConf_pass.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setForeground(new Color(255, 255, 255));
		lblAddress.setBounds(241, 14, 95, 24);
		panel.add(lblAddress);
		lblAddress.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JLabel lblPlace = new JLabel("Place:");
		lblPlace.setForeground(new Color(255, 255, 255));
		lblPlace.setBounds(241, 51, 95, 24);
		panel.add(lblPlace);
		lblPlace.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JLabel lblCity = new JLabel("City:");
		lblCity.setForeground(new Color(255, 255, 255));
		lblCity.setBounds(241, 122, 95, 24);
		panel.add(lblCity);
		lblCity.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JLabel lblState = new JLabel("State:");
		lblState.setForeground(new Color(255, 255, 255));
		lblState.setBounds(241, 194, 95, 24);
		panel.add(lblState);
		lblState.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JLabel lblCountry = new JLabel("Country:");
		lblCountry.setForeground(new Color(255, 255, 255));
		lblCountry.setFont(new Font("Dialog", Font.BOLD, 14));
		lblCountry.setBounds(241, 266, 95, 24);
		panel.add(lblCountry);
		
		JLabel lblPhone = new JLabel("Phone No.:");
		lblPhone.setForeground(new Color(255, 255, 255));
		lblPhone.setFont(new Font("Dialog", Font.BOLD, 14));
		lblPhone.setBounds(241, 338, 95, 24);
		panel.add(lblPhone);
		
		JLabel lblMiddleName = new JLabel("Middle Name:");
		lblMiddleName.setForeground(new Color(255, 255, 255));
		lblMiddleName.setFont(new Font("Dialog", Font.BOLD, 14));
		lblMiddleName.setBounds(22, 120, 112, 24);
		panel.add(lblMiddleName);
		
		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setForeground(new Color(255, 255, 255));
		lblLastName.setFont(new Font("Dialog", Font.BOLD, 14));
		lblLastName.setBounds(22, 192, 112, 24);
		panel.add(lblLastName);
		
		final JTextField F_Name = new JTextField();
		F_Name.setColumns(10);
		F_Name.setBounds(32, 84, 183, 24);
		panel.add(F_Name);
		
		final JTextField M_Name = new JTextField();
		M_Name.setColumns(10);
		M_Name.setBounds(32, 156, 183, 24);
		panel.add(M_Name);
		
		final JTextField L_Name = new JTextField();
		L_Name.setColumns(10);
		L_Name.setBounds(32, 228, 183, 24);
		panel.add(L_Name);
		
		final JTextField Email = new JTextField();
		Email.setColumns(10);
		Email.setBounds(32, 302, 183, 24);
		panel.add(Email);
		
		final JPasswordField Pass = new JPasswordField();
		Pass.setColumns(10);
		Pass.setBounds(32, 374, 183, 24);
		panel.add(Pass);
		
		final JPasswordField Conf_Pass = new JPasswordField();
		Conf_Pass.setColumns(10);
		Conf_Pass.setBounds(32, 448, 183, 24);
		panel.add(Conf_Pass);
		
		final JTextField Place = new JTextField();
		Place.setColumns(10);
		Place.setBounds(251, 87, 183, 24);
		panel.add(Place);
		
		final JTextField City = new JTextField();
		City.setColumns(10);
		City.setBounds(251, 158, 183, 24);
		panel.add(City);
		
		final JTextField State = new JTextField();
		State.setColumns(10);
		State.setBounds(251, 230, 183, 24);
		panel.add(State);
		
		final JTextField Country = new JTextField();
		Country.setColumns(10);
		Country.setBounds(251, 302, 183, 24);
		panel.add(Country);
		
		final JTextField Phone_No = new JTextField();
		Phone_No.setColumns(10);
		Phone_No.setBounds(251, 374, 183, 24);
		panel.add(Phone_No);
		
		JButton btnRegisterUser = new JButton("Register User");
		btnRegisterUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)throws NullPointerException {
				user_pass=Pass.getText();
				user_fname=F_Name.getText();
				user_mname=M_Name.getText();
				user_lname=L_Name.getText();
				user_place=Place.getText();
				user_city=City.getText();
				user_state=State.getText();
				user_country=Country.getText();
				user_email=Email.getText();
				user_phone_no= Phone_No.getText();
				int len = user_phone_no.length();
				
				String msg = "" + user_fname;
                msg += " \n";
                
                if (len == 10 && user_phone_no.matches(Phone_no_Pattern) && user_email.matches(emailPattern))
                {
                	try {
    					Class.forName("org.postgresql.Driver");
    					Connection connection=DriverManager.getConnection("jdbc:postgresql://localhost:5432/foodorderingsystem", "postgres","1021");
                        String query = "insert into user_reg (user_email, user_pass, user_fname, user_mname, user_lname, user_phone_no, user_place, user_city, user_state, user_country) values ('"+ user_email + "','"+ user_pass + "','" + user_fname + "','" + user_mname + "','" + user_lname + "','" +
                        		user_phone_no + "','" + user_place + "','" + user_city + "','" + user_state + "','" + user_country + "')";

                        Statement sta = connection.createStatement();
                        int x = sta.executeUpdate(query);
                        if (x == 0) 
                        {
                            JOptionPane.showMessageDialog(null, "This is alredy exist");
                        } 
                        else 
                        {
                            JOptionPane.showMessageDialog(null,"Welcome, " + msg + "Your account is sucessfully created");
                            dispose();
            				LoginPage LogPage= new LoginPage();
            				LogPage.setVisible(true);
                        }
                        connection.close();
                    } 
                	catch (Exception exception) 
                	{
                        exception.printStackTrace();
                    }
                }
                else if (len != 10 || !user_phone_no.matches(Phone_no_Pattern))
                {
                    JOptionPane.showMessageDialog(null, "Enter a valid mobile number");
                }
                else if(!user_email.matches(emailPattern))
                {
                	JOptionPane.showMessageDialog(null, "Email pattern is incorrect");
                }
            }
		});
		btnRegisterUser.setBounds(284, 447, 150, 25);
		panel.add(btnRegisterUser);
		
		
		final JPanel panel_1 = new JPanel();
		panel_1.setVisible(false);
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(0, 0, 0));
		panel_1.setBounds(12, 12, 459, 495);
		contentPane.add(panel_1);
		
		JLabel lblClientRegistration = new JLabel("Client Registration");
		lblClientRegistration.setForeground(Color.WHITE);
		lblClientRegistration.setFont(new Font("Dialog", Font.BOLD, 20));
		lblClientRegistration.setBounds(12, 12, 217, 24);
		panel_1.add(lblClientRegistration);
		
		JLabel lblCantName_1 = new JLabel("Canteen Name:");
		lblCantName_1.setForeground(Color.WHITE);
		lblCantName_1.setFont(new Font("Dialog", Font.BOLD, 14));
		lblCantName_1.setBounds(22, 48, 130, 24);
		panel_1.add(lblCantName_1);
		
		JLabel lblEmail_1 = new JLabel("Email:");
		lblEmail_1.setForeground(Color.WHITE);
		lblEmail_1.setFont(new Font("Dialog", Font.BOLD, 14));
		lblEmail_1.setBounds(22, 266, 95, 24);
		panel_1.add(lblEmail_1);
		
		JLabel lblPassoword_1 = new JLabel("Passoword:");
		lblPassoword_1.setForeground(Color.WHITE);
		lblPassoword_1.setFont(new Font("Dialog", Font.BOLD, 14));
		lblPassoword_1.setBounds(22, 338, 95, 24);
		panel_1.add(lblPassoword_1);
		
		JLabel lblConf_pass_1 = new JLabel("Confirm Password:");
		lblConf_pass_1.setForeground(Color.WHITE);
		lblConf_pass_1.setFont(new Font("Dialog", Font.BOLD, 14));
		lblConf_pass_1.setBounds(22, 410, 157, 24);
		panel_1.add(lblConf_pass_1);
		
		JLabel lblAddress_1 = new JLabel("Address:");
		lblAddress_1.setForeground(Color.WHITE);
		lblAddress_1.setFont(new Font("Dialog", Font.BOLD, 14));
		lblAddress_1.setBounds(241, 14, 95, 24);
		panel_1.add(lblAddress_1);
		
		JLabel lblPlace_1 = new JLabel("Place:");
		lblPlace_1.setForeground(Color.WHITE);
		lblPlace_1.setFont(new Font("Dialog", Font.BOLD, 14));
		lblPlace_1.setBounds(241, 51, 95, 24);
		panel_1.add(lblPlace_1);
		
		JLabel lblCity_1 = new JLabel("City:");
		lblCity_1.setForeground(Color.WHITE);
		lblCity_1.setFont(new Font("Dialog", Font.BOLD, 14));
		lblCity_1.setBounds(241, 122, 95, 24);
		panel_1.add(lblCity_1);
		
		JLabel lblState_1 = new JLabel("State:");
		lblState_1.setForeground(Color.WHITE);
		lblState_1.setFont(new Font("Dialog", Font.BOLD, 14));
		lblState_1.setBounds(241, 194, 95, 24);
		panel_1.add(lblState_1);
		
		JLabel lblCountry_1 = new JLabel("Country:");
		lblCountry_1.setForeground(Color.WHITE);
		lblCountry_1.setFont(new Font("Dialog", Font.BOLD, 14));
		lblCountry_1.setBounds(241, 266, 95, 24);
		panel_1.add(lblCountry_1);
		
		JLabel lblPhone_1 = new JLabel("Phone No.:");
		lblPhone_1.setForeground(Color.WHITE);
		lblPhone_1.setFont(new Font("Dialog", Font.BOLD, 14));
		lblPhone_1.setBounds(241, 338, 95, 24);
		panel_1.add(lblPhone_1);
		
		JLabel lblFirstName_1 = new JLabel("First Name:");
		lblFirstName_1.setForeground(Color.WHITE);
		lblFirstName_1.setFont(new Font("Dialog", Font.BOLD, 14));
		lblFirstName_1.setBounds(22, 120, 112, 24);
		panel_1.add(lblFirstName_1);
		
		JLabel lblLastName_1 = new JLabel("Last Name:");
		lblLastName_1.setForeground(Color.WHITE);
		lblLastName_1.setFont(new Font("Dialog", Font.BOLD, 14));
		lblLastName_1.setBounds(22, 192, 112, 24);
		panel_1.add(lblLastName_1);
		
		final JTextField Cant_Name_1 = new JTextField();
		Cant_Name_1.setColumns(10);
		Cant_Name_1.setBounds(32, 84, 183, 24);
		panel_1.add(Cant_Name_1);
		
		final JTextField F_Name_1 = new JTextField();
		F_Name_1.setColumns(10);
		F_Name_1.setBounds(32, 156, 183, 24);
		panel_1.add(F_Name_1);
		
		final JTextField L_Name_1 = new JTextField();
		L_Name_1.setColumns(10);
		L_Name_1.setBounds(32, 228, 183, 24);
		panel_1.add(L_Name_1);
		
		final JTextField Email_1 = new JTextField();
		Email_1.setColumns(10);
		Email_1.setBounds(32, 302, 183, 24);
		panel_1.add(Email_1);
		
		final JPasswordField Pass_1 = new JPasswordField();
		Pass_1.setColumns(10);
		Pass_1.setBounds(32, 374, 183, 24);
		panel_1.add(Pass_1);
		
		final JPasswordField Conf_Pass_1 = new JPasswordField();
		Conf_Pass_1.setColumns(10);
		Conf_Pass_1.setBounds(32, 448, 183, 24);
		panel_1.add(Conf_Pass_1);
		
		final JTextField Place_1 = new JTextField();
		Place_1.setColumns(10);
		Place_1.setBounds(251, 87, 183, 24);
		panel_1.add(Place_1);
		
		final JTextField City_1 = new JTextField();
		City_1.setColumns(10);
		City_1.setBounds(251, 158, 183, 24);
		panel_1.add(City_1);
		
		final JTextField State_1 = new JTextField();
		State_1.setColumns(10);
		State_1.setBounds(251, 230, 183, 24);
		panel_1.add(State_1);
		
		final JTextField Country_1 = new JTextField();
		Country_1.setColumns(10);
		Country_1.setBounds(251, 302, 183, 24);
		panel_1.add(Country_1);
		
		final JTextField Phone_No_1 = new JTextField();
		Phone_No_1.setColumns(10);
		Phone_No_1.setBounds(251, 374, 183, 24);
		panel_1.add(Phone_No_1);
		
		JButton btnRegisterClient = new JButton("Register Client");
		btnRegisterClient.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				client_pass=Pass_1.getText();
				client_cantname=Cant_Name_1.getText();
				client_fname=F_Name_1.getText();
				client_lname=L_Name_1.getText();
				client_place=Place_1.getText();
				client_city=City_1.getText();
				client_state=State_1.getText();
				client_country=Country_1.getText();
				client_email=Email_1.getText();
				client_phone_no= Phone_No_1.getText();
				int len = client_phone_no.length();
				String msg = "" + client_fname;
                msg += " \n";
                
                if (len == 10 && client_phone_no.matches(Phone_no_Pattern) && client_email.matches(emailPattern))
                {
                	try {
    					Class.forName("org.postgresql.Driver");
    					Connection connection=DriverManager.getConnection("jdbc:postgresql://localhost:5432/foodorderingsystem", "postgres","1021");
    					String query = "insert into client_reg (client_email, client_pass, client_cantname, client_fname, client_lname, client_phone_no, client_place, client_city, client_state, client_country) values ('"+ client_email + "','"+ client_pass + "','" + client_cantname + "','" + client_fname + "','" + client_lname + "','" +
    							client_phone_no + "','" + client_place + "','" + client_city + "','" + client_state + "','" + client_country + "');";

                        Statement sta = connection.createStatement();
                        int x = sta.executeUpdate(query);
                        if (x == 0) {
                            JOptionPane.showMessageDialog(null, "This is alredy exist");
                        } 
                        else 
                        {
                            JOptionPane.showMessageDialog(null,"Welcome, " + msg + "Your account is sucessfully created");
                            dispose();
            				LoginPage LogPage= new LoginPage();
            				LogPage.setVisible(true);
                        }
                        connection.close();
                    } catch (Exception exception) 
                	{
                        exception.printStackTrace();
                    }
                }
				else if (len != 10 || !client_phone_no.matches(Phone_no_Pattern))
                {
                    JOptionPane.showMessageDialog(null, "Enter a valid mobile number");
                }
                else if(!client_email.matches(emailPattern))
                {
                	JOptionPane.showMessageDialog(null, "Email pattern is incorrect");
                }
            }
		});
		btnRegisterClient.setBounds(284, 447, 150, 25);
		panel_1.add(btnRegisterClient);
		
		JButton btnGotoClientRegistration = new JButton("Goto Client Registration");
		btnGotoClientRegistration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(panel.isVisible()) {
					panel.setVisible(false);
					panel_1.setVisible(true);
				}
			}
		});
		btnGotoClientRegistration.setBounds(256, 526, 205, 25);
		contentPane.add(btnGotoClientRegistration);
		
		JButton btnGotoUserRegistration = new JButton("Goto User Registration");
		btnGotoUserRegistration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(panel_1.isVisible()) {
					panel_1.setVisible(false);
					panel.setVisible(true);
				}
			}
		});
		btnGotoUserRegistration.setBounds(22, 526, 205, 25);
		contentPane.add(btnGotoUserRegistration);
		
		JLabel backgroundImg = new JLabel("");
		Image img= new ImageIcon(this.getClass().getResource("bg1.jpg")).getImage();
		
		JButton btnGotoLoginPage = new JButton("Goto Login Page");
		btnGotoLoginPage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				LoginPage LogPage= new LoginPage();
				LogPage.setVisible(true);
			}
		});
		btnGotoLoginPage.setBounds(692, 269, 151, 25);
		contentPane.add(btnGotoLoginPage);
		backgroundImg.setIcon(new ImageIcon(img));
		backgroundImg.setBounds(0, 0, 1010, 610);
		getContentPane().add(backgroundImg);
	}
}
