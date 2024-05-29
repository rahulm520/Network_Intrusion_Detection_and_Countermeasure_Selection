import javax.swing.*;
import java.net.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
class ServerListener extends Thread
{
	
	ServerSocket ss;
	int port_id=0;
	Index obj;
	
	ServerListener(int port,Index obj)
	{
		super();
		port_id=port;
		this.obj=obj;
		start();
	}
	
	public void run()
	{
		try
		{
			ss=new ServerSocket(port_id);
			while(true)
			{
				Socket s=ss.accept();
				new HandleRequest(s,obj);
				
			}	
		}
		catch(BindException be)
		{
			System.exit(0);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	
}


class HandleRequest extends Thread
{
	
	Socket s;
	String req="";
	Index obj;
	
	HandleRequest(Socket so,Index obj)
	{
		super();
		s=so;
		this.obj=obj;
		start();
	}
	
	public void run()
	{
		try
		{
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			req=(String)ois.readObject();
			System.out.println("REQ "+req);
				
			if(req.equals("FILE_DATA"))
			{
				String file_name = (String) ois.readObject();
				int file_len = (Integer)ois.readObject();
				int tot_pkt = (Integer)ois.readObject();
				int index = 1;
				int start = 0,end = 65535;
				
				if(tot_pkt==1)
				{
					end = file_len;
				}
				
				byte[] file_data = new byte[file_len];
				while(index<=tot_pkt)
				{
					byte[] data = (byte[])ois.readObject();	
					int j=0;
					for(int i=start;i<end;i++,j++)
					{
						file_data[i] = data[j];
					}
					
					index++;
					start =+ end;
					if(index==tot_pkt)
					{
						end = file_len;
					}
					else
					{
						end = end+65535;
					}
					
				}
				
				FileOutputStream fout=new FileOutputStream(Index.usr_nme+"//"+file_name);
				fout.write(file_data);
				fout.close();
				
				JOptionPane.showMessageDialog(obj,"File "+file_name+" successfully downlaoded");
				
			}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	/*---------------------------------------------------------------------------------------*/
	
	
}