package com.ctl.ch.drivr.drivrproxydatasource;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.ctl.ch.common.coreutilities.FormattedString;
import com.ctl.ch.common.coreutilities.ReturnStatus;
import com.ctl.ch.common.legacylogger.LegacyLog;
import com.ctl.ch.drivr.accessorproxymessagedefinition.AccessorProxyRequest;
import com.ctl.ch.drivr.accessorproxymessagedefinition.AccessorProxyRequest.InputParameterList;
import com.ctl.ch.drivr.accessorproxymessagedefinition.AccessorProxyRequest.InputParameterList.InputParameters;
import com.ctl.ch.drivr.accessorproxymessagedefinition.AccessorProxyResponse;
import com.ctl.ch.drivr.accessorproxymessagedefinition.AccessorProxyResponse.OutputParameterList;
import com.ctl.ch.drivr.accessorproxymessagedefinition.AccessorProxyResponse.OutputParameterList.OutputParameters;
import com.ctl.ch.drivr.dataaccess.DataAccessException;
import com.ctl.ch.drivr.dataaccess.IDataSource;

public class ProxyDataSource implements IDataSource {

	private static final Logger log = Logger.getLogger(ProxyDataSource.class);

	/* (non-Javadoc)
	 * @see com.ctl.ch.drivr.dataaccess.IDataSource#performAction(java.lang.String, java.lang.String, java.util.HashMap, com.ctl.ch.common.legacylogger.LegacyLog, java.lang.String)
	 */
	public HashMap performAction(String pSourceName, String pActionName,
			HashMap pInputParameters, LegacyLog pLogger, String pVersion)
					throws DataAccessException {

		AccessorProxyRequest request = new AccessorProxyRequest();
		request.setActionName(pActionName);
		request.setSourceName(pSourceName);
		InputParameterList input = new InputParameterList();
		request.setInputParameterList(input);
		for (Object key: pInputParameters.keySet()) {
			InputParameters parameter = new InputParameters();
			parameter.setKey((String) key);
			parameter.setValue((String) pInputParameters.get(key));
			input.getInputParameters().add(parameter);
		}
		HashMap<String, String> output = new HashMap<String, String>();		
		ProxyAccess access = new ProxyAccess();
		AccessorProxyResponse response = access.postRequest(request);
		if (response != null) {
			OutputParameterList outputParameters = response.getOutputParameterList();

			for (OutputParameters outputParameter : outputParameters.getOutputParameters()) {
				output.put(outputParameter.getKey(), outputParameter.getValue());
			}

		} else {
			output.put(ReturnStatus.RETURN_STATUS_KEY, ReturnStatus.STATUS_FAILURE);
		}
		log.info(String.format("Output for Source: %s Action: %s is %s", pSourceName, pActionName, FormattedString.getSingleLine(output)));
		return output;
	}

}
