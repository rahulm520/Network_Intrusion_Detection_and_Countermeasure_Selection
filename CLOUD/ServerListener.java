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
	static String VM = "TP1";
	static int VM1_CNT=0,VM2_CNT=0,VM3_CNT=0;
	Index obj;
	
	ServerListener(int port,Index obj)
	{
		super();
		this.obj=obj;
		port_id=port;
		
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
				
			if(req.equals("LOGIN"))
			{
				String req_name=(String)ois.readObject();
				String sys_info=(String)ois.readObject();
				
				
				String info[]=sys_info.split("/");
				System.out.println("IPPPP "+sys_info);
				String user_NAME=req_name+":"+info[1];
				int id = user_NAME.hashCode();
				if(id<0)
				{
					id*=-1;
				}
				String user_ID=Integer.toString(id);	
				String resp = checkNodeDetails(req_name,info[1],user_ID);
				System.out.println("RESP "+resp);
				if((resp.indexOf("SUCCESS")!=-1)&(resp.indexOf("FAILURE")==-1))
				{
					oos.writeObject(resp);
					oos.writeObject(Index.CLOUD_DATA);
					obj.jtap4.append("Client: "+user_ID+"\n");
					obj.jtap4.append("Response: "+resp+"\n");
				}
				else
				{
					oos.writeObject(resp);
					obj.jtap4.append("Client: "+user_ID+"\n");
					obj.jtap4.append("Response: "+resp+"\n");
				}
			}
			else if(req.equals("LOGOUT"))
			{
				String u_id = (String)ois.readObject();
				String resp="";
				
				if(AllThreadGroup.registered_ID.contains(u_id))
				{
					String [] data = AllThreadGroup.nodeDetails.get(u_id);
					String port = data[2];
					System.out.println(port);
					AllThreadGroup.nodeDetails.remove(u_id);
					AllThreadGroup.registered_ID.remove(u_id);
					AllThreadGroup.registered_PORT.remove(port);
					resp = "LOGGED OUT SUCCESSFULLY!!!";
				}
				else
				{
					resp = "USER ID DOESN'T EXISTS";
				}
				
				oos.writeObject(resp);
			}
			else if(req.equals("DOWNLOAD"))
			{
				String U_ID = (String)ois.readObject();
				String FILE_NAME = (String)ois.readObject();
				String FILE_TYPE = (String)ois.readObject();
				
				if (obj.v1.contains(U_ID))
				{
					int i=obj.v1.indexOf(U_ID);
					long c=(Long)obj.v2.get(i);
					c++;
					obj.v2.set(i,c);
				}
				else
				{
					obj.v1.add(U_ID);
					long c=1;
					obj.v2.add(c);
					obj.v3.add("NORMAL");
				}
				
				
				if(Index.NICE_STATUS)
				{
					String resp = NICE.checkNodeStats(U_ID);
						
					System.out.println("resp: "+resp);	
					
					
						
					if(resp.indexOf("ID_RISTRICTED")==-1)
					{
						obj.jtap4.append(U_ID+": "+FILE_NAME+": "+resp+"\n");
						VMProfile.allocateResource(resp,FILE_NAME,FILE_TYPE);
					}
					else
					{
						int i=obj.v1.indexOf(U_ID);
						obj.v3.set(i,"ATTACK");
						obj.jtap4.append(U_ID+": "+FILE_NAME+": "+resp+"\n");
						
						String path="fake/"+FILE_TYPE;
						if (FILE_TYPE.equals("VIDEO"))
						FILE_NAME="fake.mpg";
						
						if (FILE_TYPE.equals("AUDIO"))
						FILE_NAME="fake.mp3";
						
						if (FILE_TYPE.equals("OTHERS"))
						FILE_NAME="fake.png";
						
						System.out.println("substring: "+resp.substring(14,resp.length()));
						VMProfile.allocateResource(resp.substring(14,resp.length()),FILE_NAME,path);
					}
					
				}
				else
				{
					VMProfile.allocateResource(U_ID,FILE_NAME,FILE_TYPE);
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	/*---------------------------------------------------------------------------------------*/
	
	public String checkNodeDetails(String u_NM,String u_IP,String u_ID)
	{
		String resp="";
		String res="";
		try
		{
			
			System.out.println("asd "+u_ID+" asdf");
			
			res=AllThreadGroup.checkThread(u_ID,"RegND");
			
			if(res.equals("ADD NODE"))
			{
				boolean flg=AllThreadGroup.addThread(u_ID,"RegND");
				if(flg)
				{
					String[] nd_details={u_NM,u_IP};
					resp = AllThreadGroup.addThreadDetals(u_ID,nd_details);
					resp = "SUCCESS:"+resp+":"+u_ID;
					
				}
				
			}
			else if(res.equals("NODE ALREADY EXISTS"))
			{
				resp = "FAILURE:NODE ALREADY EXISTS";
			}
			else if(res.equals("NODE RESTRICTED"))
			{
				resp = "FAILURE:NODE RESTRICTED";
			}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	return resp;
	}
}