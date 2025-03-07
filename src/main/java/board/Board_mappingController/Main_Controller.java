package board.Board_mappingController;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import board.Board_Service.board_Service;
import board.Board_Service.comments_Service;
import board.Board_Service.users_Service;
import board.DTO.board_DTO;
import board.DTO.users_DTO;
import board.ETCClass.GooglePaging;
import board.ETCClass.MyFileManager;
import board.ETCClass.MyMailSender;
import board.ETCClass.StaticPath;
import board.security.MySecurityMethods;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class Main_Controller implements StaticPath{
	
	private board_Service board_Service;
	private comments_Service comments_Service;
	private users_Service users_Service;
	private MyFileManager myFileManager;
	private MyMailSender mailSender;
	
	public Main_Controller
	(
			board_Service board_Service,
			comments_Service comments_Service,
			users_Service users_Service,
			MyFileManager myFileManager,
			MyMailSender mailSender
	) 
	{
		this.board_Service = board_Service;
		this.comments_Service = comments_Service;
		this.users_Service = users_Service;
		this.myFileManager = myFileManager;
		this.mailSender = mailSender;
	}
	
	@GetMapping("/")
	public String index(HttpServletRequest req){
		String target = req.getParameter("target") != null ? req.getParameter("target") : null;
		String keyword = req.getParameter("keyword") != null ? req.getParameter("keyword") : null;
		int pageIndex = req.getParameter("pageIndex") != null ? Integer.parseInt(req.getParameter("pageIndex")):1;    
		GooglePaging gp = new GooglePaging(pageIndex,board_Service.getBoardCnt(target,keyword), 10,15);
		req.setAttribute("contents",board_Service.getAllContents(gp.getPageCnt(),gp.getContentCnt(),pageIndex-1,target,keyword));
		req.setAttribute("pages",gp.getPages());
		req.setAttribute("pageIndex",pageIndex);
		req.setAttribute("target", target);
		req.setAttribute("keyword",keyword);
		return "index";
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}
	
	@GetMapping("/user/history/{nickname}")
	public String userHistory(@PathVariable("nickname") String nickname,Model m) {
		int user_no = users_Service.getUserNoByNickname(nickname);
		if(user_no == -1) {
			user_no = MySecurityMethods.getObject(users_DTO.class).getUser_no();
			nickname = MySecurityMethods.getObject(users_DTO.class).getNickname();
		}
		m.addAttribute("boards",board_Service.getRecentlyBoard(user_no));
		m.addAttribute("comments",comments_Service.getRecentlyComment(user_no));
		m.addAttribute("nickname",nickname);
		return "userHistory";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/join")
	public String join(HttpSession s) {
		mailSender.waitQueue();
		return "join";
	}
	
	@GetMapping("/user/data")
	public String userdata() {
		return "userdata";
	}
	
	@GetMapping("/write")
	public String write(Model m,HttpSession s) throws IOException {
		myFileManager.removeAll(temporaryPath+"/"+MySecurityMethods.getObject(users_DTO.class).getUser_no());
		return "insertBoardForm";
	}
	
	@GetMapping("/modify/{board_no}")
	public String modify(Model m,@PathVariable("board_no") int board_no) {
		board_DTO dto = board_Service.getOneBoard(board_no);
		m.addAttribute("title",dto.getTitle());
		m.addAttribute("content",dto.getContent());
		return "insertBoardForm";
	} 
	
	@GetMapping("/user/changePassword")
	public String changePassword() {
		return "changePassword";
	}
	
	@GetMapping("/certified")
	public String certified() {
		return "certified";
	}
}
