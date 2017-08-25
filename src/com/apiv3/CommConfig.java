package com.apiv3;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

public class CommConfig 
{
	/**
	 *
	 String appkey = "233eda56-b2bb-40ad-b785-33b27189ed50";
	 String appsecret = "345e5577-3b17-4d1b-8d73-80c1981ef8a0";
	 */
	public static String  AppKey = "233eda56-b2bb-40ad-b785-33b27189ed50";
	public static String  AppSecret = "345e5577-3b17-4d1b-8d73-80c1981ef8a0";
	public static String  MotionBaseURL = "http://api.e6gps.com/public/v3/Inface/Call";
	public static String  ReportBaseURL = "http://api.e6gps.com/public/v3/StatisticsReport/Call";
	public static String  VehicleMap = "http://api.e6gps.com/public/v3/MapServices/Orientation.aspx?";
	public static String  LoginE6WebGisUrl = "http://login.e6gps.com/Home/LoginWithToken?";
	 
	
	public static String Format = "json";
	
	
	//获取系统当前时间
	public static String GetNowTime()
	{
		Date dt=new Date();//如果不需要格式,可直接用dt,dt就是当前系统时间
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置显示格式
		String nowTime="";
		nowTime= df.format(dt);//用DateFormat的format()方法在dt中获取并以yyyy-MM-dd HH:mm:ss格式显示
		return nowTime;
	}
	
	public static String GetSign(Map<String, String> map)
	{
		//将所有的键放在一个collection进行排序，是为了生成签名用到
		ArrayList<String> keyList = new ArrayList<String>();
		for(String dataKey  : map.keySet())
		{
			keyList.add(dataKey);
		}
		
		//对键进行排序
		Collections.sort(keyList);
		
		//根据键再从map当中获取值，拼接成字符串
		StringBuilder  sb = new StringBuilder();
		for(String key : keyList)
		{
			String value = map.get(key);
			if(value !=null)
			{
				sb.append(key);
				sb.append(value);	
			}
			
		}
		
		return Sign(sb.toString());
	}
	
	 /// <summary>
    /// 签名字符串
    /// </summary>
    /// <param name="pPrestr">需要签名的字符串</param>
    /// <param name="pKey">密钥</param>
    /// <param name="_input_charset">编码格式</param>
    /// <returns>签名结果</returns>
    public static String Sign(String pPrestr)
    {
        pPrestr = AppSecret + pPrestr + AppSecret;
        String md5Str = MD5Util.str2MD5(pPrestr);
        return md5Str.toUpperCase();
    }
}
