package au.unimelb.videostreamingjf.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class VideoServiceImp implements VideoService {

    private final Path rootPath;

    public VideoServiceImp(@Value(value = "${custom.path}") String rootPathString) {
        this.rootPath = Paths.get(rootPathString);
    }

    @Override
    public Stream<Path> loadAll() throws IOException {
        // get ride of none mp4 file
        return Files.walk(this.rootPath, 1)
                .filter(path -> !path.equals(rootPath))
                .filter(path -> path.toString().endsWith("mp4"))
                .map(this.rootPath::relativize);
    }

    @Override
    public FileSystemResource loadResource(String fileName) {

        File file = new File(rootPath + "\\" + fileName);
        return new FileSystemResource(file);

    }
}
