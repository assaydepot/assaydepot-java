package com.assaydepot;

import com.assaydepot.conf.Configuration;

public class AssayDepotFactory {
	
  private Configuration conf;  
  /**
   * Creates a AssayDepotFactory with the given configuration.
   *
   * @param conf the configuration to use
   */
  public AssayDepot getAssayDepot(Configuration conf) {
    if (conf == null) {
        throw new NullPointerException("configuration cannot be null");
    }
    this.conf = conf;
    return new AssayDepotImpl( conf );
  }
}
