package cn.com.dhcc;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GenerateStorageAlarmSqlTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		List<String> moTypes = new ArrayList();
		moTypes.add("1509");
		moTypes.add("1501");
		moTypes.add("1508");
		
		List<String> dynamicLevelCauses = new ArrayList();

		dynamicLevelCauses.add("CPU利用率阀值越界");
		dynamicLevelCauses.add("丢包率阀值越界");
		dynamicLevelCauses.add("内存利用率阀值越界");
		dynamicLevelCauses.add("错包率阀值越界");
		dynamicLevelCauses.add("文件系统使用率阀值越界");
		dynamicLevelCauses.add("物理卷使用率阀值越界");
		dynamicLevelCauses.add("磁盘分区使用率阀值越界");
		dynamicLevelCauses.add("表空间使用率阀值越界");
		dynamicLevelCauses.add("响应时间阀值越界");
		dynamicLevelCauses.add("电源传感器配置变更");

		List<String> topLevelCauses = new ArrayList();

		topLevelCauses.add("设备不能访问");
		topLevelCauses.add("ENT坏");
		topLevelCauses.add("电源坏");
		topLevelCauses.add("风扇坏");
		topLevelCauses.add("设备不可达");
		topLevelCauses.add("接口down");
		topLevelCauses.add("电源配置变更" );
		topLevelCauses.add("电源传感器坏" );
		topLevelCauses.add("链路:down" );
		topLevelCauses.add("系统单元坏" );
		topLevelCauses.add("接口状态变更" );
		
		Map<String,String> moTypeIpMap = new HashMap();
		moTypeIpMap.put("1501", "10.204.248.58");
		moTypeIpMap.put("1508", "10.209.160.80");
		moTypeIpMap.put("1509", "10.220.49.250");

		String template  = "INSERT INTO tfactive( fAlmSN,MOSN,fRMOSN,fPMOSN, fMOType,"+
"fAlarmKpi,fMOIp,fCause, fSeverity,fStatus,fTrend, fOccurTime,fLastTime, fCount, fDetail,fHealth,flag) VALUES(",
	suffix = ");\n";
		
		StringBuilder all = new StringBuilder();
		for(String moType:moTypes){
			int causeNum = 10;
			for(String dynamicLevelCause:dynamicLevelCauses){
				
				for(int severity = 2;severity<6;severity++){
					StringBuilder one = new StringBuilder();
					one.append(template)
					.append(moType+causeNum+severity)
					.append(",")
					.append("998003776")
					.append(",")
					.append("998003776")
					.append(",")
					.append("998003776")
					.append(",'")
					.append(moType)
					.append("','")
					.append("_____")
					.append("','")
					.append(moTypeIpMap.get(moType))
					.append("','")
					.append(dynamicLevelCause)
					 .append("',")
					 .append(severity)
					 .append(",'")
					 .append("发生")
					 .append("','")
					 .append("不变")
					 .append("','")
					 .append("2017-11-26 16:00:00")
					 .append("','")
					 .append("2017-11-26 16:00:00")
					 .append("',")
					 .append("1")
					 .append(",'")
					 .append("test测试消息，中英文混合，测试编码utf-8 "+dynamicLevelCause)
					  .append("',")
					  .append("2")
					   .append(",")
					   .append("1")
					 .append(suffix);
					
					all.append(one);
				}
				causeNum++;
			}
		}
		
		for(String moType:moTypes){
			int causeNum = 20;
			for(String topLevelCause:topLevelCauses){
				StringBuilder one = new StringBuilder();
				one.append(template)
				.append(moType+(causeNum++)+4)
				.append(",")
				.append("998003776")
				.append(",")
				.append("998003776")
				.append(",")
				.append("998003776")
				.append(",'")
				.append(moType)
				.append("','")
				.append("_____")
					.append("','")
				.append(moTypeIpMap.get(moType))
				
				.append("','")
				.append(topLevelCause)
				 .append("',")
				 .append("4")
				 .append(",'")
				 .append("发生")
				 .append("','")
				 .append("不变")
				 .append("','")
				 .append("2017-11-26 16:00:00")
				 .append("','")
				 .append("2017-11-26 16:00:00")
				 .append("',")
				 .append("1")
				 .append(",'")
				 .append("test测试消息，中英文混合，测试编码utf-8 "+topLevelCause)
				  .append("',")
				  .append("2")
				   .append(",")
				   .append("1")
				 .append(suffix);
				all.append(one);
			}
		}
		
		System.out.println(all);
	}

}
