import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.Border;
import java.util.*;

import java.io.File;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;


class Index extends JFrame implements ActionListener
{
	
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	JPanel jp1,jp2,jp3,jp31;
	static JPanel jp21,jp22,jp23;
	JLabel jli1p1,jli2p1,jli1p3;
	JLabel jlp1,jlp2,jlp3,jlp4,jlp5,jlp6,jlp7,jlp8;
	JTextArea jtap1,jtap2,jtap3,jtap4;
	static JTextArea jta5;
	Container c;
	JCheckBox jcb;
	JScrollPane jspp1,jspp2,jspp3,jspp4,jspp5,jspp6,jspp7;
	String[] CLOUD_TYPE = {"AUDIO","VIDEO","OTHERS"};
	static JScrollPane jspp8;
	JButton jbview;
	
	Font f = new Font("Dialog",Font.BOLD,15);
	Font f1 = new Font("Dialog",Font.BOLD,25);
	Font f2 = new Font("Dialog",Font.PLAIN,60);
	Font f3 = new Font("Dialog",Font.BOLD,22);
	
	Color blue = new Color(0,150,250);
	
	double width=d.width*0.8;
	double height=d.height*0.96;
	
	static boolean  NICE_STATUS=true;
	static int  NICE_THRESHOLD=3;
	
	static Map<String,ArrayList> CLOUD_DATA = new HashMap<String,ArrayList>();
	
	Vector v1=new Vector();
	Vector v2=new Vector();
	Vector v3=new Vector();
	
	Index()
	{
		super("CLOUD SERVER");
		
		c=this.getContentPane();
		c.setLayout(null);
		
		
		
		
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		
		jp1.setLayout(null);
		jp2.setLayout(null);
		jp3.setLayout(null);
		
		jp1.setBackground(Color.white);
		jp2.setBackground(Color.white);
		jp3.setBackground(blue);
		
		Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		jp1.setBorder(raisedetched );
		jp2.setBorder(raisedetched );
		jp3.setBorder(raisedetched );
		
		addComponent(c,jp1,0,0,(int)width-10,(int)height/4);
		addComponent(c,jp2,0,(int)height/4,(int)width-10,(int)height/3);
		addComponent(c,jp3,0,((int)height/4+(int)height/3),(int)width-10,(int)height-((int)height/4+(int)height/3)-35);
				
				
				
		
		jlp1 = new JLabel("VIDEO DATA");
		jlp2 = new JLabel("AUDIO DATA");
		jlp3 = new JLabel("OTHER DATA");
		jli1p1 = new JLabel(new ImageIcon("img/cloud-computing.jpg"));
		jli2p1 = new JLabel(new ImageIcon("img/cloud-data-lost.jpg"));
		jtap1 = new JTextArea();
		jtap2 = new JTextArea();
		jtap3 = new JTextArea();
		jspp1=new JScrollPane();
		jspp2=new JScrollPane();
		jspp3=new JScrollPane();
		jspp1.setViewportView(jtap1);
		jspp2.setViewportView(jtap2);
		jspp3.setViewportView(jtap3);
		
		jlp1.setFont(f);
		jlp2.setFont(f);
		jlp3.setFont(f);
		
		addComponent(jp1,jlp1,90,-6,150,40);
		addComponent(jp1,jlp2,465,-6,150,40);
		addComponent(jp1,jlp3,835,-6,150,40);
		addComponent(jp1,jspp1,10,25,225,150);
		addComponent(jp1,jli1p1,250,0,140,150);
		addComponent(jp1,jspp2,402,25,225,150);
		addComponent(jp1,jli2p1,632,75,140,150);
		addComponent(jp1,jspp3,782,25,225,150);
		
		
		
				
		jp21 = new JPanel();
		jp22 = new JPanel();
		jp23 = new JPanel();
		jlp4 = new JLabel("VM1");
		jlp5 = new JLabel("VM2");
		jlp6 = new JLabel("VM3");
		jspp4=new JScrollPane(jp21);
		jspp5=new JScrollPane(jp22);
		jspp6=new JScrollPane(jp23);
		
		jp21.setLayout(null);
		jp22.setLayout(null);
		jp23.setLayout(null);
		
		
		jspp4.setPreferredSize(new Dimension(275,500));
		//jspp4.setViewportView(jp21);
		//jspp5.setViewportView(jp22);
		//jspp6.setViewportView(jp23);
		
		jlp4.setFont(f1);
		jlp5.setFont(f1);
		jlp6.setFont(f1);
		
		jlp4.setForeground(Color.blue);
		jlp5.setForeground(Color.blue);
		jlp6.setForeground(Color.blue);
				
		addComponent(jp2,jspp4,10,47,275,275);
		addComponent(jp2,jspp5,370,47,275,275);
		addComponent(jp2,jspp6,730,47,275,275);
		addComponent(jp2,jlp4,125,10,100,30);
		addComponent(jp2,jlp5,485,10,100,30);
		addComponent(jp2,jlp6,845,10,100,30);
				
				
		
		
		jp31 = new JPanel();
		jcb = new JCheckBox();
		jta5 = new JTextArea();
		jlp7 = new JLabel("NICE");
		jtap4 = new JTextArea();
		jspp7 = new JScrollPane();
		jspp8 = new JScrollPane();
		jlp8 = new JLabel("CLOUD RESOURCE");
		jli1p3 = new JLabel(new ImageIcon("img/System-Security-Firewall-ON-icon.png"));
		
		jlp8.setFont(f);
		jlp7.setFont(f2);
		jta5.setFont(f3);
		jp31.setLayout(null);
		jcb.setSelected(true);
		jcb.setBackground(blue);
		jspp7.setViewportView(jtap4);
		jspp8.setViewportView(jta5);
		jta5.setEditable(false);
		jta5.setBackground(new Color(0,225,250));
		
		jbview=new JButton("View Graph");
							
		addComponent(jp3,jp31,jp3.getWidth()-330,38,300,300);
		addComponent(jp3,jlp7,jp3.getWidth()/2-100,5,250,125);
		addComponent(jp3,jli1p3,0,45,256,256);
		addComponent(jp3,jbview,300,27,100,30);
		addComponent(jp3,jcb,545,27,25,25);
		addComponent(jp3,jspp7,325,110,300,150);
		addComponent(jp3,jlp8,765,5,300,35);
		addComponent(jp31,jspp8,0,0,300,300);
		
		
		
		
		jcb.addActionListener(this);
		jbview.addActionListener(this);
				
		this.setLocation((int)(d.width-width)/2,0);
		this.setSize((int)width,(int)height);
		this.setVisible(true);
		
		new ServerListener(9696,this);
		new CloudResourceHandler();
		new AllThreadGroup();
		
		getAllData();
	}
	
