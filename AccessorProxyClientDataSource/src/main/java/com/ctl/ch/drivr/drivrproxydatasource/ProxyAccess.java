package com.ctl.ch.drivr.drivrproxydatasource;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXB;

import org.apache.log4j.Logger;

import com.ctl.ch.common.coreutilities.HttpHelper;
import com.ctl.ch.common.coreutilities.PropertyAccess;
import com.ctl.ch.drivr.accessorproxymessagedefinition.AccessorProxyRequest;
import com.ctl.ch.drivr.accessorproxymessagedefinition.AccessorProxyResponse;
import com.ctl.ch.drivr.dataaccess.IDataSource;


public class ProxyAccess {
	private static final Logger log = Logger.getLogger(ProxyAccess.class); 
	public static final String DRIVR_PROXY_URL_PROPERTY = "com.ctl.ch.drivr.drivrproxydatasource.endpoint";
	
	private static final PropertyAccess mProxyProperties = new PropertyAccess("proxy");

	public AccessorProxyResponse postRequest(AccessorProxyRequest request) {
		String endpoint = mProxyProperties.getProperty(DRIVR_PROXY_URL_PROPERTY);
		log.info(String.format("Sending request for Source: %s Action: %s to proxy service at %s", request.getSourceName(), request.getActionName(), endpoint ));
		HttpHelper http = new HttpHelper(endpoint); 
		http.addHeader("Content-Type", MediaType.APPLICATION_XML);
		StringWriter sw = new StringWriter();
		JAXB.marshal(request, sw);
		String response = null;
		try {
			response = http.post(sw.toString());
		} catch (IOException e) {
			log.error(e);
			return null;
		}
		StringReader reader = new StringReader(response);
		return JAXB.unmarshal(reader, AccessorProxyResponse.class);
	}

}
