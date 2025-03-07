package board.Board_Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import board.Board_Redis.board_Redis;
import board.DTO.board_DTO;
import board.DTO.users_DTO;
import board.ETCClass.MyFileManager;
import board.ETCClass.MyRegulexMethod;
import board.ETCClass.StaticPath;
import board.security.MySecurityMethods;

@Service
public class Main_Service implements StaticPath{
	
	private board_Redis board_Redis;
	private board_Service board_Service;
	private MyFileManager fileManager;
	public Main_Service(
			board_Redis board_Redis,
			board_Service board_Service,
			MyFileManager fileManager
	) 
	{
		this.board_Redis = board_Redis;
		this.board_Service = board_Service;
		this.fileManager = fileManager;
	}
	
	public String proxy(String url,String body) {
		StringBuilder sb = new StringBuilder();
		HttpsURLConnection https = null;
		try {
			https = (HttpsURLConnection)new URL(url).openConnection();
			https.setRequestMethod("POST");
			https.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			https.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Whale/4.29.282.15 Safari/537.36");
			https.setRequestProperty("Content-Length", "length");
			
			
			https.setDoInput(true);
			https.setDoOutput(true);
			
			try(OutputStream wr = https.getOutputStream();){
				wr.write(body.getBytes());
				wr.flush();
				wr.close();
			}
			
			try(BufferedReader br = new BufferedReader(new InputStreamReader(https.getInputStream(),Charset.forName("UTF-8")))){
				String line = null;
				while((line = br.readLine()) != null)sb.append(line);
			}catch(Exception e) {e.printStackTrace();}
		
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return sb.toString();
	}
	
	
	
	public void saveCheck(String title,String content,int board_no) {
		if(board_no == 0) {
			int user_no = MySecurityMethods.getObject(users_DTO.class).getUser_no();
			if(!title.isEmpty() || !contentIsEmpty(content)){
				board_Redis.setTemporaryBoard(title, content, user_no);
			}else {
				board_DTO dto = board_Redis.getTemporaryBoard(user_no);
				if(dto != null) {
					board_Redis.delTemporaryUser(user_no);
				}
			}
		}
		else {
			content = board_Service.getOneBoard(board_no).getContent();
		}
		
		String dirPath = fileManager.getPath(board_no);
		fileManager.matchAndRemoveFile(content,dirPath);
		
	}
	
	public boolean contentIsEmpty(String content) {
		Map<String,Integer> map = MyRegulexMethod.matchMap(content, "(?<=<p>)(?!<br>).*?(?=<\\/p>)"); 
		return map.keySet().size() == 0;
	}
	
	public String saveFile(MultipartFile mt,int no) throws IllegalStateException, IOException {
		String savePath = fileManager.getName(no, mt.getOriginalFilename());
		mt.transferTo(new File(defaultPath+savePath));
		return savePath;
	}
	
}
