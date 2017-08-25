package com.apiv3;

import com.e6gps.util.http.HttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class InvaceAPiV3CallServlet extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        // TODO Auto-generated method stub
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String flag = req.getParameter("flag");
        // http://localhost:8080/ApiV3CallService/index.jsp
        if (flag.equals("GetVehcileInfo"))
        {
            GetVehcileInfo(req, resp);

        } else if (flag.equals("VehicleMap"))
        {

            VehicleMap(req, resp);
        } else if (flag.equals("GetLoginStr"))
        {

            GetLoginStr(req, resp);
        } else if (flag.equals("AddDriverLine"))
        {
            AddDriverLine(req, resp);
        }
    }

    private void GetVehcileInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        //
        Map<String, String> map = new HashMap<String, String>();

        String vehicle = "粤BN0195";
        // 生成签名 先将key进行排序，然后再根据key值在map当中获取相应的值，然后再进行添加
        map.put("timestamp", CommConfig.GetNowTime());
        map.put("format", CommConfig.Format);
        map.put("appkey", CommConfig.AppKey);
        map.put("method", "GetVehcileInfo");
        map.put("sessionid", "");
        map.put("vehicle", vehicle);

        // 根据字符串找生成签名
        String sign = CommConfig.GetSign(map);
        map.put("sign", sign);

        map.remove("vehicle");
        //然后再进行编码:
        map.put("vehicle", vehicle);


        // 开始调用接口
        String res = HttpClient.MethodPostResponse(CommConfig.MotionBaseURL, map);
        System.out.print(res);
    }

    private void VehicleMap(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {


        Map<String, String> map = new HashMap<String, String>();

        String timestamp = CommConfig.GetNowTime();
        String vehicle = "粤BY1518";
        // 生成签名 先将key进行排序，然后再根据key值在map当中获取相应的值，然后再进行添加
        map.put("timestamp", timestamp);
        map.put("appkey", CommConfig.AppKey);
        map.put("vehicle", vehicle);

        // 根据字符串找生成签名
        String sign = CommConfig.GetSign(map);
        map.put("sign", sign);

        StringBuilder sb = new StringBuilder();
        // 拼接参数
        sb.append("appkey=");
        sb.append(CommConfig.AppKey);
        sb.append("&timestamp=");
        sb.append(timestamp);
        sb.append("&vehicle=");
        sb.append(java.net.URLEncoder.encode(vehicle, "utf-8"));
        //sb.append(vehicle);
        sb.append("&sign=");
        sb.append(sign);

        // 拼接URL 开始转向
        String FullUrl = CommConfig.VehicleMap + sb.toString();
        resp.sendRedirect(FullUrl);

        // JSONObject obj = new JSONObject(sign);
        // String totalBkCnt = obj.getString("bCt");

    }

    private void GetLoginStr(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        //
        Map<String, String> map = new HashMap<String, String>();

        // 生成签名 先将key进行排序，然后再根据key值在map当中获取相应的值，然后再进行添加
        map.put("timestamp", CommConfig.GetNowTime());
        map.put("format", CommConfig.Format);
        map.put("appkey", CommConfig.AppKey);
        map.put("method", "GetLoginStr");

        // 根据字符串找生成签名
        String sign = CommConfig.GetSign(map);
        map.put("sign", sign);

        try
        {
            // 开始调用接口
            String jsonstr = HttpClient.MethodPostResponse(CommConfig.MotionBaseURL, map);

            //{"result":{"message":"",
            // "data":[{"LoginKey":"AsRSHGPrrVW2BbyBIy1TvMRrKaaGZ/keP62yiFMVlRijnoruQhE11mY1mv7hs28jBgUdQBh8joo="}],
			// "code":"1"}}
            JSONObject jsonObj = new JSONObject(jsonstr);
            JSONObject resultJsonObj = jsonObj.getJSONObject("result");

            JSONArray jsonArray = resultJsonObj.getJSONArray("data");
            JSONObject dataJsonObject = jsonArray.getJSONObject(0);
            String LoginKey = dataJsonObject.getString("LoginKey");

            // 拼接URL 开始转向
            String FullUrl = CommConfig.LoginE6WebGisUrl + "sid=" + LoginKey;
            resp.sendRedirect(FullUrl);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void AddDriverLine(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {

        Map<String, String> map = new HashMap<String, String>();
        // 生成签名 先将key进行排序，然后再根据key值在map当中获取相应的值，然后再进行添加
        map.put("appkey", CommConfig.AppKey);
        map.put("timestamp", CommConfig.GetNowTime());
        map.put("format", CommConfig.Format);
        map.put("method", "AddDriverLine");
        map.put("linename", "测试Test8");
        map.put("slonlat", "114.0587,23.76691");
        map.put("elonlat", "114.023,22.72366");
        //map.put("midlonlat","113.05532,22.74636;113.0562,22.7934");
        map.put("midlonlat", "");
        map.put("remark", "西安至天津线888888");
        map.put("linetype", "3");

        // 根据字符串找生成签名
        String sign = CommConfig.GetSign(map);
        map.put("sign", sign);
        //然后再进行编码:

        // 开始调用接口
        String res = HttpClient.MethodPostResponse(CommConfig.MotionBaseURL, map);
        System.out.print(res);
    }
}
