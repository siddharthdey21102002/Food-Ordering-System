import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class MenuManagement extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField txtfood_name;
	private JTextField txtfood_price;
	private JTextField txtfood_id;
	private JTextArea txtfood_des;
	private JLabel lblfood_image;
	private String path2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuManagement frame = new MenuManagement();
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
	public MenuManagement() {
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
				String food_name=txtfood_name.getText();
				String food_price=txtfood_price.getText();
				String food_des=txtfood_des.getText();
				Icon food_image=lblfood_image.getIcon();
				try 
				{
					Class.forName("org.postgresql.Driver");
					Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/foodorderingsystem", "postgres","1021");
					String query = "INSERT INTO food_items (food_name, food_des, food_price, food_image) VALUES (?, ?, ?, ?)";

		            PreparedStatement pst = conn.prepareStatement(query);
		            
		            FileInputStream is = new FileInputStream(path2);
		            pst.setString(1, food_name);
		            pst.setString(2, food_des);
		            pst.setString(3, food_price);
		            pst.setBinaryStream(4, is);
		            pst.executeUpdate();
					txtfood_name.setText("");
					txtfood_des.setText("");
					txtfood_price.setText("");
					lblfood_image.setIcon(null);
		            pst.close();
		            is.close();
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		btnAddToMenu.setBounds(441, 518, 131, 32);
		contentPane.add(btnAddToMenu);
		
		JButton btnRemove = new JButton("Remove item");
		btnRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel model=(DefaultTableModel)table.getModel();
				if(table.getSelectedRowCount()==1)
				{
					model.removeRow(table.getSelectedRow());
					txtfood_id.setText("");
					txtfood_name.setText("");
					txtfood_price.setText(""); 
					txtfood_des.setText("");
					lblfood_image.setIcon(null);
					JOptionPane.showMessageDialog(null, "Removed Successfully..");
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
				DefaultTableModel model=(DefaultTableModel)table.getModel();
				if(table.getSelectedRowCount()==1)
				{
					String food_name = txtfood_name.getText();
					String food_price = txtfood_price.getText();
					String food_des= txtfood_des.getText();
					Icon food_image = lblfood_image.getIcon();
					
					model.setValueAt(food_name, table.getSelectedRow(), 0);
					model.setValueAt(food_price, table.getSelectedRow(), 1);
					model.setValueAt(food_des, table.getSelectedRow(), 2);
					model.setValueAt(food_image, table.getSelectedRow(), 3);
					
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
			}
		});
		btnUpdateMenu.setBounds(155, 518, 131, 32);
		contentPane.add(btnUpdateMenu);
		
		JButton btnShowMenu = new JButton("Show Menu");
		btnShowMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					Class.forName("org.postgresql.Driver");
					Connection connection=DriverManager.getConnection("jdbc:postgresql://localhost:5432/foodorderingsystem", "postgres","1021");
                    Statement st = connection.createStatement();
                    String query = "select * from food_items;";
                    ResultSet rs=st.executeQuery(query);
                    ResultSetMetaData rsmd=rs.getMetaData();
                    DefaultTableModel model=(DefaultTableModel)table.getModel();
                    int cols=rsmd.getColumnCount();
                    String[] colName=new String[cols];
                    for(int i=0;i<cols;i++)
                    	colName[i]=rsmd.getColumnName(i+1);
                    model.setColumnIdentifiers(colName);
                    String food_id, food_name, food_des, food_price, food_image;
                    while(rs.next())
                    {
                    	food_id = rs.getString(1); 
                    	food_name = rs.getString(2); 
                    	food_des = rs.getString(3);
                    	food_price = rs.getString(4); 
                    	food_image = rs.getString(5);
                    	String[] row = {food_id, food_name, food_des, food_price, food_image};
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
		});
		btnShowMenu.setBounds(12, 518, 131, 32);
		contentPane.add(btnShowMenu);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 67, 527, 439);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel tbModel = (DefaultTableModel) table.getModel();
				String tbfood_id = tbModel.getValueAt(table.getSelectedRow(), 1).toString();
				String tbfood_name = tbModel.getValueAt(table.getSelectedRow(), 2).toString();
				String tbfood_des= tbModel.getValueAt(table.getSelectedRow(), 3).toString();
				String tbfood_price = tbModel.getValueAt(table.getSelectedRow(), 4).toString();
				Icon tbfood_image = (Icon) tbModel.getValueAt(table.getSelectedRow(), 5);
				
				txtfood_id.setText(tbfood_id);
				txtfood_name.setText(tbfood_name);
				txtfood_des.setText(tbfood_des);
				txtfood_price.setText(tbfood_price);
				lblfood_image.getIcon();
				}
		});
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		panel.setBounds(584, 55, 398, 499);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblFoodName = new JLabel("Food Name:");
		lblFoodName.setBounds(12, 48, 94, 24);
		panel.add(lblFoodName);
		lblFoodName.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JLabel lblFoodDescrption = new JLabel("Food Descrption:");
		lblFoodDescrption.setBounds(12, 120, 136, 24);
		panel.add(lblFoodDescrption);
		lblFoodDescrption.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JLabel lblFoodPrice = new JLabel("Food Price:");
		lblFoodPrice.setBounds(12, 84, 94, 24);
		panel.add(lblFoodPrice);
		lblFoodPrice.setFont(new Font("Dialog", Font.BOLD, 14));
		
		txtfood_name = new JTextField();
		txtfood_name.setBounds(140, 48, 245, 24);
		panel.add(txtfood_name);
		txtfood_name.setColumns(10);
		
		txtfood_price = new JTextField();
		txtfood_price.setBounds(140, 84, 245, 24);
		panel.add(txtfood_price);
		txtfood_price.setColumns(10);
		
		JLabel lblFoodImage = new JLabel("Food Image:");
		lblFoodImage.setBounds(12, 230, 103, 24);
		panel.add(lblFoodImage);
		lblFoodImage.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JLabel lblfood_id = new JLabel("Food Id:");
		lblfood_id.setBounds(12, 12, 94, 24);
		panel.add(lblfood_id);
		lblfood_id.setFont(new Font("Dialog", Font.BOLD, 14));
		
		txtfood_id = new JTextField();
		txtfood_id.setBounds(140, 12, 245, 24);
		panel.add(txtfood_id);
		txtfood_id.setColumns(10);
		
		txtfood_des = new JTextArea();
		txtfood_des.setBorder(new LineBorder(new Color(119, 118, 123)));
		txtfood_des.setBounds(62, 156, 324, 68);
		panel.add(txtfood_des);
		
		JButton btnInsert_Image = new JButton("Insert Image");
		btnInsert_Image.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(null);
				File f = chooser.getSelectedFile();
				String path = f.getAbsolutePath();
				try
				{
					BufferedImage bi = ImageIO.read(new File(path));
					Image img = bi.getScaledInstance(323,171,Image.SCALE_SMOOTH);
					ImageIcon icon =new ImageIcon(img);
					lblfood_image.setIcon(icon);
					path2=path;
				}
				catch(Exception ex)
				{
					Logger.getLogger(MenuManagement.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});
		btnInsert_Image.setBounds(154, 449, 131, 32);
		panel.add(btnInsert_Image);
		
		lblfood_image = new JLabel("");
		lblfood_image.setBounds(62, 259, 323, 178);
		panel.add(lblfood_image);
	}
}



/*JButton btnAddToMenu = new JButton("Add to Menu");
btnAddToMenu.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		if(txtfood_id.getText().equals("") || txtfood_name.getText().equals("") || txtfood_des.getText().equals("") || txtfood_price.getText().equals("") || txtfood_image.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "Please Enter all the Details:");
		}
		else
		{
			String data[]= {txtfood_id.getText(), txtfood_name.getText(), txtfood_des.getText(), txtfood_price.getText(), txtfood_image.getText()};
			DefaultTableModel model=(DefaultTableModel)table.getModel();
			model.addRow(data);
			JOptionPane.showMessageDialog(null, "Data added seccessfully");
			txtfood_id.setText("");
			txtfood_name.setText("");
			txtfood_price.setText(""); 
			txtfood_des.setText("");
			txtfood_image.setText("");
		}
	}
});
btnAddToMenu.setBounds(441, 518, 131, 32);
contentPane.add(btnAddToMenu);
*/