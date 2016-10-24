package com.nickblomberg.kivation.models;

import org.parceler.Parcel;

/**
 * // TODO create class javadoc
 *
 * @author Nick Blomberg
 */

@Parcel(Parcel.Serialization.BEAN)
public class Description {

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Description{" +
                "description='" + description + '\'' +
                '}';
    }
}
