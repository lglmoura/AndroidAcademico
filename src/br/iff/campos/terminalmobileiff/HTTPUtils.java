/**
 * 
 */
package br.iff.campos.terminalmobileiff;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import android.net.Uri;

/**
 * @author lglmoura
 * 
 */
public class HTTPUtils {

	public static String acessar(String modelo) {
		try {

			String urlIFF = "http://192.168.0.100:3000/";
			//String urlIFF = "http://10.10.4.234:3000/";
			String url1 = Uri.parse(urlIFF + modelo).toString();

			URL url = new URL(url1);
			URLConnection conn = url.openConnection();
			InputStream is = conn.getInputStream();
			Scanner scanner = new Scanner(is);
			String conteudo = scanner.useDelimiter("\\A").next();
			scanner.close();

			return conteudo;
		} catch (Exception e) {
			return null;
		}
	}

	

}