	public void addComponent(Container c,Component cp,int x,int y,int w,int h)
	{
		c.add(cp,BorderLayout.CENTER);
		cp.setBounds(x,y,w,h);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		Object obj=ae.getSource();
		if (obj==jbview)
		{
			try
			{
				System.out.println("v1 size: "+v1.size());
				System.out.println("v2 size: "+v2.size());
				chart1 c=new chart1("Scenario Attack Graph",v1,v2,v3);
				c.setSize(500,400);
			}
			catch(Exception e)
			{
				System.out.println(e);
				e.printStackTrace();
			}
			
			
			
			
		}
		else
		if(obj==jcb)
		{
			if(jcb.isSelected())
			{
				NICE_STATUS=true;
				jli1p3.setIcon(new ImageIcon("img/System-Security-Firewall-ON-icon.png"));
			}
			else
			{
				NICE_STATUS=false;
				jli1p3.setIcon(new ImageIcon("img/Security_Firewall_OFF.png"));
			}
		}
	}
	
	public void getAllData()
	{
		
		try
		{
			for(int i=0;i<CLOUD_TYPE.length;i++)
			{
				File f = new File(CLOUD_TYPE[i]);
				File[] fil_lst = f.listFiles();
				ArrayList v = new ArrayList();
				for(int j=0;j<fil_lst.length;j++)
				{
					if(fil_lst[j].isFile())
					{
						v.add(fil_lst[j].getName());
					}
				}
				
				CLOUD_DATA.put(CLOUD_TYPE[i],v);
				
			}
			
			DisplayFiles();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	
	}
	
	public void DisplayFiles()
	{
		for(int i=0;i<CLOUD_TYPE.length;i++)
		{
			ArrayList v = CLOUD_DATA.get(CLOUD_TYPE[i]);
			String tmp = "";
			for( int ii = 0 ; ii < v.size(); ii++ )
			{
			   tmp = tmp + v.get(ii) + "\n"; 
			}
			
			if(CLOUD_TYPE[i].equals("VIDEO"))
			{
				jtap1.setText(tmp);
			}
			else if(CLOUD_TYPE[i].equals("AUDIO"))
			{
				jtap2.setText(tmp);	
			}
			else if(CLOUD_TYPE[i].equals("OTHERS"))
			{
				jtap3.setText(tmp);
			}
			
		}
		
	}
	
	public static void main(String args[])
	{
		new Index();
	}
}