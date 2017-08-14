import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import android.util.Base64;
import android.content.Context;
import android.content.res.Configuration;
import android.Manifest;
import android.R;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Common {
	public String getRRNValue(){
		String rrn="";
		try {
			rrn=getTime("yyyydddhh");
			rrn=rrn+getUniqueId();
		} catch (Exception e) {
			throw e;
		}
		return rrn.substring(rrn.length()-12);
	}
	public String getUniqueId(){
		String uuId = getUniqueID();
		uuId = uuId.replaceAll("[A-Za-z-]", "");
		return getRandomNumber(6, uuId);
	}
	public static String getTime (String myTime) {
		return new SimpleDateFormat (myTime).format (new Date ());
	}
	 public String getUniqueID(){
			//generate random UUIDs
	      UUID idOne = UUID.randomUUID();
		  String uid = idOne.toString();
		  StringBuffer sb = new StringBuffer();
		  String[] uidArr = uid.split("-");

		  for(int i =0; i < uidArr.length; i++)
		   {
				sb.append(uidArr[i]);
		   }

		  return sb.toString();
	}
	public  String getRandomNumber(int length, String charset) {
		Random rand = new Random(System.currentTimeMillis());
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int pos = rand.nextInt(charset.length());
			sb.append(charset.charAt(pos));
		}
		return sb.toString();
	}
	public String getUniqueRequestorId()
	{
		//"A123dFh4-".replaceAll("[A-Za-z-]", "")
		String UUID = getUniqueID();

		UUID = UUID.replaceAll("[A-Za-z-]", "");

		String randomId = "123"+getRandomPassword(8, UUID);
		return randomId;
	}
	 public static String getRandomPassword(int length, String charset) {
	        Random rand = new Random(System.currentTimeMillis());
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < length; i++) {
	            int pos = rand.nextInt(charset.length());
	            sb.append(charset.charAt(pos));
	        }
	        return sb.toString();
	    }
	 public String getBase64EncodedStr(String str){
			try {
				byte[] encodeValue = Base64.encode(str.getBytes(), Base64.DEFAULT);
				//byte[] message = str.getBytes("UTF-8");
				//String encoded = DatatypeConverter.printBase64Binary(message);
				return new String(encodeValue);
			} catch (Exception e) {
				// TODO: handle exception
				return e.toString();
			}
			//byte[] encodedBytes = Base64.getEncoder().encode("str".getBytes());

		}
	 public String getBase64DecodedStr(String EncodedStr){
			try {
				byte[] decodeValue = Base64.decode(EncodedStr, Base64.DEFAULT);
				 //byte[] decoded = DatatypeConverter.parseBase64Binary(EncodedStr);
				 return  new String(decodeValue);
			} catch (Exception e) {
				// TODO: handle exception
				return e.toString();
			}
		 	//byte[] decodedBytes = Base64.getDecoder().decode(encodedBytes);

	}
	 public String getFormatedAmt(String amt){
			double f = Double.parseDouble(amt);
			String floatStr=String.format("%.2f", f).replace(".","");
			int floatStrLen=floatStr.length();
			System.out.println("floatStrLen :"+ floatStrLen);
			if(floatStrLen<12){
				System.out.println("condition :"+ (floatStrLen<12));
				floatStr=StringUtils.leftPad(floatStr, 12, "0");
			}
			return floatStr;


	 }
	 public static Map fromJson(String jsonStr)  {
			System.out.println("Inside Map :"+jsonStr);
			    Map params = null;
			    try
			    {
			      ObjectMapper mapper = new ObjectMapper();

			      params = mapper.readValue(jsonStr, Map.class);
			    }
			    catch (Exception e) {
			    	params.put("Exception",e.toString());
			    	return params;
			    }
			    return params;
			  }
	 public String isAndroid() {
		    try {
		        Class.forName("android.app.Activity");
		        return "Android";
		    } catch(ClassNotFoundException e) {
		        return  System.getProperty("os.name");
		    }
		}
	 public String getSdkVersion() {
		    String release = android.os.Build.VERSION.RELEASE;
		    int sdkVersion = android.os.Build.VERSION.SDK_INT;
		    return sdkVersion + " (" + release +")";
		}
	 public  String isTablet(Context context) {
		    return (context.getResources().getConfiguration().screenLayout
		            & Configuration.SCREENLAYOUT_SIZE_MASK)
		            >= Configuration.SCREENLAYOUT_SIZE_LARGE ? "Tablet":"Mobile" ;
		}
	 
}
