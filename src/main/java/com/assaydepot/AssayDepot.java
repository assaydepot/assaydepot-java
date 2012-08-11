package com.assaydepot;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;

import com.assaydepot.result.Results;

public interface AssayDepot {
  
	public Results getProviderRefs( String query ) throws JsonParseException, IOException;
	public Results getWares( String query );
}
