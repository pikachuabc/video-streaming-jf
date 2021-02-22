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
    public Stream<Path> loadAllVideoInfo() throws IOException {
        // get ride of none mp4 file
        return Files.walk(this.rootPath, 3)
                .filter(path -> !path.equals(rootPath))
                .filter(path -> path.toString().endsWith("mp4"))
                .map(this.rootPath::relativize);
    }

    @Override
    public FileSystemResource loadResource(String fileName) {
        File file = new File(Paths.get(rootPath.toString(),fileName).toString());
        return new FileSystemResource(file);
    }


    @Override
    public Stream<Path> loadAllVideoFolder() throws IOException {
        return Files.walk(this.rootPath,1)
                .filter(path -> !path.equals(rootPath))
                .filter(path -> !path.toString().contains(".")) // we just want folder
                .map(this.rootPath::relativize);
    }


    @Override
    public Stream<Path> loadAllEpisodeInfo(String folderName) throws IOException {
        Path newPath = Paths.get(this.rootPath.toString(),folderName);
        return Files.walk(newPath, 1)
                .filter(path -> !path.equals(rootPath))
                .filter(path -> path.toString().endsWith("mp4"))
                .map(newPath::relativize);// relative path to root folder of this TV show
    }
}
