package cn.com.dhcc;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.junit.Test;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MessageProcessingModel;
import org.snmp4j.smi.Counter32;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.PDUFactory;
import org.snmp4j.util.TableEvent;
import org.snmp4j.util.TableListener;
import org.snmp4j.util.TableUtils;

public class TableUtilTest {
	
	@Test
	public void testWalk(){
	}
	
	public static void main(String[] args){
		SnmpService service = new SnmpService();
		try {
			List<TableEvent> eventList = service.createTable("192.168.1.197", "HBnmc311", new String[]{".1.3.6.1.2.1.2.2"});
			for(TableEvent event:eventList){
				VariableBinding[] columns = event.getColumns();
				for(VariableBinding column:columns)
					System.out.println(column);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

 class SnmpService  
{
   private static final int SNMP_VERSION = 0;
   private static final int RETRIES = 2;
   private static final int TIMEOUT = 10000;   
   //private Snmp snmp;
   private Vector vbs = new Vector();
   
	public static int default_version = org.snmp4j.mp.SnmpConstants.version2c;
	public static final int default_retries = 3;
	private Integer default_port = new Integer(161);
	private int default_timeout = 5000;
	public static String snmpversion = "";

   public List<TableEvent> createTable(String address,String community,String[] oids) throws IOException
   {
   	TransportMapping  transport = new DefaultUdpTransportMapping();

	Snmp snmp = new Snmp(transport);
		CommunityTarget target  = new CommunityTarget();
		target.setCommunity(new OctetString(community));
		target.setVersion(default_version);
		target.setAddress(
			GenericAddress.parse(address + "/" + default_port));
		target.setRetries(default_retries);
		target.setTimeout(default_timeout);
		snmp.listen();
		
		TableUtils tableUtils = new TableUtils(snmp, new DefaultPDUFactory(PDU.GETBULK));
		OID[] columns = new OID[oids.length];
		for (int i = 0; i < columns.length; i++) {
			columns[i] = (new VariableBinding(new OID(oids[i]))).getOid();
		}
		List<TableEvent> list =
				tableUtils.getTable(
					target,
					columns,
					null,
					null);
		
       return list;
   }
  
   
   public String[][] getTableData(String address,String community,String[] columnoids) throws Exception
   {
   	  String[][] tablevalues = null;
      TableEvent row = null;
      VariableBinding[] columnvalues = null;
      VariableBinding columnvalue = null;
      
   	  
      List rowvalues = null;
      try{
     	 rowvalues = createTable(address,community,columnoids);
      }catch(Exception e){
     	 
      }
      if(rowvalues == null)return tablevalues;
      tablevalues = new String[rowvalues.size()][columnoids.length];

      for (int i = 0; i < rowvalues.size(); i++)
      {
         row = (TableEvent) rowvalues.get(i);
         columnvalues = row.getColumns();
         
         if(columnvalues!=null)
         {
            for (int j = 0; j < columnvalues.length; j++)
            {     
         	  //;
               columnvalue = columnvalues[j];
               if(columnvalue == null)continue;
               
               String value=columnvalue.toString().substring(columnvalue.toString().indexOf("=")+1,columnvalue.toString().length()).trim();
               tablevalues[i][j] = value;
            }
         }
      }
   
      return tablevalues;
   }
}
