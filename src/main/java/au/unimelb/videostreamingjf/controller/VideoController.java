package au.unimelb.videostreamingjf.controller;

import au.unimelb.videostreamingjf.domain.VideoFileInfo;
import au.unimelb.videostreamingjf.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/file")
public class VideoController {

    @Autowired
    VideoService videoService;

    /**
     * return a raw html for browser, if you wish to have a restful response, eg, list of urls,
     * change this return type to List<VideoFileInfo> and return fileInfos will do the trick.
     */
    @GetMapping(value = "/videoList", produces = MediaType.TEXT_HTML_VALUE)
    public String getListVideoInfo(HttpServletRequest request) {

        // build raw html
        String head = "<html>" + "<header><title>Repository</title></header>" + "<body>" + "<h1>My Movie Repository</h1>";
        String tail = "</body>" + "</html>";
        StringBuilder content = new StringBuilder();

        try {
            List<VideoFileInfo> fileInfos = videoService.loadAll().map(path -> {
                String fileName = path.toString();
                String url = MvcUriComponentsBuilder.fromMethodName(VideoController.class, "getVideoByName", path.toString()).build().toString();
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
     * get single video file from server
     * @param fileName name of the video file
     * @return resource for browser
     */
    @GetMapping("/videoList/{fileName:.+}")
    public @ResponseBody
    ResponseEntity<FileSystemResource> getVideoByName(@PathVariable String fileName) {
        return ResponseEntity.ok().header("Content-Type", "video/mp4").body(videoService.loadResource(fileName));
    }

}