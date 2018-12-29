package com.yfy.beem.clientv3.apiaccess.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.yfy.beem.clientv3.apiaccess.util.PropertyName;
import com.yfy.beem.clientv3.crypto.CryptoUtils;
import com.yfy.beem.clientv3.datamodel.User;

import java.io.IOException;

/**
 * Gson {@link com.google.gson.TypeAdapter} for {@link com.yfy.beem.clientv3.datamodel.User}
 * */
public class UserTypeAdapter extends TypeAdapter<User> {
    @Override
    public void write(JsonWriter jsonWriter, User user) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name(PropertyName.ID);
        jsonWriter.value(user.getId());
        jsonWriter.name(PropertyName.NAME);
        jsonWriter.value(user.getName());
        jsonWriter.name(PropertyName.PUBLIC_KEY);
        jsonWriter.value(CryptoUtils.keyToString(user.getPublicKey()));
        jsonWriter.endObject();
    }

    @Override
    public User read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
