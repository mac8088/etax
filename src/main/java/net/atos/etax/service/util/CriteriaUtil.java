package net.atos.etax.service.util;

import java.util.*;

import org.aspectj.asm.IRelationship;
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

    /**
     * split up params
     * @param params
     * @return
     */
    public static Map<String, String> getMap(String params) {
        HashMap<String, String> map = new HashMap<>();

        int start = 0, len = params.length();

        while (start < len) {
            int i = params.indexOf('&', start);

            if (i == -1) {
                i = params.length(); // 此时处理最后的键值对
            }

            String keyValue = params.substring(start, i);

            int j = keyValue.indexOf('=');
            String key = keyValue.substring(0, j);
            String value = keyValue.substring(j + 1, keyValue.length());

            map.put(key, value);

            if (i == params.length()) {
                break;
            }

            start = i + 1; // index+1 为下一个键值对的起始位置
        }

        return map;
    }

    /**
     *the relationship between usertype and officetype
     * OFFICE_TYPE : HO,DP,PO,GT,DG,TO,PM,FO
     * USER_TYPE:OA,OC/SC,SA/SV,MU/MN
     */
    public static List<String> ChooseOfficeFromUserType(String type){
        List<String> list = new ArrayList<>();
        if (type.equals("MN")) {
            list.add("HO");
            list.add("DP");
            list.add("PO");
            list.add("GT");
            list.add("DG");
        }else if (ostrs.contains(type)){
            list.add("HO");
            list.add("DP");
            list.add("PO");
            list.add("GT");
            list.add("DG");
            list.add("TO");
        }else if (sstrs.contains(type)){
            list.add("HO");
        }else if (allstrs.contains(type)){
            list.add("HO");
            list.add("DP");
            list.add("PO");
            list.add("GT");
            list.add("DG");
            list.add("TO");
            list.add("PM");
            list.add("FO");
        }
        return list;
    }

    public static String ostrs = "OA,OC";
    public static String sstrs = "SA,SC";
    public static String allstrs = "SV,NU";
}
