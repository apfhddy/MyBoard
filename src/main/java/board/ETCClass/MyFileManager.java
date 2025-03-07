package board.ETCClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.springframework.context.annotation.Configuration;

import board.DTO.users_DTO;
import board.security.MySecurityMethods;

@Configuration
public class MyFileManager extends Thread implements StaticPath{
	
	private  final int sleepMills = 1000*60*60*24;
	
	public MyFileManager() {
		start();
	}
	
	@Override
	public void run() {
		while(true) {
			task();
			try {
				sleep(sleepMills);
			} catch (InterruptedException e) {e.printStackTrace();} 
		}
	}
	
	public void task() {
		File tmp = new File(defaultPath+temporaryPath);
		
		long time = new Date().getTime();
		
		for(File f : tmp.listFiles()) {
			if(86400000 <= time - f.lastModified()) {
				removeFile(f);
			}
		}
	}
	public boolean removeFile(File f) {
		for(File ff : f.listFiles()) {
			ff.delete();
		}
		return f.delete();
	}
	
	public void changeFileDir(String content,int user_no,int board_no) {
		File temporaryDir = new File(defaultPath+temporaryPath+"\\"+user_no);
		if(!temporaryDir.exists()) return;
			
		Map<String,Integer> useFile = MyRegulexMethod.matchMap(content,"(?<=(<(img|span)[^>]+src=[\"']\\\\rs\\\\local\\\\temporary\\\\"+user_no+"\\\\))([^\"']+)");
		File boardDir = null;
		if(useFile.keySet().size() != 0) {
			boardDir = new File(defaultPath+boardPath+"\\"+board_no);
			boardDir.mkdir();
		}
		
		for(File f : temporaryDir.listFiles()) {
			if(useFile.get(f.getName()) == null) {
				f.delete();
				continue;
			}
			File ff = new File(boardDir,f.getName());
			try(FileInputStream fis = new FileInputStream(f);FileOutputStream fos = new FileOutputStream(ff)){
				ff.createNewFile();
				fos.write(fis.readAllBytes());
			} 
			catch (FileNotFoundException e) {e.printStackTrace();} 
			catch (IOException e) {e.printStackTrace();}
			f.delete();
		}
		temporaryDir.delete();
	}
	
	public void removeAll(String path) {
		File f = new File(defaultPath+path);
		if(!f.exists())return;
		
		for(File ff : f.listFiles()) {
			ff.delete();
		}
		f.delete();
	}
	
	public String changeSrc(String content,int user_no,int board_no) {
		Map<String,Integer> useFile = MyRegulexMethod.matchMap(content,"(<(img|span)[^>]+src=[\"'])([^\"']+)");
		String defaultSrc = temporaryPath+"\\"+user_no;
		String changeSrc = boardPath+"\\"+board_no;
		for(String key : useFile.keySet()) {
			int index = key.indexOf(defaultSrc);
			String first = key.substring(0,index);
			String last = key.substring(index+defaultSrc.length());
			content = content.replace(key,first+changeSrc+last);
		}
		return content;
	}
	
	
	public void matchAndRemoveFile(String content,String path) {
		//img 또는 span 태그의 src를 가져와 연관성 체크
		path = path.replaceAll("\\\\","\\\\\\\\");
		
		Map<String,Integer> useFile = MyRegulexMethod.matchMap(content,"(?<=(<(img|span)[^>]+src=[\"']"+path+"\\\\))([^\"']+)");
		File f = new File(defaultPath+path);
		System.out.println(useFile+"   |   "+f.getPath() );
		if(f.exists()) {
			for(File ff : f.listFiles()) {
				if(useFile.get(ff.getName()) == null)ff.delete();
			}
			if(f.listFiles().length == 0)f.delete();
		}
	}
	
	public String getName(int no,String name) {
		String savePath = getPath(no); 
		
		File saveFile = new File(defaultPath+savePath);
		if(!saveFile.exists())
			saveFile.mkdir();
		
		File target = new File(saveFile,name);
		
		while(target.exists()) {
			int index = name.lastIndexOf("."); 
			String first = name.substring(0,index);
			String last = name.substring(index);
			name = first+(new Date().getTime())+last;
			target = new File(defaultPath+savePath+"\\"+name);
		}
		
		return savePath+"\\"+name;
	}
	public String getPath(int board_no) {
		return board_no == 0? 
				temporaryPath+"\\"+MySecurityMethods.getObject(users_DTO.class).getUser_no() 
				: 
				boardPath+"\\"+board_no;
	}
}
