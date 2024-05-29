import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JProgressBar;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.io.*;
import java.net.*;
import java.awt.Color;
import java.util.Random;
import java.util.ArrayList;

class VMProfile
{
	public static void allocateResource(String t_id,String f_nme,String f_typ)
	{
	
		ExecutorService T_POOL=null;

		
		String C_VM=ServerListener.VM;
		if(ServerListener.VM.equals("TP1"))
		{
			T_POOL = AllThreadGroup.T_POOL_1;
			ServerListener.VM = "TP2";	
		}
		else if(ServerListener.VM.equals("TP2"))
		{
			T_POOL = AllThreadGroup.T_POOL_2;
			ServerListener.VM = "TP3";	
		}
		else if(ServerListener.VM.equals("TP3"))
		{
			T_POOL = AllThreadGroup.T_POOL_3;
			ServerListener.VM = "TP1";	
		}
		
		
		Runnable worker = new ResourceHandler(t_id,f_nme,f_typ,C_VM);
        T_POOL.execute(worker);

		
		
		
	}
}

class ResourceHandler implements Runnable 
{
	String THREAD_ID="";
	String FILE_NAME="";
	String FILE_TYPE="";
	String VIRT_MACH="";
	
	ResourceHandler(String t_id,String f_nme,String f_typ,String VM)
	{
		
		this.THREAD_ID = t_id;
		this.FILE_NAME = f_nme;
		this.FILE_TYPE = f_typ;
		this.VIRT_MACH = VM;
		
			
	}
	
	public void run()
	{
		JProgressBar jpb = null;
		if(VIRT_MACH.equals("TP1"))
		{
			System.out.println("IN TP1");
			jpb = new JProgressBar(0, 100);
			jpb.setBounds(10,50*ServerListener.VM1_CNT+10,250,48);
			jpb.setIndeterminate(true);
			jpb.setValue(0);
			jpb.setStringPainted(true);
			jpb.setForeground(new Color(0,255,128));
			Border border = BorderFactory.createTitledBorder(THREAD_ID);
    		jpb.setBorder(border); 
			Index.jp21.add(jpb);
			Index.jp21.revalidate();
			ServerListener.VM1_CNT++;
		}
		else if(VIRT_MACH.equals("TP2"))
		{
			System.out.println("IN TP2");
			jpb = new JProgressBar(0, 100);
			jpb.setBounds(10,50*ServerListener.VM2_CNT+10,250,48);
			jpb.setIndeterminate(true);
			jpb.setValue(0);
			jpb.setStringPainted(true);
			jpb.setForeground(new Color(0,255,128));
			Border border = BorderFactory.createTitledBorder(THREAD_ID);
    		jpb.setBorder(border); 
			Index.jp22.add(jpb);
			ServerListener.VM2_CNT++;
		}
		else if(VIRT_MACH.equals("TP3"))
		{
			System.out.println("IN TP3");
			jpb = new JProgressBar(0, 100);
			jpb.setBounds(10,50*ServerListener.VM3_CNT+10,250,48);
			jpb.setIndeterminate(true);
			jpb.setValue(0);
			jpb.setStringPainted(true);
			jpb.setForeground(new Color(0,255,128));
			Border border = BorderFactory.createTitledBorder(THREAD_ID);
    		jpb.setBorder(border); 
			Index.jp23.add(jpb);
			ServerListener.VM3_CNT++;
		}
		System.out.println("thread id: "+THREAD_ID);
		String[] split_node_id = THREAD_ID.split(":");
		
		String[] node_details = AllThreadGroup.nodeDetails.get(split_node_id[0]);
	
		try
		{
			File file = new File(FILE_TYPE+"/"+FILE_NAME);
			System.out.println("File type: "+FILE_TYPE);
			System.out.println("File Name: "+FILE_NAME);
			if (!file.exists())
			System.out.println("File doesn't exist");
			
			if(file.exists())
			{
				int index=1;
				int start=0;
				int end=65535;
				float completed_pkt=100;
				
				System.out.println("File: "+file);
				FileInputStream fis = new FileInputStream(file);
				byte[] file_data=new byte[fis.available()];
				fis.read(file_data);
				fis.close();
				
				int file_len = (int)file.length();
				int tot_pkt = file_len/65535;
				int mod_pkt = file_len%65535;
				if(mod_pkt>0)
				{
					tot_pkt+=1;
				}
				completed_pkt = completed_pkt/tot_pkt;
				System.out.println("TOT "+file_len+" pkt "+tot_pkt+" MOD "+mod_pkt+" IP "+node_details[1]+" PORT "+completed_pkt+" asd");
				
				Socket s = new Socket(node_details[1],Integer.parseInt(node_details[2]));
				ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				
				oos.writeObject("FILE_DATA");
				oos.writeObject(file.getName());
				oos.writeObject(file_len);
				oos.writeObject(tot_pkt);  
				
				Thread.sleep(1500);
				jpb.setIndeterminate(false);
				
				
				while(index<=tot_pkt)
				{
					byte[] b;
					if(tot_pkt==1)
					{
						b = new byte[file_len];
						end = file_len;
					}
					else
					{
						b = new byte[65535];
					}
					
					int j=0;
					int k=0;
					for(int i=start;i<end;i++)
					{
						b[j]=file_data[i];
						j++;
						k++;
					}
					System.out.println("K "+k);
					index++;
					start=+end;
					if(index==tot_pkt)
					{
						end=file_len;
					}
					else
					{
						end=end+65535;
					}
					
					int val = new Random().nextInt(10);
					Thread.sleep(val*150);
					
					oos.writeObject(b);
					
					
					//completed_pkt+=completed_pkt;
            		jpb.setValue((int)completed_pkt*index);
            		if(jpb.getValue()==100)
            		{
            			String[] split_t_id = THREAD_ID.split(":");
            			if(AllThreadGroup.curr_alive_ID_Info.containsKey(split_t_id[0]))
            			{
            				ArrayList vec = AllThreadGroup.curr_alive_ID_Info.get(split_t_id[0]);
	            			String node_info = (String)vec.get(0);
	            			String[] split_node_info = node_info.split(":");
	            			int count = Integer.parseInt(split_node_info[1]);
	            			System.out.println("COUNT "+count);
	            			count=-1;
	            			System.out.println("COUNT "+count);
	            			if(count>0)
	            			{
	            				String[] split_time = split_node_info[2].split(";");
		            			String time="";
		            			for(int i=0;i<split_time.length-1;i++)
		            			{
		            				time=time+";"+split_time[i];
		            			}
		            			ArrayList nu_vec = new ArrayList();
		            			nu_vec.add(split_node_info[0]+":"+count+":"+time);
		            			AllThreadGroup.curr_alive_ID_Info.put(split_t_id[0],nu_vec);
		            			System.out.println("IN 1111111");
	            			}
	            			else
	            			{
	            				AllThreadGroup.curr_alive_ID.remove(split_t_id[0]);
								AllThreadGroup.curr_alive_ID_Info.remove(split_t_id[0]);
								System.out.println("IN 22222222");
	            			}
            				
            				
            			}
            			
            			
            		}
					
				}
				
				s.close();
				
			}
		
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
				
				
	}
}