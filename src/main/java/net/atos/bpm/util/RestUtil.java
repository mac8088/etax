package net.atos.bpm.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;

/**
 * Created by ahoo on 2017/4/19.
 */
public class RestUtil {

	private static final Logger log = LoggerFactory.getLogger(RestUtil.class);
	private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

	private static OkHttpClient client = new OkHttpClient();

	public static String rest(String url, String json, String method) throws IOException {
		if ("get".equalsIgnoreCase(method)) {
			JSONObject obj = JSON.parseObject(json);
			Map<String, String> params = new HashMap<String, String>();
			for (String key : obj.keySet()) {
				params.put(key, obj.getString(key));
			}
			return getForText(url, params);
		}
		RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, json);
		Builder builder = new Request.Builder().url(url);
		if ("post".equalsIgnoreCase(method)) {
			builder.post(body);
		} else if ("put".equalsIgnoreCase(method)) {
			builder.put(body);
		} else if ("delete".equalsIgnoreCase(method)) {
			builder.delete(body);
		} else if ("patch".equalsIgnoreCase(method)) {
			builder.patch(body);
		}
		Request request = builder.build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}

	private static String buildURL(String url, Map<String, String> params) {
		if (params == null || params.isEmpty()) {
			return url;
		}
		StringBuffer sb = new StringBuffer();
		for (String key : params.keySet()) {
			String value = params.get(key);
			if (!StringUtils.isEmpty(value)) {
				sb.append(key).append("=").append(value);
				sb.append("&");
			}
		}
		String paramsStr = sb.toString();
		if (paramsStr.length() > 0) {
			paramsStr = paramsStr.substring(0, paramsStr.length() - 1);
		}
		if (url.indexOf("?") > -1) {
			return url + "&" + paramsStr;
		} else {
			return url + "?" + paramsStr;
		}
	}

	public static String getForText(String url) throws IOException {
		return getForText(url, null);
	}

	public static String getForText(String url, Map<String, String> params) throws IOException {
		url = buildURL(url, params);
		if (log.isDebugEnabled()) {
			log.debug("the build url: {}", url);
		}
		Request request = new Request.Builder().url(url).get().build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}

	public static String postForText(String url, String json) throws IOException {
		return rest(url, json, "post");
	}

	public static String putForText(String url, String json) throws IOException {
		return rest(url, json, "put");
	}

	public static File postForFile(String url, String json) throws IOException {
		RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, json);
		Request request = new Request.Builder().url(url).post(body).build();
		Response response = client.newCall(request).execute();
		File downloadedFile = File.createTempFile(new Date().getTime() + "", ".tmp");
		BufferedSink sink = Okio.buffer(Okio.sink(downloadedFile));
		sink.writeAll(response.body().source());
		sink.close();
		return downloadedFile;
	}

	public static File getForFile(String url) throws IOException {
		Request request = new Request.Builder().url(url).get().build();
		Response response = client.newCall(request).execute();
		File downloadedFile = File.createTempFile(new Date().getTime() + "", ".tmp");
		BufferedSink sink = Okio.buffer(Okio.sink(downloadedFile));
		sink.writeAll(response.body().source());
		sink.close();
		return downloadedFile;
	}
}
