# Assay Depot Java SDK

This is a the Official Assay Depot Java SDK. You can use it to interact with the Assay Depot (http://www.assaydepot.com) marketplace from within your Java program. More information about the API can be found at http://assaydepot.github.com/api.

## Assay Depot Developer Program

An authentication token is required for the API to function. If you would like access to the API, please email cpetersen@assaydepot.com.

## Configuration

The Java SDK exposes a configuration object which can be used to directly configure the SDK.

```java
import com.assaydepot.conf.Configuration;
import java.net.URL;

conf = new Configuration();
conf.setUrl(new URL("https://www.assaydepot.com/api"));
conf.setApiToken("YOUR TOKEN HERE");
```

Alternatively, you may choose to configure the SDK using a properties file:

```java
import com.assaydepot.conf.Configuration;
import java.net.URL;
import java.io.InputStream;
import java.util.Properties;

ClassLoader loader = Thread.currentThread().getContextClassLoader(); 
InputStream stream = loader.getResourceAsStream("assaydepot.properties");
Properties prop = new Properties();
prop.load(stream);

conf = new Configuration();
conf.setUrl(new URL(prop.getProperty("assaydepot.url")));
conf.setApiToken(prop.getProperty("assaydepot.api_token"));
```

The properties file would look like:
```
assaydepot.url=https://www.assaydepot.com/api
assaydepot.api_token=YOUR TOKEN HERE
```

## Accessing Wares

The SDK allows users to search for and get the details of wares in the Assay Depot system. You search using the ```AssayDepot#getWareRefs``` method. For example, to search for wares containing the word "antibody" you would do the following (assuming you've already created the ```conf``` object:
```java
AssayDepot assayDepot = AssayDepotFactory.getAssayDepot( conf );
Results results = assayDepot.getWareRefs( "antibody" );
for( WareRef ref : results.getWareRefs() ) {
  System.out.println( "ware = ["+ref.getName()+"]" );
}
```

Searching for wares returns ```WareRef``` objects which contain a subset of the information contained in a ```Ware``` object. You retrieve the entire ware, use the ```AssayDepot#getWare``` method. For example:
```java
AssayDepot assayDepot = AssayDepotFactory.getAssayDepot( conf );
Ware ware = assayDepot.getWare("SOME WARE ID");
System.out.println( "HTML = ["+ware.htmlDescription()+"]" );
```

## Accessing Providers

The SDK allows users to search for and get the details of providers in the Assay Depot system. You search using the ```AssayDepot#getProviderRefs``` method. For example, to search for providers containing the word "china" you would do the following (assuming you've already created the ```conf``` object:
```java
AssayDepot assayDepot = AssayDepotFactory.getAssayDepot( conf );
Results results = assayDepot.getProviderRefs( "china" );
for( ProviderRef ref : results.getProviderRefs() ) {
  System.out.println( "provider = ["+ref.getName()+"]" );
}
```

Searching for providers returns ```ProviderRef``` objects which contain a subset of the information contained in a ```Provider``` object. You retrieve the entire provider, use the ```AssayDepot#getProvider``` method. For example:
```java
AssayDepot assayDepot = AssayDepotFactory.getAssayDepot( conf );
Provider provider = assayDepot.getProvider("SOME WARE ID");
System.out.println( "HTML = ["+provider.htmlDescription()+"]" );
```

## License

The Assay Depot Java SDK is released under the MIT license.
