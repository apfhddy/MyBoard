package board.Board_mappingController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import board.Board_Service.comments_Service;
import board.DTO.comments_DTO;

@Controller
public class comments_Controller {
	
	private comments_Service comments_Service;
	
	public comments_Controller(comments_Service comments_Service) {
		this.comments_Service = comments_Service;
	}
	
	
	@PostMapping("/comment/write")
	public String write(comments_DTO comment_DTO){
		if(comment_DTO.getComment_no() != 0)
			comments_Service.replyComment(comment_DTO, comment_DTO.getComment_no());
		else
			comments_Service.writeComment(comment_DTO);
		
		return "redirect:/"+comment_DTO.getBoard_no();
	}
	
	@PostMapping("/comment/update/{comment_no}")
	public String update(comments_DTO comment_DTO) {
		comments_Service.updateComment(comment_DTO);
		return "redirect:/"+comment_DTO.getBoard_no();
	}
	
	@PostMapping("/comment/delete/{comment_no}")
	public String delete(comments_DTO comment_DTO) {
		comments_Service.deleteUpdateComment(comment_DTO);
		return "redirect:/"+comment_DTO.getBoard_no();
	}
	
}
