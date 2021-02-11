
package com.netizen.cms.api.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author riad
 */
@Component
public class UserInfoUtils {

	private static ApplicationContext context;

	@Autowired
	public UserInfoUtils(ApplicationContext ac) {
		context = ac;
	}

	public static ApplicationContext getContext() {
		return context;
	}

	/*
	 * public static String getHashPassword(String password) { PasswordEncoder
	 * userPasswordEncoder = context.getBean("userPasswordEncoder",
	 * PasswordEncoder.class); return userPasswordEncoder.encode(password); }
	 * 
	 * public static boolean isPreviousPasswordCorrect(String previousPlainPassword,
	 * String previousEncriptedPassword) { PasswordEncoder userPasswordEncoder =
	 * context.getBean("userPasswordEncoder", PasswordEncoder.class); return
	 * userPasswordEncoder.matches(previousPlainPassword,
	 * previousEncriptedPassword); }
	 */

	/*
	 * public static String getLoggedInUserName() { Users user = (Users)
	 * SecurityContextHolder.getContext().getAuthentication().getPrincipal(); return
	 * user.getUsername();
	 * 
	 * }
	 * 
	 * public static Long getLoggedInUserId() { Users user = (Users)
	 * SecurityContextHolder.getContext().getAuthentication().getPrincipal(); return
	 * user.getId();
	 * 
	 * }
	 * 
	 * public static Long getLoggedInICmsId() { Users user = (Users)
	 * SecurityContextHolder.getContext().getAuthentication().getPrincipal(); return
	 * user.getCmsInfo().getCmsId();
	 * 
	 * }
	 * 
	 * public static CmsInfo getLoggedInCmsInfo() { Users user = (Users)
	 * SecurityContextHolder.getContext().getAuthentication().getPrincipal(); return
	 * user.getCmsInfo(); }
	 */

}
