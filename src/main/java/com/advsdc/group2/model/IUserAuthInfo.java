package com.advsdc.group2.model;

import java.sql.Timestamp;

public interface IUserAuthInfo {

	public void setOneTimePassword(int oneTimePassword);

	public void setOtpTime(String string);

	public void setPassword(String password);

	public void setUser_id(String user_id);
}