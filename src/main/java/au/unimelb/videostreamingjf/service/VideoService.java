package au.unimelb.videostreamingjf.service;

import org.springframework.core.io.FileSystemResource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface VideoService {
    Stream<Path> loadAllVideoInfo() throws IOException;
    FileSystemResource loadResource(String fileName);
    Stream<Path> loadAllVideoFolder() throws IOException;
    Stream<Path> loadAllEpisodeInfo(String folderName) throws IOException;
}
