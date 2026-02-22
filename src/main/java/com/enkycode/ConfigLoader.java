package com.enkycode;

import com.enkycode.drops.Item;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ConfigLoader {
    Slayers slayer;
    public ConfigLoader(Slayers s) {
        slayer = s;
    }
    public List<Item> loadItems() {
        Gson gson = new Gson();
        List<Item> items = new ArrayList<>();
        try (FileReader reader = new FileReader("configFiles/items"+slayer.name()+".json")) {
            Type listType = new TypeToken<List<Item>>(){}.getType();
            items.addAll(gson.fromJson(reader, listType));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return items;
    }
}
