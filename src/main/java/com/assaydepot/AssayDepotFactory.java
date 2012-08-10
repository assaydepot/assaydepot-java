package com.assaydepot;

import com.assaydepot.conf.Configuration;

public class AssayDepotFactory {
	
  private Configuration conf;  
  /**
   * Creates a AssayDepotFactory with the given configuration.
   *
   * @param conf the configuration to use
   */
  public static AssayDepot getAssayDepot(Configuration conf) {
    if (conf == null) {
        throw new NullPointerException("configuration cannot be null");
    }
    return new AssayDepotTreeImpl( conf );
  }
}
