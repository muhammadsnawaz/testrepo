package com.adobe.aem.guides.wknd.core.services;

import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(name="custom service",service=CustomService.class,immediate = true)
public class CustomService {
	public String almari(){
		String almariReturn = "variable from almari method";
		return almariReturn;
	}

}

