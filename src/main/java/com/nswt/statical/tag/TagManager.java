package com.nswt.statical.tag;

import java.util.ArrayList;

import com.nswt.cms.tag.CmsAdTag;
import com.nswt.cms.tag.CmsArticleTag;
import com.nswt.cms.tag.CmsCatalogTag;
import com.nswt.cms.tag.CmsCommentTag;
import com.nswt.cms.tag.CmsFormTag;
import com.nswt.cms.tag.CmsFriendLinkTag;
import com.nswt.cms.tag.CmsIfTag;
import com.nswt.cms.tag.CmsImagePlayerTag;
import com.nswt.cms.tag.CmsIncludeTag;
import com.nswt.cms.tag.CmsLinkTag;
import com.nswt.cms.tag.CmsListTag;
import com.nswt.cms.tag.CmsVarTag;
import com.nswt.cms.tag.CmsVoteTag;
import com.nswt.framework.utility.StringUtil;
import com.nswt.statical.tag.impl.ZIfTag;
import com.nswt.statical.tag.impl.ZIncludeTag;
import com.nswt.statical.tag.impl.ZListTag;
import com.nswt.statical.tag.modifiers.CharWidth;
import com.nswt.statical.tag.modifiers.Format;

/*
 * @Author NSWT
 * @Date 2016-7-21
 * @Mail nswt@nswt.com.cn
 */
public class TagManager {
	private static IModifierHandler[] handlers = null;

	public static IModifierHandler[] getAllModifers() {
		if (handlers == null) {
			ArrayList list = new ArrayList();

			// 默认的修饰符
			list.add(new Format());
			list.add(new CharWidth());

			// todo:从数据库中读取

			// 转换成数组
			IModifierHandler[] arr = new IModifierHandler[list.size()];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = (IModifierHandler) list.get(i);
			}
			handlers = arr;
		}
		return handlers;
	}

	public static TagBase[] getAllTags() {
		ArrayList list = new ArrayList();

		// 默认的修饰符
		list.add(new CmsAdTag());
		list.add(new CmsCommentTag());
		list.add(new CmsCatalogTag());
		list.add(new CmsFormTag());
		list.add(new CmsImagePlayerTag());
		list.add(new CmsIncludeTag());
		list.add(new CmsLinkTag());
		list.add(new CmsListTag());
		list.add(new CmsVarTag());
		list.add(new CmsVoteTag());

		list.add(new CmsArticleTag());
		list.add(new CmsFriendLinkTag());
		list.add(new CmsIfTag());

		list.add(new ZIfTag());
		list.add(new ZListTag());
		list.add(new ZIncludeTag());

		TagBase[] arr = new TagBase[list.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (TagBase) list.get(i);
		}
		return arr;
	}

	public static TagBase getTagByName(String prefix, String name) {
		TagBase[] tags = getAllTags();
		for (int i = 0; i < tags.length; i++) {
			TagBase tag = tags[i];
			if (StringUtil.isEmpty(tag.getPrefix()) || StringUtil.isEmpty(tag.getTagName())) {
				throw new RuntimeException("标签没有标签前缀或标签名:" + tag.getClass().getName());
			}
			if (tag.getPrefix().equalsIgnoreCase(prefix) && tag.getTagName().equalsIgnoreCase(name)) {
				return tag;
			}
		}
		return null;
	}
}
