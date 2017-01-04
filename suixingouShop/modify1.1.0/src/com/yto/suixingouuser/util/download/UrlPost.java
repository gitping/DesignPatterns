/**
 * 
 */
package com.yto.suixingouuser.util.download;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * post闁圭粯鍔掑锕傚礆閻楀牆鐦归悗瑙勬皑濞堟叅RL
 * @author 鐎殿喚濮甸弸锟� *
 */
public class UrlPost {	
	
	private String url;
	
	private Map<String,String> pramMap;
	
	private Map<String,String> header = new HashMap<String, String>();
	
	private String requestEncoding="UTF-8";
	
	private String recvEncoding="UTF-8";
	
	
	/**
	 * 闁兼儳鍢茶ぐ鍥箵閹邦亝鍞夐弶鈺傛煥濞叉牠宕愰懖鈺傜暠閻庢稒顨熼浣虹磽閺嶎偆鍨�
	 * @author 鐎殿喚濮甸弸锟�	 * @return
	 */
	public String getRecvEncoding() {
		return recvEncoding;
	}

	/**
	 * 閻犱焦鍎抽悾楣冨箵閹邦亝鍞夐弶鈺傛煥濞叉牠宕愰懖鈺傜暠閻庢稒顨熼浣虹磽閺嶎偆鍨�
	 * 濮掓稒顭堥缁樼▔缁☆枽F-8
	 * @author 鐎殿喚濮甸弸锟�	 * @param recvEncoding
	 */
	public void setRecvEncoding(String recvEncoding) {
		this.recvEncoding = recvEncoding;
	}

	/**
	 * 閻犱焦鍎抽悾楣冨箵閹邦亝鍞夐柣銊ュ瀶RL闁革附婢樺锟�	 * @author 鐎殿喚濮甸弸锟�	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 閻犱焦鍎抽悾楣冨箵閹邦亝鍞夐柣銊ュ瀵剟寮敓锟� * @author 鐎殿喚濮甸弸锟�	 * @param pramMap
	 */
	public void setPramMap(Map<String, String> pramMap) {
		this.pramMap = pramMap;
	}
	
	/**
	 * 闁兼儳鍢茶ぐ鍥箵閹邦亝鍞夐柣銊ュ閻⊙呯箔閿斿墽妞介柣顕嗘嫹
	 * @author 鐎殿喚濮甸弸锟�	 * @return
	 */
	public String getRequestEncoding() {
		return requestEncoding;
	}

	/**
	 * 閻犱焦鍎抽悾楣冨箵閹邦亝鍞夐柣銊ュ閻⊙呯箔閿斿墽妞介柣顕嗘嫹
	 * 濮掓稒顭堥缁樼▔缁☆枽F-8
	 * @author 鐎殿喚濮甸弸锟�	 * @return
	 */
	public void setRequestEncoding(String requestEncoding) {
		this.requestEncoding = requestEncoding;
	}

	/**
	 * 閺夆晜鐟﹀Σ鎼佸箵閹邦亝鍞塰eader
	 * @author three
	 * @date 2012-9-28
	 * 
	 * @param headerName
	 * @param headerValue
	 */
	public void setHeader(String headerName, String headerValue) {
		this.header.put(headerName, headerValue);
	}
	
