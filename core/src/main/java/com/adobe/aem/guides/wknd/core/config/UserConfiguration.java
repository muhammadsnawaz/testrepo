package com.adobe.aem.guides.wknd.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;

@ObjectClassDefinition(name = "AEM63APP Declarative Service User Account Configuration", description = "Configure the user Details")
public @interface UserConfiguration {

	@AttributeDefinition(name = "user.name", description = "User Name")
	String getUserName() default "";

	@AttributeDefinition(name = "userpassword", description = "Password of the user account", type = AttributeType.PASSWORD)
	String getPassword() default "";
	
	@AttributeDefinition(name = "user.isAdminUser", description = "is it admin user or not?")
	boolean isAdminUser() default false;

	@AttributeDefinition(name = "user.environments", description = "Define the all environment where this user will be available")
	String[] getEnvironments() default {};	

	@AttributeDefinition(name = "user.validity", description ="Validity of user account", defaultValue = "10", required = true, type = AttributeType.INTEGER, min = "10")
	int getValidity() default 10;

	@AttributeDefinition(name = "memberof.name", description = "Member of the group", options = {
			@Option(label = "Approval", value = "approve"),
			@Option(label = "Editor", value = "editor"),
			@Option(label = "Workflow Editor", value = "workflow.editor"),
			@Option(label = "Administrator", value = "admin")})
	String getMemberOf() default "";
}
