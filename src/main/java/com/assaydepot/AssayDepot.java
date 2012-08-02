package com.assaydepot;

import com.assaydepot.conf.Configuration;
import com.assaydepot.result.ProviderRef;
import com.assaydepot.result.WareRef;

public interface AssayDepot {
  
	public ProviderRef getProviders();
	public WareRef getWares();
}
