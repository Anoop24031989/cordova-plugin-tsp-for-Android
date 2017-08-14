
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.os.AsyncTask;
import android.provider.Settings.Secure;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import org.apache.cordova.*;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import sample.test;

/**
 * This class echoes a string called from JavaScript.
 */
public class tsp extends CordovaPlugin {
	 private static void callbackSuccess(CallbackContext callbackContext, JSONObject jsonObject) {
		    if (jsonObject == null) // in case there are no data
		      jsonObject = new JSONObject();

		    PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, jsonObject);
		    pluginResult.setKeepCallback(true);
		    callbackContext.sendPluginResult(pluginResult);
		  }
		  
		  private static void callbackError(CallbackContext callbackContext, JSONObject jsonObject) {
		    if (jsonObject == null) // in case there are no data
		      jsonObject = new JSONObject();
		    
		    PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR, jsonObject);
		    pluginResult.setKeepCallback(true);
		    callbackContext.sendPluginResult(pluginResult);
		  }
		  
		  private static void callbackError(CallbackContext callbackContext, String str) {
		    PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR, str);
		    pluginResult.setKeepCallback(true);
		    callbackContext.sendPluginResult(pluginResult);
		  }
	
	 private void tsp_a30000(String message, CallbackContext callbackContext)throws Exception {
    	 JSONObject jsonIds = new JSONObject();
    	 Common common=new Common();
        if (message != null && message.length() > 0) {
        	try {
        		JSONObject obj = new JSONObject(message);
        		jsonIds.put("message", message);
        		String cardNo =  obj.getString("cardNo");
        		jsonIds.put("cardNo",cardNo);
        		String expiryDate = obj.getString("expiryDate"); 
        		jsonIds.put("expiryDate",expiryDate);
        		String cardLast4Digit=cardNo.substring(cardNo.length()-4,cardNo.length());
        		String rrn = common.getRRNValue();
        		String tokenRequestorID =common.getUniqueRequestorId();
        		char Active = 'Y';
        		String createdDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format (new Date ());
        		String tranTimeStamp =new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        		String transmissionDateTime =tranTimeStamp.substring(4);//"1115153447"//dateFormat(new Date(),"mmyyHHMMss");// 1115153447
        		String dateCapture = tranTimeStamp.substring(4,8);
        		String LocalTranDate = tranTimeStamp.substring(4,8);
        		String localTranTime = tranTimeStamp.substring(8);
        		StringBuilder sb = new StringBuilder();
        		sb.append("<?xml version='1.0' encoding='UTF-8' ?>" )
        		.append("<TSPService  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" )
        		.append(" xmlns='http://xml.netbeans.org/schema/TSPService'" )
        		.append(" xsi:schemaLocation='http://xml.netbeans.org/schema/TSPService" )
        		.append(" TSPReqType.xsd http://xml.netbeans.org/schema/TSPService" )
        		.append(" TSPHeaderType.xsd http://xml.netbeans.org/schema/TSPService" )
        		.append(" TSPResType.xsdhttp://xml.netbeans.org/schema/TSPServiceTSPService.xsd'>" )
        		.append("<Header>" )
        		.append("<Version>1.0</Version>" )
        		.append("<SrcApp>TSP</SrcApp>" )
        		.append("<TargetApp>DH</TargetApp>" )
        		.append("<SrcMsgId>00000001198469411500</SrcMsgId>" )
        		.append("<TranTimeStamp>").append(tranTimeStamp).append("</TranTimeStamp>" )
        		.append("</Header>" )
        		.append("<Body>" )
        		.append("<TSPReq>" )
        		.append("<PAN>").append(cardNo).append("</PAN>" )
        		.append("<ProcessingCode>A30000</ProcessingCode>" )
        		.append("<TransmissionDateTime>").append(transmissionDateTime).append("</TransmissionDateTime>" )
        		.append("<DateCapture>").append(dateCapture).append("</DateCapture>" )
        		.append("<MerchantType>5812</MerchantType>" ) 
        		.append("<POSEntryMode>010</POSEntryMode>" )
				.append("<AcquiringInstitutionIdentificationCode>10011001222</AcquiringInstitutionIdentificationCode>" )
				.append("<LocalTranTime>").append(localTranTime).append("</LocalTranTime>" )
				.append("<LocalTranDate>").append(LocalTranDate).append("</LocalTranDate>" )
				.append("<Track2Data>9566264329000000=121200000000000000</Track2Data>" )
				.append("<MsgType>0100</MsgType>" )
				.append("<SystemTraceAuditNumber>367103</SystemTraceAuditNumber>" )
				.append("<CardAcceptorIdentificationCode>123897654546576</CardAcceptorIdentificationCode>" )
				.append("<RRN>").append(rrn).append("</RRN>" )
				.append("<CardAcceptTermID>12345678</CardAcceptTermID>" )
				.append("<CardAcceptorNameLocation>UBI                   MUMBAI       MH 91</CardAcceptorNameLocation>" )
				.append("<ExpirationDate>").append(expiryDate).append("</ExpirationDate>" )
				.append("<PersonalIdentificationNumberData>99d7dd968ad469b4</PersonalIdentificationNumberData>" )
				.append("<ReceivingInstitutionIdentificationCode>3</ReceivingInstitutionIdentificationCode>" )
				.append("<RequestedTokenAssuranceLevel>00</RequestedTokenAssuranceLevel>" )
				.append("<TokenRequestorID>12322197357</TokenRequestorID>" ) 
				.append("<VersionNumber>456</VersionNumber>" )
				.append("<TokenLocation>03</TokenLocation>" )
				.append("<SharedKey>P1dYuet+h7p/5DABkPzhMiVlecGXTgKY</SharedKey>" )
				.append("</TSPReq>" )
				.append("</Body>" )
				.append("</TSPService>");
        		jsonIds.put("XMLStr",sb.toString());
				String encoded = common.getBase64EncodedStr(sb.toString()).replaceAll("\n", "");
				jsonIds.put("encoded",encoded);
				StringBuilder sbEncodedXMLStr = new StringBuilder();
				sbEncodedXMLStr.append("{\"timestamp\":\"").append(tranTimeStamp ).append("\",\"message\":\"").append(encoded ).append("\",\"source\":\"LX62=TEST_TOKEN_NODE=RQSTR_HTTP_SERVER_TEST_XML_ONE\",\"responseRequired\":\"1\",\"alternateDestArray\":[],\"alternateSrcArray\":[],\"uniqueId\":\"\",\"destination\":\"LX62=TEST_TOKEN_NODE=RQSTR_HTTP_SERVER_TEST_XML_ONE\"}");
				jsonIds.put("sbEncodedXMLStr",sbEncodedXMLStr.toString());
				String responseStr=new AsyncUtility().doInBackground("http://products.fss.co.in/TSP/transaction.htm",sbEncodedXMLStr.toString());
				jsonIds.put("responseStr",responseStr);
				if(!responseStr.equals("")){
					JSONObject resObj = new JSONObject(responseStr);
					jsonIds.put("messageObj",resObj.getString("message"));
	        		InputStream is = new ByteArrayInputStream(common.getBase64DecodedStr(resObj.getString("message")).getBytes("UTF-8"));
	    			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	    		    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	    		    Document document = docBuilder.parse(is);
	    		    jsonIds.put("decodedXMLStrRes",document.toString());
	    		    if(document.getElementsByTagName("ResponseCode").getLength()>0){
		    		    jsonIds.put("response_code",document.getElementsByTagName("ResponseCode").item(0).getTextContent());
		    		    if(document.getElementsByTagName("ResponseCode").item(0).getTextContent().toString().equals("00")){
		    		    	jsonIds.put("token",document.getElementsByTagName("Token").item(0).getTextContent());
		    		    }
	    		    }
				}
				
        		
        		callbackSuccess(callbackContext,jsonIds);
        	}catch(Exception e){
        		callbackError(callbackContext,e.toString());
        	}
        } else {
        	callbackError(callbackContext,"Expected one non-empty string argument.");
        }
    }
	 private void tsp_a40000(String message, CallbackContext callbackContext)throws Exception {
    	 JSONObject jsonIds = new JSONObject();
    	 Common common=new Common();
        if (message != null && message.length() > 0) {
        	try {
        		JSONObject obj = new JSONObject(message);
        		 Context context = this.cordova.getActivity().getApplicationContext();
        		 PackageManager packageManager = this.cordova.getActivity().getPackageManager();
        	     ApplicationInfo app = packageManager.getApplicationInfo(this.cordova.getActivity().getPackageName(), 0);
        	     TelephonyManager tMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        		jsonIds.put("message", message);
        		String tokenReferenceID =  obj.getString("provisionID");
        		jsonIds.put("tokenReferenceID",tokenReferenceID);
        		String tranAmt = common.getFormatedAmt(obj.getString("tranAmt")); 
        		jsonIds.put("tranAmt",tranAmt);
        		String rrn = common.getRRNValue();
        		String tokenRequestorID =common.getUniqueRequestorId();
        		String createdDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format (new Date ());
        		String tranTimeStamp =new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        		String transmissionDateTime =tranTimeStamp.substring(4);//"1115153447"//dateFormat(new Date(),"mmyyHHMMss");// 1115153447
        		String dateCapture = tranTimeStamp.substring(4,8);
        		String LocalTranDate = tranTimeStamp.substring(4,8);
        		String localTranTime = tranTimeStamp.substring(8);
        		StringBuilder sb = new StringBuilder();
        		sb.append("<?xml version='1.0' encoding='UTF-8' ?>" )
        		.append("<TSPService  xmlns='http://xml.netbeans.org/schema/TSPService'>" )
        		.append("<Header>" )
        		.append("<Version>1.0</Version>" )
        		.append("<SrcApp>TSP</SrcApp>" )
        		.append("<TargetApp>DH</TargetApp>" )
        		.append("<SrcMsgId>00000001198469411500</SrcMsgId>" )
        		.append("<TranTimeStamp>").append(tranTimeStamp).append("</TranTimeStamp>" )
        		.append("</Header>" )
        		.append("<Body>" )
        		.append("<TSPReq>" )
        		.append("<MsgType>0100</MsgType>")
        		.append("<ProcessingCode>A40000</ProcessingCode>" )
        		.append("<TransmissionDateTime>").append(transmissionDateTime).append("</TransmissionDateTime>" )
        		.append("<SystemTraceAuditNumber>607830</SystemTraceAuditNumber>" )
        		.append("<LocalTranTime>").append(localTranTime).append("</LocalTranTime>" )
				.append("<LocalTranDate>").append(LocalTranDate).append("</LocalTranDate>" )
        		.append("<DateCapture>").append(dateCapture).append("</DateCapture>" )
        		.append("<MerchantType>1003</MerchantType>" ) 
        		.append("<POSEntryMode>010</POSEntryMode>" )
				.append("<AcquiringInstitutionIdentificationCode>12398745621</AcquiringInstitutionIdentificationCode>" )
				.append("<RRN>").append(rrn).append("</RRN>" )
				.append("<CardAcceptTermID>12345678</CardAcceptTermID>" )
				.append("<CardAcceptorNameLocation>UBI                   MUMBAI       MH 91</CardAcceptorNameLocation>" )
				.append("<AcquiringInstitutionCountryCode>455</AcquiringInstitutionCountryCode>" )
				.append("<TranAmt>").append(tranAmt).append("</TranAmt>")
				.append("<TranCurrCode>356</TranCurrCode>") 
				.append("<CardAcceptorIdentificationCode>123897654546576</CardAcceptorIdentificationCode>" )
				.append("<VersionNumber>456</VersionNumber>" )
				.append("<TokenRequestorID>12322197357</TokenRequestorID>" ) 
				.append("<TokenReferenceID>").append(tokenReferenceID).append("</TokenReferenceID>" )
				.append("<SharedKey>P1dYuet+h7p/5DABkPzhMiVlecGXTgKY</SharedKey>" )
				.append("<DeviceId>").append(Secure.getString(context.getContentResolver(),Secure.ANDROID_ID)).append("</DeviceId>")
				.append("<DeviceOSType>").append(common.isAndroid()).append(", ").append(common.getSdkVersion()).append("</DeviceOSType>")
				.append("<AppVersion>").append((String)packageManager.getApplicationLabel(app)).append(", ").append(packageManager.getPackageInfo(this.cordova.getActivity().getPackageName(), 0).versionName).append("</AppVersion>")
				.append("<WalletProviderId>").append(obj.getString("app_Id")!=null?obj.getString("app_Id"):"").append("</WalletProviderId>")
				.append("<TranDeviceType>").append(common.isTablet(context)).append("</TranDeviceType>")
				.append("<CustomerId>").append(obj.getString("userID")!=null?obj.getString("userID"):"").append("</CustomerId>")
				.append("<IMEI>").append(tMgr.getDeviceId()!=null ? tMgr.getDeviceId():"").append("</IMEI>")
				.append("<MobileNo>").append(obj.getString("phoneNo")!=null ? obj.getString("phoneNo") : tMgr.getLine1Number()!=null ? tMgr.getLine1Number(): "").append("</MobileNo>")
				.append("<AppInstanceVersion>").append(obj.getString("appInstanceId")!=null ? obj.getString("appInstanceId") :"").append("</AppInstanceVersion>")
				.append("<VerificationType>").append(obj.getString("verification_Type")!=null?obj.getString("verification_Type"):"").append("</VerificationType>")
				.append("</TSPReq>" )
				.append("</Body>" )
				.append("</TSPService>");
        		jsonIds.put("XMLStr",sb.toString());
				String encoded = common.getBase64EncodedStr(sb.toString()).replaceAll("\n", "");
				jsonIds.put("encoded",encoded);
				StringBuilder sbEncodedXMLStr = new StringBuilder();
				sbEncodedXMLStr.append("{\"timestamp\":\"").append(tranTimeStamp ).append("\",\"message\":\"").append(encoded ).append("\",\"source\":\"LX62=TEST_TOKEN_NODE=RQSTR_HTTP_SERVER_TEST_XML_ONE\",\"responseRequired\":\"1\",\"alternateDestArray\":[],\"alternateSrcArray\":[],\"uniqueId\":\"\",\"destination\":\"LX62=TEST_TOKEN_NODE=RQSTR_HTTP_SERVER_TEST_XML_ONE\"}");
				jsonIds.put("sbEncodedXMLStr",sbEncodedXMLStr.toString());
				String responseStr=new AsyncUtility().doInBackground("http://products.fss.co.in/TSP/transaction.htm",sbEncodedXMLStr.toString());
				jsonIds.put("responseStr",responseStr);
				if(!responseStr.equals("")){
					JSONObject resObj = new JSONObject(responseStr);
					jsonIds.put("messageObj",resObj.getString("message"));
					jsonIds.put("decodedStrRes",common.getBase64DecodedStr(resObj.getString("message")));
	        		InputStream is = new ByteArrayInputStream(common.getBase64DecodedStr(resObj.getString("message")).getBytes("UTF-8"));
	    			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	    		    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	    		    Document document = docBuilder.parse(is);
	    		    if(document.getElementsByTagName("ResponseCode").getLength()>0){
		    		    jsonIds.put("response_code",document.getElementsByTagName("ResponseCode").item(0).getTextContent());
		    		    if(document.getElementsByTagName("ResponseCode").item(0).getTextContent().toString().equals("00")){
		    		    	jsonIds.put("tokenCryptogram",document.getElementsByTagName("TOKENCRYPTOGRAM").item(0).getTextContent());
		    		    }
	    		    }
				}
				
        		
        		callbackSuccess(callbackContext,jsonIds);
        	}catch(Exception e){
        		callbackError(callbackContext,e.toString());
        	}
        } else {
        	callbackError(callbackContext,"Expected one non-empty string argument.");
        }
    }
	 private void tsp_330000(String message, CallbackContext callbackContext)throws Exception {
    	 JSONObject jsonIds = new JSONObject();
    	 Common common=new Common();
        if (message != null && message.length() > 0) {
        	try {
        		JSONObject obj = new JSONObject(message);
        		 Context context = this.cordova.getActivity().getApplicationContext();
        		 PackageManager packageManager = this.cordova.getActivity().getPackageManager();
        	     ApplicationInfo app = packageManager.getApplicationInfo(this.cordova.getActivity().getPackageName(), 0);
        	     TelephonyManager tMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        		jsonIds.put("message", message);
        		String tokens =  obj.getString("tokens");
        		jsonIds.put("tokens",tokens);
        		String tokenCryptogram = obj.getString("tokenCryptogram"); 
        		jsonIds.put("tokenCryptogram",tokenCryptogram);
        		String tranAmt = common.getFormatedAmt(obj.getString("tranAmt"));
        		jsonIds.put("tranAmt",tranAmt);
        		String rrn = common.getRRNValue();
        		String tokenRequestorID =common.getUniqueRequestorId();
        		String createdDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format (new Date ());
        		String tranTimeStamp =new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        		String transmissionDateTime =tranTimeStamp.substring(4);//"1115153447"//dateFormat(new Date(),"mmyyHHMMss");// 1115153447
        		String dateCapture = tranTimeStamp.substring(4,8);
        		String LocalTranDate = tranTimeStamp.substring(4,8);
        		String localTranTime = tranTimeStamp.substring(8);
        		StringBuilder sb = new StringBuilder();
        		sb.append("<?xml version='1.0' encoding='UTF-8' ?>" )
        		.append("<TSPService  xmlns='http://xml.netbeans.org/schema/TSPService'>" )
        		.append("<Header>" )
        		.append("<Version>1.0</Version>" )
        		.append("<SrcApp>TSP</SrcApp>" )
        		.append("<TargetApp>DH</TargetApp>" )
        		.append("<SrcMsgId>00000001198469411500</SrcMsgId>" )
        		.append("<TranTimeStamp>").append(tranTimeStamp).append("</TranTimeStamp>" )
        		.append("</Header>" )
        		.append("<Body>" )
        		.append("<TSPReq>" )
        		.append("<MsgType>0100</MsgType>")
        		.append("<PAN>").append(tokens).append("</PAN>" )
        		.append("<ProcessingCode>330000</ProcessingCode>" )
        		.append("<TransmissionDateTime>").append(transmissionDateTime).append("</TransmissionDateTime>" )
        		.append("<SystemTraceAuditNumber>607830</SystemTraceAuditNumber>" )
        		.append("<LocalTranTime>").append(localTranTime).append("</LocalTranTime>" )
				.append("<LocalTranDate>").append(LocalTranDate).append("</LocalTranDate>" )
				.append("<ExpirationDate>1801</ExpirationDate>")
        		.append("<DateCapture>").append(dateCapture).append("</DateCapture>" )
        		.append("<MerchantType>1003</MerchantType>" ) 
        		.append("<POSEntryMode>010</POSEntryMode>" )
        		.append("<AcquiringInstitutionIdentificationCode>12398745621</AcquiringInstitutionIdentificationCode>" )
				.append("<RRN>").append(rrn).append("</RRN>" )
				.append("<CardAcceptTermID>12345679</CardAcceptTermID>" )
				.append("<CardAcceptorNameLocation>UBI                   MUMBAI       MH 91</CardAcceptorNameLocation>" )
        		.append("<AcquiringInstitutionCountryCode>455</AcquiringInstitutionCountryCode>" )
        		.append("<MessageReasonCode>00</MessageReasonCode>" )
        		.append("<AdviceReasonCode>0101102012030100601607017</AdviceReasonCode>" )
        		.append("<Terminaltype>1</Terminaltype>" )
        		.append("<TerminalEntryCapability>2</TerminalEntryCapability>" )
        		.append("<ChipConditionCode>0</ChipConditionCode>" )
        		.append("<ChipTransactionIndicator>6</ChipTransactionIndicator>" )
        		.append("<ChipAuthenticationReliabilityIndicator>7</ChipAuthenticationReliabilityIndicator>" )
        		.append("<TOKENCRYPTOGRAM>").append(tokenCryptogram).append("</TOKENCRYPTOGRAM>" )
        		.append("<NetworkIdentificationCode>0000</NetworkIdentificationCode>" )
        		.append("<NetworkManagementInformationCode>105</NetworkManagementInformationCode>" )
        		.append("<TranAmt>").append(tranAmt).append("</TranAmt>")
				.append("<TranCurrCode>356</TranCurrCode>") 
				.append("<CardAcceptorIdentificationCode>123897654546576</CardAcceptorIdentificationCode>" )
				.append("<VersionNumber>456</VersionNumber>" )
				.append("<TokenRequestorID>12322197357</TokenRequestorID>" )
				.append("<Cardholderdata>123</Cardholderdata>" )
				.append("<SharedKey>P1dYuet+h7p/5DABkPzhMiVlecGXTgKY</SharedKey>" )
				.append("<DeviceId>").append(Secure.getString(context.getContentResolver(),Secure.ANDROID_ID)).append("</DeviceId>")
				.append("<DeviceOSType>").append(common.isAndroid()).append(", ").append(common.getSdkVersion()).append("</DeviceOSType>")
				.append("<AppVersion>").append((String)packageManager.getApplicationLabel(app)).append(", ").append(packageManager.getPackageInfo(this.cordova.getActivity().getPackageName(), 0).versionName).append("</AppVersion>")
				.append("<WalletProviderId>").append(obj.getString("app_Id")!=null?obj.getString("app_Id"):"").append("</WalletProviderId>")
				.append("<TranDeviceType>").append(common.isTablet(context)).append("</TranDeviceType>")
				.append("<CustomerId>").append(obj.getString("userID")!=null?obj.getString("userID"):"").append("</CustomerId>")
				.append("<IMEI>").append(tMgr.getDeviceId()!=null ? tMgr.getDeviceId():"").append("</IMEI>")
				.append("<MobileNo>").append(obj.getString("phoneNo")!=null ? obj.getString("phoneNo") : tMgr.getLine1Number()!=null ? tMgr.getLine1Number(): "").append("</MobileNo>")
				.append("<AppInstanceVersion>").append(obj.getString("appInstanceId")!=null ? obj.getString("appInstanceId") :"").append("</AppInstanceVersion>")
				.append("<VerificationType>").append(obj.getString("verification_Type")!=null?obj.getString("verification_Type"):"").append("</VerificationType>")
				.append("</TSPReq>" )
				.append("</Body>" )
				.append("</TSPService>");
        		jsonIds.put("XMLStr",sb.toString());
				String encoded = common.getBase64EncodedStr(sb.toString()).replaceAll("\n", "");
				jsonIds.put("encoded",encoded);
				StringBuilder sbEncodedXMLStr = new StringBuilder();
				sbEncodedXMLStr.append("{\"timestamp\":\"").append(tranTimeStamp ).append("\",\"message\":\"").append(encoded ).append("\",\"source\":\"LX62=TEST_TOKEN_NODE=RQSTR_HTTP_SERVER_TEST_XML_ONE\",\"responseRequired\":\"1\",\"alternateDestArray\":[],\"alternateSrcArray\":[],\"uniqueId\":\"\",\"destination\":\"LX62=TEST_TOKEN_NODE=RQSTR_HTTP_SERVER_TEST_XML_ONE\"}");
				jsonIds.put("sbEncodedXMLStr",sbEncodedXMLStr.toString());
				String responseStr=new AsyncUtility().doInBackground("http://products.fss.co.in/TSP/transaction.htm",sbEncodedXMLStr.toString());
				jsonIds.put("responseStr",responseStr);
				if(!responseStr.equals("")){
					JSONObject resObj = new JSONObject(responseStr);
					jsonIds.put("messageObj",resObj.getString("message"));
					jsonIds.put("decodedStrRes",common.getBase64DecodedStr(resObj.getString("message")));
	        		InputStream is = new ByteArrayInputStream(common.getBase64DecodedStr(resObj.getString("message")).getBytes("UTF-8"));
	    			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	    		    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	    		    Document document = docBuilder.parse(is);
	    		    if(document.getElementsByTagName("ResponseCode").getLength()>0){
		    		    jsonIds.put("response_code",document.getElementsByTagName("ResponseCode").item(0).getTextContent());
		    		    if(document.getElementsByTagName("ResponseCode").item(0).getTextContent().toString().equals("00")){
		    		    	jsonIds.put("token",document.getElementsByTagName("TokenNumber").item(0).getTextContent());
		    		    	jsonIds.put("pan",document.getElementsByTagName("PAN").item(0).getTextContent());
		    		    	jsonIds.put("tranAmt",document.getElementsByTagName("TranAmt").item(0).getTextContent());
		    		    	
		    		    }
	    		    }
				}
				
        		
        		callbackSuccess(callbackContext,jsonIds);
        	}catch(Exception e){
        		callbackError(callbackContext,e.toString());
        	}
        } else {
        	callbackError(callbackContext,"Expected one non-empty string argument.");
        }
    }
	 private void tsp_M10000(String message, CallbackContext callbackContext)throws Exception {
    	 JSONObject jsonIds = new JSONObject();
    	 Common common=new Common();
        if (message != null && message.length() > 0) {
        	try {
        		JSONObject obj = new JSONObject(message);
        		 Context context = this.cordova.getActivity().getApplicationContext();
        		 PackageManager packageManager = this.cordova.getActivity().getPackageManager();
        	     ApplicationInfo app = packageManager.getApplicationInfo(this.cordova.getActivity().getPackageName(), 0);
        	     TelephonyManager tMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        		jsonIds.put("message", message);
        		String cardNo =  obj.getString("cardNo");
        		jsonIds.put("cardNo",cardNo);
        		String expiryDate = obj.getString("expiryDate"); 
        		jsonIds.put("expiryDate",expiryDate);
        		String rrn = common.getRRNValue();
        		String tokenRequestorID =common.getUniqueRequestorId();
        		char Active = 'Y';
        		String createdDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format (new Date ());
        		String tranTimeStamp =new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        		String transmissionDateTime =tranTimeStamp.substring(4);//"1115153447"//dateFormat(new Date(),"mmyyHHMMss");// 1115153447
        		String dateCapture = tranTimeStamp.substring(4,8);
        		String LocalTranDate = tranTimeStamp.substring(4,8);
        		String localTranTime = tranTimeStamp.substring(8);
        		StringBuilder sb = new StringBuilder();
        		sb.append("<?xml version='1.0' encoding='UTF-8' ?>" )
        		.append("<TSPService  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" )
        		.append(" xmlns='http://xml.netbeans.org/schema/TSPService'" )
        		.append(" xsi:schemaLocation='http://xml.netbeans.org/schema/TSPService" )
        		.append(" TSPReqType.xsd http://xml.netbeans.org/schema/TSPService" )
        		.append(" TSPHeaderType.xsd http://xml.netbeans.org/schema/TSPService" )
        		.append(" TSPResType.xsdhttp://xml.netbeans.org/schema/TSPServiceTSPService.xsd'>" )
        		.append("<Header>" )
        		.append("<Version>1.0</Version>" )
        		.append("<SrcApp>TSP</SrcApp>" )
        		.append("<TargetApp>DH</TargetApp>" )
        		.append("<SrcMsgId>00000001198469411500</SrcMsgId>" )
        		.append("<TranTimeStamp>").append(tranTimeStamp).append("</TranTimeStamp>" )
        		.append("</Header>" )
        		.append("<Body>" )
        		.append("<TSPReq>" )
        		.append("<PAN>").append(cardNo).append("</PAN>" )
        		.append("<ProcessingCode>A30000</ProcessingCode>" )
        		.append("<TransmissionDateTime>").append(transmissionDateTime).append("</TransmissionDateTime>" )
        		.append("<DateCapture>").append(dateCapture).append("</DateCapture>" )
        		.append("<MerchantType>5812</MerchantType>" ) 
        		.append("<POSEntryMode>010</POSEntryMode>" )
				.append("<AcquiringInstitutionIdentificationCode>10011001222</AcquiringInstitutionIdentificationCode>" )
				.append("<LocalTranTime>").append(localTranTime).append("</LocalTranTime>" )
				.append("<LocalTranDate>").append(LocalTranDate).append("</LocalTranDate>" )
				.append("<Track2Data>9566264329000000=121200000000000000</Track2Data>" )
				.append("<MsgType>0100</MsgType>" )
				.append("<SystemTraceAuditNumber>367103</SystemTraceAuditNumber>" )
				.append("<CardAcceptorIdentificationCode>123897654546576</CardAcceptorIdentificationCode>" )
				.append("<RRN>").append(rrn).append("</RRN>" )
				.append("<CardAcceptTermID>12345678</CardAcceptTermID>" )
				.append("<CardAcceptorNameLocation>UBI                   MUMBAI       MH 91</CardAcceptorNameLocation>" )
				.append("<ExpirationDate>").append(expiryDate).append("</ExpirationDate>" )
				.append("<PersonalIdentificationNumberData>99d7dd968ad469b4</PersonalIdentificationNumberData>" )
				.append("<ReceivingInstitutionIdentificationCode>3</ReceivingInstitutionIdentificationCode>" )
				.append("<RequestedTokenAssuranceLevel>00</RequestedTokenAssuranceLevel>" )
				.append("<TokenRequestorID>12322197357</TokenRequestorID>" ) 
				.append("<VersionNumber>456</VersionNumber>" )
				.append("<TokenLocation>03</TokenLocation>" )
				.append("<SharedKey>P1dYuet+h7p/5DABkPzhMiVlecGXTgKY</SharedKey>" )
				.append("<DeviceId>").append(Secure.getString(context.getContentResolver(),Secure.ANDROID_ID)).append("</DeviceId>")
				.append("<DeviceOSType>").append(common.isAndroid()).append(", ").append(common.getSdkVersion()).append("</DeviceOSType>")
				.append("<AppVersion>").append((String)packageManager.getApplicationLabel(app)).append(", ").append(packageManager.getPackageInfo(this.cordova.getActivity().getPackageName(), 0).versionName).append("</AppVersion>")
				.append("<WalletProviderId>").append(obj.getString("app_Id")!=null?obj.getString("app_Id"):"").append("</WalletProviderId>")
				.append("<TranDeviceType>").append(common.isTablet(context)).append("</TranDeviceType>")
				.append("<CustomerId>").append(obj.getString("userID")!=null?obj.getString("userID"):"").append("</CustomerId>")
				.append("<IMEI>").append(tMgr.getDeviceId()!=null ? tMgr.getDeviceId():"").append("</IMEI>")
				.append("<MobileNo>").append(obj.getString("phoneNo")!=null ? obj.getString("phoneNo") : tMgr.getLine1Number()!=null ? tMgr.getLine1Number(): "").append("</MobileNo>")
				.append("<AppInstanceVersion>").append(obj.getString("appInstanceId")!=null ? obj.getString("appInstanceId") :"").append("</AppInstanceVersion>")
				.append("<VerificationType>").append(obj.getString("verification_Type")!=null?obj.getString("verification_Type"):"").append("</VerificationType>")
				.append("</TSPReq>" )
				.append("</Body>" )
				.append("</TSPService>");
        		jsonIds.put("XMLStr",sb.toString());
				String encoded = common.getBase64EncodedStr(sb.toString()).replaceAll("\n", "");
				jsonIds.put("encoded",encoded);
				StringBuilder sbEncodedXMLStr = new StringBuilder();
				sbEncodedXMLStr.append("{\"timestamp\":\"").append(tranTimeStamp ).append("\",\"message\":\"").append(encoded ).append("\",\"source\":\"LX62=TEST_TOKEN_NODE=RQSTR_HTTP_SERVER_TEST_XML_ONE\",\"responseRequired\":\"1\",\"alternateDestArray\":[],\"alternateSrcArray\":[],\"uniqueId\":\"\",\"destination\":\"LX62=TEST_TOKEN_NODE=RQSTR_HTTP_SERVER_TEST_XML_ONE\"}");
				jsonIds.put("sbEncodedXMLStr1",sbEncodedXMLStr.toString());
				String responseStr=new AsyncUtility().doInBackground("http://products.fss.co.in/TSP/transaction.htm",sbEncodedXMLStr.toString());
				jsonIds.put("responseStr1",responseStr);
				if(!responseStr.equals("")){
					JSONObject resObj = new JSONObject(responseStr);
					jsonIds.put("messageObj",resObj.getString("message"));
					jsonIds.put("decodedStrRes",common.getBase64DecodedStr(resObj.getString("message")));
	        		InputStream is = new ByteArrayInputStream(common.getBase64DecodedStr(resObj.getString("message")).getBytes("UTF-8"));
	    			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	    		    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	    		    Document document = docBuilder.parse(is);
	    		    if(document.getElementsByTagName("ResponseCode").getLength()>0){
		    		    jsonIds.put("response_code",document.getElementsByTagName("ResponseCode").item(0).getTextContent());
		    		    if(document.getElementsByTagName("ResponseCode").item(0).getTextContent().toString().equals("00")){
		    		    	jsonIds.put("token",document.getElementsByTagName("Token").item(0).getTextContent());
		    		    	jsonIds.put("provisionID",document.getElementsByTagName("TokenReferenceID").item(0).getTextContent());
		    		    	jsonIds.put("enrolledID",document.getElementsByTagName("EnrollmentID").item(0).getTextContent());
		    		    }
	    		    }
				}
				
        		
        		callbackSuccess(callbackContext,jsonIds);
        	}catch(Exception e){
        		callbackError(callbackContext,e.toString());
        	}
        } else {
        	callbackError(callbackContext,"Expected one non-empty string argument.");
        }
    }
	 private void tsp_M20000(String message, CallbackContext callbackContext)throws Exception {
    	 JSONObject jsonIds = new JSONObject();
    	 Common common=new Common();
        if (message != null && message.length() > 0) {
        	try {
        		JSONObject obj = new JSONObject(message);
        		 Context context = this.cordova.getActivity().getApplicationContext();
        		 PackageManager packageManager = this.cordova.getActivity().getPackageManager();
        	     ApplicationInfo app = packageManager.getApplicationInfo(this.cordova.getActivity().getPackageName(), 0);
        	     TelephonyManager tMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        		jsonIds.put("message", message);
        		String enrollmentId =  obj.getString("enrollmentId");
        		jsonIds.put("enrollmentId",enrollmentId);
        		String expiryDate = obj.getString("expiryDate"); 
        		jsonIds.put("expiryDate",expiryDate);
        		String rrn = common.getRRNValue();
        		String tokenRequestorID =common.getUniqueRequestorId();
        		char Active = 'Y';
        		String createdDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format (new Date ());
        		String tranTimeStamp =new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        		String transmissionDateTime =tranTimeStamp.substring(4);//"1115153447"//dateFormat(new Date(),"mmyyHHMMss");// 1115153447
        		String dateCapture = tranTimeStamp.substring(4,8);
        		String LocalTranDate = tranTimeStamp.substring(4,8);
        		String localTranTime = tranTimeStamp.substring(8);
        		StringBuilder sb = new StringBuilder();
        		sb.append("<?xml version='1.0' encoding='UTF-8' ?>" )
        		.append("<TSPService  xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'" )
        		.append(" xmlns='http://xml.netbeans.org/schema/TSPService'" )
        		.append(" xsi:schemaLocation='http://xml.netbeans.org/schema/TSPService" )
        		.append(" TSPReqType.xsd http://xml.netbeans.org/schema/TSPService" )
        		.append(" TSPHeaderType.xsd http://xml.netbeans.org/schema/TSPService" )
        		.append(" TSPResType.xsdhttp://xml.netbeans.org/schema/TSPServiceTSPService.xsd'>" )
        		.append("<Header>" )
        		.append("<Version>1.0</Version>" )
        		.append("<SrcApp>TSP</SrcApp>" )
        		.append("<TargetApp>DH</TargetApp>" )
        		.append("<SrcMsgId>00000001198469411500</SrcMsgId>" )
        		.append("<TranTimeStamp>").append(tranTimeStamp).append("</TranTimeStamp>" )
        		.append("</Header>" )
        		.append("<Body>" )
        		.append("<TSPReq>" )
        		.append("<EnrollmentID>").append(enrollmentId).append("</EnrollmentID>" )
        		.append("<ProcessingCode>A20000</ProcessingCode>" )
        		.append("<TransmissionDateTime>").append(transmissionDateTime).append("</TransmissionDateTime>" )
        		.append("<DateCapture>").append(dateCapture).append("</DateCapture>" )
        		.append("<MerchantType>5812</MerchantType>" ) 
        		.append("<POSEntryMode>010</POSEntryMode>" )
				.append("<AcquiringInstitutionIdentificationCode>10011001222</AcquiringInstitutionIdentificationCode>" )
				.append("<LocalTranTime>").append(localTranTime).append("</LocalTranTime>" )
				.append("<LocalTranDate>").append(LocalTranDate).append("</LocalTranDate>" )
				.append("<Track2Data>9566264329000000=121200000000000000</Track2Data>" )
				.append("<MsgType>0100</MsgType>" )
				.append("<SystemTraceAuditNumber>367103</SystemTraceAuditNumber>" )
				.append("<CardAcceptorIdentificationCode>123897654546576</CardAcceptorIdentificationCode>" )
				.append("<RRN>").append(rrn).append("</RRN>" )
				.append("<CardAcceptTermID>12345678</CardAcceptTermID>" )
				.append("<CardAcceptorNameLocation>UBI                   MUMBAI       MH 91</CardAcceptorNameLocation>" )
				.append("<ExpirationDate>").append(expiryDate).append("</ExpirationDate>" )
				.append("<PersonalIdentificationNumberData>99d7dd968ad469b4</PersonalIdentificationNumberData>" )
				.append("<ReceivingInstitutionIdentificationCode>3</ReceivingInstitutionIdentificationCode>" )
				.append("<RequestedTokenAssuranceLevel>00</RequestedTokenAssuranceLevel>" )
				.append("<TokenRequestorID>12322197357</TokenRequestorID>" ) 
				.append("<VersionNumber>456</VersionNumber>" )
				.append("<TokenLocation>03</TokenLocation>" )
				.append("<SharedKey>P1dYuet+h7p/5DABkPzhMiVlecGXTgKY</SharedKey>" )
				.append("<DeviceId>").append(Secure.getString(context.getContentResolver(),Secure.ANDROID_ID)).append("</DeviceId>")
				.append("<DeviceOSType>").append(common.isAndroid()).append(", ").append(common.getSdkVersion()).append("</DeviceOSType>")
				.append("<AppVersion>").append((String)packageManager.getApplicationLabel(app)).append(", ").append(packageManager.getPackageInfo(this.cordova.getActivity().getPackageName(), 0).versionName).append("</AppVersion>")
				.append("<WalletProviderId>").append(obj.getString("app_Id")!=null?obj.getString("app_Id"):"").append("</WalletProviderId>")
				.append("<TranDeviceType>").append(common.isTablet(context)).append("</TranDeviceType>")
				.append("<CustomerId>").append(obj.getString("userID")!=null?obj.getString("userID"):"").append("</CustomerId>")
				.append("<IMEI>").append(tMgr.getDeviceId()!=null ? tMgr.getDeviceId():"").append("</IMEI>")
				.append("<MobileNo>").append(obj.getString("phoneNo")!=null ? obj.getString("phoneNo") : tMgr.getLine1Number()!=null ? tMgr.getLine1Number(): "").append("</MobileNo>")
				.append("<AppInstanceVersion>").append(obj.getString("appInstanceId")!=null ? obj.getString("appInstanceId") :"").append("</AppInstanceVersion>")
				.append("<VerificationType>").append(obj.getString("verification_Type")!=null?obj.getString("verification_Type"):"").append("</VerificationType>")
				.append("</TSPReq>" )
				.append("</Body>" )
				.append("</TSPService>");
        		jsonIds.put("XMLStr",sb.toString());
				String encoded = common.getBase64EncodedStr(sb.toString()).replaceAll("\n", "");
				jsonIds.put("encoded",encoded);
				StringBuilder sbEncodedXMLStr = new StringBuilder();
				sbEncodedXMLStr.append("{\"timestamp\":\"").append(tranTimeStamp ).append("\",\"message\":\"").append(encoded ).append("\",\"source\":\"LX62=TEST_TOKEN_NODE=RQSTR_HTTP_SERVER_TEST_XML_ONE\",\"responseRequired\":\"1\",\"alternateDestArray\":[],\"alternateSrcArray\":[],\"uniqueId\":\"\",\"destination\":\"LX62=TEST_TOKEN_NODE=RQSTR_HTTP_SERVER_TEST_XML_ONE\"}");
				jsonIds.put("sbEncodedXMLStr",sbEncodedXMLStr.toString());
				String responseStr=new AsyncUtility().doInBackground("http://products.fss.co.in/TSP/transaction.htm",sbEncodedXMLStr.toString());
				jsonIds.put("responseStr",responseStr);
				if(!responseStr.equals("")){
					JSONObject resObj = new JSONObject(responseStr);
					jsonIds.put("messageObj",resObj.getString("message"));
					jsonIds.put("decodedStrRes",common.getBase64DecodedStr(resObj.getString("message")));
	        		InputStream is = new ByteArrayInputStream(common.getBase64DecodedStr(resObj.getString("message")).getBytes("UTF-8"));
	    			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	    		    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	    		    Document document = docBuilder.parse(is);
	    		    if(document.getElementsByTagName("ResponseCode").getLength()>0){
		    		    jsonIds.put("response_code",document.getElementsByTagName("ResponseCode").item(0).getTextContent());
		    		    if(document.getElementsByTagName("ResponseCode").item(0).getTextContent().toString().equals("00")){
		    		    	jsonIds.put("token",document.getElementsByTagName("Token").item(0).getTextContent());
		    		    }
	    		    }
				}
				
        		
        		callbackSuccess(callbackContext,jsonIds);
        	}catch(Exception e){
        		callbackError(callbackContext,e.toString());
        	}
        } else {
        	callbackError(callbackContext,"Expected one non-empty string argument.");
        }
    }
	
	 @Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		try{
			if (action.equals("tsp_a30000")) {
		        String message = args.getString(0);
		       this.tsp_a30000(message, callbackContext);
		        return true;
		    }else if(action.equals("tsp_M10000")){
	    		String message = args.getString(0);
		       this.tsp_M10000(message, callbackContext);
		        return true;
		    }else if(action.equals("tsp_M20000")){
	    		String message = args.getString(0);
		       this.tsp_M20000(message, callbackContext);
		        return true;
		    }else if(action.equals("tsp_a40000")){
	    		String message = args.getString(0);
		       this.tsp_a40000(message, callbackContext);
		        return true;
		    }else if(action.equals("tsp_330000")){
	    		String message = args.getString(0);
			       this.tsp_330000(message, callbackContext);
			        return true;
			    }
		}catch(Exception e){
			return false;
		}
		return false;
	    
	}
	 
}
