import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class AllThreadGroup
{
	static ExecutorService T_POOL_1;
	static ExecutorService T_POOL_2;
	static ExecutorService T_POOL_3;
	static ArrayList curr_alive_ID;
	static ArrayList registered_ID;
	static ArrayList restricted_ID;
	static ArrayList registered_PORT;
	static Map <String,String[]> nodeDetails;
	static Map <String,ArrayList> curr_alive_ID_Info;
	
	AllThreadGroup()
	{
		
		nodeDetails = new HashMap<String,String[]>();
		curr_alive_ID_Info = new HashMap<String,ArrayList>();
		T_POOL_1 = Executors.newFixedThreadPool(10);
		T_POOL_2 = Executors.newFixedThreadPool(10);
		T_POOL_3 = Executors.newFixedThreadPool(10);

		curr_alive_ID = new ArrayList();
		registered_ID = new ArrayList();
		registered_PORT = new ArrayList();
		restricted_ID = new ArrayList();
	}
	
	public static boolean addThread(String id,String type)
	{
		boolean flg=false;
		
		if(type.equals("RegND"))
		{
			registered_ID.add(id);
			flg = true;
		}
		
	return flg;
	}
	
	
	public static String checkThread(String id,String type)
	{
		String resp="";
		
		if(type.equals("RegND"))
		{
			if(!restricted_ID.contains(id))
			{
				if(!registered_ID.contains(id))
				{
					resp="ADD NODE";
				}
				else
				{
					resp="NODE ALREADY EXISTS";
				}
			}
			else
			{
				resp="NODE RESTRICTED";
			}
		}
		else if(type.equals("RestND"))
		{
			
		}
		else if(type.equals("TGND"))
		{
			
		}
		
		
		
	return resp;
	}
	
	public static String addThreadDetals(String id,String[] details)
	{
		String resp="";
		boolean chk=true;
		String[] nd_dt = new String[3]; 
		int port=0;
		do
		{
			Random r=new Random();
			
			port=r.nextInt(10000)+r.nextInt(5000);
			resp=Integer.toString(port);
			if(registered_PORT.size()!=0)
			{
				if(!registered_PORT.contains(resp))
				{
					registered_PORT.add(resp);
					chk=false;
				}
				else
				{
					chk=true;		
				}
			}
			else
			{
				registered_PORT.add(resp);
				chk=false;
			}
			
			
		}while(chk);
		
		nd_dt[0]=details[0];
		nd_dt[1]=details[1];
		nd_dt[2]=resp;
		
		nodeDetails.put(id,nd_dt);
		
		System.out.println("PORT "+resp+" asdf");
	return resp;	
	}
}