	/**
	 * POST閻犲洭鏀遍惇浼村箵閹邦亝鍞�
	 * @author 鐎殿喚濮甸弸锟�	 * @return String
	 * @throws Exception
	 */
	public String doPost() throws Exception{
		if (this.url==null || this.url.equals("")) throw new Exception("閻犲洭鏀遍惇鐧燫L闂佹寧鐟ㄩ锟�");
        StringBuffer params = new StringBuffer();
        
        if (this.pramMap!=null){
        	Iterator<String> ipm= this.pramMap.keySet().iterator();
        	while (ipm.hasNext()){
        		String key=ipm.next();
        		String val=this.pramMap.get(key);
        		params.append(key)
        			  .append("=")
        			  .append(URLEncoder.encode(val,this.requestEncoding))
        			  .append("&");
        	}
        	
        	if (params.length()>0){
        		params=params.deleteCharAt(params.length()-1);
        	}
        }
        
        URL linkUrl = new URL(this.url);
        HttpURLConnection urlCon=null;
        String requestString="";
        
        try{
	        urlCon = (HttpURLConnection) linkUrl.openConnection();
	        urlCon.setRequestMethod("POST");
	        for(String headerName: header.keySet()) {
	        	urlCon.setRequestProperty(headerName, header.get(headerName));
	        	//urlCon.addRequestProperty(headerName, header.get(headerName));
	        }
	        
	        
	        urlCon.setDoOutput(true);
	        byte[] b = params.toString().getBytes();
	        urlCon.getOutputStream().write(b, 0, b.length);
	        urlCon.getOutputStream().flush();
	        urlCon.getOutputStream().close();
	        
	        InputStream in = urlCon.getInputStream();
	        BufferedReader rd = new BufferedReader(new InputStreamReader(in,this.recvEncoding));
	        
	        String revLine = rd.readLine();
	        StringBuffer revStr = new StringBuffer();
	        String crlf=System.getProperty("line.separator");
	        while (revLine != null)
	        {
	        	revStr.append(revLine);
	        	revStr.append(crlf);
	            revLine = rd.readLine();
	        }
	        
	        requestString = revStr.toString();
            rd.close();
            in.close();

        }
        catch(Exception e){
        	e.printStackTrace();
        	throw new Exception(e);
        }
        finally{
        	if (urlCon!=null)
        		urlCon.disconnect();
        }
        
        return requestString;
	}
	
	/**
	 * post from闁圭粯鍔掑锟�	 * @author gyq
	 * @date 2012-5-9
	 * @return
	 * @throws Exception
	 */
	public String doFromPost() throws Exception {
	    if (this.url==null || this.url.equals("")) throw new Exception("閻犲洭鏀遍惇鐧燫L闂佹寧鐟ㄩ锟�");
		
        StringBuffer params = new StringBuffer();
        if (this.pramMap!=null){
        	
        	Iterator<String> ipm= this.pramMap.keySet().iterator();
        	while (ipm.hasNext()){
        		String key=ipm.next();
        		String val=this.pramMap.get(key);
    			if("content".equals(key)){
    				params.append(key)
    					.append("=")
    					.append(URLEncoder.encode(val,this.requestEncoding))
    					.append("&");
    			}else{
	        		params.append(key)
	        			  .append("=")
	        			  .append(val)
	        			  .append("&");
    			}
        	}
        	
        	if (params.length()>0){
        		params=params.deleteCharAt(params.length()-1);
        	}
        }
        
        URL postUrl = new URL(this.url);
        // 闁瑰灚鎸哥槐鎴炴交閻愭潙澶�
        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
        //闁瑰灚鎸哥槐鎴犳嫚鐠囨彃鏅搁悘鐐靛仦閿熶粙鏁嶅畝鍕笡閻犱降鍊曞搴㈢▔缁＄lse
        connection.setDoOutput(true);                
        connection.setDoInput(true);
        // 閻犱礁澧介悿鍡欐嫚闁垮婀撮柡鍌滄嚀缁憋繝鏁嶅畝鍕笡閻犱降鍊撶拹鐑T
        connection.setRequestMethod("POST");
        // Post 閻犲洭鏀遍惇鐗堢▔瀹ュ牆鍘村ù锝堟硶閺併倗绱撻幘宕囨憼
        connection.setUseCaches(false);
        //闁哄嫷鍨遍崹姘跺川濡搫姣愰柡浣稿簻缁辨繃绂掗崨顒傜▕闁活枌鍔嬬花顒冦亹閹惧啿顤呴柛鎴ｅГ閺嗭拷
        connection.setInstanceFollowRedirects(true);
        // 闂佹澘绉堕悿鍡樻交閻愭潙澶嶉柣銊ュ殝ontent-type闁挎稑鐭傞崢銈囩磾椤旀槒绀媋pplication/x- www-form-urlencoded闁汇劌瀚崜浼村箑濠靛洦笑婵繐绲鹃弸鍐及閻ョlencoded缂傚倹鐗滈悥婊勬交閸モ晜鐣眆orm闁告瑥鍊归弳锟�        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        connection.connect();
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes(params.toString());
        out.flush();
        out.close(); 
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuffer revStr = new StringBuffer();
        while ((line = reader.readLine()) != null) {
        	revStr.append(line);
        }
          reader.close();
          connection.disconnect();
        return revStr.toString();
	}
	
}
