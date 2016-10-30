package ru.nsu.ccfit.dymova;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class CustomClassLoader extends ClassLoader {

    private Path classesDir;

    public CustomClassLoader(ClassLoader parent, Path classesDir) {
        super(parent);
        this.classesDir = classesDir;
    }

    public List<Class<?>> loadClasses() throws IOException {
        return Files.walk(classesDir)
                .filter(file -> Files.isRegularFile(file))
                .map(file -> {
                    byte[] bytes = new byte[0];
                    try {
                        bytes = Files.readAllBytes(file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return defineClass(null, bytes, 0, bytes.length);
                })
                .collect(Collectors.toList());
    }

}
