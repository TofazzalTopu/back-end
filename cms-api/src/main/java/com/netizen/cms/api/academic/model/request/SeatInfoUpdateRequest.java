package com.netizen.cms.api.academic.model.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatInfoUpdateRequest {

	@NotNull
	private Long seatId;
	
	@NotNull
	private Integer totalSeat;
	
	private Integer seatSerial;

	@NotNull
	private Long classId;
	
	@NotNull
	private Long groupId;
	
	@NotNull
	private Long cmsId;

}
