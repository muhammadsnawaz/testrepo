package com.adobe.aem.guides.wknd.core.config;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;

@Component(service=UserAccountDetail.class,immediate=true)
@Designate(ocd = UserConfiguration.class)
public class UserAccountDetail {

	private String userName;

	private String password;

	private Boolean isAdminUser;

	private String memberOf;

	private int validity;

	private String[] environments;

	@Activate @Modified
	protected void activate(final UserConfiguration userConfiguration) {
		userName = userConfiguration.getUserName();
		password = userConfiguration.getPassword();
		isAdminUser = userConfiguration.isAdminUser();
		memberOf = userConfiguration.getMemberOf();
		validity = userConfiguration.getValidity();
		environments = userConfiguration.getEnvironments();
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the isAdminUser
	 */
	public Boolean getIsAdminUser() {
		return isAdminUser;
	}

	/**
	 * @return the memberOf
	 */
	public String getMemberOf() {
		return memberOf;
	}

	/**
	 * @return the validity
	 */
	public int getValidity() {
		return validity;
	}

	/**
	 * @return the environments
	 */
	public String[] getEnvironments() {
		return environments;
	}
}