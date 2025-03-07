package board.DTO;

import java.sql.Date;

import lombok.Data;

@Data
public class board_DTO {
	private int board_no;
	private String title;
	private int user_no;
	private String content;
	private Date create_date;
}
