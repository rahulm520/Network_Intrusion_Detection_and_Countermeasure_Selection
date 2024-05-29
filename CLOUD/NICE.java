import java.util.ArrayList;
class NICE
{
	NICE()
	{
		
	}
	
	public static String checkNodeStats(String u_id)
	{
		String resp="";
		
		if(AllThreadGroup.curr_alive_ID.contains(u_id))
		{
			ArrayList node_info = AllThreadGroup.curr_alive_ID_Info.get(u_id);
			String node_inf = (String)node_info.get(0);
			System.out.println(node_inf);
			String[] split_info = node_inf.split(":");
			int count = Integer.parseInt(split_info[1]);
			String cur_time = split_info[2];
			
			if((count++)>=Index.NICE_THRESHOLD)
			{
				System.out.println("CNT1 FAIL "+count);
			//	AllThreadGroup.curr_alive_ID.remove(u_id);
			//	AllThreadGroup.curr_alive_ID_Info.remove(u_id);
				AllThreadGroup.restricted_ID.add(u_id);
				resp = "ID_RISTRICTED:";
				
			}
		//	else
			{
				System.out.println("CNT2 "+count);
				
				ArrayList vec = new ArrayList();
				long sys_tim = System.currentTimeMillis();
				cur_time+=";"+sys_tim;
				vec.add(u_id+":"+count+":"+cur_time);
				AllThreadGroup.curr_alive_ID_Info.put(u_id,vec);
				
				//if (!resp.equals("ID_RESTRICTED"))
				resp+=u_id+":"+count;
				resp.trim();
				
				
			}
		}
		else
		{
			if(!AllThreadGroup.restricted_ID.contains(u_id))
			{
				
				
				resp=u_id+":1";
			}
			else
			{
				resp = "ID_RISTRICTED:"+u_id+":1";			
			}
			
			ArrayList vec = new ArrayList();
				long sys_tim = System.currentTimeMillis();
				vec.add(u_id+":1:"+sys_tim);
				AllThreadGroup.curr_alive_ID.add(u_id);
				AllThreadGroup.curr_alive_ID_Info.put(u_id,vec);
			
		}
		
	return resp;
	}
}