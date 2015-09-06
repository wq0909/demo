package com.netease.yixin.publicaccount.datang.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netease.framework.dao.DomainDaoSupport;
import com.netease.framework.dao.annotation.NCache;
import com.netease.framework.dao.util.Const.OptionType;
import com.netease.yixin.publicaccount.datang.meta.PAConfig;

@Component
public class PAConfigDao {

	protected DomainDaoSupport<PAConfig> domainDaoSupport;

	@Autowired
	public void setDomainDaoSupport(DomainDaoSupport<PAConfig> domainDaoSupport) {
		this.domainDaoSupport = domainDaoSupport;
		this.domainDaoSupport.initConfig(PAConfig.class);
	}

	//@NCache(optionType = OptionType.ADD)
	public PAConfig add(PAConfig u) {
		return this.domainDaoSupport.add(u);
	}

	//@NCache(optionType = OptionType.GET)
	public PAConfig getById(long id) {
		return this.domainDaoSupport.getById(id);
	}

	//@NCache(optionType = OptionType.UPDATE)
	public PAConfig updateConfig(PAConfig u) {
		return this.domainDaoSupport.update(u);
	}

	public List<PAConfig> getList() {
		return domainDaoSupport.getListByWhere(" status = ? ", new Object[] { 0 });
	}

}
