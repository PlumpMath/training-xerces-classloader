package com.peergreen.jonas.tutorials.classloading;

/**
 * A {@code ClassLoaders} is ...
 *
 * @author Guillaume Sauthier
 */
public class ClassLoaders {

    public static ClassLoader loader(Class<?> type) {
        ClassLoader loader = type.getClassLoader();
        if (loader == null) {
            // System loader
            loader = ClassLoader.getSystemClassLoader();
        }
        return loader;
    }
}
