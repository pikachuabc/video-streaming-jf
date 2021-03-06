# 在线视频播放(mp4) v1.1 
***
基于springboot，servlet一套最基本的视频流在线播放后端 `FileSystemResources`
帮助完成填充 `206 response`以及`content-Range`， 
避免了采用`StreamingBody`或者`byte[]`需要自己写分段请求头以及视频流中产生的`IOException`)
***
使用说明

启动时指定参数`-rootPath [绝对路径]`为视频根目录的绝对路径即可

如果没有前端跑起来后访问localhost:8082/file/allVideo

Api: 
- Get /file/allVideo： 返回目录下最大深度为3的所有视频
- Get /file/videoFolderList: 返回根目录下所有目录
- Get /file/episodeList?__folderName__= XXX: 返回指定目录下的所有视频
- Get /file/streamVideoByName?__folderName__=XXX&__fileName__=XX: 在线播放指定视频
- Get /file/downloadVideoByName?__folderName__=XXX&__fileName__=XX: 下载指定视频 

Dockerhub 支持:  
docker `pull pikachu1023/video-streaming` 注意启动的时候加上 -v 把视频文件夹挂载在container内 
- `docker run -p 8082:8082 -v {视频文件根目录绝对路径}:/share pikachu1023/video-streaming -rootPath /share`
***
# Online video streaming for mp4
***
based on springboot and servlet (we still have webflux as option for a better performance since
this is roughly an IO intense task). `FileSystemResources` automatically handle
 request with `content-range` and gives `206 response` to achieve dragging function when it handles the video
resources. Alternatively, `StreamingBody` or `byte[]` is an option as return type, but you have to fill 
the response headers (`content-range,content-length,accept-typt`) and handle `IOException` during the transmission 
which I don't know exactly why)
***
Usage 

add arg `-rootPath [absolute path of your folder]` when starting as your actual movie folder.
Note this has a maximum 3 depth, if you wish to have a deep tree structure, go edit `Files.walk`in`videoServiceImp.java`

localhost:8082/file/allVideo

Api:
- Get /file/allVideo： get all video from root path
- Get /file/videoFolderList: get all folder from root path
- Get /file/episodeList?__folderName__= XXX: get all video info from given folder
- Get /file/streamVideoByName?__folderName__=XXX&__fileName__=XX: streaming video
- Get /file/downloadVideoByName?__folderName__=XXX&__fileName__=XX: download video

Dockerhub 支持:  
docker `pull pikachu1023/video-streaming` notice add -v when booting container for mounting your video folder in host machine into container
- `docker run -p 8082:8082 -v {root path of your video folder}:/share pikachu1023/video-streaming -rootPath /share`
