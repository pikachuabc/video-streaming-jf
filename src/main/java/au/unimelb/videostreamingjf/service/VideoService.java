package au.unimelb.videostreamingjf.service;

import org.springframework.core.io.FileSystemResource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface VideoService {
    Stream<Path> loadAll() throws IOException;
    FileSystemResource loadResource(String fileName);
}
