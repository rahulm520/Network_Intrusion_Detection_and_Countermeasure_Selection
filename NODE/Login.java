import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Map;
class Login extends JFrame implements ActionListener 
{
	
	JLabel title,lb1,lb2,jl0;
	JButton  submit,reset;
	JTextField tf1;
	JPasswordField tf2;
	Boolean flag=false,flag1=false;
	Container c;
	Login() 
	{
		super("LOGIN");
		c=this.getContentPane();
		c.setLayout(null);
		Dimension d=Toolkit.getDefaultToolkit().getScreenSize(); 
		this.setSize(500,300);
		c.setBackground(Color.white);
		
		jl0=new JLabel();
		jl0.setIcon(new ImageIcon("img/fusion.jpg"));
		jl0.setBounds(200,-20,150,150);
		c.add(jl0);
	
		title=new JLabel("");
		title.setBounds(10,0,d.width,120);
		title.setFont(new Font("Dialog",Font.PLAIN,23));
		title.setForeground(Color.red);
		c.add(title);
		
		
		lb1=new JLabel("User Name :");
		lb1.setBounds(125,125,100,35);
		lb1.setForeground(new Color(0,100,254));
		lb1.setFont(new Font("Dialog",Font.PLAIN,18));
		c.add(lb1);
		
		tf1=new JTextField ();
		tf1.setBounds(245,130,125,25);
		c.add(tf1);
		
		lb2=new JLabel("Password :");
		lb2.setBounds(125,175,100,35);
		lb2.setForeground(new Color(0,100,254));
		lb2.setFont(new Font("Dialog",Font.PLAIN,18));
		c.add(lb2);
		
		tf2=new JPasswordField(10);
		tf2.setBounds(245,180,125,25);
		c.add(tf2);
		
		submit=new JButton("Submit");
        submit.reshape(125,220,100,30);
        c.add(submit);       
        submit.setFont(new Font("Times New Roman",Font.PLAIN,18));
       	submit.addActionListener(this);
       	
       	reset=new JButton("Reset");
        reset.reshape(265,220,100,30);
        c.add(reset);       
        reset.setFont(new Font("Times New Roman",Font.PLAIN,18));
       	reset.addActionListener(this);
       	
       	
       	this.setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==submit)
		{
			String IP="";
			int ch=0;
			String name=tf1.getText().trim();
			String pwd=tf2.getText().trim();
			if(name.equals(pwd))
			{
				try
				{
					
					FileInputStream fin=new FileInputStream("ServerIP.txt");
					while((ch=fin.read())!=-1)
					IP+=(char)ch;
					IP.trim();
									
					Socket s=new Socket(IP,9696);
					ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
					ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
					InetAddress inet=(InetAddress)s.getLocalAddress();
					String sys_info = inet.getLocalHost().toString();
					System.out.println("SYS "+sys_info);
					oos.writeObject("LOGIN");
					oos.writeObject(name.toUpperCase());
					oos.writeObject(sys_info);
					
					String resp=(String)ois.readObject();
					System.out.println("RES "+resp);
					String[] split_resp = resp.split(":");
					
					if(split_resp[0].equals("SUCCESS"))
					{
						Map c_data = (Map)ois.readObject();
						
						JOptionPane.showMessageDialog(null,"LOGIN SUCCESS");
						new Index(split_resp[2],split_resp[1],c_data,name.toUpperCase());
						File f=new File(name.toUpperCase());
						if(!f.exists())
						{
							f.mkdir();
						}
						this.setVisible(false);
					}
					else if(split_resp[0].equals("FAILURE"))
					{
						JOptionPane.showMessageDialog(null,split_resp[1]);
					}
															
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		else
		{
			tf1.setText("");
			tf2.setText("");
		}
	}
	
	public static void main(String args[])
	{
		new Login();
	}
}