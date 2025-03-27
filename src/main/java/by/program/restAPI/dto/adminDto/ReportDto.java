package by.program.restAPI.dto.adminDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportDto {

    private long totalActiveUsers;
    private long newUsers;
    private long totalActiveTask;
    private long totalCompletedTask;
    private long totalDeletedTask;

}
