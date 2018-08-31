package test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.*;

import org.junit.Test;

public class MeteoFranceApiRestTest {

	public static final String METEOFRANCE_API_SERVER_URL = "https://plateforme.api-agro.fr/service/sorties-de-modeles-meteofrance";
	private final Moshi moshi = new Moshi.Builder().build();
	private final JsonAdapter<Root> rootJsonAdapter = moshi.adapter(Root.class);

	@Test
	public void testGetAromeRecords() {
		try {
			//Pour éviter la problématique du certificat SSL
			trustAll();
			//On génère l'URL du point d'entrée AROME de l'API Météo France
			StringBuilder urlBuilder = new StringBuilder();
			urlBuilder.append(METEOFRANCE_API_SERVER_URL).append("/arome?")
				//En passant en paramètre les coordonnées du point GPS dont on veut la météo
				.append("lat=48.85839179087659")
				.append("&long=2.33861966446477");
			System.out.println("URL:");
			System.out.println(urlBuilder.toString());
			//On récupère le contenu du point d'entrée AROME de l'API Météo France
			String jsonData = retrieve(urlBuilder.toString());
			System.out.println("Retrieved data:");			
			System.out.println(jsonData);			
			//On parse le contenu et on le traduit en une liste d'instances de la classe métier idoine
			Root root = rootJsonAdapter.fromJson(jsonData);
			//Enfin on opère les tests unitaires correspondants
			assertThat(root.nhits, greaterThan(0));
			assertThat(root.parameters, notNullValue());
			Parameters parameters = root.parameters;
			assertThat(parameters.dataset, notNullValue());
			String datasets[] = parameters.dataset;
			assertThat(datasets.length, greaterThan(0));
			String dataset = parameters.dataset[0];
			assertThat(dataset.length(), greaterThan(0));
			assertThat(dataset, is("arome-0025-sp1_sp2"));
			assertThat(parameters.timezone, is("UTC"));
			assertThat(parameters.rows, is(200));
			assertThat(parameters.format, is("json"));
			/*
			assertThat(parameters.geofilter_distance, notNullValue());
			String latLongDist[] = parameters.geofilter_distance;
			assertThat(latLongDist.length, is(1));
			assertThat(latLongDist[0], is("48.85,2.35,10"));
			*/
			Record records[] = root.records;
			assertThat(records, notNullValue());
			assertThat(records.length, greaterThan(0));
			Record record = records[0];
			assertThat(record.datasetid, notNullValue());
			assertThat(record.datasetid, is(dataset));
			assertThat(record.recordid, notNullValue());
			assertThat(record.recordid, is("arome_0025_sp1_sp2_sp3_lastgrib2-7835796"));
			//Récupération de la date du jour au bon format
			Date aujourdhui = new Date();
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
			assertThat(record.record_timestamp, notNullValue());
			assertThat(record.record_timestamp, is(formater.format(aujourdhui)+"T00:00:00+00:00"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetArpegeRecords() {
		try {
			//Pour éviter la problématique du certificat SSL
			trustAll();
			//On génère l'URL du point d'entrée AROME de l'API Météo France
			StringBuilder urlBuilder = new StringBuilder();
			urlBuilder.append(METEOFRANCE_API_SERVER_URL).append("/arpege?")
				//En passant en paramètre les coordonnées du point GPS dont on veut la météo
				.append("lat=48.85839179087659")
				.append("&long=2.33861966446477");
			System.out.println("URL:");
			System.out.println(urlBuilder.toString());
			//On récupère le contenu du point d'entrée AROME de l'API Météo France
			String jsonData = retrieve(urlBuilder.toString());
			System.out.println("Retrieved data:");			
			System.out.println(jsonData);			
			//On parse le contenu et on le traduit en une liste d'instances de la classe métier idoine
			Root root = rootJsonAdapter.fromJson(jsonData);
			//Enfin on opère les tests unitaires correspondants
			assertThat(root.nhits, greaterThan(0));
			assertThat(root.parameters, notNullValue());
			Parameters parameters = root.parameters;
			assertThat(parameters.dataset, notNullValue());
			String datasets[] = parameters.dataset;
			assertThat(datasets.length, greaterThan(0));
			String dataset = parameters.dataset[0];
			assertThat(dataset.length(), greaterThan(0));
			assertThat(dataset, is("arpege-05-sp1_sp2"));
			assertThat(parameters.timezone, is("UTC"));
			assertThat(parameters.rows, is(200));
			assertThat(parameters.format, is("json"));
			/*
			assertThat(parameters.geofilter_distance, notNullValue());
			String latLongDist[] = parameters.geofilter_distance;
			assertThat(latLongDist.length, is(1));
			assertThat(latLongDist[0], is("48.85,2.35,10"));
			*/
			Record records[] = root.records;
			assertThat(records, notNullValue());
			assertThat(records.length, greaterThan(0));
			Record record = records[0];
			assertThat(record.datasetid, notNullValue());
			assertThat(record.datasetid, is(dataset));
			assertThat(record.recordid, notNullValue());
			assertThat(record.recordid, is("arpege_05_sp1_sp2_lastgrib20-2138405"));
			assertThat(record.record_timestamp, notNullValue());
			//Récupération de la date du jour au bon format
			Date aujourdhui = new Date();
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
			assertThat(record.record_timestamp, is(formater.format(aujourdhui)+"T00:00:00+00:00"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Internal helper for running http request
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws KeyManagementException 
	 */
	private void trustAll() throws IOException, NoSuchAlgorithmException, KeyManagementException {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
			public void checkClientTrusted(X509Certificate[] certs, String authType) { }
			public void checkServerTrusted(X509Certificate[] certs, String authType) { }

		} };
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) { return true; }
		};
		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	}

	/**
	 * Internal helper for running http request
	 * @param url
	 * @return
	 * @throws IOException
	 */
	private String retrieve(String query) throws IOException {
		URL url = new URL(query);
		URLConnection con = url.openConnection();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String line = null;
		StringBuilder content = new StringBuilder();
		while ((line = bufferedReader.readLine()) != null) {
			if (content.length() > 0) {
				content.append("\n");
			}
			content.append(line);
		}
		bufferedReader.close();
		return content.toString();
	}

	/**
	 * Internal class: adapter for "fields" field
	 */
	static public class AromeFields {
	    String surface_net_thermal_radiation;
	    String wind_speed;
	    String dist;
	    String downward_short_wave_radiation_flux;
	    String surface_net_solar_radiation;
	    String timestamp;
	    String surface_latent_heat_flux;
	    String total_water_precipitation;
	    String relative_humidity;
	    String surface_sensible_heat_flux;
	    String forecast;
	}

	/**
	 * Internal class: adapter for "geometry" field
	 */
	static public class Geometry {
		String type;
	}

	/**
	 * Internal class: adapter for "record" item
	 */
	static public class Record {
		String datasetid;
		String recordid;
		AromeFields fields;
		Geometry geometry;
		String record_timestamp;
	}

	/**
	 * Internal class: adapter for "parameters" item
	 */
	static public class Parameters {
		String[] dataset;
		String timezone;
		int rows;
		String format;
		String[] geofilter_distance;
	}

	/**
	 * Internal class: adapter for root fields
	 */
	static public class Root {
		int nhits;
		Parameters parameters;
		Record[] records;
	}

}
