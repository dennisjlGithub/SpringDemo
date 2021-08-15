package com.test.util.sessionForm;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class TmAction {
		
	/**
	 * Save all the request fields to the form bean. The parameter name must be consistent with the form field name.
	 * Only String, Integer(int) and Long(long) types are supported.
	 * Date format or String[] Array(like check box), please handle it manually.
	 * @param request
	 * @param form
	 */
	public void saveDeclaredFieldsToForm(HashMap<String, String> requestParams, Object form) {
		Field[] fields = form.getClass().getDeclaredFields();
        
		String paramName, paramValue;
		for (Map.Entry<String, String> entry : requestParams.entrySet()) {
			paramName = entry.getKey();
			paramValue = entry.getValue();
			logGetParams(entry.getKey(), entry.getValue());
			
			String[] paramNames = paramName.split("\\.");
			if (paramNames.length == 1) {
				// Bypass
			} else if (paramNames.length == 2) {// Set form.value
				setFieldValue(paramNames[1], paramValue, form);
			} else if (paramNames.length == 3) {// Set form.VO.value
				for (Field field : fields) {
					if (paramNames[1].equals(field.getName())) { // find form.VO object
						try {
							field.setAccessible(true);
							Object vo = field.get(form);
							setFieldValue(paramNames[2], paramValue, vo);
							break;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	private void setFieldValue(String name, String value, Object form) {
		Field[] fields = form.getClass().getDeclaredFields();
		try {
            for (Field field : fields) {
				if (name.equals(field.getName())) {
					field.setAccessible(true);
					String typeName = field.getType().getTypeName();
					if (typeName.equals("java.lang.Integer") || typeName.equals("int")) {
						logSetValue(name, value);
						field.set(form, Integer.valueOf(value));
					} else if (typeName.equals("java.lang.Long") || typeName.equals("long")) {
						logSetValue(name, value);
						field.set(form, Long.valueOf(value));
					} else if (typeName.equals("java.lang.String")) {
						logSetValue(name, value);
						field.set(form, value);
					} else {
						System.out.println("Error :: Field type is not supported: " + typeName);
					}
					break;
				}
			}
        } catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void logGetParams(String paramName, String paramValue) {
		System.out.println("<<<<< paramName = '" + paramName + "', paramValue = '" + paramValue + "'");
	}
	
	private void logSetValue(String paramName, String paramValue) {
		System.out.println(">>>>> Set the Value: paramName = '" + paramName + "', paramValue = '" + paramValue + "'");
	}
	
	public static void main(String[] args) {
		HashMap<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("blockName", "Block Name");
		requestParams.put("tmForm.blockSize", "1000");
		requestParams.put("tmForm.tmVO.userName", "User Name");
		requestParams.put("tmForm.tmVO.userAge", "30");
		
		TmForm tmForm = new TmForm();
		tmForm.setBlockName("initiate");
		tmForm.setBlockSize(0);
		TmVO tmVO = new TmVO();
		tmVO.setUserName("initiate");
		tmVO.setUserAge(0);
		tmForm.setTmVO(tmVO);
		
		TmAction tmAction = new TmAction();
		tmAction.saveDeclaredFieldsToForm(requestParams, tmForm);
		System.out.println(tmForm.getBlockName() + "| " +  tmForm.getBlockSize());
		System.out.println(tmForm.getTmVO().getUserName() + "| " + tmForm.getTmVO().getUserAge());
		System.out.println();
	}
}
