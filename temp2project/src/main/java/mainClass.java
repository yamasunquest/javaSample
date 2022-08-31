import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import okhttp3.*;
import top.sunquest.SSLSocketClient;
import top.sunquest.getHttpClient;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class mainClass {
	public static void main(String args []) throws ParseException, UnsupportedEncodingException {

		MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append("http://192.168.11.200/artemis/api/v1/oauth/token");
		String sbPath = "/artemis/api/v1/oauth/token";
		RequestBody requestBody = RequestBody.create(mediaType,"");
		Date date  = new Date();

		String contentMd5 = Base64.encode(SecureUtil.md5(requestBody.toString()));
		System.out.println(requestBody.toString());
		String XCaKey = "24338637";
		String appSecret = "205TajuFyE8NnFapI2By";

		String httpHeaders = "POST" + "\n"
				+ "*/*" + "\n"
				+ contentMd5 + "\n"
				+ "application/json" + "\n"
				+ date.toString() + "\n";

		String customHeaders = "x-ca-key" + ":" + XCaKey + "\n";

		byte[] key = appSecret.getBytes();
		HMac mac = new HMac(HmacAlgorithm.HmacSHA256, key);
		String macHex1 = mac.digestHex(httpHeaders  + customHeaders + sbPath);
		String XCaSignature = Base64.encode(macHex1);

		final OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.readTimeout(60, TimeUnit.SECONDS)
				.connectTimeout(60, TimeUnit.SECONDS)
				.sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getX509TrustManager())
				.hostnameVerifier(SSLSocketClient.getHostnameVerifier())
				.build();

		final Request request = new Request.Builder()
				.url(sbUrl.toString())
				.post(requestBody)
				.addHeader("Accept", "*/*")
				.addHeader("Content-MD5",contentMd5)
				.addHeader("Content-Type","application/json")
				.addHeader("Date",date.toString())
				.addHeader("X-Ca-Key",XCaKey)
				.addHeader("X-Ca-Signature",XCaSignature)
				.addHeader("X-Ca-Signature-Headers","x-ca-key")
				.build();

		System.out.println(contentMd5);
		System.out.println(date.toString());
		System.out.println(XCaKey);
		System.out.println(XCaSignature);
		System.out.println(httpHeaders  + customHeaders + sbPath);
		System.out.println("");

		okHttpClient.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				System.out.println(e.toString());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
//				Headers headers = response.headers();
//				for (int i = 0; i < headers.size(); i++) {
//					System.out.println(headers.name(i) + ":" + headers.value(i));
//				}

				System.out.println("onResponse: " + response.body().string());
			}
		});


//		System.out.println("asd");
//
//		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Long time=new Long(445555555);
//		String d = format.format(time);
//		Date date=format.parse(d);
//		System.out.println("Format To String(Date):"+d);
//		System.out.println("Format To Date:"+date);
//
//		Date now = new Date();
//		System.out.print("Format To times:"+now.getTime());

	}
}