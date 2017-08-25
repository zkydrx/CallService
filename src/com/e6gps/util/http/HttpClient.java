package com.e6gps.util.http;

import org.apache.commons.httpclient.NameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.Map;


public class HttpClient
{
    public static String MethodPostResponse(String url, Map<String, String> m)
    {
        String ret = "";
        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
        /* Post传递变量必须用NameValuePair[]数组 */
        NameValuePair[] params = generatNameValuePair(m);
        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        request.setCharset("UTF-8");
        request.setParameters(params);
        request.setUrl(url);
        HttpResponse response = httpProtocolHandler.execute(request);

        if (response == null)
        {
            return "{'status':'2'}";
        }

        try
        {
            ret = response.getStringResult();
        }
        catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ret = "{'status':'0'}";
        }

        return ret;
    }

    /**
     * MAP类型数组转换成NameValuePair类型
     *
     * @param properties MAP类型数组
     * @return NameValuePair类型数组
     */
    private static NameValuePair[] generatNameValuePair(Map<String, String> properties)
    {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet())
        {
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }
        return nameValuePair;
    }


}
