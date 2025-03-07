package board.DTO;

import java.sql.Date;

import lombok.Data;

@Data
public class comments_DTO {
	private int comment_no;
	private int board_no;
	private int user_no;
	private String comment_data;
	private Date comment_date;
	private int group_no;
	private int orderNum;
	private int step;
	private int childCnt;
	private int parent_no;
}
