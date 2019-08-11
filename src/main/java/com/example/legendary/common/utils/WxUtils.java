package com.example.legendary.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.net.util.Base64;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.spec.AlgorithmParameterSpec;

/**
 * 微信小程序工具类
 * @author: 吴嘉晟
 * @date: 2018.10.11
 * @version 1.0
 * @date 2018.11.5
 * @version 1.1
 * @remark: 修复部分上线存在风险的问题
 */
public class WxUtils {
	private static final String APPID = "wx96603e07aef7d2b8";
	private static final String SECRET = "305bfa04b59a777f5b51e4d22d8bd780";
	private static final String GRANT_TYPE ="authorization_code";
	private static final String URL="https://api.weixin.qq.com/sns/jscode2session";

	/**
	 * 获取openId
	 * @param code 小程序code
	 * @return openID
	 */
	public static String getOpenId(String code) {
		MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
		params.add("appid",APPID);
		params.add("secret",SECRET);
		params.add("grant_type",GRANT_TYPE);
		params.add("js_code",code);
		return HttpClientUtils.post(URL, params);
	}

	/**
	 * 微信用户解密
	 * @param key keu
	 * @param iv iv
	 * @param encData 时间
	 * @return 解密数据
	 */
	private static String decrypt(byte[] key, byte[] iv, byte[] encData) throws Exception {
		AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
		return new String(cipher.doFinal(encData), StandardCharsets.UTF_8);
	}

	/**
	 * 解密用户手机号
	 * @param key sessionKey
	 * @param iv iv
	 * @param encData 时间
	 * @return 手机号
	 */
	public static String getPhone(String key, String iv, String encData) {
		Base64 base64 = new Base64();
		byte[] raw = base64.decode(iv);
		byte[] sessionKey = base64.decode(key);
		byte[] encryptedDataB = base64.decode(encData);
		try {
			return JSONObject.parseObject(WxUtils.decrypt(sessionKey, raw, encryptedDataB)).getString("phoneNumber");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成微信二维码
	 * @param sceneStr 参数
	 * @param path 二维码指向小程序的路径
	 * @param filePath	文件存放路径
	 */
	public static void getQR(String sceneStr, String path, String filePath) {

		String result = get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+SECRET);
		String accessToken = JSONObject.parseObject(result).getString("access_token");
		try
		{
			java.net.URL url = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			// 提交模式
			httpURLConnection.setRequestMethod("POST");
			// 发送POST请求必须设置如下两行
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
			// 发送请求参数
			JSONObject paramJson = new JSONObject();
			paramJson.put("scene", sceneStr);
			paramJson.put("page", path);
			paramJson.put("width", 430);

			printWriter.write(paramJson.toString());
			// flush输出流的缓冲
			printWriter.flush();
			//开始获取数据
			BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
			OutputStream os = new FileOutputStream(new File(filePath));
			int len;
			byte[] arr = new byte[1024];
			while ((len = bis.read(arr)) != -1)
			{
				os.write(arr, 0, len);
				os.flush();
			}
			os.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 获取access_token
	 * @param url api路径
	 * @return access_token
	 */
	private static String get(String url) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<String> entity = new HttpEntity<>(url, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		return responseEntity.getBody();
	}

}