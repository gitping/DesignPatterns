package com.frame.sxgou;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import net.tsz.afinal.http.AjaxParams;

import com.frame.lib.log.L;
import com.frame.lib.utils.AES256Encryption;
import com.frame.lib.utils.AESHelper;
import com.frame.lib.utils.FUtils;
import com.frame.lib.utils.MD5Util;
import com.frame.sxgou.model.FrameRequest;
import com.frame.sxgou.model.RequestDTO;
import com.google.gson.Gson;
import com.suixingou.sdkcommons.packet.CRequestBody;
import com.suixingou.sdkcommons.packet.IdEntity;
import com.suixingou.sdkcommons.utils.GsonUtil;

public class SxgouFrameUtil {
	private final static Gson gson = new Gson();
	private static String jsonTem;
	private static String s;

	/**
	 * 包装参数 一.obj不为空时 1.把方法号和对象放入RequestDTO
	 * 2.使用obj2Json把dto转为json,并且使用AESHelper.encrypt进行加密,把加密结果赋给jsonTem.
	 * 3.把jsonTem,version,uuid添加给ap 4.jsonTem = version + jsonTem + uuid +
	 * cipher; 
	 * 5.使用MD5Util.MD5Encode对jsonTem进行加密赋给s 6.把s添加给ap 7.把ap赋给hr,返回hr
	 * 
	 * @param requestDTO
	 * @returnFrameRequest
	 */
	public static FrameRequest packRequest(Object obj, String method, String cipher, String uuid, String version) {
		FrameRequest hr = new FrameRequest();
		RequestDTO dto = new RequestDTO();
		HashMap<String, Object> tem = new HashMap<String, Object>();
		if (obj == null) {
			tem.put("key", new Object());
		} else {
			tem.put("key", obj);
		}
		dto.setP(tem);
		dto.setM(method);
		jsonTem = AESHelper.encrypt(obj2Json(dto), "9408");
//		jsonTem = AESHelper.encrypt(obj2Json(dto), cipher);
		AjaxParams ap = new AjaxParams();
		ap.put("k", jsonTem);
		ap.put("v", version);
		ap.put("uuid", uuid);
		jsonTem = version + jsonTem + uuid + cipher;
		s = MD5Util.MD5Encode(jsonTem);
		ap.put("s", s);
		hr.setAp(ap);
		L.i("FConstants.getCipher()" + cipher + "\nap: " + ap.toString());
		return hr;
	}
	
	/**
	 * 新的封装服务端请求的方法  ShenHua 2015/06/26
	 * @param cmd 服务端判断用哪个bean的code，服务端同样也会放回给客户端cmd
	 * @param obj 包装要传参数的obj
	 * @param map 在一些需要单独传一个参数的时候，放在map中传
	 * @param uuid 保持登录的用户id
	 * @param version 版本
	 * @return
	 */
	public static FrameRequest packRequestMain(Byte cmd, Object obj, Map<String, String> map, String uuid, String version) {
		FrameRequest hr = new FrameRequest();
		CRequestBody<IdEntity> crb= new CRequestBody<IdEntity>();
		if(cmd != 0){
			crb.setCmd(cmd);
		}
		if(!FUtils.isStringNull(uuid)){
			crb.setUuid(uuid);
		}
		crb.setVersion(version);
		if(map != null){
			crb.setExtMap(map);
		}
		if(obj != null){
			if (obj instanceof List){
				crb.setLst((List<IdEntity>)obj);
			}else{
				crb.setObj((IdEntity)obj);
			}
		}
		/*jsonTem = AESHelper.encrypt(obj2Json(crb), "9048");
		AjaxParams ap = new AjaxParams();
		ap.put("k", jsonTem);
		s = MD5Util.MD5Encode(jsonTem);
		ap.put("s", s);
		hr.setAp(ap);*/
		try {
			jsonTem = AES256Encryption.encrypt(obj2Json(crb), "9048");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		AjaxParams ap = new AjaxParams();
		try {
			jsonTem = new String(new Base64().encodeBase64(jsonTem.getBytes("utf-8")));
			s = URLEncoder.encode(MD5Util.MD5Encode(jsonTem), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(!FUtils.isStringNull(jsonTem)){
			ap.put("k", jsonTem);
			if(!FUtils.isStringNull(s)){
				ap.put("s", s);
				hr.setAp(ap);
			}
		}
		L.i("ap: " + ap.toString());
		return hr;
	}

	public static String obj2Json(Object obj) {
		String tem = null;
		try {
			tem = GsonUtil.toJson(obj);		
			L.i(tem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tem;
	}
}
