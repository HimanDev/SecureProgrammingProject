package com.uta.sp.helper;

import java.util.Arrays;
import java.util.List;

public class Constants {
	
	public static final String SP_USERNAME="SP_USERNAME";
	public static final String SP_USERTYPE="SP_USERTYPE";
	public static final String SP_USERID="SP_USERID";
	public static final Object SP_USERTYPE_PROFESSOR = "P";
	public static final Object SP_USERTYPE_STUDENT = "S";
	
	public static final List<String> ALLOWED_URLS=Arrays.asList("/","/index.jsp","/verify","/error","/error.html");

}
