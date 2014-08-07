package com.codenvy.example.riak;

import com.basho.riak.client.convert.RiakKey;

public class Property {
    @RiakKey
    private String propertyName;
    private String propertyValue;
    private String propertyDescription;

    public Property() {}

    public Property(String propertyName, String propertyValue, String propertyDescription) {
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
        this.propertyDescription = propertyDescription;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getPropertyDescription() {
        return propertyDescription;
    }

    public void setPropertyDescription(String propertyDescription) {
        this.propertyDescription = propertyDescription;
    }

    @Override
    public String toString() {
        return "Property{" +
               "propertyName='" + propertyName + '\'' +
               ", propertyValue='" + propertyValue + '\'' +
               ", propertyDescription='" + propertyDescription + '\'' +
               '}';
    }
}
