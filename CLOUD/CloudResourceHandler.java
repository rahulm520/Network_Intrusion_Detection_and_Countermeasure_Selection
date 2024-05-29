import java.text.NumberFormat;

class CloudResourceHandler extends Thread
{
	Runtime runtime;
	NumberFormat format;
	long low_stat;
	CloudResourceHandler()
	{
		super("Cloud_Resource_Handler");
		runtime = Runtime.getRuntime();
		format = NumberFormat.getInstance();
		start();
	}
	
	public void run()
	{
		try
		{
			low_stat=runtime.maxMemory() / 1024;
			while(true)
			{
				String sb = new String();
				long maxMemory = runtime.maxMemory();
			    long allocatedMemory = runtime.totalMemory();
			    long freeMemory = runtime.freeMemory();
			    
			    sb+="Free Memory : " + format.format(freeMemory / 1024) + "\n";
		    	sb+="Allocated Memory : " + format.format(allocatedMemory / 1024) + "\n";
		    	sb+="Max Memory : " + format.format(maxMemory / 1024) + "\n";
		    	sb+="Total Free Memory : " + format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024) + "\n";	
		    
			    
			    if(((freeMemory + (maxMemory - allocatedMemory)) / 1024)<low_stat)
			    {
			    	low_stat=((freeMemory + (maxMemory - allocatedMemory)) / 1024);
			    }
			    
			    sb+="Low Stat : "+low_stat+" \n";
			    Index.jta5.setText(sb.toString());
			    
			    
				Thread.sleep(5000);
			}
					    
		    
		    
		    
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
}