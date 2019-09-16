package net.atos.bpm.util;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CriteriaUtil {

	private static final Logger log = LoggerFactory.getLogger(CriteriaUtil.class);

	/**
	 * build the query criteria for the business entity. this method is used to
	 * build the RESTful get URL form the previous HTTP parameters. For example
	 * the following could be a valid requests:
	 * <code> /jh-users?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
	 * 
	 * here support two kind of URL: <br/>
	 * <code> http://ip:8090/api/jh-users</code>
	 * <code> http://ip:8090/api/jh-user?sort=id,desc</code>
	 * 
	 * here list some comparison operators: <br/>
	 * <code>equals,in,contains</code> <code>specified</code>
	 * <code>greaterThan,greaterOrEqualThan</code> <code>lessThan</code>
	 */
	public static String build(String apiUrl, Map<String, String[]> paramMap) {

		StringBuilder sb = new StringBuilder().append(apiUrl);

		// hard-code with sort by
		sb.append(sb.indexOf("?") < 0 ? "?" : "&").append("sort=id,desc");

		Set<String> keySet = paramMap.keySet();
		for (String key : keySet) {
			String[] values = paramMap.get(key);
			if (values != null && values.length > 0) {
				if (values.length == 1) {
					sb.append('&').append(key).append('=').append(values[0]);
				} else {
					throw new UnsupportedOperationException("Don't support multi-parameterValues!");
				}
			}
		}

		String getUrl = sb.toString();
		log.debug("the URL of HTTP Get: " + getUrl);
		return getUrl;
	}

}
