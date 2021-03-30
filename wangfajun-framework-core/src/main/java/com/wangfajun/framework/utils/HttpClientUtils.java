package com.wangfajun.framework.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wangfajun.framework.exception.FrameWorkErrorCode;
import com.wangfajun.framework.exception.FrameworkErrorException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;


@Slf4j
public class HttpClientUtils {

    private static RequestConfig requestConfig;

    private static final String CHARSET = "utf-8";

    private HttpClientUtils() {

    }

    static {
        // 设置请求和传输超时时间
        requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
    }

    /**
     * post请求传输json参数
     * @param url url地址
     * @param jsonParam 请求参数
     * @return
     */
    public static JSONObject httpPost(String url, JSONObject jsonParam) {
        // post请求返回结果
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        // 设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            if (null != jsonParam) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), CHARSET);
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // 请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 读取服务器返回过来的json字符串数据
                String str = EntityUtils.toString(result.getEntity(), CHARSET);
                // 把json字符串转换成json对象
                jsonResult = JSON.parseObject(str);
            } else {
                log.error("post请求提交失败, httpStatus:{}", result.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            log.error("post请求提交失败, 请求地址:{}", url, e);
			throw new FrameworkErrorException(FrameWorkErrorCode.FAILED);

		} finally {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }

    /**
     * post请求
     * @param url url地址
     * @param nameValuePairs 请求参数
     * @return 响应数据
     */
    public static JSONObject httpPost(String url, List<NameValuePair> nameValuePairs) {
        // 请求返回结果
        JSONObject jsonResult;

        HttpPost httpPost = new HttpPost(url);
        // 设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        // 设置头信息http-head
        httpPost.setHeader("charset","UTF-8");
        httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");

        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // 请求发送成功，并得到响应
            // 读取服务器返回过来的json字符串数据
            String str = EntityUtils.toString(result.getEntity(), CHARSET);
            // 把json字符串转换成json对象
            jsonResult = JSON.parseObject(str);
        } catch (IOException e) {
            log.error("post请求提交失败, 请求地址:{}", url, e);
			throw new FrameworkErrorException(FrameWorkErrorCode.FAILED);
		} finally {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }

    /**
     * post请求传输String参数 例如：name=Jack&sex=1&type=2
     * Content-type:application/x-www-form-urlencoded
     * @param url url地址
     * @param strParam 参数
     * @return
     */
    public static JSONObject httpPost(String url, String strParam) {
        // post请求返回结果
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            if (null != strParam) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(strParam, CHARSET);
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // 请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 读取服务器返回过来的json字符串数据
                String str = EntityUtils.toString(result.getEntity(), CHARSET);
                // 把json字符串转换成json对象
                jsonResult = JSON.parseObject(str);
            } else {
                log.error("post请求提交失败, httpStatus:{}", result.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            log.error("post请求提交失败:{}", url, e);
			throw new FrameworkErrorException(FrameWorkErrorCode.FAILED);
		} finally {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }

    /**
     * 发送get请求
     * @param url url地址
     * @return
     */
    public static JSONObject httpGet(String url) {
        // get请求返回结果
        JSONObject jsonResult = null;
        // 发送get请求
        HttpGet request = new HttpGet(url);
        request.setConfig(requestConfig);
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            CloseableHttpResponse result = client.execute(request);
            // 请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 读取服务器返回过来的json字符串数据
                String strResult = EntityUtils.toString(result.getEntity(), CHARSET);
                // 把json字符串转换成json对象
                jsonResult = JSON.parseObject(strResult);
            } else {
                log.error("get请求提交失败, httpStatus:{}", result.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            log.error("get请求提交失败:{}", url, e);
			throw new FrameworkErrorException(FrameWorkErrorCode.FAILED);
        } finally {
            request.releaseConnection();
        }
        return jsonResult;
    }

}
