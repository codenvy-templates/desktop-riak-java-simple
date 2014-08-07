package com.codenvy.example.riak;

import com.basho.riak.client.IRiakObject;
import com.basho.riak.client.builders.RiakObjectBuilder;
import com.basho.riak.client.cap.VClock;
import com.basho.riak.client.convert.ConversionException;
import com.basho.riak.client.convert.Converter;
import com.basho.riak.client.convert.NoKeySpecifiedException;
import com.basho.riak.client.http.util.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static com.basho.riak.client.convert.KeyUtil.getKey;

public class CustomGSONConverter implements Converter<Property> {

    private String bucket;

    public CustomGSONConverter(String bucket) {
        this.bucket = bucket;
    }

    @Override
    public IRiakObject fromDomain(Property domainObject, VClock vclock) throws ConversionException {
        String key = getKey(domainObject);

        if (key == null) {
            throw new NoKeySpecifiedException(domainObject);
        }

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(domainObject);

        return RiakObjectBuilder.newBuilder(bucket, key)
                                .withValue(json)
                                .withVClock(vclock)
                                .withContentType(Constants.CTYPE_JSON)
                                .build();
    }

    @Override
    public Property toDomain(IRiakObject riakObject) throws ConversionException {
        if (riakObject == null) {
            return null;
        }

        Gson gson = new GsonBuilder().create();
        return gson.fromJson(riakObject.getValueAsString(), Property.class);
    }
}
