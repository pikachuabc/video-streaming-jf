package au.unimelb.videostreamingjf.controller;

import au.unimelb.videostreamingjf.domain.VideoFileInfo;
import au.unimelb.videostreamingjf.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/file")
@CrossOrigin(allowedHeaders = "*")
public class VideoController {

    @Autowired
    VideoService videoService;

    /**
     * return a raw html for browser containing all video file url in rootPath (max 3 depth)
     */
    @Deprecated
    @GetMapping(value = "/allVideo", produces = MediaType.TEXT_HTML_VALUE)
    public String getEpisodeInfo() {

        // build raw html
        String head = "<html>" + "<header><title>Repository</title></header>" + "<body>" + "<h1>My Movie Repository</h1>";
        String tail = "</body>" + "</html>";
        StringBuilder content = new StringBuilder();

        try {
            List<VideoFileInfo> fileInfos = videoService.loadAllVideoInfo().map(path -> {
                String fileName = path.toString();
                String url = MvcUriComponentsBuilder.fromMethodName(VideoController.class, "getVideoByName", "",path.toString()).build().toString();
                return new VideoFileInfo(fileName, url);
            }).collect(Collectors.toList());


            for (VideoFileInfo fileInfo : fileInfos) {
                content.append(String.format("<a href=\"%s\">%s</a><br>", fileInfo.getUrl(), fileInfo.getName()));
            }

        } catch (IOException e) {
            content.append("<p>something wrong - -, most likely wrong dictionary path</p>");
        }
        return head + content.toString() + tail;

    }

    /**
     * @return return a list of string indicating what movie/TV shows available on
     * the server
     */
    @GetMapping(value = "/videoFolderList")
    public List<String> getVideoFolder() {
        try {
            return videoService.loadAllVideoFolder().map(Path::toString).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return return a list of video information in given folder, eg, all
     *  episode in "bingBangTheory" folder, assuming we organize the mp4
     *  video in this structure
     */
    @GetMapping(value = "/episodeList")
    public List<VideoFileInfo> getEpisodeInfo(String folderName) {

        try {
            return videoService.loadAllEpisodeInfo(folderName).map(path -> {
                String fileName = path.toString();
                String url = MvcUriComponentsBuilder.fromMethodName(VideoController.class, "getVideoByName", folderName ,path.toString()).build().toString();
                return new VideoFileInfo(fileName, url);
            }).collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * get single video file from server
     *
     * @param fileName name of the video file
     * @return resource for browser
     */
    @GetMapping("/{folderName:.+}/{fileName:.+}")
    public @ResponseBody
    ResponseEntity<FileSystemResource> getVideoByName( @PathVariable String folderName,@PathVariable String fileName) {
        return ResponseEntity.ok().header("Content-Type", "video/mp4").body(videoService.loadResource(Paths.get(folderName,fileName).toString()));
    }

}