package com.assaydepot;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;

import com.assaydepot.result.Provider;
import com.assaydepot.result.Results;
import com.assaydepot.result.Ware;

public interface AssayDepot {
  
	public Results getProviderRefs( String query );
	public Provider getProvider( String id );
	public Results getWareRefs( String query );
	public Ware getWare( String id );
}
