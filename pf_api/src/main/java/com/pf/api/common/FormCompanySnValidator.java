package com.pf.api.common;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.pf.api.dto.AccountForm;
import com.pf.api.service.AccountService;

@Component
public class FormCompanySnValidator implements Validator{
	@Autowired
	private AccountService accountService;
	
    @Override
    public boolean supports(Class<?> form) {
        return AccountForm.Join2.class.isAssignableFrom(form);
    }

    @Override
    public void validate(Object target, Errors errors) {
    	AccountForm.Join2 accountForm = (AccountForm.Join2) target;
    	if(accountForm.getCompanySn().replaceAll("[^0-9]","").length()!=10 || !Pattern.matches("[0-9-]{10,12}", accountForm.getCompanySn()))
    		errors.rejectValue("companySn","form.account.companysn.invalid");	
    	
    	else if(accountService.findByConpanySn(accountForm.getCompanySn().replaceAll("[^0-9]",""))!=null) 
    		errors.rejectValue("companySn","form.account.companysn.duplicate");	
    }
}
