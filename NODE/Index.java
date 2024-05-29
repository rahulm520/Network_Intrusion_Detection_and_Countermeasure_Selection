import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.Border;
import java.net.*;
import java.io.*;

class Index extends JFrame implements ActionListener, ListSelectionListener
{
	static String user_ID="",usr_nme="";
	int user_port=0;
	String[] DATA_TYPE={"VIDEO","AUDIO","OTHERS"};
	Map<String,ArrayList> CLOUD_DATA =new HashMap<String,ArrayList>();
	
	JPanel jp1,jp2;
	JList jlst1,jlst2,jlst3;
	JLabel jl1,jl2,jl3,jl4;
	JScrollPane jsp1,jsp2,jsp3;
	JButton jb1,jb2;
	JCheckBox jcb;
		
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	double wid=d.width*0.4;
	double hei=d.height*0.7;
	Font f = new Font("Dialog",Font.PLAIN,22);
	
	Index(String id,String prt,Map c_dat,String nme)
	{
		super("User : "+id);
		
		usr_nme=nme;
		user_ID=id;
		user_port=Integer.parseInt(prt);
		CLOUD_DATA=c_dat;
		this.setLayout(null);
				
		jp1 = new JPanel();
		jp2 = new JPanel();
		
		jp1.setBackground(Color.white);
		jp2.setBackground(Color.white);
		
		jp1.setLayout(null);
		jp2.setLayout(null);
		Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		jp1.setBorder(raisedetched );
		jp2.setBorder(raisedetched );
		
		addComponent(this,jp1,0,0,(int)wid-10,(int)(hei/2)+150);
		addComponent(this,jp2,0,(int)(hei/2)+150,(int)wid-10,(int)(hei-jp1.getHeight())-35);
		
		
		
		
		double jwed=jp1.getWidth()*0.3;
		double jhei=jp1.getHeight()*0.8;
		
		ArrayList rec_data=retriveData();
		
		jlst1 = new JList((String[])rec_data.get(0));
		jlst2 = new JList((String[])rec_data.get(1));
		jlst3 = new JList((String[])rec_data.get(2));
		jl1 = new JLabel("VIDEO");
		jl2 = new JLabel("AUDIO");
		jl3 = new JLabel("OTHERS");
		jsp1 = new JScrollPane();
		jsp2 = new JScrollPane();
		jsp3 = new JScrollPane();
		
		jl1.setFont(f);
		jl2.setFont(f);
		jl3.setFont(f);
		jsp1.setViewportView(jlst1);
		jsp2.setViewportView(jlst2);
		jsp3.setViewportView(jlst3);
		
		addComponent(jp1,jsp1,15,65,(int)jwed,(int)jhei);
		addComponent(jp1,jsp2,30+(int)jwed,65,(int)jwed,(int)jhei);
		addComponent(jp1,jsp3,45+(int)jwed*2,65,(int)jwed,(int)jhei);
		addComponent(jp1,jl1,58,30,75,35);
		addComponent(jp1,jl2,73+(int)jwed,30,75,35);
		addComponent(jp1,jl3,75+(int)jwed*2,30,95,35);
		
		
		jb1 = new JButton("Download");
		jb2 = new JButton("Logout");
		//jcb = new JCheckBox("Generate Multiple Request");
		
		//jcb.setBackground(Color.white);
		
		addComponent(jp2,jb1,25,25,100,35);
		addComponent(jp2,jb2,150,25,100,35);
		//addComponent(jp2,jcb,280,25,200,35);		  		
		
		jlst1.addListSelectionListener(this);
       	jlst2.addListSelectionListener(this);
       	jlst3.addListSelectionListener(this);
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		
		this.setSize((int)wid,(int)hei);
		this.setVisible(true);
		new ServerListener(user_port,this);
		
	}
	
	public void addComponent(Container c,Component cm,int x,int y,int w,int h)
	{
		c.add(cm);
		cm.setBounds(x,y,w,h);
	}
	
	public ArrayList retriveData()
	{
		ArrayList vec=new ArrayList();
		
		for(int i=0;i<DATA_TYPE.length;i++)
		{
			ArrayList data=(ArrayList)CLOUD_DATA.get(DATA_TYPE[i]);
			String ext_data[] = new String[data.size()];
			for(int j=0;j<data.size();j++)
			{
				ext_data[j] = (String)data.get(j);
			}
			vec.add(ext_data);
		}
	return vec;	
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		Object o = ae.getSource();
		
		
		int ch=0;
		String IP="";
		
		try
		{
				
			FileInputStream fin=new FileInputStream("ServerIP.txt");
			while((ch=fin.read())!=-1)
			IP+=(char)ch;
			IP.trim();
		
		
			if(o==jb1)
			{
				int i1 = jlst1.getSelectedIndex();
				int i2 = jlst2.getSelectedIndex();
				int i3 = jlst3.getSelectedIndex();
				if((i1<0)&(i2<0)&(i3<0))
				{
					JOptionPane.showMessageDialog(null,"Select File to Download!!!");	
				}
				else
				{
					String SELECTED_FILE="";
					String FILE_TYPE="";
					if(i1>-1)
					{
						SELECTED_FILE = (String)jlst1.getSelectedValue();
						FILE_TYPE = "VIDEO";
						System.out.println("FILE "+SELECTED_FILE);
					}
					else if(i2>-1)
					{
						SELECTED_FILE = (String)jlst2.getSelectedValue();
						FILE_TYPE = "AUDIO";
						System.out.println("FILE "+SELECTED_FILE);
					}
					else if(i3>-1)
					{
						SELECTED_FILE = (String)jlst3.getSelectedValue();
						FILE_TYPE = "OTHERS";
						System.out.println("FILE "+SELECTED_FILE);
					}
					
					Socket s=new Socket(IP,9696);
					ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
					ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
					oos.writeObject("DOWNLOAD");
					oos.writeObject(user_ID);
					oos.writeObject(SELECTED_FILE);
					oos.writeObject(FILE_TYPE);
					
					
					
					
				}
			}
			else if(o==jb2)
			{
				
					Socket s=new Socket(IP,9696);
					ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
					ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
					oos.writeObject("LOGOUT");
					oos.writeObject(user_ID);
					
					String resp = (String)ois.readObject();
					JOptionPane.showMessageDialog(null,resp);
					
					if(resp.equals("LOGGED OUT SUCCESSFULLY!!!"))
					{
						System.exit(0);
					}
					
				
				
			}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void valueChanged(ListSelectionEvent lse)
	{
		Object o=lse.getSource();
		if((o==jlst1)||(o==jlst2)||(o==jlst3))
		{
			
			int i1 = jlst1.getSelectedIndex();
			int i2 = jlst2.getSelectedIndex();
			int i3 = jlst3.getSelectedIndex();
			
			if(i1>=0)
			{
				jlst2.clearSelection();
				jlst3.clearSelection();
			}
			if(i2>=0)
			{
				jlst1.clearSelection();
				jlst3.clearSelection();
			}
			if(i3>=0)
			{
				jlst1.clearSelection();
				jlst2.clearSelection();	
			}
			
		}
	}
	
	public static void main(String args[])
	{
		new Index("aasas","2000",new HashMap(),"C");
	}
}