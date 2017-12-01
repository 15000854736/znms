package info.zznet.znms.web.util;

import info.zznet.znms.base.util.StringUtil;
import info.zznet.znms.base.util.StringUtil;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 国际化资源工具类
 */
public class MessageUtil implements MessageSourceAware {

	private static MessageSource messageSource;

	public void setMessageSource(MessageSource messageSource) {
		MessageUtil.messageSource = messageSource;
	}

	public static String getMessage(String key) {
		Object[] obj = null;
		return getMessage(key, obj);
	}

	public static String getMessage(String key, Object... args) {
		if (!StringUtil.isNullString(key)) {
			Locale locale = LocaleContextHolder.getLocale();
			if (null == locale) {
				locale = Locale.CHINESE;
			}
			return messageSource.getMessage(key, args, "", locale);
		}
		return "";
	}

}