package melonproject.melon.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import melonproject.melon.entity.artist.ArtistGroupInfoEntity;
import melonproject.melon.entity.artist.ArtistSoloInfoEntity;
import melonproject.melon.entity.artist.album.AlbumCommentEntity;
import melonproject.melon.entity.artist.album.AlbumInfoEntity;
import melonproject.melon.entity.artist.song.SongFileEntity;
import melonproject.melon.repository.artist.ArtistInfoRepository;
import melonproject.melon.repository.artist.album.AlbumCommentRepository;
import melonproject.melon.repository.artist.album.AlbumInfoRepository;
import melonproject.melon.repository.artist.song.SongFileRepository;


@Service
@RequiredArgsConstructor
public class FileService {
    private final String artist_img_path = "artist";
    private final String album_img_path = "album";
    private final String song_img_path = "song";
    private final String comment_img_path = "comment";
    // @Value("${file.image.artist}") String artist_img_path;
    // @Value("${file.image.album}") String album_img_path;
    // @Value("${file.image.song}") String song_img_path;
    // @Value("${file.image.commnet}") String comment_img_path;

    private final ArtistInfoRepository artRepo;
    private final AlbumInfoRepository albumRepo;
    private final SongFileRepository sfRepo;
    private final AlbumCommentRepository acRepo;

    private final AmazonS3Client  s3Client;

    @Value("${cloud.aws.s3.bucket}") String bucket;

