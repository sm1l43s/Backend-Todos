package by.program.restAPI.dto.forAdminDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportsDto {

    private long totalTask;
    private long totalUsers;
    private long newUsers;
    private long totalActiveTask;
    private long totalCompletedTask;
    private long totalDeletedTask;

}
