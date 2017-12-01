package info.zznet.znms.web.module.common.page.jackson2;

import info.zznet.znms.web.module.common.page.PageList;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**jackson objectMapper
 * @author yuanjingtao
 */
public class PageListJsonMapper extends ObjectMapper{
	
	private static final long serialVersionUID = 6570669831342989951L;

	@SuppressWarnings("deprecation")
	public PageListJsonMapper() {
        SimpleModule module = new SimpleModule("PageListJSONModule", new Version(1, 0, 0, null));
        module.addSerializer(PageList.class, new PageListJsonSerializer(this));
        registerModule(module);
    }
}
