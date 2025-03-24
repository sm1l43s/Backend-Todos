package by.program.restAPI.web.user;

import by.program.restAPI.dto.forUserDto.UserDto;
import by.program.restAPI.model.Status;
import by.program.restAPI.model.User;
import by.program.restAPI.responseEntity.CommonResponse;
import by.program.restAPI.responseEntity.ResponseFromServer;
import by.program.restAPI.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = UserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    static final String REST_URL = "/api/v1";

    private final UserService userService;

    @GetMapping( "/users/{id}")
    public ResponseEntity getUserById(@PathVariable("id") long id) {
        User user = userService.findByIdAndStatusNot(id, Status.DELETED);
        CommonResponse response = null;

        if (user == null) {
            response = new CommonResponse(HttpStatus.NOT_FOUND, null, "Not found user by id: " + id, 1);
            return ResponseFromServer.returnResult(response, HttpStatus.OK);
        }
        response = new CommonResponse(HttpStatus.OK, UserDto.fromUserToUserDto(user), "", 0);
        return ResponseFromServer.returnResult(response, HttpStatus.OK);
    }


//    @GetMapping("/{username}")
//    @Transactional
//    @Operation(summary = "Get trainee details", description = "Gets the details of the specified trainee")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Trainee details retrieved successfully"),
//            @ApiResponse(responseCode = "404", description = "Trainee not found")
//    })
//    public TraineeDto get(@PathVariable String username) {
//        log.debug("Get the trainee with username={}", username);
//        Trainee receivedTrainee = traineeService.getWithUser(username);
//        List<Trainer> listTrainers = trainerService.getTrainersForTrainee(username);
//        return createDtoWithTrainerDtoList(receivedTrainee, listTrainers);
//    }

}
