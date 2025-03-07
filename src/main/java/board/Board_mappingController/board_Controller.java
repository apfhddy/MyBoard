package board.Board_mappingController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import board.Board_Service.board_Service;
import board.Board_Service.comments_Service;
import board.DTO.board_DTO;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class board_Controller {
	
	private board_Service board_Service;
	private comments_Service comments_Service;
	
	
	public board_Controller 
	(
			board_Service board_Service,
			comments_Service comments_Service
	
	) 
	{
		this.board_Service = board_Service;
		this.comments_Service = comments_Service;
	}
	
	@PostMapping("/write")
	public String insertBoard(board_DTO board_dto) {
		board_Service.insertBoard(board_dto);
		return "redirect:/";
	}
	
	@GetMapping("/{board_no}")
	public String oneContent(@PathVariable("board_no") int board_no,HttpServletRequest req) {
		req.setAttribute("content",board_Service.getOneContent(board_no,!req.isUserInRole("admin")));
		req.setAttribute("comments",comments_Service.getCommentsByBoardNo(board_no));
		req.setAttribute("data",req.getParameter("data-token"));
		
		return "content";
	}
	
	@PostMapping("/modify/{board_no}")
	public String updateBoard(Model m,board_DTO board_DTO) {
		board_Service.updateBoard(board_DTO);
		return "redirect:/"+board_DTO.getBoard_no();
	}

	@PostMapping("/delete/{board_no}")
	public String deleteBoard(@PathVariable("board_no") int board_no) {
		board_Service.deleteBoard(board_no);
		return "redirect:/";
	}
	
	
	
}
