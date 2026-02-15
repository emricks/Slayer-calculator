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
    String subDir;
    int tier;
    public ConfigLoader(Slayers s, int t) {
        slayer = s;
        tier = t;
        subDir = switch (s) {
            case Z -> "zombie";
            case S -> "spider";
            case W -> "wolf";
            case E -> "enderman";
            case V -> "vampire";
            case B -> "blaze";
        };
    }
    public List<Item> loadItems() {
        Gson gson = new Gson();
        List<Item> items = new ArrayList<>();
        try (FileReader reader = new FileReader("configFiles/"+ subDir +"/items"+slayer.name()+tier+".json")) {
            Type listType = new TypeToken<List<Item>>(){}.getType();
            items.addAll(gson.fromJson(reader, listType));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return items;
    }
}
