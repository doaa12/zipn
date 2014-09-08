package lbs.baidu.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author zby
 * 2014-9-8 下午6:15:51
 */
public class LBSTest {

	public static void main(String[] args) throws Exception {
		
		//String address = "湖南省汉寿县军山铺镇杏花村马颈组";
		String address = "上海市浦东新区中科路2500弄";
		String ak = "hUOPaUZIwH7GxmR2KvyMGA06";
		
		/*
		String sk = "jqw6EAnb5Hpx0B59idvG2r7gMCKSQUVM";
		
		SnCal cal = new SnCal();
		String sn = cal.getSn(address, ak, sk);
		*/
		
		String surl = "http://api.map.baidu.com/geocoder/v2/?address=" + address + "&output=json&ak=" + ak;
		
		URL url = new URL(surl);
		
		HttpURLConnection conn = null;
		
		try{
			
			conn = (HttpURLConnection)url.openConnection();
			conn.connect();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder content = new StringBuilder();
			String line = null;
			
			while((line = reader.readLine()) != null) {
				content.append(line);
			}
			
			System.out.println(content.toString());
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			conn.disconnect();
		}
		
	}
	
}