    public ResponseEntity<Resource> getImage ( @PathVariable String uri, 
    @PathVariable String type , HttpServletRequest request ) throws Exception { 
    System.out.println(bucket);
    String folderLocation = null;
    String filename = null;
    // 내보낼 파일의 이름을 만든다. 
    // 폴더 경로와 파일의 이름을 합쳐서 목표 파일의 경로를 만든다. 
    if(type.equals("artist")){
        folderLocation = artist_img_path;
        
        filename = artRepo.findByArtUri(uri).getArtFile();
    }else if(type.equals("album")){
        folderLocation = album_img_path;
        filename = albumRepo.findByAlbumUri(uri).getAlbumCover();
    }
    else if(type.equals("comment")){
        folderLocation = comment_img_path;
        filename = uri;
    }else{
        return null;
    }
        String[] split = filename.split("\\.");
        String ext = split[split.length - 1];
        String exportName = uri + "." + ext;
        
        S3Object o = s3Client.getObject(new GetObjectRequest(bucket, folderLocation + "/" + filename));
        System.out.println(o);
        System.out.println("bbb");
		S3ObjectInputStream objectInputStream = o.getObjectContent();
		byte[] bytes = IOUtils.toByteArray(objectInputStream);
		
		Resource resource = new ByteArrayResource(bytes);
		
        return ResponseEntity.ok()
            // 응답의 코드를 200 OK로 설정하고 
            // 산출한 타입을 응답에 맞는 형태로 변환 
            // .contentType(MediaType.parseMediaType(contentType))
            // 내보낼 내용의 타입을 설정 (파일), 
            // attachment; filename*=\""+r.getFilename()+"\" 요청한 쪽에서 다운로드 한 
            // 파일의 이름을 결정 
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(exportName, "UTF-8") + "\"")
            .body(resource);
        // 변환된 파일을 ResponseEntity에 추가 }
    }
    public ResponseEntity<Resource> getSong ( @PathVariable String uri, 
        HttpServletRequest request 
    ) throws Exception { 
    // Path folderLocation = Paths.get(song_img_path);
    String filename = sfRepo.findBySfUri(uri).getSfFile();
    // 내보낼 파일의 이름을 만든다. 
    // 폴더 경로와 파일의 이름을 합쳐서 목표 파일의 경로를 만든다. 

        String[] split = filename.split("\\.");
        String ext = split[split.length - 1];
        String exportName = uri + "." + ext;
        
        S3Object o = s3Client.getObject(new GetObjectRequest(bucket+"/"+song_img_path, filename));
        System.out.println(o);
        System.out.println("bbb");
		S3ObjectInputStream objectInputStream = o.getObjectContent();
		byte[] bytes = IOUtils.toByteArray(objectInputStream);
		
		Resource resource = new ByteArrayResource(bytes);
		
        return ResponseEntity.ok()
            // 응답의 코드를 200 OK로 설정하고 
            // 산출한 타입을 응답에 맞는 형태로 변환 
            // .contentType(MediaType.parseMediaType(contentType))
            // 내보낼 내용의 타입을 설정 (파일), 
            // attachment; filename*=\""+r.getFilename()+"\" 요청한 쪽에서 다운로드 한 
            // 파일의 이름을 결정 
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(exportName, "UTF-8") + "\"")
            .body(resource);
        // 변환된 파일을 ResponseEntity에 추가 }
    }

    public AlbumInfoEntity saveAlbumFile(MultipartFile file) throws AmazonServiceException, SdkClientException, IOException{

        String originFileName = file.getOriginalFilename();
        String[] split = originFileName.split(("\\.")); //.을 기준으로 나눔
        String ext = split[split.length - 1]; //확장자
        String fileName = "";
        for (int i = 0; i < split.length - 1; i++) {
            fileName += split[i]; //원래 split[i]+"." 이렇게 해줘야함
        }
        String saveFileName = "album_"; //보통 원본 이름을 저장하는것이아니라 시간대를 입력함
        Calendar c = Calendar.getInstance();
        saveFileName += c.getTimeInMillis() + "." + ext; // todo_161310135.png 이런식으로 저장됨

        long size = file.getSize(); // 파일 크기
			
			ObjectMetadata objectMetaData = new ObjectMetadata();
			objectMetaData.setContentType(file.getContentType());
			objectMetaData.setContentLength(size);
			
			// S3에 업로드
			s3Client.putObject(
				new PutObjectRequest(bucket+"/"+album_img_path, saveFileName, file.getInputStream(), objectMetaData)
					.withCannedAcl(CannedAccessControlList.PublicRead)
			);
			
        String imagePath = s3Client.getUrl(bucket, originFileName).toString(); // 접근가능한 URL 가져오기
        return AlbumInfoEntity.builder().albumCover(saveFileName).albumUri(fileName).build();
    }
    public ArtistSoloInfoEntity saveArtistSoloFile(MultipartFile file, String name) throws AmazonServiceException, SdkClientException, IOException{
        if(file==null){
            return ArtistSoloInfoEntity.builder().artFile("basic.jpeg").artUri(name).build();
        }

        String originFileName = file.getOriginalFilename();
        String[] split = originFileName.split(("\\.")); //.을 기준으로 나눔
        String ext = split[split.length - 1]; //확장자
        String fileName = "";
        for (int i = 0; i < split.length - 1; i++) {
            fileName += split[i]; //원래 split[i]+"." 이렇게 해줘야함
        }
        String saveFileName = "artist_solo_"; //보통 원본 이름을 저장하는것이아니라 시간대를 입력함
        Calendar c = Calendar.getInstance();
        saveFileName += c.getTimeInMillis() + "." + ext; // todo_161310135.png 이런식으로 저장됨

        long size = file.getSize(); // 파일 크기
			
			ObjectMetadata objectMetaData = new ObjectMetadata();
			objectMetaData.setContentType(file.getContentType());
			objectMetaData.setContentLength(size);
			
			// S3에 업로드
			s3Client.putObject(
				new PutObjectRequest(bucket+"/"+artist_img_path, saveFileName, file.getInputStream(), objectMetaData)
					.withCannedAcl(CannedAccessControlList.PublicRead)
			);
			
        String imagePath = s3Client.getUrl(bucket, originFileName).toString(); // 접근가능한 URL 가져오기
        return ArtistSoloInfoEntity.builder().artFile(saveFileName).artUri(fileName).build();
    }
    public ArtistGroupInfoEntity saveArtistGroupFile(MultipartFile file, String name) throws AmazonServiceException, SdkClientException, IOException{
        if(file==null){
            return ArtistGroupInfoEntity.builder().artFile("basic.jpeg").artUri(name).build();
        }

        String originFileName = file.getOriginalFilename();
        String[] split = originFileName.split(("\\.")); //.을 기준으로 나눔
        String ext = split[split.length - 1]; //확장자
        String fileName = "";
        for (int i = 0; i < split.length - 1; i++) {
            fileName += split[i]; //원래 split[i]+"." 이렇게 해줘야함
        }
        String saveFileName = "artist_Group_"; //보통 원본 이름을 저장하는것이아니라 시간대를 입력함
        Calendar c = Calendar.getInstance();
        saveFileName += c.getTimeInMillis() + "." + ext; // todo_161310135.png 이런식으로 저장됨

        long size = file.getSize(); // 파일 크기
			
			ObjectMetadata objectMetaData = new ObjectMetadata();
			objectMetaData.setContentType(file.getContentType());
			objectMetaData.setContentLength(size);
			
			// S3에 업로드
			s3Client.putObject(
				new PutObjectRequest(bucket+"/"+artist_img_path, saveFileName, file.getInputStream(), objectMetaData)
					.withCannedAcl(CannedAccessControlList.PublicRead)
			);
			
        String imagePath = s3Client.getUrl(bucket, originFileName).toString(); // 접근가능한 URL 가져오기
        return ArtistGroupInfoEntity.builder().artFile(saveFileName).artUri(fileName).build();
    }
    public SongFileEntity saveSongFile(MultipartFile file) throws AmazonServiceException, SdkClientException, IOException{
        Path folderLocation = Paths.get(song_img_path);

        String originFileName = file.getOriginalFilename();
        String[] split = originFileName.split(("\\.")); //.을 기준으로 나눔
        String ext = split[split.length - 1]; //확장자
        String fileName = "";
        for (int i = 0; i < split.length - 1; i++) {
            fileName += split[i]; //원래 split[i]+"." 이렇게 해줘야함
        }
        long size = file.getSize(); // 파일 크기
        String saveFileName = "song_"; //보통 원본 이름을 저장하는것이아니라 시간대를 입력함
        Calendar c = Calendar.getInstance();
        saveFileName += c.getTimeInMillis() + "." + ext; // todo_161310135.png 이런식으로 저장됨

        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType(file.getContentType());
        objectMetaData.setContentLength(size);
        
        // S3에 업로드
        s3Client.putObject(
            new PutObjectRequest(bucket+"/"+song_img_path, saveFileName, file.getInputStream(), objectMetaData)
                .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        
        String imagePath = s3Client.getUrl(bucket, originFileName).toString(); // 접근가능한 URL 가져오기
        return SongFileEntity.builder().sfFile(saveFileName).sfUri(fileName).build();
    }
    public AlbumCommentEntity saveCommentFile(MultipartFile file) throws AmazonServiceException, SdkClientException, IOException{
            String originalName = file.getOriginalFilename(); // 파일 이름
            String[] split = originalName.split(("\\.")); //.을 기준으로 나눔
            String ext = split[split.length - 1]; //확장자
            String saveFileName = "comment_"; //보통 원본 이름을 저장하는것이아니라 시간대를 입력함
            Calendar c = Calendar.getInstance();
            saveFileName += c.getTimeInMillis() + "." + ext; // todo_161310135.png 이런식으로 저장됨
			long size = file.getSize(); // 파일 크기
			
			ObjectMetadata objectMetaData = new ObjectMetadata();
			objectMetaData.setContentType(file.getContentType());
			objectMetaData.setContentLength(size);
			
			// S3에 업로드
			s3Client.putObject(
				new PutObjectRequest(bucket+"/"+comment_img_path, saveFileName, file.getInputStream(), objectMetaData)
					.withCannedAcl(CannedAccessControlList.PublicRead)
			);
			
			String imagePath = s3Client.getUrl(bucket, originalName).toString(); // 접근가능한 URL 가져오기
        return AlbumCommentEntity.builder().albumcFile(saveFileName).albumcUri(file.getOriginalFilename()).build();
    }
}
