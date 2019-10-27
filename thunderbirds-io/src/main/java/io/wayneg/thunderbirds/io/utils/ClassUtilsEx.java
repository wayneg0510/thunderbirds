package io.wayneg.thunderbirds.io.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.ClassUtils;
import org.reflections.Reflections;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.stream.Collectors;

public class ClassUtilsEx extends ClassUtils {

    /**
     * Judge whether the target class is simple type.
     *
     * Simple type = primitive + primitive's wrapper + String.class
     */
    public static boolean isSimpleType(Class<?> clazz) {
        return org.springframework.util.ClassUtils.isPrimitiveOrWrapper(clazz) || isString(clazz);
    }

    /**
     * Judge whether the target class is simple type's array.
     *
     * Simple type array = (primitive + primitive's wrapper + String.class) * array
     *
     */
    public static boolean isSimpleTypeArray(Class<?> clazz) {
        return org.springframework.util.ClassUtils.isPrimitiveArray(clazz) || org.springframework.util.ClassUtils.isPrimitiveWrapperArray(clazz) || isStringArray(clazz);
    }

    /**
     * Judge whether the target class is String.class.
     */
    public static boolean isString(Class<?> clazz) {
        return clazz == String.class;
    }

    /**
     * Judge whether the target class is string array.
     */
    public static boolean isStringArray(Class<?> clazz) {
        return clazz.isArray() && isString(clazz.getComponentType());
    }

    /**
     * The result list contains all manifest.mf files existing current thread context class loader.
     * Generic, the manifest file path is "...jar!/META-INF/MANIFEST.MF"
     */
    public static List<Manifest> getManifestListOfSpringBootJar() {
        List<Manifest> manifests = Lists.newArrayList();
        try {
            Enumeration resEnum = Thread.currentThread().getContextClassLoader().getResources(JarFile.MANIFEST_NAME);
            while (resEnum.hasMoreElements()) {
                URL url = (URL)resEnum.nextElement();
                try (InputStream is = url.openStream()) {
                    if (is != null) {
                        Manifest m = new Manifest(is);
                        manifests.add(m);
                    }
                } catch (Exception ignored) {
                }
            }
        } catch (IOException ignored) {
        }

        return manifests;
    }

    /**
     * The target manifest file path: "....jar!/BOOT-INF/lib/<lib-name>.jar!/META-INF-MANIFEST.MF"
     */
    public static Manifest getManifestOfSpringBootLib(Class<?> libClass) {
        if (libClass == null) {
            return null;
        }

        String folder = libClass.getProtectionDomain().getCodeSource().getLocation().toString();
        URL file;
        try {
            file = new URL(folder + JarFile.MANIFEST_NAME);
        } catch (MalformedURLException e) {
            // ignore
            return null;
        }

        try (InputStream is = file.openStream()){
            if (is != null) {
                return new Manifest(is);
            }
        } catch (IOException ignored) {
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<Class<? extends T>> scanSubTypes(String packagePrefix, Class<T> clazz) {
        Reflections reflections = new Reflections(packagePrefix);
        Set<Class<? extends T>> clazzSet = reflections.getSubTypesOf(clazz);
        if (clazzSet != null) {
            return clazzSet.stream().filter(c -> !c.isSynthetic()).collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }
}
