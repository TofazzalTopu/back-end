package com.netizen.cms.api.academic.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatInfoViewResponse {
	
	private Long seatId;
	private Integer totalSeat;	
	private Integer seatSerial;
	private Long classId;	
	private String className;
	private String groupName;
	private Long groupId;
}
