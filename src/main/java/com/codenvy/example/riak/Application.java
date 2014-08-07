package com.codenvy.example.riak;

import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.RiakFactory;
import com.basho.riak.client.bucket.Bucket;

import java.util.Arrays;
import java.util.List;

public class Application {
    public static void main(String[] args) throws Exception {
        IRiakClient client = RiakFactory.httpClient();

        final String PROPERTY_BUCKET = "properties";

        System.out.println(String.format("Bucket %s created.", PROPERTY_BUCKET));
        Bucket bucket = client.fetchBucket(PROPERTY_BUCKET).execute();

        System.out.println();

        List<Property> properties = Arrays.asList(new Property("prop1", "value1", "some description"),
                                                  new Property("prop2", "value2", "another description"),
                                                  new Property("prop3", "value3", "awesome description"));

        for (Property property : properties) {
            System.out.println(String.format("Object %s successfully stored.", property.toString()));
            bucket.store(property).execute();
        }

        System.out.println();

        Property gsonConvertedProperty = new Property("prop4", "value4", "property stored with different converter");
        System.out.println(String.format("Store object with custom content serialization: %s", gsonConvertedProperty.toString()));
        bucket.store(gsonConvertedProperty).withConverter(new CustomGSONConverter(PROPERTY_BUCKET)).execute();
        gsonConvertedProperty = bucket.fetch(gsonConvertedProperty.getPropertyName(), Property.class)
                                      .withConverter(new CustomGSONConverter(PROPERTY_BUCKET))
                                      .execute();
        System.out.println(String.format("Fetch stored object with custom content serialization: %s", gsonConvertedProperty.toString()));

        System.out.println("Delete from bucket document with key 'prop4'.");
        bucket.delete(gsonConvertedProperty).execute();

        System.out.println();

        Property fetchedProperty = bucket.fetch("prop2", Property.class).execute();
        System.out.println(String.format("Fetch property object with key 'prop2': %s", fetchedProperty.toString()));

        System.out.println();
        System.out.println("Update document with key 'prop2'.");
        fetchedProperty.setPropertyDescription("changed description");
        bucket.store(fetchedProperty).execute();

        System.out.println();
        System.out.println("Fetch all stored documents.");

        for (String documentKey : bucket.keys().getAll()) {
            Property storedProperty = bucket.fetch(documentKey, Property.class).execute();
            if (storedProperty != null) {
                System.out.println(String.format("Stored document: %s", storedProperty.toString()));
            }
        }

        client.shutdown();
    }
}
