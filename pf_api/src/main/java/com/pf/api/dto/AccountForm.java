package com.pf.api.dto;

import java.util.ArrayList;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.pf.api.common.FieldMatch;

import lombok.Data;
/*
 * tip
 * boolean 은 @notnull 사용
 */
public class AccountForm{
	@Data
	@FieldMatch(first = "password", second = "confirmPassword", message = "{form.account.password.invalid_match}")
	public static class Mypage{
		
		private boolean changePassword;
		
		@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,15}$", message = "{form.account.username.invalid}") 
		private String name;
		
		private String originPassword;
		private String password;
		private String confirmPassword;
		
	}
	@Data
	@FieldMatch(first = "password", second = "confirmPassword", message = "{form.account.password.invalid_match}")
	public static class ConfirmRecoveryPassword{
		
		private String email;
		
		@Pattern(regexp="[a-zA-Z0-9-]{5,50}", message = "{form.device.authkey.required}")
		private String uid;
		
		//@Pattern(regexp="[a-zA-Z0-9!@#$%^&*+=-]{4,12}", message = "{form.account.password.invalid}")
		@Pattern(regexp="^(?=.*[A-Za-z])(?=.*[0-9])(?=\\S+$).*{4,12}$", message = "{form.account.password.invalid}")
		private String password;
		
		//@Pattern(regexp="[a-zA-Z0-9!@#$%^&*+=-]{4,12}", message = "{form.account.password.invalid}")
		@Pattern(regexp="^(?=.*[A-Za-z])(?=.*[0-9])(?=\\S+$).*{4,12}$", message = "{form.account.password.invalid}")
		private String confirmPassword;
	}
	@Data
	@FieldMatch(first = "password", second = "confirmPassword", message = "{form.account.password.invalid_match}")
	public static class Join2{

		private boolean checkEmailAuth;
		
		@NotBlank(message="{form.account.email.required}")
		@Email
		private String username;//email
		
		@Pattern(regexp="[a-zA-Z0-9]{4,10}", message = "{form.account.authkey.required}")
		private String authentication;
		
		//@Pattern(regexp="[a-zA-Z0-9!@#$%^&*+=-]{4,12}", message = "{form.account.password.invalid}")
		@Pattern(regexp="^(?=.*[A-Za-z])(?=.*[0-9])(?=\\S+$).*{4,12}$", message = "{form.account.password.invalid}")
		private String password;
		
		//@Pattern(regexp="[a-zA-Z0-9!@#$%^&*+=-]{4,12}", message = "{form.account.password.invalid}")
		@Pattern(regexp="^(?=.*[A-Za-z])(?=.*[0-9])(?=\\S+$).*{4,12}$", message = "{form.account.password.invalid}")
		private String confirmPassword;
		
		@NotBlank(message="{form.account.username.required}")
		@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,15}$", message = "{form.account.username.invalid}")  
		private String name;
		
		@DecimalMin(value="0", message="{form.account.usertype.required}") 
		private int userType;
		
		@DecimalMin(value="0", message="{form.account.company_type.required}") 
		private int companyType;
		
		private String companyName;
		
		private String companySn;
		
		@DecimalMin(value="0", message="{form.account.location.required}") 
		private int companyLocation;
		private String managerName;
		private String personalName;
	}
	@Data
	public static class Join3{
		@NotBlank(message="{form.account.email.required}")
		@Email
		private String username;//email
		
		//@Pattern(regexp="[a-zA-Z0-9!@#$%^&*+=-]{4,12}", message = "{form.account.password.invalid}")
		@Pattern(regexp="^(?=.*[A-Za-z])(?=.*[0-9])(?=\\S+$).*{4,12}$", message = "{form.account.password.invalid}")
		private String password;
		
		@NotBlank(message="{form.account.username.required}")
		private String name;
		
		@DecimalMin(value="0", message="{form.account.usertype.required}") 
		private int userType;
		
		private int companyType;
		private String companyName;
		private int companyLocation;
		private String managerName;
		private String personalName;
		
		private String companySn;
		
		@Pattern(regexp="[a-zA-Z0-9]{5,50}", message = "{form.device.sn.required}")
		private String sn;
		@Pattern(regexp="[a-zA-Z0-9]{5,50}", message = "{form.device.authkey.required}")
		private String authCode;
	}
	
	@Data
	public static class Login{
		
		@NotBlank(message="{form.login.id.required}")
		private String name;//email
		
		//@Pattern(regexp="[a-zA-Z0-9!@#$%^&*+=-]{4,12}", message = "{form.account.password.invalid}")
		////@Pattern(regexp="^(?=.*[A-Za-z])(?=.*[0-9])(?=\\S+$).*{4,12}$", message = "{form.account.password.invalid}")
		private String password;
		
		//@NotNull
		private Boolean isRemember;
	}
	
	@Data
	@FieldMatch(first = "password", second = "confirmPassword", message = "{form.account.password.invalid_match}")
	public static class Update{
	
		@DecimalMin(value="1",message="{error.unknow}") 
		private int id;

		@NotNull(message="패스워드변경")
		private boolean isUpdatePassword;
		
		@NotBlank(message="{form.account.username.required}")
		@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,15}$", message = "{form.account.username.invalid}")  
		private String name;

		private String password;
		
		private String confirmPassword;
		
		private String username;//email
		
		@NotNull(message="활성화")
		private boolean enabled;
		
		@NotNull(message="권한설정")
		private boolean ROLE_USER_CFR_DELETE;
		@NotNull(message="권한설정")
		private boolean ROLE_USER_CFR_DOWNLOAD;
		
		@NotNull(message="권한설정")
		private boolean ROLE_USER_DOC_CREATE;
		@NotNull(message="권한설정")
		private boolean ROLE_USER_DOC_DELETE;
		@NotNull(message="권한설정")
		private boolean ROLE_USER_DOC_DOWNLOAD;
		
		@NotNull(message="권한설정")
		private boolean ROLE_USER_DEVICE_CREATE;
		@NotNull(message="권한설정")
		private boolean ROLE_USER_DEVICE_DELETE;
		
		@NotNull(message="권한설정")
		private boolean ROLE_USER_ACCOUNT_CREATE;
		@NotNull(message="권한설정")
		private boolean ROLE_USER_ACCOUNT_DELETE;
		
		private ArrayList<String> selectedDeviceSn;
		private ArrayList<Integer> device_id;
	}
	
	@Data
	@FieldMatch(first = "password", second = "confirmPassword", message = "{form.account.password.invalid_match}")
	public static class Reg{
		
		private String isNew;
		
		@Email
		@NotBlank(message="{form.account.email.required}")
		private String username;//email
		
		//@Pattern(regexp="[a-zA-Z0-9!@#$%^&*+=-]{4,12}", message = "{form.account.password.invalid}")
		@Pattern(regexp="^(?=.*[A-Za-z])(?=.*[0-9])(?=\\S+$).*{4,12}$", message = "{form.account.password.invalid}")
		private String password;

		private String confirmPassword;
		
		@NotBlank(message="{form.account.username.required}")
		@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,15}$", message = "{form.account.username.invalid}")  
		private String name;
		
		@NotNull(message="활성화")
		private boolean enabled;
		
		@NotNull(message="권한설정")
		private boolean ROLE_USER_CFR_DELETE;
		@NotNull(message="권한설정")
		private boolean ROLE_USER_CFR_DOWNLOAD;
		
		@NotNull(message="권한설정")
		private boolean ROLE_USER_DOC_CREATE;
		@NotNull(message="권한설정")
		private boolean ROLE_USER_DOC_DELETE;
		@NotNull(message="권한설정")
		private boolean ROLE_USER_DOC_DOWNLOAD;
		
		@NotNull(message="권한설정")
		private boolean ROLE_USER_DEVICE_CREATE;
		@NotNull(message="권한설정")
		private boolean ROLE_USER_DEVICE_DELETE;
		
		@NotNull(message="권한설정")
		private boolean ROLE_USER_ACCOUNT_CREATE;
		@NotNull(message="권한설정")
		private boolean ROLE_USER_ACCOUNT_DELETE;
		
		
		private ArrayList<Integer> device_id;
	}
	
	@Data
	@FieldMatch(first = "password", second = "confirmPassword", message = "{form.account.password.invalid_match}")
	public static class adminReg{
		
		private String isNew;
		
		@Email
		@NotBlank(message="{form.account.email.required}")
		private String username;//email
		
		//@Pattern(regexp="[a-zA-Z0-9!@#$%^&*+=-]{4,12}", message = "{form.account.password.invalid}")
		@Pattern(regexp="^(?=.*[A-Za-z])(?=.*[0-9])(?=\\S+$).*{4,12}$", message = "{form.account.password.invalid}")
		private String password;

		private String confirmPassword;
		
		@NotBlank(message="{form.account.username.required}")
		@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,15}$", message = "{form.account.username.invalid}")  
		private String name;
		
		private String companyName;
		private String personalName;
		
		@NotNull(message="활성화")
		private boolean enabled;
		
		@NotNull(message="권한설정")
		private boolean ROLE_USER_CFR_DELETE;
		@NotNull(message="권한설정")
		private boolean ROLE_USER_CFR_DOWNLOAD;
		
		@NotNull(message="권한설정")
		private boolean ROLE_USER_DOC_CREATE;
		@NotNull(message="권한설정")
		private boolean ROLE_USER_DOC_DELETE;
		@NotNull(message="권한설정")
		private boolean ROLE_USER_DOC_DOWNLOAD;
		
		@NotNull(message="권한설정")
		private boolean ROLE_USER_DEVICE_CREATE;
		@NotNull(message="권한설정")
		private boolean ROLE_USER_DEVICE_DELETE;
		
		@NotNull(message="권한설정")
		private boolean ROLE_USER_ACCOUNT_CREATE;
		@NotNull(message="권한설정")
		private boolean ROLE_USER_ACCOUNT_DELETE;
	}
}
