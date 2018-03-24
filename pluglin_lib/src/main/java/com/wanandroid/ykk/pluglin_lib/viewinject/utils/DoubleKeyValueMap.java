package com.wanandroid.ykk.pluglin_lib.viewinject.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2016/2/23.
 */
public class DoubleKeyValueMap<K1, K2, V> {
    private ConcurrentHashMap<K1, ConcurrentHashMap<K2, V>> k1_k2V_map = new ConcurrentHashMap();

    public DoubleKeyValueMap() {
    }

    public void put(K1 key1, K2 key2, V value) {
        if (key1 != null && key2 != null && value != null) {
            ConcurrentHashMap k2V_map;
            if (this.k1_k2V_map.containsKey(key1)) {
                k2V_map = (ConcurrentHashMap) this.k1_k2V_map.get(key1);
                if (k2V_map != null) {
                    k2V_map.put(key2, value);
                } else {
                    k2V_map = new ConcurrentHashMap();
                    k2V_map.put(key2, value);
                    this.k1_k2V_map.put(key1, k2V_map);
                }
            } else {
                k2V_map = new ConcurrentHashMap();
                k2V_map.put(key2, value);
                this.k1_k2V_map.put(key1, k2V_map);
            }

        }
    }

    public Set<K1> getFirstKeys() {
        return this.k1_k2V_map.keySet();
    }

    public ConcurrentHashMap<K2, V> get(K1 key1) {
        return (ConcurrentHashMap) this.k1_k2V_map.get(key1);
    }

    public V get(K1 key1, K2 key2) {
        ConcurrentHashMap k2_v = (ConcurrentHashMap) this.k1_k2V_map.get(key1);
        return k2_v == null ? null : (V) k2_v.get(key2);
    }

    public Collection<V> getAllValues(K1 key1) {
        ConcurrentHashMap k2_v = (ConcurrentHashMap) this.k1_k2V_map.get(key1);
        return k2_v == null ? null : k2_v.values();
    }

    public Collection<V> getAllValues() {
        ArrayList result = null;
        Set k1Set = this.k1_k2V_map.keySet();
        if (k1Set != null) {
            result = new ArrayList();
            Iterator var3 = k1Set.iterator();

            while (var3.hasNext()) {
                Object k1 = var3.next();
                Collection values = ((ConcurrentHashMap) this.k1_k2V_map.get(k1)).values();
                if (values != null) {
                    result.addAll(values);
                }
            }
        }

        return result;
    }

    public boolean containsKey(K1 key1, K2 key2) {
        return this.k1_k2V_map.containsKey(key1) ? ((ConcurrentHashMap) this.k1_k2V_map.get(key1)).containsKey(key2) : false;
    }

    public boolean containsKey(K1 key1) {
        return this.k1_k2V_map.containsKey(key1);
    }

    public int size() {
        if (this.k1_k2V_map.size() == 0) {
            return 0;
        } else {
            int result = 0;

            ConcurrentHashMap k2V_map;
            for (Iterator var2 = this.k1_k2V_map.values().iterator(); var2.hasNext(); result += k2V_map.size()) {
                k2V_map = (ConcurrentHashMap) var2.next();
            }

            return result;
        }
    }

    public void remove(K1 key1) {
        this.k1_k2V_map.remove(key1);
    }

    public void remove(K1 key1, K2 key2) {
        ConcurrentHashMap k2_v = (ConcurrentHashMap) this.k1_k2V_map.get(key1);
        if (k2_v != null) {
            k2_v.remove(key2);
        }

    }

    public void clear() {
        if (this.k1_k2V_map.size() > 0) {
            Iterator var1 = this.k1_k2V_map.values().iterator();

            while (var1.hasNext()) {
                ConcurrentHashMap k2V_map = (ConcurrentHashMap) var1.next();
                k2V_map.clear();
            }

            this.k1_k2V_map.clear();
        }

    }
}